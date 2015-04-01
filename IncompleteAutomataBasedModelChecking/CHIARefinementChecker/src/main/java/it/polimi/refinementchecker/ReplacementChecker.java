package it.polimi.refinementchecker;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.Checker;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.ibatransparentstateremoval.IBATransparentStateRemoval;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.checker.intersection.IntersectionRule;
import it.polimi.constraints.Color;
import it.polimi.constraints.Component;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Port;
import it.polimi.constraints.Replacement;
import it.polimi.constraints.SubProperty;
import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertiesIdentifier;
import it.polimi.refinementchecker.decorator.AutomatonDecorator;
import it.polimi.refinementchecker.portscomputation.UpdateSubPropertiesInternalReachability;
import it.polimi.refinementchecker.portsreachability.RemoveUnreachablePorts;
import it.polimi.refinementchecker.refinementChecker.IncomingPortsIdentifier;
import it.polimi.refinementchecker.refinementChecker.OutComingPortsIdentifier;

import java.util.HashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * Is used to check the refinement of a transparent state. The refinement
 * checker updates the constraint associated with the transparent state and the
 * constraints associated with the other transparent states.<br>
 * 
 * 
 * @author claudiomenghi
 */
public class RefinementChecker {

	/**
	 * is the logger of the ModelChecker class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ReplacementChecker.class);

	/**
	 * contains the constraint to be updated
	 */
	private final Constraint constraint;

	/**
	 * contains the refinement to be verified
	 */
	private final Replacement refinement;

	/**
	 * contains the new constraint generated
	 */
	private Constraint newConstraint;

	/**
	 * creates a new Refinement Checker. The refinement checker is used to check
	 * the refinement of a transparent state. The refinement checker updates the
	 * constraint associated with the transparent state and the constraints
	 * associated with the other transparent states.
	 * 
	 * @param constraint
	 *            is the constraint that must be considered by the
	 *            RefinementChecker
	 * @param component
	 *            is the replacement to be considered by the refinement checker
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public RefinementChecker(Constraint constraint, Replacement replacement) {
		Preconditions.checkNotNull(constraint,
				"The constraint to be checked cannot be null");
		Preconditions.checkNotNull(replacement,
				"The constraint to be checked cannot be null");
		Preconditions
				.checkArgument(
						constraint.getConstrainedStates().contains(
								replacement.getModelState()),
						"The state constrained in the refinement must be contained into the set of the states of the constraint");
		this.constraint = constraint;
		this.refinement = replacement;
	}

	public Constraint newConstraint() {
		return this.newConstraint;
	}

	/**
	 * returns the updated constraint
	 * 
	 * @return the updated constraint
	 */
	public int check() {

		logger.debug("Starting the refinement checking phase");

		logger.debug("Updating the paths with no transparent states");

		/*
		 * COMPUTING THE INTERSECTION BETWEEN THE CLAIM: the automaton
		 * associated with the SUBPROPERTY and the model: the automaton which
		 * corresponds with the REFINEMENT
		 */

		// GETTING THE CLAIM
		// gets the sub-property associated with the model state, i.e., the
		// claim automaton
		SubProperty subproperty = this.constraint
				.getSubproperties(this.refinement.getModelState());
		// sets the initial and accepting states depending on the incoming and
		// out-coming transitions
		BA claim = subproperty.getAutomaton();
		
		// GETTING THE MODEL
		// gets the model to be considered, i.e., the model of the refinement
		// where the transparent states have been removed
		IBA model = this.refinement.getAutomaton();

		Checker checker=new Checker(model, claim, new ModelCheckingResults(true, true, true));
		// checking if there exists a path that does not satisfy the property
		int resChecking=checker.check();
		if(resChecking==0){
			// if there exists a path that does not satisfy the property the value 0 is returned
			return 0;
		}
		
		new AutomatonDecorator(claim).decorate(subproperty.getIncomingPorts(),
				subproperty.getOutcomingPorts());
		claim.addStuttering();

		// sets the initial and accepting states of the refinement automaton in
		// correspondance with the incoming and out=coming transitions
		new AutomatonDecorator(refinement.getAutomaton())
				.decorate(refinement.getIncomingPorts(),
						this.refinement.getOutcomingPorts());
		model.addStuttering();

		/*
		 * updates the paths which do not involve any transparent state of the
		 * refinement. Indeed, when the refinement contains transparent states
		 * the paths that do not include any transparent state are analyzed.
		 * These paths do not generate any constraint
		 */
		int res = this.updatedPathsWithNoTransparentStates(subproperty, claim,
				model);

		if (res == 0) {
			return res;
		}
		logger.debug("Updating the paths with transparent states");
		newConstraint = this.updatedPathsWithTransparentStates(subproperty,
				claim, model);

		logger.debug("Refinement checking phase ended");
		if (this.constraint.getPortsGraph().vertexSet().isEmpty()) {
			return 1;
		}
		return -1;

	}

