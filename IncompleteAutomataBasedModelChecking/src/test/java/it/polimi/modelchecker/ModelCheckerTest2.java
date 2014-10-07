package it.polimi.modelchecker;

import static org.junit.Assert.assertTrue;
import it.polimi.model.automata.BuilderException;
import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;
import it.polimi.model.io.AutomatonBuilder;
import it.polimi.modelchecker.brzozowski.Constraint;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class ModelCheckerTest2 {

	private static final String arg0="src//main//resources//ExtendedAutomaton1.xml";
	private static final String arg1="src//main//resources//Automaton2.xml";
	
	private IncompleteBuchiAutomaton<State, LabelledTransition<State>> model =null;
	private BuchiAutomaton<State, LabelledTransition<State>>  specification=null;
	private ModelChecker<State,LabelledTransition<State>,IntersectionState<State>,LabelledTransition<IntersectionState<State>>> mck;
	private ModelCheckerParameters<State> mp;
	
	@Before
	public void setUp() throws BuilderException {
		AutomatonBuilder<State, LabelledTransition<State>, IncompleteBuchiAutomaton<State, LabelledTransition<State>>> builderIBA=
				new AutomatonBuilder<State, LabelledTransition<State>, IncompleteBuchiAutomaton<State, LabelledTransition<State>>>();
		model = builderIBA.loadAutomaton(IncompleteBuchiAutomaton.class, arg0);
		
		AutomatonBuilder<State, LabelledTransition<State>, BuchiAutomaton<State, LabelledTransition<State>>> builderBA=
				new AutomatonBuilder<State, LabelledTransition<State>, BuchiAutomaton<State, LabelledTransition<State>>>();
		
		specification=builderBA.loadAutomaton(BuchiAutomaton.class, arg1);
		
		mp=new ModelCheckerParameters<State>();
		mck=new ModelChecker<State,LabelledTransition<State>,IntersectionState<State>,LabelledTransition<IntersectionState<State>>>(model,specification, mp);
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
		assertTrue(mck.check()==-1);
		
		Constraint<State> con=mck.getParameters().getConstraint();
		System.out.println(con);
		assertTrue(
				con.toString().equals("¬(<s2,(a)*b(b)*c(((((a)+(c)))+(b)))*λ>)")||
				con.toString().equals("¬(<s2,(a)*b(b)*c(((((a)+(b)))+(c)))*λ>)")||
				con.toString().equals("¬(<s2,(a)*b(b)*c(((((b)+(a)))+(c)))*λ>)")||
				con.toString().equals("¬(<s2,(a)*b(b)*c(((((b)+(c)))+(a)))*λ>)")||
				con.toString().equals("¬(<s2,(a)*b(b)*c(((((c)+(a)))+(b)))*λ>)")||
				con.toString().equals("¬(<s2,(a)*b(b)*c(((((c)+(b)))+(a)))*λ>)"));
	}

}
