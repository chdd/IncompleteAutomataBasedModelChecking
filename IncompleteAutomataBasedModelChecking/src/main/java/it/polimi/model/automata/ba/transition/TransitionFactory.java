package it.polimi.model.automata.ba.transition;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections15.Factory;

public class TransitionFactory<T extends LabelledTransition> implements Factory<T>{

	protected static int transitionCount=0;
	
	@Override
	public T create() {
		Set<String> labeling=new HashSet<String>();
		labeling.add("<SIGMA>");
		T t=(T) new LabelledTransition(labeling, TransitionFactory.transitionCount);
		TransitionFactory.transitionCount++;
		return t;
	}
	
	public T create(Set<String> labels) {
		
		T t=(T) new LabelledTransition(labels, TransitionFactory.transitionCount);
		TransitionFactory.transitionCount++;
		return t;
	}
}
