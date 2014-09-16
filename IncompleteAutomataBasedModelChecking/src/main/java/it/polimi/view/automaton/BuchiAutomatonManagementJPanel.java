package it.polimi.view.automaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.xml.bind.JAXBException;

@SuppressWarnings("serial")
public class BuchiAutomatonManagementJPanel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S, T>> extends JPanel {

	private BuchiAutomatonLoadingJPanel<S, T, A> loadingPanel;
	private BuchiAutomatonJPanel<S,T, A> automatonPanel;
	
	public BuchiAutomatonManagementJPanel(){
		super(new FlowLayout());
	}
	
	protected void addGraph(){
		
	}
	public BuchiAutomatonManagementJPanel(Dimension d) throws JAXBException{
		
		 super();
		 this.setBackground(Color.getColor("myColor"));
		 this.setSize(d);
		 this.setMinimumSize(d);
		 this.setMaximumSize(d);
		 this.setPreferredSize(d);
		 this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		 
		 Dimension automatonJPanelDimension=new Dimension(d.width/2, d.height);
		 automatonPanel=this.getAutomatonPanel(automatonJPanelDimension);
		 this.add(automatonPanel);
		 
		 Dimension automatonLoadingPanelDimension=new Dimension(d.width/2, d.height);
		 loadingPanel=new BuchiAutomatonLoadingJPanel<S,T,A>(automatonLoadingPanelDimension);
		 this.add(loadingPanel);
		 
	}
	
	public void update(A a) throws JAXBException{
		this.automatonPanel.update(a);
		this.loadingPanel.update(a);
	}
	
	protected BuchiAutomatonJPanel<S,T, A> getAutomatonPanel(Dimension automatonJPanelDimension){
		 return new  BuchiAutomatonJPanel<S,T, A>(automatonJPanelDimension);
	}

  /*
	protected void addAcationListener(JButton b){
		 b.addActionListener(this);
		
	}
	protected A loadAutomatonFromText(String automaton) throws JAXBException{
		AutomatonBuilder<S, T, A> builderBA=new AutomatonBuilder<S,T,A>();
		
		return (A) builderBA.loadAutomatonFromText(a.getClass(), automaton);
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
	}*/
}