	/**
	 * updates the paths which do not involve any transparent state of the
	 * refinement. Indeed, when the refinement contains transparent states the
	 * paths that do not include any transparent state are analyzed. These paths
	 * do not generate any constraint
	 */
	private int updatedPathsWithNoTransparentStates(Component subproperty,
			BA claim, IBA model) {
		Preconditions.checkNotNull(subproperty,
				"The sub-property to be checked cannot be null");
		Preconditions.checkNotNull(claim,
				"The claim to be checked cannot be null");
		Preconditions.checkNotNull(model,
				"The model to be checked cannot be null");

		IBA modelWithoutTransparentStates = new IBATransparentStateRemoval()
				.removeTransparentStates(model.clone());

		// computes the intersection between the claim and the model
		IntersectionBuilder intersectionBuilder = new IntersectionBuilder(modelWithoutTransparentStates, claim);
		IntersectionBA intersectionBA = intersectionBuilder
				.computeIntersection();

		// computes the incoming ports of the intersection automaton
		IncomingPortsIdentifier incomingPortIdentifier = new IncomingPortsIdentifier<S, T>(
				constraint, refinement, subproperty, intersectionBuilder);
		incomingPortIdentifier.computeIntersectionIncomingPorts();
		OutComingPortsIdentifier<S, T> outcomingPortIdentifier = new OutComingPortsIdentifier<S, T>(
				constraint, refinement, subproperty, intersectionBuilder);
		outcomingPortIdentifier.computeIntersectionOutcomingPorts();

		IntersectionPortColorUpdater<S, T> intersectionUpdated = new IntersectionPortColorUpdater<S, T>();
		// updates the color of the ports of the intersection automaton
		int res = intersectionUpdated.updateComponentPorts(intersectionBA,
				incomingPortIdentifier, outcomingPortIdentifier,
				this.constraint);

		// if the result is 0 it means that a red port has touched a green port,
		// which means that the property is not satisfied
		if (res == 0) {
			return res;
		}

		PortSubPropertiesReachabilityChecking<S, T> portReachability = new PortSubPropertiesReachabilityChecking<S, T>(
				intersectionBA, incomingPortIdentifier
						.getIntersectionIncomingPorts().keySet(),
				outcomingPortIdentifier.getIntersectionOutcomingPorts()
						.keySet(), new HashSet<Port<S, T>>(),
				new HashSet<Port<S, T>>());
		portReachability.computeTransitionsClosure(intersectionBA.getStates());

		// updates the component that surrounds the sub-property with respect
		// with the new reachability relation and the new colors.
		new ComponentPortRelationProcessor<S, T, BA<S, T>>()
				.updateNoTransparentPortRelation(subproperty, constraint);
		return 1;
	}

