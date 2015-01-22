package it.polimi.controller.actions.createnew;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.RefinementNode;
import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

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
TRANSITION extends Transition>
		implements
		ActionInterface<CONSTRAINEDELEMENT, STATE, TRANSITION> {

	/** 
	 * @see {@link ModelInterface}
	 */
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition>  
	void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view) throws Exception{
		
		// creates a new root state
		STATE rootState=model.getModel().getStateFactory().create("Model");
		
		// creates a new empty model
		IBAImpl<STATE, TRANSITION> newModel=
				new IBAImpl<STATE, TRANSITION>(model.getModel().getTransitionFactory(), model.getModel().getStateFactory());
		
		// creates a new root node of the refinement tree
		DefaultMutableTreeNode rootnode=new DefaultMutableTreeNode(new
				RefinementNode<STATE, TRANSITION> (rootState, newModel));
		
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
