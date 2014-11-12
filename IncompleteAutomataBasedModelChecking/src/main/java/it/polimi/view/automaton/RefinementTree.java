package it.polimi.view.automaton;

/**
 * This application that requires the following additional files:
 *   TreeDemoHelp.html
 *    arnold.html
 *    bloch.html
 *    chan.html
 *    jls.html
 *    swingtutorial.html
 *    tutorial.html
 *    tutorialcont.html
 *    vm.html
 */
import it.polimi.controller.viewRefinement.ViewFlatStateRefinementAction;
import it.polimi.controller.viewRefinement.ViewHierarchyStateRefinementAction;
import it.polimi.model.RefinementNode;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeSelectionModel;

/**
 * @author claudiomenghi
 *
 * @param <CONSTRAINEDELEMENT>
 * @param <STATE>
 * @param <STATEFACTORY>
 * @param <TRANSITION>
 * @param <TRANSITIONFACTORY>
 * @param <AUTOMATON>
 */
@SuppressWarnings("serial")
public class RefinementTree<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>,
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>> extends JPanel 
implements TreeSelectionListener{
    
	private boolean flat=false;
	
	private JTree tree;
    private ActionListener l;
    private Dimension d;

    public JTree getTree(){
    	return this.tree;
    }
    
    
    
    public RefinementTree(Dimension d, DefaultTreeModel treeModel, ActionListener l) {
        super();
        this.d=d;
        this.tree = new JTree(treeModel);
        this.l=l;
        this.setTreeProperties();
    }
    
    private void setTreeProperties(){
        this.setSize(d);
        this.setPreferredSize(d);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
  
    	 this.tree.setSize(d);
         this.tree.setPreferredSize(d);
         this.tree.setMaximumSize(d);
         this.tree.setMinimumSize(d);
         this.tree.setEditable(true);
         this.tree.getSelectionModel().setSelectionMode
                 (TreeSelectionModel.SINGLE_TREE_SELECTION);
         this.tree.setShowsRootHandles(true);
         this.add(tree);
         this.tree.setCellEditor(new NullCellEditor());
         this.tree.addTreeSelectionListener(this);
         this.tree.setCellRenderer(new MyRender());
         this.tree.setLargeModel(true);
    }
    

    public void changeModel(DefaultTreeModel treeModel){
    	this.remove(this.tree);
		this.tree=new JTree(treeModel);
		this.add(this.tree);
		this.setTreeProperties();
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		
		RefinementNode<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> refnode=(RefinementNode<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>) 
		 ((DefaultMutableTreeNode)e.getNewLeadSelectionPath().getLastPathComponent())
		 .getUserObject();

		if(flat){
			l.actionPerformed(new ViewFlatStateRefinementAction
					<CONSTRAINEDELEMENT, STATE, 
					STATEFACTORY, 
					TRANSITION, 
					TRANSITIONFACTORY>(e.getSource(), 0, "Refinement", 
							refnode));
		}
		else{
			l.actionPerformed(new ViewHierarchyStateRefinementAction
					<CONSTRAINEDELEMENT, STATE, 
					STATEFACTORY, 
					TRANSITION, 
					TRANSITIONFACTORY>(e.getSource(), 0, "Refinement", 
							refnode));
		}
	}
	
	
	private class NullCellEditor implements TreeCellEditor{

		@Override
		public Object getCellEditorValue() {
			return null;
		}

		@Override
		public boolean isCellEditable(EventObject anEvent) {
			return false;
		}

		@Override
		public boolean shouldSelectCell(EventObject anEvent) {
			return false;
		}

		@Override
		public boolean stopCellEditing() {
			return false;
		}

		@Override
		public void cancelCellEditing() {
		}

		@Override
		public void addCellEditorListener(CellEditorListener l) {
		}

		@Override
		public void removeCellEditorListener(CellEditorListener l) {
		}

		@Override
		public Component getTreeCellEditorComponent(JTree tree, Object value,
				boolean isSelected, boolean expanded, boolean leaf, int row) {
			return null;
		}
	}
	
	public class MyRender extends DefaultTreeCellRenderer{
		
		private final ImageIcon treeIcon= new ImageIcon(this.getClass().getResource("/img/Node.png"));
		
		public Component getTreeCellRendererComponent(
                JTree tree,
                Object value,
                boolean sel,
                boolean expanded,
                boolean leaf,
                int row,
                boolean hasFocus) 
		{

			super.getTreeCellRendererComponent(
					tree, value, sel,
					expanded, leaf, row,
					hasFocus);
			this.setIcon(this.treeIcon);
			return this;
		}
	}
}