package it.polimi.model.ltltoba;

import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.labeling.ConjunctiveClauseImpl;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.labeling.Proposition;
import it.polimi.model.impl.labeling.SigmaProposition;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.labeling.ConjunctiveClause;
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

public class LTLtoBATransformer<
	CONSTRAINEDELEMENT extends State,
	STATE extends State,
	STATEFACTORY extends StateFactory<STATE>,
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>> 
	implements Transformer<String, BAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>> {

	private STATEFACTORY stateFactory;
	private TRANSITIONFACTORY transitionFactory;
	
	public LTLtoBATransformer(STATEFACTORY stateFactory,TRANSITIONFACTORY transitionFactory){
		this.transitionFactory=transitionFactory;
		this.stateFactory=stateFactory;
		
	}
	
	@Override
	public BAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> transform(String input) {
		
		BAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> ba=new BAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>(transitionFactory);
		
		Map<IState, STATE> map=new HashMap<IState, STATE>();
		
		LTL2BA4J.formulaToBA(input);
		Collection<ITransition> transitions=LTL2BA4J.formulaToBA(input);
		
		for(ITransition t: transitions){
			 if(!map.containsKey(t.getSourceState())){
				 STATE s=stateFactory.create();
				map.put(t.getSourceState(), s);
			 }
			 if(!map.containsKey(t.getTargetState())){
				 STATE s=stateFactory.create();
				map.put(t.getTargetState(), s);
			 }
		}
		
		for(Entry<IState, STATE> e: map.entrySet()){
			ba.addVertex(e.getValue());
			if(e.getKey().isInitial()){
				ba.addInitialState(e.getValue());
			}
			if(e.getKey().isFinal()){
				ba.addAcceptState(e.getValue());
			}
		}
		for(ITransition t: transitions){
			ConjunctiveClauseImpl<CONSTRAINEDELEMENT> conjunctionClause=new ConjunctiveClauseImpl<CONSTRAINEDELEMENT>();
			for(IGraphProposition p: t.getLabels()){
				if(p.getLabel().equals("<SIGMA>")){
					this.addConjunctionClause(new SigmaProposition<CONSTRAINEDELEMENT>(), ba, map, t);
				}
				else{
					conjunctionClause.addProposition(new Proposition(p.getLabel(),p.isNegated()));
				}
			}
			this.addConjunctionClause(conjunctionClause, ba, map, t);
			
		}
		
		return ba;
	}
	
	private void addConjunctionClause(ConjunctiveClause<CONSTRAINEDELEMENT> conjunctionClause, BAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> ba, Map<IState, STATE> map, ITransition t){
		if(ba.isSuccessor(map.get(t.getSourceState()), map.get(t.getTargetState()))){
			ba.findEdge(map.get(t.getSourceState()), map.get(t.getTargetState())).getDnfFormula().addDisjunctionClause(conjunctionClause);
		}
		else{
			DNFFormula<CONSTRAINEDELEMENT> dnfFormula=new DNFFormula<CONSTRAINEDELEMENT>();
			dnfFormula.addDisjunctionClause(conjunctionClause);
			ba.addTransition(
					map.get(t.getSourceState()),
					map.get(t.getTargetState()),
					transitionFactory.create(dnfFormula));
		}	
	}
}
