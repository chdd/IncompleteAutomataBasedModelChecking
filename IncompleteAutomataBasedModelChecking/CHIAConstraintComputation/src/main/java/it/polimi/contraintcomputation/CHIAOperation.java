package it.polimi.contraintcomputation;

/**
 * contains the abstract description of a CHIA checking operation. It contains a
 * flag that specifies whenever the operation has been performed
 * 
 * @author Claudio Menghi
 *
 */
public abstract class CHIAOperation {

	/**
	 * is a flag that specifies if the operation has been performed or not
	 */
	private boolean performed;

	/**
	 * creates a new CHIAOperation, set the performed flag to false
	 */
	public CHIAOperation() {
		setPerformed(false);
	}

	/**
	 * @return the performed
	 */
	public boolean isPerformed() {
		return performed;
	}

	/**
	 * @param performed
	 *            the performed to set
	 */
	public void setPerformed(boolean performed) {
		this.performed = performed;
	}
}
