package it.polimi.controller.actions.file.loading;

import it.polimi.io.BAReader;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.automata.BAFactoryImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.BAFactory;
import it.polimi.view.ViewInterface;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class LoadSpecification<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition> 
extends LoadingAction<CONSTRAINEDELEMENT, STATE, TRANSITION>{

	/**
	 * 
	 */
	private BAReader<STATE, 
		TRANSITION,
		BA<STATE, TRANSITION>,
		BAFactory<STATE, TRANSITION, 
			BA<STATE, TRANSITION>>> baReader;
	
	
	public LoadSpecification(Object source, int id, String command) {
		super(source, id, command);
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition> 
	void  perform(
			ModelInterface<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, 
	INTERSECTIONTRANSITION> model,
	ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view) throws Exception {
		String file=this.getFile(new FileNameExtensionFilter("Buchi Automaton (*.ba)", "ba"));
		if(file!=null){
			
			this.baReader=new BAReader<STATE, 
					TRANSITION, 
					BA<STATE, TRANSITION>,
					BAFactory<STATE, TRANSITION, 
						BA< STATE, TRANSITION>>>(
								model.getSpecification().getTransitionFactory(),
								model.getSpecification().getStateFactory(), 
							new BAFactoryImpl<STATE, TRANSITION>(model.getSpecification().getTransitionFactory(), model.getSpecification().getStateFactory()),
							new BufferedReader(new FileReader(file)));
			
			model.setSpecification(this.baReader.readGraph());
			 
			
			view.updateSpecification(model.getSpecification(), this.baReader.getStatePositionTransformer());
		}
	}
}
