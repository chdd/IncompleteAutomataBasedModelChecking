package it.polimi;

import it.polimi.automata.io.out.IntersectionToStringTransformer;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.NormalAcceptingPolicy;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.io.in.constraint.ConstraintReader;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.replacement.ReplacementToStringTransformer;
import it.polimi.replacementchecker.ReplacementChecker;
import it.polimi.statemachine.CHIAException;
import it.polimi.statemachine.CHIAReplacementState;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import asg.cliche.Command;
import asg.cliche.Param;

/**
 * contains the console which is used for the replacement checking
 * 
 * @author Claudio1
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

	protected AcceptingPolicy policy = new KripkeAcceptingPolicy();

	@Command(name = "loadReplacement", abbrev = "lR", description = "It is used to load the replacement from an XML file. The XML file must mach the Replacement.xsd", header = "replacement loaded")
	public void loadReplacement(
			@Param(name = "replacementFilePath", description = "is the path of the file that contains the replacement to be considered") String replacementFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		try {

			this.chiaState = chiaState.perform(ReplacementReader.class);
			this.replacement = new ReplacementReader(replacementFilePath)
					.read();
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "changePolicy", abbrev = "cp", description = "Is used to change the accepting policy.", header = "policy changed")
	public void changePolicy(
			@Param(name = "policy", description = "is the policy to be used KRIPKE or NORMAL") String policy) {
		if (policy.equals("KRIPKE")) {
			this.policy = new KripkeAcceptingPolicy();
		} else {
			if (policy.equals("NORMAL")) {
				this.policy = new NormalAcceptingPolicy();
			} else {
				System.out.println("Parameter: " + policy + " not accepted");
			}
		}
	}

	@Command(name = "loadConstraint", abbrev = "lC", description = "It is used to load the constraint from an XML file. The XML file must mach the Constraint.xsd", header = "constraint loaded")
	public void loadConstraint(
			@Param(name = "constraintFilePath", description = "is the path of the file that contains the constraint to be considered") String constraintFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {

		try {
			this.chiaState = chiaState.perform(ConstraintReader.class);
			this.constraint = new ConstraintReader(constraintFilePath).read();

		} catch (CHIAException e) {
			logger.info(e.toString());
		}

	}

	@Command(name = "replacementChecking", abbrev = "ck", description = "Is used to check the replacement against the constraint previously generated.", header = "Performing the replacement checking")
	public void replacementChecking() {

		try {
			this.chiaState = chiaState
					.perform(ReplacementChecker.class);
			
			this.replacementChecker = new ReplacementChecker(this.constraint.getSubProperty(this.replacement.getModelState()), this.replacement, this.policy);
			long startTime = System.currentTimeMillis();
			SatisfactionValue result=this.replacementChecker.check();
			long endTime = System.currentTimeMillis();
			logger.info("Verification result: "+result.toString());
			logger.info("Verification time: "+Long.toString(endTime-startTime)+" ms");
			logger.info("Dimension of the intersection automaton (states+transitions): "+(this.replacementChecker.getUpperIntersectionBA().size()+this.replacementChecker.getLowerIntersectionBA().size()));
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "displayReplacement", abbrev = "dispR", description = "It is used to display the replacement into the console.", header = "Replacement displayed")
	public void dispReplacement() throws Exception {

		try {
			this.chiaState = chiaState
					.perform(ReplacementToStringTransformer.class);
			ReplacementToStringTransformer action = new ReplacementToStringTransformer();
			logger.info(action.toString(this.replacement));
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "displayConstraint", abbrev = "dispCon", description = "It is used to display the constraint into the console.", header = "Constraint displayed")
	public void dispConstraint() throws Exception {

		try {
			this.chiaState = chiaState
					.perform(ConstraintToStringTrasformer.class);
			ConstraintToStringTrasformer action = new ConstraintToStringTrasformer();
			logger.info(action.toString(this.constraint));
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}
	@Command(name = "displayIntersection", abbrev = "dispInt", description = "It is used to display the intersection into the console.", header = "Intersection displayed")
	public void dispIntersection() throws Exception {

		try {
			this.chiaState = chiaState
					.perform(IntersectionToStringTransformer.class);
			IntersectionToStringTransformer action = new IntersectionToStringTransformer();
			logger.info(action.toString(this.replacementChecker.getUpperIntersectionBA()));
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "exit", abbrev = "exit", description = "Returns to the CHIA main console", header = "CHIA Automata console exit")
	public void exit() {
	}
}
