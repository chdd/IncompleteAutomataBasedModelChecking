package it.polimi.casestudies;

import it.polimi.CHIA;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.IntersectionRule;
import it.polimi.constraints.Constraint;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class RandomTests {

	// public static final int EXPERIMENT_NUMBER = 50;
	public static final int EXPERIMENT_NUMBER = 10;

	//	public static final int INIT_TRANSITION_DENSITY = 50;
	public static final int INIT_TRANSITION_DENSITY = 200;
	public static final int FINAL_TRANSITION_DENSITY = 200;
	// public static final int TRANSITION_DENSITY_INCREMENT = 10;
	public static final int TRANSITION_DENSITY_INCREMENT = 5;

	public static final int INIT_ACCEPTING_DENSITY = 10;
//	public static final int INIT_ACCEPTING_DENSITY = 5;
	public static final int FINAL_ACCEPTING_DENSITY = 10;
	public static final int ACCEPTING_DENSITY_INCREMENT = 5;

	public static final int INIT_TRANSPARENT_DENSITY = 5;
	// public static final int FINAL_TRANSPARENT_DENSITY = 50;
	public static final int FINAL_TRANSPARENT_DENSITY = 50;
	public static final int TRANSPARENT_DENSITY_INCREMENT = 5;
	// public static final int TRANSPARENT_DENSITY_INCREMENT = 5;

	//public static final int INIT_NSTATES = 10;
	public static final int INIT_NSTATES = 10;
	// public static final int FINAL_NSTATES = 100;
	public static final int FINAL_NSTATES = 20;
	public static final int INCREMENT_NSTATES = 1;
	
	//public static final int INCREMENT_NSTATES = 10;
	public static boolean completed = false;
	

	public static void main(String[] args) throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException {

		FileWriter results = new FileWriter(
				"/Users/Claudio1/Desktop/CHIA/CHIAresults.txt");
		results.write("EXPERIMENT_NUMBER: " + EXPERIMENT_NUMBER + "\n");
		results.write("INIT_TRANSITION_DENSITY: " + INIT_TRANSITION_DENSITY
				+ "\n");
		results.write("FINAL_TRANSITION_DENSITY: " + FINAL_TRANSITION_DENSITY
				+ "\n");
		results.write("TRANSITION_DENSITY_INCREMENT: "
				+ TRANSITION_DENSITY_INCREMENT + "\n");
		results.write("INIT_ACCEPTING_DENSITY: " + INIT_ACCEPTING_DENSITY
				+ "\n");
		results.write("FINAL_ACCEPTING_DENSITY: " + FINAL_ACCEPTING_DENSITY
				+ "\n");
		results.write("ACCEPTING_DENSITY_INCREMENT: "
				+ ACCEPTING_DENSITY_INCREMENT + "\n");
		results.write("INIT_TRANSPARENT_DENSITY: " + INIT_TRANSPARENT_DENSITY
				+ "\n");
		results.write("FINAL_TRANSPARENT_DENSITY: " + FINAL_TRANSPARENT_DENSITY
				+ "\n");
		results.write("TRANSPARENT_DENSITY_INCREMENT: "
				+ TRANSPARENT_DENSITY_INCREMENT + "\n");
		results.write("INIT_NSTATES: " + INIT_NSTATES + "\n");
		results.write("FINAL_NSTATES: " + FINAL_NSTATES + "\n");
		results.write("INCREMENT_NSTATES: " + INCREMENT_NSTATES + "\n");

		results.write("Experiment Number \t" + "Transparent density \t"
				+ "N states \t" + "Transition density  \t"
				+ "Acceptance density \t" + " Intersection states \t"
				+ "Model transparent states \t" + "VerificationTime \t"
				+ "SubPropertyIdentification \t" + "PortReachabilityTime \t"
				+ "result\n");

		System.out.println("Experiment Number \t" + "Transparent density \t"
				+ "N states \t" + "Transition density \t"
				+ "Acceptance density \t" + " Intersection states \t"
				+ "Model transparent states \t" + "VerificationTime \t"
				+ "SubPropertyIdentification \t" + "PortReachabilityTime \t"
				+ "result");
		int transitionSteps = ((FINAL_TRANSITION_DENSITY - INIT_TRANSITION_DENSITY +1) / TRANSITION_DENSITY_INCREMENT);
		int acceptingSteps = ((FINAL_ACCEPTING_DENSITY - INIT_ACCEPTING_DENSITY +1) / ACCEPTING_DENSITY_INCREMENT);
		int transparentSteps = ((FINAL_TRANSITION_DENSITY - INIT_TRANSITION_DENSITY +1) / TRANSITION_DENSITY_INCREMENT);
		int statesStep = (FINAL_NSTATES - INIT_NSTATES +1) / INCREMENT_NSTATES;
		int tot = transitionSteps * acceptingSteps * transparentSteps
				* statesStep *EXPERIMENT_NUMBER;
		
		System.out.println("N tests: "+tot);
		
		int counter = 0;
		for (int experimentNumber = 1; experimentNumber <= EXPERIMENT_NUMBER; experimentNumber++) {

			for (int transparentDensity = INIT_TRANSPARENT_DENSITY; transparentDensity <= FINAL_TRANSPARENT_DENSITY; transparentDensity = transparentDensity
					+ TRANSPARENT_DENSITY_INCREMENT) {
				for (int nStates = INIT_NSTATES; nStates <= FINAL_NSTATES; nStates = nStates
						+ INCREMENT_NSTATES) {
					for (int transitionDensity = INIT_TRANSITION_DENSITY; transitionDensity <= FINAL_TRANSITION_DENSITY; transitionDensity = transitionDensity
							+ TRANSITION_DENSITY_INCREMENT) {

						for (int acceptanceDensity = INIT_ACCEPTING_DENSITY; acceptanceDensity <= FINAL_ACCEPTING_DENSITY; acceptanceDensity = acceptanceDensity
								+ ACCEPTING_DENSITY_INCREMENT) {

							IBA<State, Transition> model = IBA
									.generateRandomBA(
											transitionDensity / 100.0,
											acceptanceDensity / 100.0, nStates,
											transparentDensity / 100.0);

							BA<State, Transition> claim = BA
									.generateRandomBA(
											transitionDensity / 100.0,
											acceptanceDensity / 100.0, nStates);
							final CHIA<State, Transition> chia = new CHIA<State, Transition>(
									claim,
									model,
									new IntersectionRule<State, Transition>(
											new ClaimTransitionFactory<State>(),
											new StateFactory()),
									new ClaimTransitionFactory<State>(),
									new ModelCheckingResults(true, true, false));
							int res = chia.check();

							final Object wait = new Object();
							if (res == -1) {

								completed=false;
								Thread t = new Thread(new Runnable() {
									// run method
									@Override
									public void run() {
										// setup run
										Constraint<State, Transition, BA<State, Transition>> constraint = chia
												.getConstraint();
										// done modifying list
										completed = true;
										
										System.out.println("Ho finito");
										synchronized (wait) {
											wait.notify();
										}
									}
								});
								t.start();
								// wait for ten seconds, then return list
								try {
									synchronized (wait) {
										wait.wait(60000);
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								
								if(completed==false){
									System.out.println("Non sono riuscito");
									chia.getMcResults().setPortReachabilityTime(-1);
								}
								/*
								 * File constraintFile = new File(
								 * "/Users/Claudio1/Desktop/CHIA/results/Constraint"
								 * + nStates + "." + transitionDensity / 100.0 +
								 * "." + acceptanceDensity / 100.0 + "." +
								 * transparentDensity / 100.0 + ".txt"); new
								 * ConstraintWriter<State, Transition, BA<State,
								 * Transition>>( constraint,
								 * constraintFile).write();
								 */
							}
							results.write(experimentNumber
									+ "\t"
									+ transparentDensity / 100.0
									+ "\t"
									+ nStates
									+ "\t"
									+ transitionDensity / 100.0
									+ "\t"
									+ acceptanceDensity / 100.0
									+ "\t"
									+ chia.getMcResults()
											.getNumStatesIntersection()
									+ "\t"
									+ chia.getMcResults()
											.getNumTransparentStatesModel()
									+ "\t"
									+ chia.getMcResults()
											.getTotalVerificationTime()
									+ "\t"
									+ chia.getMcResults()
											.getSubpropertyTime()
									+ "\t"
									+ "\t"
									+ chia.getMcResults()
											.getPortReachabilityTime() + "\t"
									+ res + "\n"

							);
							counter++;
							System.out.println(experimentNumber
									+ "\t"
									+ transparentDensity
									/ 100.0
									+ "\t"
									+ nStates
									+ "\t"
									+ +transitionDensity
									/ 100.0
									+ "\t"
									+ acceptanceDensity
									/ 100.0
									+ "\t"
									+ chia.getMcResults()
											.getNumStatesIntersection()
									+ "\t"
									+ chia.getMcResults()
											.getNumTransparentStatesModel()
									+ "\t"
									+ chia.getMcResults()
											.getTotalVerificationTime()
									+ "\t"
									+ chia.getMcResults()
											.getSubpropertyTime()
									+ "\t"
									+ chia.getMcResults()
											.getPortReachabilityTime() + "\t"
									+ res);
							if ((counter) % 10 == 0) {
								System.out.println(counter + "/" + tot);
							}

						}
					}
				}
			}
		}
		results.close();
	}
}
