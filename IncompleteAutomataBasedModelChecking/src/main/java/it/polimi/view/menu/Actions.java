package it.polimi.view.menu;

/*
 * MyMouseMenus.java
 *
 * Created on March 21, 2007, 3:34 PM; Updated May 29, 2007
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */

import it.polimi.controller.actions.automata.ChangeModelEdgeLabel;
import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import edu.uci.ics.jung.visualization.VisualizationViewer;

public class Actions {

	@SuppressWarnings("serial")
	public class EdgeMenu<
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>>
	extends JPopupMenu {
		// private JFrame frame;
		public EdgeMenu() {
			super("Edge Menu");
			// this.frame = frame;
			this.add(new AddCharacter<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
			this.addSeparator();
			
			this.add(new DeleteEdgeMenuItem<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
		}
	}

	@SuppressWarnings("serial")
	public class AddCharacter<
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>>
	extends Box implements TransitionListener<STATE, TRANSITION> {
		private TRANSITION edge;
		private VisualizationViewer<STATE, TRANSITION> visComp;
		private ActionListener listener;
		private JTextField character;

		/** Creates a new instance of DeleteEdgeMenuItem */
		public AddCharacter() {
			super(BoxLayout.X_AXIS);

			this.add(new JLabel("Character to add: "));
			this.character = new JTextField("character");
			this.add(character);

			this.character.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
	 						
							listener.actionPerformed(new ChangeModelEdgeLabel
									<STATE, STATEFACTORY, 
									TRANSITION, TRANSITIONFACTORY>(e.getSource(), e.getID(), e.getActionCommand(), character.getText(), edge));
	 				
				}
			});
		}
		
		public void setEdgeAndView(TRANSITION edge,
				VisualizationViewer<STATE, TRANSITION> visComp, ActionListener l) {
			this.edge = edge;
			this.visComp = visComp;
			this.listener=l;
		}
	}

	
	

	@SuppressWarnings("serial")
	public class DeleteEdgeMenuItem<
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>> extends JMenuItem implements
			TransitionListener<STATE, TRANSITION> {
		
		private TRANSITION edge;
		private VisualizationViewer<STATE, TRANSITION> visComp;

		/** Creates a new instance of DeleteEdgeMenuItem */
		public DeleteEdgeMenuItem() {
			super("Delete Edge");
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					visComp.getPickedEdgeState().pick(edge, false);
					visComp.getGraphLayout().getGraph().removeEdge(edge);
					visComp.repaint();
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
		public void setEdgeAndView(TRANSITION edge,
				VisualizationViewer<STATE, TRANSITION> visComp, ActionListener l) {
			this.edge = edge;
			this.visComp = visComp;
			this.setText("Delete Edge " + edge.toString());
		}

	}

	@SuppressWarnings("serial")
	public class Rename<
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>> extends Box implements
			StateMenuListener<STATE, TRANSITION> {
		STATE v;
		VisualizationViewer<STATE, TRANSITION> visComp;
		JTextField name;
	
		public Rename() {
			super(BoxLayout.X_AXIS);
		
			this.add(new JLabel("Rename: "));
			name = new JTextField("New name");
			this.add(name);

			name.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					v.setName(name.getText());
					visComp.repaint();

				}

			});
		}

		public void setVertexAndView(STATE v,
				VisualizationViewer<STATE, TRANSITION> visComp) {
			this.v = v;
			this.visComp = visComp;
		}
	}

	@SuppressWarnings("serial")
	public class Initial<
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>> extends JMenuItem implements
			StateMenuListener<STATE, TRANSITION> {
		STATE v;
		VisualizationViewer<STATE, TRANSITION> visComp;
		
		public Initial() {
			super("Initial");
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					visComp.getPickedVertexState().pick(v, false);
					((BAImpl<STATE, TRANSITION, TRANSITIONFACTORY>) visComp
							.getGraphLayout().getGraph()).addInitialState(v);
					;
					visComp.repaint();

		
				}

			});
		}

		public void setVertexAndView(STATE v,
				VisualizationViewer<STATE, TRANSITION> visComp) {
			this.v = v;
			this.visComp = visComp;
		}

	}

	@SuppressWarnings("serial")
	public class Transparent<
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>> extends JMenuItem implements
			StateMenuListener<STATE, TRANSITION> {
		STATE v;
		VisualizationViewer<STATE, TRANSITION> visComp;

		public Transparent() {
			super("Transparent");
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					visComp.getPickedVertexState().pick(v, false);
					((IBAImpl<STATE, TRANSITION, TRANSITIONFACTORY>) visComp
							.getGraphLayout().getGraph())
							.addTransparentState(v);
					;
					visComp.repaint();

				}

			});
		}

		public void setVertexAndView(STATE v,
				VisualizationViewer<STATE, TRANSITION> visComp) {
			this.v = v;
			this.visComp = visComp;
		}

	}

	@SuppressWarnings("serial")
	public  class Accepting extends JMenuItem implements
			StateMenuListener<State, LabelledTransition> {
		State v;
		VisualizationViewer<State, LabelledTransition> visComp;

		public Accepting() {
			super("Accepting");

			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					visComp.getPickedVertexState().pick(v, false);
					((BAImpl<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>) visComp
							.getGraphLayout().getGraph()).addAcceptState(v);
					;
					visComp.repaint();
				}

			});
		}

		public void setVertexAndView(State v,
				VisualizationViewer<State, LabelledTransition> visComp) {
			this.v = v;
			this.visComp = visComp;
		}

	}

	@SuppressWarnings("serial")
	public class DeleteVertexMenuItem<
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>> extends JMenuItem implements
			StateMenuListener<STATE, TRANSITION> {
		private STATE vertex;
		private VisualizationViewer<STATE, TRANSITION> visComp;
		
		/** Creates a new instance of DeleteVertexMenuItem */
		public DeleteVertexMenuItem() {
			super("Delete Vertex");
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					visComp.getPickedVertexState().pick(vertex, false);
					visComp.getGraphLayout().getGraph().removeVertex(vertex);
					visComp.repaint();
				}
			});
		}

		/**
		 * Implements the VertexMenuListener interface.
		 * 
		 * @param v
		 * @param visComp
		 */
		public void setVertexAndView(STATE v,
				VisualizationViewer<STATE, TRANSITION> visComp) {
			this.vertex = v;
			this.visComp = visComp;
			this.setText("Delete Vertex " + v.toString());
		}

	}
}