package it.polimi.model.impl.automata.io;

import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.BAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.io.BufferedReader;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.graphml.EdgeMetadata;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;
import edu.uci.ics.jung.io.graphml.GraphMetadata;
import edu.uci.ics.jung.io.graphml.HyperEdgeMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class BAReader<
		STATE extends State, 
		TRANSITION extends LabelledTransition, 
		TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>, 
		STATEFACTORY extends StateFactory<STATE>,
		AUTOMATON extends DrawableBA<STATE, TRANSITION, TRANSITIONFACTORY>,
		AUTOMATONFACTORY extends BAFactory<STATE, TRANSITION, TRANSITIONFACTORY, AUTOMATON>>{

	protected GraphMLReader2<AUTOMATON, STATE, TRANSITION> graphReader=null;
	protected AUTOMATON ba;
		
	public BAReader( 
			TRANSITIONFACTORY transitionFactory,
			STATEFACTORY stateFactory, 
			AUTOMATONFACTORY automatonFactory,
			BufferedReader fileReader) {
		
		this.ba=automatonFactory.create();
		
		this.graphReader=new GraphMLReader2<AUTOMATON, STATE, TRANSITION>(
				fileReader,
				new BATransformer(ba), 
				this.getStateTransformer(stateFactory),
				new BAMetadataToTransitionTransformer(transitionFactory), 
				new HyperEdgeMetadataToTransitionTransformer(transitionFactory));
	}
	
	protected Transformer<NodeMetadata, STATE> getStateTransformer(STATEFACTORY stateFactory){
		return new BAMetadataStateTransformer(ba, stateFactory); 
	}

	public DrawableBA<STATE, TRANSITION, TRANSITIONFACTORY> readGraph() throws GraphIOException {
		
		return this.graphReader.readGraph();
	}
		
	protected class HyperEdgeMetadataToTransitionTransformer implements Transformer<HyperEdgeMetadata, TRANSITION>{

		protected TRANSITIONFACTORY transitionFactory;

		public HyperEdgeMetadataToTransitionTransformer(TRANSITIONFACTORY transitionFactory) {
			this.transitionFactory = transitionFactory;
		}
		
		@Override
		public TRANSITION transform(HyperEdgeMetadata input) {
			return this.transitionFactory.create();
		}
	}
	
	protected class BAMetadataStateTransformer implements
			Transformer<NodeMetadata, STATE> {

		protected STATEFACTORY StateFactory;
		protected DrawableBA<STATE, TRANSITION, TRANSITIONFACTORY> a;

		public BAMetadataStateTransformer(DrawableBA<STATE, TRANSITION, TRANSITIONFACTORY> a, STATEFACTORY transitionFactory) {
			this.StateFactory = transitionFactory;
			this.a=a;
		}

		@Override
		public STATE transform(NodeMetadata input) {
			
			STATE s=this.StateFactory.create(input.getProperty("name"), Integer.parseInt(input.getId()));
			if(Boolean.parseBoolean(input.getProperty("initial"))){
				this.a.addInitialState(s);
			}
			if(Boolean.parseBoolean(input.getProperty("accepting"))){
				this.a.addAcceptState(s);
			}
			return s;
		}

	}

	protected class BAMetadataToTransitionTransformer implements
			Transformer<EdgeMetadata, TRANSITION> {

		protected TRANSITIONFACTORY transitionFactory;

		public BAMetadataToTransitionTransformer(
				TRANSITIONFACTORY transitionFactory) {
			this.transitionFactory = transitionFactory;
		}

		@Override
		public TRANSITION transform(EdgeMetadata input) {
			return this.transitionFactory.create(
					Integer.parseInt(input.getProperty("id")),
					DNFFormula.loadFromString(input.getProperty("DNFFormula")));

		}

	}
	
	protected  class BATransformer implements Transformer<GraphMetadata, AUTOMATON> {
		private AUTOMATON ba;
		public BATransformer(AUTOMATON ba){
			this.ba=ba;
			
		}
		@Override
		public AUTOMATON transform(GraphMetadata input) {
			return ba;
		}

	}
}
