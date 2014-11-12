package it.polimi.controller.actions.file.saving;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map.Entry;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import it.polimi.io.IBAWriter;
import it.polimi.model.ModelInterface;
import it.polimi.model.RefinementNode;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.ViewInterface;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

@SuppressWarnings("serial")
public class SaveModel<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>, 
LAYOUT extends AbstractLayout<STATE, TRANSITION>>
	extends SaveAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, LAYOUT> {

	private IBAWriter<CONSTRAINEDELEMENT, STATE, 
	TRANSITION,
	TRANSITIONFACTORY, 
	DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>> ibaToFile;
	
	public SaveModel(Object source, int id, String command, LAYOUT layout) {
		super(source, id, command, layout);
		this.ibaToFile=new IBAWriter<CONSTRAINEDELEMENT,STATE, 
				TRANSITION,
				TRANSITIONFACTORY, 
				DrawableIBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>>(layout);
		
	 }

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
			INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, 
			TRANSITIONFACTORY,
			INTERSECTIONTRANSITIONFACTORY> view) throws Exception {
		String filePath=this.getDirectory("model.iba");
		if(filePath!=null){
			for(Entry<STATE, DefaultMutableTreeNode> entry: model.getStateRefinementMap().entrySet()){
				
				RefinementNode<CONSTRAINEDELEMENT,
						STATE, 
						TRANSITION, 
						TRANSITIONFACTORY> refinementNode= (RefinementNode<CONSTRAINEDELEMENT,
								STATE, 
								TRANSITION, 
								TRANSITIONFACTORY>)entry.getValue().getUserObject();
				System.out.println(filePath);
				this.ibaToFile.save(refinementNode.getAutomaton()
						, new PrintWriter(new BufferedWriter(new FileWriter(filePath+"/"+refinementNode.getState().getId()+"-"+refinementNode.getState().getName()+".iba"))));
			}
		}
		else{
			throw new NullPointerException("The directory cannot be null");
		}
	}

}
