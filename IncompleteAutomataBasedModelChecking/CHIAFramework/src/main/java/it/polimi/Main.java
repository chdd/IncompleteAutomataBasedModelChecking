package it.polimi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import asg.cliche.Command;
import asg.cliche.ShellFactory;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.BAReader;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.io.out.ConstraintWriter;

public class Main {

	
	
	public static void main(String[] args) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
		
		ShellFactory.createConsoleShell("CHIA", null, new CHIAConsole())
        .commandLoop();
		
/*Config.init(args);
//		Config config = Config.getInstance();
		
		ModelCheckingResults results=new ModelCheckingResults(true, true, true);
		CHIA chia=new CHIA(claim, model, results);
		int result=chia.check();
		System.out.println("The model checking result is "+result);
		
		config.
		if(config.intersectionPath!=null){
			chia.getMcResults()
			new WriterBA(intersectionAutomaton, f)
			
		}
		
		if(result==-1){
			Constraint constraint=chia.getConstraint();
			ConstraintWriter constraintWriter=new ConstraintWriter(constraint, constraintFile);
			constraintWriter.write();
		}	*/
	}
}
