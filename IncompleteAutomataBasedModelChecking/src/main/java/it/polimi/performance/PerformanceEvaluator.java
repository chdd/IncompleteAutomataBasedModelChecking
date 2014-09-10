package it.polimi.performance;

import it.polimi.model.AutomatonBuilder;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.ModelChecker;
import it.polimi.modelchecker.ModelCheckerParameters;
import it.polimi.modelchecker.brzozowski.predicates.EmptyPredicate;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

/**
 * evaluates the performances of the model checking tool, it generates a set of random models (BA) with an increasing number of states and transparent states
 * and evaluates the verification time
 * @author claudiomenghi
 */
public class PerformanceEvaluator{


	private static final int initialNumberOfStates=10;
	private static final int maxNumberOfStates=50;
	private static final int numberOfStatesIncrement=2;
	
	private static final int initialNumberOfTransparentStates=1;
	private static final int incrementNumberOfTransparentStates=1;
	private static final int maxNumberTransparentStates=10;
	
	private static final int numInitialStates=2;
	private static final int numAcceptingStates=2;
	
	private static final double numeroProve=30;
	
	private static final String resultsPath="/Users/Claudio1/Desktop/LTLLover/Performance/";

	public static void main(String args[]) throws JAXBException, FileNotFoundException, XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		
		ModelCheckerParameters mp=new ModelCheckerParameters();
		
		for(int n=initialNumberOfStates; n<=maxNumberOfStates; n=n+numberOfStatesIncrement){
			
			for(int i=initialNumberOfTransparentStates; i<=maxNumberTransparentStates;i=i+incrementNumberOfTransparentStates){
				
				for(int j=0;j<numeroProve;j++){
					Set<String> alphabetModel=new HashSet<String>();
					alphabetModel.add("a");
					alphabetModel.add("b");
					
					PrintWriter writer=null;
					if(j==0){
						writer = new PrintWriter(new BufferedWriter(new FileWriter(resultsPath+"res"+j+".dat", false)));
						writer.println(mp.toStringHeader());
					}
					else{
						writer = new PrintWriter(new BufferedWriter(new FileWriter(resultsPath+"res"+j+".dat", true)));
					}
					IncompleteBuchiAutomaton<State, Transition<State>> a1 = IncompleteBuchiAutomaton.getRandomAutomaton2(n, 2*Math.log(n)/n, numInitialStates, numAcceptingStates, i, alphabetModel);
					
					AutomatonBuilder<State, Transition<State>, BuchiAutomaton<State, Transition<State>>> builder=
							new AutomatonBuilder<State, Transition<State>, BuchiAutomaton<State, Transition<State>>>();
					
					BuchiAutomaton<State, Transition<State>>  a2=builder.loadAutomaton(BuchiAutomaton.class, "src/main/resources/Automaton2.xml");
					
					ModelChecker<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> mc=new ModelChecker<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>>(a1, a2, mp);
					
					mc.check(new EmptyPredicate<State>());
					
					writer.println(mp.toString());
					System.out.println("Experiment Number: "+j+" \t states: "+n+"\t transparent states: "+i+"\t states in the intersection: "+mp.getNumStatesIntersection()+"\t satisfied: "+mp.getResult()+"\t time: "+mp.getConstraintComputationTime());
					
					
					a1.toFile("/Users/Claudio1/Desktop/LTLLover/Automata/"+"a1-provaNumero"+j+"-stati"+n+"-tr"+i+".xml");
					mc.getIntersection().toFile("/Users/Claudio1/Desktop/LTLLover/Automata/"+"ris-provaNumero"+j+"-stati"+n+"-tr"+i+".xml");
					writer.close();
					a1.reset();
					a2.reset();
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
