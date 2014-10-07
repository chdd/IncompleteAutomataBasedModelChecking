package it.polimi.model.io;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.io.fromfile.BATransformer;
import it.polimi.model.automata.ba.state.BAMetadataToStateTransformer;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.BAMetadataToTransitionTransformer;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.ba.transition.TransitionFactory;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
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
	public static BuchiAutomaton<State, LabelledTransition> loadBAAutomaton(String filePath) throws IOException, GraphIOException  {
		
		BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
		
		BuchiAutomaton<State, LabelledTransition> ba=new BuchiAutomaton<State, LabelledTransition>();
		
		/* Create the Graph Transformer */
		Transformer<GraphMetadata, BuchiAutomaton<State, LabelledTransition>>
		graphTransformer = new BATransformer<GraphMetadata, BuchiAutomaton<State, LabelledTransition>>(ba);
		
		/* Create the Vertex Transformer */
		BAMetadataToStateTransformer<BuchiAutomaton<State, LabelledTransition>> vertexTransformer=new BAMetadataToStateTransformer<BuchiAutomaton<State,LabelledTransition>>(ba);

		/* Create the Edge Transformer */
		 Transformer<EdgeMetadata, LabelledTransition> edgeTransformer =new BAMetadataToTransitionTransformer<BuchiAutomaton<State, LabelledTransition>>(ba);
		
		
		 /* Create the Hyperedge Transformer */
		 Transformer<HyperEdgeMetadata, LabelledTransition> hyperEdgeTransformer
		 = new Transformer<HyperEdgeMetadata, LabelledTransition>() {
		      public LabelledTransition transform(HyperEdgeMetadata metadata) {
		    	  LabelledTransition e = new TransitionFactory<LabelledTransition>().create();
			        
		          return e;
		      }
		 };
		 
		
		GraphMLReader2<BuchiAutomaton<State, LabelledTransition>, State, LabelledTransition> graphReader = 
				new GraphMLReader2<BuchiAutomaton<State, LabelledTransition>, State, LabelledTransition>
		(fileReader, graphTransformer, vertexTransformer,
			       edgeTransformer, hyperEdgeTransformer);
		BuchiAutomaton<State, LabelledTransition> g = graphReader.readGraph();
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
	public static IncompleteBuchiAutomaton<State, LabelledTransition> loadIBAAutomaton(String filePath) throws IOException, GraphIOException  {
			
		BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
		
		IncompleteBuchiAutomaton<State, LabelledTransition> ba=new IncompleteBuchiAutomaton<State, LabelledTransition>();
		
		/* Create the Graph Transformer */
		Transformer<GraphMetadata, IncompleteBuchiAutomaton<State, LabelledTransition>>
		graphTransformer = new BATransformer<GraphMetadata, IncompleteBuchiAutomaton<State, LabelledTransition>>(ba);
		
		/* Create the Vertex Transformer */
		IBAMetadataToStateTransformer<IncompleteBuchiAutomaton<State, LabelledTransition>> vertexTransformer=
				new IBAMetadataToStateTransformer<IncompleteBuchiAutomaton<State, LabelledTransition>>(ba);

		/* Create the Edge Transformer */
		 Transformer<EdgeMetadata, LabelledTransition> edgeTransformer =new BAMetadataToTransitionTransformer<IncompleteBuchiAutomaton<State, LabelledTransition>>(ba);
			
		
		
		 /* Create the Hyperedge Transformer */
		 Transformer<HyperEdgeMetadata, LabelledTransition> hyperEdgeTransformer
		 = new Transformer<HyperEdgeMetadata, LabelledTransition>() {
		      public LabelledTransition transform(HyperEdgeMetadata metadata) {
		    	  LabelledTransition e = new TransitionFactory<LabelledTransition>().create();
			        
		          return e;
		      }
		 };
		 
		
		GraphMLReader2<IncompleteBuchiAutomaton<State, LabelledTransition>, State, LabelledTransition> graphReader = 
				new GraphMLReader2<IncompleteBuchiAutomaton<State, LabelledTransition>, State, LabelledTransition>
		(fileReader, graphTransformer, vertexTransformer,
			       edgeTransformer, hyperEdgeTransformer);
				
				
		IncompleteBuchiAutomaton<State, LabelledTransition> g = graphReader.readGraph();
		return g;
	}
}
