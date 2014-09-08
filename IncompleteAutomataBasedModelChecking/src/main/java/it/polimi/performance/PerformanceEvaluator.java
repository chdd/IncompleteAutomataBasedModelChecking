package it.polimi.performance;

import it.polimi.browzozky.predicates.types.EmptyConstraint;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecking.ModelChecker;
import it.polimi.modelchecking.ModelCheckerParameters;

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
		
		int counter=0;
		//System.out.println(mp.toStringHeader());
		for(int n=initialNumberOfStates; n<=maxNumberOfStates; n=n+numberOfStatesIncrement){
			counter++;
			
			/*for(int i=0;i<n;i=i+3){
				alphabet.add("s"+i);
			}*/
			for(int i=initialNumberOfTransparentStates; i<=maxNumberTransparentStates;i=i+incrementNumberOfTransparentStates){
				
				for(int j=0;j<numeroProve;j++){
					Set<String> alphabetModel=new HashSet<String>();
					alphabetModel.add("a");
					alphabetModel.add("b");
					
					//if(counter%3==0){
					//	for(int e=0; e<n; e=e+3){
					//		alphabetModel.add("s"+e);
					//	}
					//}
					//Set<String> alphabetSpecification=new HashSet<String>();
					//	alphabetSpecification.add("a");
					//	alphabetSpecification.add("c");
					//alphabetSpecification.add("d");
					PrintWriter writer=null;
					if(j==0){
						writer = new PrintWriter(new BufferedWriter(new FileWriter(resultsPath+"res"+j+".dat", false)));
						writer.println(mp.toStringHeader());
					}
					else{
						writer = new PrintWriter(new BufferedWriter(new FileWriter(resultsPath+"res"+j+".dat", true)));
					}
					IncompleteBuchiAutomaton<State, Transition<State>> a1 = IncompleteBuchiAutomaton.getRandomAutomaton2(n, 2*Math.log(n)/n, numInitialStates, numAcceptingStates, i, alphabetModel);
					//ExtendedAutomaton<State, Transition<State>> a1 = ExtendedAutomaton.getRandomAutomaton(n, 2*Math.log(n)/n, probabilityOfAStateToBeInitial, probabilityOfAStateToBeAccepting, i, alphabet);
					int numStateSpecification=Math.min(n%10+5, 15);
					
					BuchiAutomaton<State, Transition<State>>  a2 = BuchiAutomaton.loadAutomaton("src/main/resources/Automaton2.xml");
					//BuchiAutomaton<State, Transition<State>>  a2 = BuchiAutomaton.getRandomAutomaton2(numStateSpecification, 0.5, numInitialStates, numAcceptingStates, alphabetSpecification);
					
					ModelChecker<State, Transition<State>> mc=new ModelChecker<State, Transition<State>>(a1, a2, mp);
					
					mc.check(new EmptyConstraint<State>());
					
					//System.out.println(mp.toString());
					writer.println(mp.toString());
					System.out.println("Prova Numero: "+j+" \t states: "+n+"\t transparent states: "+i+"\t states in the intersection: "+mp.getNumStatesIntersection()+"\t satisfied: "+mp.getResult()+"\t time: "+mp.getConstraintComputationTime());
					
					
					a1.toFile("/Users/Claudio1/Desktop/LTLLover/Automata/"+"a1-provaNumero"+j+"-stati"+n+"-tr"+i+".xml");
					mc.getIntersection().toFile("/Users/Claudio1/Desktop/LTLLover/Automata/"+"ris-provaNumero"+j+"-stati"+n+"-tr"+i+".xml");
					writer.close();
					a1.clear();
					a2.clear();
					mc.getIntersection().clear();
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
