package it.polimi.view.intersectionautomaton;

import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.ModelCheckerParameters;
import it.polimi.view.incompleteautomaton.IncompleteBuchiAutomatonManagementJPanel;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.xml.bind.JAXBException;

@SuppressWarnings("serial")
public class IntersectionAutomatonManagementJPanel<S extends State, T extends Transition<S>, S1 extends IntersectionState<S>, T1 extends Transition<S1>, A  extends IntersectionAutomaton<S, T, S1, T1>> extends
IncompleteBuchiAutomatonManagementJPanel<S1, T1, A> {

	public IntersectionAutomatonManagementJPanel(Dimension d, ActionListener container) throws JAXBException {
		super(d, container);
	}
	@Override
	protected IntersectionAutomatonJPanel<S,T, S1,T1, A> getAutomatonPanel(Dimension automatonJPanelDimension){
		 return new  IntersectionAutomatonJPanel<S,T, S1,T1, A>(automatonJPanelDimension);
	}
	
	public void updateAutomatonPanel(A a){
		super.updateAutomatonPanel(a);
	}
	public void updateVerificationResults(ModelCheckerParameters<S> verificationResults){
		this.loadingPanel.updateResults(verificationResults);
	}
}
