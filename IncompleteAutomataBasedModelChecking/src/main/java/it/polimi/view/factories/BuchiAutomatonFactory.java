package it.polimi.view.factories;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.State;
import it.polimi.view.buchiautomaton.BuchiAutomatonJPanel;
import it.polimi.view.buchiautomaton.BuchiAutomatonLoadingJPanel;
import it.polimi.view.buchiautomaton.BuchiAutomatonManagementJPanel;
import it.polimi.view.buchiautomaton.BuchiButtonJPanel;

import java.awt.Dimension;

public class BuchiAutomatonFactory<S extends State, T extends LabelledTransition<S>, A extends BuchiAutomaton<S,T>> extends AbstractAutomatonFactory<S,T,A>{

	
	public BuchiAutomatonFactory() {
		super();
	}

	@Override
	public BuchiAutomatonManagementJPanel<S,T,A> getPanel(Dimension d) {
		return new BuchiAutomatonManagementJPanel<S,T,A>(d, 
				this.getAutomatonPanel(new Dimension(d.width, d.height/8*7)), 
				this.getLoadingPanel(new Dimension(d.width, d.height/8*1)));
	}

	@Override
	protected BuchiAutomatonJPanel<S,T,A> getAutomatonPanel(Dimension d) {
		return new BuchiAutomatonJPanel<S,T,A>(d);
	}

	

	@Override
	protected BuchiAutomatonLoadingJPanel<S,T,A> getLoadingPanel(Dimension d) {
		
		return new BuchiAutomatonLoadingJPanel<S,T,A>(d,
				this.getJButtonPanel(new Dimension(d.width, d.height)));
	}

	@Override
	protected BuchiButtonJPanel<S,T,A> getJButtonPanel(Dimension d) {
		return new BuchiButtonJPanel<S,T,A>(d);
	}

}
