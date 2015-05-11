package it.polimi.constraints.io.in;

import it.polimi.automata.BA;
import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.in.ElementToBATransformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.constraints.ColoredPort;
import it.polimi.constraints.SubProperty;

import java.util.HashSet;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.common.base.Preconditions;

public class ElementToSubPropertyTransformer implements Transformer<Element, SubProperty> {

	
	public ElementToSubPropertyTransformer(){
		
	}
	
	@Override
	public SubProperty transform(Element input) {

		Preconditions.checkNotNull(input, "The subproperty to be converted cannot be null");
		int componentId = Integer.parseInt(input
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_MODEL_STATE_ID));

		String stateName = input.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_NAME);
		
		
		ElementToBATransformer elementToBATransformer=new ElementToBATransformer();
		
		NodeList list=input.getElementsByTagName(
				AutomataIOConstants.XML_ELEMENT_BA);
		if(list.getLength()==0){
			throw new InternalError("No BA element inside the component");
		}
		BA ba=elementToBATransformer.transform((Element) (input.getElementsByTagName(
				AutomataIOConstants.XML_ELEMENT_BA).item(0)));
		
		State modelState = new StateFactory().create(stateName,
				componentId);
		SubProperty component = new SubProperty(modelState, ba, new HashSet<ColoredPort>(), new HashSet<ColoredPort>());
				
				
		return component;
	}

}
