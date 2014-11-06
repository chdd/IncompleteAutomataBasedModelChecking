package it.polimi.model.impl.automata;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.automata.IntBAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import org.apache.commons.collections15.Factory;

public class IntBAFactoryImpl<
	CONSTRAINEDELEMENT extends State,
	STATE extends State,
	INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends StateFactory<INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT,  INTERSECTIONTRANSITION>,
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>>
	implements
		IntBAFactory<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY, DrawableIntBA<CONSTRAINEDELEMENT, STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION,INTERSECTIONTRANSITIONFACTORY>>
		
	{

	/**
	 * contains the {@link LabelledTransitionFactory} which is used to create the {@link LabelledTransition} of the {@link DrawableIBA}
	 */
	protected INTERSECTIONTRANSITIONFACTORY transitionFactory;
	protected TRANSITIONFACTORY labelledTransitionFactory;
	
	/**
	 * crates a new {@link Factory} for the {@link DrawableIBA}
	 * @param transitionFactory is the {@link Factory} which is used to create the {@link LabelledTransition} of the {@link DrawableIBA}
	 * @throws NullPointerException if the transitionFactory is null
	 */
	public IntBAFactoryImpl(INTERSECTIONTRANSITIONFACTORY transitionFactory, TRANSITIONFACTORY labelledTransitionFactory){
		if(transitionFactory==null){
			throw new NullPointerException("The transition factory cannot be null");
		}
		this.transitionFactory=transitionFactory;
		this.labelledTransitionFactory=labelledTransitionFactory;
	}
	
	

	/**
	 * creates a new empty {@link DrawableIBA}
	 * @return a new empty {@link DrawableIBA}
	 */
	@Override
	public DrawableIntBA<CONSTRAINEDELEMENT, STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION,INTERSECTIONTRANSITIONFACTORY> create(IBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> model, BA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> specification) {
		return new IntBAImpl<CONSTRAINEDELEMENT, STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>
				(model, specification, this.transitionFactory);
	}

	@Override
	public DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> create() {
		return new IntBAImpl<CONSTRAINEDELEMENT, STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>
				(	new IBAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>(labelledTransitionFactory),
					new BAImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>(labelledTransitionFactory),
					this.transitionFactory);
	}
}
