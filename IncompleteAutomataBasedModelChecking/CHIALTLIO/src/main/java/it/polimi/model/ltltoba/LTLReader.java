package it.polimi.model.ltltoba;

import it.polimi.automata.BA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import action.CHIAAction;

import com.google.common.base.Preconditions;

public class LTLReader extends CHIAAction{
	
	private String filePath;
	
	private static final String NAME="LOADING FROM LTL FILE";
	
	/**
	 * creates a new reader that reads the LTL formula from a file
	 * @param filePath
	 */
	public LTLReader(String filePath){
		super(NAME);
		Preconditions.checkNotNull(filePath, "The path of the file cannot be null");

		this.filePath=filePath;
	}
	
	public BA loadLTLFromFile() throws IOException{
		
		String ltlFormula="";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        boolean first=true;
	        while (line != null) {
	        	if(!line.startsWith("//") && !line.isEmpty()){
	        		if(first){
	        			sb.append(line);
	        			first=false;
	        		}
	        		else{
	        			sb.append("&&"+line);
	        		}
	        		
	        	} 
		        line = br.readLine();
	        }
	        ltlFormula = sb.toString();
	    } finally {
	        br.close();
	    }
		
	    System.out.println(ltlFormula);
		BA ba=new LTLtoBATransformer().transform("!("+ltlFormula+")");
		
		return ba;
	}


}
