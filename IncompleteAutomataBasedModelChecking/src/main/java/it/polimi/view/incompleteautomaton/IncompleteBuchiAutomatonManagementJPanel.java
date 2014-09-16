package it.polimi.view.incompleteautomaton;

import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.automaton.BuchiAutomatonJPanel;
import it.polimi.view.automaton.BuchiAutomatonManagementJPanel;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.xml.bind.JAXBException;

/**
 * contains the {@link JPanel} that corresponds to an {@link IncompleteBuchiAutomaton}
 * @author claudiomenghi
 *
 * @param <S> is the type of the {@link State} of the {@link IncompleteBuchiAutomaton}
 * @param <T> is the type of the {@link State} of the {@link IncompleteBuchiAutomaton}
 * @param <A> is the type of the {@link IncompleteBuchiAutomaton}
 */
@SuppressWarnings("serial")
public class IncompleteBuchiAutomatonManagementJPanel<S extends State, T extends Transition<S>, A extends IncompleteBuchiAutomaton<S, T>> extends BuchiAutomatonManagementJPanel<S, T, A>  {


	public IncompleteBuchiAutomatonManagementJPanel(Dimension d ) throws JAXBException{
		 super(d);		 
	}	
	
	protected BuchiAutomatonJPanel<S,T, A> getAutomatonPanel(Dimension automatonJPanelDimension){
		 return new  IncompleteBuchiAutomatonJPanel<S,T, A>(automatonJPanelDimension);
	}
	
	public void update(A a) throws JAXBException{
		super.update(a);
	}
}
