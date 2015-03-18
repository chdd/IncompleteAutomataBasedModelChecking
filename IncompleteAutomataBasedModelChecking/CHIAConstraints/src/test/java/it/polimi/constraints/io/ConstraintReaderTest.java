package it.polimi.constraints.io;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.io.transformer.states.BAStateElementParser;
import it.polimi.automata.io.transformer.states.StateElementParser;
import it.polimi.automata.io.transformer.transitions.BATransitionParser;
import it.polimi.automata.io.transformer.transitions.ClaimTransitionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.constraints.Component;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.io.in.BAConstraintReader;

import java.io.File;

import org.junit.Test;

public class ConstraintReaderTest {

	@Test
	public void test() {
		File f = new File(getClass().getClassLoader()
				.getResource("ConstraintM1R1.xml").getFile());

		StateElementParser<State, Transition, BA<State, Transition>> stateElementParser = new BAStateElementParser(
				new StateFactory());

		ClaimTransitionParser<State, Transition, BA<State, Transition>> transitionParser = new BATransitionParser(
				new ClaimTransitionFactory<State>());

		BAConstraintReader<State, Transition> constraintReader = new BAConstraintReader<State, Transition>(
				f,  stateElementParser, transitionParser);
		
		Constraint<State, Transition, BA<State,Transition>> constraint=constraintReader.read();

		assertTrue(constraint.getComponents().size()==2);
		
		for(Component<State, Transition, BA<State,Transition>> c: constraint.getComponents()){
			if(c.getId()==4){
				assertTrue(constraint.getOutcomingPorts(c).size()==2);
				assertTrue(constraint.getIncomingPorts(c).size()==1);
			}
			if(c.getId()==5){
				assertTrue(constraint.getOutcomingPorts(c).size()==1);
				assertTrue(constraint.getIncomingPorts(c).size()==2);
			}
		}
	}
}
