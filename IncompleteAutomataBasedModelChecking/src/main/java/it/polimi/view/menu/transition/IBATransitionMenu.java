package it.polimi.view.menu.transition;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.automaton.RefinementTree;
import it.polimi.view.menu.TransitionListener;
import it.polimi.view.menu.actions.ModelActionFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class IBATransitionMenu<
	CONSTRAINEDELEMENT extends State, 
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
	ACTIONFACTORY extends ModelActionFactory<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>>
	extends BATransitionMenu<CONSTRAINEDELEMENT, 
		STATE, STATEFACTORY, 
		TRANSITION, 
		TRANSITIONFACTORY, 
		ACTIONFACTORY>{

	private RefinementTree<CONSTRAINEDELEMENT,
							STATE, 
							STATEFACTORY,
							TRANSITION, 
							TRANSITIONFACTORY> tree;
	public IBATransitionMenu(ACTIONFACTORY actionTypesInterface, RefinementTree<CONSTRAINEDELEMENT,
																				STATE, 
																				STATEFACTORY,
																				TRANSITION, 
																				TRANSITIONFACTORY> tree){
		super(actionTypesInterface);
		this.tree=tree;
		this.populate(tree);
	}
	
	public void setActions(){
		
	}
	protected void populate(RefinementTree<CONSTRAINEDELEMENT,
											STATE, 
											STATEFACTORY,
											TRANSITION, 
											TRANSITIONFACTORY> treeP){
		this.add(new TransitionAddCharacter(this));
		this.addSeparator();
		
		this.add(new TransitionDelete(tree));
	}
	
	public class TransitionDelete extends BATransitionMenu<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, ACTIONFACTORY>.TransitionDelete implements
			TransitionListener<CONSTRAINEDELEMENT, STATE, TRANSITION> {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		RefinementTree<
				CONSTRAINEDELEMENT,
				STATE, 
				STATEFACTORY,
				TRANSITION, 
				TRANSITIONFACTORY> tree;
		
		
		/** Creates a new instance of DeleteEdgeMenuItem */
		public TransitionDelete(RefinementTree<CONSTRAINEDELEMENT,
												STATE, 
												STATEFACTORY,
												TRANSITION, 
												TRANSITIONFACTORY> treePar) 
		{
			super();
			this.tree=treePar;
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					l.actionPerformed(actionTypesInterface.deleteEdgeAction(
							e.getSource(), e.getID(), e.getActionCommand(), edge, (DefaultMutableTreeNode) 
							tree.getTree().getLastSelectedPathComponent()));
					
				}
			});
		}
		
		/**
		 * Implements the EdgeMenuListener interface to update the menu item
		 * with info on the currently chosen edge.
		 * 
		 * @param edge
		 * @param visComp
		 */
		public void setEdgeAndView(TRANSITION edge, ActionListener l) {
			this.edge = edge;
			this.l = l;
			this.setText("Delete Transition");
		}
	}
}