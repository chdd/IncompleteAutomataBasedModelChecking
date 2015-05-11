package it.polimi;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.in.BAReader;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.automata.io.out.BAToElementTrasformer;
import it.polimi.automata.io.out.IBAToElementTrasformer;
import it.polimi.automata.io.out.IntersectionWriter;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.NormalAcceptingPolicy;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.io.out.ConstraintWriter;
import it.polimi.constraints.io.out.ElementToStringTransformer;
//import it.polimi.model.ltltoba.LTLReader;
//import it.polimi.model.ltltoba.LTLtoBATransformer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import asg.cliche.Command;
import asg.cliche.Param;

public class CHIAAutomataConsole {

	protected IBA model;
	protected BA claim;
	protected CHIA chia;
	protected Constraint constraint;
	boolean modelLoaded = false;
	boolean claimLoaded = false;

	boolean checked = false;
	boolean constraintComputed = false;
	AcceptingPolicy policy = new KripkeAcceptingPolicy();

	@Command(name = "loadModel", abbrev = "lm", description = "Is used to load the model from an XML file. The XML file must mach the IBA.xsd.", header = "model loaded")
	public void loadModel(
			@Param(name = "modelFilePath", description = "is the path of the file that contains the model to be checked") String modelFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		this.model = new IBAReader(modelFilePath).read();
		this.checked = false;
		this.constraintComputed = false;
		this.modelLoaded = true;
	}

	@Command(name = "changePolicy", abbrev = "cp", description = "Is used to change the accepting policy.", header = "policy changed")
	public void changePolicy(
			@Param(name = "policy", description = "is the policy to be used KRIPKE or NORMAL") String policy){
		if(policy.equals("KRIPKE")){
			this.policy=new KripkeAcceptingPolicy();
		}
		else{
			if(policy.equals("NORMAL")){
				this.policy=new NormalAcceptingPolicy();
			}
			else{
				System.out.println("Parameter: "+policy+" not accepted");
			}
		}
	}

	@Command(name = "loadClaim", abbrev = "lc", description = "Is used to load the claim from an XML file. The XML file must mach the BA.xsd.", header = "claim loaded")
	public void loadClaim(
			@Param(name = "claimFilePath", description = "is the path of the file that contains the claim to be checked") String claimFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		this.claim = new BAReader(claimFilePath).read();
		this.checked = false;
		this.constraintComputed = false;
		this.claimLoaded = true;
	}

	/*@Command(name = "loadLTLClaim", abbrev = "lcLTL", description = "It is used to load the property from an LTL formula", header = "load LTL claim")
	public void loadProperty(
			@Param(name = "LTLFormula", description = "is the LTL formula that represents the claim") String ltlProperty) {
		this.claim = new LTLtoBATransformer().transform("!(" + ltlProperty
				+ ")");
		System.out.println(this.claim);
		this.checked = false;
		this.constraintComputed = false;
		this.claimLoaded = true;
	}*/

	/*
	@Command(name = "loadLTLClaim", abbrev = "lcLTL", description = "It is used to load the property from an LTL formula", header = "load LTL claim")
	public void loadProperty(
			@Param(name = "-f", description = "is the flag that specify that the formula must be loaded from file") String flag,
			@Param(name = "file", description = "is the path of the file from which the formula must be loaded") String file)
			throws IOException {
		this.claim = new LTLReader(file).loadLTLFromFile();
		this.checked = false;
		this.constraintComputed = false;
		this.claimLoaded = true;
	}*/

	@Command(name = "check", abbrev = "ck", description = "Is used to check the model against the specified claim. Before running the model checking procedure it is necessary to load the model and the claim to be considered", header = "Checking procedure ended")
	public void check() {
		if (!this.claimLoaded) {
			System.out
					.println("You must load a claim before running the checker");
		}
		if (!this.modelLoaded) {
			System.out
					.println("You must load the model before running the checker");
		}
		ModelCheckingResults results = new ModelCheckingResults(true, true,
				true);
		
		chia = new CHIA(claim, model, policy, results);

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
		this.checked = true;
	}

	@Command(name = "displayModel", abbrev = "dispM", description = "It is used to display the model into the console.", header = "Model displayed")
	public void dispModel() throws Exception {
		if (!this.modelLoaded) {
			System.out.println("You must load the model before showing it");
			return;
		}
		System.out.println(new ElementToStringTransformer()
				.transform(new IBAToElementTrasformer().transform(this.model)));
	}

	@Command(name = "displayClaim", abbrev = "dispC", description = "It is used to display the claim into the console.", header = "Claim displayed")
	public void dispClaim() throws Exception {
		if (!this.claimLoaded) {
			System.out.println("You must load the claim before showing it");
			return;
		}
		System.out.println(new ElementToStringTransformer()
				.transform(new BAToElementTrasformer().transform(this.claim)));
	}

	@Command(name = "check", abbrev = "ck", description = "Is used to check the model against the specified claim. Before running the model checking procedure it is necessary to load the model and the claim to be considered", header = "Checking procedure ended")
	public void check(
			@Param(name = "intersectionFilePath", description = "The location where the intersection automaton must be saved") String intersectionFilePath) {
		ModelCheckingResults results = new ModelCheckingResults(true, true,
				true);
	
		chia = new CHIA(claim, model, policy, results);

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
		IntersectionBA intersectionAutomaton = chia.getModelChecker()
				.getIntersectionAutomaton();
		IntersectionWriter intersectionWriter = new IntersectionWriter(
				intersectionAutomaton, new File(intersectionFilePath));
		intersectionWriter.write();
	}

	@Command(name = "computeConstraint", abbrev = "cc", description = "Is used to compute the constraint corresponding to the model and the specified claim. ")
	public void computeConstraint() throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException {
		if (!checked) {
			System.out
					.println("You must run the model checker before computing the constraint");
		} else {
			ModelCheckingResults results = new ModelCheckingResults(true, true,
					true);
			
			chia = new CHIA(claim, model, policy, results);
			//this.constraint = this.chia.generateConstraint();
			this.constraint = this.chia.generateConstraintNoPortReachability();
			this.constraintComputed = true;
		}
	}

	@Command(name = "computeConstraint", abbrev = "cc", description = "Is used to compute the constraint corresponding to the model and the specified claim. ")
	public void computeConstraint(
			@Param(name = "-p", description = "if the -p flag is specified the port reachability relation is not computed") String p)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		if (!checked) {
			System.out
					.println("You must run the model checker before computing the constraint");
		} else {
			ModelCheckingResults results = new ModelCheckingResults(true, true,
					true);
			
			chia = new CHIA(claim, model, policy, results);
			this.constraint = this.chia.generateConstraintNoPortReachability();
		}
	}

	@Command(name = "saveConstraint", abbrev = "sc", description = "It is used to save the constraint in an XML file.", header = "constraint saved")
	public void saveConstraint(
			@Param(name = "constraintFilePath", description = "is the path of the file where the constraint must be saved") String constraintFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		if (!this.constraintComputed) {
			System.out
					.println("You must compute the constraint before saving it");
		} else {
			ConstraintWriter constraintWriter = new ConstraintWriter(
					this.constraint, constraintFilePath);
			constraintWriter.write();
		}
	}

	@Command(name = "exit", abbrev = "exit", description = "Returns to the CHIA main console", header = "CHIA Automata console exit")
	public void exit() {
	}
}
