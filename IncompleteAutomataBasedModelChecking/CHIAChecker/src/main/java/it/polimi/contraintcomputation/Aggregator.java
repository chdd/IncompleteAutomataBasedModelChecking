/**
 * 
 */
package it.polimi.contraintcomputation;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.IntersectionBAFactory;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.contraintcomputation.brzozowski.Brzozowski;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections15.BidiMap;
import org.apache.commons.collections15.bidimap.DualHashBidiMap;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * @author claudiomenghi
 * 
 */
class Aggregator<L extends Label, S extends State, T extends Transition<L>> {

	/**
	 * maps each state of the abstracted automaton to the corresponding cluster
	 * it belong with
	 */
	private Map<S, Set<S>> intersectionStateClusterMap;

	/**
	 * contains the abstracted Buchi Automaton
	 */
	private IntersectionBA<L, S, T> abstractedIntersection;

	/**
	 * contains the map from the original transparent state of the model to the
	 * corresponding clusters
	 */
	private Map<S, Set<Component<S>>> modelStateClusterMap;

	/**
	 * is the factory which is used to create transitions
	 */
	private TransitionFactory<L, T> transitionFactory;

	/**
	 * is the factory which is used to create states
	 */
	private StateFactory<S> stateFactory;

	/**
	 * is the factory which is used to create labels
	 */
	private LabelFactory<L> labelFactory;

	/**
	 * contains the intersection automaton to be returned
	 */
	private IntersectionBA<L, S, T> newAbstractedIntersection;

	/**
	 * contains the map between a component and its incoming transition and the
	 * states created in the abstraction intersection
	 */
	private BidiMap<Entry<T, Set<S>>, S> mapComponentState;
	
	private IntersectionBAFactory<L, S, T> intersectionBAFactory;

	/**
	 * 
	 * @param abstractedIntersection
	 *            contains the abstracted Buchi Automaton
	 * @param intersectionStateClusterMap
	 *            maps each state of the abstracted automaton to the
	 *            corresponding cluster it belong with
	 * @param modelStateClusterMap
	 *            contains the map from the original transparent states of the
	 *            model to the corresponding clusters
	 * @param intersectionBAFactory
	 * @param transitionFactory
	 * @param stateFactory
	 * @param labelFactory
	 */
	public Aggregator(IntersectionBA<L, S, T> abstractedIntersection,
			Map<S, Set<S>> intersectionStateClusterMap,
			Map<S, Set<Component<S>>> modelStateClusterMap,
			IntersectionBAFactory<L, S, T> intersectionBAFactory,
			TransitionFactory<L, T> transitionFactory,
			StateFactory<S> stateFactory, LabelFactory<L> labelFactory) {
		
		this.mapComponentState = new DualHashBidiMap<Entry<T, Set<S>>, S>();

		if (abstractedIntersection == null) {
			throw new NullPointerException(
					"The abstracted intersection cannot be null");
		}
		if (intersectionStateClusterMap == null) {
			throw new NullPointerException(
					"The state cluster map cannot be null");
		}
		if (modelStateClusterMap == null) {
			throw new NullPointerException(
					"The model state sub automata map cannot be null");
		}
		if (intersectionBAFactory == null) {
			throw new NullPointerException(
					"The intersection factory cannot be null");
		}
		if (transitionFactory == null) {
			throw new NullPointerException(
					"The transition factory cannot be null");
		}
		if (stateFactory == null) {
			throw new NullPointerException("The state factory cannot be null");
		}
		if (labelFactory == null) {
			throw new NullPointerException("The label factory cannot be null");
		}
		this.intersectionBAFactory=intersectionBAFactory;
		this.newAbstractedIntersection = intersectionBAFactory.create();
		this.abstractedIntersection = abstractedIntersection;
		this.intersectionStateClusterMap = intersectionStateClusterMap;
		this.modelStateClusterMap = modelStateClusterMap;
		this.transitionFactory = transitionFactory;
		this.stateFactory = stateFactory;
		this.labelFactory = labelFactory;
	}

	/**
	 * returns a version of the automaton where the states are aggregated
	 * according with the corresponding clusters.
	 * 
	 * @return a version of the automaton where the states are aggregated
	 *         according with the corresponding clusters.
	 */
	public IntersectionBA<L, S, T> aggregate() {

		this.createAggregatedStates();
		this.createTransitions();

		return newAbstractedIntersection;
	}

