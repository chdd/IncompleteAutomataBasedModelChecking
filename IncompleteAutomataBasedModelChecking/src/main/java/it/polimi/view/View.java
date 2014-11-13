package it.polimi.view;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.controller.actions.CheckAction;
import it.polimi.controller.actions.LoadClaimAction;
import it.polimi.controller.actions.createnew.NewClaim;
import it.polimi.controller.actions.createnew.NewModel;
import it.polimi.controller.actions.file.loading.LoadIntersection;
import it.polimi.controller.actions.file.loading.LoadModel;
import it.polimi.controller.actions.file.loading.LoadSpecification;
import it.polimi.controller.actions.file.saving.SaveIntersection;
import it.polimi.controller.actions.file.saving.SaveModel;
import it.polimi.controller.actions.file.saving.SaveSpecification;
import it.polimi.controller.actions.flattening.ShowFlattedModel;
import it.polimi.controller.actions.flattening.ShowHierarchicalModel;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.ModelCheckingResults;
import it.polimi.view.tabs.ClaimTab;
import it.polimi.view.tabs.IntersectionTab;
import it.polimi.view.tabs.ModelTab;
import it.polimi.view.tabs.VerificationTab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

public class View<
			CONSTRAINEDELEMENT extends State,
			STATE extends State, 
			STATEFACTORY extends StateFactory<STATE>,
			TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
			TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
			INTERSECTIONSTATE extends IntersectionState<STATE>, 
			INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
			INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
			INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>> 
			extends Observable 
			implements ViewInterface<CONSTRAINEDELEMENT, STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>, ActionListener{

	private JPanel container;
	
	public boolean reload=false;
	private boolean flatten=false;
	
	private ViewInstrumentBar instrumentBar;
	
	private ViewMenuBar menuBar;
	
	
	// ------------------------------------
	// tabs
	// ------------------------------------
	private JTabbedPane tabbedPane;
	
	// model
	private ModelTab<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY,INTERSECTIONSTATE,INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> modelTab;
	
	// claim
	private ClaimTab<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY,INTERSECTIONSTATE,INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> claimTab;
	
	
	// Intersection
	private IntersectionTab<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY,INTERSECTIONSTATE,INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersectionTab;
	

	// verification snapshot Tab
	private VerificationTab<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY,INTERSECTIONSTATE,INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> verificationSnapshotTab;
	
	private Map<Component,Map<JButton, Boolean>> enabledButtons=new HashMap<Component,Map<JButton, Boolean>>();
	
	// constraint visualizer
	private ConstraintJPanel
		<CONSTRAINEDELEMENT,
		STATE, 
		STATEFACTORY,
		TRANSITION, 
		TRANSITIONFACTORY,
		INTERSECTIONSTATE, 
		INTERSECTIONSTATEFACTORY,
		INTERSECTIONTRANSITION,
		INTERSECTIONTRANSITIONFACTORY> constraintVisualizer;
		
	// main frame
	private JFrame jframe;
	
	public View(
			ModelInterface<CONSTRAINEDELEMENT, STATE, 
			STATEFACTORY, TRANSITION, 
			TRANSITIONFACTORY, INTERSECTIONSTATE, 
			INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, 
			INTERSECTIONTRANSITIONFACTORY> model) {

		 this.jframe=new JFrame();
		 this.jframe.setTitle(Constants.appName);
		 
		 // setting the size of the jframe
		 this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.jframe.getContentPane().setBackground(Color.getColor("myColor"));
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 this.jframe.setSize(screenSize);
		 
		 // creating the menu bar
		 this.menuBar=new ViewMenuBar(this, this.jframe);
		 this.menuBar.setVisible(true);
		 this.jframe.setJMenuBar(this.menuBar);
		
		 // creating the instrument bar
		 this.instrumentBar=new ViewInstrumentBar(this, this.jframe);
		 this.jframe.getContentPane().add(this.instrumentBar, BorderLayout.NORTH);

		 
		 this.tabbedPane = new JTabbedPane();
		 this.tabbedPane.setSize(this.jframe.getContentPane().getSize());
		 this.tabbedPane.setPreferredSize(this.jframe.getContentPane().getSize());
		 
		 modelTab=new ModelTab<>(model, this, screenSize);
		 this.tabbedPane.addTab("Model",modelTab);
		 this.tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
		 this.enabledButtons.put(this.modelTab, new HashMap<JButton, Boolean>());
		 this.enabledButtons.get(this.modelTab).put(this.instrumentBar.saveButton, true);
		 this.enabledButtons.get(this.modelTab).put(this.instrumentBar.openButton, true);
		 this.enabledButtons.get(this.modelTab).put(this.instrumentBar.newButton, true);
		 this.enabledButtons.get(this.modelTab).put(this.instrumentBar.editingButton, false);
		 this.enabledButtons.get(this.modelTab).put(this.instrumentBar.transformingButton, true);
		 this.enabledButtons.get(this.modelTab).put(this.instrumentBar.flattenButton, true);
		 this.enabledButtons.get(this.modelTab).put(this.instrumentBar.hierarchyButton, false);
		 this.enabledButtons.get(this.modelTab).put(this.instrumentBar.checkButton, true);
				
		 
		 this.claimTab = new ClaimTab<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY,INTERSECTIONSTATE,INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>(model, this);
		 
		 this.tabbedPane.addTab("Claim", claimTab);
		 this.tabbedPane.setMnemonicAt(1, KeyEvent.VK_3);
		 this.enabledButtons.put(this.claimTab, new HashMap<JButton, Boolean>());
		 this.enabledButtons.get(this.claimTab).put(this.instrumentBar.saveButton, true);
		 this.enabledButtons.get(this.claimTab).put(this.instrumentBar.openButton, true);
		 this.enabledButtons.get(this.claimTab).put(this.instrumentBar.newButton, true);
		 this.enabledButtons.get(this.claimTab).put(this.instrumentBar.editingButton, false);
		 this.enabledButtons.get(this.claimTab).put(this.instrumentBar.transformingButton, true);
		 this.enabledButtons.get(this.claimTab).put(this.instrumentBar.flattenButton, false);
		 this.enabledButtons.get(this.claimTab).put(this.instrumentBar.hierarchyButton, false);
		 this.enabledButtons.get(this.claimTab).put(this.instrumentBar.checkButton, true);
		 
		 
		 this.intersectionTab=new IntersectionTab<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY,INTERSECTIONSTATE,INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>(model, this);
		 this.tabbedPane.addTab("Intersection", intersectionTab);
		 this.tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		 this.enabledButtons.put(this.intersectionTab, new HashMap<JButton, Boolean>());
		 this.enabledButtons.get(this.intersectionTab).put(this.instrumentBar.saveButton, false);
		 this.enabledButtons.get(this.intersectionTab).put(this.instrumentBar.openButton, false);
		 this.enabledButtons.get(this.intersectionTab).put(this.instrumentBar.newButton, false);
		 this.enabledButtons.get(this.intersectionTab).put(this.instrumentBar.editingButton, false);
		 this.enabledButtons.get(this.intersectionTab).put(this.instrumentBar.transformingButton, false);
		 this.enabledButtons.get(this.intersectionTab).put(this.instrumentBar.flattenButton, false);
		 this.enabledButtons.get(this.intersectionTab).put(this.instrumentBar.hierarchyButton, false);
		 this.enabledButtons.get(this.intersectionTab).put(this.instrumentBar.checkButton, true);
		 
		 this.verificationSnapshotTab = new VerificationTab<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY,INTERSECTIONSTATE,INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>(model, this);
		 this.enabledButtons.put(this.verificationSnapshotTab, new HashMap<JButton, Boolean>());
		 this.enabledButtons.get(this.verificationSnapshotTab).put(this.instrumentBar.saveButton, false);
		 this.enabledButtons.get(this.verificationSnapshotTab).put(this.instrumentBar.openButton, false);
		 this.enabledButtons.get(this.verificationSnapshotTab).put(this.instrumentBar.newButton, false);
		 this.enabledButtons.get(this.verificationSnapshotTab).put(this.instrumentBar.editingButton, false);
		 this.enabledButtons.get(this.verificationSnapshotTab).put(this.instrumentBar.transformingButton, false);
		 this.enabledButtons.get(this.verificationSnapshotTab).put(this.instrumentBar.flattenButton, false);
		 this.enabledButtons.get(this.verificationSnapshotTab).put(this.instrumentBar.hierarchyButton, false);
		 this.enabledButtons.get(this.verificationSnapshotTab).put(this.instrumentBar.checkButton, true);
		 
		 this.tabbedPane.addTab("Verification", verificationSnapshotTab);
		 this.tabbedPane.setMnemonicAt(3, KeyEvent.VK_1);
		 this.verificationSnapshotTab.setLayout(new BoxLayout(verificationSnapshotTab,BoxLayout.X_AXIS));
		 
		 this.tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				updateButtons(tabbedPane.getSelectedComponent());
			}
		 });;
		 
		 this.jframe.add(tabbedPane);
		 
		 this.container = new JPanel();
		 container.setAutoscrolls(false);
		 
		 JScrollPane scrPane = new JScrollPane(this.container);
		 jframe.add(scrPane);
		 
		 this.container.setLayout(new BoxLayout(this.container,BoxLayout.Y_AXIS));
		 this.container.add(tabbedPane);
		 
		 
		 
		 
		 
		 
		 //******************************************************************************************************************************
		 // intersection tab
		 //******************************************************************************************************************************
		
		 
		 
		 //******************************************************************************************************************************
		 // verification snapshot
		 //******************************************************************************************************************************
		 
		 
		 
		 //******************************************************************************************************************************
		 // constraint visualizer
		 //******************************************************************************************************************************
		
		 this.jframe.setResizable(true);
		 this.jframe.setVisible(true);
		
		 this.constraintVisualizer=new ConstraintJPanel<
											CONSTRAINEDELEMENT,
											STATE, 
											STATEFACTORY,
											TRANSITION, 
											TRANSITIONFACTORY,
											INTERSECTIONSTATE, 
											INTERSECTIONSTATEFACTORY,
											INTERSECTIONTRANSITION,
											INTERSECTIONTRANSITIONFACTORY>(
													new Dimension(this.modelTab.getWidth(), Toolkit.getDefaultToolkit().getScreenSize().height/Constants.constraintPanelYRation));
		 this.constraintVisualizer.setVisible(false);
		 
		 this.container.add(this.constraintVisualizer);
		 
		 this.updateButtons(this.tabbedPane.getSelectedComponent());
		 
	}
	
	@Override
	public void updateModel(DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> model, Transformer<STATE, Point2D> positions,  DefaultTreeModel hierarchicalModelRefinement, DefaultTreeModel flatModelRefinement){
		this.modelTab.updateModel(model, positions, hierarchicalModelRefinement, flatModelRefinement, this.flatten, this.reload);
		this.modelTab.repaint();
		this.verificationSnapshotTab.updateModel(model, positions, hierarchicalModelRefinement, flatModelRefinement);;
		this.jframe.repaint();
	}
	@Override
	public void updateSpecification(DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> specification, Transformer<STATE, Point2D> positions){
		this.claimTab.updateSpecification(specification, positions);
		this.jframe.repaint();
	}

	@Override
	public void updateIntersection(DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersection, Transformer<INTERSECTIONSTATE, Point2D> positions){
		
		this.verificationSnapshotTab.updateIntersection(intersection, positions);
		this.intersectionTab.updateIntersection(intersection, positions);
		
		this.jframe.repaint();
	}
	
	

	@Override
	public void updateVerificationResults(ModelCheckingResults<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> verificationResults,
			DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersection) {
		
		if(verificationResults.getResult()==1){
			this.constraintVisualizer.setVisible(false);
		}
		if(verificationResults.getResult()==0){
			this.constraintVisualizer.setVisible(false);
		}
		if(verificationResults.getResult()==-1){
			this.showConstraints(verificationResults);
		}
		this.verificationSnapshotTab.updateVerificationResults(verificationResults, intersection);
		this.tabbedPane.setSelectedComponent(this.verificationSnapshotTab);
		this.jframe.repaint();
	}

	public void setBrzozoski(String system){
		this.intersectionTab.setBrzozoski(system);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setChanged();
		
		
		// new 
		if(((e.getSource().equals(this.instrumentBar.newButton) || e.getSource().equals(this.menuBar.filenew)) && this.tabbedPane.getSelectedComponent().equals(this.modelTab))){
			reload=true;
			this.notifyObservers(new NewModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
		}
		
		if((e.getSource().equals(this.instrumentBar.newButton) ||(e.getSource().equals(this.menuBar.filenew)) && this.tabbedPane.getSelectedComponent().equals(this.claimTab))){
			this.notifyObservers(new NewClaim<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
		}
		//---------------------------------------------
		// open
		if((e.getSource().equals(this.instrumentBar.openButton) || e.getSource().equals(this.menuBar.fileopen)) && this.tabbedPane.getSelectedComponent().equals(this.modelTab)){
			reload=true;
			this.notifyObservers(new LoadModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(e.getSource(), e.getID(), e.getActionCommand()));
		}
		if((e.getSource().equals(this.instrumentBar.openButton) || e.getSource().equals(this.menuBar.fileopen)) && this.tabbedPane.getSelectedComponent().equals(this.claimTab)){
			this.notifyObservers(new LoadSpecification<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(e.getSource(), e.getID(), e.getActionCommand()));
		}
		if((e.getSource().equals(this.instrumentBar.openButton) || e.getSource().equals(this.menuBar.fileopen)) && this.tabbedPane.getSelectedComponent().equals(this.intersectionTab)){
			this.notifyObservers(new LoadIntersection<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(e.getSource(), e.getID(), e.getActionCommand()));
		}
		
		
		// --------------------------------------------
		// saving
		if((e.getSource().equals(this.instrumentBar.saveButton) || e.getSource().equals(this.menuBar.filesave))  && this.tabbedPane.getSelectedComponent().equals(this.modelTab)){
			this.notifyObservers(new SaveModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, AbstractLayout<STATE, TRANSITION>>(e.getSource(), e.getID(), e.getActionCommand(), this.modelTab.getModelLayout()));
		}
		if((e.getSource().equals(this.instrumentBar.saveButton) || e.getSource().equals(this.menuBar.filesave)) && this.tabbedPane.getSelectedComponent().equals(this.claimTab)){
			this.notifyObservers(new SaveSpecification<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, AbstractLayout<STATE, TRANSITION>>(e.getSource(), e.getID(), e.getActionCommand(), this.claimTab.getClaimLayout()));
		}
		if((e.getSource().equals(this.instrumentBar.saveButton) || e.getSource().equals(this.menuBar.filesave)) && this.tabbedPane.getSelectedComponent().equals(this.intersectionTab)){
			this.notifyObservers(new SaveIntersection<CONSTRAINEDELEMENT, STATE, STATEFACTORY,
					TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, 
					INTERSECTIONTRANSITION,  AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION>>(e.getSource(), e.getID(), e.getActionCommand(), this.intersectionTab.getIntersectionLayout()));
		}
		
		// ltlloading
		if(e.getSource().equals(this.instrumentBar.loadLTLButton) || e.getSource().equals(this.menuBar.loadClaimFromLTLMenuItem)){
			String ltlFormula=JOptionPane.showInputDialog("Type the LTL formula\n "
															+ "Syntax:\n"
															+ "Propositions: true, false, any lowercase string \n"
															+ "Boolean operators:  ! (negation) ->(implication) <-> (equivalence) &&  (and) ||  (or)\n"
															+ "Temporal operators: []  (always) <>   (eventually) U   (until) V (realease) X   (next)");
			
			this.notifyObservers(new LoadClaimAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(ltlFormula));
		}
		
		//editing
		
		if((e.getSource().equals(this.instrumentBar.editingButton) || e.getSource().equals(this.menuBar.editModeMenu)) && this.tabbedPane.getSelectedComponent().equals(this.modelTab)){
			if(!flatten){
				this.enabledButtons.get(this.modelTab).put(this.instrumentBar.editingButton, false);
				this.enabledButtons.get(this.modelTab).put(this.instrumentBar.transformingButton, true);
				this.modelTab.setTransformingMode();
			}
		}
		
		if((e.getSource().equals(this.instrumentBar.editingButton) || e.getSource().equals(this.menuBar.editModeMenu)) && this.tabbedPane.getSelectedComponent().equals(this.claimTab)){
			this.enabledButtons.get(this.claimTab).put(this.instrumentBar.editingButton, false);
			this.enabledButtons.get(this.claimTab).put(this.instrumentBar.transformingButton, true);
			this.claimTab.setEditingMode();
		}
		
		
		// transforming
		if((e.getSource().equals(this.instrumentBar.transformingButton) || e.getSource().equals(this.menuBar.transformingModeMenu))&& this.tabbedPane.getSelectedComponent().equals(this.modelTab)){
			this.modelTab.setTransformingMode();
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.editingButton, true);
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.transformingButton, false);
		}
		if((e.getSource().equals(this.instrumentBar.transformingButton) || e.getSource().equals(this.menuBar.transformingModeMenu)) && this.tabbedPane.getSelectedComponent().equals(this.claimTab)){
			this.claimTab.setTransformingMode();
			this.enabledButtons.get(this.claimTab).put(this.instrumentBar.editingButton, true);
			this.enabledButtons.get(this.claimTab).put(this.instrumentBar.transformingButton, false);
		}
		
		
		// checking
		if(e.getSource().equals(this.instrumentBar.checkButton) || e.getSource().equals(this.menuBar.runCheckerMenuItem)){
			this.notifyObservers(new CheckAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
		}
		
		if(e.getSource().equals(this.instrumentBar.flattenButton)){
			this.flatten=true;
			this.notifyObservers(new ShowFlattedModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.transformingButton, false);
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.editingButton, false);
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.flattenButton, false);
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.hierarchyButton, true);
			
			this.modelTab.setTransformingMode();
		}
		if(e.getSource().equals(this.instrumentBar.hierarchyButton)){
			this.flatten=false;
			this.notifyObservers(new ShowHierarchicalModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.transformingButton, false);
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.editingButton, true);
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.flattenButton, true);
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.hierarchyButton, false);
			
			this.modelTab.setTransformingMode();
		}
		if(e instanceof ActionInterface<?, ?, ?, ?, ?>){
			this.notifyObservers(e);
		}
		this.updateButtons(this.tabbedPane.getSelectedComponent());
	}

	public void displayErrorMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	
	
	
	

	@Override
	public void updateModel(
			DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> model, 
			DefaultTreeModel hierarchicalModelRefinement,
			DefaultTreeModel flatModelRefinement) {
		this.updateModel(model, null, hierarchicalModelRefinement, flatModelRefinement);
		
	}

	@Override
	public void updateClaim(
			DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> specification) {
		this.updateSpecification(specification, null);
		
	}

	private void showConstraints(ModelCheckingResults
									<CONSTRAINEDELEMENT, 
									STATE, 
									TRANSITION, 
									INTERSECTIONSTATE, 
									INTERSECTIONTRANSITION> verificationResults) {
		
		this.constraintVisualizer.updateConstraintJPanel(this,  verificationResults);
		this.constraintVisualizer.setVisible(true);

	}
	
	

	@Override
	public void hightLightConstraint(
			STATE state,
			Set<INTERSECTIONTRANSITION> intersectionTransitions) {
		this.modelTab.highLightState(state);
		this.verificationSnapshotTab.hightLightConstraint(state, intersectionTransitions);
		this.jframe.repaint();
		
		this.intersectionTab.hightLightConstraint(state, intersectionTransitions);	
	}

	@Override
	public void doNothightLightConstraint() {
		this.modelTab.setEditingMode();
		this.verificationSnapshotTab.doNothightLightConstraint();
		this.intersectionTab.doNothightLightConstraint();
		this.jframe.repaint();
		
	}
	
	private void updateButtons(Component component){
		
		 this.instrumentBar.saveButton.setEnabled(this.enabledButtons.get(component).get(this.instrumentBar.saveButton));
		 this.instrumentBar.openButton.setEnabled(this.enabledButtons.get(component).get(this.instrumentBar.openButton));
		 this.instrumentBar.newButton.setEnabled(this.enabledButtons.get(component).get(this.instrumentBar.newButton));
		 this.instrumentBar.editingButton.setEnabled(this.enabledButtons.get(component).get(this.instrumentBar.editingButton));
		 this.instrumentBar.transformingButton.setEnabled(this.enabledButtons.get(component).get(this.instrumentBar.transformingButton));
		 this.instrumentBar.flattenButton.setEnabled(this.enabledButtons.get(component).get(this.instrumentBar.flattenButton));
		 this.instrumentBar.hierarchyButton.setEnabled(this.enabledButtons.get(component).get(this.instrumentBar.hierarchyButton));
		 this.instrumentBar.checkButton.setEnabled(this.enabledButtons.get(component).get(this.instrumentBar.checkButton));
				
	}
}
