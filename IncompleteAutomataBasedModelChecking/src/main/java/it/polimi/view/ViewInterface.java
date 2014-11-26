package it.polimi.view;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.automata.IIntBA;
import it.polimi.modelchecker.ModelCheckingResults;

import java.awt.geom.Point2D;
import java.util.Observer;
import java.util.Set;

import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.collections15.Transformer;

public interface ViewInterface<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends Transition, 
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition>
	{

	/**
	 * 
	 * @param model
	 */
	public void updateModel(IBA<STATE, TRANSITION> model, Transformer<STATE, Point2D> positions, DefaultTreeModel hierarchicalModelRefinement, DefaultTreeModel flatModelRefinement);
	
	public void updateModel(
			IBA<STATE, TRANSITION> model, 
			DefaultTreeModel hierarchicalModelRefinement,
			DefaultTreeModel flatModelRefinement);
	
	
	
	/*
	 * --------------------------------------- CLAIM ---------------------------------------
	 */
	
	public void updateClaim(BA<STATE, TRANSITION> specification);
	
	public void hightLightConstraint(
			STATE state,
			Set<INTERSECTIONTRANSITION> intersectionState
			);
	
	public void doNothightLightConstraint(
			);
	
	public void updateSpecification(BA<STATE, TRANSITION> specification, Transformer<STATE, Point2D> positions);
	public void updateIntersection(IIntBA<STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION> intersection, Transformer<INTERSECTIONSTATE, Point2D> positions);
	
	public void updateVerificationResults(ModelCheckingResults<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> verificationResults,
			IIntBA<STATE, TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION> intersection);
	
	
	public void addObserver(Observer o);

	
	public void setBrzozoski(String system);

	public void displayErrorMessage(String message);
}
