package it.polimi.contraintcomputation.decorator;

import it.polimi.Constants;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.contraintcomputation.abstractedBA.AbstractedBA;
import it.polimi.contraintcomputation.brzozowski.Brzozowski;
import it.polimi.contraintcomputation.brzozowski.ConcatenateTransformer;
import it.polimi.contraintcomputation.brzozowski.StarTransformer;
import it.polimi.contraintcomputation.brzozowski.UnionTransformer;
import it.polimi.contraintcomputation.component.Component;

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
 * @param <L>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class TransitionDecorator<L extends Label, S extends State, T extends Transition<L>> {

	
	private AbstractedBA<L, S, T, Component<L, S, T>> intersection;
	
	private LabelFactory<L> labelFactory;

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
	public TransitionDecorator(AbstractedBA<L, S, T, Component<L, S, T>> intersection, LabelFactory<L> labelFactory) {
		if (intersection == null) {
			throw new NullPointerException(
					"The intersection automaton to be filtered cannot be null");
		}
		this.intersection = intersection;
		this.labelFactory=labelFactory;

	}

	/**
	 * returns a new Intersection automaton which contains the only states in
	 * the set componentStates
	 * 
	 * @return a new Intersection automaton which contains the only states in
	 *         the set componentStates
	 */
	public void decorates() {
		for (Component<L,S,T> currComponent : this.intersection.getStates()) {
			if(currComponent.isTransparent()){
				if(currComponent.getInitialStates().size()!=1){
					throw new IllegalArgumentException("Each component that represent transparent state must have exactly 1 initial state");
				}
				S init=currComponent.getInitialStates().iterator().next();
				if(currComponent.getIncomingTransition().get(init).size()!=1){
					throw new IllegalArgumentException("Each component that represent transparent state must have exactly 1 incoming transition");
				}
				T incomingTransition=currComponent.getIncomingTransition().get(init).get(currComponent.getIncomingTransition().get(init).keySet().iterator().next());
				for(S accept: currComponent.getAcceptStates()){
					Brzozowski<L,S,T> b=new Brzozowski<L,S,T>(currComponent, init, accept, new StarTransformer(), new UnionTransformer(Constants.UNIONPLUS), new ConcatenateTransformer(Constants.CONCATENATIONDOT));
					String regexString=b.getRegularExpression();
					
					for(T outComingTransition: this.intersection.getOutTransitions(currComponent)){
						Component<L,S,T> destinationComponent=this.intersection.getTransitionDestination(outComingTransition);
						Set<S> incomingStates=new HashSet<S>(destinationComponent.getIncomingTransition().keySet());
						Set<S> nextStates=currComponent.getOutcomingTransition().get(accept).keySet();
						if(!Collections.disjoint(incomingStates, nextStates)){
							Component<L, S, T> predecessor=this.getPredecessorComponent(currComponent, init, incomingTransition);
							String label="@{"+predecessor.getModelState().getId()+"}:"+predecessor.getModelState().getName()+"- {"+incomingTransition.getId()+"}:"+incomingTransition.getLabels()+"@"+
						regexString+"@{"+destinationComponent.getModelState().getId()+"}:"+destinationComponent.getModelState().getName()+"- {"+outComingTransition.getId()+"}:"+outComingTransition.getLabels()+"@";
							this.decorateTransition(outComingTransition, label, incomingTransition, currComponent.getModelState());
						}
					}
					
					
				}
			}
			else{
				for(T t: this.intersection.getOutTransitions(currComponent)){
					Set<L> labels=new HashSet<L>();
					t.setLabels(labels);
				}
			}
		}
	}
	
	public void decorateTransition(T outComingTransition, String regexString, T incomingTransition, S modelState){
		
		Set<L> labels=new HashSet<L>(outComingTransition.getLabels());
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		propositions.add(new RegexProposition<L, S, T>(modelState, regexString, incomingTransition, outComingTransition));
		labels.add(labelFactory.create(propositions));
		outComingTransition.setLabels(labels);
	}
	
	/**
	 * returns the component that precedes the current component which is connected to the incoming state
	 * @param currComponent
	 * @param incomingState
	 * @return
	 */
	public Component<L, S, T> getPredecessorComponent(Component<L, S, T> currComponent, S incomingState, T transition){
	
		for(Component<L,S,T> predecessor: this.intersection.getPredecessors(currComponent)){
			if(!Collections.disjoint(currComponent.getIncomingTransition().get(incomingState).keySet(), predecessor.getOutcomingTransition().keySet())){
				return predecessor;
			}
		}
		throw new InternalError("The predecessor cannot be found");
	}
}
