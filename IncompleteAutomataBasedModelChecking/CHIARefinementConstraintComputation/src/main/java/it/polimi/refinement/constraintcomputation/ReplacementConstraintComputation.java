package it.polimi.refinement.constraintcomputation;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.checker.ibatransparentstateremoval.IBATransparentStateRemoval;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraints.Component;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Port;
import it.polimi.constraints.Replacement;
import it.polimi.constraints.SubProperty;
import it.polimi.contraintcomputation.ConstraintGenerator;
import it.polimi.refinement.constraintcomputation.colorpropagator.SubpropertyPortColorPropagator;
import it.polimi.refinement.constraintcomputation.merger.Merger;
import it.polimi.refinement.constraintcomputation.merger.UnreachablePortsIdentifier;
import it.polimi.refinement.constraintcomputation.merger.UnreachablePortsRemover;
import it.polimi.refinement.constraintcomputation.portsIdentifier.IncomingPortsIdentifier;
import it.polimi.refinement.constraintcomputation.portsIdentifier.OutComingPortsIdentifier;
import it.polimi.refinementchecker.ReplacementChecker;

import java.util.Set;

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
public class ReplacementConstraintComputation {

	/**
	 * is the logger of the ModelChecker class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ReplacementConstraintComputation.class);

	/**
	 * contains the constraint to be updated
	 */
	private final Constraint constraint;

	/**
	 * contains the replacement to be verified
	 */
	private final Replacement replacement;

	/**
	 * contains the new constraint generated
	 */
	private Constraint newConstraint;

	/**
	 * contains the checker which is used to check the replacement
	 */
	private final ReplacementChecker replacementChecker;

	/**
	 * is the identifiers use to compute the incoming ports
	 */
	private IncomingPortsIdentifier incomingPortIdentifier;

	/**
	 * is the identifier used to compute the out-coming ports
	 */
	private OutComingPortsIdentifier outcomingPortIdentifier;

	/**
	 * creates a new Refinement Checker. The refinement checker is used to check
	 * the refinement of a transparent state. The refinement checker updates the
	 * constraint associated with the transparent state and the constraints
	 * associated with the other transparent states.
	 * 
	 * @param replacementChecker
	 *            the replacement checker used to check the replacement against
	 *            the constraint
	 * @throws NullPointerException
	 *             if the replacementChecker is null
	 */
	public ReplacementConstraintComputation(
			ReplacementChecker replacementChecker) {
		Preconditions.checkNotNull(replacementChecker,
				"The constraint to be checked cannot be null");
		this.constraint = replacementChecker.getConstraint();
		this.replacement = replacementChecker.getReplacement();
		this.replacementChecker = replacementChecker;
	}

	/**
	 * returns the new constraint which have been computed
	 * 
	 * @return the new constraint which have been computed
	 */
	public Constraint newConstraint() {
		return this.newConstraint;
	}

	/**
	 * compute the constraint associated with the given constraint and the
	 * replacement
	 */
	public void constraintComputation() {
		// GETTING THE CLAIM
		// gets the sub-property associated with the model state, i.e., the
		// claim automaton
		SubProperty subproperty = this.constraint
				.getSubproperty(this.replacement.getModelState());
		// sets the initial and accepting states depending on the incoming and
		// out-coming transitions
		BA claim = subproperty.getAutomaton();

		// GETTING THE MODEL
		// gets the model to be considered, i.e., the model of the refinement
		// where the transparent states have been removed
		IBA model = this.replacement.getAutomaton();

		this.updatedPathsWithNoTransparentStates(subproperty, claim, model);

		this.newConstraint=this.updatedPathsWithTransparentStates(subproperty, claim, model);

	}

	/**
	 * updates the paths which do not involve any transparent state of the
	 * refinement. Indeed, when the refinement contains transparent states the
	 * paths that do not include any transparent state are analyzed. These paths
	 * do not generate any constraint
	 * 
	 * @param subproperty
	 *            is the sub-property to be considered
	 * @param claim
	 *            is the BA which corresponds to the claim of the sub-property
	 * @param model
	 *            is the model that constraints the state specified by the
	 *            sub-property
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	private void updatedPathsWithNoTransparentStates(SubProperty subproperty,
			BA claim, IBA model) {
		Preconditions.checkNotNull(subproperty,
				"The sub-property to be checked cannot be null");
		Preconditions.checkNotNull(claim,
				"The claim to be checked cannot be null");
		Preconditions.checkNotNull(model,
				"The model to be checked cannot be null");
		logger.info("updatePaths with no Transparent states");

		IBA modelWithoutTransparentStates = new IBATransparentStateRemoval()
				.removeTransparentStates(model.clone());

		// computes the intersection between the claim and the model
		IntersectionBuilder intersectionBuilder = new IntersectionBuilder(
				modelWithoutTransparentStates, claim);
		IntersectionBA intersectionBA = intersectionBuilder
				.computeIntersection();

		// computes the incoming ports of the intersection automaton
		incomingPortIdentifier = new IncomingPortsIdentifier(replacement,
				subproperty, intersectionBuilder);
		Set<Port> incomingPorts = incomingPortIdentifier
				.computeIntersectionIncomingPorts();
		outcomingPortIdentifier = new OutComingPortsIdentifier(replacement,
				subproperty, intersectionBuilder);
		Set<Port> outcomingPorts = outcomingPortIdentifier
				.computeIntersectionOutcomingPorts();

		SubpropertyPortColorPropagator portColorPropagator = new SubpropertyPortColorPropagator(
				intersectionBA, incomingPorts, outcomingPorts);
		portColorPropagator.updateComponentPorts();

		logger.info("paths with no transparent states updated");

	}

	/**
	 * updates the paths which the transparent states of the refinement. Indeed,
	 * when the refinement contains transparent states the paths that do not
	 * include any transparent state are analyzed. These paths do not generate
	 * any constraint
	 */
	private Constraint updatedPathsWithTransparentStates(Component subproperty,
			BA claim, IBA model) {
		Preconditions.checkNotNull(subproperty,
				"The sub-property to be considered cannot be null");
		Preconditions
				.checkNotNull(claim,
						"The ba which represents the claim to be considered cannot be null");
		Preconditions
				.checkNotNull(model,
						"The iba which represents the model to be considered cannot be null");

		// generate the constraint
		ConstraintGenerator cg = new ConstraintGenerator(
				this.replacementChecker.getChecker());
		Constraint subConstraint = cg.generateConstraint();
		logger.debug("Generated a new constraint with: "+subConstraint.getSubProperties().size()+" sub-properties");

		// identifies the unreachable ports
		UnreachablePortsIdentifier uid = new UnreachablePortsIdentifier(
				this.replacementChecker.getChecker().getIntersectionAutomaton(),
				this.incomingPortIdentifier.computeIntersectionIncomingPorts(),
				this.outcomingPortIdentifier
						.computeIntersectionOutcomingPorts());
		Set<Port> unreachableInPorts = uid.getUnreachableInPorts();
		Set<Port> unreachableOutPorts = uid.getUnreachableOutPorts();

		// removes the unreachable ports
		UnreachablePortsRemover remover = new UnreachablePortsRemover(
				unreachableInPorts, unreachableOutPorts, subConstraint);
		remover.remove();

		// merges the constraint and the sub-property
		Merger merger = new Merger(
				this.constraint,
				subConstraint,
				this.replacementChecker.getChecker().getIntersectionAutomaton(),
				this.incomingPortIdentifier, this.outcomingPortIdentifier);
		Constraint c = merger.merge(subproperty.getModelState());

		return c;
	}
}
