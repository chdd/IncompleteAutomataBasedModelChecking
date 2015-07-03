package it.polimi.constraints.reachability;

import it.polimi.constraints.transitions.ColoredPluggingTransition;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

/**
 * contains the reachability relation. The reachability relation specified
 * whether an incoming port of a sub-property is reachable from the outgoing
 * port of the same transition
 * 
 * @author Claudio Menghi
 *
 */
public class ReachabilityRelation {

	/**
	 * is the map that specifies for each incoming transition the corresponding
	 * outgoing transition
	 */
	private final SetMultimap<ColoredPluggingTransition, ColoredPluggingTransition> map;

	private final Map<Map.Entry<ColoredPluggingTransition, ColoredPluggingTransition>, Boolean> acceptingMap;
	private final Map<Map.Entry<ColoredPluggingTransition, ColoredPluggingTransition>, Boolean> claimMap;
	private final Map<Map.Entry<ColoredPluggingTransition, ColoredPluggingTransition>, Boolean> modelMap;
	/**
	 * creates a new empty reachability relation. The reachability relation is
	 * used to map the incoming transitions which are reachable from the
	 * outgoing transition of the same component
	 */
	public ReachabilityRelation() {
		this.map = HashMultimap.create();
		this.acceptingMap = new HashMap<Map.Entry<ColoredPluggingTransition, ColoredPluggingTransition>, Boolean>();
		this.claimMap = new HashMap<Map.Entry<ColoredPluggingTransition, ColoredPluggingTransition>, Boolean>();
		this.modelMap = new HashMap<Map.Entry<ColoredPluggingTransition, ColoredPluggingTransition>, Boolean>();
	}

	/**
	 * adds the transition to the reachability relation. The sourceTransition is
	 * the outgoingPort of the sub-property from which the destinationTransition
	 * can be reached. The destinationTransition is the incoming port of the
	 * subproperty which is reachable from the sourceTransition
	 * 
	 * @param sourceTransition
	 *            is the source of the transition
	 * @param destinationTransition
	 *            is the destination of the transition
	 * @throws NullPointerException
	 *             if one of the two transitions is null
	 */
	public void addTransition(ColoredPluggingTransition sourceTransition,
			ColoredPluggingTransition destinationTransition, Boolean accepting, Boolean modelAccepting, Boolean claimAccepting) {
		Preconditions.checkNotNull(sourceTransition,
				"The source transition cannot be null");
		Preconditions.checkNotNull(destinationTransition,
				"The destination transition cannot be null");

		this.map.put(sourceTransition, destinationTransition);
		this.acceptingMap
				.put(new AbstractMap.SimpleEntry<ColoredPluggingTransition, ColoredPluggingTransition>(
						sourceTransition, destinationTransition), accepting);
		this.modelMap.put(new AbstractMap.SimpleEntry<ColoredPluggingTransition, ColoredPluggingTransition>(
				sourceTransition, destinationTransition), modelAccepting);
		this.claimMap.put(new AbstractMap.SimpleEntry<ColoredPluggingTransition, ColoredPluggingTransition>(
				sourceTransition, destinationTransition), claimAccepting);
	}

	/**
	 * returns a multimap which specifies for each transition its successors
	 * 
	 * @return a multimap which specifies for each transition its successors
	 */
	public SetMultimap<ColoredPluggingTransition, ColoredPluggingTransition> getMap() {
		return this.map;
	}
	public Map<Map.Entry<ColoredPluggingTransition, ColoredPluggingTransition>, Boolean> getReachabilityAcceptingMap(){
		return this.acceptingMap;
	}
}
