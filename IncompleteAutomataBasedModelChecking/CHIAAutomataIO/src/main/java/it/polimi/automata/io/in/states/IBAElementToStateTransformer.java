package it.polimi.automata.io.in.states;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;

import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * transforms an object of type Element into an object of type State and
 * populates the corresponding IBA
 * 
 * @author Claudio Menghi
 *
 */
public class IBAElementToStateTransformer implements
		Transformer<Element, State> {

	/**
	 * is the factory which is used to create the states of the Buchi automaton
	 */
	protected final StateFactory stateFactory;
	/**
	 * contains the IBA which must be populated by the
	 * IBAElementToStateTransformer
	 */
	protected final IBA iba;

	/**
	 * creates a new transformer in charge of populating the IBA passed as
	 * parameter with the parsed states
	 * 
	 * @param iba
	 *            is the IBA to be populated
	 */
	public IBAElementToStateTransformer(IBA iba) {
		Preconditions.checkNotNull(iba,
				"The incomplete Buchi automaton cannot be null");

		this.stateFactory = new StateFactory();
		this.iba = iba;
	}

	/**
	 * transforms the {@link Element} into the corresponding state.
	 * 
	 * @param eElement
	 *            is the element to be converted into a state
	 * @throws NullPointerException
	 *             if the element is null
	 * @throws IllegalArgumentException
	 *             if the state does not have an id
	 * @throws IllegalArgumentException
	 *             if the state does not have a name
	 */
	@Override
	public State transform(Element eElement) {
		Preconditions.checkNotNull(eElement, "The element cannot be null");
		Preconditions.checkArgument(
				eElement.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_ID),
				"The state must have a " + AutomataIOConstants.XML_ATTRIBUTE_ID
						+ " attribute");
		Preconditions
				.checkArgument(eElement
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_NAME),
						"The state must have a "
								+ AutomataIOConstants.XML_ATTRIBUTE_NAME
								+ " attribute");

		int id = Integer.parseInt(eElement
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_ID));

		State s = stateFactory.create(
				eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_NAME),
				id);
		iba.addState(s);

		if (!eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_INITIAL)
				.isEmpty()
				&& Boolean.parseBoolean(eElement.getAttribute(
						AutomataIOConstants.XML_ATTRIBUTE_INITIAL)
						.toLowerCase())) {
			iba.addInitialState(s);
		}
		if (!eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_ACCEPTING)
				.isEmpty()
				&& Boolean.parseBoolean(eElement.getAttribute(
						AutomataIOConstants.XML_ATTRIBUTE_ACCEPTING)
						.toLowerCase())) {
			iba.addAcceptState(s);
		}
		if (!eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_BLACKBOX)
				.isEmpty()
				&& Boolean.parseBoolean(eElement.getAttribute(
						AutomataIOConstants.XML_ATTRIBUTE_BLACKBOX)
						.toLowerCase())) {
			iba.addBlackBoxState(s);
		}
		return s;
	}
}
