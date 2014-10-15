package it.polimi.view;

import it.polimi.controller.actions.CheckAction;
import it.polimi.controller.actions.LoadClaimAction;
import it.polimi.controller.actions.file.loading.LoadModel;
import it.polimi.controller.actions.file.loading.LoadSpecification;
import it.polimi.controller.actions.file.saving.SaveModel;
import it.polimi.controller.actions.file.saving.SaveSpecification;
import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;
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

public class View<S1 extends State, T1 extends LabelledTransition, S extends IntersectionState<S1>, T extends ConstrainedTransition<S1>> extends Observable implements ViewInterface<S1,T1,S,T>, ActionListener{

	// Icons
	private final ImageIcon openIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/actions/document-open.png"));
	private final ImageIcon saveIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/devices/media-floppy.png"));
	private final ImageIcon ltlIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/devices/input-keyboard.png"));
	private final ImageIcon developmentIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/categories/applications-office.png"));
	private final ImageIcon editIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/actions/view-fullscreen.png"));
	private final ImageIcon checkIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/categories/applications-system.png"));
	
	// model
	private JButton openModel;
	private JButton saveModel;
	private JButton modelDevelopment;
	private JButton modelEditing;
	private IncompleteBuchiAutomatonJPanel<S1,T1,IncompleteBuchiAutomaton<S1,T1>> modelTabmodel;
	
	// claim
	private JButton openClaim;
	private JButton saveClaim;
	private JButton ltlLoadClaim;
	private JButton claimDevelopment;
	private JButton claimEditing;
	private BuchiAutomatonJPanel<S1,T1, BuchiAutomaton<S1, T1>>  claimTabClaimPanel;

	
	
	// verification results
	private JButton openResults;
	private JButton saveResults;
	private JButton check;
	private IntersectionAutomatonJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>> verificationResultsIntersection;
	private TextArea brzozowskiSystem;

