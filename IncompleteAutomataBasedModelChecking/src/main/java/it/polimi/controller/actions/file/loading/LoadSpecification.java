package it.polimi.controller.actions.file.loading;

import it.polimi.io.BAReader;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.automata.BAFactoryImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.BAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.ViewInterface;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class LoadSpecification<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>> 
extends LoadingAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>{

	/**
	 * 
	 */
	private BAReader<CONSTRAINEDELEMENT, STATE, 
		TRANSITION,
		TRANSITIONFACTORY, 
		STATEFACTORY,
		DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>,
		BAFactory<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY, 
			DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>>> baReader;
	
	
	public LoadSpecification(Object source, int id, String command) {
		super(source, id, command);
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>> 
	void  perform(
			ModelInterface<CONSTRAINEDELEMENT,STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
	INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
	ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, 
	TRANSITIONFACTORY,
	INTERSECTIONTRANSITIONFACTORY> view) throws Exception {
		String file=this.getFile(new FileNameExtensionFilter("Buchi Automaton (*.ba)", "ba"));
		if(file!=null){
			
			this.baReader=new BAReader<CONSTRAINEDELEMENT,STATE, 
					TRANSITION,
					TRANSITIONFACTORY, 
					STATEFACTORY,
					DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>,
					BAFactory<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY, 
						DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>>>(
								model.getSpecificationTransitionFactory(),
								model.getSpecificationStateFactory(), 
							new BAFactoryImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>(model.getSpecificationTransitionFactory()),
							new BufferedReader(new FileReader(file)));
			
			model.setSpecification(this.baReader.readGraph());
			 
			
			view.updateSpecification(model.getSpecification(), this.baReader.getStatePositionTransformer());
		}
	}
}
