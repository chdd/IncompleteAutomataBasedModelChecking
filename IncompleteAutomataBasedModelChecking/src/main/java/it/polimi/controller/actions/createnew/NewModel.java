package it.polimi.controller.actions.createnew;

import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.RefinementNode;
import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.ViewInterface;

/**
 * creates a new Model
 * @author claudiomenghi
 *
 * @param <CONSTRAINEDELEMENT> is the type of the constraint that can be added on the transitions
 * @param <STATE> is the type of the states of the automata
 * @param <STATEFACTORY> is the type of the factory of the states of the automata
 * @param <TRANSITION> is the type of the transitions of the automata
 * @param <TRANSITIONFACTORY> is the type of the factory which allow to create the transitions of the automata
 */
public class NewModel<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>>
		implements
		ActionInterface<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> {

	/** 
	 * @see {@link ModelInterface}
	 */
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>  
	void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, 
			TRANSITIONFACTORY,
			INTERSECTIONTRANSITIONFACTORY> view) throws Exception{
		
		// creates a new root state
		STATE rootState=model.getModelStateFactory().create("Model");
		
		// creates a new empty model
		IBAImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY> newModel=
				new IBAImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>(model.getModelTransitionFactory());
		
		// creates a new root node of the refinement tree
		DefaultMutableTreeNode rootnode=new DefaultMutableTreeNode(new
				RefinementNode<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> (rootState, newModel));
		
		// creates a new model refinement
		DefaultTreeModel modelRefinement=new DefaultTreeModel(rootnode);
		
		// sets the three model of the refinement
		model.setModelRefinement(modelRefinement);
		
		// set the current model to the empty model
		model.setModel(newModel);
		
		// updates the model refinement map
		Map<STATE, DefaultMutableTreeNode> map=new HashMap<STATE, DefaultMutableTreeNode>();
		map.put(rootState, rootnode);
		model.setStateRefinementMap(map);
		
		model.setFlatstateRefinementMap(new HashMap<STATE, DefaultMutableTreeNode>());
	}

}
