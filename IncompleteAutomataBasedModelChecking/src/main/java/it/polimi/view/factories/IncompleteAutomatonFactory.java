package it.polimi.view.factories;

import it.polimi.model.ba.LabelledTransition;
import it.polimi.model.graph.State;
import it.polimi.model.iba.IncompleteBuchiAutomaton;
import it.polimi.view.incompleteautomaton.IncompleteBuchiAutomatonJPanel;
import it.polimi.view.incompleteautomaton.IncompleteBuchiAutomatonLoadingPanel;
import it.polimi.view.incompleteautomaton.IncompleteBuchiAutomatonManagementJPanel;
import it.polimi.view.incompleteautomaton.IncompleteBuchiButtonJPanel;

import java.awt.Dimension;

public class IncompleteAutomatonFactory<S extends State, T extends LabelledTransition<S>, A extends IncompleteBuchiAutomaton<S,T>> extends AbstractAutomatonFactory<S,T,A>{

	@Override
	public IncompleteBuchiAutomatonManagementJPanel<S,T,A> getPanel(Dimension d) {
		return new IncompleteBuchiAutomatonManagementJPanel<S,T,A>(d, 
				this.getAutomatonPanel(new Dimension(d.width/2, d.height)), 
				this.getLoadingPanel(new Dimension(d.width/2, d.height)));
	}

	@Override
	protected IncompleteBuchiAutomatonJPanel<S,T, A> getAutomatonPanel(Dimension d) {
		return new IncompleteBuchiAutomatonJPanel<S,T,A>(d);
	}

	@Override
	protected IncompleteBuchiAutomatonLoadingPanel<S, T, A> getLoadingPanel(Dimension d) {
		return new IncompleteBuchiAutomatonLoadingPanel<S,T,A>(d, 
				this.getJButtonPanel(new Dimension(d.width, d.height/4)), 
				this.getXmlAreaDimension(new Dimension(d.width, d.height/4*3)));
	}

	@Override
	protected IncompleteBuchiButtonJPanel<S, T, A> getJButtonPanel(Dimension d) {
		return new IncompleteBuchiButtonJPanel<S,T,A>(d);
	}

}
