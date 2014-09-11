package it.polimi.modelchecker.brzozowski;

import static org.junit.Assert.*;
import it.polimi.model.AutomatonBuilder;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.brzozowski.predicates.AbstractPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EmptyPredicate;
import it.polimi.modelchecker.brzozowski.predicates.LambdaPredicate;
import it.polimi.modelchecker.brzozowski.predicates.Predicate;

import java.io.IOException;
import java.util.Hashtable;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class BrzozowskiGetConstrainedT {

	private static final String arg0="src//main//resources//ExtendedAutomaton1.xml";
	private static final String arg1="src//main//resources//Automaton2.xml";
	
	private IncompleteBuchiAutomaton<State, Transition<State>> a1 =null;
	private BuchiAutomaton<State, Transition<State>>  a2=null;
	private IntersectionAutomaton<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> ris=null;
	
	private IntersectionState<State> s1s40=null;
	private IntersectionState<State> s2s40=null;
	private IntersectionState<State> s2s50=null;
	private IntersectionState<State> s2s60=null;
	private IntersectionState<State> s4s40=null;
	private IntersectionState<State> s3s51=null;
	private IntersectionState<State> s3s60=null;
	private IntersectionState<State> s3s61=null;
	private IntersectionState<State> s3s62=null;
	private State s1=null;
	private State s2=null;
	private State s3=null;
	private State s4=null;
	private State s5=null;
	private State s6=null;
	
	private AbstractPredicate<State> lambdaPredicate=null;
	
	@Before
	public void setUp() throws JAXBException, SAXException, IOException, ParserConfigurationException{
		AutomatonBuilder<State, Transition<State>, IncompleteBuchiAutomaton<State, Transition<State>>> builderIBA=
				new AutomatonBuilder<State, Transition<State>, IncompleteBuchiAutomaton<State, Transition<State>>>();
		a1 = builderIBA.loadAutomaton(IncompleteBuchiAutomaton.class, arg0);
		
		AutomatonBuilder<State, Transition<State>, BuchiAutomaton<State, Transition<State>>> builderBA=
				new AutomatonBuilder<State, Transition<State>, BuchiAutomaton<State, Transition<State>>>();
		
		a2=builderBA.loadAutomaton(BuchiAutomaton.class, arg1);
		ris=new IntersectionAutomaton<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>>(a1, a2);
		s1=new State("s1");
		s2=new State("s2");
		s3=new State("s3");
		s4=new State("s4");
		s5=new State("s5");
		s6=new State("s6");
		
		s1s40=new IntersectionState<State>(s1, s4, 0);
		s4s40=new IntersectionState<State>(s4, s4, 0);
		s3s51=new IntersectionState<State>(s3, s5, 1);
		s2s40=new IntersectionState<State>(s2, s4, 0);
		s2s50=new IntersectionState<State>(s2, s5, 0);
		s2s60=new IntersectionState<State>(s2, s6, 0);
		s3s60=new IntersectionState<State>(s3, s6, 0);
		s3s61=new IntersectionState<State>(s3, s6, 1);
		s3s62=new IntersectionState<State>(s3, s6, 2);
		
		lambdaPredicate=new LambdaPredicate<State>();

	}
	
	/**
	 * tests the correct behavior of the method getS when an accepting state is passed as parameter
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws JAXBException 
	 */
	@Test
	public void testBrozowskiGetConstrainedT() throws JAXBException, SAXException, IOException, ParserConfigurationException {
		
		
		Brzozowski<State,Transition<State>,IntersectionState<State>,Transition<IntersectionState<State>>> brzozowski=
				new Brzozowski<State,Transition<State>,IntersectionState<State>,Transition<IntersectionState<State>>>(ris);
		Hashtable<IntersectionState<State>,Hashtable<IntersectionState<State>, AbstractPredicate<State>>> constraintT=brzozowski.getConstraintT();
		
		
		assertTrue(constraintT.get(s1s40)!=null);
		assertTrue(constraintT.get(s1s40).size()==ris.getStates().size());
		assertTrue(constraintT.get(s1s40).get(s4s40).equals(lambdaPredicate));
		assertTrue(constraintT.get(s1s40).get(s2s40).equals(lambdaPredicate));
		for(IntersectionState<State> s: ris.getStates()){
			if(!(s.equals(s4s40)) && !(s.equals(s2s40))){
				assertTrue(constraintT.get(s1s40).get(s).equals(new EmptyPredicate<>()));
			}
		}
		
		assertTrue(constraintT.get(s4s40)!=null);
		assertTrue(constraintT.get(s4s40).size()==ris.getStates().size());
		assertTrue(constraintT.get(s4s40).get(s3s51).equals(lambdaPredicate));
		for(IntersectionState<State> s: ris.getStates()){
			if(!(s.equals(s3s51))){
				assertTrue(constraintT.get(s4s40).get(s).equals(new EmptyPredicate<>()));
			}
		}
		
		assertTrue(constraintT.get(s3s51)!=null);
		assertTrue(constraintT.get(s3s51).size()==ris.getStates().size());
		assertTrue(constraintT.get(s3s51).get(s3s51).equals(lambdaPredicate));
		for(IntersectionState<State> s: ris.getStates()){
			if(!(s.equals(s3s51))){
				assertTrue(constraintT.get(s3s51).get(s).equals(new EmptyPredicate<>()));
			}
		}
		
		assertTrue(constraintT.get(s2s40)!=null);
		assertTrue(constraintT.get(s2s40).size()==ris.getStates().size());
		assertTrue(constraintT.get(s2s40).get(s2s40).equals(new Predicate<State>(s2, "a")));
		assertTrue(constraintT.get(s2s40).get(s2s50).equals(new Predicate<State>(s2, "b")));
		assertTrue(constraintT.get(s2s40).get(s3s51).equals(new Predicate<State>(s2, "λ")));
		for(IntersectionState<State> s: ris.getStates()){
			if(!(s.equals(s2s40)) && !(s.equals(s2s50)) && !(s.equals(s3s51))){
				assertTrue(constraintT.get(s2s40).get(s).equals(new EmptyPredicate<>()));
			}
		}
		
		assertTrue(constraintT.get(s2s50)!=null);
		assertTrue(constraintT.get(s2s50).size()==ris.getStates().size());
		assertTrue(constraintT.get(s2s50).get(s2s50).equals(new Predicate<State>(s2, "b")));
		assertTrue(constraintT.get(s2s50).get(s2s60).equals(new Predicate<State>(s2, "c")));
		assertTrue(constraintT.get(s2s50).get(s3s51).equals(new Predicate<State>(s2, "λ")));
		for(IntersectionState<State> s: ris.getStates()){
			if(!(s.equals(s2s50)) && !(s.equals(s2s60)) && !(s.equals(s3s51))){
				assertTrue(constraintT.get(s2s50).get(s).equals(new EmptyPredicate<>()));
			}
		}
		
		assertTrue(constraintT.get(s2s60)!=null);
		assertTrue(constraintT.get(s2s60).size()==ris.getStates().size());
		assertTrue(constraintT.get(s2s60).get(s2s60).equals(new Predicate<State>(s2, "((((a)+(b)))+(c))"))||
				   constraintT.get(s2s60).get(s2s60).equals(new Predicate<State>(s2, "((((a)+(c)))+(b))"))||
				   constraintT.get(s2s60).get(s2s60).equals(new Predicate<State>(s2, "((((b)+(a)))+(c))"))||
				   constraintT.get(s2s60).get(s2s60).equals(new Predicate<State>(s2, "((((b)+(c)))+(a))"))||
				   constraintT.get(s2s60).get(s2s60).equals(new Predicate<State>(s2, "((((c)+(a)))+(b))"))||
				   constraintT.get(s2s60).get(s2s60).equals(new Predicate<State>(s2, "((((c)+(b))+(a))"))
				);
		assertTrue(constraintT.get(s2s60).get(s3s61).equals(new Predicate<State>(s2, "λ")));
		for(IntersectionState<State> s: ris.getStates()){
			if(!(s.equals(s2s60)) && !(s.equals(s3s61) )){
				assertTrue(constraintT.get(s2s60).get(s).equals(new EmptyPredicate<>()));
			}
		}
		
		assertTrue(constraintT.get(s3s61)!=null);
		assertTrue(constraintT.get(s3s61).size()==ris.getStates().size());
		assertTrue(constraintT.get(s3s61).get(s3s62).equals(lambdaPredicate));
		for(IntersectionState<State> s: ris.getStates()){
			if(!(s.equals(s3s62)) ){
				assertTrue(constraintT.get(s3s61).get(s).equals(new EmptyPredicate<>()));
			}
		}
		
		assertTrue(constraintT.get(s3s62)!=null);
		assertTrue(constraintT.get(s3s62).size()==ris.getStates().size());
		assertTrue(constraintT.get(s3s62).get(s3s60).equals(lambdaPredicate));
		for(IntersectionState<State> s: ris.getStates()){
			if(!(s.equals(s3s60)) ){
				assertTrue(constraintT.get(s3s62).get(s).equals(new EmptyPredicate<>()));
			}
		}
		
		
		assertTrue(constraintT.get(s3s60)!=null);
		assertTrue(constraintT.get(s3s60).size()==ris.getStates().size());
		assertTrue(constraintT.get(s3s60).get(s3s61).equals(lambdaPredicate));
		for(IntersectionState<State> s: ris.getStates()){
			if(!(s.equals(s3s61)) ){
				assertTrue(constraintT.get(s3s60).get(s).equals(new EmptyPredicate<>()));
			}
		}
		
		

	}
}