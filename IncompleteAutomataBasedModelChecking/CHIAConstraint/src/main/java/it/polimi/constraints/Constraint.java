package it.polimi.constraints;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.state.State;
import it.polimi.constraints.components.SubProperty;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * The Constraint class describes the constraint associated with the
 * satisfaction of a claim on a model. The constraint contains the set of
 * sub-properties associated with the black box states of the model, a map
 * that contains for each sub-property how they are connected with the original
 * model (i.e., the in-coming and out-coming) transitions of the sub-property,
 * the color of this ports and a graph that describes how the sub-property are
 * related, i.e., how the ports of the sub-properties are connected.
 * 
 * @author claudiomenghi
 *
 */
public class Constraint {

	/**
	 * is the set sub-property to be considered in the refinement of the
	 * black box state
	 */
	private final Set<SubProperty> subProperties;

	/**
	 * is a map that specifies for each black box state the corresponding
	 * sub-property and viceversa
	 */
	private final BiMap<State, SubProperty> stateSubPropertyMap;

	/**
	 * creates a new empty constraint
	 * 
	 * @param subProperty
	 *            is the sub-property to be associated to the constraint
	 */
	public Constraint() {
		this.subProperties = new HashSet<SubProperty>();
		this.stateSubPropertyMap = HashBiMap.create();
	}

	/**
	 * returns the sub-property associated with the constraint
	 * 
	 * @return the sub-property associated with the constraint
	 */
	public Set<SubProperty> getSubProperties() {
		return Collections.unmodifiableSet(subProperties);
	}

	/**
	 * returns the sub-property associated with the specified state
	 * 
	 * @param state
	 *            the state of the model to which the sub-property is associated
	 * @return the sub-property associated with the specified state of the model
	 * @throws NullPointerException
	 *             if the state is null
	 * @throws IllegalArgumentException
	 *             if the state is not associated with any sub-property
	 */
	public SubProperty getSubProperty(State state) {
		Preconditions.checkNotNull(state,
				"The state to be considered cannot be null");
		Preconditions.checkArgument(
				this.stateSubPropertyMap.containsKey(state),
				"No sub-property associated with the state: " + state);
		return this.stateSubPropertyMap.get(state);
	}

	/**
	 * adds the sub-property to the set of the sub-properties of the constraint
	 * 
	 * @param subProperty
	 *            the sub-property to be added to the set of sub-properties
	 * @throws NullPointerException
	 *             if the sub-property is null
	 * @throws IllegalArgumentException
	 *             if the sub-property constraints an already constrained state
	 */
	public void addSubProperty(SubProperty subProperty) {
		Preconditions.checkNotNull(subProperty,
				"The sub-Property to be considered cannot be null");
		Preconditions.checkArgument(!this.stateSubPropertyMap
				.containsKey(subProperty.getModelState()),
				"A sub-property is already associated with the black box state"
						+ subProperty.getModelState());
		this.subProperties.add(subProperty);
		this.stateSubPropertyMap.put(subProperty.getModelState(), subProperty);
	}

	/**
	 * returns the set of the states of the model constrained by the constraint
	 * 
	 * @return the set of the states of the model constrained by the constraint
	 */
	public Set<State> getConstrainedStates() {
		return this.stateSubPropertyMap.keySet();
	}
}