	// verification snapshot
	private IncompleteBuchiAutomatonJPanel<S1,T1,IncompleteBuchiAutomaton<S1,T1>> verificationSnapshotModelPanel;
	private BuchiAutomatonJPanel<S1,T1, BuchiAutomaton<S1, T1>>  verificationSnapshotClaimPanel;
	private IntersectionAutomatonJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>> verificationSnapshotIntersectionPanel;
	private ResultsJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>> verificationSnapshotResultsPanel;
	
	
	
	
	// main frame
	private JFrame jframe;
	
	
		
	
	public View(IncompleteBuchiAutomaton<S1, T1> model,
			BuchiAutomaton<S1, T1> claim,
			IntersectionAutomaton<S1, T1,S,T> intersection) {
		
		
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
		 
		 
		 JComponent verificationResultsTab= makeTextPanel("Verification Results");
		 tabbedPane.addTab("Verification Results", verificationResultsTab);
		 tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		 
		 JComponent verificationSnapshotTab = makeTextPanel("Verification Snapshot");
		 tabbedPane.addTab("Verification Snapshot", verificationSnapshotTab);
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
		 
		 this.openModel=new JButton(openIcon);
		 this.openModel.addActionListener(this);
		 this.openModel.setFocusPainted(false);
		 containerModelMenu.add(this.openModel);
		 
		 this.saveModel=new JButton(saveIcon);
		 this.saveModel.addActionListener(this);
		 this.saveModel.setFocusPainted(false);
		 containerModelMenu.add(saveModel);
		 
		 this.modelDevelopment=new JButton(this.developmentIcon);
		 this.modelDevelopment.addActionListener(this);
		 this.modelDevelopment.setFocusPainted(true);
		 containerModelMenu.add(this.modelDevelopment);
		 
		 this.modelEditing=new JButton(this.editIcon);
		 this.modelEditing.addActionListener(this);
		 this.modelEditing.setFocusPainted(false);
		 
		 containerModelMenu.add(this.modelEditing);
		 modelTab.add(containerModelMenu);
		 
		 this.modelTabmodel=new IncompleteBuchiAutomatonJPanel<S1,T1,IncompleteBuchiAutomaton<S1,T1>>(model, this);
		 modelTab.add(modelTabmodel);
		 
		 //******************************************************************************************************************************
		 // claim tab
		 //******************************************************************************************************************************
		 claimTab.setLayout(new BoxLayout(claimTab, BoxLayout.PAGE_AXIS));
		 
		 JPanel containerClaimMenu=new JPanel();
		 containerClaimMenu.setLayout(new BoxLayout(containerClaimMenu, BoxLayout.X_AXIS));
		 
		 this.openClaim=new JButton(openIcon);
		 this.openClaim.addActionListener(this);
		 this.openClaim.setFocusPainted(false);
		 containerClaimMenu.add(this.openClaim);
		 
		 this.saveClaim=new JButton(saveIcon);
		 this.saveClaim.addActionListener(this);
		 this.saveClaim.setFocusPainted(false);
		 containerClaimMenu.add(this.saveClaim);
		
		 this.ltlLoadClaim=new JButton(this.ltlIcon);
		 this.ltlLoadClaim.addActionListener(this);
		 this.ltlLoadClaim.setFocusPainted(false);
		 containerClaimMenu.add(this.ltlLoadClaim);
		 
		 this.claimDevelopment=new JButton(this.developmentIcon);
		 this.claimDevelopment.addActionListener(this);
		 this.claimDevelopment.setFocusPainted(true);
		 containerClaimMenu.add(this.claimDevelopment);
		 
		 this.claimEditing=new JButton(this.editIcon);
		 this.claimEditing.addActionListener(this);
		 this.claimEditing.setFocusPainted(false);
		 containerClaimMenu.add(this.claimEditing);
		 
		 this.claimTabClaimPanel=new BuchiAutomatonJPanel<S1,T1, BuchiAutomaton<S1, T1>>(claim, this);
		 
		 claimTab.add(containerClaimMenu);
		 claimTab.add(this.claimTabClaimPanel);
		 
		 
		 
		 //******************************************************************************************************************************
		 // Verification tab
		 //******************************************************************************************************************************
		 
		 verificationResultsTab.setLayout(new BoxLayout(verificationResultsTab, BoxLayout.Y_AXIS));
		 
		 JPanel verificationMenu=new JPanel();
		 verificationMenu.setLayout(new BoxLayout(verificationMenu, BoxLayout.X_AXIS));
		 
		 this.openResults=new JButton(openIcon);
		 this.openResults.addActionListener(this);
		 this.openResults.setFocusPainted(false);
		 verificationMenu.add(this.openResults);
		 
		 this.saveResults=new JButton(this.saveIcon);
		 this.saveResults.addActionListener(this);
		 this.saveResults.setFocusPainted(false);
		 verificationMenu.add(this.saveResults);
		
		 this.check=new JButton(this.checkIcon);
		 this.check.addActionListener(this);
		 this.check.setFocusPainted(false);
		 verificationMenu.add(this.check);
		 
		 verificationResultsTab.add(verificationMenu);
		
		 this.verificationResultsIntersection=new IntersectionAutomatonJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>>(intersection, this);
		 JPanel containerInt1=new JPanel();
		 containerInt1.setLayout(new BoxLayout(containerInt1,BoxLayout.Y_AXIS));
		 containerInt1.add(new JLabel("Intersection automaton"));
		 containerInt1.add(this.verificationResultsIntersection);
			
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
		 this.verificationSnapshotModelPanel=new IncompleteBuchiAutomatonJPanel<S1,T1,IncompleteBuchiAutomaton<S1,T1>>(model, this);
		 container1.add(verificationSnapshotModelPanel);
		
		 container1.add(new JLabel("Claim"));
		 this.verificationSnapshotClaimPanel=new BuchiAutomatonJPanel<S1,T1, BuchiAutomaton<S1, T1>>(claim, this);
		 container1.add(verificationSnapshotClaimPanel);
		 verificationSnapshotTab.add(container1);
		 
		 JPanel container2=new JPanel();
		 container2.setLayout(new BoxLayout(container2,BoxLayout.Y_AXIS));
		 
		 JLabel intersectionLabel=new JLabel("Intersection Automaton");
		 container2.add(intersectionLabel);
		 
		 this.verificationSnapshotIntersectionPanel=new IntersectionAutomatonJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>>(intersection, this);
		 
		 container2.add(verificationSnapshotIntersectionPanel);
		 
		 container2.add(new JLabel("Model Checking results"));
		 
		 this.verificationSnapshotResultsPanel=new ResultsJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>>();
		 container2.add(verificationSnapshotResultsPanel);
			
		 verificationSnapshotTab.add(container2);
		 jframe.setResizable(true);
		 jframe.setVisible(true);
		 
	}
	
