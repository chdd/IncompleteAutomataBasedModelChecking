package it.polimi.console;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.automata.io.out.ClaimToStringTrasformer;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.automata.io.out.IBAToElementTrasformer;
import it.polimi.automata.io.out.IntersectionWriter;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.constraint.ConstraintWriter;
import it.polimi.contraintcomputation.ConstraintGenerator;
//import it.polimi.model.ltltoba.LTLReader;
//import it.polimi.model.ltltoba.LTLtoBATransformer;
import it.polimi.model.ltltoba.ClaimLTLReader;
import it.polimi.model.ltltoba.LTLtoBATransformer;
import it.polimi.statemachine.CHIAAutomataState;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import action.CHIAAction;
import action.CHIAException;
import asg.cliche.Command;
import asg.cliche.Param;

public class CHIAAutomataConsole {

	private static final Logger logger = LoggerFactory
			.getLogger(CHIAAutomataConsole.class);

	protected IBA model;
	protected BA claim;
	protected Checker checker;
	private CHIAAutomataState chiaState;
	protected Constraint constraint;

	AcceptingPolicy policy;

	private ConstraintGenerator cg;

	public CHIAAutomataConsole() {
		policy =AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA);
		chiaState = CHIAAutomataState.INIT;
	}

	@Command(name = "changePolicy", abbrev = "cp", description = "Is used to change the accepting policy.", header = "policy changed")
	public void changePolicy(
			@Param(name = "policy", description = "is the policy to be used KRIPKE or BA") String policy) {
		try{
			this.policy=AcceptingPolicy.getAcceptingPolicy(AcceptingType.valueOf(policy));
		} catch(Exception e) {
				System.out.println("Parameter: " + policy + " not accepted the policy must be one of "+AcceptingType.values().toString());
		}
	}

	@Command(name = "loadModel", abbrev = "lm", description = "Is used to load the model from an XML file. The XML file must mach the IBA.xsd.", header = "model loaded")
	public void loadModel(
			@Param(name = "modelFilePath", description = "is the path of the file that contains the model to be checked") String modelFilePath)
			throws Exception {
		try {
			this.chiaState = chiaState.perform(ModelReader.class);

			ModelReader action = new ModelReader(modelFilePath);
			this.model = action.perform();

		} catch (CHIAException e) {
			logger.info(e.toString());
		}

	}

	@Command(name = "loadClaim", abbrev = "lc", description = "Is used to load the claim from an XML file. The XML file must mach the BA.xsd.", header = "claim loaded")
	public void loadClaim(
			@Param(name = "claimFilePath", description = "is the path of the file that contains the claim to be checked") String claimFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		try {

			this.chiaState = chiaState.perform(ClaimReader.class);
			ClaimReader action = new ClaimReader(claimFilePath);
			this.claim = action.perform();

		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "loadLTLClaim", abbrev = "lcLTL", description = "It is used to load the property from an LTL formula", header = "load LTL claim")
	public void loadProperty(
			@Param(name = "LTLFormula", description = "is the LTL formula that represents the claim") String ltlProperty) {
		try {
			this.chiaState = chiaState.perform(LTLtoBATransformer.class);
			LTLtoBATransformer action = new LTLtoBATransformer("!("
					+ ltlProperty + ")");
			this.claim = action.perform();
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "loadLTLClaim", abbrev = "lcLTL", description = "It is used to load the property from an LTL formula", header = "load LTL claim")
	public void loadProperty(
			@Param(name = "-f", description = "is the flag that specify that the formula must be loaded from file") String flag,
			@Param(name = "file", description = "is the path of the file from which the formula must be loaded") String file)
			throws IOException {
		try {
			this.chiaState = chiaState.perform(ClaimLTLReader.class);

			ClaimLTLReader action = new ClaimLTLReader(file);
			this.claim = action.perform();
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "check", abbrev = "ck", description = "Is used to check the model against the specified claim. Before running the model checking procedure it is necessary to load the model and the claim to be considered", header = "Checking procedure ended")
	public void check() {
		try {
			this.chiaState = chiaState.perform(Checker.class);
			ThreadMXBean thradBean = ManagementFactory.getThreadMXBean();
			long startTime = thradBean.getCurrentThreadCpuTime();
			checker = new Checker(model, claim, policy);
			SatisfactionValue result = checker.perform();
			long endTime = thradBean.getCurrentThreadCpuTime();
			logger.info("Verification result: " + result.toString());
			logger.info("Verification time: "
					+ Long.toString(TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS)) + " ms");
			logger.info("Dimension of the intersection automaton (states+transitions): "
					+ this.checker.getIntersectionAutomataSize());
			if(result.equals(SatisfactionValue.NOTSATISFIED)){
				logger.info("State counterexample:"+this.checker.getStateCounterexample());
				logger.info("Transition counterexample:"+this.checker.getTransitionCounterexample());
				
			}
			

		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "computeConstraint", abbrev = "cc", description = "Is used to compute the constraint corresponding to the model and the specified claim. ")
	public void computeConstraint() throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException {
		try {
			this.chiaState = chiaState.perform(ConstraintGenerator.class);
			cg = new ConstraintGenerator(this.checker);
			this.constraint = cg.perform();
			// this.constraint = this.chia.generateConstraint();
		} catch (CHIAException e) {
			logger.info(e.toString());
		}

	}

	@Command(name = "computeConstraint", abbrev = "cc", description = "Is used to compute the constraint corresponding to the model and the specified claim. ")
	public void computeConstraint(
			@Param(name = "-p", description = "if the -p flag is specified the port reachability relation is not computed") String p)
			throws Exception {
		try {

			cg = new ConstraintGenerator(this.checker);
			this.constraint = this.performAction(cg);
			cg.coloring();
		} catch (CHIAException e) {
			logger.info(e.toString());
		}

	}

	@Command(name = "computeConstraint", abbrev = "cc", description = "Is used to compute the constraint corresponding to the model and the specified claim. ")
	public void computeConstraint(
			@Param(name = "-p", description = "if the -p flag is specified the port reachability relation is not computed") String p,
			@Param(name = "-r", description = "if the -r flag is specified the port reachability relation is not computed") String r)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		try {
			this.chiaState = chiaState.perform(ConstraintGenerator.class);

			logger.info("Subproperty computation started");
			cg = new ConstraintGenerator(this.checker);
			this.constraint = cg.perform();
			cg.coloring();
			cg.computePortReachability();
			cg.computeIndispensable();
			logger.info("Subproperty computation ended");

			// this.constraint = this.chia.generateConstraint();
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "saveIntersection", abbrev = "si", description = "It is used to save the intersection automaton", header = "Intersection automaton saved")
	public void saveIntersection(
			@Param(name = "intersectionFilePath", description = "The location where the intersection automaton must be saved") String intersectionFilePath) {
		try {
			this.chiaState = chiaState.perform(IntersectionWriter.class);

			IntersectionBA intersectionAutomaton = this.checker
					.getUpperIntersectionBuilder().getIntersectionAutomaton();
			IntersectionWriter intersectionWriter = new IntersectionWriter(
					new File(intersectionFilePath), intersectionAutomaton);

			intersectionWriter.perform();
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "saveConstraint", abbrev = "sc", description = "It is used to save the constraint in an XML file.", header = "constraint saved")
	public void saveConstraint(
			@Param(name = "constraintFilePath", description = "is the path of the file where the constraint must be saved") String constraintFilePath)
			throws Exception {
		try {
			this.chiaState = chiaState.perform(ConstraintWriter.class);

			ConstraintWriter constraintWriter = new ConstraintWriter(
					this.constraint, constraintFilePath);
			constraintWriter.perform();
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "displayModel", abbrev = "dispM", description = "It is used to display the model into the console.", header = "Model displayed")
	public void dispModel() throws Exception {

		System.out.println(new ElementToStringTransformer()
				.transform(new IBAToElementTrasformer().transform(this.model)));
	}

	@Command(name = "displayClaim", abbrev = "dispC", description = "It is used to display the claim into the console.", header = "Claim displayed")
	public void dispClaim() throws ParserConfigurationException, Exception {
		try {

			this.chiaState = chiaState.perform(ClaimToStringTrasformer.class);
			ClaimToStringTrasformer action = new ClaimToStringTrasformer(this.claim);
			logger.info(action.perform());
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "displayConstraint", abbrev = "dispCon", description = "It is used to display the contraint into the console.", header = "Constraint displayed")
	public void dispConstraint() throws ParserConfigurationException, Exception {
		try {
			this.chiaState = chiaState
					.perform(ConstraintToStringTrasformer.class);
			ConstraintToStringTrasformer action = new ConstraintToStringTrasformer(
					this.constraint);
			logger.info(action.perform());
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "exit", abbrev = "exit", description = "Returns to the CHIA main console", header = "CHIA Automata console exit")
	public void exit() {
	}

	private <O> O performAction(CHIAAction<O> chiaAction) throws Exception {
		if (this.chiaState.isPerformable(ConstraintGenerator.class)) {
			logger.info("Subproperty computation started");
			O result = chiaAction.perform();
			logger.info("Subproperty computation ended");

			this.chiaState = chiaState.perform(ConstraintGenerator.class);
			return result;
		} else {
			throw new CHIAException("The action " + chiaAction.getName()
					+ " cannot be performed into the state " + chiaState.name());
		}

	}

}
