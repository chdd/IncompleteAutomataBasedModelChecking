package it.polimi.constraints.io.in;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.in.IBAElementToTransformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.constraints.Port;
import it.polimi.constraints.Replacement;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.common.base.Preconditions;

public class ElementToReplacementTransformer implements Transformer<Element, Replacement> {

	
	public ElementToReplacementTransformer(){
		
	}
	
	@Override
	public Replacement transform(Element input) {

		Preconditions.checkNotNull(input, "The input element cannot be null");
		
		int componentId = Integer.parseInt(input
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_MODEL_STATE_ID));

		String stateName = input.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_NAME);
		
		
		IBAElementToTransformer elementToIBATransformer=new IBAElementToTransformer();
		
		NodeList list=input.getElementsByTagName(
				AutomataIOConstants.XML_ELEMENT_IBA);
		if(list.getLength()==0){
			throw new InternalError("No BA element inside the component");
		}
		IBA ba=elementToIBATransformer.transform((Element) (input.getElementsByTagName(
				AutomataIOConstants.XML_ELEMENT_IBA).item(0)));
		
		State modelState = new StateFactory().create(stateName,
				componentId);
		
		Set<Port> outcomingPorts=new HashSet<Port>();
		Element xmlOutComingPorts = (Element) input
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_PORTS_OUT).item(0);
		NodeList xmlOutComingPortsList = xmlOutComingPorts
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_PORT);
		for (int portId = 0; portId < xmlOutComingPortsList.getLength(); portId++) {
			Element xmlOutComingPort = (Element) xmlOutComingPortsList
					.item(portId);
			Port port = new ElementToPortTransformer(false)
					.transform(xmlOutComingPort);
			outcomingPorts.add(port);
		}

		Set<Port> incomingPorts=new HashSet<Port>();
		Element xmlInComingPorts = (Element) input
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_PORTS_IN).item(0);
		NodeList xmlInComingPortsList = xmlInComingPorts
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_PORT);
		for (int portId = 0; portId < xmlInComingPortsList.getLength(); portId++) {
			Element xmlInComingPort = (Element) xmlInComingPortsList
					.item(portId);
			Port port = new ElementToPortTransformer(true)
					.transform(xmlInComingPort);
			incomingPorts.add(port);
		}
		
		Replacement replacement=new Replacement(modelState, ba, incomingPorts, outcomingPorts);
		return replacement;
	}
}
