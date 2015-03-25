package it.polimi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.BAReader;
import it.polimi.automata.io.IBAReader;
import it.polimi.automata.io.WriterBA;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.io.out.ConstraintWriter;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
		
		Config.init(args);
		Config config = Config.getInstance();
		
		File modelFile=new File(config.modelPath);
		File claimFile=new File(config.claimPath);
		File constraintFile=new File(config.constraintPath);
		
		IBA model=new IBAReader(modelFile).read();
		BA claim=new BAReader(claimFile).read();
		ModelCheckingResults results=new ModelCheckingResults(true, true, true);
		CHIA chia=new CHIA(claim, model, results);
		int result=chia.check();
		System.out.println("The model checking result is "+result);
		
		if(config.intersectionPath!=null){
			chia.getMcResults()
			new WriterBA(intersectionAutomaton, f)
			
		}
		
		if(result==-1){
			Constraint constraint=chia.getConstraint();
			ConstraintWriter constraintWriter=new ConstraintWriter(constraint, constraintFile);
			constraintWriter.write();
		}	
	}
}
