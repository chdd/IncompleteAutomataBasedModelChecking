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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

@SuppressWarnings("serial")
public class ConstraintJPanel<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>,
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
INTERSECTIONSTATE extends IntersectionState<STATE>, 
INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>  extends JPanel {

	private JPanel container;
	private JPanel constraintContainer;

	private JPanel longConstraint;
	private JPanel shortConstraint;
	
	private JLabel constraints;
	
	private ViewInterface<
	CONSTRAINEDELEMENT,
	STATE, 
	TRANSITION, 
	INTERSECTIONSTATE, 
	INTERSECTIONTRANSITION,
	TRANSITIONFACTORY,
	INTERSECTIONTRANSITIONFACTORY> view;
	
	public void updateConstraintJPanel(ViewInterface<
			CONSTRAINEDELEMENT,
			STATE, 
			TRANSITION, 
			INTERSECTIONSTATE, 
			INTERSECTIONTRANSITION,
			TRANSITIONFACTORY,
			INTERSECTIONTRANSITIONFACTORY> view,
			ModelCheckingResults<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> verificationResults){
		   
		this.view=view;
		this.shortConstraint.add(new JLabel("¬"));
		this.addButtons(verificationResults.getSimplifiedConstraint().getLogicalItem(), this.shortConstraint, true);
		this.longConstraint.add(new JLabel("¬"));
		this.addButtons(verificationResults.getConstraint().getLogicalItem(), this.longConstraint, false);
		this.container.repaint();
		
	}
	
	public ConstraintJPanel(Dimension d){
		super();
		this.setSize(new Dimension(d.width-50, d.height));
		this.setPreferredSize(new Dimension(d.width-50, d.height));
		this.setMinimumSize(new Dimension(d.width-50, d.height));
		this.setMaximumSize(new Dimension(d.width-50, d.height));
		
		Dimension constraintElementDimension=new Dimension( (int) (d.width*Constants.constraintConstraintElements)-50, 50);
		Dimension constraintLabelDimension=new Dimension(d.width-((int) (d.width*Constants.constraintConstraintElements))-50, 50);
		
		this.container=new JPanel();
		this.container.setLayout(new BoxLayout(this.container, BoxLayout.Y_AXIS));
		this.constraints=new JLabel("Constraints");
		JPanel constraintLabelContainer=new JPanel();
		constraintLabelContainer.setSize(new Dimension(d.width-50, 50));
		constraintLabelContainer.add(this.constraints);
		this.container.add(constraintLabelContainer);
		
		this.constraintContainer=new JPanel(new GridLayout(2,1));
		this.constraintContainer.setBackground(Color.white);
		
		
		JPanel shortConstraintLabelPanel=new JPanel();
		shortConstraintLabelPanel.setSize(constraintLabelDimension);
		shortConstraintLabelPanel.setPreferredSize(constraintLabelDimension);
		
		shortConstraintLabelPanel.add(new JLabel("Simplified constraint"));
		this.shortConstraint = new JPanel();
		
		this.shortConstraint.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				  BorderFactory.createLoweredBevelBorder()));
		this.shortConstraint.setBackground(Color.white);
		
		this.shortConstraint.setSize(constraintElementDimension);
		this.shortConstraint.setPreferredSize(constraintElementDimension);
		this.shortConstraint.setMinimumSize(constraintElementDimension);
		this.shortConstraint.setMaximumSize(constraintElementDimension);
		
		
		JSplitPane shortConstraintsPanel=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				shortConstraintLabelPanel,shortConstraint
				);
		
		this.constraintContainer.add(shortConstraintsPanel);
		
		
		JPanel longConstraintLabelPanel=new JPanel();
		longConstraintLabelPanel.add(new JLabel("Full constraint"));

		longConstraintLabelPanel.setSize(constraintLabelDimension);
		longConstraintLabelPanel.setPreferredSize(constraintLabelDimension);
		
		this.longConstraint = new JPanel();

		this.longConstraint.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				  BorderFactory.createLoweredBevelBorder()));
		this.longConstraint.setBackground(Color.white);
		this.longConstraint.setSize(constraintElementDimension);
		this.longConstraint.setPreferredSize(constraintElementDimension);
		this.longConstraint.setMinimumSize(constraintElementDimension);
		this.longConstraint.setMaximumSize(constraintElementDimension);
		
		
		
		JSplitPane longConstraintsPanel=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, longConstraintLabelPanel,
				longConstraint);
		
		this.constraintContainer.add(longConstraintsPanel);
		
		this.container.add(constraintContainer);
		this.add(this.container);
	}
	
	public void addButtons(LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> item, JPanel panel, Boolean simplified){
		if(item instanceof AndProposition){
			panel.add(new JLabel("("));
			AndProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> andProp=(AndProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>) item;
			for(int i=0; i<andProp.getPredicates().size();i++){
				this.addButtons(andProp.getPredicates().get(i), panel, simplified);
				if(i!=andProp.getPredicates().size()-1){
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
			panel.add(new PropositionButton(atomicp, view, simplified));
		}
		if(item instanceof OrProposition){
			panel.add(new JLabel("("));
			OrProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> orProp=(OrProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>) item;
			
			for(int i=0; i<orProp.getPredicates().size();i++){
				this.addButtons(orProp.getPredicates().get(i), panel, simplified);
				if(i!=orProp.getPredicates().size()-1){
					panel.add(new JLabel("v"));
				}
			}
			panel.add(new JLabel(")"));
		}
	}
	
	private class PropositionButton extends JButton implements MouseListener{
		
		private AtomicProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> item; 
		private Boolean simplified;
		
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
				INTERSECTIONTRANSITIONFACTORY> view, Boolean simplified){
			super(item.toString());
			this.simplified=simplified;
			this.item=item;
			this.view=view;
			this.setBackground(Color.white);
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
			if(simplified){
				this.view.hightLightConstraint((STATE) this.item.getState(), null);
			}
			else{
				this.view.hightLightConstraint((STATE) this.item.getState(), this.item.getTransitions());
			}
		}
		@Override
		public void mouseExited(MouseEvent e) {
			this.view.doNothightLightConstraint();
		}
	}
}

