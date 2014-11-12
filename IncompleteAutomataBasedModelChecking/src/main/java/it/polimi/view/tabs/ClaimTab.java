package it.polimi.view.tabs;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.automaton.BuchiAutomatonJPanel;

import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;

@SuppressWarnings("serial")
public class ClaimTab<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>,
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>
	extends JPanel  {

	private BuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>>  claimTabClaimPanel;
	private AbstractLayout<STATE, TRANSITION> claimLayout;
	
	public ClaimTab(ModelInterface<CONSTRAINEDELEMENT, STATE, 
			STATEFACTORY, TRANSITION, 
			TRANSITIONFACTORY, INTERSECTIONSTATE, 
			INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, 
			INTERSECTIONTRANSITIONFACTORY> model, 
			ActionListener l){
		super(false);
        
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		 
		 JPanel containerClaimMenu=new JPanel();
		 containerClaimMenu.setLayout(new BoxLayout(containerClaimMenu, BoxLayout.X_AXIS));
		 
		 this.claimLayout=new FRLayout<STATE,TRANSITION>(model.getSpecification());
		 this.claimTabClaimPanel=new BuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>>(model.getSpecification(), l,  this.claimLayout, null);
		 
		 this.add(containerClaimMenu);
		 this.add(this.claimTabClaimPanel);
		
	}
	public void updateSpecification(DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> specification, Transformer<STATE, Point2D> positions){
		if(positions!=null){
			this.claimLayout.setInitializer(positions);
			//this.verificationClaimLayout.setInitializer(positions);
		}
		this.claimTabClaimPanel.update(specification);
		//this.verificationClaimPanel.update(specification);
		
	}
	
	public AbstractLayout<STATE, TRANSITION> getClaimLayout(){
		return this.claimLayout;
	}
	
	public void setTransformingMode(){
		this.claimTabClaimPanel.setTranformingMode();
	}
	
	public void setEditingMode(){
		this.claimTabClaimPanel.setEditingMode();		
	}
}
