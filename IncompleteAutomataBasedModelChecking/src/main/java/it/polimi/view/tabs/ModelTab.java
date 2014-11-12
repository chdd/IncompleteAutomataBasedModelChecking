package it.polimi.view.tabs;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.Constants;
import it.polimi.view.automaton.IncompleteBuchiAutomatonJPanel;
import it.polimi.view.automaton.RefinementTree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;

@SuppressWarnings("serial")
public class ModelTab<
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

	private IncompleteBuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableIBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>> modelTabmodel;
	private AbstractLayout<STATE, TRANSITION> modelLayout;
	
	private RefinementTree<
	 CONSTRAINEDELEMENT,
	 STATE, 
	 STATEFACTORY,
	 TRANSITION, 
	 TRANSITIONFACTORY> tree;
	
	private JPanel containerModelMenu;
	private JPanel jtreePanel;

	
	public ModelTab(ModelInterface<CONSTRAINEDELEMENT, STATE, 
			STATEFACTORY, TRANSITION, 
			TRANSITIONFACTORY, INTERSECTIONSTATE, 
			INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, 
			INTERSECTIONTRANSITIONFACTORY> model, 
			ActionListener l,
			Dimension d){
		super(false);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.containerModelMenu=new JPanel();
		this.containerModelMenu.setBackground(Color.white);
		this.containerModelMenu.setLayout(new BoxLayout(containerModelMenu, BoxLayout.X_AXIS));
		 
		 this.add(containerModelMenu);
		 jtreePanel=new JPanel();
		 jtreePanel.setLayout(new BoxLayout(jtreePanel, BoxLayout.X_AXIS));
		 jtreePanel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createRaisedBevelBorder(), 
					BorderFactory.createLoweredBevelBorder()));
		 jtreePanel.setBackground(Color.white);
		 
		 this.tree=new RefinementTree<
				 CONSTRAINEDELEMENT,
				 STATE, 
				 STATEFACTORY,
				 TRANSITION, 
				 TRANSITIONFACTORY>(new Dimension((int) (d.width/Constants.JTreeXProportion), d.height), 
						 model.getModelRefinementHierarchy(), 
						 l); 
		 	jtreePanel.add(tree);
		 	containerModelMenu.add(jtreePanel);
		 this.modelLayout=new KKLayout<STATE,TRANSITION>(model.getModel());
		 this.modelTabmodel=new IncompleteBuchiAutomatonJPanel<
				 	CONSTRAINEDELEMENT, 
				 	STATE,
				 	STATEFACTORY, 
				 	TRANSITION, 
				 	TRANSITIONFACTORY, 
				 	DrawableIBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>>
		 			(model.getModel(), l, this.modelLayout,
		 					this.tree);
		 containerModelMenu.add(this.modelTabmodel);
		 this.add(containerModelMenu);
	}
	public void updateModel(DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> model, Transformer<STATE, Point2D> positions,  
			DefaultTreeModel hierarchicalModelRefinement, 
			DefaultTreeModel flatModelRefinement, 
			boolean flatten,
			boolean reload){
		if(positions!=null){
			this.modelLayout.setInitializer(positions);
			//this.verificationModelLayout.setInitializer(positions);
		}
		this.containerModelMenu.repaint();
		if(!reload && (flatten && !this.tree.getTree().getModel().getRoot().equals(flatModelRefinement.getRoot()))){
			
			this.tree.changeModel(flatModelRefinement);
		}
		else{
			if(reload || ((!flatten) && (!this.tree.getTree().getModel().getRoot().equals(hierarchicalModelRefinement.getRoot())))){
				this.tree.changeModel(hierarchicalModelRefinement);
				reload=false;
			}
		}
		
		this.modelTabmodel.update(model);
	}
	
	public AbstractLayout<STATE, TRANSITION> getModelLayout(){
		return this.modelLayout;
	}
	
	public void setTransformingMode(){
		this.modelTabmodel.setTranformingMode();
	}
	
	public void setDefaultTransformer(){
		this.modelTabmodel.defaultTransformers();		
	}
	
	public void highLightState(STATE s){
		this.modelTabmodel.highLightState(s);
	}
}
