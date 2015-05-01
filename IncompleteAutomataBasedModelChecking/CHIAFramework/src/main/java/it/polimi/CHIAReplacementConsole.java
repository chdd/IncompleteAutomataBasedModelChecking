package it.polimi;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.out.IntersectionWriter;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Replacement;
import it.polimi.constraints.io.in.ConstraintReader;
import it.polimi.constraints.io.in.ReplacementReader;
import it.polimi.constraints.io.out.ConstraintWriter;
import it.polimi.refinement.constraintcomputation.ReplacementConstraintComputation;
import it.polimi.refinementchecker.ReplacementChecker;

import java.io.File;
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
	protected ReplacementChecker replacementChecker;
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

	@Command(name = "loadReplacement", abbrev = "lr", description = "It is used to load the replacement from an XML file. The XML file must mach the Replacement.xsd", header = "replacement loaded")
	public void loadReplacement(
			@Param(name = "replacementFilePath", description = "is the path of the file that contains the replacement to be considered") String replacementFilePath)
			throws FileNotFoundException, ParserConfigurationException,
			SAXException, IOException {
		this.replacement = new ReplacementReader(replacementFilePath).read();
		replacementLoaded=true;
		checked=false;
		constraintcomputed=false;
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
	
	@Command(name = "replacementChecking", abbrev = "rck", description = "Is used to check the replacement against the constraint previously generated.", header = "Performing the replacement checking")
	public void replacementChecking(){
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
		}
	}
	
	@Command(name = "replacementChecking", abbrev = "rck", description = "Is used to check the replacement against the constraint previously generated", header = "Performing the replacement checking")
	public void replacementChecking(
			@Param(name = "intersectionFilePath", description = "The location where the intersection automaton must be saved") String intersectionFilePath) {
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
		}
		IntersectionBA intersectionAutomaton=replacementChecker.getChecker().getIntersectionAutomaton();
		IntersectionWriter intersectionWriter=new IntersectionWriter(intersectionAutomaton, new File(intersectionFilePath));
		intersectionWriter.write();
	}
	
	
	@Command(name = "replacementComputeConstraint", abbrev = "rcc", description = "Is used to check the replacement against the constraint previously generated.", header = "replacement checking performed")
	public void replacementComputeConstraint(){
		if(!checked){
			System.out.println("You must run the model checker before computing the constraint");
			return;
		}
		ReplacementConstraintComputation replacementConstraintComputation = new ReplacementConstraintComputation(replacementChecker);
		replacementConstraintComputation.constraintComputation();
		this.newConstraint=replacementConstraintComputation.newConstraint();
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
