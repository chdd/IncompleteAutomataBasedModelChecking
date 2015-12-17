package it.polimi.chia.scalability;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.out.BAToElementTrasformer;
import it.polimi.automata.io.out.IBAToElementTrasformer;
import it.polimi.automata.io.out.ModelToStringTrasformer;
import it.polimi.automata.io.out.XMLWriter;
import it.polimi.automata.state.State;
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
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.RefinementGenerator;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.out.constraint.ConstraintToElementTransformer;
import it.polimi.constraints.io.out.replacement.ReplacementToElementTransformer;
import it.polimi.constraints.io.out.replacement.ReplacementToStringTransformer;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.base.Stopwatch;

public class ScalabilityTest {

    private static final Logger LOGGER = Logger
            .getLogger(ScalabilityTest.class);

    private final static String resultsFile = "results.txt";
    private final static String resultsFilePossibly = "resultsPossibly.txt";

    private final static int N_TESTS = 20;

    private final static AcceptingType acceptingPolicy = AcceptingType.BA;

    public static void main(String[] args) throws Exception {

        String testDirectory = args[0];
        System.out
                .println("--------------------------- STARTING THE TEST: ------------------------");

        Stopwatch timer = Stopwatch.createUnstarted();

        // BA claim = generateRandomClaim(new RandomConfiguration(3, 2, 0.5, 0,
        // 0));
        List<BA> claims = ScalabilityTest.getClaimToBeConsidered();

        for (int testNumber = 1; testNumber <= N_TESTS; testNumber++) {

            File dir = new File(testDirectory + "Test" + testNumber);
            dir.mkdir();
            for (int claimNum = 0; claimNum < claims.size(); claimNum++) {
                File claimdir = new File(testDirectory + "Test" + testNumber
                        + "/Claim" + claimNum);
                claimdir.mkdir();
            }
        }

        ScalabilityTest.test(testDirectory, timer);

    }

    private static List<BA> getClaimToBeConsidered() throws Exception {

        List<BA> claims = new ArrayList<BA>();
        /*
         * claims.add(new ClaimReader(new File("Claim1.xml")).perform());
         * 
         * claims.add(new ClaimReader(new File("Claim2.xml")).perform());
         * 
         * claims.add(new ClaimReader(new File("Claim3.xml")).perform());
         */
        claims.add(new ClaimReader(new File(ClassLoader.getSystemResource(
                "Claim1.xml").getPath())).perform());

        claims.add(new ClaimReader(new File(ClassLoader.getSystemResource(
                "Claim2.xml").getPath())).perform());

        claims.add(new ClaimReader(new File(ClassLoader.getSystemResource(
                "Claim3.xml").getPath())).perform());

        return claims;

    }

