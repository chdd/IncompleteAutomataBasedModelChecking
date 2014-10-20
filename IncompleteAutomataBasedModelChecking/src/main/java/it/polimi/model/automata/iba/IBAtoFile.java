package it.polimi.model.automata.iba;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.ba.transition.TransitionToMetadataTransformer;
import it.polimi.model.elements.states.State;
import it.polimi.model.elements.states.BAStateAcceptingToMetadataTransformer;
import it.polimi.model.elements.states.BAStateInitialToMetadataTransformer;
import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.io.iba.StateTransparentToMetadataTransformer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.bind.JAXBException;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.GraphMLWriter;

public class IBAtoFile {

	/**
	 * writes the BuchiAutomaton to the file with path filePath
	 * @param filePath is the path of the file where the BuchiAutomaton must be written
	 * @throws JAXBException if an error was encountered while creating the XML description of the BuchiAutomaton
	 * @throws IOException - if an I/O error occurs.
	 */
	public void toFile(String filePath, IBAImpl<State, LabelledTransition> iba) throws IOException{
		
		GraphMLWriter<State, LabelledTransition> graphWriter =new GraphMLWriter<State, LabelledTransition>();
		
		
		graphWriter.setVertexIDs(new Transformer<State, String>() {

			@Override
			public String transform(State input) {
				return Integer.toString(input.getId());
			}
			
		});
		graphWriter.addVertexData("name", "name", "", new Transformer<State, String>() {
			@Override
			public String transform(State input) {
				return input.getName();
			}
		});
		
		graphWriter.addVertexData("initial", "initial", "false", new BAStateInitialToMetadataTransformer<State,LabelledTransition, IBAImpl<State, LabelledTransition>>(iba));
		graphWriter.addVertexData("accepting", "accepting", "false", new BAStateAcceptingToMetadataTransformer<State,LabelledTransition, IBAImpl<State, LabelledTransition>>(iba));
		graphWriter.addVertexData("transparent", "transparent", "false", new StateTransparentToMetadataTransformer<State,LabelledTransition, IBAImpl<State, LabelledTransition>>(iba));
		
		graphWriter.setEdgeIDs(new Transformer<LabelledTransition, String>() {
			@Override
			public String transform(LabelledTransition input) {
				return Integer.toString(input.getId());
			}
		});
		graphWriter.addEdgeData("id", "id", "", new TransitionToMetadataTransformer.TransitionIdToMetadataTransformer<LabelledTransition>());
		graphWriter.addEdgeData("DNFFormula", "DNFFormula", "", new TransitionToMetadataTransformer.TransitionDNFFormulaToMetadataTransformer<LabelledTransition>());
		
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
		
		graphWriter.save(iba, out);
	}
		
}
