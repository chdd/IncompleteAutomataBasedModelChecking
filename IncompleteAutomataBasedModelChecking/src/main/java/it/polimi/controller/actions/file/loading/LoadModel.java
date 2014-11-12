package it.polimi.controller.actions.file.loading;

import it.polimi.io.IBAReader;
import it.polimi.model.ModelInterface;
import it.polimi.model.RefinementNode;
import it.polimi.model.impl.automata.IBAFactoryImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IBAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.ViewInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import edu.uci.ics.jung.io.GraphIOException;

@SuppressWarnings("serial")
public class LoadModel<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>> 
	extends LoadingAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>{

	private IBAReader<CONSTRAINEDELEMENT, STATE, 
	TRANSITION,
	TRANSITIONFACTORY, 
	STATEFACTORY,
	DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>,
	IBAFactory<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY,
		DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>>> ibaReader;
	
	public LoadModel(Object source, int id, String command) {
		super(source, id, command);
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>  void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
			INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, 
			TRANSITIONFACTORY,
			INTERSECTIONTRANSITIONFACTORY> view) throws Exception {
		String file=this.getFile(new FileNameExtensionFilter("Incomplete Buchi Automaton (*.iba)", "iba"));
		if(file!=null){
			
			this.ibaReader=new IBAReader<
					CONSTRAINEDELEMENT,
					STATE, 
					TRANSITION,
					TRANSITIONFACTORY, 
					STATEFACTORY,
					DrawableIBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>,
					IBAFactory<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY, 
					DrawableIBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>>>(
							model.getModelTransitionFactory(),
							model.getModelStateFactory(), 
							new IBAFactoryImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>(model.getModelTransitionFactory()),
							new BufferedReader(new FileReader(file)));
			
			model.resetModel(model.getModelStateFactory().create("Model"), this.ibaReader.readGraph());
			for(STATE s: model.getModel().getTransparentStates()){
				this.recLoading(s, file, model, (DefaultMutableTreeNode) model.getModelRefinementHierarchy().getRoot());
			}
			
		}
	}
	
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>
	void  recLoading(STATE transparentState, 
			String path,
			ModelInterface<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
			INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
			DefaultMutableTreeNode parent) throws FileNotFoundException, GraphIOException{
		
		String newFilePath=path.replace(path.substring(path.lastIndexOf("/")+1), 
				transparentState.getId()+"-"+transparentState.getName()+".iba");
		this.ibaReader=new IBAReader<
				CONSTRAINEDELEMENT,
				STATE, 
				TRANSITION,
				TRANSITIONFACTORY, 
				STATEFACTORY,
				DrawableIBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>,
				IBAFactory<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY, 
				DrawableIBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>>>(
						model.getModelTransitionFactory(),
						model.getModelStateFactory(), 
						new IBAFactoryImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>(model.getModelTransitionFactory()),
						new BufferedReader(new FileReader
								(newFilePath)));
		
		DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> childIba=this.ibaReader.readGraph();
		DefaultMutableTreeNode child=new DefaultMutableTreeNode(
				new RefinementNode<
				CONSTRAINEDELEMENT,
				STATE, 
				TRANSITION, 
				TRANSITIONFACTORY>(transparentState, childIba));
		
		model.getStateRefinementMap().put(transparentState, child);
		model.getModelRefinementHierarchy().insertNodeInto(child, 
				parent, 
				model.getModelRefinementHierarchy().getChildCount(parent));
		for(STATE s: childIba.getTransparentStates()){
			this.recLoading(s, path, model, child);
		}
	
	
	}
}
