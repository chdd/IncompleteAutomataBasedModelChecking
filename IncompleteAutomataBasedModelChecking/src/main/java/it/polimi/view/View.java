package it.polimi.view;

import it.polimi.controller.actions.CheckAction;
import it.polimi.controller.actions.LoadClaimAction;
import it.polimi.controller.actions.file.loading.LoadModel;
import it.polimi.controller.actions.file.loading.LoadSpecification;
import it.polimi.controller.actions.file.saving.SaveIntersection;
import it.polimi.controller.actions.file.saving.SaveModel;
import it.polimi.controller.actions.file.saving.SaveSpecification;
import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.ModelCheckerParameters;
import it.polimi.view.automaton.BuchiAutomatonJPanel;
import it.polimi.view.automaton.IncompleteBuchiAutomatonJPanel;
import it.polimi.view.automaton.IntersectionAutomatonJPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class View<S1 extends State, 
			T1 extends LabelledTransition, 
			S extends IntersectionState<S1>, 
			T extends ConstrainedTransition<S1>,
			LABELLEDTRANSITIONFACTORY extends LabelledTransitionFactory<T1>,
			CONSTRAINTTRANSITIONFACTORY extends ConstrainedTransitionFactory<S1, T>> extends Observable implements ViewInterface<S1,T1,S,T, LABELLEDTRANSITIONFACTORY, CONSTRAINTTRANSITIONFACTORY>, ActionListener{

	// Icons
	private final ImageIcon openIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/actions/document-open.png"));
	private final ImageIcon saveIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/devices/media-floppy.png"));
	private final ImageIcon ltlIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/devices/input-keyboard.png"));
	private final ImageIcon developmentIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/categories/applications-office.png"));
	private final ImageIcon editIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/actions/view-fullscreen.png"));
	private final ImageIcon checkIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/categories/applications-system.png"));
	
	// Messages
	private final String editingMessage="<html>Editing Mode:<br>"
			+ "MouseButtonOne press on empty space creates a new State<br>"
			+ "MouseButtonOne press on a State, followed by a drag to another State creates an directed transition between them<br>"
			+ "Right click on the state or on the transition to modify its properties<br></html>";
	private final String transorfmingMessage="<html>Transorming Mode:<br>"
			+ "MouseButtonOne+drag to translate the display<br>"
			+ "MouseButtonOne+Shift+drag to rotate the display<br>"
			+ "MouseButtonOne+ctrl(or Command)+drag to shear the display<br>"
			+ "Position the mouse on a state and press p to enter the state selection mode that allows to move the states<br>"
			+ "Press t to exit the state selection mode<br></html>";
	
	// model
	private JButton openModelButton;
	private JButton saveModelButton;
	private JButton modelEditingButton;
	private JButton modelTrasformingButton;
	private IncompleteBuchiAutomatonJPanel<S1,T1,LABELLEDTRANSITIONFACTORY,DrawableIBA<S1,T1, LABELLEDTRANSITIONFACTORY>> modelTabmodel;
	
	// claim
	private JButton openClaimButton;
	private JButton saveClaimButton;
	private JButton ltlLoadClaimButton;
	
	private JButton claimEditingButton;
	private JButton claimTransformingButton;
	private BuchiAutomatonJPanel<S1,T1, LABELLEDTRANSITIONFACTORY, DrawableBA<S1, T1, LABELLEDTRANSITIONFACTORY>>  claimTabClaimPanel;

	// verification results
	private JButton openIntersectionButton;
	private JButton saveIntersectionButton;
	private JButton checkButton;
	private IntersectionAutomatonJPanel<S1, T1, S, T, LABELLEDTRANSITIONFACTORY, CONSTRAINTTRANSITIONFACTORY, DrawableIntBA<S1, T1, S, T, CONSTRAINTTRANSITIONFACTORY>> verificationResultsIntersection;
	private TextArea brzozowskiSystem;

	// verification snapshot
	private IncompleteBuchiAutomatonJPanel<S1,T1, LABELLEDTRANSITIONFACTORY, DrawableIBA<S1,T1, LABELLEDTRANSITIONFACTORY>> verificationSnapshotModelPanel;
	private BuchiAutomatonJPanel<S1,T1, LABELLEDTRANSITIONFACTORY, DrawableBA<S1, T1, LABELLEDTRANSITIONFACTORY>>  verificationSnapshotClaimPanel;
	private IntersectionAutomatonJPanel<S1, T1, S, T, LABELLEDTRANSITIONFACTORY, CONSTRAINTTRANSITIONFACTORY, DrawableIntBA<S1, T1, S, T, CONSTRAINTTRANSITIONFACTORY>> verificationSnapshotIntersectionPanel;
	private ResultsJPanel<S1, T1, S, T, LABELLEDTRANSITIONFACTORY, CONSTRAINTTRANSITIONFACTORY,IntBAImpl<S1, T1, S, T, LABELLEDTRANSITIONFACTORY, CONSTRAINTTRANSITIONFACTORY >> verificationSnapshotResultsPanel;
	
	// main frame
	private JFrame jframe;
	
	public View(DrawableIBA<S1, T1, LABELLEDTRANSITIONFACTORY> model,
			DrawableBA<S1, T1, LABELLEDTRANSITIONFACTORY> claim,
			DrawableIntBA<S1, T1,S,T, CONSTRAINTTRANSITIONFACTORY> intersection) {
		
		 this.jframe=new JFrame();
		 // setting the size of the jframe
		 this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.jframe.getContentPane().setBackground(Color.getColor("myColor"));
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 this.jframe.setSize(screenSize);
		 
		
		 
		 // creating the menu bar
		 this.createMenuBar(jframe);
		 
		 
		 JTabbedPane tabbedPane = new JTabbedPane();
		 tabbedPane.setSize(this.jframe.getContentPane().getSize());
		 tabbedPane.setPreferredSize(this.jframe.getContentPane().getSize());
		 
		 JComponent modelTab = makeTextPanel("Model");
		 tabbedPane.addTab("Model",modelTab);
		 tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
		
		 JComponent claimTab = makeTextPanel("Claim");
		 tabbedPane.addTab("Claim", claimTab);
		 tabbedPane.setMnemonicAt(1, KeyEvent.VK_3);
		 
		 
		 JComponent verificationResultsTab= makeTextPanel("Intersection");
		 tabbedPane.addTab("Intersection", verificationResultsTab);
		 tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		 
		 JComponent verificationSnapshotTab = makeTextPanel("Verification");
		 tabbedPane.addTab("Verification", verificationSnapshotTab);
		 tabbedPane.setMnemonicAt(3, KeyEvent.VK_1);
		 verificationSnapshotTab.setLayout(new BoxLayout(verificationSnapshotTab,BoxLayout.X_AXIS));
		 
		 this.jframe.add(tabbedPane);
		 
		 		
		 JPanel container = new JPanel();
		 container.setAutoscrolls(false);
		 
		 
		 JScrollPane scrPane = new JScrollPane(container);
		 jframe.add(scrPane);
		 
		 
		 container.setLayout(new BoxLayout(container,BoxLayout.X_AXIS));
		 container.add(tabbedPane);
		 
		 
		 //******************************************************************************************************************************
		 // model tab
		 //******************************************************************************************************************************
		 modelTab.setLayout(new BoxLayout(modelTab, BoxLayout.PAGE_AXIS));
		 JPanel containerModelMenu=new JPanel();
		 containerModelMenu.setLayout(new BoxLayout(containerModelMenu, BoxLayout.X_AXIS));
		 
		 this.openModelButton=new JButton(openIcon);
		 this.openModelButton.addActionListener(this);
		 this.openModelButton.setFocusPainted(false);
		 containerModelMenu.add(this.openModelButton);
		 
		 this.saveModelButton=new JButton(saveIcon);
		 this.saveModelButton.addActionListener(this);
		 this.saveModelButton.setFocusPainted(false);
		 containerModelMenu.add(saveModelButton);
		 
		 this.modelEditingButton=new JButton(this.developmentIcon);
		 this.modelEditingButton.addActionListener(this);
		 this.modelEditingButton.setFocusPainted(true);
		 this.modelEditingButton.setToolTipText(this.editingMessage);
		 containerModelMenu.add(this.modelEditingButton);
		 
		 this.modelTrasformingButton=new JButton(this.editIcon);
		 this.modelTrasformingButton.addActionListener(this);
		 this.modelTrasformingButton.setFocusPainted(false);
		 this.modelTrasformingButton.setToolTipText(this.transorfmingMessage);
		 
		 containerModelMenu.add(this.modelTrasformingButton);
		 modelTab.add(containerModelMenu);
		 
		 this.modelTabmodel=new IncompleteBuchiAutomatonJPanel<S1,T1, LABELLEDTRANSITIONFACTORY, DrawableIBA<S1,T1, LABELLEDTRANSITIONFACTORY>>(model, this);
		 modelTab.add(modelTabmodel);
		 
		 //******************************************************************************************************************************
		 // claim tab
		 //******************************************************************************************************************************
		 claimTab.setLayout(new BoxLayout(claimTab, BoxLayout.PAGE_AXIS));
		 
		 JPanel containerClaimMenu=new JPanel();
		 containerClaimMenu.setLayout(new BoxLayout(containerClaimMenu, BoxLayout.X_AXIS));
		 
		 this.openClaimButton=new JButton(openIcon);
		 this.openClaimButton.addActionListener(this);
		 this.openClaimButton.setFocusPainted(false);
		 containerClaimMenu.add(this.openClaimButton);
		 
		 this.saveClaimButton=new JButton(saveIcon);
		 this.saveClaimButton.addActionListener(this);
		 this.saveClaimButton.setFocusPainted(false);
		 containerClaimMenu.add(this.saveClaimButton);
		
		 this.ltlLoadClaimButton=new JButton(this.ltlIcon);
		 this.ltlLoadClaimButton.addActionListener(this);
		 this.ltlLoadClaimButton.setFocusPainted(false);
		 containerClaimMenu.add(this.ltlLoadClaimButton);
		 
		 this.claimEditingButton=new JButton(this.developmentIcon);
		 this.claimEditingButton.addActionListener(this);
		 this.claimEditingButton.setFocusPainted(true);
		 this.claimEditingButton.setToolTipText(this.editingMessage);
		 containerClaimMenu.add(this.claimEditingButton);
		 
		 this.claimTransformingButton=new JButton(this.editIcon);
		 this.claimTransformingButton.addActionListener(this);
		 this.claimTransformingButton.setFocusPainted(false);
		 this.claimTransformingButton.setToolTipText(this.transorfmingMessage);
		 containerClaimMenu.add(this.claimTransformingButton);
		 
		 this.claimTabClaimPanel=new BuchiAutomatonJPanel<S1,T1, LABELLEDTRANSITIONFACTORY, DrawableBA<S1, T1, LABELLEDTRANSITIONFACTORY>>(claim, this);
		 
		 claimTab.add(containerClaimMenu);
		 claimTab.add(this.claimTabClaimPanel);
		 
		 
		 
		 //******************************************************************************************************************************
		 // Verification tab
		 //******************************************************************************************************************************
		 
		 verificationResultsTab.setLayout(new BoxLayout(verificationResultsTab, BoxLayout.Y_AXIS));
		 
		 JPanel verificationMenu=new JPanel();
		 verificationMenu.setLayout(new BoxLayout(verificationMenu, BoxLayout.X_AXIS));
		 
		 this.openIntersectionButton=new JButton(openIcon);
		 this.openIntersectionButton.addActionListener(this);
		 this.openIntersectionButton.setFocusPainted(false);
		 verificationMenu.add(this.openIntersectionButton);
		 
		 this.saveIntersectionButton=new JButton(this.saveIcon);
		 this.saveIntersectionButton.addActionListener(this);
		 this.saveIntersectionButton.setFocusPainted(false);
		 verificationMenu.add(this.saveIntersectionButton);
		
		 this.checkButton=new JButton(this.checkIcon);
		 this.checkButton.addActionListener(this);
		 this.checkButton.setFocusPainted(false);
		 verificationMenu.add(this.checkButton);
		 
		 verificationResultsTab.add(verificationMenu);
		
		 this.verificationResultsIntersection=new IntersectionAutomatonJPanel<S1, T1, S, T, LABELLEDTRANSITIONFACTORY, CONSTRAINTTRANSITIONFACTORY, DrawableIntBA<S1, T1, S, T, CONSTRAINTTRANSITIONFACTORY>>(intersection, this);
		 JPanel containerInt1=new JPanel();
		 containerInt1.setLayout(new BoxLayout(containerInt1,BoxLayout.Y_AXIS));
		 containerInt1.add(new JLabel("Intersection automaton"));
		 containerInt1.add(this.verificationResultsIntersection);
			
		 verificationResultsIntersection.setTranformingMode();
		 JPanel containerInt2=new JPanel();
		 containerInt2.setLayout(new BoxLayout(containerInt2,BoxLayout.Y_AXIS));
		 containerInt2.add(new JLabel("Brzozowski representation"));
		 this.brzozowskiSystem=new TextArea();
		 containerInt2.add(this.brzozowskiSystem);
		
		 verificationResultsTab.add(containerInt1);
		 verificationResultsTab.add(containerInt2);
		 
		 
		 //******************************************************************************************************************************
		 // verification snapshot
		 //******************************************************************************************************************************
		 
		 JPanel container1=new JPanel();
		 container1.setLayout(new BoxLayout(container1,BoxLayout.Y_AXIS));
		
		 
		 container1.add(new JLabel("Model"));
		 this.verificationSnapshotModelPanel=new IncompleteBuchiAutomatonJPanel<S1,T1,LABELLEDTRANSITIONFACTORY, DrawableIBA<S1,T1, LABELLEDTRANSITIONFACTORY>>(model, this);
		 this.verificationSnapshotModelPanel.setTranformingMode();
		 container1.add(verificationSnapshotModelPanel);
		
		 container1.add(new JLabel("Claim"));
		 this.verificationSnapshotClaimPanel=new BuchiAutomatonJPanel<S1,T1, LABELLEDTRANSITIONFACTORY, DrawableBA<S1, T1, LABELLEDTRANSITIONFACTORY>>(claim, this);
		 this.verificationSnapshotClaimPanel.setTranformingMode();
		 container1.add(verificationSnapshotClaimPanel);
		 verificationSnapshotTab.add(container1);
		 
		 JPanel container2=new JPanel();
		 container2.setLayout(new BoxLayout(container2,BoxLayout.Y_AXIS));
		 
		 JLabel intersectionLabel=new JLabel("Intersection Automaton");
		 container2.add(intersectionLabel);
		 
		 this.verificationSnapshotIntersectionPanel=new IntersectionAutomatonJPanel<S1, T1, S, T, LABELLEDTRANSITIONFACTORY, CONSTRAINTTRANSITIONFACTORY, DrawableIntBA<S1, T1, S, T, CONSTRAINTTRANSITIONFACTORY>>(intersection, this);
		 this.verificationSnapshotIntersectionPanel.setTranformingMode();
		 container2.add(verificationSnapshotIntersectionPanel);
		 
		 container2.add(new JLabel("Model Checking results"));
		 
		 this.verificationSnapshotResultsPanel=new ResultsJPanel<S1, T1, S, T, LABELLEDTRANSITIONFACTORY, CONSTRAINTTRANSITIONFACTORY, IntBAImpl<S1, T1, S, T, LABELLEDTRANSITIONFACTORY, CONSTRAINTTRANSITIONFACTORY>>();
		 container2.add(verificationSnapshotResultsPanel);
			
		 verificationSnapshotTab.add(container2);
		 jframe.setResizable(true);
		 jframe.setVisible(true);
		 
	}
	
	@Override
	public void updateModel(DrawableIBA<S1, T1, LABELLEDTRANSITIONFACTORY> model){
		this.modelTabmodel.update(model);
		this.verificationSnapshotModelPanel.update(model);
	}
	@Override
	public void updateSpecification(DrawableBA<S1, T1, LABELLEDTRANSITIONFACTORY> specification){
		this.claimTabClaimPanel.update(specification);
		this.verificationSnapshotClaimPanel.update(specification);
		
	}

	@Override
	public void updateIntersection(DrawableIntBA<S1, T1,S,T, CONSTRAINTTRANSITIONFACTORY> intersection){
		this.verificationSnapshotIntersectionPanel.update(intersection);
		this.verificationResultsIntersection.update(intersection);
	}

	@Override
	public void updateVerificationResults(ModelCheckerParameters<S1, S> verificationResults,
			DrawableIntBA<S1, T1,S,T, CONSTRAINTTRANSITIONFACTORY> intersection) {
		
		this.verificationSnapshotResultsPanel.updateResults(verificationResults);
		if(verificationResults.getResult()==0){
			this.verificationResultsIntersection.highlightPath(verificationResults.getViolatingPath(), intersection);
		}
	}

	public void setBrzozoski(String system){
		this.brzozowskiSystem.setText(system);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setChanged();
		
		if(e.getSource().equals(this.specificationSaveItem) || e.getSource().equals(this.saveClaimButton)){
			this.notifyObservers(new SaveSpecification(e.getSource(), e.getID(), e.getActionCommand()));
			this.saveClaimButton.setFocusPainted(false);
		}
		if(e.getSource().equals(this.specificationOpenItem) || e.getSource().equals(this.openClaimButton)){
			this.notifyObservers(new LoadSpecification(e.getSource(), e.getID(), e.getActionCommand()));
			this.openClaimButton.setFocusPainted(false);
		}
		if(e.getSource().equals(this.modelSaveItem) || e.getSource().equals(this.saveModelButton)){
			this.notifyObservers(new SaveModel(e.getSource(), e.getID(), e.getActionCommand()));
			this.saveModelButton.setFocusPainted(false);
		}
		if(e.getSource().equals(this.saveIntersectionButton)){
			this.notifyObservers(new SaveIntersection(e.getSource(), e.getID(), e.getActionCommand()));
			this.saveModelButton.setFocusPainted(false);
		}
		if(e.getSource().equals(this.modelOpenItem) || e.getSource().equals(this.openModelButton)){
			this.notifyObservers(new LoadModel(e.getSource(), e.getID(), e.getActionCommand()));
			this.openModelButton.setFocusPainted(false);
		}
		if(e.getSource().equals(this.editItem) || e.getSource().equals(this.modelEditingButton)){
			this.modelTabmodel.setEditingMode();
			this.modelEditingButton.setFocusPainted(true);
			this.modelTrasformingButton.setFocusable(false);
		}
		if(e.getSource().equals(this.trasformItem) || e.getSource().equals(this.modelTrasformingButton)){
			this.modelTabmodel.setTranformingMode();
			this.modelEditingButton.setFocusPainted(false);
			this.modelTrasformingButton.setFocusable(true);
		}
		if(e.getSource().equals(this.editItem) || e.getSource().equals(this.claimEditingButton)){
			this.claimTabClaimPanel.setEditingMode();
			this.claimEditingButton.setFocusPainted(true);
			this.claimTransformingButton.setFocusable(false);
		}
		if(e.getSource().equals(this.trasformItem) || e.getSource().equals(this.claimTransformingButton)){
			this.claimTabClaimPanel.setTranformingMode();
			this.claimEditingButton.setFocusPainted(false);
			this.claimTransformingButton.setFocusable(true);
		}
		if(e.getSource().equals(this.checkItem) || e.getSource().equals(this.checkButton)){
			this.notifyObservers(new CheckAction());
		}
		if(e.getSource().equals(this.ltlLoadClaimButton)){
			String ltlFormula=JOptionPane.showInputDialog("Type the LTL formula\n "
															+ "Syntax:\n"
															+ "Propositions: true, false, any lowercase string \n"
															+ "Boolean operators:  ! (negation) ->(implication) <-> (equivalence) &&  (and) ||  (or)\n"
															+ "Temporal operators: []  (always) <>   (eventually) U   (until) V (realease) X   (next)");
			
			this.notifyObservers(new LoadClaimAction(ltlFormula));
			this.ltlLoadClaimButton.setFocusPainted(false);
		}
		
	}

	public void displayErrorMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	private JMenuBar menuBar;
	private JMenu modelMenu;
	private JMenu editMenu;
	
	
	private JMenuItem modelOpenItem;
	private JMenuItem editItem;
	
	private JMenuItem trasformItem;
	private JMenuItem checkItem;
	
	private JMenuItem modelSaveItem;
	private JMenuItem specificationOpenItem;
	private JMenuItem specificationSaveItem;
	
	private void createMenuBar(JFrame jframe){
		menuBar=new JMenuBar();
		modelMenu=new JMenu("File");
		modelMenu.addActionListener(this);
		
		modelOpenItem = new JMenuItem("Open Model", KeyEvent.VK_T);
		modelMenu.add(modelOpenItem);
		modelOpenItem.setMnemonic(KeyEvent.VK_O);
		modelOpenItem.addActionListener(this);
		
		modelSaveItem = new JMenuItem("Save Model", KeyEvent.VK_T);
		modelMenu.add(modelSaveItem);
		modelSaveItem.setMnemonic(KeyEvent.VK_O);
		modelSaveItem.addActionListener(this);
		
		
		specificationOpenItem = new JMenuItem("Open Specification", KeyEvent.VK_T);
		modelMenu.add(specificationOpenItem);
		specificationOpenItem.setMnemonic(KeyEvent.VK_O);
		specificationOpenItem.addActionListener(this);
		
		
		specificationSaveItem = new JMenuItem("Save Specification", KeyEvent.VK_T);
		modelMenu.add(specificationSaveItem);
		specificationSaveItem.setMnemonic(KeyEvent.VK_O);
		specificationSaveItem.addActionListener(this);
		
		menuBar.add(modelMenu);
		
		editMenu=new JMenu("Project");
		editMenu.addActionListener(this);
		
		editItem = new JMenuItem("Edit", KeyEvent.VK_T);
		editMenu.add(editItem);
		editItem.setMnemonic(KeyEvent.VK_O);
		editItem.addActionListener(this);
		
		trasformItem = new JMenuItem("Trasform", KeyEvent.VK_T);
		editMenu.add(trasformItem);
		trasformItem.setMnemonic(KeyEvent.VK_O);
		trasformItem.addActionListener(this);
		
		checkItem=new JMenuItem("Check", KeyEvent.VK_T);
		editMenu.add(checkItem);
		checkItem.setMnemonic(KeyEvent.VK_O);
		checkItem.addActionListener(this);

		trasformItem.setMnemonic(KeyEvent.VK_O);
		trasformItem.addActionListener(this);
		
		
		menuBar.add(editMenu);
		
		

		
		
		jframe.setJMenuBar(menuBar);
		menuBar.setVisible(true);
		
	}
	
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        panel.setLayout(new GridLayout(1, 1));
        return panel;
    }
}
