package it.polimi.console;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.automata.io.out.ClaimToStringTrasformer;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.automata.io.out.IBAToElementTrasformer;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.constraint.ConstraintWriter;
import it.polimi.contraintcomputation.ConstraintGenerator;
import it.polimi.model.ltltoba.ClaimLTLReader;
import it.polimi.model.ltltoba.LTLtoBATransformer;
import it.polimi.statemachine.CHIAAutomataState;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import action.CHIAException;
import asg.cliche.Command;
import asg.cliche.Param;

import com.google.common.base.Preconditions;

/**
 * Is the Console associated with the automata mode. It specifies the set of
 * action that can be performed when the automata mode is selected
 * 
 * @author Claudio Menghi
 *
 */
public class CHIAAutomataConsole {

	private static final Logger logger = LoggerFactory
			.getLogger(CHIAAutomataConsole.class);

	/**
	 * contains the model of the system to be considered. Null if no model is
	 * loaded
	 */
	protected IBA model;
	
	private final PrintStream out;

	/**
	 * contains the claim to be considered. Null if no claim is loaded
	 */
	protected BA claim;

	/**
	 * contains the model checker to be used in the model checking activity
	 */
	protected Checker checker;

	/**
	 * is the state of the application. The state changes in response to user
	 * inputs
	 */
	private CHIAAutomataState chiaState;

	/**
	 * contains the constraint associated with the specified model and claim if
	 * the model possibly satisfy the claim
	 */
	protected Constraint constraint;

	/**
	 * contains the accepting policy which specifies how the accepting states of
	 * the intersection automata are computed
	 */
	private AcceptingPolicy policy;

	/**
	 * contains the engine used to compute the constraint
	 */
	private ConstraintGenerator cg;

	public CHIAAutomataConsole(PrintStream out) {
		Preconditions.checkNotNull(out, "The output stream cannot be null");
		policy = AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA);
		chiaState = CHIAAutomataState.INIT;
		this.out=out;
	}

	/**
	 * loads the model from the specified path
	 * 
	 * @param modelFilePath
	 *            is the path of the file that contains the model
	 * 
	 * @throws NullPointerException
	 *             if the path of the file is null
	 * @throws IllegalArgumentException
	 *             if the file does not exist
	 * @throws Exception
	 */
	public void loadModel(String modelFilePath){

		com.google.common.base.Preconditions.checkNotNull(modelFilePath,
				"The path of the model cannot be null");
		com.google.common.base.Preconditions.checkArgument(Files.exists(
				Paths.get(modelFilePath), LinkOption.NOFOLLOW_LINKS),
				"The path of the model cannot be null");

		try {
			this.chiaState = chiaState.perform(ModelReader.class);

			ModelReader action = new ModelReader(modelFilePath);
			this.model = action.perform();
			out.println("Model readed");

		} catch (FileNotFoundException fileNotFound){
			logger.info(fileNotFound.toString());
			out.println(fileNotFound.getMessage());
		} catch (CHIAException e) {
			logger.info(ExceptionUtils.getStackTrace(e));
			out.println(e.getMessage());
		} catch (SAXException e) {
			logger.info(ExceptionUtils.getStackTrace(e));
			out.println(e.getMessage());
		} catch (IOException e) {
			logger.info(ExceptionUtils.getStackTrace(e));
			out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			logger.info(ExceptionUtils.getStackTrace(e));
			out.println(e.getMessage());
		}
	}

	public void loadProperty(String claimFilePath)
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

	@Command(name = "loadLTLProperty", abbrev = "lpLTL", description = "It is used to load the property from an LTL formula", header = "loading the LTL property")
	public void loadLTLProperty(
			@Param(name = "-f", description = "is the flag that specify that the formula must be loaded from file") String flag,
			@Param(name = "file", description = "is the path of the file from which the formula must be loaded") String file)
			throws Exception {
		try {
			this.chiaState = chiaState.perform(ClaimLTLReader.class);

			ClaimLTLReader action = new ClaimLTLReader(file);
			this.claim = action.perform();
			System.out.println("LTL property loaded");
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "loadLTLProperty", abbrev = "lpLTL", description = "It is used to load the property from an LTL formula", header = "loading the LTL property")
	public void loadLTLProperty(
			@Param(name = "LTLFormula", description = "is the LTL formula that represents the property") String ltlProperty)
			throws Exception {
		try {
			this.chiaState = chiaState.perform(LTLtoBATransformer.class);
			LTLtoBATransformer action = new LTLtoBATransformer("!("
					+ ltlProperty + ")");
			this.claim = action.perform();
			System.out.println("LTL property loaded");
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	/**
	 * displays the model of the system
	 * 
	 * @throws ParserConfigurationException 
	 * 
	 * @throws Exception
	 */
	public void dispModel() throws ParserConfigurationException, Exception   {

		Preconditions.checkNotNull(model, "The model of the system must be loaded before being displayed");
		
		out.println(new ElementToStringTransformer()
				.transform(new IBAToElementTrasformer().transform(this.model)));
	}

	public void dispClaim() throws ParserConfigurationException, Exception {
		try {

			this.chiaState = chiaState.perform(ClaimToStringTrasformer.class);
			ClaimToStringTrasformer action = new ClaimToStringTrasformer(
					this.claim);
			logger.info(action.perform());
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	@Command(name = "changePolicy", abbrev = "cp", description = "Is used to change the accepting policy.", header = "policy changed")
	public void changePolicy(
			@Param(name = "policy", description = "is the policy to be used KRIPKE or BA") String policy) {
		try {
			this.policy = AcceptingPolicy.getAcceptingPolicy(AcceptingType
					.valueOf(policy));
		} catch (Exception e) {
			System.out.println("Parameter: " + policy
					+ " not accepted the policy must be one of "
					+ AcceptingType.values().toString());
		}
	}

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
					+ Long.toString(TimeUnit.MILLISECONDS.convert(
							(endTime - startTime), TimeUnit.NANOSECONDS))
					+ " ms");
			logger.info("Dimension of the intersection automaton (states+transitions): "
					+ this.checker.getIntersectionAutomataSize());
			if (result.equals(SatisfactionValue.NOTSATISFIED)) {
				logger.info("State counterexample:"
						+ this.checker.getStateCounterexample());
				logger.info("Transition counterexample:"
						+ this.checker.getTransitionCounterexample());

			}

		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	public void computeConstraint() throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException {
		try {
			this.chiaState = chiaState.perform(ConstraintGenerator.class);
			cg = new ConstraintGenerator(this.checker);
			this.constraint = cg.perform();
			cg.coloring();
			cg.computePortReachability();
			cg.computeIndispensable();
			// this.constraint = this.chia.generateConstraint();
		} catch (CHIAException e) {
			logger.info(e.toString());
		}

	}

	public void saveConstraint(String constraintFilePath) throws Exception {
		try {
			this.chiaState = chiaState.perform(ConstraintWriter.class);

			ConstraintWriter constraintWriter = new ConstraintWriter(
					this.constraint, constraintFilePath);
			constraintWriter.perform();
		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

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

	public void exit() {
	}

	

}
