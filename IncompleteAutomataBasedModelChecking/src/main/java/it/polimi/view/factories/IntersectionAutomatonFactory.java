package it.polimi.view.factories;

import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.LabelledTransition;
import it.polimi.model.graph.State;
import it.polimi.view.intersectionautomaton.IntersectionAutomatonButtonJPanel;
import it.polimi.view.intersectionautomaton.IntersectionAutomatonJPanel;
import it.polimi.view.intersectionautomaton.IntersectionAutomatonLoadingPanel;
import it.polimi.view.intersectionautomaton.IntersectionAutomatonManagementJPanel;

import java.awt.Dimension;

public class IntersectionAutomatonFactory <S extends State, T extends LabelledTransition<S>, S1 extends IntersectionState<S>, T1 extends LabelledTransition<S1>, A extends IntersectionAutomaton<S,T, S1,T1>> extends AbstractAutomatonFactory<S1,T1,A> {

	@Override
	public IntersectionAutomatonManagementJPanel<S, T, S1, T1, A> getPanel(Dimension d) {
		return new IntersectionAutomatonManagementJPanel<S,T, S1,T1, A>(d, 
				this.getAutomatonPanel(new Dimension(d.width/2, d.height)), 
				this.getLoadingPanel(new Dimension(d.width/2, d.height)));
	}

	@Override
	protected IntersectionAutomatonJPanel<S,T, S1, T1, A> getAutomatonPanel(Dimension d) {
		return new IntersectionAutomatonJPanel<S,T, S1,T1, A>(d);
	}
	

	@Override
	protected IntersectionAutomatonLoadingPanel<S, T, S1, T1, A> getLoadingPanel(Dimension d) {
		return new IntersectionAutomatonLoadingPanel<S,T, S1,T1, A>(d, 
				this.getJButtonPanel(new Dimension(d.width, d.height/4)), 
				this.getXmlAreaDimension(new Dimension(d.width, d.height/4*3)));
	}

	@Override
	protected IntersectionAutomatonButtonJPanel getJButtonPanel(Dimension d) {
		return new IntersectionAutomatonButtonJPanel(d);
	}

}
