package it.polimi.model.interfaces.transitions;

import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.labeling.Formula;

import org.apache.commons.collections15.Factory;

/**
 * contains the {@link Factory} that is used to generate Constrained
 * {@link Transition}s
 * 
 * @author claudiomenghi
 * 
 * @param CONSTRAINEDELEMENT
 *            is the element that is constrained by the {@link Transition}
 * @param TRANSITION
 *            is the type of the {@link Transition} to be generated
 */
public interface ConstrainedTransitionFactory<CONSTRAINEDELEMENT, TRANSITION extends Transition>
		extends TransitionFactory<TRANSITION> {

	/**
	 * creates a new {@link Transition} with the specified {@link Formula} and
	 * CONSTRAINEDELEMENT
	 * 
	 * @param condition
	 *            specifies the {@link Formula} the element must satisfy
	 * @param element
	 *            is the element which is constrained
	 * @return a new {@link Transition} with the specified condition and element
	 * @throws NullPointerException
	 *             if the condition or the element are null
	 */
	public TRANSITION create(Formula condition, CONSTRAINEDELEMENT element);

	/**
	 * creates a new {@link Transition} with the specified id, condition and
	 * CONSTRAINEDELEMENT
	 * 
	 * @param id
	 *            is the id of the {@link Transition} to be created
	 * @param condition
	 *            is the {@link Formula} that specifies the condition the
	 *            CONSTRAINEDELEMENT must satisfy
	 * @param element
	 *            is the element which is constrained
	 * @return {@link NullPointerException} if the condition or the
	 *         CONSTRAINEDELEMENT are null
	 * @throws NullPointerException
	 *             if the {@link Formula} or the CONSTRAINEDELEMENT are null
	 */
	public TRANSITION create(int id, Formula condition,
			CONSTRAINEDELEMENT element);
}
