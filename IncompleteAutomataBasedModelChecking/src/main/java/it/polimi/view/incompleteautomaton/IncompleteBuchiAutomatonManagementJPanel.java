package it.polimi.view.incompleteautomaton;

import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.State;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.view.automaton.AutomatonManagementJPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

/**
 * contains the {@link JPanel} that corresponds to an {@link IncompleteBuchiAutomaton}
 * @author claudiomenghi
 *
 * @param <S> is the type of the {@link State} of the {@link IncompleteBuchiAutomaton}
 * @param <T> is the type of the {@link State} of the {@link IncompleteBuchiAutomaton}
 * @param <A> is the type of the {@link IncompleteBuchiAutomaton}
 */
@SuppressWarnings("serial")
public class IncompleteBuchiAutomatonManagementJPanel<S extends State, T extends LabelledTransition<S>, A extends IncompleteBuchiAutomaton<S, T>> extends AutomatonManagementJPanel<S,T,A>  {


	public IncompleteBuchiAutomatonManagementJPanel(Dimension d,
			IncompleteBuchiAutomatonJPanel<S,T, A> automatonPanel,
			IncompleteBuchiAutomatonLoadingPanel<S, T, A> loadingPanel
			) 
	{
		super(new FlowLayout(), d, automatonPanel, loadingPanel);
		
	}
	
	
	public void updateAutomatonPanel(A a){
		this.automatonPanel.update(a);
		
	}
	public void updateLoadingPanel(A a){
		if(a==null){
			throw new IllegalArgumentException("The incomplete automaton a cannot be null");
		}
	//	((IncompleteBuchiAutomatonLoadingPanel<S, T, A>) this.loadingPanel).update(a.toXMLString());
	}
	
	
	public String getAutomatonXML() {
		return ((IncompleteBuchiAutomatonLoadingPanel<S, T, A>) this.loadingPanel).getAutomatonXML();
	}
	
	
}
