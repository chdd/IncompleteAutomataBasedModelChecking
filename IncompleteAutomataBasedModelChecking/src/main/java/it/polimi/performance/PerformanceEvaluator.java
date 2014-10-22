package it.polimi.performance;

import it.polimi.model.impl.automata.BAFactoryImpl;
import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.automata.io.BAReader;
import it.polimi.model.impl.labeling.Proposition;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.impl.transitions.LabelledTransitionFactoryImpl;
import it.polimi.model.interfaces.automata.BAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.ModelChecker;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import edu.uci.ics.jung.io.GraphIOException;

/**
 * evaluates the performances of the model checking tool, it generates a set of random models (BA) with an increasing number of states and transparent states
 * and evaluates the verification time
 * @author claudiomenghi
 */
public class PerformanceEvaluator{


	private static final int initialNumberOfStates=10;
	private static final int maxNumberOfStates=100;
	private static final int numberOfStatesIncrement=1;
	
	private static final int initialNumberOfTransparentStates=1;
	private static final int incrementNumberOfTransparentStates=1;
	private static final int maxNumberTransparentStates=20;
	
	private static final int numInitialStates=2;
	private static final int numAcceptingStates=2;
	
	private static final double numeroProve=30;
	
	private static final String resultsPath="/Users/Claudio1/Desktop/LTLLover/Performance/";

	public static void main(String args[]) throws  IOException, GraphIOException {
		
		ModelCheckerParameters<State, IntersectionState<State>> mp=new ModelCheckerParameters<State, IntersectionState<State>>();
		
		for(int n=initialNumberOfStates; n<=maxNumberOfStates; n=n+numberOfStatesIncrement){
			
			for(int i=initialNumberOfTransparentStates; i<=maxNumberTransparentStates;i=i+incrementNumberOfTransparentStates){
				
				for(int j=0;j<numeroProve;j++){
					Set<IGraphProposition> alphabetModel=new HashSet<IGraphProposition>();
					alphabetModel.add(new Proposition("a", false));
					alphabetModel.add(new Proposition("b", false));
					
					PrintWriter writer=null;
					if(j==0){
						writer = new PrintWriter(new BufferedWriter(new FileWriter(resultsPath+"res"+j+".dat", false)));
						writer.println(mp.toStringHeader());
					}
					else{
						writer = new PrintWriter(new BufferedWriter(new FileWriter(resultsPath+"res"+j+".dat", true)));
					}
					
					IBAImpl<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>> a1 =new IBAImpl<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>(new LabelledTransitionFactoryImpl());
					a1.getRandomAutomaton2(n, 2*Math.log(n)/n, numInitialStates, numAcceptingStates, i, alphabetModel);
					
					BAReader<State, 
					LabelledTransition,
					LabelledTransitionFactory<LabelledTransition>, 
					StateFactory<State>,
					DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>,
					BAFactory<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>, DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>>> baReader=new BAReader<State, 
							LabelledTransition,
							LabelledTransitionFactory<LabelledTransition>, 
							StateFactory<State>,
							DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>,
							BAFactory<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>, 
							DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>>>(
									new LabelledTransitionFactoryImpl(), 
									new StateFactory<State>(), 
									new BAFactoryImpl<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>(new LabelledTransitionFactoryImpl()),
									new BufferedReader(new FileReader("src/main/resources/Automaton2.xml")));
				
					
					
					DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>> a2=baReader.readGraph();
					
					ModelChecker<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>,
					LabelledTransitionFactory<LabelledTransition>,
					ConstrainedTransitionFactory<State, ConstrainedTransition<State>>> mc=
					new ModelChecker<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>,
					LabelledTransitionFactory<LabelledTransition>,
					ConstrainedTransitionFactory<State, ConstrainedTransition<State>>>(a1, a2, mp);
					mc.check();
					writer.println(mp.toString());
					System.out.println("Experiment Number: "+j+" \t states: "+n+"\t transparent states: "+i+"\t states in the intersection: "+mp.getNumStatesIntersection()+"\t satisfied: "+mp.getResult()+"\t time: "+mp.getConstraintComputationTime());
					
					writer.close();
					mc.getIntersection().reset();
				}
			}
			PrintWriter confWriter = new PrintWriter("/Users/Claudio1/Desktop/LTLLover/conf.dat", "UTF-8");
			confWriter.println(numeroProve+", "+initialNumberOfStates+", "+n+", "+numberOfStatesIncrement+", "
					+initialNumberOfTransparentStates+", "+incrementNumberOfTransparentStates+", "+incrementNumberOfTransparentStates+", "+
					+numInitialStates+", "+numAcceptingStates);
			confWriter.close();
			
		}
	}	
}
