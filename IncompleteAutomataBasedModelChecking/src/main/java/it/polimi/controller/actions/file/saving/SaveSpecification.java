package it.polimi.controller.actions.file.saving;

import it.polimi.io.BAWriter;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.view.ViewInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.filechooser.FileNameExtensionFilter;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

@SuppressWarnings("serial")
public class SaveSpecification<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition,
LAYOUT extends AbstractLayout<STATE, TRANSITION>>
	extends SaveAction<CONSTRAINEDELEMENT, STATE,  TRANSITION,  LAYOUT>{

	private BAWriter< STATE, 
	TRANSITION, BA< STATE, TRANSITION>> baWriter;
	
	public SaveSpecification(Object source, int id, String command, LAYOUT layout) {
		super(source, id, command, layout);
		this.baWriter=new BAWriter<STATE, 
				TRANSITION,
				BA<STATE, TRANSITION>>(layout);
		
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition> void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, 
			INTERSECTIONTRANSITION> model, 
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view) throws Exception {
		String filePath=this.getFile(new FileNameExtensionFilter("Buchi Automaton (*.ba)", "ba"));
		
		if(filePath!=null){
			this.baWriter.save(model.getSpecification(), new PrintWriter(new BufferedWriter(new FileWriter(filePath))));
		}
	}


}
