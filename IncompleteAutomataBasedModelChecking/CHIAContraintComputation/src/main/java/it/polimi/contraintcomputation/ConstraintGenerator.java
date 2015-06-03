package it.polimi.contraintcomputation;

import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.SubProperty;
import it.polimi.contraintcomputation.portreachability.ReachabilityIdentifier;
import it.polimi.contraintcomputation.subpropertyidentifier.IntersectionCleaner;
import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertyIdentifier;
import it.polimi.contraintcomputation.subpropertyidentifier.coloring.Coloring;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import action.CHIAAction;

import com.google.common.base.Preconditions;

/**
 * The constraint generator computes a constraint. A constraint specifies the
 * behavior the replacement of the transparent state must satisfy
 * 
 * @author claudiomenghi
 * 
 */
public class ConstraintGenerator extends CHIAAction {

	private final static String NAME = "CONSTRAINT GENERATION";

	/**
	 * is the logger of the ConstraintGenerator class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ConstraintGenerator.class);

	private final Checker checker;

	private final Constraint constraint;

	/**
	 * is a map that contains for each subproperty the subpropertyIdentifier
	 * using to compute the sub-property
	 */
	private final Map<SubProperty, SubPropertyIdentifier> subpropetySubPropertyIdentifierMap;

	/**
	 * creates a new ConstraintGenerator object which starting from the
	 * intersection automaton and the map between the states of the model and
	 * the corresponding states of the intersection automaton computes the
	 * constraints
	 * 
	 * @param checker
	 *            is the model checker
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 * @throws IllegalStateException
	 *             if the checker has not been executed before the constraint
	 *             generation
	 */
	public ConstraintGenerator(Checker checker) {
		super(NAME);
		Preconditions.checkNotNull(checker, "The model checker cannot be null");
		Preconditions
				.checkState(
						checker.check() == SatisfactionValue.POSSIBLYSATISFIED,
						"You can perform the constraint generation iff the claim is possibly satisfied");

		Preconditions.checkNotNull(checker,
				"The intersection builder cannot be null");
		this.checker = checker;
		this.constraint = new Constraint();
		this.subpropetySubPropertyIdentifierMap = new HashMap<SubProperty, SubPropertyIdentifier>();
	}

	/**
	 * returns the constraint of the automaton
	 * 
	 * @return the constraint of the automaton
	 */
	public Constraint generateConstraint() {

		logger.info("Computing the constraint");
		/*
		 * removes from the intersection automaton the states from which it is
		 * not possible to reach an accepting state since these states are not
		 * useful in the constraint computation
		 */
		IntersectionCleaner intersectionCleaner = new IntersectionCleaner(
				this.checker.getUpperIntersectionBuilder());
		intersectionCleaner.clean();

		for (State transparentState : this.checker.getUpperIntersectionBuilder()
				.getModel().getTransparentStates()) {

			constraint.addSubProperty(this
					.generateSubProperty(transparentState));

		}

		logger.info("Constraint computed");
		return constraint;

	}

	/**
	 * returns the sub-property associated with the specific transparent state
	 * 
	 * @param transparentState
	 *            the transparent state to be considered
	 * @return the sub-property associated with the transparent state
	 * @throws NullPointerException
	 *             if the transparent state is null
	 */
	private SubProperty generateSubProperty(State transparentState) {
		Preconditions.checkNotNull(transparentState,
				"The transparent state to be considered cannot be null");
		/*
		 * extract the sub-properties from the intersection automaton. It
		 * identifies the portions of the state space (the set of the mixed
		 * states and the transitions between them) that refer to the same
		 * transparent states of the model. It also compute the corresponding
		 * ports, i.e., the set of the transition that connect the sub-property
		 * to the original model.
		 */
		SubPropertyIdentifier subPropertiesIdentifier = new SubPropertyIdentifier(
				this.checker, transparentState);
		SubProperty subProperty = subPropertiesIdentifier.getSubProperty();
		this.subpropetySubPropertyIdentifierMap.put(subProperty,
				subPropertiesIdentifier);
		return subProperty;
	}

	public void coloring() {

		for (java.util.Map.Entry<SubProperty, SubPropertyIdentifier> entry : this.subpropetySubPropertyIdentifierMap
				.entrySet()) {
			Coloring coloring = new Coloring(entry.getValue());
			coloring.startColoring();
		}
	}

	/**
	 * computes the reachability between the ports, i.e., it updates the
	 * reachability relation between the ports and updates the corresponding
	 * colors
	 * 
	 * @return the constraints where the color of the ports have been updated
	 */
	public Constraint computePortReachability() {
		logger.info("Udating the Port reachability relation");
		
		for(Map.Entry<SubProperty, SubPropertyIdentifier> e: subpropetySubPropertyIdentifierMap.entrySet()){
			ReachabilityIdentifier reachability = new ReachabilityIdentifier(this.checker.getUpperIntersectionBuilder(),
					e.getValue());
			reachability.computeReachability();

		}
		
		logger.info("Port reachability relation updated");
		return constraint;

	}
	public void computeIndispensable(){
		for (java.util.Map.Entry<SubProperty, SubPropertyIdentifier> entry : this.subpropetySubPropertyIdentifierMap
				.entrySet()) {
			State modelState=entry.getKey().getModelState();
			IBA model=checker.getUpperIntersectionBuilder().getModel().clone();
			model.removeState(modelState);
			SatisfactionValue value=new Checker(model, checker.getUpperIntersectionBuilder().getClaim(), checker.getUpperIntersectionBuilder().getAcceptingPolicy()).check();
			if(value==SatisfactionValue.NOTSATISFIED){
				throw new InternalError("It is not possible that removing a transparent state of the model makes the property not satisfied");
			}
			if(value==SatisfactionValue.POSSIBLYSATISFIED){
				entry.getKey().setIndispensable(false);
			}
			if(value==SatisfactionValue.SATISFIED){
				entry.getKey().setIndispensable(true);
			}
		}
		
	}
}
