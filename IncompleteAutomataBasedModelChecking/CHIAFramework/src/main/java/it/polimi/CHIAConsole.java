package it.polimi;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.in.BAReader;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.automata.io.out.IntersectionWriter;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.constraints.Replacement;
import it.polimi.constraints.io.in.ReplacementReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import asg.cliche.Command;
import asg.cliche.Param;

public class CHIAConsole {

	private IBA model;
	private BA claim;
	
	private Replacement replacement;

	@Command(name = "loadModel", abbrev = "lm", description = "Is used to load the model from an XML file. The XML file must mach the IBA.xsd.", header = "model loaded")
	public void loadModel(
			@Param(name = "modelFilePath", description = "is the path of the file that contains the model to be checked") String modelFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		this.model = new IBAReader(modelFilePath).read();
	}

	@Command(name = "loadClaim", abbrev = "lc", description = "Is used to load the claim from an XML file. The XML file must mach the BA.xsd.", header = "claim loaded")
	public void loadClaim(
			@Param(name = "claimFilePath", description = "is the path of the file that contains the claim to be checked") String claimFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		this.claim = new BAReader(claimFilePath).read();

	}

	@Command(name = "check", abbrev = "ck", description = "Is used to check the model against the specified claim. Before running the model checking procedure it is necessary to load the model and the claim to be considered", header = "Checking procedure ended")
	public void check() {
		ModelCheckingResults results = new ModelCheckingResults(true, true,
				true);
		CHIA chia = new CHIA(claim, model, results);

		int result = chia.check();
		if (result == 1) {
			System.out.println("The property is satisfied");
		}
		if (result == 0) {
			System.out.println("The property is not satisfied");
		}
		if (result == -1) {
			System.out.println("The property is possibly satisfied");
		}

	}
	
	@Command(name = "check", abbrev = "ck", description = "Is used to check the model against the specified claim. Before running the model checking procedure it is necessary to load the model and the claim to be considered", header = "Checking procedure ended")
	public void check(
			@Param(name = "intersectionFilePath", description = "The location where the intersection automaton must be saved") String intersectionFilePath) {
		ModelCheckingResults results = new ModelCheckingResults(true, true,
				true);
		CHIA chia = new CHIA(claim, model, results);

		int result = chia.check();
		if (result == 1) {
			System.out.println("The property is satisfied");
		}
		if (result == 0) {
			System.out.println("The property is not satisfied");
		}
		if (result == -1) {
			System.out.println("The property is possibly satisfied");
		}
		IntersectionBA intersectionAutomaton=chia.getModelChecker().getIntersectionAutomaton();
		IntersectionWriter intersectionWriter=new IntersectionWriter(intersectionAutomaton, new File(intersectionFilePath));
		intersectionWriter.write();

	}
	
	
	@Command(name = "loadReplacement", abbrev = "lr", description = "I is used to load the replacement from an XML file. The XML file must mach the Replacement.xsd", header = "replacement loaded")
	public void loadReplacement(
			@Param(name = "replacementFilePath", description = "is the path of the file that contains the replacement to be considered") String replacementFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		this.replacement = new ReplacementReader(replacementFilePath).read();
	}
}
