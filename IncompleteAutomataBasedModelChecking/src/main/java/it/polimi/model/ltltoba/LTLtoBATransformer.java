package it.polimi.model.ltltoba;

import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.labeling.ConjunctiveClause;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.impl.transitions.LabelledTransitionFactoryImpl;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections15.Transformer;

import rwth.i2.ltl2ba4j.LTL2BA4J;
import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.IState;
import rwth.i2.ltl2ba4j.model.ITransition;

public class LTLtoBATransformer implements Transformer<String, BAImpl<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>> {

	private StateFactory<State> factory=new StateFactory<State>();
	private LabelledTransitionFactoryImpl transitionFactory=new LabelledTransitionFactoryImpl();
	
	@Override
	public BAImpl<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>> transform(String input) {
		
		BAImpl<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>> ba=new BAImpl<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>(new LabelledTransitionFactoryImpl());
		
		Map<IState, State> map=new HashMap<IState, State>();
		
		LTL2BA4J.formulaToBA(input);
		Collection<ITransition> transitions=LTL2BA4J.formulaToBA(input);
		
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
			ConjunctiveClause conjunctionClause=new ConjunctiveClause();
			for(IGraphProposition p: t.getLabels()){
				conjunctionClause.addProposition(p);
			}
			if(ba.isSuccessor(map.get(t.getSourceState()), map.get(t.getTargetState()))){
				ba.findEdge(map.get(t.getSourceState()), map.get(t.getTargetState())).getDnfFormula().addDisjunctionClause(conjunctionClause);
			}
			else{
				DNFFormula dnfFormula=new DNFFormula();
				dnfFormula.addDisjunctionClause(conjunctionClause);
				ba.addTransition(
						map.get(t.getSourceState()),
						map.get(t.getTargetState()),
						transitionFactory.create(dnfFormula));
			}
		}
		
		return ba;
	}
}
