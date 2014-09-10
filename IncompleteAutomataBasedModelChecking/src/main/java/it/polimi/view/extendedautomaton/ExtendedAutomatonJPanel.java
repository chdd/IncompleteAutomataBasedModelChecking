package it.polimi.view.extendedautomaton;

import it.polimi.model.AutomatonBuilder;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.automaton.AutXMLTextArea;
import it.polimi.view.automaton.AutomatonJPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

@SuppressWarnings("serial")
public class ExtendedAutomatonJPanel<S extends State, T extends Transition<S>, A extends IncompleteBuchiAutomaton<S, T>> extends AutomatonJPanel<S, T, A>  {

	public A getAutomaton(){
		return this.a;
	}
	
	public ExtendedAutomatonJPanel(Dimension d, A  a ) throws JAXBException{
		 super();
		 this.a=a;
		 this.setSize(d);
		 this.setBorder(BorderFactory.createLineBorder(Color.black));
		 
		 label=new ExtendedAutJLabel<S,T,A>(new Dimension(d.width/2,d.height/2), a);
		 label.setVisible(true);
		 label.setSize(new Dimension(d.width/2,d.height/2));
		 this.label.setVisible(true);
		 this.label.setLocation(0,0);
		 this.add(label); 
		 
		 this.createButtons(d);
			
		 area=new AutXMLTextArea<S,T,A>(new Dimension(d.width/3,d.height/4), a);
		 JScrollPane scroll = new JScrollPane(area);
		 scroll.setSize(new Dimension(d.width/3+200,d.height/4));
		 scroll.setLocation(d.width/2, d.height/2/5+2*d.height/2/5/20);
		 scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		 this.add(scroll);
		  
		 this.revalidate();
		 this.setVisible(true);
		 
	}	
	@SuppressWarnings({ "static-access", "unchecked" })
	protected A loadAutomatonFromText(String automaton) throws JAXBException{
		System.out.println("Entro extended");
		
		return (A) a.loadAutomatonFromText(automaton);
	}
	@SuppressWarnings("unchecked")
	protected A loadAutomatonFromFile(String filePath) throws JAXBException, SAXException, IOException, ParserConfigurationException{
		AutomatonBuilder<State, Transition<State>, IncompleteBuchiAutomaton<State, Transition<State>>> builderIBA=
				new AutomatonBuilder<State, Transition<State>, IncompleteBuchiAutomaton<State, Transition<State>>>();
		return (A) builderIBA.loadAutomaton(IncompleteBuchiAutomaton.class, filePath);
		
	}
	protected void addAcationListener(JButton b){
		 b.addActionListener(this);
	}
	public Component add(Component comp) {
        return super.add(comp);
    }
	
}
