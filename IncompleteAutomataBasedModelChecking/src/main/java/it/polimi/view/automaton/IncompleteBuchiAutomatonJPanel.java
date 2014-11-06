package it.polimi.view.automaton;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.menu.IBAStateMenu;
import it.polimi.view.menu.actions.ActionTypesInterface;
import it.polimi.view.menu.actions.ModelActionFactory;

import java.awt.Color;
import java.awt.Paint;
import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

public class IncompleteBuchiAutomatonJPanel
<
CONSTRAINEDELEMENT extends State, 
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>,
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
IBA extends DrawableIBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>> 
extends 
BuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, IBA> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected IBA a;
	
	public IncompleteBuchiAutomatonJPanel(IBA a, 
			 ActionListener l, AbstractLayout<STATE, TRANSITION> layout){
		super(a, l, layout);
		this.a=a; 
	}
	
	protected BuchiAutomatonStatePaintTransformer getPaintTransformer(IBA a){
		return new IncompleteBuchiAutomatonPaintTransformer(a);
	}
	protected JPopupMenu getStateMenu(){
		 return new IBAStateMenu<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>();
	}
	public ActionTypesInterface<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> getActionInterface(){
		return new ModelActionFactory<>();
	}
	
	public void highLightState(STATE s){
		this.getRenderContext().setVertexFillPaintTransformer(new IncompleteBuchiAutomatonHighLightStatePaintTransformer(a, s));
	}
	public void defaultTransformers(){
		this.getRenderContext().setVertexFillPaintTransformer(this.getPaintTransformer(a));
	}
	
	public class IncompleteBuchiAutomatonPaintTransformer extends BuchiAutomatonStatePaintTransformer {

		public IncompleteBuchiAutomatonPaintTransformer(IBA a) {
			super(a);
		}
		
		@Override
		public Paint transform(STATE input) {
			if(a.isTransparent(input)){
				return Color.GRAY;
			}
			return Color.WHITE;
		}
	}
	public class IncompleteBuchiAutomatonHighLightStatePaintTransformer extends IncompleteBuchiAutomatonPaintTransformer {

		STATE s;
		public IncompleteBuchiAutomatonHighLightStatePaintTransformer(IBA a, STATE s) {
			super(a);
			this.s=s;
		}
		
		@Override
		public Paint transform(STATE input) {
			
			if(input.equals(s)){
				return Color.GREEN;
			}
			else{
				return super.transform(input);
			}
		}
	}
}
