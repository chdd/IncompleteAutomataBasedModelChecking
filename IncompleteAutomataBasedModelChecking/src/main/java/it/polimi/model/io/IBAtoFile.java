package it.polimi.model.io;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.model.io.ba.tofile.StateAcceptingToMetadataTransformer;
import it.polimi.model.io.ba.tofile.StateInitialToMetadataTransformer;
import it.polimi.model.io.ba.tofile.StateNameToMetadataTransformer;
import it.polimi.model.io.ba.tofile.StateToIdTransformer;
import it.polimi.model.io.ba.tofile.TransitionToMetadataTransformer;
import it.polimi.model.io.iba.StateTransparentToMetadataTransformer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.bind.JAXBException;

import edu.uci.ics.jung.io.GraphMLWriter;

public class IBAtoFile {

	/**
	 * writes the BuchiAutomaton to the file with path filePath
	 * @param filePath is the path of the file where the BuchiAutomaton must be written
	 * @throws JAXBException if an error was encountered while creating the XML description of the BuchiAutomaton
	 * @throws IOException - if an I/O error occurs.
	 */
	public void toFile(String filePath, IncompleteBuchiAutomaton<State, LabelledTransition<State>> iba) throws IOException{
		
		GraphMLWriter<State, LabelledTransition<State>> graphWriter =new GraphMLWriter<State, LabelledTransition<State>>();
		graphWriter.setVertexIDs(new StateToIdTransformer<State>());
		graphWriter.addVertexData("name", "name", "", new StateNameToMetadataTransformer<State>());
		
		graphWriter.addVertexData("initial", "initial", "false", new StateInitialToMetadataTransformer<State,LabelledTransition<State>, IncompleteBuchiAutomaton<State, LabelledTransition<State>>>(iba));
		graphWriter.addVertexData("accepting", "accepting", "false", new StateAcceptingToMetadataTransformer<State,LabelledTransition<State>, IncompleteBuchiAutomaton<State, LabelledTransition<State>>>(iba));
		graphWriter.addVertexData("transparent", "transparent", "false", new StateTransparentToMetadataTransformer<State,LabelledTransition<State>, IncompleteBuchiAutomaton<State, LabelledTransition<State>>>(iba));
		
		graphWriter.addEdgeData("labels", "labels", "", new TransitionToMetadataTransformer<State, LabelledTransition<State>, IncompleteBuchiAutomaton<State, LabelledTransition<State>>>());
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
		
		graphWriter.save(iba, out);
	}
		
}
