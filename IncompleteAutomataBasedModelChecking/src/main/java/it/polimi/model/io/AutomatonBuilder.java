package it.polimi.model.io;

import it.polimi.model.automata.ba.io.fromfile.BATransformer;
import it.polimi.model.automata.ba.transition.BAMetadataToTransitionTransformer;
import it.polimi.model.elements.states.BAMetadataToStateTransformer;
import it.polimi.model.elements.states.IBAMetadataToStateTransformer;
import it.polimi.model.elements.states.State;
import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.impl.transitions.LabelledTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.graphml.EdgeMetadata;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;
import edu.uci.ics.jung.io.graphml.GraphMetadata;
import edu.uci.ics.jung.io.graphml.HyperEdgeMetadata;

/**
 * @author claudiomenghi
 * This class implements the singleton pattern to ensure that the class only has one instance, 
 * and provide a global point of access to it.
 * The same construction process can be used to create different implementations.
 */
public class AutomatonBuilder{
	

	/**
	 * loads the automaton from a file which contains the XML representation of the automaton
	 * @param type is the type of the automaton to be loaded
	 * @param filePath is the path of the file from which the automaton must be loaded
	 * @return the loaded automaton of type type
	 * @throws BuilderException is generated if problems are detected in the loading of the automaton
	 * @throws IOException 
	 * @throws GraphIOException 
	 */
	public static BAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>> loadBAAutomaton(String filePath) throws IOException, GraphIOException  {
		
		BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
		
		BAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>> ba=new BAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>(new LabelledTransitionFactory());
		
		/* Create the Graph Transformer */
		Transformer<GraphMetadata, BAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>>
		graphTransformer = new BATransformer<GraphMetadata, BAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>>(ba);
		
		
		/* Create the Edge Transformer */
		 Transformer<EdgeMetadata, LabelledTransition> edgeTransformer =new BAMetadataToTransitionTransformer<BAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>>(ba);
		
		
		 /* Create the Hyperedge Transformer */
		 Transformer<HyperEdgeMetadata, LabelledTransition> hyperEdgeTransformer
		 = new Transformer<HyperEdgeMetadata, LabelledTransition>() {
		      public LabelledTransition transform(HyperEdgeMetadata metadata) {
		    	  LabelledTransition e = new LabelledTransitionFactory().create();
			        
		          return e;
		      }
		 };
		 
		
		GraphMLReader2<BAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>, State, LabelledTransition> graphReader = 
				new GraphMLReader2<BAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>, State, LabelledTransition>
		(fileReader, graphTransformer, 
				new BAMetadataToStateTransformer<BAImpl<State,LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>>(ba)
				,
			       edgeTransformer, hyperEdgeTransformer);
		BAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>> g = graphReader.readGraph();
		return g;
	}
	
	/**
	 * loads the automaton from a file which contains the XML representation of the automaton
	 * @param type is the type of the automaton to be loaded
	 * @param filePath is the path of the file from which the automaton must be loaded
	 * @return the loaded automaton of type type
	 * @throws BuilderException is generated if problems are detected in the loading of the automaton
	 * @throws IOException 
	 * @throws GraphIOException 
	 */
	public static IBAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>> loadIBAAutomaton(String filePath) throws IOException, GraphIOException  {
			
		BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
		
		IBAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>> ba=new IBAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>(new LabelledTransitionFactory());
		
		/* Create the Graph Transformer */
		Transformer<GraphMetadata, IBAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>>
		graphTransformer = new BATransformer<GraphMetadata, IBAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>>(ba);
		
		/* Create the Vertex Transformer */
		IBAMetadataToStateTransformer<IBAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>> vertexTransformer=
				new IBAMetadataToStateTransformer<IBAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>>(ba);

		/* Create the Edge Transformer */
		 Transformer<EdgeMetadata, LabelledTransition> edgeTransformer =new BAMetadataToTransitionTransformer<IBAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>>(ba);
			
		
		
		 /* Create the Hyperedge Transformer */
		 Transformer<HyperEdgeMetadata, LabelledTransition> hyperEdgeTransformer
		 = new Transformer<HyperEdgeMetadata, LabelledTransition>() {
		      public LabelledTransition transform(HyperEdgeMetadata metadata) {
		    	  LabelledTransition e = new LabelledTransitionFactory().create();
			        
		          return e;
		      }
		 };
		 
		
		GraphMLReader2<IBAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>, State, LabelledTransition> graphReader = 
				new GraphMLReader2<IBAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>, State, LabelledTransition>
		(fileReader, graphTransformer, 
				vertexTransformer,
			       edgeTransformer, hyperEdgeTransformer);
				
				
		IBAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>> g = graphReader.readGraph();
		return g;
	}
}
