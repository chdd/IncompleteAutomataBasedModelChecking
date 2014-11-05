package it.polimi.controller.actions.file.loading;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.ViewInterface;

import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;

@SuppressWarnings("serial")
public class LoadSpecification<
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition, 
TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>> 
extends LoadingAction<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>{

	public LoadSpecification(Object source, int id, String command) {
		super(source, id, command);
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends LabelledTransition,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>>  void  perform(ModelInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
	INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
	ViewInterface<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, 
	TRANSITIONFACTORY,
	INTERSECTIONTRANSITIONFACTORY> view) throws Exception {
		String file=this.getFile();
		if(file!=null){
			Transformer<STATE, Point2D> positions=model.changeSpecification(file);
			view.updateSpecification(model.getSpecification(), positions);
		}
	}
}
