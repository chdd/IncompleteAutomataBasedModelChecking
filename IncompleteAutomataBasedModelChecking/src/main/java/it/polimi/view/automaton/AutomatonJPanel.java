package it.polimi.view.automaton;

import it.polimi.model.AutomatonBuilder;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileSystemView;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

@SuppressWarnings("serial")
public class AutomatonJPanel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S, T>> extends JPanel implements ActionListener {

	protected AutJLabel<S,T,A> label;
	protected AutXMLTextArea<S,T,A> area;
	protected A  a;
	
	public A getAutomaton(){
		return this.a;
	}
	public AutomatonJPanel(){
		super(null);
	}
	
	protected void addGraph(){
		
	}
	public AutomatonJPanel(Dimension d, A  a ) throws JAXBException{
	
		 super(null);
		 this.a=a;
		 this.setSize(d);
		 this.setBorder(BorderFactory.createLineBorder(Color.black));
		 label=new AutJLabel<S,T,A>(new Dimension(d.width/2,d.height/2), a);
		 label.setVisible(true);
		 label.setSize(new Dimension(d.width/2,d.height/2));
		 this.add(label); 
		 this.label.setVisible(true);
		 
		
		 this.createButtons(d);
		 
		 area=new AutXMLTextArea<S,T,A>(new Dimension(d.width/3,d.height/4), a);
		 //this.add(area);
		 JScrollPane scroll = new JScrollPane(area);
		 scroll.setSize(new Dimension(d.width/3+200,d.height/4));
		 scroll.setLocation(d.width/2, d.height/2/5+2*d.height/2/5/20);
		 scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		 this.add(scroll); 
		  this.revalidate();
		 this.setVisible(true);
		 
	}
	protected void createButtons(Dimension d){
		 Dimension buttonDimension=new Dimension(d.width/2/4, d.height/2/5);
		 JButton btnInsert = new JButton("Update Automaton");
		 btnInsert.setSize(buttonDimension);
		 btnInsert.setOpaque(true);
		 btnInsert.setLocation(d.width/2, d.height/3/5/20);
		 btnInsert.setVisible(true);
		 this.addAcationListener(btnInsert);
		 this.add(btnInsert);
		 
		 JButton btnLoad = new JButton("Open");
		 btnLoad.setSize(buttonDimension);
		 btnLoad.setOpaque(true);
		 btnLoad.setLocation(d.width/2+buttonDimension.width, d.height/2/5/20);
		 btnLoad.setVisible(true);
		 this.addAcationListener(btnLoad);
		 this.add(btnLoad);
		 
		 JButton btnSave = new JButton("Save");
		 btnSave.setSize(buttonDimension);
		 btnSave.setOpaque(true);
		 btnSave.setLocation(d.width/2+(buttonDimension.width*2), d.height/2/5/20);
		 btnSave.setVisible(true);
		 this.addAcationListener(btnSave);
		 this.add(btnSave);
	}
	protected void addAcationListener(JButton b){
		 b.addActionListener(this);
		
	}
	
	@SuppressWarnings({ "static-access", "unchecked" })
	protected A loadAutomatonFromText(String automaton) throws JAXBException{
		System.out.println("Entro automaton");
		
		return (A) a.loadAutomatonFromText(automaton);
	}
	@SuppressWarnings("unchecked")
	protected A loadAutomatonFromFile(String filePath) throws JAXBException, SAXException, IOException, ParserConfigurationException{
		AutomatonBuilder<State, Transition<State>, BuchiAutomaton<State, Transition<State>>> builder=
				new AutomatonBuilder<State, Transition<State>, BuchiAutomaton<State, Transition<State>>>();
		
		return (A) builder.loadAutomaton(BuchiAutomaton.class, filePath);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Update Automaton"){
			try {
				this.a=this.loadAutomatonFromText(this.area.getText());
				this.label.updateGraph(a);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
		}
		else if(e.getActionCommand() == "Open"){
			try {
				//Create a file chooser
				JFileChooser fc = new JFileChooser();
				//In response to a button click:
				fc.showOpenDialog(this);
				String path = fc.getSelectedFile().getPath();
				this.a=this.loadAutomatonFromFile(path);
				this.label.updateGraph(a);
				this.area.setText(a.toXMLString());
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
		}
		else if(e.getActionCommand() == "Save"){
			  JFileChooser c = new JFileChooser(FileSystemView.getFileSystemView());
			  c.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			  // Demonstrate "Open" dialog:
		      int rVal = c.showOpenDialog(this);
		      if (rVal == JFileChooser.APPROVE_OPTION) {
		    	  String file=c.getSelectedFile().getName();
		    	  String folder=c.getCurrentDirectory().toString();
		    	 
		    	  try {
					this.a=this.loadAutomatonFromText(this.area.getText());
					this.a.toFile(folder+"/"+file);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
		    }
		}
		else{
			JOptionPane.showMessageDialog(null, "event: "+e.getActionCommand()+" not supported");
		}
	}
}
