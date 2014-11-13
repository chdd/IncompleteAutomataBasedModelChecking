package it.polimi.view.tabs;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.automaton.IntersectionAutomatonJPanel;

import java.awt.Color;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;

@SuppressWarnings("serial")
public class IntersectionTab<
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

	
	private AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION> intersectionLayout;
	private IntersectionAutomatonJPanel
	<CONSTRAINEDELEMENT, STATE, 
	STATEFACTORY,
	TRANSITION, 
	TRANSITIONFACTORY,
	INTERSECTIONSTATE, 
	INTERSECTIONSTATEFACTORY,
	INTERSECTIONTRANSITION,
	INTERSECTIONTRANSITIONFACTORY,DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>> intersectionPanel;

	private TextArea brzozowskiSystemArea;
	
	
	public IntersectionTab(ModelInterface<CONSTRAINEDELEMENT, STATE, 
			STATEFACTORY, TRANSITION, 
			TRANSITIONFACTORY, INTERSECTIONSTATE, 
			INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, 
			INTERSECTIONTRANSITIONFACTORY> model, 
			ActionListener l){
		
		super(false);
	       
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.intersectionLayout=new FRLayout<INTERSECTIONSTATE,INTERSECTIONTRANSITION>(model.getIntersection());
		 this.intersectionPanel=new IntersectionAutomatonJPanel
				 <CONSTRAINEDELEMENT, 
				 STATE, 
					STATEFACTORY,
					TRANSITION, 
					TRANSITIONFACTORY,
					INTERSECTIONSTATE, 
					INTERSECTIONSTATEFACTORY,
					INTERSECTIONTRANSITION,
					INTERSECTIONTRANSITIONFACTORY,DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>>(model.getIntersection(), l, this.intersectionLayout);
		 JPanel containerInt1=new JPanel();
		 containerInt1.setLayout(new BoxLayout(containerInt1,BoxLayout.Y_AXIS));
		 containerInt1.add(this.intersectionPanel);
			
		 intersectionPanel.setTranformingMode();
		 JPanel containerInt2=new JPanel();
		 containerInt2.setLayout(new BoxLayout(containerInt2,BoxLayout.Y_AXIS));
		 containerInt2.add(new JLabel("Brzozowski representation"));
		 this.brzozowskiSystemArea=new TextArea();
		 containerInt2.add(this.brzozowskiSystemArea);
		
		 this.add(containerInt1);
		 this.add(containerInt2);
	}
	
	public void updateIntersection(DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersection, Transformer<INTERSECTIONSTATE, Point2D> positions){
		if(positions!=null){
			this.intersectionLayout.setInitializer(positions);
		}
		this.intersectionPanel.update(intersection);
	}
	
	public void hightLightConstraint(
			STATE state,
			Set<INTERSECTIONTRANSITION> intersectionTransitions) {
		
		if(intersectionTransitions!=null){
			this.intersectionPanel.highlightTransitions(intersectionTransitions, Color.GREEN);
		}	
	}
	
	public void doNothightLightConstraint() {
		this.intersectionPanel.defaultTransformers();
	}
	
	public AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION> getIntersectionLayout(){
		return this.intersectionLayout;
	}
	public void setBrzozoski(String system){
		this.brzozowskiSystemArea.setText(system);
	}
}
