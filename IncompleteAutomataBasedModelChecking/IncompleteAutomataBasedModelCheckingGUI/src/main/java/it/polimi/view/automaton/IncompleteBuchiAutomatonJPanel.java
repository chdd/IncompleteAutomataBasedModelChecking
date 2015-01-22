package it.polimi.view.automaton;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.view.menu.actions.ModelActionFactory;
import it.polimi.view.menu.states.IBAStateMenu;
import it.polimi.view.menu.transition.IBATransitionMenu;

import java.awt.Color;
import java.awt.Paint;
import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

public class IncompleteBuchiAutomatonJPanel<CONSTRAINEDELEMENT extends State, STATE extends State, TRANSITION extends Transition, AUTOMATON extends IBA<STATE, TRANSITION>>
		extends
		BuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE, TRANSITION, AUTOMATON> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected AUTOMATON a;

	public IncompleteBuchiAutomatonJPanel(AUTOMATON a, ActionListener l,
			AbstractLayout<STATE, TRANSITION> layout,
			RefinementTree<CONSTRAINEDELEMENT, STATE, TRANSITION> parentNode) {
		super(a, l, layout, parentNode);

		this.a = a;
	}

	protected BuchiAutomatonStatePaintTransformer getPaintTransformer(
			AUTOMATON a) {
		return new IncompleteBuchiAutomatonPaintTransformer(a);
	}

	@Override
	protected JPopupMenu getStateMenu() {
		return new IBAStateMenu<CONSTRAINEDELEMENT, STATE,  TRANSITION,  ModelActionFactory<CONSTRAINEDELEMENT, STATE,  TRANSITION>>(
				new ModelActionFactory<CONSTRAINEDELEMENT, STATE,  TRANSITION>(),
				parentNode);
	}

	@Override
	public JPopupMenu getTransitionPopupMenu() {
		return new IBATransitionMenu<CONSTRAINEDELEMENT, STATE,  TRANSITION, ModelActionFactory<CONSTRAINEDELEMENT, STATE, TRANSITION>>(
				new ModelActionFactory<CONSTRAINEDELEMENT, STATE, TRANSITION>(),
				parentNode);

	}

	public void highLightState(STATE s) {
		this.getRenderContext()
				.setVertexFillPaintTransformer(
						new IncompleteBuchiAutomatonHighLightStatePaintTransformer(
								a, s));
	}

	public void defaultTransformers() {
		this.getRenderContext().setVertexFillPaintTransformer(
				new IncompleteBuchiAutomatonPaintTransformer(a));
	}

	public class IncompleteBuchiAutomatonPaintTransformer extends
			BuchiAutomatonStatePaintTransformer {

		public IncompleteBuchiAutomatonPaintTransformer(AUTOMATON a) {
			super(a);
		}

		@Override
		public Paint transform(STATE input) {
			if (a.isTransparent(input)) {
				return Color.GRAY;
			}
			return Color.WHITE;
		}
	}

	public class IncompleteBuchiAutomatonHighLightStatePaintTransformer extends
			IncompleteBuchiAutomatonPaintTransformer {

		STATE s;

		public IncompleteBuchiAutomatonHighLightStatePaintTransformer(
				AUTOMATON a, STATE s) {
			super(a);
			this.s = s;
		}

		@Override
		public Paint transform(STATE input) {

			if (input.equals(s)) {
				return Color.GREEN;
			} else {
				return super.transform(input);
			}
		}
	}
}
