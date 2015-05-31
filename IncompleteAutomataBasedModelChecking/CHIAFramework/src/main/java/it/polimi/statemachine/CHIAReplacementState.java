package it.polimi.statemachine;

import it.polimi.automata.io.out.IntersectionToStringTransformer;
import it.polimi.constraints.io.in.constraint.ConstraintReader;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.replacement.ReplacementToStringTransformer;
import it.polimi.replacementchecker.ReplacementChecker;
import action.CHIAAction;

public enum CHIAReplacementState implements CHIAState{
	
	/**
	 * is the initial state of the automaton
	 */
	INIT {
		@Override
		public CHIAReplacementState perform(Class<? extends CHIAAction> chiaAction) throws CHIAException {
		
			if(chiaAction==ReplacementReader.class){
				return REPLACEMENTLOADED;
			}
			if(chiaAction==ConstraintReader.class){
				return CONSTRAINTLOADED;
			}
			
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());

		}
	},
	REPLACEMENTLOADED {
		@Override
		public CHIAReplacementState perform(Class<? extends CHIAAction> chiaAction) throws CHIAException {

			if(chiaAction==ReplacementReader.class){
				return REPLACEMENTLOADED;
			}
			if(chiaAction==ConstraintReader.class){
				return READY;
			}
			if(chiaAction==ReplacementToStringTransformer.class){
				return REPLACEMENTLOADED;
			}
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());
		}
	},
	CONSTRAINTLOADED {
		@Override
		public CHIAReplacementState perform(Class<? extends CHIAAction> chiaAction) throws CHIAException {

			if(chiaAction==ConstraintReader.class){
				return CONSTRAINTLOADED;
			}
			if(chiaAction==ReplacementReader.class){
				return READY;
			}
			if(chiaAction==ConstraintToStringTrasformer.class){
				return CONSTRAINTLOADED;
			}
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());
		}
	},
	READY {
		@Override
		public CHIAReplacementState perform(Class<? extends CHIAAction> chiaAction) throws CHIAException {
			
			if(chiaAction==ConstraintToStringTrasformer.class){
				return READY;
			}
			if(chiaAction==ReplacementToStringTransformer.class){
				return READY;
			}
			if(chiaAction==ReplacementChecker.class){
				return CHECKED;
			}
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());
		}
	},
	CHECKED {
		@Override
		public CHIAReplacementState perform(Class<? extends CHIAAction> chiaAction) throws CHIAException {
			if(chiaAction==ConstraintToStringTrasformer.class){
				return CHECKED;
			}
			if(chiaAction==ReplacementToStringTransformer.class){
				return CHECKED;
			}
			if(chiaAction==IntersectionToStringTransformer.class){
				return CHECKED;
			}
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());
		}
	};
	

	public abstract CHIAReplacementState perform(Class<? extends CHIAAction> chiaAction)
			throws CHIAException;
	
}
