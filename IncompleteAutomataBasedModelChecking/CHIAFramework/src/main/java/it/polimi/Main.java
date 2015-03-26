package it.polimi;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import asg.cliche.ShellFactory;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
		
		ShellFactory.createConsoleShell("CHIA", null, new CHIAConsole())
        .commandLoop();
	}
}
