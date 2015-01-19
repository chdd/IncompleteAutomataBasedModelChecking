package it.polimi.automata.labeling.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;

public class LabelImplFactory implements LabelFactory<Label>{

	@Override
	public Label create() {
		return new LabelImpl(new HashSet<IGraphProposition>());
	}

	@Override
	public Label create(Set<Entry<String, Boolean>> labels) {
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		for(Entry<String, Boolean> entry: labels){
			propositions.add(new GraphProposition(entry.getKey(), entry.getValue()));
		}
		return new LabelImpl(propositions);
	}

}
