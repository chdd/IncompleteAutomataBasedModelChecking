package it.polimi.contraintcomputation.decorator;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Component;
import it.polimi.contraintcomputation.Constants;
import it.polimi.contraintcomputation.abstractedBA.AbstractedBA;
import it.polimi.contraintcomputation.brzozowski.Brzozowski;
import it.polimi.contraintcomputation.brzozowski.ConcatenateTransformer;
import it.polimi.contraintcomputation.brzozowski.StarTransformer;
import it.polimi.contraintcomputation.brzozowski.UnionTransformer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * analyzes each component
 * 
 * @author claudiomenghi
 * 
 * @param <S>
 *            is the type of the state of the Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <T>
 *            is the type of the transition of the Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 */
public class TransitionDecorator<S extends State, T extends Transition> {

	
	private AbstractedBA<S, T, Component<S, T>> intersection;
	

	/**
	 * creates a new Filter
	 * 
	 * @param intersection
	 *            is the intersection automaton to be filtered
	 * @param componentStates
	 *            is the set of the states to be included in the intersection
	 *            automaton
	 * @param intersectionBAFactory
	 *            is the factory to be used in creating an Intersection buchi
	 *            automaton
	 * @throws NullPointerException
	 *             if the set of the states in the intersection automaton or the
	 *             set of the states in the componentStates is null
	 */
	public TransitionDecorator(AbstractedBA<S, T, Component<S, T>> intersection) {
		if (intersection == null) {
			throw new NullPointerException(
					"The intersection automaton to be filtered cannot be null");
		}
		this.intersection = intersection;

	}

	/**
	 * returns a new Intersection automaton which contains the only states in
	 * the set componentStates
	 * 
	 * @return a new Intersection automaton which contains the only states in
	 *         the set componentStates
	 */
	public void decorates() {
		for (Component<S,T> currComponent : this.intersection.getStates()) {
			if(currComponent.isTransparent()){
				if(currComponent.getInitialStates().size()!=1){
					throw new IllegalArgumentException("Each component that represent transparent state must have exactly 1 initial state");
				}
				S init=currComponent.getInitialStates().iterator().next();
				if(currComponent.getIncomingPorts().get(init).size()!=1){
					throw new IllegalArgumentException("Each component that represent transparent state must have exactly 1 incoming transition");
				}
				T incomingTransition=currComponent.getIncomingTransition().get(init).get(currComponent.getIncomingTransition().get(init).keySet().iterator().next());
				for(S accept: currComponent.getAcceptStates()){
					Brzozowski<S,T> b=new Brzozowski<S,T>(currComponent, init, accept, new StarTransformer(), new UnionTransformer(Constants.UNIONPLUS), new ConcatenateTransformer(Constants.CONCATENATIONDOT));
					String regexString=b.getRegularExpression();
					
					for(T outComingTransition: this.intersection.getOutTransitions(currComponent)){
						Component<S,T> destinationComponent=this.intersection.getTransitionDestination(outComingTransition);
						Set<S> incomingStates=new HashSet<S>(destinationComponent.getIncomingTransition().keySet());
						Set<S> nextStates=currComponent.getOutcomingPorts().get(accept).keySet();
						if(!Collections.disjoint(incomingStates, nextStates)){
							Component<S, T> predecessor=this.getPredecessorComponent(currComponent, init, incomingTransition);
							String label="@"+predecessor.getModelState().getName()+"- "+incomingTransition.getPropositions()+"@"+
						regexString+"@"+destinationComponent.getModelState().getName()+"- "+outComingTransition.getPropositions()+"@";
							this.decorateTransition(outComingTransition, label, incomingTransition, currComponent.getModelState());
						}
					}
					
					
				}
			}
		}
		for (Component<S,T> currComponent : this.intersection.getStates()) {
			if(!currComponent.isTransparent()){
				for(T t: this.intersection.getOutTransitions(currComponent)){
					t.setLabels(new HashSet<IGraphProposition>());
				}
			}
		}
	}
	
	public void decorateTransition(T outComingTransition, String regexString, T incomingTransition, S modelState){
		
		Set<IGraphProposition> labels=new HashSet<IGraphProposition>();
		labels.add(new RegexProposition<S, T>(modelState, regexString, incomingTransition, outComingTransition));
		outComingTransition.setLabels(labels);
	}
	
	/**
	 * returns the component that precedes the current component which is connected to the incoming state
	 * @param currComponent
	 * @param incomingState
	 * @return
	 */
	public Component<S, T> getPredecessorComponent(Component<S, T> currComponent, S incomingState, T transition){
	
		for(Component<S,T> predecessor: this.intersection.getPredecessors(currComponent)){
			if(!Collections.disjoint(currComponent.getIncomingTransition().get(incomingState).keySet(), predecessor.getOutcomingPorts().keySet())){
				return predecessor;
			}
		}
		throw new InternalError("The predecessor cannot be found");
	}
}
