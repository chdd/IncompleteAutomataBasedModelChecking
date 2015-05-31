package it.polimi;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.in.BAReader;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.automata.io.out.BAToStringTrasformer;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.automata.io.out.IBAToElementTrasformer;
import it.polimi.automata.io.out.IntersectionWriter;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.NormalAcceptingPolicy;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.constraint.ConstraintWriter;
import it.polimi.contraintcomputation.ConstraintGenerator;
//import it.polimi.model.ltltoba.LTLReader;
//import it.polimi.model.ltltoba.LTLtoBATransformer;
import it.polimi.model.ltltoba.LTLReader;
import it.polimi.model.ltltoba.LTLtoBATransformer;
import it.polimi.statemachine.CHIAAutomataState;
import it.polimi.statemachine.CHIAException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

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
		policy = new KripkeAcceptingPolicy();
		chiaState = CHIAAutomataState.INIT;
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

	@Command(name = "loadModel", abbrev = "lm", description = "Is used to load the model from an XML file. The XML file must mach the IBA.xsd.", header = "model loaded")
	public void loadModel(
			@Param(name = "modelFilePath", description = "is the path of the file that contains the model to be checked") String modelFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		try {
			this.chiaState = chiaState.perform(IBAReader.class);

			IBAReader action = new IBAReader(modelFilePath);
			this.model = action.read();

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

			this.chiaState = chiaState.perform(BAReader.class);
			BAReader action = new BAReader(claimFilePath);
			this.claim = action.read();

		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "loadLTLClaim", abbrev = "lcLTL", description = "It is used to load the property from an LTL formula", header = "load LTL claim")
	public void loadProperty(
			@Param(name = "LTLFormula", description = "is the LTL formula that represents the claim") String ltlProperty) {
		try {
			this.chiaState = chiaState.perform(LTLtoBATransformer.class);
			LTLtoBATransformer action = new LTLtoBATransformer();
			this.claim = action.transform("!(" + ltlProperty + ")");
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
			this.chiaState = chiaState.perform(LTLReader.class);

			LTLReader action = new LTLReader(file);
			this.claim = action.loadLTLFromFile();
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "check", abbrev = "ck", description = "Is used to check the model against the specified claim. Before running the model checking procedure it is necessary to load the model and the claim to be considered", header = "Checking procedure ended")
	public void check() {
		try {
			this.chiaState = chiaState.perform(Checker.class);

			long startTime = System.currentTimeMillis();
			checker = new Checker(model, claim, policy);
			SatisfactionValue result=checker.check();
			long endTime = System.currentTimeMillis();
			logger.info("Verification result: "+result.toString());
			logger.info("Verification time: "+Long.toString(endTime-startTime)+" ms");
			logger.info("Dimension of the intersection automaton (states+transitions): "+this.checker.getIntersectionBuilder().getIntersectionAutomaton().size());
			;

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
			this.constraint = cg.generateConstraint();
			// this.constraint = this.chia.generateConstraint();
		} catch (CHIAException e) {
			logger.info(e.toString());
		}

	}

	@Command(name = "computeConstraint", abbrev = "cc", description = "Is used to compute the constraint corresponding to the model and the specified claim. ")
	public void computeConstraint(
			@Param(name = "-p", description = "if the -p flag is specified the port reachability relation is not computed") String p)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		try {
			this.chiaState = chiaState.perform(ConstraintGenerator.class);

			logger.info("Subproperty computation started");
			cg = new ConstraintGenerator(this.checker);
			this.constraint = cg.generateConstraint();
			cg.coloring();
			logger.info("Subproperty computation ended");

			// this.constraint = this.chia.generateConstraint();
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
			this.constraint = cg.generateConstraint();
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

			IntersectionWriter intersectionWriter = new IntersectionWriter(
					new File(intersectionFilePath));

			IntersectionBA intersectionAutomaton = this.checker
					.getIntersectionBuilder().getIntersectionAutomaton();
			intersectionWriter.write(intersectionAutomaton);
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
			constraintWriter.write();
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

			this.chiaState = chiaState.perform(BAToStringTrasformer.class);
			BAToStringTrasformer action = new BAToStringTrasformer();
			logger.info(action.toString(this.claim));
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "displayConstraint", abbrev = "dispCon", description = "It is used to display the contraint into the console.", header = "Constraint displayed")
	public void dispConstraint() throws ParserConfigurationException, Exception {
		try {
			this.chiaState = chiaState
					.perform(ConstraintToStringTrasformer.class);
			ConstraintToStringTrasformer action = new ConstraintToStringTrasformer();
			logger.info(action.toString(this.constraint));
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "exit", abbrev = "exit", description = "Returns to the CHIA main console", header = "CHIA Automata console exit")
	public void exit() {
	}
	
}
