package it.polimi.automata.io.in.states;

import it.polimi.automata.BA;
import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;

import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * transforms an object of type Node into an object of type State and populates the corresponding BA
 * 
 * @author Claudio Menghi
 *
 */
public class BAElementToStateTransformer implements Transformer<Element, State> {

	/**
	 * is the factory which is used to create the states of the Buchi automaton
	 */
	protected final StateFactory stateFactory;

	/**
	 * The BA to be populated
	 */
	protected final BA ba;

	/**
	 * creates a new transformer in charge of populating the BA passed as
	 * parameter with the parsed states
	 * 
	 * @param ba
	 *            is the BA to be populated
	 */
	public BAElementToStateTransformer(BA ba) {
		Preconditions.checkNotNull(ba, "The ba cannot be null");

		this.stateFactory = new StateFactory();
		this.ba = ba;
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
		ba.addState(s);

		if (!eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_INITIAL)
				.isEmpty()
				&& Boolean.parseBoolean(eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_INITIAL).toLowerCase())) {
			ba.addInitialState(s);
		}
		if (!eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_ACCEPTING).isEmpty() &&
			 Boolean.parseBoolean(eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_ACCEPTING).toLowerCase()))
				 {
			ba.addAcceptState(s);
		}
		return s;
	}
}
