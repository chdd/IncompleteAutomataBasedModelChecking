package it.polimi;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.in.BAReader;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.automata.io.out.IntersectionWriter;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Replacement;
import it.polimi.constraints.io.in.ConstraintReader;
import it.polimi.constraints.io.in.ReplacementReader;
import it.polimi.constraints.io.out.ConstraintWriter;
import it.polimi.model.ltltoba.LTLReader;
import it.polimi.model.ltltoba.LTLtoBATransformer;
import it.polimi.refinement.constraintcomputation.ReplacementConstraintComputation;
import it.polimi.refinementchecker.ReplacementChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import asg.cliche.Command;
import asg.cliche.Param;

public class CHIAConsole {

	protected IBA model;
	protected BA claim;
	protected CHIA chia;
	protected Constraint constraint;
	protected Replacement replacement;
	protected ReplacementChecker replacementChecker;

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
	@Command(name = "loadLTLClaim", abbrev = "lcLTL", description = "It is used to load the property from an LTL formula", header = "load LTL claim")
	public void loadProperty(
			@Param(name = "LTLFormula", description = "is the LTL formula that represents the claim") String ltlProperty
			){
		this.claim=new LTLtoBATransformer().transform("!("+ltlProperty+")");
		System.out.println(this.claim);
	}
	
	@Command(name = "loadLTLClaim", abbrev = "lcLTL", description = "It is used to load the property from an LTL formula", header = "load LTL claim")
	public void loadProperty(
			@Param(name = "-f", description = "is the flag that specify that the formula must be loaded from file") String flag,
			@Param(name = "file", description = "is the path of the file from which the formula must be loaded") String file
			) throws IOException{
		this.claim=new LTLReader(file).loadLTLFromFile();
		System.out.println(this.claim);
	}

	@Command(name = "check", abbrev = "ck", description = "Is used to check the model against the specified claim. Before running the model checking procedure it is necessary to load the model and the claim to be considered", header = "Checking procedure ended")
	public void check() {
		ModelCheckingResults results = new ModelCheckingResults(true, true,
				true);
		chia = new CHIA(claim, model, results);

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
		chia = new CHIA(claim, model, results);

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
	
	
	@Command(name = "loadReplacement", abbrev = "lr", description = "It is used to load the replacement from an XML file. The XML file must mach the Replacement.xsd", header = "replacement loaded")
	public void loadReplacement(
			@Param(name = "replacementFilePath", description = "is the path of the file that contains the replacement to be considered") String replacementFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		this.replacement = new ReplacementReader(replacementFilePath).read();
	}
	
	
			
	@Command(name = "loadConstraint", abbrev = "lC", description = "It is used to load the constraint from an XML file. The XML file must mach the Constraint.xsd", header = "constraint loaded")
	public void loadConstraint(
			@Param(name = "constraintFilePath", description = "is the path of the file that contains the constraint to be considered") String constraintFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		this.constraint = new ConstraintReader(constraintFilePath).read();
	}
	
	@Command(name = "computeConstraint", abbrev = "cc", description = "Is used to compute the constraint corresponding to the model and the specified claim. ")
	public void computeConstraint()
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		ModelCheckingResults results = new ModelCheckingResults(true, true,
				true);
		if(chia==null){
			chia = new CHIA(claim, model, results);
		}
		this.constraint=this.chia.generateConstraint();
	}
	@Command(name = "computeConstraint", abbrev = "cc", description = "Is used to compute the constraint corresponding to the model and the specified claim. ")
	public void computeConstraint(
			@Param(name = "-p", description = "if the -p flag is specified the port reachability relation is not computed") String p)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		ModelCheckingResults results = new ModelCheckingResults(true, true,
				true);
		if(chia==null){
			chia = new CHIA(claim, model, results);
		}
		this.constraint=this.chia.generateConstraintNoPortReachability();
	}
	
	@Command(name = "saveConstraint", abbrev = "sc", description = "It is used to save the constraint in an XML file.", header = "constraint saved")
	public void saveConstraint(
			@Param(name = "constraintFilePath", description = "is the path of the file where the constraint must be saved") String constraintFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		ConstraintWriter constraintWriter=new ConstraintWriter(this.constraint, constraintFilePath);
		constraintWriter.write();
	}
	
	@Command(name = "replacementChecking", abbrev = "rck", description = "Is used to check the replacement against the constraint previously generated.", header = "Performing the replacement checking")
	public void replacementChecking(){
		replacementChecker=new ReplacementChecker(constraint, replacement);
		int result=replacementChecker.check();
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
	
	@Command(name = "replacementChecking", abbrev = "rck", description = "Is used to check the replacement against the constraint previously generated", header = "Performing the replacement checking")
	public void replacementChecking(
			@Param(name = "intersectionFilePath", description = "The location where the intersection automaton must be saved") String intersectionFilePath) {
		replacementChecker=new ReplacementChecker(constraint, replacement);
		int result=replacementChecker.check();
		if (result == 1) {
			System.out.println("The property is satisfied");
		}
		if (result == 0) {
			System.out.println("The property is not satisfied");
		}
		if (result == -1) {
			System.out.println("The property is possibly satisfied");
		}
		IntersectionBA intersectionAutomaton=replacementChecker.getChecker().getIntersectionAutomaton();
		IntersectionWriter intersectionWriter=new IntersectionWriter(intersectionAutomaton, new File(intersectionFilePath));
		intersectionWriter.write();
	}
	
	
	@Command(name = "replacementComputeConstraint", abbrev = "rcc", description = "Is used to check the replacement against the constraint previously generated.", header = "replacement checking performed")
	public void replacementComputeConstraint(){
		if(replacementChecker==null){
			this.replacementChecking();
		}
		ReplacementConstraintComputation replacementConstraintComputation = new ReplacementConstraintComputation(replacementChecker);
		replacementConstraintComputation.constraintComputation();
		this.constraint=replacementConstraintComputation.newConstraint();
	}
	
	@Command(name = "saveReplacementConstraint", abbrev = "rsc", description = "It is used to save the constraint in an XML file.", header = "constraint saved")
	public void replacementSaveConstraint(
			@Param(name = "constraintFilePath", description = "is the path of the file where the constraint must be saved") String constraintFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		ConstraintWriter constraintWriter=new ConstraintWriter(this.constraint, constraintFilePath);
		constraintWriter.write();
	}
}