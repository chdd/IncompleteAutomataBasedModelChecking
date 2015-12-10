package it.polimi.constraints.components;

import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.transitions.PluggingTransition;
import action.CHIAAction;

import com.google.common.base.Preconditions;

/**
 * Checks if the replacement is a valid replacement for a state of the specified
 * model
 * 
 * @author Claudio Menghi
 *
 */
public class RefinementGenerator extends CHIAAction<IBA> {

	/**
	 * the model to be considered
	 */
	private final IBA model;
	/**
	 * the replacement to be analyzed
	 */
	private final Replacement replacement;

	/**
	 * creates a new refinement checker for the specified model and replacement
	 * 
	 * @param model
	 *            the model to be considered
	 * @param replacement
	 *            the replacement to be analyzed
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public RefinementGenerator(IBA model, Replacement replacement) {
		super("Refinement Generator");
		Preconditions.checkNotNull(model,
				"the model to be considered cannot be null");
		Preconditions.checkNotNull(replacement,
				"The replacement to be considered cannot be null");
		this.model = model;
		this.replacement = replacement;

	}

	/**
	 * checks is the replacement is a valid replacement for one of the black box
	 * states of the model
	 * 
	 * @throws Exception
	 *             if the replacement is not a valid replacement for the model
	 */
	public void checkValidReplacement() throws Exception {
		if (!model.getBlackBoxStates().contains(replacement.getModelState())) {
			throw new Exception(
					"The state of the replacement must be a black box state of the original model");
		}
		if (!model.getAcceptStates().contains(replacement.getModelState())
				&& !replacement.getAutomaton().getAcceptStates().isEmpty()) {
			throw new Exception(
					"The automaton that refines a black box state wich is not accepting cannot contain accepting states");
		}
		if (!model.getInitialStates().contains(replacement.getModelState())
				&& !replacement.getAutomaton().getInitialStates().isEmpty()) {
			throw new Exception(
					"The automaton that refines a black box state wich is not accepting cannot contain initial states");
		}
		this.checkInTransitions();
		this.checkOutTransitions();

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public IBA perform() throws Exception {
		this.checkValidReplacement();
		IBA refinement = model.clone();

		refinement
				.addPropositions(replacement.getAutomaton().getPropositions());

		refinement.removeState(replacement.getModelState());
		for (State replacementState : replacement.getAutomaton().getStates()) {
			refinement.addState(replacementState);
			if (replacement.getAutomaton().getAcceptStates()
					.contains(replacementState)) {
				refinement.addAcceptState(replacementState);
			}
			if (replacement.getAutomaton().getInitialStates()
					.contains(replacementState)) {
				refinement.addInitialState(replacementState);
			}
			if (replacement.getAutomaton().getBlackBoxStates()
					.contains(replacementState)) {
				refinement.addBlackBoxState(replacementState);
			}
		}
		for (Transition transition : replacement.getAutomaton()
				.getTransitions()) {

			Transition transitionClone = new ModelTransitionFactory().create(
					transition.getId(), transition.getPropositions());
			refinement.addTransition(replacement.getAutomaton()
					.getTransitionSource(transition), replacement
					.getAutomaton().getTransitionDestination(transition),
					transitionClone);
		}

		for (PluggingTransition inTransition : replacement
				.getIncomingTransitions()) {
			Transition transitionClone = new ModelTransitionFactory().create(
					inTransition.getId(), inTransition.getTransition()
							.getPropositions());

			refinement.addTransition(inTransition.getSource(),
					inTransition.getDestination(), transitionClone);
		}
		for (PluggingTransition outTransition : replacement
				.getOutgoingTransitions()) {
			Transition transitionClone = new ModelTransitionFactory().create(
					outTransition.getId(), outTransition.getTransition()
							.getPropositions());

			refinement.addTransition(outTransition.getSource(),
					outTransition.getDestination(), transitionClone);
		}

		return refinement;
	}

	/**
	 * checks the correctness of the incoming transitions
	 * 
	 * @throws Exception
	 */
	private void checkInTransitions() throws Exception {
		for (Transition incomingTransition : model.getInTransitions(replacement
				.getModelState())) {
			boolean founded = false;
			for (PluggingTransition inTransition : replacement
					.getIncomingTransitions()) {
				if (inTransition.getTransition().equals(incomingTransition)
						&& inTransition.getSource().equals(
								model.getTransitionSource(incomingTransition))) {
					founded = true;
				}
			}
			if (!founded) {
				throw new Exception(
						"The incoming transition <"
								+ incomingTransition
								+ "> of the model is not associated with an incoming transition of the replacement");
			}
		}
		for (PluggingTransition inTransition : replacement
				.getIncomingTransitions()) {
			boolean founded = false;
			for (Transition incomingTransition : model
					.getInTransitions(replacement.getModelState())) {

				if (inTransition.getTransition().equals(incomingTransition)
						&& inTransition.getSource().equals(
								model.getTransitionSource(incomingTransition))) {
					founded = true;
				}
			}
			if (!founded) {
				throw new Exception(
						"The incoming transition <"
								+ inTransition
								+ "> of the replacement is not associated with an incoming transition of the model");
			}
		}
	}

	/**
	 * checks the correctness of the incoming transitions
	 * 
	 * @throws Exception
	 */
	private void checkOutTransitions() throws Exception {
		for (Transition outgoingTransition : model
				.getOutTransitions(replacement.getModelState())) {
			boolean founded = false;
			for (PluggingTransition outTransition : replacement
					.getOutgoingTransitions()) {
				if (outTransition.getTransition().equals(outgoingTransition)
						&& outTransition
								.getDestination()
								.equals(model
										.getTransitionDestination(outgoingTransition))) {
					founded = true;
				}
			}
			if (!founded) {
				throw new Exception(
						"The outgoing transition <"
								+ outgoingTransition
								+ "> of the model is not associated with an outgoing transition of the replacement");
			}
		}
		for (PluggingTransition outTransition : replacement
				.getOutgoingTransitions()) {
			boolean founded = false;
			for (Transition outgointTransition : model
					.getOutTransitions(replacement.getModelState())) {

				if (outTransition.getTransition().equals(outgointTransition)
						&& outTransition
								.getDestination()
								.equals(model
										.getTransitionDestination(outgointTransition))) {
					founded = true;
				}
			}
			if (!founded) {
				throw new Exception(
						"The outgoing transition <"
								+ outTransition
								+ "> of the replacement is not associated with an incoming transition of the model");
			}
		}
	}

}
