package it.polimi.view.tabs;

import it.polimi.Constants;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IBA;
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

/**
 * contains the {@link JPanel} which displays the model under development
 * @author claudiomenghi
 *
 * @param <CONSTRAINEDELEMENT>
 * @param <STATE>
 * @param <STATEFACTORY>
 * @param <TRANSITION>
 * @param <TRANSITIONFACTORY>
 * @param <INTERSECTIONSTATE>
 * @param <INTERSECTIONSTATEFACTORY>
 * @param <INTERSECTIONTRANSITION>
 * @param <INTERSECTIONTRANSITIONFACTORY>
 */
@SuppressWarnings("serial")
public class ModelTab<
		CONSTRAINEDELEMENT extends State,
		STATE extends State, 
		TRANSITION extends Transition, 
		INTERSECTIONSTATE extends IntersectionState<STATE>, 
		INTERSECTIONTRANSITION extends Transition>
	extends JPanel {

	/**
	 * contains the {@link DrawableIBA} to be displayed
	 */
	private IncompleteBuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE, TRANSITION, IBA<STATE,TRANSITION>> modelTabmodel;
	
	/**
	 * contains the {@link AbstractLayout} to be used when the model is displayed
	 */
	private AbstractLayout<STATE, TRANSITION> modelLayout;
	
	private RefinementTree<CONSTRAINEDELEMENT, 
	 STATE, 
	 TRANSITION> tree;
	
	private JPanel containerModelMenu;
	private JPanel jtreePanel;

	/**
	 * creates a new {@link ModelTab}
	 * @param model is the model to be displayed in the modelTab
	 * @param l the actionListener that is notified every time an action is performed on the model
	 * @param d the dimension of the {@link ModelTab}
	 * @throws when the model or the action listener or the dimension is null
	 */
	public ModelTab(ModelInterface<CONSTRAINEDELEMENT, STATE, 
			 TRANSITION, 
			 INTERSECTIONSTATE, 
			 INTERSECTIONTRANSITION> model, 
			ActionListener l,
			Dimension d){
		super(false);
		if(model==null){
			throw new NullPointerException("The model cannot be null");
		}
		if(l==null){
			throw new NullPointerException("The action listener cannot be null");
		}
		if(d==null){
			throw new NullPointerException("The dimension o the ModelTab cannot be null");
		}
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
				 TRANSITION>(new Dimension((int) (d.width/Constants.JTreeXProportion), d.height), 
						 model.getModelRefinementHierarchy(), 
						 l); 
		 	jtreePanel.add(tree);
		 	containerModelMenu.add(jtreePanel);
		 this.modelLayout=new KKLayout<STATE,TRANSITION>(model.getModel().getGraph());
		 this.modelTabmodel=new IncompleteBuchiAutomatonJPanel<
				 	CONSTRAINEDELEMENT, 
				 	STATE,
				 	TRANSITION, 
				 	IBA<STATE,TRANSITION>>
		 			(model.getModel(), l, this.modelLayout,
		 					this.tree);
		 containerModelMenu.add(this.modelTabmodel);
		 this.add(containerModelMenu);
	}
	public void updateModel(IBA<STATE, TRANSITION> model, Transformer<STATE, Point2D> positions,  
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
	
	public void setEditingMode(){
		this.modelTabmodel.setEditingMode();		
	}
	
	public void highLightState(STATE s){
		this.modelTabmodel.highLightState(s);
	}
	public void doNothightLightConstraint() {
		this.modelTabmodel.defaultTransformers();
	}
}
