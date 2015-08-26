/**
 * 
 */
package it.polimi.constraints.utils;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.transitions.PluggingTransition;
import action.CHIAAction;

import com.google.common.base.Preconditions;

/**
 * Inject the a replacement into an IBA
 * 
 * @author Claudio Menghi
 *
 */
public class Injector extends CHIAAction<IBA> {

	/**
	 * The incomplete Buchi automaton where the replacement must be injected
	 */
	private final IBA model;
	/**
	 * The replacement to be injected into the model
	 */
	private final Replacement replacement;

	/**
	 * The name of the CHIAAction
	 */
	private static final String ACTION_NAME = "INJECTION";

	/**
	 * creates a new Injector. The injector injects the replacement into the IBA
	 * 
	 * @param model
	 *            the model where the replacement must be injected
	 * @param replacement
	 *            the replacement to be injected into the model
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 * @throws IllegalArgumentException
	 *             if the replacement is not associated with a transparent state
	 *             of the model
	 */
	public Injector(IBA model, Replacement replacement) {
		super(ACTION_NAME);
		Preconditions.checkNotNull(model,
				"The model to be considered by the injector cannot be null");
		Preconditions.checkNotNull(replacement,
				"The replacement to be injected into the model cannot be null");
		Preconditions
				.checkArgument(
						model.getTransparentStates().contains(
								replacement.getModelState()),
						"The replacement must be associated with a transparent state of the model");
		State transparentState = replacement.getModelState();

		for (PluggingTransition incomingTransition : replacement
				.getIncomingTransitions()) {
			if (!model.getPredecessors(transparentState).contains(
					incomingTransition.getSource())) {
				throw new IllegalArgumentException(
						"The source of an incoming transition to be injected was not connected to the transparent state");
			}
		}

		for (PluggingTransition outgoingTransition : replacement
				.getOutgoingTransitions()) {
			if (!model.getSuccessors(transparentState).contains(
					outgoingTransition.getDestination())) {
				throw new IllegalArgumentException(
						"the destination of an out-coming transition was not connected to the transparent state");
			}
		}

		this.model = model.clone();
		this.replacement = replacement;
	}

	/**
	 * injects the replacement into the IBA
	 * 
	 * @return an IBA where the replacement has been injected into the IBA
	 * 
	 */
	@Override
	public IBA perform() throws Exception {
		IBA newIba = (IBA) this.model;

		IBA ibaToInject = replacement.getAutomaton();
		this.injectTheAutomaton(newIba, ibaToInject);
		newIba.removeState(replacement.getModelState());
		this.addIncomingTransitions(newIba);
		this.addOutgoingTransitions(newIba);
		return newIba;
	}

	/**
	 * inject the incoming transitions of the replacement into the IBA
	 * 
	 * @param newIba
	 *            is the IBA where the incoming transitions of the replacement
	 *            must be injected
	 * @throws NullPointerException
	 *             if the newIba is null
	 */
	protected void addIncomingTransitions(IBA newIBA) {
		Preconditions.checkNotNull(newIBA, "The newIBA cannot be null");
		// processing the incoming transitions
		// for each incoming transition
		for (PluggingTransition pluggingTransition : replacement
				.getIncomingTransitions()) {
			newIBA.addTransition(pluggingTransition.getSource(),
					pluggingTransition.getDestination(),
					pluggingTransition.getTransition());
		}
	}

	/**
	 * inject the outgoing transitions of the replacement into the IBA
	 * 
	 * @param newIba
	 *            is the IBA where the incoming transitions of the replacement
	 *            must be injected
	 * @throws NullPointerException
	 *             if the newIba is null
	 */
	protected void addOutgoingTransitions(IBA newIBA) {
		Preconditions.checkNotNull(newIBA, "The newIBA cannot be null");
		// processing the out-coming transitions
		for (PluggingTransition pluggingTransition : replacement
				.getOutgoingTransitions()) {
			newIBA.addTransition(pluggingTransition.getSource(),
					pluggingTransition.getDestination(),
					pluggingTransition.getTransition());
		}
	}

	/**
	 * @param newIba
	 *            the IBA where the ibaToInject must be injected
	 * @param ibaToInject
	 *            is the IBA to be injected into the newIBA
	 * @throws NullPointerException
	 *             if one of the IBA is null
	 */
	protected void injectTheAutomaton(IBA newIba, IBA ibaToInject) {
		Preconditions
				.checkNotNull(newIba,
						"The iba where the IBAToInject must be inserted cannot be null");
		Preconditions.checkNotNull(ibaToInject,
				"The iba to be injected cannot be null");

		for (IGraphProposition proposition: ibaToInject.getPropositions()){
			newIba.addProposition(proposition);
		}
		for (State s : ibaToInject.getStates()) {
			newIba.addState(s);
		}
		for (State s : ibaToInject.getTransparentStates()) {
			newIba.addTransparentState(s);
		}
		for (State s : ibaToInject.getInitialStates()) {
			newIba.addInitialState(s);
		}
		for (State s : ibaToInject.getAcceptStates()) {
			newIba.addAcceptState(s);
		}

		// copy the transition into the refinement
		for (Transition t : ibaToInject.getTransitions()) {
			newIba.addTransition(ibaToInject.getTransitionSource(t),
					ibaToInject.getTransitionDestination(t), t);
		}
	}
}
