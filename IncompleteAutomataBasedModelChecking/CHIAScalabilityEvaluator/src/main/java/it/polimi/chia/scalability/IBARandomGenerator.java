package it.polimi.chia.scalability;

import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.jgrapht.experimental.RandomGraphHelper;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

public class IBARandomGenerator {

	public static IBA generateRandomBA(double transitionDensity,
			double acceptanceDensity, int nStates, double transparentDensity) {

		ClaimTransitionFactory transitionFactory = new ClaimTransitionFactory();

		List<IGraphProposition> proposition1 = new ArrayList<IGraphProposition>();
		proposition1.add(new GraphProposition("a", false));
		proposition1.add(new GraphProposition("b", false));

		IBA ba = new IBA(new ClaimTransitionFactory());
		ba.addProposition(new GraphProposition("a", false));
		ba.addProposition(new GraphProposition("b", false));

		int numTransition = (int) Math.round(nStates * transitionDensity);
		int numAcceptingStates = (int) Math.round(nStates * acceptanceDensity);
		int numTransparentStates = (int) Math.round(nStates
				* transparentDensity);

		RandomGraphHelper.addVertices(ba.getGraph(), new StateFactory(),
				nStates);

		List<State> states = new ArrayList<State>(ba.getStates());

		Random randSingleton = new Random();
		for (int i = 0; i < numTransition; ++i) {
			ba.addTransition(states.get(randSingleton.nextInt(nStates)),
					states.get(randSingleton.nextInt(nStates)),
					transitionFactory.create());
		}

		ArrayList<State> baStates = new ArrayList<State>(ba.getStates());
		Collections.shuffle(baStates);

		int i = 0;
		for (State s : baStates) {
			if (i < numAcceptingStates) {
				ba.addAcceptState(s);
			}
			i++;
		}
		i = 0;
		Collections.shuffle(baStates);
		for (State s : baStates) {
			if (i < numAcceptingStates) {
				ba.addAcceptState(s);
			}
			i++;
		}
		i = 0;
		Collections.shuffle(baStates);
		for (State s : baStates) {
			if (i < numTransparentStates) {
				ba.addTransparentState(s);
			}
			i++;
		}
		Collections.shuffle(baStates);
		ba.addInitialState(baStates.get(0));
		ba.addTransition(baStates.get(0), baStates.get(1),
				transitionFactory.create());
		ba.addTransition(baStates.get(0), baStates.get(2),
				transitionFactory.create());
		return ba;
	}
}
