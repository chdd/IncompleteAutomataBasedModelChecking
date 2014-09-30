package it.polimi.view.buchiautomaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.automaton.AutomatonManagementJPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class BuchiAutomatonManagementJPanel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S, T>> extends AutomatonManagementJPanel<S,T,A> {

	
	public BuchiAutomatonManagementJPanel(Dimension d,
			BuchiAutomatonJPanel<S,T, A> automatonPanel,
			BuchiAutomatonLoadingJPanel<S, T, A> loadingPanel
			) {
		
		super(new FlowLayout(), d, automatonPanel, loadingPanel);
	}
	
	
	public void updateAutomatonPanel(A a){
		this.automatonPanel.update(a);
		
	}
	public void updateLoadingPanel(A a){
		((BuchiAutomatonLoadingJPanel<S,T,A>) this.loadingPanel).update(a);
	}
	
	
	public String getAutomatonXML() {
		return ((BuchiAutomatonLoadingJPanel<S,T,A>)this.loadingPanel).getAutomatonXML();
	}
}
