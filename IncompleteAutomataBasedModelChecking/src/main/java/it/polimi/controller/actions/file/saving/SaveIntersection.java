package it.polimi.controller.actions.file.saving;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.filechooser.FileNameExtensionFilter;

import it.polimi.io.IntBAWriter;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.ViewInterface;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

@SuppressWarnings("serial")
public class SaveIntersection<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
INTERSECTIONS extends IntersectionState<STATE>,
INTERSECTIONT extends LabelledTransition<CONSTRAINEDELEMENT>,
LAYOUT extends AbstractLayout<INTERSECTIONS, INTERSECTIONT>>
extends SaveAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, LAYOUT> {


	public SaveIntersection(Object source, int id, String command, LAYOUT layout) {
		super(source, id, command, layout);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>, 
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>> 
	void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY> view)
			throws Exception {
		IntBAWriter<CONSTRAINEDELEMENT,
		STATE, 
		TRANSITION, 
		INTERSECTIONSTATE, 
		INTERSECTIONTRANSITION, 
		TRANSITIONFACTORY, 
		INTERSECTIONTRANSITIONFACTORY,
		DrawableIntBA<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>> intBaWriter;

		
		String filePath=this.getFile(new FileNameExtensionFilter("Intersection Automaton (*.int)", "int"));
		if(filePath!=null){
			intBaWriter=new IntBAWriter<CONSTRAINEDELEMENT,
					STATE, 
					TRANSITION, 
					INTERSECTIONSTATE, 
					INTERSECTIONTRANSITION, 
					TRANSITIONFACTORY, 
					INTERSECTIONTRANSITIONFACTORY,
					DrawableIntBA<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>>((AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION>)  this.layout);
		
			intBaWriter.save(model.getIntersection(), new PrintWriter(new BufferedWriter(new FileWriter(filePath))));
			
		}
	}
}
