package it.polimi;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import asg.cliche.ShellFactory;

/**
 * Contains the main of the application
 * @author Claudio1
 *
 */
public class Main {

	public static void main(String[] args) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
		
		ShellFactory.createConsoleShell("CHIA", null, new CHIAConsole())
        .commandLoop();
	}
}
