package it.polimi.view.menu;

/*
 * MyMouseMenus.java
 *
 * Created on March 21, 2007, 3:34 PM; Updated May 29, 2007
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */

import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

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
	public static class EdgeMenu extends JPopupMenu {
		// private JFrame frame;
		public EdgeMenu() {
			super("Edge Menu");
			// this.frame = frame;
			this.add(new AddCharacter());
			this.addSeparator();
			
			this.add(new DeleteEdgeMenuItem());
		}
	}

	@SuppressWarnings("serial")
	public static class AddCharacter extends Box implements EdgeMenuListener<State, LabelledTransition> {
		private LabelledTransition edge;
		private VisualizationViewer<State, LabelledTransition> visComp;
		private JTextField character;

		/** Creates a new instance of DeleteEdgeMenuItem */
		public AddCharacter() {
			super(BoxLayout.X_AXIS);

			this.add(new JLabel("Character to add: "));
			this.character = new JTextField("character");
			this.add(character);

			this.character.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					edge.setDNFFormula(DNFFormula.loadFromString(character.getText()));
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
		public void setEdgeAndView(LabelledTransition edge,
				VisualizationViewer<State, LabelledTransition> visComp) {
			this.edge = edge;
			this.visComp = visComp;
		}
	}

	

	@SuppressWarnings("serial")
	public static class DeleteEdgeMenuItem extends JMenuItem implements
			EdgeMenuListener<State, LabelledTransition> {
		private LabelledTransition edge;
		private VisualizationViewer<State, LabelledTransition> visComp;

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
		public void setEdgeAndView(LabelledTransition edge,
				VisualizationViewer<State, LabelledTransition> visComp) {
			this.edge = edge;
			this.visComp = visComp;
			this.setText("Delete Edge " + edge.toString());
		}

	}

	@SuppressWarnings("serial")
	public static class Rename extends Box implements
			StateMenuListener<State, LabelledTransition> {
		State v;
		VisualizationViewer<State, LabelledTransition> visComp;
		JTextField name;
		private ActionListener listener;

		public Rename(ActionListener l) {
			super(BoxLayout.X_AXIS);
			this.listener = l;

			this.add(new JLabel("Rename: "));
			name = new JTextField("New name");
			this.add(name);

			name.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					v.setName(name.getText());
					visComp.repaint();

					listener.actionPerformed(e);
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
	public static class Initial extends JMenuItem implements
			StateMenuListener<State, LabelledTransition> {
		State v;
		VisualizationViewer<State, LabelledTransition> visComp;
		private ActionListener listener;

		public Initial(ActionListener l) {
			super("Initial");
			this.listener = l;
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					visComp.getPickedVertexState().pick(v, false);
					((BAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>) visComp
							.getGraphLayout().getGraph()).addInitialState(v);
					;
					visComp.repaint();

					listener.actionPerformed(e);

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
	public static class Transparent extends JMenuItem implements
			StateMenuListener<State, LabelledTransition> {
		State v;
		VisualizationViewer<State, LabelledTransition> visComp;

		public Transparent() {
			super("Transparent");
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					visComp.getPickedVertexState().pick(v, false);
					((IBAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>) visComp
							.getGraphLayout().getGraph())
							.addTransparentState(v);
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
	public static class Accepting extends JMenuItem implements
			StateMenuListener<State, LabelledTransition> {
		State v;
		VisualizationViewer<State, LabelledTransition> visComp;
		private ActionListener listener;

		public Accepting(ActionListener l) {
			super("Accepting");
			this.listener = l;

			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					visComp.getPickedVertexState().pick(v, false);
					((BAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>) visComp
							.getGraphLayout().getGraph()).addAcceptState(v);
					;
					visComp.repaint();
					listener.actionPerformed(e);
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
	public static class DeleteVertexMenuItem extends JMenuItem implements
			StateMenuListener<State, LabelledTransition> {
		private State vertex;
		private VisualizationViewer<State, LabelledTransition> visComp;
		private ActionListener listener;

		/** Creates a new instance of DeleteVertexMenuItem */
		public DeleteVertexMenuItem(ActionListener l) {
			super("Delete Vertex");
			this.listener = l;
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					visComp.getPickedVertexState().pick(vertex, false);
					visComp.getGraphLayout().getGraph().removeVertex(vertex);
					visComp.repaint();
					listener.actionPerformed(e);
				}
			});
		}

		/**
		 * Implements the VertexMenuListener interface.
		 * 
		 * @param v
		 * @param visComp
		 */
		public void setVertexAndView(State v,
				VisualizationViewer<State, LabelledTransition> visComp) {
			this.vertex = v;
			this.visComp = visComp;
			this.setText("Delete Vertex " + v.toString());
		}

	}
}