package it.polimi.chia.scalability;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.out.BAToElementTrasformer;
import it.polimi.automata.io.out.IBAToElementTrasformer;
import it.polimi.automata.io.out.XMLWriter;
import it.polimi.automata.state.StateFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.chia.scalability.configuration.Configuration;
import it.polimi.chia.scalability.configuration.RandomConfigurationGenerator;
import it.polimi.chia.scalability.results.Record;
import it.polimi.chia.scalability.results.ResultWriter;
import it.polimi.chia.scalability.results.Statistics;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.io.out.constraint.ConstraintToElementTransformer;
import it.polimi.constraints.io.out.replacement.ReplacementToElementTransformer;
import it.polimi.constraints.utils.Injector;
import it.polimi.contraintcomputation.ConstraintGenerator;
import it.polimi.model.ltltoba.LTLtoBATransformer;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class ScalabilityTest {

	private static final Logger logger = LoggerFactory
			.getLogger(ScalabilityTest.class);

	private final static String resultsFile = "results.txt";
	private final static String resultsFilePossibly = "resultsPossibly.txt";
	private final static String errors = "errors.txt";

	private final static int N_TESTS = 20;

	private final static AcceptingPolicy acceptingPolicy = AcceptingPolicy
			.getAcceptingPolicy(AcceptingType.BA);

	public static void main(String[] args) throws Exception {

		String testDirectory = args[0];
		System.out
				.println("--------------------------- STARTING THE TEST: ------------------------");

		Stopwatch timer = Stopwatch.createUnstarted();

		// BA claim = generateRandomClaim(new RandomConfiguration(3, 2, 0.5, 0,
		// 0));

		
		for (int testNumber = 1; testNumber <= N_TESTS; testNumber++) {
			int claimNum = 1;
			File dir = new File(testDirectory + "Test" + testNumber);
			dir.mkdir();
			for (BA claim : ScalabilityTest.getClaimToBeConsidered()) {
				File claimdir = new File(testDirectory + "Test" + testNumber
						+ "/Claim" + claimNum);
				claimdir.mkdir();
				claimNum++;
			}
		}
		
		ScalabilityTest.test(testDirectory, timer);

	}

	private static List<BA> getClaimToBeConsidered() {

		List<BA> claims = new ArrayList<BA>();
		LTLtoBATransformer action = new LTLtoBATransformer("!((a)U(b))");
		BA claim = action.perform();
		claims.add(claim);

		action = new LTLtoBATransformer("!([](a->(<>b)))");
		claim = action.perform();
		claims.add(claim);

		action = new LTLtoBATransformer("!(<>((a)U(b)))");
		claim = action.perform();
		claims.add(claim);

		return claims;

	}

	private static void test(String testDirectory,  Stopwatch timer) throws Exception {

		RandomConfigurationGenerator randomConfigurationGenerator = new RandomConfigurationGenerator(
				ScalabilityTest.getClaimToBeConsidered());

		Statistics stat = new Statistics();

		long initTime = System.currentTimeMillis();
		while (randomConfigurationGenerator.hasNext()) {

			
			Record record;

			Configuration configuration = randomConfigurationGenerator.next();

			ResultWriter resultWriter = new ResultWriter(
					testDirectory + "Test" + configuration.getTestNumber() + "/Claim"+ configuration.getClaimNumber() + "/" + resultsFile);
			ResultWriter resultPossiblyWriter = new ResultWriter(
					testDirectory + "Test" + configuration.getTestNumber() + "/Claim" + configuration.getClaimNumber() + "/"
							+ resultsFilePossibly);
			FileWriter errorsWriter = new FileWriter(new File(testDirectory
					+ "Test" + configuration.getTestNumber() + "/Claim" + configuration.getClaimNumber() + "/" + errors));

			File dir = new File(testDirectory + "Test" + configuration.getTestNumber() + "/Claim"
					+ configuration.getClaimNumber() + "/Experiment" + configuration.getConfigurationId());
			dir.mkdir();

			if (configuration.getConfigurationId() % 100 == 0) {
				logger.info("--------------------------- CONFIGURATION: " + configuration.getConfigurationId()
						+ "------------------------");
				logger.info(randomConfigurationGenerator.toString());
				logger.info(stat.toString());
				errorsWriter.close();
				errorsWriter = new FileWriter(testDirectory + "Test"
						+ configuration.getTestNumber() + "/Claim" + configuration.getClaimNumber() + "/" + errors);

			}
			
			// BA claim = generateRandomClaim(randomConfiguration);

			//BAToElementTrasformer claimToElementTransformer = new BAToElementTrasformer();

			//XMLWriter claimWriter = new XMLWriter(new File(testDirectory
			//		+ "Test" + configuration.getTestNumber() + "/Claim" + configuration.getClaimNumber() + "/"
			//		+ "/Experiment" + configuration.getConfigurationId() + "/claim.xml"),
			//		claimToElementTransformer.transform(configuration.getCurrentClaim()));
			//claimWriter.perform();

			BARandomGenerator modelBAgenerator = new BARandomGenerator(
					configuration.getPropositions(), new StateFactory(),
					configuration.getTransitionDensity(),
					configuration.getAcceptingDensity(),
					configuration.getnStates(), new Random());

			BA modelBA = modelBAgenerator.perform();

			IBARandomGenerator ibaGenerator = new IBARandomGenerator(modelBA,
					new StateFactory(), configuration.getTransparentDensity(),
					configuration.getReplacementDensity());

			IBA model = ibaGenerator.perform();

			//IBAToElementTrasformer modelToElementTransformer = new IBAToElementTrasformer();

			//XMLWriter writer = new XMLWriter(new File(testDirectory + "Test"
			//		+ configuration.getTestNumber() + "/Claim" + configuration.getClaimNumber() + "/" + "/Experiment"
			//		+ configuration.getConfigurationId() + "/model.xml"),
			//		modelToElementTransformer.transform(model));
			//writer.perform();

			// check whether the model possibly satisfies the claim
			Checker checker = new Checker(model, configuration.getCurrentClaim(), acceptingPolicy);
			SatisfactionValue value = checker.perform();

			// logger.info("Random configuration: "+configuration);
			// logger.info("Verification of the IBA vs the BA: "+value.toString());
			// logger.info("The property is: "+value.toString());

			if (value == SatisfactionValue.POSSIBLYSATISFIED) {
				stat.incNumPossibly();
				// compute the constraint
				Constraint constraint = computeConstraint(configuration.getCurrentClaim(), model, checker);

				//ConstraintToElementTransformer constraintTransformer = new ConstraintToElementTransformer();
				//XMLWriter constraintWriter = new XMLWriter(new File(
				//		testDirectory + "Test" + configuration.getTestNumber() + "/Claim"
				//				+ configuration.getClaimNumber() + "/" + "/Experiment" + configuration.getConfigurationId()
				//				+ "/constraint.xml"),
				//		constraintTransformer.transform(constraint));
				//constraintWriter.perform();

				// choose a random replacement
				List<Replacement> replacements = new ArrayList<Replacement>(
						ibaGenerator.getTransparentStateReplacementMap()
								.values());
				Collections.shuffle(replacements);

				Replacement replacement = replacements.iterator().next();

				// VERIFICATION OF THE REFINEMENT
				IBA refinedModel = new Injector(model, replacement).perform();
				Checker refinementChecker = new Checker(refinedModel, configuration.getCurrentClaim(),
						acceptingPolicy);

				//IBAToElementTrasformer refinementTransformer = new IBAToElementTrasformer();
				//XMLWriter refinementWriter = new XMLWriter(new File(
				//		testDirectory + "Test" + configuration.getTestNumber() + "/Claim"
				///				+ configuration.getClaimNumber() + "/" + "/Experiment" + configuration.getConfigurationId()
				//				+ "/refinement.xml"),
				//		refinementTransformer.transform(refinedModel));
				//refinementWriter.perform();

				timer.reset();
				timer.start();
				SatisfactionValue refinementSatisfactionvalue = refinementChecker
						.perform();
				timer.stop();
				// logger.info("REFINEMENT VERIFICATION PERFORMED IN: "+timer.elapsed(TimeUnit.NANOSECONDS)+" ms ");
				// logger.info("REFINEMENT VERIFICATION STATES OF THE INTERSECTION AUTOMATON: "+refinementChecker.getIntersectionAutomataSize());
				long refinementVerificationTime = timer
						.elapsed(TimeUnit.NANOSECONDS);

				// VERIFICATION OF THE REPLACEMENT
				ReplacementChecker replacementChecker = new ReplacementChecker(
						constraint.getSubProperty(replacement.getModelState()),
						replacement, acceptingPolicy);

				//ReplacementToElementTransformer replacementTransformer = new ReplacementToElementTransformer();
				//XMLWriter replacementWriter = new XMLWriter(new File(
				//		testDirectory + "Test" + configuration.getTestNumber() + "/Claim"
				//				+ configuration.getClaimNumber() + "/" + "/Experiment" + configuration.getConfigurationId()
				//				+ "/replacement.xml"),
				//		replacementTransformer.transform(replacement));
				//replacementWriter.perform();

				timer.reset();
				timer.start();
				SatisfactionValue replacementSatisfactionvalue = replacementChecker
						.perform();
				timer.stop();
				// logger.info("REPLACEMENT VERIFICATION PERFORMED IN: "+timer.elapsed(TimeUnit.NANOSECONDS)+" ms ");
				// logger.info("REPLACEMENT VERIFICATION STATES OF THE INTERSECTION AUTOMATON: "+replacementChecker.getIntersectionAutomataSize());

				long replacementVerificationTime = timer
						.elapsed(TimeUnit.NANOSECONDS);

				if (refinementVerificationTime < replacementVerificationTime) {
					// logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
					// logger.info("&&&&&&&&&&&&&&&&&&&& THE VERIFICATION OF THE REPLACEMENT IS LESS EFFICIENT &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
					// logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
				} else {
					// logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
					// logger.info("&&&&&&&&&&&&&&&&&&&& THE VERIFICATION OF THE REPLACEMENT IS MORE EFFICIENT &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
					// logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
					stat.incRepIsMoreEfficient();
				}
				if (refinementSatisfactionvalue != replacementSatisfactionvalue) {
					errorsWriter.close();
					throw new InternalError("Test Number " + configuration.getTestNumber()
							+ " \t Claim Number " + configuration.getClaimNumber()
							+ " \t Configuration " + configuration.getConfigurationId() + "\t refinement "
							+ refinementSatisfactionvalue + " \t replacement: "
							+ replacementSatisfactionvalue);

				}
				// logger.info("The property is: "+refinementSatisfactionvalue.toString());
				record = new Record(configuration, value,
						refinementSatisfactionvalue,
						replacementChecker.isTriviallySatisfied(),
						refinementChecker.getIntersectionAutomataSize(),
						replacementChecker.getIntersectionAutomataSize(),
						refinementVerificationTime, replacementVerificationTime);
				resultPossiblyWriter.append(record);
			} else {
				if (value.equals(SatisfactionValue.NOTSATISFIED)) {
					stat.incNumUnsat();
				}
				if (value.equals(SatisfactionValue.SATISFIED)) {
					stat.incNumSat();
				}
				record = new Record(configuration, value);
			}
			resultWriter.append(record);
			System.gc();
			System.runFinalization();
			errorsWriter.close();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("One test one claim performed in: "
				+ (endTime - initTime) + " ms ");
		
	}

	private static Constraint computeConstraint(BA claim, IBA model,
			Checker checker) {
		ConstraintGenerator cg = new ConstraintGenerator(checker);
		Constraint constraint = cg.perform();
		cg.coloring();
		cg.computePortReachability();
		cg.computeIndispensable();

		return constraint;
	}
}
