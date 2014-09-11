package it.polimi.modelchecker.brzozowski;

import static org.junit.Assert.assertTrue;
import it.polimi.model.AutomatonBuilder;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.brzozowski.predicates.Constraint;
import it.polimi.modelchecker.brzozowski.predicates.Predicate;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class BrzozowskiGetConstraintsTest {

	private static final String arg0="src//main//resources//ExtendedAutomaton1.xml";
	private static final String arg1="src//main//resources//Automaton2.xml";
	
	private IncompleteBuchiAutomaton<State, Transition<State>> a1 =null;
	private BuchiAutomaton<State, Transition<State>>  a2=null;
	private IntersectionAutomaton<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> ris=null;
	private State s2=new State("s2");
	
	@Before
	public void setUp() throws JAXBException, SAXException, IOException, ParserConfigurationException{
		AutomatonBuilder<State, Transition<State>, IncompleteBuchiAutomaton<State, Transition<State>>> builderIBA=
				new AutomatonBuilder<State, Transition<State>, IncompleteBuchiAutomaton<State, Transition<State>>>();
		a1 = builderIBA.loadAutomaton(IncompleteBuchiAutomaton.class, arg0);
		
		AutomatonBuilder<State, Transition<State>, BuchiAutomaton<State, Transition<State>>> builderBA=
				new AutomatonBuilder<State, Transition<State>, BuchiAutomaton<State, Transition<State>>>();
		
		a2=builderBA.loadAutomaton(BuchiAutomaton.class, arg1);
		ris=new IntersectionAutomaton<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>>(a1, a2);
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
		Constraint<State> con=brzozowski.getConstraint();
		assertTrue(
				con.equals(new Constraint<State>(new Predicate<State>(s2,"(a)*b(b)*c(((((a)+(c)))+(b)))*λ")))||
				con.equals(new Constraint<State>(new Predicate<State>(s2,"(a)*b(b)*c(((((a)+(b)))+(c)))*λ")))||
				con.equals(new Constraint<State>(new Predicate<State>(s2,"(a)*b(b)*c(((((b)+(a)))+(c)))*λ")))||
				con.equals(new Constraint<State>(new Predicate<State>(s2,"(a)*b(b)*c(((((b)+(c)))+(a)))*λ")))||
				con.equals(new Constraint<State>(new Predicate<State>(s2,"(a)*b(b)*c(((((c)+(a)))+(b)))*λ")))||
				con.equals(new Constraint<State>(new Predicate<State>(s2,"(a)*b(b)*c(((((c)+(b)))+(a)))*λ"))));
	}
}
