package it.polimi.view;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.ModelCheckingResults;
import it.polimi.modelchecker.brzozowski.propositions.states.AndProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.AtomicProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EpsilonProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LambdaProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LogicalItem;
import it.polimi.modelchecker.brzozowski.propositions.states.OrProposition;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ConstraintJDialog<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>,
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
INTERSECTIONSTATE extends IntersectionState<STATE>, 
INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>  extends JDialog {

	private JPanel panel;
	private ViewInterface<
	CONSTRAINEDELEMENT,
	STATE, 
	TRANSITION, 
	INTERSECTIONSTATE, 
	INTERSECTIONTRANSITION,
	TRANSITIONFACTORY,
	INTERSECTIONTRANSITIONFACTORY> view;
	public ConstraintJDialog(ModelCheckingResults<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> verificationResults,
			ViewInterface<
			CONSTRAINEDELEMENT,
			STATE, 
			TRANSITION, 
			INTERSECTIONSTATE, 
			INTERSECTIONTRANSITION,
			TRANSITIONFACTORY,
			INTERSECTIONTRANSITIONFACTORY> view, 
			Dimension d
			){
		super();
		this.view=view;
		panel = new JPanel(false);
		this.setPreferredSize(d);
		panel.add(new JLabel("¬"));
		this.addButtons(verificationResults.getConstraint().getLogicalItem());
		this.getContentPane().add(panel);
		this.pack();
		

	}
	
	public void addButtons(LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> item){
		if(item instanceof AndProposition){
			panel.add(new JLabel("("));
			AndProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> andProp=(AndProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>) item;
			for(LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> andClause: andProp.getPredicates()){
				this.addButtons(andClause);
				if(andProp.getPredicates().lastIndexOf(andClause)!=(andProp.getPredicates().size()-1)){
					panel.add(new JLabel("^"));
				}
			}
			panel.add(new JLabel(")"));
		}
		if(item instanceof EpsilonProposition){
			panel.add(new JLabel(item.toString()));
		}
		if(item instanceof LambdaProposition){
			panel.add(new JLabel(item.toString()));
		}
		if(item instanceof AtomicProposition){
			AtomicProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> atomicp=(AtomicProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>) (item);
			panel.add(new PropositionButton(atomicp, view));
		}
		if(item instanceof OrProposition){
			panel.add(new JLabel("("));
			OrProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> orProp=(OrProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>) item;
			for(LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> orClause: orProp.getPredicates()){
				this.addButtons(orClause);
				if(orProp.getPredicates().lastIndexOf(orClause)!=(orProp.getPredicates().size()-1)){
					panel.add(new JLabel("v"));
				}
			}
			panel.add(new JLabel(")"));
		}
	}
	
	private class PropositionButton extends JButton implements MouseListener{
		
		private AtomicProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> item; 
		private ViewInterface<
		CONSTRAINEDELEMENT,
		STATE, 
		TRANSITION, 
		INTERSECTIONSTATE, 
		INTERSECTIONTRANSITION,
		TRANSITIONFACTORY,
		INTERSECTIONTRANSITIONFACTORY> view;
		public PropositionButton(AtomicProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> item, 
				
				ViewInterface<
				CONSTRAINEDELEMENT,
				STATE, 
				TRANSITION, 
				INTERSECTIONSTATE, 
				INTERSECTIONTRANSITION,
				TRANSITIONFACTORY,
				INTERSECTIONTRANSITIONFACTORY> view){
			super(item.toString());
			this.item=item;
			this.view=view;
			this.setFocusPainted(false);
			this.setMargin(new Insets(0, 0, 0, 0));
			this.setContentAreaFilled(false);
			this.setBorderPainted(false);
			this.setBorder(null);
			this.setOpaque(false);
			this.addMouseListener(this);
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			this.view.hightLightConstraint((STATE) this.item.getState(), this.item.getTransitions());
		}
		@Override
		public void mouseExited(MouseEvent e) {
			this.view.doNothightLightConstraint();
		}
	}
}

