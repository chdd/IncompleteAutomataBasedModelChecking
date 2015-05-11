package it.polimi;

import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.NormalAcceptingPolicy;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Replacement;
import it.polimi.constraints.io.in.ConstraintReader;
import it.polimi.constraints.io.in.ReplacementReader;
import it.polimi.constraints.io.out.ConstraintToElementTransformer;
import it.polimi.constraints.io.out.ConstraintWriter;
import it.polimi.constraints.io.out.ElementToStringTransformer;
import it.polimi.constraints.io.out.ReplacementToElementTransformer;
import it.polimi.refinementchecker.SubPropertyChecker;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import asg.cliche.Command;
import asg.cliche.Param;

/**
 * contains the console which is used for the replacement checking
 * 
 * @author Claudio1
 *
 */
public class CHIAReplacementConsole {
	
	/**
	 * contains the constraint to be considered
	 */
	protected Constraint constraint;
	/**
	 * contains the replacement to be considered
	 */
	protected Replacement replacement;
	/**
	 * contains the new constraint computed
	 */
	protected Constraint newConstraint;
	/**
	 * is the checker used in the replacement checking activity
	 */
	protected SubPropertyChecker subpropertyChecker;
	/**
	 * is a flag which specifies if a constraint have been loaded from a file
	 */
	private boolean constraintLoaded=false;
	/**
	 * is a flag which specifies if a replacement have been loaded from a file
	 */
	private boolean replacementLoaded=false;
	/**
	 * is a flag which specifies whether the constraint have been considered against the replacement
	 */
	private boolean checked=false;
	/**
	 * is a flag which specifies whether the constraint have been computed
	 */
	private boolean constraintcomputed=false;
	
	AcceptingPolicy policy = new KripkeAcceptingPolicy();


	@Command(name = "loadReplacement", abbrev = "lR", description = "It is used to load the replacement from an XML file. The XML file must mach the Replacement.xsd", header = "replacement loaded")
	public void loadReplacement(
			@Param(name = "replacementFilePath", description = "is the path of the file that contains the replacement to be considered") String replacementFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		this.replacement = new ReplacementReader(replacementFilePath).read();
		replacementLoaded=true;
		checked=false;
		constraintcomputed=false;
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
			
	@Command(name = "loadConstraint", abbrev = "lC", description = "It is used to load the constraint from an XML file. The XML file must mach the Constraint.xsd", header = "constraint loaded")
	public void loadConstraint(
			@Param(name = "constraintFilePath", description = "is the path of the file that contains the constraint to be considered") String constraintFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		this.constraint = new ConstraintReader(constraintFilePath).read();
		constraintLoaded=true;
		checked=false;
		constraintcomputed=false;
	}
	
	@Command(name = "saveConstraint", abbrev = "sc", description = "It is used to save the constraint in an XML file.", header = "constraint saved")
	public void saveConstraint(
			@Param(name = "constraintFilePath", description = "is the path of the file where the constraint must be saved") String constraintFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		if(!constraintcomputed){
			System.out.println("You must compute the constraint before savinf it");
		}
		else{
			ConstraintWriter constraintWriter=new ConstraintWriter(this.constraint, constraintFilePath);
			constraintWriter.write();
		}
	}
	@Command(name = "displayConstraint", abbrev = "dispC", description = "It is used to display the constraint into the console.", header = "Constraint displayed")
	public void dispConstraint() throws Exception{
		if(!constraintLoaded){
			System.out.println("You must load the constraint before checking it");
			return;
		}
		System.out.println(new ElementToStringTransformer().transform(new ConstraintToElementTransformer().transform(this.constraint)));
	}
	
	@Command(name = "displayReplacement", abbrev = "dispR", description = "It is used to display the replacement into the console.", header = "Replacement displayed")
	public void dispReplacement() throws Exception{
		if(!this.replacementLoaded){
			System.out.println("You must load the replacement before checking it");
			return;
		}
		System.out.println(new ElementToStringTransformer().transform(new ReplacementToElementTransformer().transform(this.replacement)));
	}
	
	@Command(name = "replacementChecking", abbrev = "rck", description = "Is used to check the replacement against the constraint previously generated.", header = "Performing the replacement checking")
	public void replacementChecking(){
		// TODO
		/*
		if(!constraintLoaded){
			System.out.println("You must load the constraint before checking it");
			return;
		}
		if(!this.replacementLoaded){
			System.out.println("You must load the replacement before checking it");
			return;
		}
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
		}*/
	}
	
	@Command(name = "subpropertyChecking", abbrev = "subck", description = " is used to check the replacement  against the corresponding sub-property. It does not update the corresponding constraint.", header = "Performing the sub-property checking")
	public void subpropertyChecking(){
		if(!constraintLoaded){
			System.out.println("You must load the constraint before checking it");
			return;
		}
		if(!this.replacementLoaded){
			System.out.println("You must load the replacement before checking it");
			return;
		}
		if(!this.constraint.isConstrained(replacement.getModelState())){
			System.out.println("There are no constraints associated to the state "+replacement.getModelState()+" the property is trivially satisfied");
			return;
		}
		
		subpropertyChecker=new SubPropertyChecker(constraint.getSubproperty(replacement.getModelState()), replacement, this.policy);
		int result=subpropertyChecker.check();
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
		
		//TODO
		/*if(!constraintLoaded){
			System.out.println("You must load the constraint before checking it");
			return;
		}
		if(!this.replacementLoaded){
			System.out.println("You must load the replacement before checking it");
			return;
		}
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
		intersectionWriter.write();*/
	}
	
	
	@Command(name = "replacementComputeConstraint", abbrev = "rcc", description = "Is used to check the replacement against the constraint previously generated.", header = "replacement checking performed")
	public void replacementComputeConstraint(){
		//TODO
		/*if(!checked){
			System.out.println("You must run the model checker before computing the constraint");
			return;
		}
		ReplacementConstraintComputation replacementConstraintComputation = new ReplacementConstraintComputation(replacementChecker);
		replacementConstraintComputation.constraintComputation();
		this.newConstraint=replacementConstraintComputation.newConstraint();*/
	}
	
	@Command(name = "saveReplacementConstraint", abbrev = "rsc", description = "It is used to save the constraint in an XML file.", header = "constraint saved")
	public void replacementSaveConstraint(
			@Param(name = "constraintFilePath", description = "is the path of the file where the constraint must be saved") String constraintFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		if(!constraintcomputed){
			System.out.println("You must compute the constraint before saving it");
			return;
		}
		ConstraintWriter constraintWriter=new ConstraintWriter(this.newConstraint, constraintFilePath);
		constraintWriter.write();
	}
	@Command(name ="exit", abbrev = "exit", description = "Returns to the CHIA main console", header = "CHIA Automata console exit")
	public void exit(){
	}
}
