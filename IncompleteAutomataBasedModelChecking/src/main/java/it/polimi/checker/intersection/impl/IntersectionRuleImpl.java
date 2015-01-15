package it.polimi.checker.intersection.impl;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.checker.intersection.IntersectionRule;

import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * Defines an {@link IntersectionRule} that specifies how the transitions of the
 * intersection automaton is generated starting from the transition of the model
 * and the claim. <br>
 * The transition of the model can be performed only if it satisfies the
 * conditions specified in the claim
 * 
 * @author claudiomenghi
 * 
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class IntersectionRuleImpl<LABEL extends Label, TRANSITION extends Transition<LABEL>>
		implements IntersectionRule<LABEL,TRANSITION> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TRANSITION getIntersectionTransition(
			TRANSITION modelTransition,
			TRANSITION claimTransition,
			TransitionFactory<LABEL, TRANSITION> intersectionTransitionFactory) {
		if (modelTransition == null) {
			throw new NullPointerException(
					"The model transition cannot be null");
		}
		if (claimTransition == null) {
			throw new NullPointerException(
					"The claim transition cannot be null");
		}
		if (intersectionTransitionFactory == null) {
			throw new NullPointerException(
					"The intersection factory cannot be null");
		}

		Set<LABEL> labels=new HashSet<LABEL>();
		for(LABEL claimLabel: claimTransition.getLabels()){
			for(LABEL modelLabel: modelTransition.getLabels()){
				if(this.satisfies(modelLabel, claimLabel)){
					labels.add(modelLabel);
				}
			}
		}
		if(labels.isEmpty()){
			return null;
		}
		else{
			return intersectionTransitionFactory.create(labels);
		}
	}
	
	/**
	 * returns true if the label of the model satisfies the label of the claim
	 * @param modelLabel is the label of the model
	 * @param claimLabel is the label of the claim
	 * @return true if the label of the model satisfies the label of the claim
	 */
	private boolean satisfies(LABEL modelLabel, LABEL claimLabel){
		for(IGraphProposition claimProposition: claimLabel.getAtomicPropositions()){
			if(claimProposition.isNegated() && modelLabel.getAtomicPropositions().contains(new GraphProposition(claimProposition.getLabel(), false))){
				return false;
			}
			else{
				if(!modelLabel.getAtomicPropositions().contains(new GraphProposition(claimProposition.getLabel(), false))){
					return false;
				}
			}
		}
		return true;
	}
}
