package it.polimi.automata.io.in.states;

import it.polimi.automata.BA;
import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;

import org.w3c.dom.Element;

import com.google.common.base.Preconditions;
/**
 * transforms an object of type Node into an object of type State
 * 
 * @author claudiomenghi
 *
 */
public class ElementToBAStateTransformer implements Transformer<Element, State>{

	
	/**
	 * is the factory which is used to create the states of the Buchi automaton
	 */
	protected final StateFactory stateFactory;
	private final BA ba;
	
	
	public ElementToBAStateTransformer(BA ba){
		Preconditions.checkNotNull(ba, "The ba cannot be null");
		
		this.stateFactory=new StateFactory();
		this.ba=ba;
	}
	
	@Override
	public State transform(Element eElement) {
		Preconditions.checkNotNull(eElement,"The element cannot be null");
		
		int id= Integer.parseInt(eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_ID));
		
		State s=stateFactory.create(eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_NAME), id);
		ba.addState(s);
		
		if(!eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_INITIAL).isEmpty()){
			ba.addInitialState(s);
		}
		if(!eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_ACCEPTING).isEmpty()){
			ba.addAcceptState(s);
		}
		return s;
	}
}
