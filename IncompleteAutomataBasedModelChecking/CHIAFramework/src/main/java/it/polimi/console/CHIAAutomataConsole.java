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
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.constraint.ConstraintWriter;
import it.polimi.model.ltltoba.LTLtoBATransformer;
import it.polimi.statemachine.states.CHIAAutomataState;

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
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import action.CHIAException;

import com.google.common.base.Preconditions;

/**
 * Is the Console associated with the automata mode. It specifies the set of
 * action that can be performed when the automata mode is selected
 * 
 * @author Claudio Menghi
 *
 */
public class CHIAAutomataConsole {

	private static final Logger logger = Logger
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
	private AcceptingType policy;

	/**
	 * contains the engine used to compute the constraint
	 */
	private ConstraintGenerator cg;

	public CHIAAutomataConsole(PrintStream out) {
		Preconditions.checkNotNull(out, "The output stream cannot be null");
		policy = AcceptingType.BA;
		chiaState = CHIAAutomataState.INIT;
		this.out = out;
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
	public void loadModel(String modelFilePath) {

		com.google.common.base.Preconditions.checkNotNull(modelFilePath,
				"The path of the model cannot be null");
		com.google.common.base.Preconditions.checkArgument(Files.exists(
				Paths.get(modelFilePath), LinkOption.NOFOLLOW_LINKS),
				"The path of the model cannot be null");

		try {
			this.chiaState = chiaState.perform(ModelReader.class);

			ModelReader action = new ModelReader(modelFilePath);
			this.model = action.perform();
			logger.info("Model readed");
			logger.info("N° states: "+this.model.getStates().size());
			logger.info("N° transitions:"+this.model.getTransitions().size());

		} catch (FileNotFoundException fileNotFound) {
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

	
	/**
	 * loads the property in form of automaton from the specified file path
	 * 
	 * @param claimFilePath
	 *            the path of the file from which the property must be loaded
	 * @throws FileNotFoundException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void loadProperty(String claimFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		try {

			this.chiaState = chiaState.perform(ClaimReader.class);
			ClaimReader action = new ClaimReader(claimFilePath);
			this.claim = action.perform();
			logger.info("Property loaded");
			logger.info("N° states: "+this.model.getStates().size());
			logger.info("N° transitions:"+this.model.getTransitions().size());

		} catch (CHIAException e) {
			logger.info(e.toString());
		}
	}

	/**
	 * loads the automaton that corresponds to the property of interest from the
	 * specified LTL formula
	 * 
	 * @param ltlProperty
	 *            is the LTL property to be converted into the corresponding
	 *            automaton
	 * @throws Exception
	 */
	public void loadLTLProperty(String ltlProperty) {
		try {
			this.chiaState = chiaState.perform(LTLtoBATransformer.class);
			LTLtoBATransformer action = new LTLtoBATransformer("!("
					+ ltlProperty + ")");
			this.claim = action.perform();
			System.out.println("LTL property loaded");

		} catch (UnsatisfiedLinkError e) {
			logger.info(e.getMessage());
			logger.info(e.toString());
			logger.info("The convertion of an LTL formula into the corresponding automaton is based on the LTL2BA4J library.");
			logger.info("The LTL2BA4J library uses the ltl2ba tool. ltl2ba is written in ANSI C");
			logger.info("The library must be available at the specified path");

		} catch (Exception e) {
			logger.info(e.toString());
			logger.info("The convertion of an LTL formula into the corresponding automaton is based on the LTL2BA4J library.");
			logger.info("The LTL2BA4J library uses the ltl2ba tool. ltl2ba is written in ANSI C");
			logger.info("If the compiled version of the library is not compatible with your operating system "
					+ "you must download the source of ltl2ba from http://www.sable.mcgill.ca/~ebodde/rv/ltl2ba4j/ltl2ba4j.tgz"
					+ " and recompile the source of ltl2ba");
		}catch(NoClassDefFoundError e){
			logger.info(e.toString());
			logger.info("The convertion of an LTL formula into the corresponding automaton is based on the LTL2BA4J library.");
			logger.info("The LTL2BA4J library uses the ltl2ba tool. ltl2ba is written in ANSI C");
			logger.info("If the compiled version of the library is not compatible with your operating system "
					+ "you must download the source of ltl2ba from http://www.sable.mcgill.ca/~ebodde/rv/ltl2ba4j/ltl2ba4j.tgz"
					+ " and recompile the source of ltl2ba");
		}
		
	}

	/**
	 * displays the model of the system
	 * 
	 * @throws ParserConfigurationException
	 * 
	 * @throws Exception
	 */
	public void dispModel() throws ParserConfigurationException, Exception {

		Preconditions
				.checkNotNull(model,
						"The model of the system must be loaded before being displayed");

		logger.info(new ElementToStringTransformer()
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

	public void changePolicy(String policy) {
		try {

			this.policy = AcceptingType.valueOf(policy);
		} catch (Exception e) {
			logger.info("Parameter: " + policy
					+ " not accepted the policy must be one of "
					+ AcceptingType.values().toString());
		}
	}

	public void check() {
		try {
			this.chiaState = chiaState.perform(Checker.class);
			ThreadMXBean thradBean = ManagementFactory.getThreadMXBean();
			long startTime = thradBean.getCurrentThreadCpuTime();
			checker = new Checker(model, claim,
					AcceptingPolicy.getAcceptingPolicy(policy, model, claim));
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
				logger.info("Counterexample:"
						+ this.checker.getCounterexample());

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
			
			logger.info("Constraint computed");
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
			logger.info("Constraint saved");
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
