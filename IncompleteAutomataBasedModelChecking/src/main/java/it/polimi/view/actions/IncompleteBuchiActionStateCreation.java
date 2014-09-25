package it.polimi.view.actions;

public class IncompleteBuchiActionStateCreation extends BuchiActionStateCreation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final boolean regular;
	public IncompleteBuchiActionStateCreation(Object source, int id,
			String command, String name, boolean regular, boolean initial,
			boolean accepting) {
		super(source, id, command, name,  initial, accepting);
		this.regular=regular;
	
	}

	/**
	 * @return the regular
	 */
	public boolean isRegular() {
		return regular;
	}

}
