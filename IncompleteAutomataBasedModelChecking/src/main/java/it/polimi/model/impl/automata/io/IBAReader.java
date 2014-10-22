package it.polimi.model.impl.automata.io;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IBAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.io.BufferedReader;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class IBAReader<
	STATE extends State, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>, 
	STATEFACTORY extends StateFactory<STATE>,
	AUTOMATON extends DrawableIBA<STATE, TRANSITION, TRANSITIONFACTORY>,
	AUTOMATONFACTORY extends IBAFactory<STATE, TRANSITION, TRANSITIONFACTORY, AUTOMATON>>
	extends BAReader<STATE, TRANSITION, TRANSITIONFACTORY, STATEFACTORY, AUTOMATON, AUTOMATONFACTORY>{

	public IBAReader( 
			TRANSITIONFACTORY transitionFactory,
			STATEFACTORY stateFactory, 
			AUTOMATONFACTORY automatonFactory,
			BufferedReader fileReader) {
		super(transitionFactory, stateFactory, automatonFactory, fileReader);
	}

	public DrawableIBA<STATE, TRANSITION, TRANSITIONFACTORY> readGraph() throws GraphIOException {
		
		return this.graphReader.readGraph();
	}
	
	protected Transformer<NodeMetadata, STATE> getStateTransformer(STATEFACTORY stateFactory){
		return new BAMetadataStateTransformer(this.ba, stateFactory); 
	}
	
	protected class IBAMetadataStateTransformer extends BAMetadataStateTransformer{

		public IBAMetadataStateTransformer(AUTOMATON a, STATEFACTORY transitionFactory) {
			super(a, transitionFactory);
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
			if(Boolean.parseBoolean(input.getProperty("transparent"))){
				this.a.addAcceptState(s);
			}
			return s;
		}
	}
}
