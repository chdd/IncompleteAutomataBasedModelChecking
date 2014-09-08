package it.polimi.view.intersectionautomaton;

import it.polimi.browzozky.predicates.AbstractConstraint;
import it.polimi.browzozky.predicates.types.EmptyConstraint;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecking.ModelChecker;
import it.polimi.modelchecking.ModelCheckerParameters;
import it.polimi.view.automaton.AutXMLTextArea;
import it.polimi.view.automaton.AutomatonJPanel;
import it.polimi.view.extendedautomaton.ExtendedAutomatonJPanel;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileSystemView;
import javax.xml.bind.JAXBException;



@SuppressWarnings("serial")
public class IntersectionAutJPanel<S1 extends State, T1 extends Transition<S1>,
									S extends IntersectionState<S1>, T extends Transition<S>,
									A extends IntersectionAutomaton<S1, T1, S, T>> 
									extends JPanel implements ActionListener {

	
	private IntersectionAutJLabel<S1,T1, S, T, A> label;
	private ExtendedAutomatonJPanel<S1, T1, IncompleteBuchiAutomaton<S1,T1>> panel1;
	private AutomatonJPanel<S1, T1, BuchiAutomaton<S1,T1>> panel2;
	private AutXMLTextArea<S,T,A> area;
	private IntersectionAutTextArea<S1,T1, S, T, A> language;
	private A  a;
	
	
	public IntersectionAutJPanel(Dimension d, A  a , 
						ExtendedAutomatonJPanel<S1, T1, IncompleteBuchiAutomaton<S1,T1>> panel1,
						AutomatonJPanel<S1, T1, BuchiAutomaton<S1, T1>> panel2) throws JAXBException{
	
		 super(null);
		 this.panel1=panel1;
		 this.panel2=panel2;
		 this.setSize(d);
		 this.a=a;
		 label=new IntersectionAutJLabel<S1,T1, S, T, A>(new Dimension(d.width/3,d.height/3), a);
		 label.setVisible(true);
		 label.setSize(new Dimension(d.width/3,d.height/3));
		 this.add(label); 
		 this.label.setVisible(true);
		 
		 JButton btnInsert = new JButton("Run Model Checking");
		 btnInsert.setSize(new Dimension(d.width/3/3, d.height/3/5));
		 btnInsert.setOpaque(true);
		 btnInsert.setLocation(d.width/3+d.width/3/3, d.height/3/5/20);
		 btnInsert.setVisible(true);
		 btnInsert.addActionListener(this);
		 this.add(btnInsert);
		 
		 area=new AutXMLTextArea<S,T,A>(new Dimension(d.width/3-200,d.height/5), a);
		 JScrollPane scroll = new JScrollPane(area);
		 scroll.setSize(new Dimension(d.width/3,d.height/5));
		 scroll.setLocation(d.width/3, d.height/3/5/20+d.height/3/5);
		 scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		 this.add(scroll);
		    
		 JButton btnWriteConstraints = new JButton("Save Constraints");
		 btnWriteConstraints.setSize(new Dimension(d.width/3/3, d.height/3/5));
		 btnWriteConstraints.setOpaque(true);
		 btnWriteConstraints.setLocation(d.width/3*2+d.width/3/3, d.height/3/5/20);
		 btnWriteConstraints.setVisible(true);
		 btnWriteConstraints.addActionListener(this);
		 this.add(btnWriteConstraints);
		 
		 language=new IntersectionAutTextArea<S1,T1, S, T, A>(new Dimension(d.width/3,d.height/5), new Point(d.width/3*2, d.height/3/5/20+d.height/3/5), a);
		 this.add(language);
		 this.revalidate();
		 this.setVisible(true);
	}


	@Override
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Run Model Checking"){
			
			IncompleteBuchiAutomaton<S1,T1> a1=this.panel1.getAutomaton();
			BuchiAutomaton<S1,T1> a2=this.panel2.getAutomaton();
			ModelChecker<S1, T1> mc=new ModelChecker<S1, T1>(a1, a2, new ModelCheckerParameters());
			AbstractConstraint<S1> constraints=new EmptyConstraint<>();
			int result=mc.check(constraints);
			
			A ris=(A) mc.getIntersection();
			 
			this.label.updateGraph(ris);
			try {
				this.area.setText(ris.toXMLString());
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.language.update(ris, result);
		}
		if(e.getActionCommand() == "Save Constraints"){
			JFileChooser c = new JFileChooser(FileSystemView.getFileSystemView());
			  c.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			  // Demonstrate "Open" dialog:
		      int rVal = c.showOpenDialog(this);
		      if (rVal == JFileChooser.APPROVE_OPTION) {
		    	  String file=c.getSelectedFile().getName();
		    	  String folder=c.getCurrentDirectory().toString();
		    	 
		    	  try {
		    		 String toBeWritten=this.language.getText();
		    		 PrintWriter out = new PrintWriter(folder+"/"+file);
		    		 out.println(toBeWritten);
		    		 out.close();
		    	} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
		    }
		}
		
	}
	
	
	
	
}