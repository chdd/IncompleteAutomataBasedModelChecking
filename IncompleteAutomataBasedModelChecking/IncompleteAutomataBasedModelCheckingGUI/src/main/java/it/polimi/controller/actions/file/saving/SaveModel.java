package it.polimi.controller.actions.file.saving;

import it.polimi.automata.io.iba.IBAWriter;
import it.polimi.model.ModelInterface;
import it.polimi.model.RefinementNode;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.view.ViewInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map.Entry;

import javax.swing.tree.DefaultMutableTreeNode;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

@SuppressWarnings("serial")
public class SaveModel<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition, 
LAYOUT extends AbstractLayout<STATE, TRANSITION>>
	extends SaveAction<CONSTRAINEDELEMENT, STATE,  TRANSITION, LAYOUT> {

	private IBAWriter<STATE, 
	TRANSITION,
	IBA<STATE, TRANSITION>> ibaToFile;
	
	public SaveModel(Object source, int id, String command, LAYOUT layout) {
		super(source, id, command, layout);
		this.ibaToFile=new IBAWriter<STATE, 
				TRANSITION,
				IBA<STATE, TRANSITION>>(layout);
		
	 }

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition>void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, 
			INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view) throws Exception {
		String filePath=this.getDirectory("model.iba");
		if(filePath!=null){
			for(Entry<STATE, DefaultMutableTreeNode> entry: model.getStateRefinementMap().entrySet()){
				
				@SuppressWarnings("unchecked")
				RefinementNode<
						STATE, 
						TRANSITION> refinementNode= (RefinementNode<
								STATE, 
								TRANSITION>)entry.getValue().getUserObject();
				this.ibaToFile.save(refinementNode.getAutomaton()
						, new PrintWriter(new BufferedWriter(new FileWriter(filePath+"/"+refinementNode.getState().getId()+"-"+refinementNode.getState().getName()+".iba"))));
			}
		}
		else{
			throw new NullPointerException("The directory cannot be null");
		}
	}

}
