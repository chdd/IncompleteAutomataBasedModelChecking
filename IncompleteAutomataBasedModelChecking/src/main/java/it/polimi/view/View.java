package it.polimi.view;

import it.polimi.controller.actions.CheckAction;
import it.polimi.controller.actions.LoadClaimAction;
import it.polimi.controller.actions.file.loading.LoadIntersection;
import it.polimi.controller.actions.file.loading.LoadModel;
import it.polimi.controller.actions.file.loading.LoadSpecification;
import it.polimi.controller.actions.file.saving.SaveIntersection;
import it.polimi.controller.actions.file.saving.SaveModel;
import it.polimi.controller.actions.file.saving.SaveSpecification;
import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
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
import java.awt.geom.Point2D;
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

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;

public class View<STATE extends State, 
			STATEFACTORY extends StateFactory<STATE>,
			TRANSITION extends LabelledTransition, 
			TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>,
			INTERSECTIONSTATE extends IntersectionState<STATE>, 
			INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
			INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>,
			INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>> 
			extends Observable implements ViewInterface<STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>, ActionListener{

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
	// menuBar
	private JMenuBar menuBar;
	private JMenu modelMenu;
	private JMenu editMenu;
	private JMenuItem editItem;
	
	private JMenuItem trasformItem;
	private JMenuItem checkItem;
	
	private JMenuItem openModelMenu;
	private JMenuItem saveModelMenu;
	private JMenuItem openClaimMenu;
	private JMenuItem saveClaimMenu;
	private JMenuItem openIntersectionMenu;
	private JMenuItem saveIntersectionMenu;
	
	
	// model
	private JButton openModelButton;
	private JButton saveModelButton;
	private JButton modelEditingButton;
	private JButton modelTrasformingButton;
	private IncompleteBuchiAutomatonJPanel<STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableIBA<STATE,TRANSITION, TRANSITIONFACTORY>> modelTabmodel;
	private AbstractLayout<STATE, TRANSITION> modelLayout;
	
	
	// claim
	private JButton openClaimButton;
	private JButton saveClaimButton;
	private JButton ltlLoadClaimButton;
	
	private JButton claimEditingButton;
	private JButton claimTransformingButton;
	private BuchiAutomatonJPanel<STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableBA<STATE,TRANSITION, TRANSITIONFACTORY>>  claimTabClaimPanel;
	private AbstractLayout<STATE, TRANSITION> claimLayout;
	
	
	// Intersection
	private JButton openIntersectionButton;
	private JButton saveIntersectionButton;
	private JButton checkButton;
	private AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION> intersectionLayout;
	
	private IntersectionAutomatonJPanel
		<STATE, 
		STATEFACTORY,
		TRANSITION, 
		TRANSITIONFACTORY,
		INTERSECTIONSTATE, 
		INTERSECTIONSTATEFACTORY,
		INTERSECTIONTRANSITION,
		INTERSECTIONTRANSITIONFACTORY,DrawableIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>> intersectionPanel;
	
	private TextArea brzozowskiSystemArea;

	// verification snapshot
	private AbstractLayout<STATE, TRANSITION> verificationModelLayout;
	private AbstractLayout<STATE, TRANSITION> verificationClaimLayout;
	private AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION> verificationIntersectionLayout;
	
	
	private IncompleteBuchiAutomatonJPanel<STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableIBA<STATE,TRANSITION, TRANSITIONFACTORY>>  verificationModelPanel;
	private BuchiAutomatonJPanel<STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableBA<STATE,TRANSITION, TRANSITIONFACTORY>>   verificationClaimPanel;
	private IntersectionAutomatonJPanel<STATE, 
	STATEFACTORY,
	TRANSITION, 
	TRANSITIONFACTORY,
	INTERSECTIONSTATE, 
	INTERSECTIONSTATEFACTORY,
	INTERSECTIONTRANSITION,
	INTERSECTIONTRANSITIONFACTORY,DrawableIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>> verificationIntersectionPanel;
	
	private ResultsJPanel<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY,IntBAImpl<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY >> verificationSnapshotResultsPanel;
	
	// main frame
	private JFrame jframe;
	
	public View(DrawableIBA<STATE, TRANSITION, TRANSITIONFACTORY> model,
			DrawableBA<STATE, TRANSITION, TRANSITIONFACTORY> claim,
			DrawableIntBA<STATE, TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersection) {
		
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
		 
		 
		 JComponent intersectionTab= makeTextPanel("Intersection");
		 tabbedPane.addTab("Intersection", intersectionTab);
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
		 this.modelLayout=new FRLayout<STATE,TRANSITION>(model);
		 this.modelTabmodel=new IncompleteBuchiAutomatonJPanel<STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableIBA<STATE,TRANSITION, TRANSITIONFACTORY>>(model, this, this.modelLayout);
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
		 this.claimLayout=new FRLayout<STATE,TRANSITION>(claim);
		 this.claimTabClaimPanel=new BuchiAutomatonJPanel<STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableBA<STATE,TRANSITION, TRANSITIONFACTORY>>(claim, this,  this.claimLayout);
		 
		 claimTab.add(containerClaimMenu);
		 claimTab.add(this.claimTabClaimPanel);
		 
		 
		 
		 //******************************************************************************************************************************
		 // intersection tab
		 //******************************************************************************************************************************
		 
		 intersectionTab.setLayout(new BoxLayout(intersectionTab, BoxLayout.Y_AXIS));
		 
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
		 
		 intersectionTab.add(verificationMenu);
		
		 this.intersectionLayout=new FRLayout<INTERSECTIONSTATE,INTERSECTIONTRANSITION>(intersection);
		 this.intersectionPanel=new IntersectionAutomatonJPanel
				 <STATE, 
					STATEFACTORY,
					TRANSITION, 
					TRANSITIONFACTORY,
					INTERSECTIONSTATE, 
					INTERSECTIONSTATEFACTORY,
					INTERSECTIONTRANSITION,
					INTERSECTIONTRANSITIONFACTORY,DrawableIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>>(intersection, this, this.intersectionLayout);
		 JPanel containerInt1=new JPanel();
		 containerInt1.setLayout(new BoxLayout(containerInt1,BoxLayout.Y_AXIS));
		 containerInt1.add(new JLabel("Intersection automaton"));
		 containerInt1.add(this.intersectionPanel);
			
		 intersectionPanel.setTranformingMode();
		 JPanel containerInt2=new JPanel();
		 containerInt2.setLayout(new BoxLayout(containerInt2,BoxLayout.Y_AXIS));
		 containerInt2.add(new JLabel("Brzozowski representation"));
		 this.brzozowskiSystemArea=new TextArea();
		 containerInt2.add(this.brzozowskiSystemArea);
		
		 intersectionTab.add(containerInt1);
		 intersectionTab.add(containerInt2);
		 
		 
		 //******************************************************************************************************************************
		 // verification snapshot
		 //******************************************************************************************************************************
		 
		 JPanel container1=new JPanel();
		 container1.setLayout(new BoxLayout(container1,BoxLayout.Y_AXIS));
		
		 
		 this.verificationModelLayout=new FRLayout<STATE,TRANSITION>(model);
		 container1.add(new JLabel("Model"));
		 this.verificationModelPanel=new IncompleteBuchiAutomatonJPanel
				 <STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableIBA<STATE,TRANSITION, TRANSITIONFACTORY>> (model, this, this.verificationModelLayout);
		 this.verificationModelPanel.setTranformingMode();
		 container1.add(verificationModelPanel);
		

		 this.verificationClaimLayout=new FRLayout<STATE,TRANSITION>(claim);
		 container1.add(new JLabel("Claim"));
		 this.verificationClaimPanel=new BuchiAutomatonJPanel<STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableBA<STATE,TRANSITION, TRANSITIONFACTORY>> (claim, this, this.verificationClaimLayout);
		 this.verificationClaimPanel.setTranformingMode();
		 container1.add(verificationClaimPanel);
		 verificationSnapshotTab.add(container1);
		 
		 JPanel container2=new JPanel();
		 container2.setLayout(new BoxLayout(container2,BoxLayout.Y_AXIS));
		 
		 JLabel intersectionLabel=new JLabel("Intersection Automaton");
		 container2.add(intersectionLabel);
		 
		 this.verificationIntersectionLayout=new FRLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION>(intersection);
		 
		 this.verificationIntersectionPanel=new IntersectionAutomatonJPanel
				 <STATE, 
					STATEFACTORY,
					TRANSITION, 
					TRANSITIONFACTORY,
					INTERSECTIONSTATE, 
					INTERSECTIONSTATEFACTORY,
					INTERSECTIONTRANSITION,
					INTERSECTIONTRANSITIONFACTORY,DrawableIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>>
		 	(intersection, this, this.verificationIntersectionLayout);
		 
		 this.verificationIntersectionPanel.setTranformingMode();
		 container2.add(verificationIntersectionPanel);
		 
		 container2.add(new JLabel("Model Checking results"));
		 
		 this.verificationSnapshotResultsPanel=new ResultsJPanel<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY, IntBAImpl<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>>();
		 container2.add(verificationSnapshotResultsPanel);
			
		 verificationSnapshotTab.add(container2);
		 jframe.setResizable(true);
		 jframe.setVisible(true);
		 
	}
	
	@Override
	public void updateModel(DrawableIBA<STATE, TRANSITION, TRANSITIONFACTORY> model, Transformer<STATE, Point2D> positions){
		if(positions!=null){
			this.modelLayout.setInitializer(positions);
			this.verificationModelLayout.setInitializer(positions);
		}
		this.modelTabmodel.update(model);
		this.verificationModelPanel.update(model);
		this.jframe.repaint();
	}
	@Override
	public void updateSpecification(DrawableBA<STATE, TRANSITION, TRANSITIONFACTORY> specification, Transformer<STATE, Point2D> positions){
		if(positions!=null){
			this.claimLayout.setInitializer(positions);
			this.verificationClaimLayout.setInitializer(positions);
		}
		this.claimTabClaimPanel.update(specification);
		this.verificationClaimPanel.update(specification);
		this.jframe.repaint();
		
	}

	@Override
	public void updateIntersection(DrawableIntBA<STATE, TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersection, Transformer<INTERSECTIONSTATE, Point2D> positions){
		if(positions!=null){
			this.intersectionLayout.setInitializer(positions);
			this.verificationIntersectionLayout.setInitializer(positions);
		}
		this.verificationIntersectionPanel.update(intersection);
		this.intersectionPanel.update(intersection);
		this.jframe.repaint();
		
	}

	@Override
	public void updateVerificationResults(ModelCheckerParameters<STATE, INTERSECTIONSTATE, INTERSECTIONTRANSITION> verificationResults,
			DrawableIntBA<STATE, TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersection) {
		
		this.verificationSnapshotResultsPanel.updateResults(verificationResults);
		if(verificationResults.getResult()==0){
			this.intersectionPanel.highlightPath(verificationResults.getViolatingPath(), intersection, verificationResults.getViolatingPathTransitions());
		}
		this.jframe.repaint();
	}

	public void setBrzozoski(String system){
		this.brzozowskiSystemArea.setText(system);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setChanged();
		
		if(e.getSource().equals(this.saveClaimMenu) || e.getSource().equals(this.saveClaimButton)){
			this.notifyObservers(new SaveSpecification<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
					INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY, AbstractLayout<STATE, TRANSITION>>(e.getSource(), e.getID(), e.getActionCommand(), this.claimLayout));
			this.saveClaimButton.setFocusPainted(false);
		}
		if(e.getSource().equals(this.openClaimMenu) || e.getSource().equals(this.openClaimButton)){
			this.notifyObservers(new LoadSpecification<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
					INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>(e.getSource(), e.getID(), e.getActionCommand()));
			this.openClaimButton.setFocusPainted(false);
		}
		if(e.getSource().equals(this.saveModelMenu) || e.getSource().equals(this.saveModelButton)){
			this.notifyObservers(new SaveModel<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
					INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY, AbstractLayout<STATE, TRANSITION>>(e.getSource(), e.getID(), e.getActionCommand(), this.modelLayout));
			this.saveModelButton.setFocusPainted(false);
		}
		if(e.getSource().equals(this.saveIntersectionButton) || e.getSource().equals(this.saveIntersectionMenu)){
			this.notifyObservers(new SaveIntersection<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
					INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY, AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION>>(e.getSource(), e.getID(), e.getActionCommand(), this.intersectionLayout));
			this.saveModelButton.setFocusPainted(false);
		}
		if(e.getSource().equals(this.openIntersectionButton) || e.getSource().equals(this.openIntersectionMenu)){
			this.notifyObservers(new LoadIntersection<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
					INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>(e.getSource(), e.getID(), e.getActionCommand()));
			this.saveModelButton.setFocusPainted(false);
		}
		if(e.getSource().equals(this.openModelMenu) || e.getSource().equals(this.openModelButton)){
			this.notifyObservers(new LoadModel<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
					INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>(e.getSource(), e.getID(), e.getActionCommand()));
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
			this.notifyObservers(new CheckAction<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
					INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>());
		}
		if(e.getSource().equals(this.ltlLoadClaimButton)){
			String ltlFormula=JOptionPane.showInputDialog("Type the LTL formula\n "
															+ "Syntax:\n"
															+ "Propositions: true, false, any lowercase string \n"
															+ "Boolean operators:  ! (negation) ->(implication) <-> (equivalence) &&  (and) ||  (or)\n"
															+ "Temporal operators: []  (always) <>   (eventually) U   (until) V (realease) X   (next)");
			
			this.notifyObservers(new LoadClaimAction<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
					INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>(ltlFormula));
			this.ltlLoadClaimButton.setFocusPainted(false);
		}
		
	}

	public void displayErrorMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	
	private void createMenuBar(JFrame jframe){
		this.menuBar=new JMenuBar();
		this.modelMenu=new JMenu("File");
		
		// model
		this.openModelMenu = new JMenuItem("Open Model");
		this.modelMenu.add(this.openModelMenu);
		this.openModelMenu.addActionListener(this);
		
		this.saveModelMenu = new JMenuItem("Save Model");
		this.modelMenu.add(this.saveModelMenu);
		this.saveModelMenu.addActionListener(this);
		
		// claim
		this.openClaimMenu = new JMenuItem("Open Claim");
		this.modelMenu.add(this.openClaimMenu);
		this.openClaimMenu.addActionListener(this);
		
		this.saveClaimMenu = new JMenuItem("Save Claim");
		this.modelMenu.add(this.saveClaimMenu);
		this.saveClaimMenu.addActionListener(this);
		
		// intersection
		this.saveIntersectionMenu=new JMenuItem("Save Intersection");
		this.modelMenu.add(this.saveIntersectionMenu);
		this.saveIntersectionMenu.addActionListener(this);
		
		this.openIntersectionMenu=new JMenuItem("Save Intersection");
		this.modelMenu.add(this.openIntersectionMenu);
		this.openIntersectionMenu.addActionListener(this);
		
		
		
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
