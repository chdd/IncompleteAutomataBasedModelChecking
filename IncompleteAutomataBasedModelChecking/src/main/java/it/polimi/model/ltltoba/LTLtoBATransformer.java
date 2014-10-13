package it.polimi.model.ltltoba;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.state.StateFactory;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.ba.transition.TransitionFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.collections15.Transformer;

import rwth.i2.ltl2ba4j.LTL2BA4J;
import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.IState;
import rwth.i2.ltl2ba4j.model.ITransition;

public class LTLtoBATransformer implements Transformer<String, BuchiAutomaton<State, LabelledTransition>> {

	private StateFactory<State> factory=new StateFactory<State>();
	private TransitionFactory<LabelledTransition> transitionFactory=new TransitionFactory<LabelledTransition>();
	
	@Override
	public BuchiAutomaton<State, LabelledTransition> transform(String input) {
		
		BuchiAutomaton<State, LabelledTransition> ba=new BuchiAutomaton<State, LabelledTransition>();
		
		Map<IState, State> map=new HashMap<IState, State>();
		
		LTL2BA4J.formulaToBA(input);
		/*Collection<ITransition> transitions=LTL2BA4J.formulaToBA(input);
		for(ITransition t: transitions){
			 if(!map.containsKey(t.getSourceState())){
				State s=factory.create(t.getSourceState().getLabel());
				map.put(t.getSourceState(), s);
			 }
			 if(!map.containsKey(t.getTargetState())){
				State s=factory.create(t.getTargetState().getLabel());
				map.put(t.getTargetState(), s);
			 }
		}
		
		for(Entry<IState, State> e: map.entrySet()){
			ba.addVertex(e.getValue());
			if(e.getKey().isInitial()){
				ba.addInitialState(e.getValue());
			}
			if(e.getKey().isFinal()){
				ba.addAcceptState(e.getValue());
			}
		}
		for(ITransition t: transitions){
			Set<String> propositions=new HashSet<String>();
			for(IGraphProposition p: t.getLabels()){
				propositions.add(p.getFullLabel());
			}
			ba.addTransition(
					map.get(t.getSourceState()),
					map.get(t.getTargetState()),
					transitionFactory.create(propositions));
		}
		*/
		return ba;
	}
}
