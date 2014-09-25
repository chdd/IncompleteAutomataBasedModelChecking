package it.polimi.view;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.ModelCheckerParameters;
import it.polimi.view.automaton.BuchiAutomatonManagementJPanel;
import it.polimi.view.incompleteautomaton.IncompleteBuchiAutomatonManagementJPanel;
import it.polimi.view.intersectionautomaton.IntersectionAutomatonManagementJPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.bind.JAXBException;

public class View<S1 extends State, T1 extends Transition<S1>, S extends IntersectionState<S1>, T extends Transition<S>> extends Observable implements ViewInterface<S1,T1,S,T>, ActionListener{

	private IncompleteBuchiAutomatonManagementJPanel<S1,T1, IncompleteBuchiAutomaton<S1, T1>>  modelPanel;
	private BuchiAutomatonManagementJPanel<S1,T1, BuchiAutomaton<S1, T1>>  specificationPanel;
	private IntersectionAutomatonManagementJPanel<S1, T1, S, T, IntersectionAutomaton<S1, T1, S, T>> intersectionPanel;
	private JFrame jframe;
	public View() throws JAXBException {
		 jframe=new JFrame();
		 
		 System.setProperty("myColor", "0XAABBCC");
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 jframe.getContentPane().setBackground(Color.getColor("myColor"));
		 jframe.setSize(screenSize);
		 Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(jframe.getGraphicsConfiguration());
		 
		 Dimension contentPanelSize=new Dimension(screenSize.width - scnMax.left -scnMax.right, screenSize.height - scnMax.bottom -scnMax.top);	
		 
		 JPanel container = new JPanel();
		 JScrollPane scrPane = new JScrollPane(container);
		 jframe.add(scrPane);
		 
		 Dimension automatonManagementPanelDimension=new Dimension(contentPanelSize.width, contentPanelSize.height/3);
		 
		 container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
		
		 modelPanel=new  IncompleteBuchiAutomatonManagementJPanel<S1,T1, IncompleteBuchiAutomaton<S1, T1>> (automatonManagementPanelDimension, this);
		 modelPanel.setSize(automatonManagementPanelDimension);
		 modelPanel.setMinimumSize(automatonManagementPanelDimension);
		 modelPanel.setMaximumSize(automatonManagementPanelDimension);
		 modelPanel.setPreferredSize(automatonManagementPanelDimension);
		 container.add(modelPanel);
		 container.setVisible(true);
		 
		 specificationPanel= new  BuchiAutomatonManagementJPanel<S1,T1, BuchiAutomaton<S1, T1>> (automatonManagementPanelDimension, this);
		 specificationPanel.setSize(automatonManagementPanelDimension);
		 specificationPanel.setMinimumSize(automatonManagementPanelDimension);
		 specificationPanel.setMaximumSize(automatonManagementPanelDimension);
		 specificationPanel.setPreferredSize(automatonManagementPanelDimension);
		 container.add(specificationPanel);
		 container.setVisible(true);
		 
		
		intersectionPanel=
				new IntersectionAutomatonManagementJPanel<S1, T1,S, T, IntersectionAutomaton<S1, T1, S, T>>(automatonManagementPanelDimension, this);
		 intersectionPanel.setSize(automatonManagementPanelDimension);
		 intersectionPanel.setMinimumSize(automatonManagementPanelDimension);
		 intersectionPanel.setMaximumSize(automatonManagementPanelDimension);
		 intersectionPanel.setPreferredSize(automatonManagementPanelDimension);
		 container.add(intersectionPanel);
		 container.setVisible(true);
		 jframe.add(container);
		 jframe.setResizable(true);
		 jframe.setVisible(true);
		 

	
	}
	
	@Override
	public void updateModel(IncompleteBuchiAutomaton<S1, T1> model){
		this.modelPanel.updateAutomatonPanel(model);
		this.modelPanel.updateLoadingPanel(model);
		jframe.repaint();
	}
	@Override
	public void updateSpecification(BuchiAutomaton<S1, T1> specification){
		this.specificationPanel.updateAutomatonPanel(specification);
		this.specificationPanel.updateLoadingPanel(specification);
		jframe.repaint();
		
	}

	@Override
	public void updateIntersection(IntersectionAutomaton<S1, T1,S,T> intersection){
		this.intersectionPanel.updateAutomatonPanel(intersection);
		jframe.repaint();
		
	}

	@Override
	public void updateVerificationResults(ModelCheckerParameters<S1> verificationResults) {
		this.intersectionPanel.updateVerificationResults(verificationResults);
		jframe.repaint();
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setChanged();
		this.notifyObservers(e);
	}

	@Override
	public String getFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    return selectedFile.getAbsolutePath();
		}
		return null;
	}
	@Override
	public String createFile(){
		 FileDialog fDialog = new FileDialog(this.jframe, "Save", FileDialog.SAVE);
	     fDialog.setVisible(true);
	     return fDialog.getDirectory() + fDialog.getFile();
	}

	@Override
	public String getModelXML() {
		return this.modelPanel.getAutomatonXML();
	}

	@Override
	public String getSpecificationXML() {
		return this.specificationPanel.getAutomatonXML();
	}
	
	public void displayErrorMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	
	
}
