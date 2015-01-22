package it.polimi.controller.actions.file.saving;

import it.polimi.automata.IntersectionBA;
import it.polimi.iotmp.IntBAWriter;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.filechooser.FileNameExtensionFilter;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

@SuppressWarnings("serial")
public class SaveIntersection<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition, 
INTERSECTIONS extends IntersectionState<STATE>,
INTERSECTIONT extends Transition,
LAYOUT extends AbstractLayout<INTERSECTIONS, INTERSECTIONT>>
extends SaveAction<CONSTRAINEDELEMENT, STATE,  TRANSITION, LAYOUT> {


	public SaveIntersection(Object source, int id, String command, LAYOUT layout) {
		super(source, id, command, layout);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition> 
	void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view)
			throws Exception {
		IntBAWriter<CONSTRAINEDELEMENT,
		STATE, 
		TRANSITION, 
		INTERSECTIONSTATE, 
		INTERSECTIONTRANSITION, 
		IntersectionBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>> intBaWriter;

		
		String filePath=this.getFile(new FileNameExtensionFilter("Intersection Automaton (*.int)", "int"));
		if(filePath!=null){
			intBaWriter=new IntBAWriter<CONSTRAINEDELEMENT,
					STATE, 
					TRANSITION, 
					INTERSECTIONSTATE, 
					INTERSECTIONTRANSITION,
					IntersectionBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>>((AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION>)  this.layout);
		
			intBaWriter.save(model.getIntersection(), new PrintWriter(new BufferedWriter(new FileWriter(filePath))));
			
		}
	}
}
