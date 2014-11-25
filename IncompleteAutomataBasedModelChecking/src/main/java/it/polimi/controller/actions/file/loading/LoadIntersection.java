package it.polimi.controller.actions.file.loading;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.modelchecker.brzozowski.Brzozowski;
import it.polimi.view.ViewInterface;

import java.awt.geom.Point2D;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.collections15.Transformer;

@SuppressWarnings("serial")
public class LoadIntersection<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition> 
extends LoadingAction<CONSTRAINEDELEMENT, STATE, TRANSITION>  {

	public LoadIntersection(Object source, int id, String command) {
		super(source, id, command);
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition>  void  perform(ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, 
			INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view) throws Exception {
		String file=this.getFile(new FileNameExtensionFilter("Intersection Automaton (*.int)", "int"));
		if(file!=null){
			Transformer<INTERSECTIONSTATE, Point2D> positions=model.loadIntersection(file);
			view.updateIntersection(model.getIntersection(),positions);
			view.setBrzozoski(new Brzozowski<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>(model.getIntersection()).getConstraintmatrix());
			
			
		}
	}
}

