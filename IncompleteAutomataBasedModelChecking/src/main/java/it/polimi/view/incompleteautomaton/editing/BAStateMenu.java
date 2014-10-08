package it.polimi.view.incompleteautomaton.editing;

import it.polimi.view.incompleteautomaton.editing.Actions.Accepting;
import it.polimi.view.incompleteautomaton.editing.Actions.DeleteVertexMenuItem;
import it.polimi.view.incompleteautomaton.editing.Actions.Initial;
import it.polimi.view.incompleteautomaton.editing.Actions.Rename;

import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class BAStateMenu extends JPopupMenu {

   public BAStateMenu(ActionListener l) {
       super("State Menu");
       
       this.stateType(l);
       this.addSeparator();
       this.add(new DeleteVertexMenuItem(l));
       this.addSeparator();
       this.add(new Rename(l));
    }
   
    protected void stateType(ActionListener l){
    	 this.add(new Initial(l));
         this.add(new Accepting(l));
    }
}