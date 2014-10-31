package it.polimi.controller.actions.file.loading;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.brzozowski.Brzozowski;
import it.polimi.view.ViewInterface;

import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;

@SuppressWarnings("serial")
public class LoadIntersection<
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition, 
TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>, 
INTERSECTIONSTATE extends IntersectionState<STATE>, 
INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE,INTERSECTIONSTATE>, 
INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>, 
INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE,INTERSECTIONTRANSITION>> 
extends LoadingAction<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>  {

	public LoadIntersection(Object source, int id, String command) {
		super(source, id, command);
	}

	@Override
	public void perform(ModelInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
			INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
			ViewInterface<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, 
			TRANSITIONFACTORY,
			INTERSECTIONTRANSITIONFACTORY> view) throws Exception {
		String file=this.getFile();
		if(file!=null){
			Transformer<INTERSECTIONSTATE, Point2D> positions=model.loadIntersection(file);
			view.updateIntersection(model.getIntersection(),positions);
			view.setBrzozoski(new Brzozowski<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>(model.getIntersection()).getConstraintmatrix());
			
			
		}
	}
}

