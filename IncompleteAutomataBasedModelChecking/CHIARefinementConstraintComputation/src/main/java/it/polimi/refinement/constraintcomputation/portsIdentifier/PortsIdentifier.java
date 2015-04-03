package it.polimi.refinement.constraintcomputation.portsIdentifier;

import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraints.Port;
import it.polimi.constraints.Replacement;
import it.polimi.constraints.SubProperty;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * 
 * Is the abstract class from which the Incoming and Outcoming PortsIdentifier
 * inherits. It contains the instantiation logic and the common attributes of
 * these classes
 * 
 * @author claudiomenghi
 *
 */
public abstract class PortsIdentifier {

	/**
	 * is the replacement to be considered
	 */
	protected final Replacement refinement;

	/**
	 * is the sub-property to be considered to be considered
	 */
	protected final SubProperty subproperty;

	/**
	 * contains the IntersectionBuilder which has been used to create the
	 * intersection between the BA corresponding to the sub-property and the IBA
	 * corresponding to the refinement
	 */
	protected final IntersectionBuilder intersectionBuilder;

	/**
	 * contains a map that keeps tracks of the relation between an intersection
	 * port and the corresponding claim port
	 */
	protected final Map<Port, Port> intersectionPortClaimPortMap = new HashMap<Port, Port>();
	protected final Map<Port, Set<Port>> claimPortIntersectionPortMap = new HashMap<Port, Set<Port>>();

	/**
	 * contains the set of the intersection port
	 */
	protected final Set<Port> ports;

	/**
	 * creates a new PortsIdentifier. The PortsIdentifier given the sub-property
	 * and the corresponding replacement computes the incoming ports/out-coming
	 * ports of the intersection automaton
	 * 
	 * @param refinement
	 *            is the replacement to be considered
	 * @param subproperty
	 *            is the sub-property to be considered
	 * @param intersectionBuilder
	 *            is the intersectionBuilder which has been used to create the
	 *            intersection between the claim corresponding with the
	 *            sub-property and the model corresponding with the replacement
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public PortsIdentifier(Replacement refinement, SubProperty subproperty,
			IntersectionBuilder intersectionBuilder) {
		Preconditions.checkNotNull(refinement, "The refinement cannot be null");
		Preconditions.checkNotNull(subproperty,
				"The subproperty cannot be null");
		Preconditions.checkNotNull(intersectionBuilder,
				"The intersection Builder cannot be null");

		this.refinement = refinement;
		this.subproperty = subproperty;
		this.intersectionBuilder = intersectionBuilder;
		this.ports = new HashSet<Port>();
	}

	/**
	 * gets the claim port that corresponds to the specific intersection port
	 * 
	 * @param intersectionPort
	 *            is the intersection port that is of interest
	 * @return the claim port that corresponds to the specific intersection port
	 * @throws NullPointerException
	 *             if the intersection port is null
	 */
	public Port getCorrespondingClaimPort(Port intersectionPort) {
		Preconditions.checkNotNull(intersectionPort,
				"The intersection port cannot be null");
		Preconditions
				.checkArgument(this.intersectionPortClaimPortMap
						.containsKey(intersectionPort),
						"The intersection Port " + intersectionPort
								+ " is not releated with a claim port");

		return this.intersectionPortClaimPortMap.get(intersectionPort);
	}

	/**
	 * gets the intersection ports that corresponds to the specific claim port
	 * 
	 * @param claimPort
	 *            is the claim port that is of interest
	 * @return the set of intersection ports that corresponds to the specific
	 *         claim port
	 * @throws NullPointerException
	 *             if the claim port is null
	 */
	public Set<Port> getCorrespondingIntersectionPort(Port claimPort) {
		Preconditions.checkNotNull(claimPort,
				"The intersection port cannot be null");
		if (this.intersectionPortClaimPortMap.containsKey(claimPort)) {
			return this.claimPortIntersectionPortMap.get(claimPort);
		} else {
			return new HashSet<Port>();
		}
	}

}
