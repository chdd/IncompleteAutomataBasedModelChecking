package it.polimi.io;

import it.polimi.model.impl.labeling.DNFFormulaImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactoryImpl;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IIntBA;
import it.polimi.model.interfaces.automata.IntBAFactory;
import it.polimi.model.interfaces.states.IntersectionStateFactory;
import it.polimi.model.interfaces.states.StateFactory;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.TransitionFactory;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.util.Map;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.EdgeMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class IntBAReader 
	<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends Transition,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition, 
	AUTOMATON extends IIntBA<STATE, TRANSITION,  INTERSECTIONSTATE, INTERSECTIONTRANSITION>,
	AUTOMATONFACTORY extends IntBAFactory<STATE, TRANSITION,  INTERSECTIONSTATE,  INTERSECTIONTRANSITION,  AUTOMATON>
	>
	extends IBAReader<CONSTRAINEDELEMENT, INTERSECTIONSTATE, INTERSECTIONTRANSITION, AUTOMATON, AUTOMATONFACTORY>{

	
	public IntBAReader(
			ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> transitionFactory,
			IntersectionStateFactory<STATE, INTERSECTIONSTATE> stateFactory, 
			AUTOMATONFACTORY automatonFactory, 
			BufferedReader fileReader) {
		super(transitionFactory, stateFactory, automatonFactory, fileReader);
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Transformer<EdgeMetadata, INTERSECTIONTRANSITION> getTransitionTransformer(TransitionFactory<INTERSECTIONTRANSITION> transitionFactory){
		if(transitionFactory==null){
			throw new NullPointerException("The stateFactory cannot be null");
		}
		return  new BAMetadataToTransitionTransformer((ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>) transitionFactory, this.ba); 
	}
	
	/**
	 * returns the {@link Transformer} that given a {@link State} returns its {@link NodeMetadata}
	 * @param stateFactory is the {@link StateFactoryImpl}
	 * @return the {@link Transformer} that given a {@link State} returns its {@link NodeMetadata}
	 * @throws NullPointerException if the {@link StateFactoryImpl} is null
	 */
	@Override
	protected Transformer<NodeMetadata, INTERSECTIONSTATE> getStateTransformer(StateFactory<INTERSECTIONSTATE> stateFactory){
		if(stateFactory==null){
			throw new NullPointerException("The stateFactory cannot be null");
		}
		return new IntBAMetadataStateTransformer(this.ba, this.statesandlocations, stateFactory); 
	}
	
	/**
	 * creates a new IBAMetadataStateTransformer
	 * @param a is the {@link DrawableIBA} that must be updated by the {@link Transformer}
	 * @param stateFactory contains the {@link Factory} which creates the {@link State} of the {@link DrawableIBA}
	 * @throws NullPointerException if the {@link DrawableIBA} or the {@link StateFactoryImpl} is null
	 */
	protected class IntBAMetadataStateTransformer extends BAMetadataStateTransformer{

		/**
		 * creates a new IBAMetadataStateTransformer
		 * @param a is the {@link DrawableIBA} that must be updated by the {@link Transformer}
		 * @param stateFactory contains the {@link Factory} which creates the {@link State} of the {@link DrawableIBA}
		 * @throws NullPointerException if the {@link DrawableIBA} or the {@link StateFactoryImpl} is null
		 */
		public IntBAMetadataStateTransformer(AUTOMATON a, Map<INTERSECTIONSTATE, Point2D> statesandlocations, StateFactory<INTERSECTIONSTATE> stateFactory) {
			super(a, statesandlocations, stateFactory);
		}

		/**
		 * transforms the {@link NodeMetadata} into the corresponding {@link State}
		 * @param input contains the {@link NodeMetadata} to be converted into the corresponding {@link State}
		 * @throws NullPointerException if the input is null
		 */
		@Override
		public INTERSECTIONSTATE transform(NodeMetadata input) {
			INTERSECTIONSTATE s=super.transform(input);
			
			if(Boolean.parseBoolean(input.getProperty("mixed"))){
				this.a.addMixedState(s);
			}
			return s;
		}
	}
	
	/**
	 * contains the {@link Transformer} that given the {@link EdgeMetadata} returns the corresponding {@link Transition}
	 * @author claudiomenghi
	 *
	 */
	protected class BAMetadataToTransitionTransformer implements
			Transformer<EdgeMetadata, INTERSECTIONTRANSITION> {
		/**
		 * is the {@link Factory} which is used to create the {@link Transition}
		 */
		protected ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> transitionFactory;
		protected StateFactoryImpl stateFactory;
		protected AUTOMATON a;

		/**
		 * creates a new {@link Transformer} that transforms {@link EdgeMetadata} into {@link Transition}
		 * @param transitionFactory is the {@link Factory} which is used to create the {@link Transition}
		 * @throws NullPointerException is the transitionFactory is null
		 */
		public BAMetadataToTransitionTransformer(
				ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> transitionFactory,
				AUTOMATON a) {
			if(transitionFactory==null){
				throw new NullPointerException("The transition factory cannot be null");
			}
			
			this.transitionFactory = transitionFactory;
			this.stateFactory=new StateFactoryImpl();
			this.a=a;
		}

		/**
		 * transforms the {@link EdgeMetadata} into the corresponding {@link Transition}
		 * @param input contains the {@link EdgeMetadata} to be converted
		 * @throws NullPointerException if the {@link EdgeMetadata} to be converted is null
		 */
		@SuppressWarnings("unchecked")
		@Override
		public INTERSECTIONTRANSITION transform(EdgeMetadata input) {
			if(input==null){
				throw new NullPointerException("The EdgeMetadata to be converted cannot be null");
			}
			if(input.getProperty("cstateId")==null){
				return this.transitionFactory.create(
						Integer.parseInt(input.getId()),
						DNFFormulaImpl.<CONSTRAINEDELEMENT>loadFromString(input.getProperty("DNFFormula")));
			}
			else
			{
				return this.transitionFactory.create(
						Integer.parseInt(input.getId()),
						DNFFormulaImpl.<CONSTRAINEDELEMENT>loadFromString(input.getProperty("DNFFormula")),
						(CONSTRAINEDELEMENT)this.stateFactory.create(
								input.getProperty("cstateName"), 
								Integer.parseInt(input.getProperty("cstateId"))));
			}
			
		}
	}

	
}
