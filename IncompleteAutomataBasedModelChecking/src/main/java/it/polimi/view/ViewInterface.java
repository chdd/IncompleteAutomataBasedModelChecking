package it.polimi.view;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.awt.geom.Point2D;
import java.util.Observer;

import org.apache.commons.collections15.Transformer;

public interface ViewInterface<
	STATE extends State, 
	TRANSITION extends LabelledTransition, 
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>,
	LABELLEDTRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>,
	CONSTRAINEDTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE,INTERSECTIONTRANSITION>>{

	/**
	 * 
	 * @param model
	 */
	public void updateModel(DrawableIBA<STATE, TRANSITION, LABELLEDTRANSITIONFACTORY> model, Transformer<STATE, Point2D> positions);
	
	public void updateModel(DrawableIBA<STATE, TRANSITION, LABELLEDTRANSITIONFACTORY> model);
	
	/*
	 * --------------------------------------- CLAIM ---------------------------------------
	 */
	
	public void updateClaim(DrawableBA<STATE, TRANSITION, LABELLEDTRANSITIONFACTORY> specification);
	
	
	public void updateSpecification(DrawableBA<STATE, TRANSITION, LABELLEDTRANSITIONFACTORY> specification, Transformer<STATE, Point2D> positions);
	public void updateIntersection(DrawableIntBA<STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, CONSTRAINEDTRANSITIONFACTORY> intersection, Transformer<INTERSECTIONSTATE, Point2D> positions);
	
	public void updateVerificationResults(ModelCheckerParameters<STATE, INTERSECTIONSTATE, INTERSECTIONTRANSITION> verificationResults,
			DrawableIntBA<STATE, TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, CONSTRAINEDTRANSITIONFACTORY> intersection);
	public void addObserver(Observer o);

	public void displayErrorMessage(String message);
	
	public void setBrzozoski(String system);
}
