package it.polimi.controller.actions.file.loading;

import it.polimi.io.IBAReader;
import it.polimi.model.ModelInterface;
import it.polimi.model.RefinementNode;
import it.polimi.model.impl.automata.IBAFactoryImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.automata.IBAFactory;
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
TRANSITION extends Transition> 
	extends LoadingAction<CONSTRAINEDELEMENT, STATE, TRANSITION>{

	private IBAReader<STATE, 
	TRANSITION,
	IBA<STATE, TRANSITION>,
	IBAFactory<STATE, TRANSITION,
		IBA<STATE, TRANSITION>>> ibaReader;
	
	public LoadModel(Object source, int id, String command) {
		super(source, id, command);
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition>  void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, 
			INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view) throws Exception {
		String file=this.getFile(new FileNameExtensionFilter("Incomplete Buchi Automaton (*.iba)", "iba"));
		if(file!=null){
			
			this.ibaReader=new IBAReader<
					STATE, 
					TRANSITION,
					IBA<STATE, TRANSITION>,
					IBAFactory<STATE, TRANSITION, 
					IBA<STATE, TRANSITION>>>(
							model.getModel().getTransitionFactory(),
							model.getModel().getStateFactory(), 
							new IBAFactoryImpl<STATE, TRANSITION>(model.getModel().getTransitionFactory(), model.getModel().getStateFactory()),
							new BufferedReader(new FileReader(file)));
			
			model.resetModel(model.getModel().getStateFactory().create("Model"), this.ibaReader.readGraph());
			for(STATE s: model.getModel().getTransparentStates()){
				this.recLoading(s, file, model, (DefaultMutableTreeNode) model.getModelRefinementHierarchy().getRoot());
			}
			
		}
	}
	
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition>
	void  recLoading(STATE transparentState, 
			String path,
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, 
			INTERSECTIONTRANSITION> model,
			DefaultMutableTreeNode parent) throws FileNotFoundException, GraphIOException{
		
		String newFilePath=path.replace(path.substring(path.lastIndexOf("/")+1), 
				transparentState.getId()+"-"+transparentState.getName()+".iba");
		this.ibaReader=new IBAReader<
				STATE, 
				TRANSITION,
				IBA<STATE, TRANSITION>,
				IBAFactory<STATE, TRANSITION, 
				IBA<STATE, TRANSITION>>>(
						model.getModel().getTransitionFactory(),
						model.getModel().getStateFactory(), 
						new IBAFactoryImpl<STATE, TRANSITION>(model.getModel().getTransitionFactory(), model.getModel().getStateFactory()),
						new BufferedReader(new FileReader
								(newFilePath)));
		
		IBA<STATE, TRANSITION> childIba=this.ibaReader.readGraph();
		DefaultMutableTreeNode child=new DefaultMutableTreeNode(
				new RefinementNode<
				STATE, 
				TRANSITION>(transparentState, childIba));
		
		model.getStateRefinementMap().put(transparentState, child);
		model.getModelRefinementHierarchy().insertNodeInto(child, 
				parent, 
				model.getModelRefinementHierarchy().getChildCount(parent));
		for(STATE s: childIba.getTransparentStates()){
			this.recLoading(s, path, model, child);
		}
	
	
	}
}
