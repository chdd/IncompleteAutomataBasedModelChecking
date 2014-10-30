package it.polimi.model.impl.automata;

import org.apache.commons.collections15.Factory;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.automata.IntBAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

public class IntBAFactoryImpl<
	STATE extends State,
	INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends StateFactory<INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>, 
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>,
	TRANSITION extends LabelledTransition,
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>>
	implements
		IntBAFactory<STATE, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY, DrawableIntBA<STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION,INTERSECTIONTRANSITIONFACTORY>>
		
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
	public DrawableIntBA<STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION,INTERSECTIONTRANSITIONFACTORY> create(IBA<STATE, TRANSITION, TRANSITIONFACTORY> model, BA<STATE, TRANSITION, TRANSITIONFACTORY> specification) {
		return new IntBAImpl<STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>
				(model, specification, this.transitionFactory);
	}

	@Override
	public DrawableIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> create() {
		return new IntBAImpl<STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>
				(	new IBAImpl<STATE, TRANSITION, TRANSITIONFACTORY>(labelledTransitionFactory),
					new BAImpl<STATE, TRANSITION, TRANSITIONFACTORY>(labelledTransitionFactory),
					this.transitionFactory);
	}
}
