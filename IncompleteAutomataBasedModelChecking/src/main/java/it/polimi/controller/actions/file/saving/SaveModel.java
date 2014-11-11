package it.polimi.controller.actions.file.saving;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.filechooser.FileNameExtensionFilter;

import it.polimi.io.IBAWriter;
import it.polimi.model.ModelInterface;
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
		String filePath=this.getFile(new FileNameExtensionFilter("Incomplete Buchi Automaton (*.iba)", "iba"));
		if(filePath!=null){
			this.ibaToFile.save(model.getModel(), new PrintWriter(new BufferedWriter(new FileWriter(filePath))));
		}
	}

}
