package it.polimi.controller.actions.file.saving;

import it.polimi.controller.actions.file.FileAction;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import javax.swing.JFileChooser;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

@SuppressWarnings("serial")
public abstract class SaveAction<
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition, 
TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>, 
INTERSECTIONSTATE extends IntersectionState<STATE>, 
INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE,INTERSECTIONSTATE>, 
INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>, 
INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE,INTERSECTIONTRANSITION>,
LAYOUT extends AbstractLayout<?, ?>>
		extends FileAction<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> {

	protected LAYOUT layout;
	
	
	public SaveAction(Object source, int id, String command, LAYOUT layout) {
		super(source, id, command);
		this.layout=layout;
		
	}

	protected String getFile(){
		JFileChooser saveFile = new JFileChooser();
		saveFile.setDialogTitle("Specify a file to save");
		saveFile.showSaveDialog(null);
		if(saveFile.getSelectedFile()!=null){
			return saveFile.getSelectedFile().getAbsolutePath();
		}
		return null;
	}
}
