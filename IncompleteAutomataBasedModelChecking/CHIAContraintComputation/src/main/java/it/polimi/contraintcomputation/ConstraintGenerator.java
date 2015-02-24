package it.polimi.contraintcomputation;

import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.TransitionFactoryClaimImpl;
import it.polimi.constraints.Component;
import it.polimi.contraintcomputation.abstractedBA.AbstractedBA;
import it.polimi.contraintcomputation.abstractor.Abstractor;
import it.polimi.contraintcomputation.brzozowski.Brzozowski;
import it.polimi.contraintcomputation.brzozowski.ConcatenateTransformer;
import it.polimi.contraintcomputation.brzozowski.IneffectiveStarTransformer;
import it.polimi.contraintcomputation.brzozowski.UnionTransformer;
import it.polimi.contraintcomputation.decorator.TransitionDecorator;
import it.polimi.contraintcomputation.splitter.Splitter;
import it.polimi.contraintcomputation.subautomatafinder.SubAutomataIdentifier;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The constraint generator computes a constraint. A constraint is a (set of)
 * sub-model(s) for the unspecified components is produce
 * 
 * @author claudiomenghi
 * 
 */
public class ConstraintGenerator<S extends State, T extends Transition> {

	/**
	 * is the logger of the ConstraintGenerator class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ConstraintGenerator.class);
	
	/**
	 * contains a map that maps each state of the model with a set of states of
	 * the intersection automaton
	 */
	private Map<S, Set<S>> modelIntersectionStatesMap;
	
	/**
	 * contains the intersection automaton
	 */
	private IntersectionBA<S, T> intBA;

	/**
	 * is the factory to be used to create transitions
	 */
	private TransitionFactory<S, T> transitionFactory;


	private IBA<S, T> model;
	
	TransitionFactory<Component<S, T>, T> refinementTransitionFactory;
	
	/**
	 * creates a new ConstraintGenerator object which starting from the
	 * intersection automaton and the map between the states of the model and
	 * the corresponding states of the intersection automaton computes the
	 * constraints
	 * 
	 * @param intBA
	 *            is the intersection automaton
	 * @param modelIntersectionStatesMap
	 *            is the map between the states of the model and the
	 *            corresponding states in the intersection automaton
	 * @param labelFactory
	 *            is the factory to be used to create labels
	 * @param stateFactory
	 *            is the factory to be used to creating the states
	 * @param transitionFactory
	 *            is the factory to be used to create transitions
	 * @throws NullPointerException
	 *             if the intersection automaton or the model intersection state
	 *             map or the label, state or transition factory is null
	 */
	public ConstraintGenerator(IntersectionBA<S, T> intBA,
			IBA<S, T> model,
			Map<S, Set<S>> modelIntersectionStatesMap,
			TransitionFactory<Component<S, T>, T> refinementTransitionFactory,
			TransitionFactory<S, T> transitionFactory) {
		if (intBA == null) {
			throw new NullPointerException(
					"The intersection model cannot be null");
		}
		if (modelIntersectionStatesMap == null) {
			throw new NullPointerException(
					"The map between the states of the model and the intersection states cannot be null");
		}
		if (transitionFactory == null) {
			throw new NullPointerException(
					"The transition factory cannot be null");
		}
		this.modelIntersectionStatesMap = modelIntersectionStatesMap;
		this.transitionFactory=transitionFactory;
		this.intBA = intBA;
		this.model=model;
		this.refinementTransitionFactory=refinementTransitionFactory;
	}

	/**
	 * returns the constraint of the automaton
	 * @return the constraint of the automaton
	 */
	public String generateConstraint() {
		
		/*
		 * returns the set of the components (set of states) that correspond to
		 * the parts of the automaton that refer to different states of the
		 * model
		 */
		SubAutomataIdentifier<S, T> subautomataIdentifier = new SubAutomataIdentifier<S, T>(
				this.intBA, model, modelIntersectionStatesMap,
				refinementTransitionFactory,
				this.transitionFactory);
		
		AbstractedBA<S, T, Component<S, T>> abstractedAutomaton = subautomataIdentifier
				.getSubAutomata();
		logger.info("Identification of the sub-automata performed");
		logger.info("The abstract automata contains "+abstractedAutomaton.getStates().size()+" states");
		
		/*
		 * The abstraction of the state space is a more concise version of the
		 * intersection automaton I where the portions of the state space which
		 * do not correspond to transparent states are removed
		 */
		Abstractor<S, T> abstractor = new Abstractor<S, T>(abstractedAutomaton);
		AbstractedBA<S, T, Component<S, T>> abstractedIntersection = abstractor
				.abstractIntersection();
		logger.info("Abstraction performed");
		logger.info("The automaton after the abstraction contains "+abstractedAutomaton.getStates().size()+" states");

		Splitter<S, T> splitter = new Splitter<S, T>(
				abstractedIntersection,
				transitionFactory);
		logger.info("Splitting performed");
		logger.info("The automaton after the splitting contains "+abstractedAutomaton.getStates().size()+" states");

		
		AbstractedBA<S, T, Component<S, T>> splittedBA = splitter.split();
		TransitionDecorator<S, T> decorator=new TransitionDecorator<S, T>(splittedBA);
		decorator.decorates();
		logger.info("Transition decoration executed");
		
		System.out.println(splittedBA);
			String ret = "";
		boolean first=true;
		for (Component<S, T> init : splittedBA.getInitialStates()) {
			for (Component<S, T> accept : splittedBA.getAcceptStates()) {
				if(first){
					ret =  new Brzozowski<Component<S, T>, T>(
									splittedBA, init, accept,
									new IneffectiveStarTransformer(), 
									new UnionTransformer(Constants.OR),
									new ConcatenateTransformer(Constants.AND))
									.getRegularExpression();
					first=false;
				}
				else{
					ret = ret
							+ Constants.OR
							+ new Brzozowski<Component<S, T>, T>(
									splittedBA, init, accept,
									new IneffectiveStarTransformer(), new UnionTransformer(Constants.OR),
									new ConcatenateTransformer(Constants.AND))
									.getRegularExpression();
				}
				
				
			}
		}
		return Constants.NOT+"("+ret+")";
	}
}
