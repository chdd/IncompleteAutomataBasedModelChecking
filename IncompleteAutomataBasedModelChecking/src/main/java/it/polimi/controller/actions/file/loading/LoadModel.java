package it.polimi.controller.actions.file.loading;

import it.polimi.io.IBAReader;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.automata.IBAFactoryImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IBAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.ViewInterface;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.collections15.Transformer;

@SuppressWarnings("serial")
public class LoadModel<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>> 
	extends LoadingAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>{

	private IBAReader<CONSTRAINEDELEMENT, STATE, 
	TRANSITION,
	TRANSITIONFACTORY, 
	STATEFACTORY,
	DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>,
	IBAFactory<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY,
		DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>>> ibaReader;
	
	public LoadModel(Object source, int id, String command) {
		super(source, id, command);
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>  void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
			INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, 
			TRANSITIONFACTORY,
			INTERSECTIONTRANSITIONFACTORY> view) throws Exception {
		String file=this.getFile(new FileNameExtensionFilter("Incomplete Buchi Automaton (*.iba)", "iba"));
		if(file!=null){
			
			this.ibaReader=new IBAReader<
					CONSTRAINEDELEMENT,
					STATE, 
					TRANSITION,
					TRANSITIONFACTORY, 
					STATEFACTORY,
					DrawableIBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>,
					IBAFactory<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY, 
					DrawableIBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>>>(
							model.getModelTransitionFactory(),
							model.getModelStateFactory(), 
							new IBAFactoryImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>(model.getModelTransitionFactory()),
							new BufferedReader(new FileReader(file)));
			model.setModel(this.ibaReader.readGraph());
			Transformer<STATE, Point2D> positions=this.ibaReader.getStatePositionTransformer();
			view.updateModel(model.getModel(), positions, model.getModelRefinement(), model.getflattenModelRefinement());
				
		}
	}
}
