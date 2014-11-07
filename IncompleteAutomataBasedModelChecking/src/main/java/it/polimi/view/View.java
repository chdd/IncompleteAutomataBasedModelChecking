package it.polimi.view;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.controller.actions.CheckAction;
import it.polimi.controller.actions.LoadClaimAction;
import it.polimi.controller.actions.ShowConstraint;
import it.polimi.controller.actions.createnew.NewClaim;
import it.polimi.controller.actions.createnew.NewModel;
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Set;

import javax.swing.BorderFactory;
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
import javax.swing.JTextField;
import javax.swing.JToolBar;

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
			extends Observable implements ViewInterface<CONSTRAINEDELEMENT, STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>, ActionListener{

	private static final String appName="CHIA: CHecker for Incompete Automata";
	
	// Icons
	private final ImageIcon newIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/actions/document-new.png"));
	private final ImageIcon openIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/actions/document-open.png"));
	private final ImageIcon saveIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/devices/media-floppy.png"));
	private final ImageIcon ltlIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/devices/input-keyboard.png"));
	private final ImageIcon editingIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/categories/applications-office.png"));
	private final ImageIcon trasformingIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/actions/view-fullscreen.png"));
	private final ImageIcon checkIcon = new ImageIcon(this.getClass().getResource("/org/freedesktop/tango/22x22/categories/applications-system.png"));
	private final ImageIcon resultYes=new ImageIcon(this.getClass().getResource("/img/Yes.png"));
	private final ImageIcon resultNo=new ImageIcon(this.getClass().getResource("/img/No.png"));
	private final ImageIcon resultMaybe=new ImageIcon(this.getClass().getResource("/img/Maybe.png"));
	private final ImageIcon resultInitial=new ImageIcon(this.getClass().getResource("/img/QuestionMark.png"));
	
	
	private static final int verificationIconSize=64;
	
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
	private final String ltlLoadingMessage="<html>Loads the claim form an LTL formula</html>";
	private final String checkingMessage="<html>Checks if the model satisfies, possibly satisfies or not satisfies the claim</html>";		
	private final String newMessage="<html>Creates a new Model or Claims<br> in relation with the selected tab</html>";
	private final String openMessage="<html>Load the Model, Claims or Intersection<br> in relation with the selected tab</html>";
	private final String saveMessage="<html>Save the Model, Claims or Intersection<br> in relation with the selected tab</html>";
	
	
	// ------------------------------------
	// menuBar
	// ------------------------------------
	private JMenuBar menuBar;
	
	// file menu
	private JMenu fileMenu;
	private JMenuItem filenew;
	private JMenuItem fileopen;
	private JMenuItem filesave;
	
	private JMenuItem loadClaimFromLTLMenuItem;
	
	// edit menu
	private JMenu editMenu;
	private JMenuItem editModeMenu;
	private JMenuItem transformingModeMenu;
	
	//check menu
	private JMenu checkMenu;
	private JMenuItem runCheckerMenuItem;
	
	// ------------------------------------
	// instrumentBar
	// ------------------------------------
	private JToolBar instrumentBar;
	
	// file
	private JButton saveButton;
	private JButton openButton;
	private JButton newButton;
	
	// from ltl
	private JButton loadLTLButton;
	
	// editing
	private JButton editingButton;
	private JButton transformingButton;
	
	// checking
	private JButton checkButton;
	
	
	
	// ------------------------------------
	// tabs
	// ------------------------------------
	// model
	private JComponent modelTab;
	private IncompleteBuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableIBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>> modelTabmodel;
	private AbstractLayout<STATE, TRANSITION> modelLayout;
	
	
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
	private JButton viewConstraints;
	
	private JTabbedPane tabbedPane;
	private JTextField intersectionTime;
	private JTextField emptinessTime;
	private JTextField constraintComputationTime;
	private JTextField verificationTime;
	private JTextField simplificationTime;
	
	
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
	
	private ResultsJPanel<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY,IntBAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY >> verificationSnapshotResultsPanel;
	
	// main frame
	private JFrame jframe;
	
	public View(DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> model,
			DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> claim,
			DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersection) {
		
		 this.jframe=new JFrame();
		 this.jframe.setTitle(appName);
		 // setting the size of the jframe
		 this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.jframe.getContentPane().setBackground(Color.getColor("myColor"));
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 this.jframe.setSize(screenSize);
		 
		 // creating the menu bar
		 this.createMenuBar(jframe);
		 
		 
		 tabbedPane = new JTabbedPane();
		 tabbedPane.setSize(this.jframe.getContentPane().getSize());
		 tabbedPane.setPreferredSize(this.jframe.getContentPane().getSize());
		 
		 modelTab = makeTextPanel("Model");
		 tabbedPane.addTab("Model",modelTab);
		 tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
		
		 claimTab = makeTextPanel("Claim");
		 tabbedPane.addTab("Claim", claimTab);
		 tabbedPane.setMnemonicAt(1, KeyEvent.VK_3);
		 
		 
		 intersectionTab= makeTextPanel("Intersection");
		 tabbedPane.addTab("Intersection", intersectionTab);
		 tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		 
		 verificationSnapshotTab = makeTextPanel("Verification");
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
		  
		 
		 modelTab.add(containerModelMenu);
		 this.modelLayout=new KKLayout<STATE,TRANSITION>(model);
		 this.modelTabmodel=new IncompleteBuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableIBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>>(model, this, this.modelLayout);
		 modelTab.add(modelTabmodel);
		 
		 //******************************************************************************************************************************
		 // claim tab
		 //******************************************************************************************************************************
		 claimTab.setLayout(new BoxLayout(claimTab, BoxLayout.PAGE_AXIS));
		 
		 JPanel containerClaimMenu=new JPanel();
		 containerClaimMenu.setLayout(new BoxLayout(containerClaimMenu, BoxLayout.X_AXIS));
		 
		 this.claimLayout=new FRLayout<STATE,TRANSITION>(claim);
		 this.claimTabClaimPanel=new BuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>>(claim, this,  this.claimLayout);
		 
		 claimTab.add(containerClaimMenu);
		 claimTab.add(this.claimTabClaimPanel);
		 
		 
		 
		 //******************************************************************************************************************************
		 // intersection tab
		 //******************************************************************************************************************************
		 intersectionTab.setLayout(new BoxLayout(intersectionTab, BoxLayout.Y_AXIS));
		 
		 this.intersectionLayout=new FRLayout<INTERSECTIONSTATE,INTERSECTIONTRANSITION>(intersection);
		 this.intersectionPanel=new IntersectionAutomatonJPanel
				 <CONSTRAINEDELEMENT, 
				 STATE, 
					STATEFACTORY,
					TRANSITION, 
					TRANSITIONFACTORY,
					INTERSECTIONSTATE, 
					INTERSECTIONSTATEFACTORY,
					INTERSECTIONTRANSITION,
					INTERSECTIONTRANSITIONFACTORY,DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>>(intersection, this, this.intersectionLayout);
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
		
		 
		 this.verificationModelLayout=new FRLayout<STATE,TRANSITION>(model);
		 container1.add(new JLabel("Model"));
		 this.verificationModelPanel=new IncompleteBuchiAutomatonJPanel
				 <CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableIBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>> (model, this, this.verificationModelLayout);
		 this.verificationModelPanel.setTranformingMode();
		 container1.add(verificationModelPanel);
		

		 this.verificationClaimLayout=new FRLayout<STATE,TRANSITION>(claim);
		 container1.add(new JLabel("Claim"));
		 this.verificationClaimPanel=new BuchiAutomatonJPanel<CONSTRAINEDELEMENT, STATE,STATEFACTORY, TRANSITION, TRANSITIONFACTORY, DrawableBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>> (claim, this, this.verificationClaimLayout);
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
		 this.result=new JLabel(this.resultInitial);
		 this.result.setSize(new Dimension(verificationIconSize, verificationIconSize));
		 
		 resultPanel.add(result);
		 this.viewConstraints=new JButton("Explore Constraints");
		 this.viewConstraints.addActionListener(this);
		 this.viewConstraints.setVisible(false);
		 resultPanel.add(viewConstraints);
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
		 
		 
		 this.verificationSnapshotResultsPanel=new ResultsJPanel<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY, IntBAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>>();
		 //resultPanel.add(verificationSnapshotResultsPanel);
		 
		 
		 // Intersection automaton
		 
		 JLabel intersectionLabel=new JLabel("Intersection Automaton");
		 container2.add(intersectionLabel);
		 this.verificationIntersectionLayout=new FRLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION>(intersection);
		 this.verificationIntersectionPanel=new IntersectionAutomatonJPanel
				 <CONSTRAINEDELEMENT, STATE, 
					STATEFACTORY,
					TRANSITION, 
					TRANSITIONFACTORY,
					INTERSECTIONSTATE, 
					INTERSECTIONSTATEFACTORY,
					INTERSECTIONTRANSITION,
					INTERSECTIONTRANSITIONFACTORY,DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>>
		 	(intersection, this, this.verificationIntersectionLayout);
		 
		 this.verificationIntersectionPanel.setTranformingMode();
		 container2.add(verificationIntersectionPanel);
		 
		 
			
		 verificationSnapshotTab.add(container2);
		 jframe.setResizable(true);
		 jframe.setVisible(true);
		 
	}
	
	@Override
	public void updateModel(DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> model, Transformer<STATE, Point2D> positions){
		if(positions!=null){
			this.modelLayout.setInitializer(positions);
			this.verificationModelLayout.setInitializer(positions);
		}
		this.modelTabmodel.update(model);
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
			this.result.setIcon(this.resultYes);
			this.result.setText("The property is satisfied");
			this.viewConstraints.setVisible(false);
			this.result.repaint();
		}
		if(verificationResults.getResult()==0){
			this.result.setIcon(this.resultNo);
			this.result.setText("The property is not satisfied");
			this.viewConstraints.setVisible(false);
			this.result.repaint();
		}
		if(verificationResults.getResult()==-1){
			this.result.setIcon(this.resultMaybe);
			this.result.setText("The property is possibly satisfied");
			this.viewConstraints.setVisible(true);
			this.viewConstraints.setEnabled(true);
			this.result.repaint();
		}
		this.intersectionTime.setText(Double.toString(verificationResults.getIntersectionTime()));
		this.emptinessTime.setText(Double.toString(verificationResults.getEmptyTime()));
		this.constraintComputationTime.setText(Double.toString(verificationResults.getConstraintComputationTime()));
		this.verificationTime.setText(Double.toString(verificationResults.getTotalVerificationTime()));
		this.simplificationTime.setText(Double.toString(verificationResults.getSimplificationTime()));
		
		this.verificationSnapshotResultsPanel.updateResults(verificationResults);
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
		if((e.getSource().equals(this.filenew) && this.tabbedPane.getSelectedComponent().equals(this.modelTab))){
			this.notifyObservers(new NewModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
		}
		
		if((e.getSource().equals(this.filenew) && this.tabbedPane.getSelectedComponent().equals(this.claimTab))){
			this.notifyObservers(new NewClaim<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
		}
		//---------------------------------------------
		// open
		if((e.getSource().equals(this.openButton) || e.getSource().equals(this.fileopen)) && this.tabbedPane.getSelectedComponent().equals(this.modelTab)){
			this.notifyObservers(new LoadModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(e.getSource(), e.getID(), e.getActionCommand()));
		}
		if((e.getSource().equals(this.openButton) || e.getSource().equals(this.fileopen)) && this.tabbedPane.getSelectedComponent().equals(this.claimTab)){
			this.notifyObservers(new LoadSpecification<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(e.getSource(), e.getID(), e.getActionCommand()));
		}
		if((e.getSource().equals(this.openButton) || e.getSource().equals(this.fileopen)) && this.tabbedPane.getSelectedComponent().equals(this.intersectionTab)){
			this.notifyObservers(new LoadIntersection<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(e.getSource(), e.getID(), e.getActionCommand()));
		}
		
		
		// --------------------------------------------
		// saving
		if((e.getSource().equals(this.saveButton) || e.getSource().equals(this.filesave))  && this.tabbedPane.getSelectedComponent().equals(this.modelTab)){
			this.notifyObservers(new SaveModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, AbstractLayout<STATE, TRANSITION>>(e.getSource(), e.getID(), e.getActionCommand(), this.modelLayout));
		}
		if((e.getSource().equals(this.saveButton) || e.getSource().equals(this.filesave)) && this.tabbedPane.getSelectedComponent().equals(this.claimTab)){
			this.notifyObservers(new SaveSpecification<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, AbstractLayout<STATE, TRANSITION>>(e.getSource(), e.getID(), e.getActionCommand(), this.claimLayout));
		}
		if((e.getSource().equals(this.saveButton) || e.getSource().equals(this.filesave)) && this.tabbedPane.getSelectedComponent().equals(this.intersectionTab)){
			this.notifyObservers(new SaveIntersection<CONSTRAINEDELEMENT, STATE, STATEFACTORY,
					TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, 
					INTERSECTIONTRANSITION,  AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION>>(e.getSource(), e.getID(), e.getActionCommand(), this.intersectionLayout));
		}
		
		// ltlloading
		if(e.getSource().equals(this.loadLTLButton) || e.getSource().equals(this.loadClaimFromLTLMenuItem)){
			String ltlFormula=JOptionPane.showInputDialog("Type the LTL formula\n "
															+ "Syntax:\n"
															+ "Propositions: true, false, any lowercase string \n"
															+ "Boolean operators:  ! (negation) ->(implication) <-> (equivalence) &&  (and) ||  (or)\n"
															+ "Temporal operators: []  (always) <>   (eventually) U   (until) V (realease) X   (next)");
			
			this.notifyObservers(new LoadClaimAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(ltlFormula));
		}
		
		//editing
		
		if((e.getSource().equals(this.editingButton) || e.getSource().equals(this.editModeMenu)) && this.tabbedPane.getSelectedComponent().equals(this.modelTab)){
			this.modelTabmodel.setEditingMode();
		}
		if((e.getSource().equals(this.editingButton)|| e.getSource().equals(this.editModeMenu))  && this.tabbedPane.getSelectedComponent().equals(this.claimTab)){
			this.claimTabClaimPanel.setEditingMode();
		}
		
		// transforming
		if((e.getSource().equals(this.transformingButton) || e.getSource().equals(this.transformingModeMenu))  && this.tabbedPane.getSelectedComponent().equals(this.modelTab)){
			this.modelTabmodel.setTranformingMode();
		}
		if((e.getSource().equals(this.transformingButton) || e.getSource().equals(this.transformingModeMenu)) && this.tabbedPane.getSelectedComponent().equals(this.claimTab)){
			this.claimTabClaimPanel.setTranformingMode();
		}
		
		// checking
		if(e.getSource().equals(this.checkButton) || e.getSource().equals(this.runCheckerMenuItem)){
			this.notifyObservers(new CheckAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
		}
		// constraint exploring
		if(e.getSource().equals(this.viewConstraints)){
			this.notifyObservers(new ShowConstraint<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
			
		}
		if(e instanceof ActionInterface<?, ?, ?, ?, ?>){
			this.notifyObservers(e);
		}
		
		
		
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
		this.filenew = new JMenuItem("New", this.newIcon);
		this.filenew.addActionListener(this);
		this.fileMenu.add(this.filenew);
		
		this.fileopen = new JMenuItem("Open", this.openIcon);
		this.fileopen.addActionListener(this);
		this.fileMenu.add(this.fileopen);
		
		this.filesave = new JMenuItem("Save", this.saveIcon);
		this.filesave.addActionListener(this);
		this.fileMenu.add(this.filesave);
		
		this.loadClaimFromLTLMenuItem=new JMenuItem("Load Claim From LTL", this.ltlIcon);
		this.loadClaimFromLTLMenuItem.addActionListener(this);
		this.fileMenu.add(this.loadClaimFromLTLMenuItem);
		
		this.menuBar.add(this.fileMenu);
		
		// editing
		this.editMenu=new JMenu("Edit");
		this.editModeMenu= new JMenuItem("Editing Mode", this.editingIcon);
		this.editModeMenu.addActionListener(this);
		this.editMenu.add(this.editModeMenu);
		
		this.transformingModeMenu= new JMenuItem("Transforming Mode", this.trasformingIcon);
		this.transformingModeMenu.addActionListener(this);
		this.editMenu.add(this.transformingModeMenu);
		
		this.menuBar.add(this.editMenu);
		
		// checking
		this.checkMenu=new JMenu("Check");
		this.runCheckerMenuItem= new JMenuItem("Run", this.checkIcon);
		this.runCheckerMenuItem.addActionListener(this);
		this.checkMenu.add(this.runCheckerMenuItem);
		this.menuBar.add(this.checkMenu);
		
		
		
		jframe.setJMenuBar(menuBar);
		menuBar.setVisible(true);
		
		
		//*****************
		// INSTRUMENT BAR
		//*****************
		
		this.instrumentBar=new JToolBar();
		this.instrumentBar.setBackground(this.jframe.getContentPane().getBackground());
		
		// file
		this.newButton=new JButton(this.newIcon);
		this.newButton.addActionListener(this);
		this.newButton.setFocusPainted(false);
		this.newButton.setToolTipText(this.newMessage);
		
		this.openButton=new JButton(this.openIcon);
		this.openButton.addActionListener(this);
		this.openButton.setFocusPainted(false);
		this.openButton.setToolTipText(this.openMessage);
		
		this.saveButton=new JButton(this.saveIcon);
		this.saveButton.addActionListener(this);
		this.saveButton.setFocusPainted(false);
		this.saveButton.setToolTipText(this.saveMessage);
		
		this.instrumentBar.add(this.newButton);
		this.instrumentBar.add(this.openButton);
		this.instrumentBar.add(this.saveButton);
		
		this.instrumentBar.addSeparator();
		
		// from ltl formula
		this.loadLTLButton=new JButton(this.ltlIcon);
		this.loadLTLButton.addActionListener(this);
		this.loadLTLButton.setFocusPainted(false);
		this.loadLTLButton.setToolTipText(this.ltlLoadingMessage);
		
		this.instrumentBar.add(this.loadLTLButton);
		this.instrumentBar.addSeparator();
		
		// editing
		this.editingButton=new JButton(this.editingIcon);
		this.editingButton.addActionListener(this);
		this.editingButton.setFocusPainted(false);
		this.editingButton.setToolTipText(this.editingMessage);
		
		this.transformingButton=new JButton(this.trasformingIcon);
		this.transformingButton.addActionListener(this);
		this.transformingButton.setFocusPainted(false);
		this.transformingButton.setToolTipText(this.transorfmingMessage);
		
		this.instrumentBar.add(this.editingButton);
		this.instrumentBar.add(this.transformingButton);
		this.instrumentBar.addSeparator();
		
		//checking
		this.checkButton=new JButton(this.checkIcon);
		this.checkButton.addActionListener(this);
		this.checkButton.setFocusPainted(false);
		this.checkButton.setToolTipText(this.checkingMessage);
		
		this.instrumentBar.add(this.checkButton);
		
		jframe.getContentPane().add(instrumentBar, BorderLayout.NORTH);
		
	}
	
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        panel.setLayout(new GridLayout(1, 1));
        return panel;
    }

	@Override
	public void updateModel(
			DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> model) {
		this.updateModel(model, null);
	}

	@Override
	public void updateClaim(
			DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> specification) {
		this.updateSpecification(specification, null);
		
	}

	@Override
	public void showConstraints(
			ModelCheckingResults<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> verificationResults) {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.jframe.setSize(new Dimension(screenSize.width, screenSize.height/10*8-this.jframe.getContentPane().getInsets().top-this.jframe.getContentPane().getInsets().bottom));
		
		
		ConstraintJDialog<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY,
			INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, 
			INTERSECTIONTRANSITIONFACTORY> constraintJDialog=new ConstraintJDialog<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY,
				INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, 
				INTERSECTIONTRANSITIONFACTORY>(verificationResults, this, new Dimension(screenSize.width, screenSize.height/100*17));
		

		constraintJDialog.setLocation(new Point(0, screenSize.height/100*90));
		constraintJDialog.setVisible(true);
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
}
