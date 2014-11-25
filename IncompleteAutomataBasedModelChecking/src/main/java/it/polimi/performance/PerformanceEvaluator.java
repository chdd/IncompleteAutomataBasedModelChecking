package it.polimi.performance;

import it.polimi.io.BAReader;
import it.polimi.model.impl.automata.BAFactoryImpl;
import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.automata.random.RandomIBAGenerator;
import it.polimi.model.impl.labeling.Proposition;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactoryImpl;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactoryImpl;
import it.polimi.model.impl.transitions.ConstrainedTransitionFactoryImpl;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.impl.transitions.TransitionFactoryImpl;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.BAFactory;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.modelchecker.ModelChecker;
import it.polimi.modelchecker.ModelCheckingResults;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

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
		
		ModelCheckingResults<State, State,Transition, IntersectionState<State>, Transition> mp=new ModelCheckingResults<State, State, Transition, IntersectionState<State>, Transition>();
		
		for(int n=initialNumberOfStates; n<=maxNumberOfStates; n=n+numberOfStatesIncrement){
			
			for(int i=initialNumberOfTransparentStates; i<=maxNumberTransparentStates;i=i+incrementNumberOfTransparentStates){
				
				for(int j=0;j<numeroProve;j++){
					Set<Proposition> alphabetModel=new HashSet<Proposition>();
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
					
					IBA<State, Transition> a1 =new IBAImpl< State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl());
					
					a1=new RandomIBAGenerator<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl()).getRandomAutomaton2(n, 2*Math.log(n)/n, numInitialStates, numAcceptingStates, i, alphabetModel);
					
					BAReader<
					State, 
					Transition, 
					BA<State, Transition>,
					BAFactory<State, Transition, BA<State, Transition>>> baReader=
						new BAReader<State, 
						Transition, 
						BA<State, Transition>,
						BAFactory<State, Transition, BA<State, Transition>>>(
									new TransitionFactoryImpl(), 
									new StateFactoryImpl(), 
									new BAFactoryImpl<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl()),
									new BufferedReader(new FileReader("src/main/resources/Automaton2.xml")));
				
					
					
					BA<State, Transition> a2=baReader.readGraph();
					
					ModelChecker<State, State, Transition,
					IntersectionState<State>, Transition> mc=
							new ModelChecker<State, State, Transition,
							IntersectionState<State>, Transition>(a1, a2, 
									new IntersectionStateFactoryImpl(),
									new ConstrainedTransitionFactoryImpl(),
									mp);
					mc.check();
					writer.println(mp.toString());
					System.out.println("Experiment Number: "+j+" \t states: "+n+"\t transparent states: "+i+"\t states in the intersection: "+mp.getNumStatesIntersection()+"\t satisfied: "+mp.getResult()+"\t time: "+mp.getConstraintComputationTime());
					
					writer.close();
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
