package it.polimi;

import it.polimi.automata.io.out.IntersectionToStringTransformer;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.io.in.constraint.ConstraintReader;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.replacement.ReplacementToStringTransformer;
import it.polimi.replacementchecker.ReplacementChecker;
import it.polimi.statemachine.CHIAReplacementState;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import action.CHIAException;
import asg.cliche.Command;
import asg.cliche.Param;

/**
 * contains the console which is used for the replacement checking
 * 
 * @author Claudio Menghi
 *
 */
public class CHIAReplacementConsole {

	private static final Logger logger = LoggerFactory
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

	protected AcceptingPolicy policy;

	/**
	 * creates a new replacement console
	 */
	public CHIAReplacementConsole() {
		this.policy = AcceptingPolicy.getAcceptingPolicy(AcceptingType.KRIPKE);
	}

	@Command(name = "loadReplacement", abbrev = "lR", description = "It is used to load the replacement from an XML file. The XML file must mach the Replacement.xsd", header = "replacement loaded")
	public void loadReplacement(
			@Param(name = "replacementFilePath", description = "is the path of the file that contains the replacement to be considered") String replacementFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {

		if (this.chiaState.isPerformable(ReplacementReader.class)) {
			try {
				this.replacement = new ReplacementReader(replacementFilePath)
						.perform();
				this.chiaState = chiaState.perform(ReplacementReader.class);

			} catch (CHIAException e) {
				logger.info(e.toString());
			}
		} else {
			PrintStream out = System.out;
			out.println("The replacement reading action cannot be performed into the state "
					+ this.chiaState.name());
		}
	}

	@Command(name = "changePolicy", abbrev = "cp", description = "Is used to change the accepting policy.", header = "policy changed")
	public void changePolicy(
			@Param(name = "policy", description = "is the policy to be used KRIPKE or NORMAL") String policy) {
		try{
			this.policy=AcceptingPolicy.getAcceptingPolicy(AcceptingType.valueOf(policy));
		} catch(Exception e) {
				System.out.println("Parameter: " + policy + " not accepted the policy must be one of "+AcceptingType.values().toString());
		}
	}

	@Command(name = "loadConstraint", abbrev = "lC", description = "It is used to load the constraint from an XML file. The XML file must mach the Constraint.xsd", header = "constraint loaded")
	public void loadConstraint(
			@Param(name = "constraintFilePath", description = "is the path of the file that contains the constraint to be considered") String constraintFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {

		if (this.chiaState.isPerformable(ConstraintReader.class)) {
			try {
				this.constraint = new ConstraintReader(constraintFilePath)
						.perform();
				this.chiaState = chiaState.perform(ConstraintReader.class);

			} catch (CHIAException e) {
				logger.info(e.toString());
			}
		} else {
			PrintStream out = System.out;
			out.println("The constraint reading action cannot be performed into the state "
					+ this.chiaState.name());
		}
	}

	@Command(name = "replacementChecking", abbrev = "ck", description = "Is used to check the replacement against the constraint previously generated.", header = "Performing the replacement checking")
	public void replacementChecking() {
		if (this.chiaState.isPerformable(ReplacementChecker.class)) {
			try {

				this.replacementChecker = new ReplacementChecker(
						this.constraint.getSubProperty(this.replacement
								.getModelState()), this.replacement,
						this.policy);

				ThreadMXBean thradBean = ManagementFactory.getThreadMXBean();
				long startTime = thradBean.getCurrentThreadCpuTime();
				SatisfactionValue result = this.replacementChecker.perform();
				long endTime = thradBean.getCurrentThreadCpuTime();
				logger.info("Verification result: " + result.toString());
				logger.info("Verification time: "
						+ Long.toString(TimeUnit.MILLISECONDS.convert(
								(endTime - startTime), TimeUnit.NANOSECONDS))
						+ " ms");
				logger.info("Dimension of the intersection automaton (states+transitions): "
						+ (this.replacementChecker.getUpperIntersectionBA()
								.size() + this.replacementChecker
								.getLowerIntersectionBA().size()));
				this.chiaState = chiaState.perform(ReplacementChecker.class);

			} catch (CHIAException e) {
				logger.info(e.toString());
			}
		} else {
			this.printNotExecutableMessage(ReplacementChecker.class);
		}
	}

	@Command(name = "displayReplacement", abbrev = "dispR", description = "It is used to display the replacement into the console.", header = "Replacement displayed")
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

	@Command(name = "displayConstraint", abbrev = "dispCon", description = "It is used to display the constraint into the console.", header = "Constraint displayed")
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

	@Command(name = "displayUpperIntersection", abbrev = "dispUpInt", description = "It is used to display the upper intersection into the console.", header = "Intersection displayed")
	public void displayUpperIntersection() throws Exception {

		if (this.chiaState.isPerformable(IntersectionToStringTransformer.class)) {
			try {
				IntersectionToStringTransformer action = new IntersectionToStringTransformer(
						this.replacementChecker.getUpperIntersectionBA());
				this.chiaState = chiaState
						.perform(IntersectionToStringTransformer.class);
				logger.info(action.perform());
			} catch (CHIAException e) {
				logger.info(e.toString());
			}
		} else {
			this.printNotExecutableMessage(IntersectionToStringTransformer.class);
		}
	}

	@Command(name = "displayLowerIntersection", abbrev = "dispLwInt", description = "It is used to display the lower intersection into the console.", header = "Intersection displayed")
	public void displayLowerIntersection() throws Exception {

		if (this.chiaState.isPerformable(IntersectionToStringTransformer.class)) {
			try {
				IntersectionToStringTransformer action = new IntersectionToStringTransformer(
						this.replacementChecker.getLowerIntersectionBA());
				logger.info(action.perform());
				this.chiaState = chiaState
						.perform(IntersectionToStringTransformer.class);
			} catch (CHIAException e) {
				logger.info(e.toString());
			}
		} else {
			this.printNotExecutableMessage(IntersectionToStringTransformer.class);
		}
	}

	@Command(name = "exit", abbrev = "exit", description = "Returns to the CHIA main console", header = "CHIA Automata console exit")
	public void exit() {
	}

	private void printNotExecutableMessage(Class<?> action) {
		PrintStream out = System.out;
		out.println("The " + action.getName()
				+ " action cannot be performed into the state "
				+ this.chiaState.name());
	}
}
