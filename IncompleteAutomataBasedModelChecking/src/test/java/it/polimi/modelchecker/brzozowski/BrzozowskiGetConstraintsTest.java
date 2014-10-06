package it.polimi.modelchecker.brzozowski;

import static org.junit.Assert.assertTrue;
import it.polimi.model.automata.AutomatonBuilder;
import it.polimi.model.automata.BuilderException;
import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.State;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class BrzozowskiGetConstraintsTest {

	private static final String arg0="src//main//resources//ExtendedAutomaton1.xml";
	private static final String arg1="src//main//resources//Automaton2.xml";
	
	private IncompleteBuchiAutomaton<State, LabelledTransition<State>> a1 =null;
	private BuchiAutomaton<State, LabelledTransition<State>>  a2=null;
	private IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>> ris=null;
	
	@Before
	public void setUp() throws BuilderException {
		AutomatonBuilder<State, LabelledTransition<State>, IncompleteBuchiAutomaton<State, LabelledTransition<State>>> builderIBA=
				new AutomatonBuilder<State, LabelledTransition<State>, IncompleteBuchiAutomaton<State, LabelledTransition<State>>>();
		a1 = builderIBA.loadAutomaton(IncompleteBuchiAutomaton.class, arg0);
		
		AutomatonBuilder<State, LabelledTransition<State>, BuchiAutomaton<State, LabelledTransition<State>>> builderBA=
				new AutomatonBuilder<State, LabelledTransition<State>, BuchiAutomaton<State, LabelledTransition<State>>>();
		
		a2=builderBA.loadAutomaton(BuchiAutomaton.class, arg1);
		ris=new IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(a1, a2);
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
		
		
		Brzozowski<State,LabelledTransition<State>,IntersectionState<State>,LabelledTransition<IntersectionState<State>>> brzozowski=
				new Brzozowski<State,LabelledTransition<State>,IntersectionState<State>,LabelledTransition<IntersectionState<State>>>(ris);
		Constraint<State> con=brzozowski.getConstraint();
		assertTrue(
				con.toString().equals("¬(<s2,(a)*b(b)*c(((((a)+(c)))+(b)))*λ>)")||
				con.toString().equals("¬(<s2,(a)*b(b)*c(((((a)+(b)))+(c)))*λ>)")||
				con.toString().equals("¬(<s2,(a)*b(b)*c(((((b)+(a)))+(c)))*λ>)")||
				con.toString().equals("¬(<s2,(a)*b(b)*c(((((b)+(c)))+(a)))*λ>)")||
				con.toString().equals("¬(<s2,(a)*b(b)*c(((((c)+(a)))+(b)))*λ>)")||
				con.toString().equals("¬(<s2,(a)*b(b)*c(((((c)+(b)))+(a)))*λ>)"));
	}
}
