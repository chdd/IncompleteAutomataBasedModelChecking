package it.polimi.view.tabs;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;
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
	TRANSITION extends Transition, 
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition>
	extends JPanel  {

	private BuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE, TRANSITION, BA<STATE,TRANSITION>>  claimTabClaimPanel;
	private AbstractLayout<STATE, TRANSITION> claimLayout;
	
	public ClaimTab(ModelInterface<CONSTRAINEDELEMENT, STATE, 
			 TRANSITION, 
			 INTERSECTIONSTATE, 
			 INTERSECTIONTRANSITION> model, 
			ActionListener l){
		super(false);
        
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		 
		 JPanel containerClaimMenu=new JPanel();
		 containerClaimMenu.setLayout(new BoxLayout(containerClaimMenu, BoxLayout.X_AXIS));
		 
		 this.claimLayout=new FRLayout<STATE,TRANSITION>(model.getSpecification().getGraph());
		 this.claimTabClaimPanel=new BuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE, TRANSITION,  BA<STATE,TRANSITION>>(model.getSpecification(), l,  this.claimLayout, null);
		 
		 this.add(containerClaimMenu);
		 this.add(this.claimTabClaimPanel);
		
	}
	public void updateSpecification(BA<STATE, TRANSITION> specification, Transformer<STATE, Point2D> positions){
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
