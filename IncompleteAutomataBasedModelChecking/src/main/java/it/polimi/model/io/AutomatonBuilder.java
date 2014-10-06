package it.polimi.model.io;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.State;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.model.io.ba.BAMetadataToStateTransformer;
import it.polimi.model.io.ba.BATransformer;
import it.polimi.model.io.iba.IBAMetadataToStateTransformer;

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
	public static BuchiAutomaton<State, LabelledTransition<State>> loadBAAutomaton(String filePath) throws IOException, GraphIOException  {
		
		BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
		
		BuchiAutomaton<State, LabelledTransition<State>> ba=new BuchiAutomaton<State, LabelledTransition<State>>();
		
		/* Create the Graph Transformer */
		Transformer<GraphMetadata, BuchiAutomaton<State, LabelledTransition<State>>>
		graphTransformer = new BATransformer<GraphMetadata, BuchiAutomaton<State, LabelledTransition<State>>>(ba);
		
		/* Create the Vertex Transformer */
		BAMetadataToStateTransformer<BuchiAutomaton<State, LabelledTransition<State>>> vertexTransformer=
				new BAMetadataToStateTransformer<BuchiAutomaton<State, LabelledTransition<State>>>(ba);

		/* Create the Edge Transformer */
		 Transformer<EdgeMetadata, LabelledTransition<State>> edgeTransformer =
		 new Transformer<EdgeMetadata, LabelledTransition<State>>() {
		     public LabelledTransition<State> transform(EdgeMetadata metadata) {
		    	 LabelledTransition<State> e = new LabelledTransition<State>(null, new State(metadata.getTarget()));
		         return e;
		     }
		 };
		
		
		 /* Create the Hyperedge Transformer */
		 Transformer<HyperEdgeMetadata, LabelledTransition<State>> hyperEdgeTransformer
		 = new Transformer<HyperEdgeMetadata, LabelledTransition<State>>() {
		      public LabelledTransition<State> transform(HyperEdgeMetadata metadata) {
		    	  LabelledTransition<State> e = new LabelledTransition<State>(null, new State(metadata.getId()));
			        
		          return e;
		      }
		 };
		 
		
		GraphMLReader2<BuchiAutomaton<State, LabelledTransition<State>>, State, LabelledTransition<State>> graphReader = 
				new GraphMLReader2<BuchiAutomaton<State, LabelledTransition<State>>, State, LabelledTransition<State>>
		(fileReader, graphTransformer, vertexTransformer,
			       edgeTransformer, hyperEdgeTransformer);
				
				
		BuchiAutomaton<State, LabelledTransition<State>> g = graphReader.readGraph();
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
	public static IncompleteBuchiAutomaton<State, LabelledTransition<State>> loadIBAAutomaton(String filePath) throws IOException, GraphIOException  {
			
		BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
		
		IncompleteBuchiAutomaton<State, LabelledTransition<State>> ba=new IncompleteBuchiAutomaton<State, LabelledTransition<State>>();
		
		/* Create the Graph Transformer */
		Transformer<GraphMetadata, IncompleteBuchiAutomaton<State, LabelledTransition<State>>>
		graphTransformer = new BATransformer<GraphMetadata, IncompleteBuchiAutomaton<State, LabelledTransition<State>>>(ba);
		
		/* Create the Vertex Transformer */
		IBAMetadataToStateTransformer<IncompleteBuchiAutomaton<State, LabelledTransition<State>>> vertexTransformer=
				new IBAMetadataToStateTransformer<IncompleteBuchiAutomaton<State, LabelledTransition<State>>>(ba);

		/* Create the Edge Transformer */
		 Transformer<EdgeMetadata, LabelledTransition<State>> edgeTransformer =
		 new Transformer<EdgeMetadata, LabelledTransition<State>>() {
		     public LabelledTransition<State> transform(EdgeMetadata metadata) {
		    	 LabelledTransition<State> e = new LabelledTransition<State>(null, new State(metadata.getTarget()));
		         return e;
		     }
		 };
		
		
		 /* Create the Hyperedge Transformer */
		 Transformer<HyperEdgeMetadata, LabelledTransition<State>> hyperEdgeTransformer
		 = new Transformer<HyperEdgeMetadata, LabelledTransition<State>>() {
		      public LabelledTransition<State> transform(HyperEdgeMetadata metadata) {
		    	  LabelledTransition<State> e = new LabelledTransition<State>(null, new State(metadata.getId()));
			        
		          return e;
		      }
		 };
		 
		
		GraphMLReader2<IncompleteBuchiAutomaton<State, LabelledTransition<State>>, State, LabelledTransition<State>> graphReader = 
				new GraphMLReader2<IncompleteBuchiAutomaton<State, LabelledTransition<State>>, State, LabelledTransition<State>>
		(fileReader, graphTransformer, vertexTransformer,
			       edgeTransformer, hyperEdgeTransformer);
				
				
		IncompleteBuchiAutomaton<State, LabelledTransition<State>> g = graphReader.readGraph();
		return g;
	}
}
