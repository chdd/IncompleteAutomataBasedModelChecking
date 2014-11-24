package it.polimi.model.impl.transitions;

import org.apache.commons.collections15.Factory;

import it.polimi.model.impl.labeling.ConjunctiveClauseImpl;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.labeling.SigmaProposition;
import it.polimi.model.impl.states.State;
import it.polimi.model.interfaces.labeling.Formula;
import it.polimi.model.interfaces.transitions.TransitionFactory;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * contains the {@link Factory} which generates {@link Transition}s
 * 
 * @author claudiomenghi
 * 
 */
public class TransitionFactoryImpl implements
		TransitionFactory<Transition> {

	/**
	 * contains the next id of the {@link Transition}
	 */
	protected static int transitionCount = 0;

	/**
	 * creates a new {@link Transition} with a {@link Formula} which contains a
	 * single {@link ConjunctiveClauseImpl} with a single
	 * {@link GraphProposition} SIGMA
	 * 
	 * @return a new {@link Transition} labeled with a {@link Formula} which
	 *         contains only the {@link GraphProposition} SIGMA
	 */
	@Override
	public Transition create() {
		DNFFormula<State> dnfFormula = new DNFFormula<State>();
		dnfFormula.addDisjunctionClause(new SigmaProposition<State>());

		Transition t = new Transition(dnfFormula,
				TransitionFactoryImpl.transitionCount);
		this.updateCounter(TransitionFactoryImpl.transitionCount);
		return t;
	}

	/**
	 * creates a new {@link Transition} with the specified {@link Formula}
	 * 
	 * @param dnfFormula
	 *            is the {@link Formula} to be added as a label of the
	 *            {@link Transition}
	 * @return a new {@link Transition} with the {@link Formula} as label
	 * @throws NullPointerException
	 *             if the {@link Formula} is null
	 */
	public Transition create(Formula dnfFormula) {
		if (dnfFormula == null) {
			throw new NullPointerException(
					"The dnfFormula to be added at the LabelledTransition cannot be null");
		}
		Transition t = new Transition(dnfFormula,
				TransitionFactoryImpl.transitionCount);
		this.updateCounter(TransitionFactoryImpl.transitionCount);
		
		return t;
	}

	/**
	 * creates a new {@link Transition} with the specified {@link Formula},
	 * and the specified id
	 * 
	 * @param condition
	 *            is the {@link Formula} to be added as a label of the
	 *            {@link Transition}
	 * @param id
	 *            is the if of the {@link Transition}
	 * @return a new {@link Transition} with the {@link Formula} as label,
	 *         and the specified id
	 * @throws NullPointerException
	 *             if the {@link Formula} is null
	 * @throws IllegalArgumentException
	 *             if the id is not grater than or equal to zero
	 */
	public Transition create(int id, Formula condition) {
		if (id < 0) {
			throw new IllegalArgumentException(
					"The id must be grater than or equal to zero");
		}
		if (condition == null) {
			throw new NullPointerException(
					"The dnfFormula to be added at the LabelledTransition cannot be null");
		}
		Transition t = new Transition(condition, id);
		this.updateCounter(id);
		return t;
	}
	
	/**
	 * updates the counter of the {@link Transition}
	 * 
	 * @param id
	 *            is the id of the last {@link Transition} that has been created
	 * @throws InternalError
	 *             if the id is less than 0
	 */
	private void updateCounter(int id) {
		if (id < 0) {
			throw new InternalError("The id cannot be less than 0");
		}
		TransitionFactoryImpl.transitionCount = Math.max(id++,
				TransitionFactoryImpl.transitionCount++);
	}
}
