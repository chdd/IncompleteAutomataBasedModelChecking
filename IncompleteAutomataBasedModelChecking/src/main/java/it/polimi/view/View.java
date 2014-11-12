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
import it.polimi.view.automaton.BuchiAutomatonJPanel;
import it.polimi.view.automaton.IncompleteBuchiAutomatonJPanel;
import it.polimi.view.automaton.IntersectionAutomatonJPanel;
import it.polimi.view.automaton.RefinementTree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;

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

	private static final int verificationIconSize=64;
	
	// ------------------------------------
	// menuBar
	// ------------------------------------
	private JMenuBar menuBar;
	private JPanel container;
	
	public boolean reload=false;
	
	// file menu
	private JMenu fileMenu;
	private JMenuItem filenew;
	private JMenuItem fileopen;
	private JMenuItem filesave;
	private JMenuItem loadClaimFromLTLMenuItem;
	private boolean flatten=false;
	
	// edit menu
	private JMenu editMenu;
	private JMenuItem editModeMenu;
	private JMenuItem transformingModeMenu;
	
	//check menu
	private JMenu checkMenu;
	private JMenuItem runCheckerMenuItem;
	
	private ViewInstrumentBar instrumentBar;
	
	
	
	// ------------------------------------
	// tabs
	// ------------------------------------
	// model
	private JComponent modelTab;
	private IncompleteBuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableIBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>> modelTabmodel;
	private AbstractLayout<STATE, TRANSITION> modelLayout;
	
	private RefinementTree<
	 CONSTRAINEDELEMENT,
	 STATE, 
	 STATEFACTORY,
	 TRANSITION, 
	 TRANSITIONFACTORY> tree;
	
	private JPanel containerModelMenu;
	private JPanel jtreePanel;
	
	// claim
	private JComponent claimTab;
	private BuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>>  claimTabClaimPanel;
	private AbstractLayout<STATE, TRANSITION> claimLayout;
	
	// Intersection
	private JComponent intersectionTab;
	private AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION> intersectionLayout;
	
	private TextArea brzozowskiSystemArea;

	// verification snapshot
	private JComponent verificationSnapshotTab;
	private AbstractLayout<STATE, TRANSITION> verificationModelLayout;
	private AbstractLayout<STATE, TRANSITION> verificationClaimLayout;
	private AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION> verificationIntersectionLayout;
	private JLabel result;
	
	private JTabbedPane tabbedPane;
	private JTextField intersectionTime;
	private JTextField emptinessTime;
	private JTextField constraintComputationTime;
	private JTextField verificationTime;
	private JTextField simplificationTime;
	
	private static final double JTreeXProportion=6;
	
	private IntersectionAutomatonJPanel
	<CONSTRAINEDELEMENT, STATE, 
	STATEFACTORY,
	TRANSITION, 
	TRANSITIONFACTORY,
	INTERSECTIONSTATE, 
	INTERSECTIONSTATEFACTORY,
	INTERSECTIONTRANSITION,
	INTERSECTIONTRANSITIONFACTORY,DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>> intersectionPanel;

	
	private IncompleteBuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableIBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>>  verificationModelPanel;
	private BuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>>   verificationClaimPanel;
	private IntersectionAutomatonJPanel<CONSTRAINEDELEMENT, STATE, 
	STATEFACTORY,
	TRANSITION, 
	TRANSITIONFACTORY,
	INTERSECTIONSTATE, 
	INTERSECTIONSTATEFACTORY,
	INTERSECTIONTRANSITION,
	INTERSECTIONTRANSITIONFACTORY,DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>> verificationIntersectionPanel;
	
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
		 this.createMenuBar(jframe);
		 
		 this.tabbedPane = new JTabbedPane();
		 this.tabbedPane.setSize(this.jframe.getContentPane().getSize());
		 this.tabbedPane.setPreferredSize(this.jframe.getContentPane().getSize());
		 
		 this.modelTab = makeTextPanel("Model");
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
				
		 
		 this.claimTab = makeTextPanel("Claim");
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
		 
		 this.intersectionTab= makeTextPanel("Intersection");
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
		 
		 this.verificationSnapshotTab = makeTextPanel("Verification");
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
		 // model tab
		 //******************************************************************************************************************************
		 this.modelTab.setLayout(new BoxLayout(modelTab, BoxLayout.PAGE_AXIS));
		 this.containerModelMenu=new JPanel();
		 this.containerModelMenu.setBackground(Color.white);
		 this.containerModelMenu.setLayout(new BoxLayout(containerModelMenu, BoxLayout.X_AXIS));
		 
		 this.modelTab.add(containerModelMenu);
		 jtreePanel=new JPanel();
		 jtreePanel.setLayout(new BoxLayout(jtreePanel, BoxLayout.X_AXIS));
		 jtreePanel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createRaisedBevelBorder(), 
					BorderFactory.createLoweredBevelBorder()));
		 jtreePanel.setBackground(Color.white);
		 
		 this.tree=new RefinementTree<
				 CONSTRAINEDELEMENT,
				 STATE, 
				 STATEFACTORY,
				 TRANSITION, 
				 TRANSITIONFACTORY>(new Dimension((int) (screenSize.width/JTreeXProportion), screenSize.height), 
						 model.getModelRefinementHierarchy(), 
						 this); 
		 	jtreePanel.add(tree);
		 	containerModelMenu.add(jtreePanel);
		 this.modelLayout=new KKLayout<STATE,TRANSITION>(model.getModel());
		 this.modelTabmodel=new IncompleteBuchiAutomatonJPanel<
				 	CONSTRAINEDELEMENT, 
				 	STATE,
				 	STATEFACTORY, 
				 	TRANSITION, 
				 	TRANSITIONFACTORY, 
				 	DrawableIBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>>
		 			(model.getModel(), this, this.modelLayout,
		 					this.tree);
		 containerModelMenu.add(this.modelTabmodel);
		 modelTab.add(containerModelMenu);
		 
		 //******************************************************************************************************************************
		 // claim tab
		 //******************************************************************************************************************************
		 claimTab.setLayout(new BoxLayout(claimTab, BoxLayout.PAGE_AXIS));
		 
		 JPanel containerClaimMenu=new JPanel();
		 containerClaimMenu.setLayout(new BoxLayout(containerClaimMenu, BoxLayout.X_AXIS));
		 
		 this.claimLayout=new FRLayout<STATE,TRANSITION>(model.getSpecification());
		 this.claimTabClaimPanel=new BuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>>(model.getSpecification(), this,  this.claimLayout, null);
		 
		 claimTab.add(containerClaimMenu);
		 claimTab.add(this.claimTabClaimPanel);
		 
		 
		 
		 //******************************************************************************************************************************
		 // intersection tab
		 //******************************************************************************************************************************
		 intersectionTab.setLayout(new BoxLayout(intersectionTab, BoxLayout.Y_AXIS));
		 
		 this.intersectionLayout=new FRLayout<INTERSECTIONSTATE,INTERSECTIONTRANSITION>(model.getIntersection());
		 this.intersectionPanel=new IntersectionAutomatonJPanel
				 <CONSTRAINEDELEMENT, 
				 STATE, 
					STATEFACTORY,
					TRANSITION, 
					TRANSITIONFACTORY,
					INTERSECTIONSTATE, 
					INTERSECTIONSTATEFACTORY,
					INTERSECTIONTRANSITION,
					INTERSECTIONTRANSITIONFACTORY,DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>>(model.getIntersection(), this, this.intersectionLayout);
		 JPanel containerInt1=new JPanel();
		 containerInt1.setLayout(new BoxLayout(containerInt1,BoxLayout.Y_AXIS));
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
		
		 
		 this.verificationModelLayout=new FRLayout<STATE,TRANSITION>(model.getModel());
		 container1.add(new JLabel("Model"));
		 this.verificationModelPanel=new IncompleteBuchiAutomatonJPanel
				 <CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableIBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>> (model.getModel(), this, this.verificationModelLayout, 
						 null);
		 this.verificationModelPanel.setTranformingMode();
		 container1.add(verificationModelPanel);
		

		 this.verificationClaimLayout=new FRLayout<STATE,TRANSITION>(model.getSpecification());
		 container1.add(new JLabel("Claim"));
		 this.verificationClaimPanel=new BuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>> (model.getSpecification(), this, this.verificationClaimLayout, null);
		 this.verificationClaimPanel.setTranformingMode();
		 container1.add(verificationClaimPanel);
		 verificationSnapshotTab.add(container1);
		 
		 JPanel container2=new JPanel();
		 container2.setLayout(new BoxLayout(container2,BoxLayout.Y_AXIS));
		 
		 
		 // verification results
		 container2.add(new JLabel("Model Checking results"));
		 
		 JPanel resultContainer=new JPanel(new GridLayout(2,1));
		 resultContainer.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				  BorderFactory.createLoweredBevelBorder()));
		
		 JPanel resultPanel=new JPanel();
		 this.result=new JLabel(Constants.resultInitial);
		 this.result.setSize(new Dimension(verificationIconSize, verificationIconSize));
		 
		 resultPanel.add(result);
		 resultContainer.add(resultPanel);
		 
		 JPanel timePanel=new JPanel(new GridLayout(6,2));
		 timePanel.add(new JLabel("Intersection Time (s)"));
		 this.intersectionTime=new JTextField(20);
		 this.intersectionTime.setEditable(false);
		 timePanel.add(this.intersectionTime);
		
		 timePanel.add(new JLabel("Emptiness Time (s)"));
		 this.emptinessTime=new JTextField(20);
		 this.emptinessTime.setEditable(false);
		 timePanel.add(this.emptinessTime);
		
		 timePanel.add(new JLabel("Constraint Computation Time (s)"));
		 this.constraintComputationTime=new JTextField(20);
		 this.constraintComputationTime.setEditable(false);
		 timePanel.add(this.constraintComputationTime);
		 
		 timePanel.add(new JLabel("Total Verification Time (s)"));
		 this.verificationTime=new JTextField(20);
		 this.verificationTime.setEditable(false);
		 timePanel.add(this.verificationTime);
		 resultContainer.add(timePanel);
		 
		 timePanel.add(new JLabel("Total Simplification Time (s)"));
		 this.simplificationTime=new JTextField(20);
		 this.simplificationTime.setEditable(false);
		 timePanel.add(this.simplificationTime);
		 resultContainer.add(timePanel);
		 
		 container2.add(resultContainer);
		 
		 
		 // Intersection automaton
		 JLabel intersectionLabel=new JLabel("Intersection Automaton");
		 container2.add(intersectionLabel);
		 this.verificationIntersectionLayout=new FRLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION>(model.getIntersection());
		 this.verificationIntersectionPanel=new IntersectionAutomatonJPanel
				 <CONSTRAINEDELEMENT, STATE, 
					STATEFACTORY,
					TRANSITION, 
					TRANSITIONFACTORY,
					INTERSECTIONSTATE, 
					INTERSECTIONSTATEFACTORY,
					INTERSECTIONTRANSITION,
					INTERSECTIONTRANSITIONFACTORY,DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>>
		 	(model.getIntersection(), this, this.verificationIntersectionLayout);
		 
		 this.verificationIntersectionPanel.setTranformingMode();
		 container2.add(verificationIntersectionPanel);
		 
		 this.verificationSnapshotTab.add(container2);
		 
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
		if(positions!=null){
			this.modelLayout.setInitializer(positions);
			this.verificationModelLayout.setInitializer(positions);
		}
		this.containerModelMenu.repaint();
		if(!reload && (this.flatten && !this.tree.getTree().getModel().getRoot().equals(flatModelRefinement.getRoot()))){
			
			this.tree.changeModel(flatModelRefinement);
		}
		else{
			if(reload || ((!this.flatten) && (!this.tree.getTree().getModel().getRoot().equals(hierarchicalModelRefinement.getRoot())))){
				this.tree.changeModel(hierarchicalModelRefinement);
				reload=false;
			}
		}
		
		this.modelTabmodel.update(model);
		this.modelTab.repaint();
		this.verificationModelPanel.update(model);
		this.jframe.repaint();
	}
	@Override
	public void updateSpecification(DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> specification, Transformer<STATE, Point2D> positions){
		if(positions!=null){
			this.claimLayout.setInitializer(positions);
			this.verificationClaimLayout.setInitializer(positions);
		}
		this.claimTabClaimPanel.update(specification);
		this.verificationClaimPanel.update(specification);
		this.jframe.repaint();
		
	}

	@Override
	public void updateIntersection(DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersection, Transformer<INTERSECTIONSTATE, Point2D> positions){
		if(positions!=null){
			this.intersectionLayout.setInitializer(positions);
			this.verificationIntersectionLayout.setInitializer(positions);
		}
		this.verificationIntersectionPanel.update(intersection);
		this.intersectionPanel.update(intersection);
		this.jframe.repaint();
		
	}
	
	

	@Override
	public void updateVerificationResults(ModelCheckingResults<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> verificationResults,
			DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersection) {
		
		if(verificationResults.getResult()==1){
			this.result.setIcon(Constants.resultYes);
			this.result.setText("The property is satisfied");
			this.result.repaint();
			this.constraintVisualizer.setVisible(false);
		}
		if(verificationResults.getResult()==0){
			this.result.setIcon(Constants.resultNo);
			this.result.setText("The property is not satisfied");
			this.result.repaint();
			this.constraintVisualizer.setVisible(false);
		}
		if(verificationResults.getResult()==-1){
			this.result.setIcon(Constants.resultMaybe);
			this.result.setText("The property is possibly satisfied");
			this.showConstraints(verificationResults);
			this.result.repaint();
		}
		this.intersectionTime.setText(Double.toString(verificationResults.getIntersectionTime()));
		this.emptinessTime.setText(Double.toString(verificationResults.getEmptyTime()));
		this.constraintComputationTime.setText(Double.toString(verificationResults.getConstraintComputationTime()));
		this.verificationTime.setText(Double.toString(verificationResults.getTotalVerificationTime()));
		this.simplificationTime.setText(Double.toString(verificationResults.getSimplificationTime()));
		
		if(verificationResults.getResult()==0){
			this.verificationIntersectionPanel.highlightPath(verificationResults.getViolatingPath(), intersection, verificationResults.getViolatingPathTransitions());
		}
		this.tabbedPane.setSelectedComponent(this.verificationSnapshotTab);
		this.jframe.repaint();
	}

	public void setBrzozoski(String system){
		this.brzozowskiSystemArea.setText(system);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setChanged();
		
		
		// new 
		if(((e.getSource().equals(this.instrumentBar.newButton) || e.getSource().equals(this.filenew)) && this.tabbedPane.getSelectedComponent().equals(this.modelTab))){
			reload=true;
			this.notifyObservers(new NewModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
		}
		
		if((e.getSource().equals(this.instrumentBar.newButton) ||(e.getSource().equals(this.filenew)) && this.tabbedPane.getSelectedComponent().equals(this.claimTab))){
			this.notifyObservers(new NewClaim<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
		}
		//---------------------------------------------
		// open
		if((e.getSource().equals(this.instrumentBar.openButton) || e.getSource().equals(this.fileopen)) && this.tabbedPane.getSelectedComponent().equals(this.modelTab)){
			reload=true;
			this.notifyObservers(new LoadModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(e.getSource(), e.getID(), e.getActionCommand()));
		}
		if((e.getSource().equals(this.instrumentBar.openButton) || e.getSource().equals(this.fileopen)) && this.tabbedPane.getSelectedComponent().equals(this.claimTab)){
			this.notifyObservers(new LoadSpecification<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(e.getSource(), e.getID(), e.getActionCommand()));
		}
		if((e.getSource().equals(this.instrumentBar.openButton) || e.getSource().equals(this.fileopen)) && this.tabbedPane.getSelectedComponent().equals(this.intersectionTab)){
			this.notifyObservers(new LoadIntersection<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(e.getSource(), e.getID(), e.getActionCommand()));
		}
		
		
		// --------------------------------------------
		// saving
		if((e.getSource().equals(this.instrumentBar.saveButton) || e.getSource().equals(this.filesave))  && this.tabbedPane.getSelectedComponent().equals(this.modelTab)){
			this.notifyObservers(new SaveModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, AbstractLayout<STATE, TRANSITION>>(e.getSource(), e.getID(), e.getActionCommand(), this.modelLayout));
		}
		if((e.getSource().equals(this.instrumentBar.saveButton) || e.getSource().equals(this.filesave)) && this.tabbedPane.getSelectedComponent().equals(this.claimTab)){
			this.notifyObservers(new SaveSpecification<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, AbstractLayout<STATE, TRANSITION>>(e.getSource(), e.getID(), e.getActionCommand(), this.claimLayout));
		}
		if((e.getSource().equals(this.instrumentBar.saveButton) || e.getSource().equals(this.filesave)) && this.tabbedPane.getSelectedComponent().equals(this.intersectionTab)){
			this.notifyObservers(new SaveIntersection<CONSTRAINEDELEMENT, STATE, STATEFACTORY,
					TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, 
					INTERSECTIONTRANSITION,  AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION>>(e.getSource(), e.getID(), e.getActionCommand(), this.intersectionLayout));
		}
		
		// ltlloading
		if(e.getSource().equals(this.instrumentBar.loadLTLButton) || e.getSource().equals(this.loadClaimFromLTLMenuItem)){
			String ltlFormula=JOptionPane.showInputDialog("Type the LTL formula\n "
															+ "Syntax:\n"
															+ "Propositions: true, false, any lowercase string \n"
															+ "Boolean operators:  ! (negation) ->(implication) <-> (equivalence) &&  (and) ||  (or)\n"
															+ "Temporal operators: []  (always) <>   (eventually) U   (until) V (realease) X   (next)");
			
			this.notifyObservers(new LoadClaimAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(ltlFormula));
		}
		
		//editing
		
		if((e.getSource().equals(this.instrumentBar.editingButton) || e.getSource().equals(this.editModeMenu)) && this.tabbedPane.getSelectedComponent().equals(this.modelTab)){
			if(!flatten){
				this.enabledButtons.get(this.modelTab).put(this.instrumentBar.editingButton, false);
				this.enabledButtons.get(this.modelTab).put(this.instrumentBar.transformingButton, true);
				this.modelTabmodel.setEditingMode();
			}
		}
		
		if((e.getSource().equals(this.instrumentBar.editingButton) || e.getSource().equals(this.editModeMenu)) && this.tabbedPane.getSelectedComponent().equals(this.claimTab)){
			this.enabledButtons.get(this.claimTab).put(this.instrumentBar.editingButton, false);
			this.enabledButtons.get(this.claimTab).put(this.instrumentBar.transformingButton, true);
			this.claimTabClaimPanel.setEditingMode();
		}
		
		
		// transforming
		if((e.getSource().equals(this.instrumentBar.transformingButton) || e.getSource().equals(this.transformingModeMenu))&& this.tabbedPane.getSelectedComponent().equals(this.modelTab)){
			this.modelTabmodel.setTranformingMode();
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.editingButton, true);
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.transformingButton, false);
		}
		if((e.getSource().equals(this.instrumentBar.transformingButton) || e.getSource().equals(this.transformingModeMenu)) && this.tabbedPane.getSelectedComponent().equals(this.claimTab)){
			this.claimTabClaimPanel.setTranformingMode();
			this.enabledButtons.get(this.claimTab).put(this.instrumentBar.editingButton, true);
			this.enabledButtons.get(this.claimTab).put(this.instrumentBar.transformingButton, false);
		}
		
		
		// checking
		if(e.getSource().equals(this.instrumentBar.checkButton) || e.getSource().equals(this.runCheckerMenuItem)){
			this.notifyObservers(new CheckAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
		}
		
		if(e.getSource().equals(this.instrumentBar.flattenButton)){
			this.flatten=true;
			this.notifyObservers(new ShowFlattedModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.transformingButton, false);
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.editingButton, false);
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.flattenButton, false);
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.hierarchyButton, true);
			
			this.modelTabmodel.setTranformingMode();
		}
		if(e.getSource().equals(this.instrumentBar.hierarchyButton)){
			this.flatten=false;
			this.notifyObservers(new ShowHierarchicalModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.transformingButton, false);
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.editingButton, true);
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.flattenButton, true);
			this.enabledButtons.get(this.modelTab).put(this.instrumentBar.hierarchyButton, false);
			
			this.modelTabmodel.setTranformingMode();
		}
		if(e instanceof ActionInterface<?, ?, ?, ?, ?>){
			this.notifyObservers(e);
		}
		this.updateButtons(this.tabbedPane.getSelectedComponent());
	}

	public void displayErrorMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	
	private void createMenuBar(JFrame jframe){
		this.menuBar=new JMenuBar();
		
		
		
		//*****************
		// MENU
		//*****************
		
		// file
		this.fileMenu=new JMenu("File");
		this.filenew = new JMenuItem("New", Constants.newIcon);
		this.filenew.addActionListener(this);
		this.fileMenu.add(this.filenew);
		
		this.fileopen = new JMenuItem("Open", Constants.openIcon);
		this.fileopen.addActionListener(this);
		this.fileMenu.add(this.fileopen);
		
		this.filesave = new JMenuItem("Save", Constants.saveIcon);
		this.filesave.addActionListener(this);
		this.fileMenu.add(this.filesave);
		
		this.loadClaimFromLTLMenuItem=new JMenuItem("Load Claim From LTL", Constants.ltlIcon);
		this.loadClaimFromLTLMenuItem.addActionListener(this);
		this.fileMenu.add(this.loadClaimFromLTLMenuItem);
		
		this.menuBar.add(this.fileMenu);
		
		// editing
		this.editMenu=new JMenu("Edit");
		this.editModeMenu= new JMenuItem("Editing Mode", Constants.editingIcon);
		this.editModeMenu.addActionListener(this);
		this.editMenu.add(this.editModeMenu);
		
		this.transformingModeMenu= new JMenuItem("Transforming Mode", Constants.trasformingIcon);
		this.transformingModeMenu.addActionListener(this);
		this.editMenu.add(this.transformingModeMenu);
		
		this.menuBar.add(this.editMenu);
		
		// checking
		this.checkMenu=new JMenu("Check");
		this.runCheckerMenuItem= new JMenuItem("Run", Constants.checkIcon);
		this.runCheckerMenuItem.addActionListener(this);
		this.checkMenu.add(this.runCheckerMenuItem);
		this.menuBar.add(this.checkMenu);
		
		
		
		jframe.setJMenuBar(menuBar);
		menuBar.setVisible(true);
		
		
		this.instrumentBar=new ViewInstrumentBar(this, this.jframe);
		
		
		jframe.getContentPane().add(instrumentBar, BorderLayout.NORTH);
		
	}
	
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        panel.setLayout(new GridLayout(1, 1));
        return panel;
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
		this.modelTabmodel.highLightState(state);
		this.verificationModelPanel.highLightState(state);
		this.jframe.repaint();
		
		if(intersectionTransitions!=null){
			this.intersectionPanel.highlightTransitions(intersectionTransitions, Color.GREEN);
			this.verificationIntersectionPanel.highlightTransitions(intersectionTransitions, Color.GREEN);
		}	
	}

	@Override
	public void doNothightLightConstraint() {
		this.modelTabmodel.defaultTransformers();
		this.verificationModelPanel.defaultTransformers();
		this.intersectionPanel.defaultTransformers();
		this.verificationIntersectionPanel.defaultTransformers();
		
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
