package it.polimi.model.ltltoba;

import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.labeling.ConjunctiveClauseImpl;
import it.polimi.model.impl.labeling.DNFFormulaImpl;
import it.polimi.model.impl.labeling.Proposition;
import it.polimi.model.impl.labeling.SigmaProposition;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.labeling.ConjunctiveClause;
import it.polimi.model.interfaces.states.StateFactory;
import it.polimi.model.interfaces.transitions.TransitionFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections15.Transformer;

import rwth.i2.ltl2ba4j.LTL2BA4J;
import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.IState;
import rwth.i2.ltl2ba4j.model.ITransition;

public class LTLtoBATransformer<STATE extends State, TRANSITION extends Transition>
		implements Transformer<String, BAImpl<STATE, TRANSITION>> {

	private StateFactory<STATE> stateFactory;
	private TransitionFactory<TRANSITION> transitionFactory;

	public LTLtoBATransformer(StateFactory<STATE> stateFactory,
			TransitionFactory<TRANSITION> transitionFactory) {
		this.transitionFactory = transitionFactory;
		this.stateFactory = stateFactory;

	}

	@Override
	public BAImpl<STATE, TRANSITION> transform(String input) {

		BAImpl<STATE, TRANSITION> ba = new BAImpl<STATE, TRANSITION>(
				this.transitionFactory, this.stateFactory);

		Map<IState, STATE> map = new HashMap<IState, STATE>();

		LTL2BA4J.formulaToBA(input);
		Collection<ITransition> transitions = LTL2BA4J.formulaToBA(input);

		for (ITransition t : transitions) {
			if (!map.containsKey(t.getSourceState())) {
				STATE s = stateFactory.create();
				map.put(t.getSourceState(), s);
			}
			if (!map.containsKey(t.getTargetState())) {
				STATE s = stateFactory.create();
				map.put(t.getTargetState(), s);
			}
		}

		for (Entry<IState, STATE> e : map.entrySet()) {
			ba.addState(e.getValue());
			if (e.getKey().isInitial()) {
				ba.addInitialState(e.getValue());
			}
			if (e.getKey().isFinal()) {
				ba.addAcceptState(e.getValue());
			}
		}
		for (ITransition t : transitions) {
			ConjunctiveClause conjunctionClause = new ConjunctiveClauseImpl();
			for (IGraphProposition p : t.getLabels()) {
				if (p.getLabel().equals("<SIGMA>")) {

					conjunctionClause = new SigmaProposition<STATE>();
				} else {
					conjunctionClause.addProposition(new Proposition(p
							.getLabel(), p.isNegated()));
				}
			}
			this.addConjunctionClause(conjunctionClause, ba, map, t);

		}

		return ba;
	}

	private void addConjunctionClause(ConjunctiveClause conjunctionClause,
			BAImpl<STATE, TRANSITION> ba, Map<IState, STATE> map, ITransition t) {
		if (ba.getGraph().isSuccessor(map.get(t.getSourceState()),
				map.get(t.getTargetState()))) {
			ba.getGraph()
					.findEdge(map.get(t.getSourceState()),
							map.get(t.getTargetState())).getCondition()
					.addDisjunctionClause(conjunctionClause);
		} else {
			DNFFormulaImpl dnfFormula = new DNFFormulaImpl();
			dnfFormula.addDisjunctionClause(conjunctionClause);
			ba.addTransition(map.get(t.getSourceState()),
					map.get(t.getTargetState()),
					transitionFactory.create(dnfFormula));
		}
	}
}
