package it.polimi.console;

import it.polimi.automata.IBA;
import it.polimi.automata.io.out.IBAToElementTrasformer;
import it.polimi.automata.io.out.XMLWriter;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.RefinementGenerator;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.in.constraint.ConstraintReader;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.replacement.ReplacementToStringTransformer;
import it.polimi.replacementchecker.ReplacementChecker;
import it.polimi.statemachine.states.CHIAReplacementState;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import action.CHIAException;

/**
 * contains the console which is used for the replacement checking
 * 
 * @author Claudio Menghi
 *
 */
public class CHIAReplacementConsole {

	private static final Logger logger = Logger
			.getLogger(CHIAReplacementConsole.class);

	private CHIAReplacementState chiaState = CHIAReplacementState.INIT;
	/**
	 * contains the constraint to be considered
	 */
	protected Constraint constraint;
	/**
	 * contains the replacement to be considered
	 */
	protected Replacement replacement;

	/**
	 * contains the new constraint computed
	 */
	protected Constraint newConstraint;
	/**
	 * is the checker used in the replacement checking activity
	 */
	protected ReplacementChecker replacementChecker;

	/**
	 * is the model from which the replacement is obtained
	 */
	protected IBA model;

	/**
	 * contains the refinement obtained by the current model and the replacement
	 */
	protected IBA refinement;

	protected AcceptingType policy;

	/**
	 * creates a new replacement console
	 */
	public CHIAReplacementConsole() {
		this.policy = AcceptingType.BA;
	}

	public void loadConstraint(String constraintFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {

		if (this.chiaState.isPerformable(ConstraintReader.class)) {
			try {
				this.constraint = new ConstraintReader(new File(
						constraintFilePath)).perform();
				this.chiaState = chiaState.perform(ConstraintReader.class);

				logger.info("Constraint Loaded");
			} catch (CHIAException e) {
				logger.info(e.toString());
			}
		} else {
			logger.info("The constraint reading action cannot be performed into the state "
					+ this.chiaState.name());
		}
	}

	public void dispConstraint() throws Exception {

		if (this.chiaState.isPerformable(ConstraintToStringTrasformer.class)) {
			try {
				ConstraintToStringTrasformer action = new ConstraintToStringTrasformer(
						this.constraint);
				this.chiaState = chiaState
						.perform(ConstraintToStringTrasformer.class);
				logger.info(action.perform());
			} catch (CHIAException e) {
				logger.info(e.toString());
			}
		} else {
			this.printNotExecutableMessage(ConstraintToStringTrasformer.class);
		}
	}

	public void loadReplacement(String replacementFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {

		if (this.chiaState.isPerformable(ReplacementReader.class)) {
			try {
				ReplacementReader rr=new ReplacementReader(new File(
						replacementFilePath));
				this.replacement = rr.perform();
				this.model=rr.getModel();
				this.chiaState = chiaState.perform(ReplacementReader.class);
				logger.info("Replacement Loaded");

			} catch (Exception e) {
				logger.info(e.toString());
			}
		} else {
			PrintStream out = System.out;
			out.println("The replacement reading action cannot be performed into the state "
					+ this.chiaState.name());
		}
	}

	public void dispReplacement() throws Exception {

		if (this.chiaState.isPerformable(ReplacementToStringTransformer.class)) {
			try {
				ReplacementToStringTransformer action = new ReplacementToStringTransformer(
						this.replacement);
				this.chiaState = chiaState
						.perform(ReplacementToStringTransformer.class);
				logger.info(action.perform());
			} catch (CHIAException e) {
				logger.info(e.toString());
			}
		} else {
			this.printNotExecutableMessage(ReplacementToStringTransformer.class);
		}
	}

	public void saveRefinement(String filePath) {
		if (this.chiaState.isPerformable(RefinementGenerator.class)) {
			try {
				RefinementGenerator action = new RefinementGenerator(
						this.model, this.replacement);
				this.refinement = action.perform();

				new XMLWriter(new File(filePath),
						new IBAToElementTrasformer().transform(this.refinement)).perform();
				this.chiaState = chiaState.perform(RefinementGenerator.class);

			} catch (Exception e) {
				logger.info(e.toString());
			}
		} else {
			this.printNotExecutableMessage(ReplacementToStringTransformer.class);
		}
	}

	public void replacementChecking() {
		if (this.chiaState.isPerformable(ReplacementChecker.class)) {
			try {

				SubProperty subProperty = this.constraint
						.getSubProperty(this.replacement.getModelState());
				this.replacementChecker = new ReplacementChecker(
						this.replacement, subProperty, AcceptingPolicy.getAcceptingPolicy(
								this.policy, this.replacement.getAutomaton(),
								subProperty.getAutomaton()));

				ThreadMXBean thradBean = ManagementFactory.getThreadMXBean();
				long startTime = thradBean.getCurrentThreadCpuTime();
				SatisfactionValue result = this.replacementChecker.perform();
				long endTime = thradBean.getCurrentThreadCpuTime();
				logger.info("Verification result: " + result.toString());
				logger.info("Verification time: "
						+ Long.toString(TimeUnit.MILLISECONDS.convert(
								(endTime - startTime), TimeUnit.NANOSECONDS))
						+ " ms");
				if (result.equals(SatisfactionValue.NOTSATISFIED)) {
					logger.info("COUNTEREXAMPLE: "
							+ this.replacementChecker.getCouterexample());

				}
				if (!result.equals(SatisfactionValue.NOTSATISFIED)) {
					logger.info("Dimension of the intersection automaton (states+transitions): "
							+ (this.replacementChecker.getUpperIntersectionBA()
									.size() + this.replacementChecker
									.getLowerIntersectionBA().size()));
				}

				this.chiaState = chiaState.perform(ReplacementChecker.class);

			} catch (CHIAException e) {
				logger.info(e.toString());
			}
		} else {
			this.printNotExecutableMessage(ReplacementChecker.class);
		}
	}

	public void changePolicy(String policy) {
		try {
			this.policy = AcceptingType.valueOf(policy);
		} catch (Exception e) {
			System.out.println("Parameter: " + policy
					+ " not accepted the policy must be one of "
					+ AcceptingType.values().toString());
		}
	}

	private void printNotExecutableMessage(Class<?> action) {
		PrintStream out = System.out;
		out.println("The " + action.getName()
				+ " action cannot be performed into the state "
				+ this.chiaState.name());
	}

}
