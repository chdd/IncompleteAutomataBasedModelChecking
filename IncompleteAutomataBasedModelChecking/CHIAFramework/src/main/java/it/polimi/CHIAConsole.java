package it.polimi;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.console.CHIAReplacementConsole;

import java.io.IOException;

import asg.cliche.Command;
import asg.cliche.ShellFactory;

public class CHIAConsole {

	
	@Command(name = "automata", abbrev = "aut", description = "Enters the automata mode", header = "automata mode")
	public void automataMode() throws IOException{
		ShellFactory.createConsoleShell("CHIAAutomata", null, new CHIAAutomataConsole())
        .commandLoop();
	}
	@Command(name = "replacement", abbrev = "rep", description = "Enters the replacement mode", header = "replacement mode")
	public void replacementMode() throws IOException{
		ShellFactory.createConsoleShell("CHIAReplacement", null, new CHIAReplacementConsole())
        .commandLoop();
	}
	@Command(name ="exit", abbrev = "exit", description = "Exits CHIA", header = "exit CHIA")
	public void exit(){
	}
}