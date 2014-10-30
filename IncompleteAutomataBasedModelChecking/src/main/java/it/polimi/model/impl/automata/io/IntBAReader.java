package it.polimi.model.impl.automata.io;

import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IntBAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.io.BufferedReader;

import org.antlr.v4.runtime.atn.Transition;
import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.EdgeMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class IntBAReader 
	<STATE extends State, 
	TRANSITION extends LabelledTransition,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE,INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>, 
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>,
	AUTOMATON extends DrawableIntBA<STATE, TRANSITION,  INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>,
	AUTOMATONFACTORY extends IntBAFactory<STATE, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY, AUTOMATON>
	>
	extends IBAReader<INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY, INTERSECTIONSTATEFACTORY, AUTOMATON, AUTOMATONFACTORY>{

	
	public IntBAReader(
			INTERSECTIONTRANSITIONFACTORY transitionFactory,
			INTERSECTIONSTATEFACTORY intersectionStateFactory,
			AUTOMATONFACTORY automatonFactory, 
			BufferedReader fileReader) {
		super(transitionFactory, intersectionStateFactory, automatonFactory, fileReader);
	
	}
	
	@Override
	protected Transformer<EdgeMetadata, INTERSECTIONTRANSITION> getTransitionTransformer(INTERSECTIONTRANSITIONFACTORY transitionFactory){
		if(transitionFactory==null){
			throw new NullPointerException("The stateFactory cannot be null");
		}
		return  new BAMetadataToTransitionTransformer(transitionFactory, this.ba); 
	}
	
	/**
	 * returns the {@link Transformer} that given a {@link State} returns its {@link NodeMetadata}
	 * @param stateFactory is the {@link StateFactory}
	 * @return the {@link Transformer} that given a {@link State} returns its {@link NodeMetadata}
	 * @throws NullPointerException if the {@link StateFactory} is null
	 */
	protected Transformer<NodeMetadata, INTERSECTIONSTATE> getStateTransformer(INTERSECTIONSTATEFACTORY stateFactory){
		if(stateFactory==null){
			throw new NullPointerException("The stateFactory cannot be null");
		}
		return new IntBAMetadataStateTransformer(this.ba, stateFactory); 
	}
	
	/**
	 * creates a new IBAMetadataStateTransformer
	 * @param a is the {@link DrawableIBA} that must be updated by the {@link Transformer}
	 * @param stateFactory contains the {@link Factory} which creates the {@link State} of the {@link DrawableIBA}
	 * @throws NullPointerException if the {@link DrawableIBA} or the {@link StateFactory} is null
	 */
	protected class IntBAMetadataStateTransformer extends BAMetadataStateTransformer{

		/**
		 * creates a new IBAMetadataStateTransformer
		 * @param a is the {@link DrawableIBA} that must be updated by the {@link Transformer}
		 * @param stateFactory contains the {@link Factory} which creates the {@link State} of the {@link DrawableIBA}
		 * @throws NullPointerException if the {@link DrawableIBA} or the {@link StateFactory} is null
		 */
		public IntBAMetadataStateTransformer(AUTOMATON a, INTERSECTIONSTATEFACTORY transitionFactory) {
			super(a, transitionFactory);
		}

		/**
		 * transforms the {@link NodeMetadata} into the corresponding {@link State}
		 * @param input contains the {@link NodeMetadata} to be converted into the corresponding {@link State}
		 * @throws NullPointerException if the input is null
		 */
		@Override
		public INTERSECTIONSTATE transform(NodeMetadata input) {
			if(input==null){
				throw new NullPointerException("The NodeMetadata to be converted into a State cannot be null");
			}
			
			INTERSECTIONSTATE s=this.stateFactory.create(input.getProperty("name"), Integer.parseInt(input.getId()));
			if(Boolean.parseBoolean(input.getProperty("initial"))){
				this.a.addInitialState(s);
			}
			if(Boolean.parseBoolean(input.getProperty("accepting"))){
				this.a.addAcceptState(s);
			}
			if(Boolean.parseBoolean(input.getProperty("transparent"))){
				this.a.addTransparentState(s);
			}
			if(Boolean.parseBoolean(input.getProperty("mixed"))){
				this.a.addMixedState(s);
			}
			return s;
		}
	}
	
	/**
	 * contains the {@link Transformer} that given the {@link EdgeMetadata} returns the corresponding {@link LabelledTransition}
	 * @author claudiomenghi
	 *
	 */
	protected class BAMetadataToTransitionTransformer implements
			Transformer<EdgeMetadata, INTERSECTIONTRANSITION> {
		/**
		 * is the {@link Factory} which is used to create the {@link LabelledTransition}
		 */
		protected INTERSECTIONTRANSITIONFACTORY transitionFactory;
		protected StateFactory<STATE> stateFactory;
		protected AUTOMATON a;

		/**
		 * creates a new {@link Transformer} that transforms {@link EdgeMetadata} into {@link LabelledTransition}
		 * @param transitionFactory is the {@link Factory} which is used to create the {@link LabelledTransition}
		 * @throws NullPointerException is the transitionFactory is null
		 */
		public BAMetadataToTransitionTransformer(
				INTERSECTIONTRANSITIONFACTORY transitionFactory,
				AUTOMATON a) {
			if(transitionFactory==null){
				throw new NullPointerException("The transition factory cannot be null");
			}
			
			this.transitionFactory = transitionFactory;
			this.stateFactory=new StateFactory<STATE>();
			this.a=a;
		}

		/**
		 * transforms the {@link EdgeMetadata} into the corresponding {@link Transition}
		 * @param input contains the {@link EdgeMetadata} to be converted
		 * @throws NullPointerException if the {@link EdgeMetadata} to be converted is null
		 */
		@Override
		public INTERSECTIONTRANSITION transform(EdgeMetadata input) {
			if(input==null){
				throw new NullPointerException("The EdgeMetadata to be converted cannot be null");
			}
			if(input.getProperty("cstateId")==null){
				return this.transitionFactory.create(
						Integer.parseInt(input.getId()),
						DNFFormula.loadFromString(input.getProperty("DNFFormula")));
			}
			else
			{
				return this.transitionFactory.create(
						Integer.parseInt(input.getId()),
						DNFFormula.loadFromString(input.getProperty("DNFFormula")),
						this.stateFactory.create(
								input.getProperty("cstateName"), 
								Integer.parseInt(input.getProperty("cstateId"))));
			}
			
		}
	}

	
}