	private void createTransitions() {
		// generating the out-coming transitions of the states that corresponds with states of the intersection
		Set<S> states=new HashSet<S>(this.abstractedIntersection.getStates());
		states.removeAll(this.intersectionStateClusterMap.keySet());
		for(S sourceState: states){
			for(T outcomingTransition: abstractedIntersection.getOutTransitions(sourceState)){
				S destinationState=mapComponentState
						.get(new AbstractMap.SimpleEntry<T, Set<S>>(
								outcomingTransition,
								intersectionStateClusterMap
										.get(this.abstractedIntersection
												.getTransitionDestination(outcomingTransition))));
				this.newAbstractedIntersection
						.addTransition(
								sourceState,
								destinationState,
								this.transitionFactory
										.create(new HashSet<L>()));
			}
		}
		
		// generating the out-coming transitions of the states that correspond with components 
		for (Entry<T, Set<S>> entry : mapComponentState.keySet()) {
			// filter the state space to analyze a specific component
			IntersectionBA<L, S, T> filteredIntersection = new Filter<L, S, T>(
					abstractedIntersection, entry.getValue(), intersectionBAFactory).filter();
			
			
			T incomingTransition=entry.getKey();
			// starting from the incoming transition entry.getKey() gets the initial state of the component
			S initState = this.abstractedIntersection
					.getTransitionDestination(incomingTransition);

			// for each accepting state of the component
			for (S finalState : filteredIntersection.getAcceptStates()) {
				
				
				//computes the regular expression
				String regex = new Brzozowski<L, S, T>(filteredIntersection,
						initState, finalState).getRegularExpression();
				// for each outcoming transition
				for (T outcomingTransition : this.abstractedIntersection
						.getOutTransitions(finalState)) {
					
					if (!filteredIntersection.getOutTransitions(finalState)
							.contains(outcomingTransition)) {
						// creates the new regex proposition
						RegexProposition<L, S, T> p = new RegexProposition<L, S, T>(
								this.mapComponentState.get(entry),
								regex, incomingTransition, outcomingTransition);
						S sourceState = this.mapComponentState.get(entry);
						// S destinationState=
						Set<L> regexLabels = new HashSet<L>();
						Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
						propositions.add(p);
						regexLabels.add(labelFactory.create(propositions));
						// adding the label to the set of characters of the BA
						for(L l: regexLabels){
							this.newAbstractedIntersection.addCharacter(l);	
						}
						
						S destinationState=mapComponentState
								.get(new AbstractMap.SimpleEntry<T, Set<S>>(
										outcomingTransition,
										intersectionStateClusterMap
												.get(this.abstractedIntersection
														.getTransitionDestination(outcomingTransition))));
						this.newAbstractedIntersection
								.addTransition(
										sourceState,
										destinationState,
										this.transitionFactory
												.create(regexLabels));
					}
				}
			}
		}
	}

	private void createAggregatedStates() {
		
		Set<S> states=new HashSet<S>(this.abstractedIntersection.getStates());
		states.removeAll(intersectionStateClusterMap.keySet());
		for(S s: states){
			this.newAbstractedIntersection.addState(s);
			if(this.abstractedIntersection.getInitialStates().contains(s)){
				this.newAbstractedIntersection.addInitialState(s);
			}
			if(this.abstractedIntersection.getAcceptStates().contains(s)){
				this.newAbstractedIntersection.addAcceptState(s);
			}
			
		}
		
		// for each state of the model
		for (S s : modelStateClusterMap.keySet()) {
			// for each component associated to the state
			for (Component<S> component : modelStateClusterMap.get(s)) {
			
				// filter the state space to analyze a specific component
				IntersectionBA<L, S, T> filteredIntersection = new Filter<L, S, T>(
						abstractedIntersection, component.getStates(), intersectionBAFactory).filter();

				this.checkAccept(s, filteredIntersection, component.getStates());
				this.checkInitial(s, filteredIntersection, component.getStates());
				
				// for each initial and final state creates a new state
				for (S initState : filteredIntersection.getInitialStates()) {

					for (T incomingTransition : this.abstractedIntersection
							.getInTransitions(initState)) {
						if (!filteredIntersection.getInTransitions(initState)
								.contains(incomingTransition)) {
							
							S newState = this.stateFactory.create("s: "
									+ s.getName() + "- initTransition: "
									+ incomingTransition.getId());
							mapComponentState.put(
									new AbstractMap.SimpleEntry<T, Set<S>>(
											incomingTransition, component.getStates()),
									newState);
							newAbstractedIntersection.addState(newState);
						}
					}
				}
			}
		}
	}
	
	private void checkInitial(S s, IntersectionBA<L, S, T> filteredIntersection, Set<S> component){
		for (S initState : filteredIntersection.getInitialStates()) {

			if(this.abstractedIntersection.getInitialStates().contains(initState)){
				S newState = this.stateFactory.create("s: "
						+ s.getName() + "- initial");
				mapComponentState.put(
						new AbstractMap.SimpleEntry<T, Set<S>>(
								null, component),
						newState);
				newAbstractedIntersection.addInitialState(newState);
			}
		}
	}
	private void checkAccept(S s, IntersectionBA<L, S, T> filteredIntersection, Set<S> component){
		for(S finalState: filteredIntersection.getAcceptStates()){
			if(this.abstractedIntersection.getAcceptStates().contains(finalState)){
				S newState = this.stateFactory.create("s: "
						+ s.getName() + "- final");
				mapComponentState.put(
						new AbstractMap.SimpleEntry<T, Set<S>>(
								null, component),
						newState);
				newAbstractedIntersection.addAcceptState(newState);
			}
		}
	}
}