    private static void test(String testDirectory, Stopwatch timer)
            throws Exception {

        RandomConfigurationGenerator randomConfigurationGenerator = new RandomConfigurationGenerator(
                ScalabilityTest.getClaimToBeConsidered());

        Statistics stat = new Statistics();

        long initTime = System.currentTimeMillis();
        while (randomConfigurationGenerator.hasNext()) {

            Record record;

            Configuration configuration = randomConfigurationGenerator.next();

            ResultWriter resultWriter = new ResultWriter(testDirectory + "Test"
                    + configuration.getTestNumber() + "/Claim"
                    + configuration.getClaimNumber() + "/" + resultsFile);
            ResultWriter resultPossiblyWriter = new ResultWriter(testDirectory
                    + "Test" + configuration.getTestNumber() + "/Claim"
                    + configuration.getClaimNumber() + "/"
                    + resultsFilePossibly);

            if (configuration.getConfigurationId() % 100 == 0) {
                LOGGER.info("--------------------------- CONFIGURATION: "
                        + configuration.getConfigurationId()
                        + "------------------------");
                LOGGER.info(randomConfigurationGenerator.toString());
                LOGGER.info(stat.toString());

            }

            // BA claim = generateRandomClaim(randomConfiguration);

            // BAToElementTrasformer claimToElementTransformer = new
            // BAToElementTrasformer();

            // XMLWriter claimWriter = new XMLWriter(new File(testDirectory
            // + "Test" + configuration.getTestNumber() + "/Claim" +
            // configuration.getClaimNumber() + "/"
            // + "/Experiment" + configuration.getConfigurationId() +
            // "/claim.xml"),
            // claimToElementTransformer.transform(configuration.getCurrentClaim()));
            // claimWriter.perform();

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

            // check whether the model possibly satisfies the claim
            Checker checker = new Checker(model,
                    configuration.getCurrentClaim(),
                    AcceptingPolicy.getAcceptingPolicy(acceptingPolicy, model,
                            configuration.getCurrentClaim()));
            SatisfactionValue value = checker.perform();

            if (value == SatisfactionValue.POSSIBLYSATISFIED) {
                stat.incNumPossibly();
                // compute the constraint

                // choose a random replacement
                List<Replacement> replacements = ibaGenerator
                        .getNonEmptyReplacements();
                if (replacements.isEmpty()) {
                    throw new InternalError(
                            "There are no non empty replacements");
                }
                Collections.shuffle(replacements);

                Replacement replacement = replacements.iterator().next();
                Constraint constraint = computeConstraint(
                        configuration.getCurrentClaim(), model, checker,
                        replacement.getModelState());

                // VERIFICATION OF THE REFINEMENT
                IBA refinedModel;
                try {
                    refinedModel = new RefinementGenerator(model, replacement)
                            .perform();
                } catch (Exception e) {
                    System.out.println(new ModelToStringTrasformer(model)
                            .perform());

                    System.out.println(new ReplacementToStringTransformer(
                            replacement).perform());
                    System.out.println(e.getMessage());
                    return;
                }
                Checker refinementChecker = new Checker(refinedModel,
                        configuration.getCurrentClaim(),
                        AcceptingPolicy.getAcceptingPolicy(acceptingPolicy,
                                refinedModel, configuration.getCurrentClaim()));

                timer.reset();
                timer.start();
                SatisfactionValue refinementSatisfactionvalue = refinementChecker
                        .perform();
                timer.stop();
                long refinementVerificationTime = timer
                        .elapsed(TimeUnit.NANOSECONDS);

                SubProperty subProperty1 = constraint
                        .getSubProperty(replacement.getModelState());
                // VERIFICATION OF THE REPLACEMENT
                ReplacementChecker replacementChecker = new ReplacementChecker(
                        replacement, subProperty1,
                        AcceptingPolicy.getAcceptingPolicy(acceptingPolicy,
                                replacement.getAutomaton(),
                                subProperty1.getAutomaton()));

                timer.reset();
                timer.start();
                SatisfactionValue replacementSatisfactionvalue = replacementChecker
                        .perform();
                timer.stop();

                long replacementVerificationTime = timer
                        .elapsed(TimeUnit.NANOSECONDS);
                if (refinementChecker.getIntersectionAutomataSize() >= replacementChecker
                        .getIntersectionAutomataSize()) {
                    stat.incRepIsMoreEfficientSpace();
                }
                if (refinementVerificationTime >= replacementVerificationTime) {
                    stat.incRepIsMoreEfficientTime();
                }

                SubProperty subProperty = constraint.getSubProperty(replacement
                        .getModelState());
                record = new Record(configuration, value,
                        refinementSatisfactionvalue,
                        replacementChecker.isTriviallyPossiblySatisfied(),
                        refinementChecker.getIntersectionAutomataSize(),
                        replacementChecker.getIntersectionAutomataSize(),
                        refinementVerificationTime,
                        replacementVerificationTime, replacement.getAutomaton()
                                .size(), replacement.getIncomingTransitions()
                                .size(), replacement.getOutgoingTransitions()
                                .size(), subProperty.getAutomaton().size(),
                        subProperty.getGreenIncomingTransitions().size(),
                        subProperty.getNumYellowIncomingTransitions(),
                        subProperty.getNumIncomingTransitions(),
                        subProperty.getNumRedOutgoingTransitions(),
                        subProperty.getNumYellowOutgoingTransitions(),
                        subProperty.getNumOutgoingTransitions(), model.size(),
                        model.getBlackBoxStates().size());
                resultPossiblyWriter.append(record);
                if (refinementSatisfactionvalue != replacementSatisfactionvalue) {
                    printError(testDirectory, configuration, replacement,
                            refinedModel, constraint, model,
                            configuration.getCurrentClaim(),
                            refinementSatisfactionvalue,
                            replacementSatisfactionvalue);
                }
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
        }
        long endTime = System.currentTimeMillis();
        System.out.println("One test one claim performed in: "
                + (endTime - initTime) + " ms ");

    }

    /**
     * @param testDirectory
     * @param configuration
     * @param refinementSatisfactionvalue
     * @param replacementSatisfactionvalue
     * @throws InternalError
     * @throws Exception
     */
    private static void printError(String testDirectory,
            Configuration configuration, Replacement replacement,
            IBA refinedModel, Constraint constraint, IBA model, BA claim,
            SatisfactionValue refinementSatisfactionvalue,
            SatisfactionValue replacementSatisfactionvalue)
            throws InternalError, Exception {

        File dir = new File(testDirectory + "Test"
                + configuration.getTestNumber() + "/Claim"
                + configuration.getClaimNumber() + "/Experiment"
                + configuration.getConfigurationId());
        dir.mkdir();

        ReplacementToElementTransformer replacementTransformer = new ReplacementToElementTransformer();
        XMLWriter replacementWriter = new XMLWriter(new File(testDirectory
                + "Test" + configuration.getTestNumber() + "/Claim"
                + configuration.getClaimNumber() + "/" + "/Experiment"
                + configuration.getConfigurationId() + "/replacement.xml"),
                replacementTransformer.transform(replacement));
        replacementWriter.perform();

        IBAToElementTrasformer refinementTransformer = new IBAToElementTrasformer();
        XMLWriter refinementWriter = new XMLWriter(new File(testDirectory
                + "Test" + configuration.getTestNumber() + "/Claim"
                + configuration.getClaimNumber() + "/" + "/Experiment"
                + configuration.getConfigurationId() + "/refinement.xml"),
                refinementTransformer.transform(refinedModel));
        refinementWriter.perform();

        ConstraintToElementTransformer constraintTransformer = new ConstraintToElementTransformer();
        XMLWriter constraintWriter = new XMLWriter(new File(testDirectory
                + "Test" + configuration.getTestNumber() + "/Claim"
                + configuration.getClaimNumber() + "/" + "/Experiment"
                + configuration.getConfigurationId() + "/constraint.xml"),
                constraintTransformer.transform(constraint));
        constraintWriter.perform();

        IBAToElementTrasformer modelToElementTransformer = new IBAToElementTrasformer();

        XMLWriter writer = new XMLWriter(new File(testDirectory + "Test"
                + configuration.getTestNumber() + "/Claim"
                + configuration.getClaimNumber() + "/" + "/Experiment"
                + configuration.getConfigurationId() + "/model.xml"),
                modelToElementTransformer.transform(model));
        writer.perform();

        BAToElementTrasformer claimToElementTransformer = new BAToElementTrasformer();

        XMLWriter claimWriter = new XMLWriter(new File(testDirectory + "Test"
                + configuration.getTestNumber() + "/Claim"
                + configuration.getClaimNumber() + "/" + "/Experiment"
                + configuration.getConfigurationId() + "/claim.xml"),
                claimToElementTransformer.transform(claim));
        claimWriter.perform();

        throw new InternalError("Test Number " + configuration.getTestNumber()
                + " \t Claim Number " + configuration.getClaimNumber()
                + " \t Configuration " + configuration.getConfigurationId()
                + "\t refinement " + refinementSatisfactionvalue
                + " \t replacement: " + replacementSatisfactionvalue);
    }

    private static Constraint computeConstraint(BA claim, IBA model,
            Checker checker, State transparentState) {
        ConstraintGenerator cg = new ConstraintGenerator(checker);
        Constraint constraint = cg.perform();

        return constraint;
    }
}
