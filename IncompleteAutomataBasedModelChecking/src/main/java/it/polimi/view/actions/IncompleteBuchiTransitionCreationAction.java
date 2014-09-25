package it.polimi.view.actions;

public class IncompleteBuchiTransitionCreationAction extends BuchiTransitionCreationAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncompleteBuchiTransitionCreationAction(Object source, int id,
			String command, String sourceState, String destinationState,
			String character) {
		super(source, id, command, sourceState, destinationState, character);
	}

}
