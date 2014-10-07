package it.polimi.model.automata.ba;

import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.ba.transition.TransitionToMetadataTransformer;
import it.polimi.model.io.ba.tofile.StateAcceptingToMetadataTransformer;
import it.polimi.model.io.ba.tofile.StateInitialToMetadataTransformer;
import it.polimi.model.io.ba.tofile.StateNameToMetadataTransformer;
import it.polimi.model.io.ba.tofile.StateToIdTransformer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.bind.JAXBException;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.GraphMLWriter;

public class BAtoFile {

	/**
	 * writes the BuchiAutomaton to the file with path filePath
	 * @param filePath is the path of the file where the BuchiAutomaton must be written
	 * @throws JAXBException if an error was encountered while creating the XML description of the BuchiAutomaton
	 * @throws IOException - if an I/O error occurs.
	 */
	public void toFile(String filePath, BuchiAutomaton<State, LabelledTransition> ba) throws IOException {
		
		GraphMLWriter<State, LabelledTransition> graphWriter =new GraphMLWriter<State, LabelledTransition>();
		
		graphWriter.setVertexIDs(new StateToIdTransformer<State>());
		graphWriter.addVertexData("name", "name", "", new StateNameToMetadataTransformer<State>());
		
		graphWriter.addVertexData("initial", "initial", "false", new StateInitialToMetadataTransformer<State,LabelledTransition, BuchiAutomaton<State, LabelledTransition>>(ba));
		graphWriter.addVertexData("accepting", "accepting", "false", new StateAcceptingToMetadataTransformer<State,LabelledTransition, BuchiAutomaton<State, LabelledTransition>>(ba));
		
		graphWriter.setEdgeIDs(new Transformer<LabelledTransition, String>() {
				@Override
				public String transform(LabelledTransition input) {
					return Integer.toString(input.getId());
				}
			});
		graphWriter.addEdgeData("labels", "labels", "", new TransitionToMetadataTransformer<State,LabelledTransition, BuchiAutomaton<State, LabelledTransition>>());
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
		
		graphWriter.save(ba, out);
	}
}
