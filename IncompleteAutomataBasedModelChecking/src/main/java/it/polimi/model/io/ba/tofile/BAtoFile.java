package it.polimi.model.io.ba.tofile;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.State;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.bind.JAXBException;

import edu.uci.ics.jung.io.GraphMLWriter;

public class BAtoFile {

	/**
	 * writes the BuchiAutomaton to the file with path filePath
	 * @param filePath is the path of the file where the BuchiAutomaton must be written
	 * @throws JAXBException if an error was encountered while creating the XML description of the BuchiAutomaton
	 * @throws IOException - if an I/O error occurs.
	 */
	public void toFile(String filePath, BuchiAutomaton<State, LabelledTransition<State>> ba) throws IOException {
		
		GraphMLWriter<State, LabelledTransition<State>> graphWriter =new GraphMLWriter<State, LabelledTransition<State>>();
		
		graphWriter.setVertexIDs(new StateToIdTransformer<State>());
		graphWriter.addVertexData("name", "name", "", new StateNameToMetadataTransformer<State>());
		
		graphWriter.addVertexData("initial", "initial", "false", new StateInitialToMetadataTransformer<State,LabelledTransition<State>, BuchiAutomaton<State, LabelledTransition<State>>>(ba));
		graphWriter.addVertexData("accepting", "accepting", "false", new StateAcceptingToMetadataTransformer<State,LabelledTransition<State>, BuchiAutomaton<State, LabelledTransition<State>>>(ba));
		
		graphWriter.addEdgeData("labels", "labels", "", new TransitionToMetadataTransformer<State,LabelledTransition<State>, BuchiAutomaton<State, LabelledTransition<State>>>());
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
		
		graphWriter.save(ba, out);
	}
}
