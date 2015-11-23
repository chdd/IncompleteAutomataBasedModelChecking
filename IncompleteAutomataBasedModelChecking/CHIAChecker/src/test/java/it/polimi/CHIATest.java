/**
 * 
 */
package it.polimi;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.BAFactory;
import it.polimi.automata.IBA;
import it.polimi.automata.IBAFactory;
import it.polimi.automata.impl.BAFactoryImpl;
import it.polimi.automata.impl.IBAFactoryImpl;
import it.polimi.automata.io.BAReader;
import it.polimi.automata.io.IBAReader;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.labeling.impl.LabelFactoryImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

import edu.uci.ics.jung.io.GraphIOException;

/**
 * @author claudiomenghi
 * 
 */
public class CHIATest {

	/**
	 * Test method for
	 * {@link it.polimi.CHIA#CHIA(it.polimi.automata.BA, it.polimi.automata.IBA)}
	 * .
	 * 
	 * @throws FileNotFoundException
	 * @throws GraphIOException
	 */
	@Test
	public void testCHIA() throws FileNotFoundException, GraphIOException {
		BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>> claimReader = new BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>>(
				new LabelFactoryImpl(),
				new ClaimTransitionFactoryImpl<Label>(),
				new StateFactoryImpl(),
				new BAFactoryImpl<Label, State, Transition<Label>>(),
				new BufferedReader(new FileReader(getClass().getClassLoader()
						.getResource("SendingMessageClaim.xml").getFile())));

		BA<Label, State, Transition<Label>> claim = claimReader.read();

		IBAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, IBAFactory<Label, State, Transition<Label>>> modelReader = new IBAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, IBAFactory<Label, State, Transition<Label>>>(
				new LabelFactoryImpl(), new ModelTransitionFactoryImpl<>(),
				new StateFactoryImpl(),
				new IBAFactoryImpl<Label, State, Transition<Label>>(),
				new BufferedReader(new FileReader(getClass().getClassLoader()
						.getResource("SendingMessageModel.xml").getFile())));

		IBA<Label, State, Transition<Label>> model = modelReader.read();
		CHIA chia = new CHIA(claim, model);
		int result = chia.check();
		assertTrue(result == -1);

		String constraint = chia.getConstraint();
		System.out.println(constraint);
		
		assertTrue(("¬((([@q1- [start]@([<SIGMA>])*@send2- [fail]@]∧[@send1- [fail]@([<SIGMA>])*.[send^!success].([!success])*@q2- [fail]@])∨([@q1- [start]@([<SIGMA>])*.[send^!success].([!success])*@send2- [fail]@]∧[@send1- [fail]@([!success])*@q2- [fail]@])))")
				.equals(constraint)
				|| ("¬((([@q1- [start]@([<SIGMA>])*.[send^!success].([!success])*@send2- [fail]@]∧[@send1- [fail]@([!success])*@q2- [fail]@])∨([@q1- [start]@([<SIGMA>])*@send2- [fail]@]∧[@send1- [fail]@([<SIGMA>])*.[send^!success].([!success])*@q2- [fail]@])))")
				.equals(constraint));
	}

	/**
	 * Test method for {@link it.polimi.CHIA#check()}.
	 */
	@Test
	public void testCheck() {
		// TODO
	}

	/**
	 * Test method for {@link it.polimi.CHIA#getConstraint()}.
	 */
	@Test
	public void testGetConstraint() {
		// TODO
	}

}