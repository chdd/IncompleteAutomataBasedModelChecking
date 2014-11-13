package it.polimi.view.tabs;

import it.polimi.Constants;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.ModelCheckingResults;
import it.polimi.view.automaton.BuchiAutomatonJPanel;
import it.polimi.view.automaton.IncompleteBuchiAutomatonJPanel;
import it.polimi.view.automaton.IntersectionAutomatonJPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;

@SuppressWarnings("serial")
public class VerificationTab<
		CONSTRAINEDELEMENT extends State,
		STATE extends State, 
		STATEFACTORY extends StateFactory<STATE>,
		TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
		TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
		INTERSECTIONSTATE extends IntersectionState<STATE>, 
		INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
		INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
		INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>
	extends JPanel {
	
	
	private AbstractLayout<STATE, TRANSITION> verificationModelLayout;
	private AbstractLayout<STATE, TRANSITION> verificationClaimLayout;
	private AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION> verificationIntersectionLayout;
	private JLabel result;
	
	private JTextField intersectionTime;
	private JTextField emptinessTime;
	private JTextField constraintComputationTime;
	private JTextField verificationTime;
	private JTextField simplificationTime;
	
	
	
	private IncompleteBuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableIBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>>  verificationModelPanel;
	private BuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>>   verificationClaimPanel;
	private IntersectionAutomatonJPanel<CONSTRAINEDELEMENT, STATE, 
		STATEFACTORY,
		TRANSITION, 
		TRANSITIONFACTORY,
		INTERSECTIONSTATE, 
		INTERSECTIONSTATEFACTORY,
		INTERSECTIONTRANSITION,
		INTERSECTIONTRANSITIONFACTORY,DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>> verificationIntersectionPanel;

	public VerificationTab(ModelInterface<CONSTRAINEDELEMENT, STATE, 
			STATEFACTORY, TRANSITION, 
			TRANSITIONFACTORY, INTERSECTIONSTATE, 
			INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, 
			INTERSECTIONTRANSITIONFACTORY> model, 
			ActionListener l){
		
		 super(false);
	     this.setLayout(new GridLayout(1, 1));
	       
		JPanel container1=new JPanel();
		 container1.setLayout(new BoxLayout(container1,BoxLayout.Y_AXIS));
		
		 
		 this.verificationModelLayout=new FRLayout<STATE,TRANSITION>(model.getModel());
		 container1.add(new JLabel("Model"));
		 this.verificationModelPanel=new IncompleteBuchiAutomatonJPanel
				 <CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableIBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>> (model.getModel(), l, this.verificationModelLayout, 
						 null);
		 this.verificationModelPanel.setTranformingMode();
		 container1.add(verificationModelPanel);
		

		 this.verificationClaimLayout=new FRLayout<STATE,TRANSITION>(model.getSpecification());
		 container1.add(new JLabel("Claim"));
		 this.verificationClaimPanel=new BuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>> (model.getSpecification(), l, this.verificationClaimLayout, null);
		 this.verificationClaimPanel.setTranformingMode();
		 container1.add(verificationClaimPanel);
		 this.add(container1);
		 
		 JPanel container2=new JPanel();
		 container2.setLayout(new BoxLayout(container2,BoxLayout.Y_AXIS));
		 
		 
		 // verification results
		 container2.add(new JLabel("Model Checking results"));
		 
		 JPanel resultContainer=new JPanel(new GridLayout(2,1));
		 resultContainer.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				  BorderFactory.createLoweredBevelBorder()));
		
		 JPanel resultPanel=new JPanel();
		 this.result=new JLabel(Constants.resultInitial);
		 this.result.setSize(new Dimension(Constants.verificationResultIconSize, Constants.verificationResultIconSize));
		 
		 resultPanel.add(result);
		 resultContainer.add(resultPanel);
		 
		 JPanel timePanel=new JPanel(new GridLayout(6,2));
		 timePanel.add(new JLabel("Intersection Time (s)"));
		 this.intersectionTime=new JTextField(20);
		 this.intersectionTime.setEditable(false);
		 timePanel.add(this.intersectionTime);
		
		 timePanel.add(new JLabel("Emptiness Time (s)"));
		 this.emptinessTime=new JTextField(20);
		 this.emptinessTime.setEditable(false);
		 timePanel.add(this.emptinessTime);
		
		 timePanel.add(new JLabel("Constraint Computation Time (s)"));
		 this.constraintComputationTime=new JTextField(20);
		 this.constraintComputationTime.setEditable(false);
		 timePanel.add(this.constraintComputationTime);
		 
		 timePanel.add(new JLabel("Total Verification Time (s)"));
		 this.verificationTime=new JTextField(20);
		 this.verificationTime.setEditable(false);
		 timePanel.add(this.verificationTime);
		 resultContainer.add(timePanel);
		 
		 timePanel.add(new JLabel("Total Simplification Time (s)"));
		 this.simplificationTime=new JTextField(20);
		 this.simplificationTime.setEditable(false);
		 timePanel.add(this.simplificationTime);
		 resultContainer.add(timePanel);
		 
		 container2.add(resultContainer);
		 
		 
		 // Intersection automaton
		 JLabel intersectionLabel=new JLabel("Intersection Automaton");
		 container2.add(intersectionLabel);
		 this.verificationIntersectionLayout=new FRLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION>(model.getIntersection());
		 this.verificationIntersectionPanel=new IntersectionAutomatonJPanel
				 <CONSTRAINEDELEMENT, STATE, 
					STATEFACTORY,
					TRANSITION, 
					TRANSITIONFACTORY,
					INTERSECTIONSTATE, 
					INTERSECTIONSTATEFACTORY,
					INTERSECTIONTRANSITION,
					INTERSECTIONTRANSITIONFACTORY,DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>>
		 	(model.getIntersection(), l, this.verificationIntersectionLayout);
		 
		 this.verificationIntersectionPanel.setTranformingMode();
		 container2.add(verificationIntersectionPanel);
		 
		 this.add(container2);
	}
	
	public void updateVerificationResults(ModelCheckingResults<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> verificationResults,
			DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersection) {
		
		if(verificationResults.getResult()==1){
			this.result.setIcon(Constants.resultYes);
			this.result.setText("The property is satisfied");
			this.result.repaint();
		}
		if(verificationResults.getResult()==0){
			this.result.setIcon(Constants.resultNo);
			this.result.setText("The property is not satisfied");
			this.result.repaint();
		}
		if(verificationResults.getResult()==-1){
			this.result.setIcon(Constants.resultMaybe);
			this.result.setText("The property is possibly satisfied");
			this.result.repaint();
		}
		this.intersectionTime.setText(Double.toString(verificationResults.getIntersectionTime()));
		this.emptinessTime.setText(Double.toString(verificationResults.getEmptyTime()));
		this.constraintComputationTime.setText(Double.toString(verificationResults.getConstraintComputationTime()));
		this.verificationTime.setText(Double.toString(verificationResults.getTotalVerificationTime()));
		this.simplificationTime.setText(Double.toString(verificationResults.getSimplificationTime()));
		
		if(verificationResults.getResult()==0){
			this.verificationIntersectionPanel.highlightPath(verificationResults.getViolatingPath(), intersection, verificationResults.getViolatingPathTransitions());
		}
		
	}
	
	public void updateIntersection(DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersection, Transformer<INTERSECTIONSTATE, Point2D> positions){
		if(positions!=null){
			this.verificationIntersectionLayout.setInitializer(positions);
		}
		this.verificationIntersectionPanel.update(intersection);
	}
	
	public void updateModel(DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> model, Transformer<STATE, Point2D> positions,  DefaultTreeModel hierarchicalModelRefinement, DefaultTreeModel flatModelRefinement){
		this.verificationModelPanel.update(model);
	}
	
	public void hightLightConstraint(
			STATE state,
			Set<INTERSECTIONTRANSITION> intersectionTransitions) {
		this.verificationModelPanel.highLightState(state);
		
		if(intersectionTransitions!=null){
			this.verificationIntersectionPanel.highlightTransitions(intersectionTransitions, Color.GREEN);
		}	
	}
	public void doNothightLightConstraint() {
		this.verificationModelPanel.defaultTransformers();
		this.verificationIntersectionPanel.defaultTransformers();
	}	
}
