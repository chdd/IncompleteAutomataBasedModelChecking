package it.polimi.contraintcomputation;

import it.polimi.checker.Checker;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraints.Constraint;
import it.polimi.contraintcomputation.portreachability.PortReachability;
import it.polimi.contraintcomputation.subpropertyidentifier.IntersectionCleaner;
import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertiesIdentifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * The constraint generator computes a constraint. A constraint is a (set of)
 * sub-model(s) for the unspecified components is produce
 * 
 * @author claudiomenghi
 * 
 */
public class ConstraintGenerator {

	/**
	 * is the logger of the ConstraintGenerator class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ConstraintGenerator.class);



	/**
	 * is updated with the Model checking results, which stores the verification
	 * results and the time required from the different verification steps
	 */
	private final ModelCheckingResults mcResults;

	/**
	 * is the intersection Builder. The intersection Builder is used by the
	 * Constraint generation class to analyze the relation between the
	 * intersection state and the states of the model, i.e., given a specific
	 * state of the model to have the corresponding state of the intersection
	 * automaton and vice-versa
	 */
	private final IntersectionBuilder intersectionBuilder;
	private SubPropertiesIdentifier subPropertiesIdentifier;
	private final Constraint constraint;

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
		Preconditions
				.checkState(checker.getVerificationResults().getResult() == -1,
						"You can perform the constraint generation iff the claim is possibly satisfied");

		Preconditions.checkNotNull(checker,
				"The intersection builder cannot be null");
		this.intersectionBuilder = checker.getIntersectionBuilder();
		this.mcResults = checker.getVerificationResults();
		this.constraint = new Constraint();
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
				intersectionBuilder);
		intersectionCleaner.clean();

		/*
		 * extract the sub-properties from the intersection automaton. It
		 * identifies the portions of the state space (the set of the mixed
		 * states and the transitions between them) that refer to the same
		 * transparent states of the model. It also compute the corresponding
		 * ports, i.e., the set of the transition that connect the sub-property
		 * to the original model.
		 */
		subPropertiesIdentifier = new SubPropertiesIdentifier(
				intersectionBuilder, this.mcResults);
		constraint.addSubProperties(subPropertiesIdentifier.getSubProperties());
		return constraint;
		
	}
	
	public Constraint computePortReachability(){
		PortReachability reachability = new PortReachability(constraint,
				intersectionBuilder, subPropertiesIdentifier);
		reachability.computeReachability();

		logger.info("Constraint computed");
		return constraint;
		
	}
}