	@Override
	public void updateModel(IncompleteBuchiAutomaton<S1, T1> model){
		this.modelTabmodel.update(model);
		this.verificationSnapshotModelPanel.update(model);
	}
	@Override
	public void updateSpecification(BuchiAutomaton<S1, T1> specification){
		this.claimTabClaimPanel.update(specification);
		this.verificationSnapshotClaimPanel.update(specification);
		
	}

	@Override
	public void updateIntersection(IntersectionAutomaton<S1, T1,S,T> intersection){
		this.verificationSnapshotIntersectionPanel.update(intersection);
		this.verificationResultsIntersection.update(intersection);
	}

	@Override
	public void updateVerificationResults(ModelCheckerParameters<S1, S> verificationResults,
			IntersectionAutomaton<S1, T1,S,T> intersection) {
		
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
		
		if(e.getSource().equals(this.specificationSaveItem) || e.getSource().equals(this.saveClaim)){
			this.notifyObservers(new SaveSpecification(e.getSource(), e.getID(), e.getActionCommand()));
			this.saveClaim.setFocusPainted(false);
		}
		else{
			if(e.getSource().equals(this.specificationOpenItem) || e.getSource().equals(this.openClaim)){
				this.notifyObservers(new LoadSpecification(e.getSource(), e.getID(), e.getActionCommand()));
				this.openClaim.setFocusPainted(false);
			}
			else{
				if(e.getSource().equals(this.modelSaveItem) || e.getSource().equals(this.saveModel)){
					this.notifyObservers(new SaveModel(e.getSource(), e.getID(), e.getActionCommand()));
					this.saveModel.setFocusPainted(false);
				}
				else{
					if(e.getSource().equals(this.modelOpenItem) || e.getSource().equals(this.openModel)){
						this.notifyObservers(new LoadModel(e.getSource(), e.getID(), e.getActionCommand()));
						this.openModel.setFocusPainted(false);
					}
					else{
						if(e.getSource().equals(this.editItem) || e.getSource().equals(this.modelDevelopment)){
							this.modelTabmodel.setEditingMode();
							this.modelDevelopment.setFocusPainted(true);
							this.modelEditing.setFocusable(false);
						}
						else{
							if(e.getSource().equals(this.trasformItem) || e.getSource().equals(this.modelEditing)){
								this.modelTabmodel.setTranformingMode();
								this.modelDevelopment.setFocusPainted(false);
								this.modelEditing.setFocusable(true);
							}
							else{
								if(e.getSource().equals(this.editItem) || e.getSource().equals(this.claimDevelopment)){
									this.verificationSnapshotClaimPanel.setEditingMode();
									this.claimDevelopment.setFocusPainted(true);
									this.claimEditing.setFocusable(false);
								}
								else{
									if(e.getSource().equals(this.trasformItem) || e.getSource().equals(this.claimEditing)){
										this.verificationSnapshotClaimPanel.setTranformingMode();
										this.claimDevelopment.setFocusPainted(false);
										this.claimEditing.setFocusable(true);
									}
									else{
										if(e.getSource().equals(this.checkItem) || e.getSource().equals(this.check)){
											this.notifyObservers(new CheckAction());
										}
										else{
											if(e.getSource().equals(this.ltlLoadClaim)){
												String ltlFormula=JOptionPane.showInputDialog("Type the LTL formula\n "
																								+ "Syntax:\n"
																								+ "Propositions: true, false, any lowercase string \n"
																								+ "Boolean operators:  ! (negation) ->(implication) <-> (equivalence) &&  (and) ||  (or)\n"
																								+ "Temporal operators: []  (always) <>   (eventually) U   (until) V (realease) X   (next)");
												
												this.notifyObservers(new LoadClaimAction(ltlFormula));
												this.ltlLoadClaim.setFocusPainted(false);
											}
										}
									}
								}
							}
						}
					}
				}
			}
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
