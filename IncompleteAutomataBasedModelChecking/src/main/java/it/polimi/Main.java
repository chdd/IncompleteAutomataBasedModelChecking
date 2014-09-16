package it.polimi;

import it.polimi.controller.Controller;
import it.polimi.controller.ControllerInterface;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.View;
import it.polimi.view.ViewInterface;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

public class Main extends JFrame {

	private static final String model="src//main//resources//ExtendedAutomaton1.xml";
	private static final String specification="src//main//resources//Automaton2.xml";
	
	/**
	 * 
	 */
	 
	private static final long serialVersionUID = 1L;

	public static void main(String args[]) throws JAXBException, FileNotFoundException, XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		
		ViewInterface view=new View<State, Transition<State>,IntersectionState<State>, Transition<IntersectionState<State>>>();
		ControllerInterface controller=new Controller(model, specification, view);
		
	}
}