	/**
	 * updates the paths which the transparent states of the refinement. Indeed,
	 * when the refinement contains transparent states the paths that do not
	 * include any transparent state are analyzed. These paths do not generate
	 * any constraint
	 */
	private Constraint<S, T, BA<S, T>> updatedPathsWithTransparentStates(
			Component<S, T, BA<S, T>> subproperty, BA<S, T> claim,
			IBA<S, T> model) {
		Preconditions.checkNotNull(subproperty,
				"The sub-property to be considered cannot be null");
		Preconditions
				.checkNotNull(claim,
						"The ba which represents the claim to be considered cannot be null");
		Preconditions
				.checkNotNull(model,
						"The iba which represents the model to be considered cannot be null");

		// computes the intersection between the automaton that corresponds with
		// the sub-property and the one that corresponds with the refinement
		IntersectionBuilder<S, T> intersectionBuilder = new IntersectionBuilder<S, T>(
				this.intersectionRule, model, claim);
		IntersectionBA<S, T> intersection = intersectionBuilder
				.computeIntersection();

		/*
		 * removes from the intersection automaton the states from which it is
		 * not possible to reach an accepting state since these states are not
		 * useful in the constraint computation
		 */
		IntersectionCleaner<S, T> intersectionCleaner = new IntersectionCleaner<S, T>(
				intersection, intersectionBuilder);
		intersectionCleaner.clean();

		/*
		 * computes the incoming and out-coming port with respect to the the
		 * intersection automaton (i.e., it does consider only the incoming and
		 * out-coming ports of the refinement)
		 */
		IncomingPortsIdentifier<S, T> intersectionIncomingPortIdentifier = new IncomingPortsIdentifier<S, T>(
				constraint, refinement, subproperty, intersectionBuilder);
		Map<Port<S, T>, Color> intersectionIncomingPorts = intersectionIncomingPortIdentifier
				.computeIntersectionIncomingPorts();
		OutComingPortsIdentifier<S, T> intersectionOutcomingPortIdentifier = new OutComingPortsIdentifier<S, T>(
				constraint, refinement, subproperty, intersectionBuilder);
		Map<Port<S, T>, Color> intersectionOutcomingPorts = intersectionOutcomingPortIdentifier
				.computeIntersectionOutcomingPorts();

		/*
		 * Given the specific set of incoming transitions identifies the new
		 * sub-properties
		 */
		SubPropertiesIdentifier<S, T, BA<S, T>> subPropertiesIdentifier = new SubPropertiesIdentifier<S, T, BA<S, T>>(
				intersectionBuilder, new BAComponentFactory<S, T>());
		Constraint<S, T, BA<S, T>> newSubProperties = subPropertiesIdentifier
				.getSubProperties(intersectionIncomingPorts,
						intersectionOutcomingPorts);

		/*
		 * COMPUTES THE INTERNAL REACHABILITY computes the relation between the
		 * incoming port of the sub-property to be verified and the in-coming
		 * transitions of the sub-properties generated by the sub-properties
		 * identifier
		 */
		UpdateSubPropertiesInternalReachability<S, T> oldSubPropertyNewSubPropertyPortRelation = new UpdateSubPropertiesInternalReachability<S, T>();
		oldSubPropertyNewSubPropertyPortRelation.computeRelations(intersection,
				newSubProperties, intersectionIncomingPorts,
				intersectionIncomingPortIdentifier, intersectionOutcomingPorts,
				intersectionOutcomingPortIdentifier);

		constraint.replace(subproperty,
				(ConstraintImpl<S, T, BA<S, T>>) newSubProperties,
				oldSubPropertyNewSubPropertyPortRelation
						.getMapOldPropertyNewConstraintIncomingPorts(),
				oldSubPropertyNewSubPropertyPortRelation
						.getMapOldPropertyNewConstraintOutcomingPorts(),
				intersectionIncomingPortIdentifier
						.getIntersectionPortClaimPortMap(),
				intersectionOutcomingPortIdentifier
						.getIntersectionPortClaimMap());

		/*
		 * removes the transitions associated with the transparent state, i.e.,
		 * the internal transition of each component
		 */
		RemoveUnreachablePorts<S, T> unreachablePortsRemover = new RemoveUnreachablePorts<S, T>(
				constraint);
		unreachablePortsRemover.removeNotUsefulPorts();

		return constraint;

	}

}
