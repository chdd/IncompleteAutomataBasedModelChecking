package it.polimi.io;

import it.polimi.model.impl.labeling.DNFFormulaImpl;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactoryImpl;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.BAFactory;
import it.polimi.model.interfaces.states.StateFactory;
import it.polimi.model.interfaces.transitions.TransitionFactory;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.graphml.EdgeMetadata;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;
import edu.uci.ics.jung.io.graphml.GraphMetadata;
import edu.uci.ics.jung.io.graphml.HyperEdgeMetadata;
import edu.uci.ics.jung.io.graphml.Metadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

/**
 * contains the reader which is used to load a {@link DrawableBA}
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the {@link State}s of the {@link DrawableBA}
 * @param <TRANSITION> is the type of the {@link Transition} of the {@link DrawableBA}
 * @param <TRANSITIONFACTORY> is the {@link Factory} which is used to create the TRANSITION of the {@link DrawableBA}
 * @param <STATEFACTORY> is the {@link Factory} which is used to create the {@link State}s of the {@link DrawableBA}
 * @param <AUTOMATON> is the type of the automaton (which extends the {@link DrawableBA}) to be loaded
 * @param <AUTOMATONFACTORY> is a {@link Factory} which is able to create a new empty {@link DrawableBA}
 */
public class BAReader<
		STATE extends State, 
		TRANSITION extends Transition,
		AUTOMATON extends BA<STATE, TRANSITION>,
		AUTOMATONFACTORY extends BAFactory<STATE, TRANSITION, AUTOMATON>>{

	/**
	 * is the {@link GraphMLReader2} which is used to load the {@link DrawableBA}
	 */
	protected GraphMLReader2<DirectedSparseGraph<STATE, TRANSITION>, STATE, TRANSITION> graphReader=null;
	/**
	 * contains the {@link DrawableBA} loaded from the file
	 */
	protected AUTOMATON ba;
	
	protected Map<STATE, Point2D> statesandlocations;
	
	/**
	 * creates a new {@link BAReader} which can be used to load the {@link DrawableBA}
	 * @param transitionFactory is the {@link Factory} which is used to crate the {@link Transition}s
	 * @param stateFactory is the {@link Factory} which is used to create the {@link State}s
	 * @param automatonFactory is the {@link Factory} which is used to create a new empty {@link DrawableBA}
	 * @param fileReader is the file from which the {@link DrawableBA} must be loaded
	 * @throws NullPointerException if the transitionFactory or the stateFactory or the automatonFactory or the fileReader is null
	 */
	public BAReader( 
			TransitionFactory<TRANSITION> transitionFactory,
			StateFactory<STATE> stateFactory, 
			AUTOMATONFACTORY automatonFactory,
			BufferedReader fileReader) {
		if(transitionFactory==null){
			throw new NullPointerException("The transition factory cannot be null");
		}
		if(stateFactory==null){
			throw new NullPointerException("The state factory cannot be null");
		}
		if(automatonFactory==null){
			throw new NullPointerException("The automaton factory cannot be null");
		}
		if(fileReader==null){
			throw new NullPointerException("The fileReader cannot be null");
		}
		this.statesandlocations=new HashMap<STATE, Point2D>();
		
		this.ba=automatonFactory.create();
		
		this.graphReader=new GraphMLReader2<DirectedSparseGraph<STATE, TRANSITION>, STATE, TRANSITION>(
				fileReader,
				new BATransformer(ba),
				this.getStateTransformer(stateFactory),
				this.getTransitionTransformer(transitionFactory), 
				new HyperEdgeMetadataToTransitionTransformer(transitionFactory));
	}
	
	
	protected Transformer<EdgeMetadata, TRANSITION> getTransitionTransformer(TransitionFactory<TRANSITION> transitionFactory){
		if(transitionFactory==null){
			throw new NullPointerException("The stateFactory cannot be null");
		}
		return  new BAMetadataToTransitionTransformer(transitionFactory); 
	}
	
	/**
	 * returns the {@link Transformer} that given a {@link State} returns its {@link Metadata}
	 * @param stateFactory is the {@link StateFactoryImpl}
	 * @return the {@link Transformer} that given a {@link State} returns its {@link Metadata}
	 * @throws NullPointerException if the {@link StateFactoryImpl} is null
	 */
	protected Transformer<NodeMetadata, STATE> getStateTransformer(StateFactory<STATE> stateFactory){
		if(stateFactory==null){
			throw new NullPointerException("The stateFactory cannot be null");
		}
		return new BAMetadataStateTransformer(ba, this.statesandlocations, stateFactory); 
	}

	/**
	 * read the {@link DrawableBA} from the file
	 * @return a new {@link DrawableBA} which is read from the {@link BufferedReader}
	 * @throws GraphIOException is generated if a problem occurs in the loading of the {@link DrawableBA}
	 */
	public AUTOMATON readGraph() throws GraphIOException {
		this.graphReader.readGraph();
		return this.ba;
	}
	
	public Transformer<STATE, Point2D> getStatePositionTransformer(){
		if(!this.statesandlocations.isEmpty()){
			return new StatePositionTransformer(this.statesandlocations);
		}
		return null;
	}
	
	
		
	/**
	 * contains the {@link Transformer} that given the {@link HyperEdgeMetadata} returns the corresponding {@link Transition}
	 * @author claudiomenghi
	 */
	protected class HyperEdgeMetadataToTransitionTransformer implements Transformer<HyperEdgeMetadata, TRANSITION>{

		/**
		 * contains the {@link TransitionFactory}
		 */
		protected TransitionFactory<TRANSITION> transitionFactory;

		/**
		 * creates a new {@link HyperEdgeMetadata} {@link Transformer}
		 * @param transitionFactory is the {@link Factory} which is used to create {@link Transition}
		 * @throws NullPointerException if the transitionFactory is null
		 */
		public HyperEdgeMetadataToTransitionTransformer(TransitionFactory<TRANSITION> transitionFactory) {
			if(transitionFactory==null){
				throw new NullPointerException("The transitionFactory cannot be null");
			}
			this.transitionFactory = transitionFactory;
		}
		
		/**
		 * transforms the {@link HyperEdgeMetadata} into the {@link Transition}
		 * @param the {@link HyperEdgeMetadata} from which the {@link Transition} must be loaded
		 * @throws NullPointerException if the {@link HyperEdgeMetadata} are null
		 */
		@Override
		public TRANSITION transform(HyperEdgeMetadata input) {
			if(input==null){
				throw new NullPointerException("The HyperEdgeMetadata cannot be null");
			}return this.transitionFactory.create();
		}
	}
	
	/**
	 * contains the {@link Transformer} that given the {@link NodeMetadata} returns the corresponding {@link State}
	 * @author claudiomenghi
	 */
	protected class BAMetadataStateTransformer implements
			Transformer<NodeMetadata, STATE> {

		/**
		 * contains the {@link Factory} which is used to create the {@link State}s
		 */
		protected StateFactory<STATE> stateFactory;
		/**
		 * contains the {@link DrawableBA} which must be updated by the {@link Transformer}
		 */
		protected AUTOMATON a;
		
		protected Map<STATE, Point2D> statesandlocations;

		/**
		 * creates a new BAMetadataStateTransformer
		 * @param a is the {@link DrawableBA} that must be updated by the {@link Transformer}
		 * @param stateFactory contains the {@link Factory} which creates the {@link State} of the {@link DrawableBA}
		 * @throws NullPointerException if the {@link DrawableBA} or the {@link StateFactoryImpl} is null
		 */
		public BAMetadataStateTransformer(AUTOMATON a, Map<STATE, Point2D> statesandlocations, StateFactory<STATE> stateFactory) {
			if(a==null){
				throw new NullPointerException("The AUTOMATON cannot be null");
			}
			if(stateFactory==null){
				throw new NullPointerException("The stateFactory cannot be null");
			}
			this.stateFactory = stateFactory;
			this.a=a;
			this.statesandlocations=statesandlocations;
		}

		/**
		 * transforms the {@link NodeMetadata} into the corresponding {@link State}
		 * @param input contains the {@link NodeMetadata} to be converted into the corresponding {@link State}
		 * @throws NullPointerException if the input is null
		 */
		@Override
		public STATE transform(NodeMetadata input) {
			if(input==null){
				throw new NullPointerException("The NodeMetadata to be converted into a State cannot be null");
			}
			
			STATE s=this.stateFactory.create(input.getProperty("name"), Integer.parseInt(input.getId()));
			if(Boolean.parseBoolean(input.getProperty("initial"))){
				this.a.addInitialState(s);
			}
			if(Boolean.parseBoolean(input.getProperty("accepting"))){
				this.a.addAcceptState(s);
			}
			if(input.getProperty("x")!=null && input.getProperty("y")!=null){
				this.statesandlocations.put(s, new Point2D.Double(Double.parseDouble(input.getProperty("x")),Double.parseDouble(input.getProperty("y"))));
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
			Transformer<EdgeMetadata, TRANSITION> {
		/**
		 * is the {@link Factory} which is used to create the {@link Transition}
		 */
		protected TransitionFactory<TRANSITION> transitionFactory;

		/**
		 * creates a new {@link Transformer} that transforms {@link EdgeMetadata} into {@link Transition}
		 * @param transitionFactory is the {@link Factory} which is used to create the {@link Transition}
		 * @throws NullPointerException is the transitionFactory is null
		 */
		public BAMetadataToTransitionTransformer(
				TransitionFactory<TRANSITION> transitionFactory) {
			if(transitionFactory==null){
				throw new NullPointerException("The transition factory cannot be null");
			}
			this.transitionFactory = transitionFactory;
		}

		/**
		 * transforms the {@link EdgeMetadata} into the corresponding {@link Transition}
		 * @param input contains the {@link EdgeMetadata} to be converted
		 * @throws NullPointerException if the {@link EdgeMetadata} to be converted is null
		 */
		@Override
		public TRANSITION transform(EdgeMetadata input) {
			if(input==null){
				throw new NullPointerException("The EdgeMetadata to be converted cannot be null");
			}
			return this.transitionFactory.create(
					Integer.parseInt(input.getId()),
					DNFFormulaImpl.loadFromString(input.getProperty("DNFFormula")));
		}
	}
	
	/**
	 * contains the {@link Transformer} that given the {@link GraphMetadata} returns the corresponding {@link DrawableBA}
	 * @author claudiomenghi
	 *
	 */
	protected  class BATransformer implements Transformer<GraphMetadata, DirectedSparseGraph<STATE, TRANSITION>> {
		/**
		 * contains the {@link DrawableBA} to be returned after the Transforming procedure
		 */
		private AUTOMATON ba;
		
		/**
		 * creates the new {@link Transformer}
		 * @param ba is the {@link DrawableBA} that must be updated by the Transforming procedure
		 * @throws NullPointerException if the ba is null
		 */
		public BATransformer(AUTOMATON ba){
			if(ba==null){
				throw new NullPointerException("The be cannot be null");
			}
			this.ba=ba;
		}
		
		/**
		 * transforms the {@link GraphMetadata} into the corresponding {@link DrawableBA} 
		 * @param input the {@link GraphMetadata} which corresponds to the {@link DrawableBA}
		 * @throws NullPointerException if the input is null
		 */
		@Override
		public DirectedSparseGraph<STATE, TRANSITION> transform(GraphMetadata input) {
			if(input==null){
				throw new NullPointerException("The input data cannot be null");
			}
			return ba.getGraph();
		}
	}
	
	protected  class StatePositionTransformer implements Transformer<STATE, Point2D> {
		
		Map<STATE, Point2D> statesandlocations;
		
		/**
		 * creates the new {@link Transformer}
		 * @param ba is the {@link DrawableBA} that must be updated by the Transforming procedure
		 * @throws NullPointerException if the ba is null
		 */
		public StatePositionTransformer(Map<STATE, Point2D> statesandlocations){
			if(statesandlocations==null){
				throw new NullPointerException("The be cannot be null");
			}
			this.statesandlocations=statesandlocations;
		}
		
		/**
		 * transforms the {@link GraphMetadata} into the corresponding {@link DrawableBA} 
		 * @param input the {@link GraphMetadata} which corresponds to the {@link DrawableBA}
		 * @throws NullPointerException if the input is null
		 */
		@Override
		public Point2D transform(STATE input) {
			if(input==null){
				throw new NullPointerException("The input data cannot be null");
			}
			if(this.statesandlocations.containsKey(input))
				return this.statesandlocations.get(input);
			else{
				return new Point2D.Double();
			}
			
		}
	}
}
