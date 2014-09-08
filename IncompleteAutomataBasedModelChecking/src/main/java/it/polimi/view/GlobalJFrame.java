package it.polimi.view;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.automaton.AutomatonJPanel;
import it.polimi.view.extendedautomaton.ExtendedAutomatonJPanel;
import it.polimi.view.intersectionautomaton.IntersectionAutJPanel;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

public class GlobalJFrame<S1 extends State, T1 extends Transition<S1>, S extends IntersectionState<S1>, T extends Transition<S>> extends JFrame {

	private static final String arg0="src//main//resources//ExtendedAutomaton1.xml";
	private static final String arg1="src//main//resources//Automaton2.xml";
	/**
	 * 
	 */
	 
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("static-access")
	public GlobalJFrame(IncompleteBuchiAutomaton<S1, T1>  a1, BuchiAutomaton<S1, T1>  a2, IntersectionAutomaton<S1, T1, S, T> a) throws JAXBException{
		
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 this.setSize(new Dimension(screenSize.width, screenSize.height));
		 this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		 
		 JPanel grid = new JPanel(null);
		 
		 ExtendedAutomatonJPanel<S1,T1, IncompleteBuchiAutomaton<S1, T1>>  panel1=new  ExtendedAutomatonJPanel<S1,T1, IncompleteBuchiAutomaton<S1, T1>> (new Dimension(screenSize.width,screenSize.height), a1);
		 panel1.setSize(new Dimension(screenSize.width,screenSize.height/3));
		 grid.add(panel1);
		 
		 AutomatonJPanel<S1,T1, BuchiAutomaton<S1, T1>>  panel2=new  AutomatonJPanel<S1,T1, BuchiAutomaton<S1, T1>> (new Dimension(screenSize.width,screenSize.height), a2);
		 panel2.setSize(new Dimension(screenSize.width,screenSize.height/3));
		 grid.add(panel2);
		 panel2.setLocation(0, screenSize.height/3);
		 
		 IntersectionAutJPanel<S1,T1, S, T, IntersectionAutomaton<S1, T1, S, T>>  panel3=new  IntersectionAutJPanel<S1,T1, S, T, IntersectionAutomaton<S1, T1, S, T>> (new Dimension(screenSize.width,screenSize.height), a, panel1, panel2);
		 panel3.setSize(new Dimension(screenSize.width,screenSize.height/3));
		 grid.add(panel3);
		 panel3.setLocation(0, screenSize.height/3*2);

		 this.add(grid);
		 grid.setVisible(true);
		 
		 this.revalidate();
		 this.repaint();
		 this.setVisible(true);
	}
	
	@SuppressWarnings("unused")
	public static void main(String args[]) throws JAXBException, FileNotFoundException, XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		
		
		IncompleteBuchiAutomaton<State, Transition<State>> a1 = IncompleteBuchiAutomaton.loadAutomaton(arg0);
		
		BuchiAutomaton<State, Transition<State>>  a2 = BuchiAutomaton.loadAutomaton(arg1);
		IntersectionAutomaton<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> ris=a1.computeIntersection(a2);
		
		GlobalJFrame<State, Transition<State>, IntersectionState<State>,Transition<IntersectionState<State>>> frame=
				  new GlobalJFrame<State, Transition<State>, IntersectionState<State>,Transition<IntersectionState<State>>>(a1, a2, ris);
	}
		
	
	
}
