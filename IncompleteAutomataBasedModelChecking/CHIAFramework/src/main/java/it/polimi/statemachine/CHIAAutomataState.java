package it.polimi.statemachine;

import action.CHIAAction;
import action.CHIAException;
import it.polimi.automata.io.in.BAReader;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.automata.io.out.BAToStringTrasformer;
import it.polimi.automata.io.out.IntersectionWriter;
import it.polimi.checker.Checker;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.constraint.ConstraintWriter;
import it.polimi.contraintcomputation.ConstraintGenerator;
import it.polimi.model.ltltoba.LTLReader;
import it.polimi.model.ltltoba.LTLtoBATransformer;

/**
 * represents a possible state of CHIA. It offers a method perform that given
 * the current state and a <code>CHIAAction</code> returns the next state of the
 * automaton.
 * 
 * @author Claudio
 *
 */
public enum CHIAAutomataState implements CHIAState {
	
	/**
	 * is the initial state of the automaton
	 */
	INIT {
		@Override
		public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction){
			/**
			 * Whenever a BA reading action is executed the system moves to the CLAIMLOADED state 
			 */
			if (chiaAction==BAReader.class) {
				return true;
			}
			/**
			 * Whenever a LTLReader reading action is executed the system moves to the CLAIMLOADED state 
			 */
			if (chiaAction==LTLReader.class) {
				return true;
			}
			if (chiaAction==LTLtoBATransformer.class) {
				return true;
			}
			if (chiaAction==IBAReader.class) {
				return true;
			}
			return false;
		}
		
		@Override
		public CHIAAutomataState perform(Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {
			/**
			 * Whenever a BA reading action is executed the system moves to the CLAIMLOADED state 
			 */
			if (chiaAction==BAReader.class) {
				return CLAIMLOADED;
			}
			/**
			 * Whenever a LTLReader reading action is executed the system moves to the CLAIMLOADED state 
			 */
			if (chiaAction==LTLReader.class) {
				return CLAIMLOADED;
			}
			if (chiaAction==LTLtoBATransformer.class) {
				return CLAIMLOADED;
			}
			if (chiaAction==IBAReader.class) {
				return MODELLOADED;
			}
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());

		}
	},
	MODELLOADED {
		@Override
		public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction){
			if (chiaAction==LTLtoBATransformer.class) {
				return true;
			}
			if (chiaAction==BAReader.class) {
				return true;
			}
			if (chiaAction==LTLReader.class) {
				return true;
			}
			if (chiaAction==IBAReader.class) {
				return true;
			}
			return false;
		}
		
		@Override
		public CHIAAutomataState perform(Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {
			if (chiaAction==LTLtoBATransformer.class) {
				return READY;
			}
			if (chiaAction==BAReader.class) {
				return READY;
			}
			if (chiaAction==LTLReader.class) {
				return READY;
			}
			if (chiaAction==IBAReader.class) {
				return MODELLOADED;
			}
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());

		}
	},
	CLAIMLOADED {
		@Override
		public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction){
			if (chiaAction==IBAReader.class) {
				return true;
			}
			if (chiaAction==LTLtoBATransformer.class) {
				return true;
			}
			if (chiaAction==LTLReader.class) {
				return true;
			}
			if (chiaAction==BAReader.class) {
				return true;
			}
			if (chiaAction==BAToStringTrasformer.class) {
				return true;
			}
			return false;
		}
		@Override
		public CHIAAutomataState perform(Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {
			if (chiaAction==IBAReader.class) {
				return READY;
			}
			if (chiaAction==LTLtoBATransformer.class) {
				return CLAIMLOADED;
			}
			if (chiaAction==LTLReader.class) {
				return CLAIMLOADED;
			}
			if (chiaAction==BAReader.class) {
				return CLAIMLOADED;
			}
			if (chiaAction==BAToStringTrasformer.class) {
				return CLAIMLOADED;
			}
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());
		}
	},
	READY {
		@Override
		public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction){
			if (chiaAction==Checker.class) {
				return true;
			}
			if (chiaAction==LTLReader.class) {
				return true;
			}
			if (chiaAction==BAToStringTrasformer.class) {
				return true;
			}
			if (chiaAction==LTLtoBATransformer.class) {
				return true;
			}
			if (chiaAction==BAReader.class) {
				return true;
			}
			if (chiaAction==IBAReader.class) {
				return true;
			}
			return false;
		}
		@Override
		public CHIAAutomataState perform(Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {
			if (chiaAction==Checker.class) {
				return CHECKED;
			}
			if (chiaAction==LTLReader.class) {
				return READY;
			}
			if (chiaAction==BAToStringTrasformer.class) {
				return READY;
			}
			if (chiaAction==LTLtoBATransformer.class) {
				return READY;
			}
			if (chiaAction==BAReader.class) {
				return READY;
			}
			if (chiaAction==IBAReader.class) {
				return READY;
			}
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());
		}
	},
	CHECKED {
		@Override
		public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction){
			if(chiaAction.getClass().equals(ConstraintGenerator.class)){
				return true;
			}
				
			if (chiaAction==LTLReader.class) {
				return true;
			}
			if (chiaAction==BAToStringTrasformer.class) {
				return true;
			}
			if (chiaAction==LTLtoBATransformer.class) {
				return true;
			}
			if (chiaAction==BAReader.class) {
				return true;
			}
			if (chiaAction==IBAReader.class) {
				return true;
			}
			if (chiaAction==IntersectionWriter.class) {
				return true;
			}
			if(chiaAction==ConstraintGenerator.class){
				return true;
			}
			return false;
		}
		@Override
		public CHIAAutomataState perform(Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {
			if(chiaAction.getClass().equals(ConstraintGenerator.class)){
				return CONSTRAINTCOMPUTED;
			}
				
			if (chiaAction==LTLReader.class) {
				return READY;
			}
			if (chiaAction==BAToStringTrasformer.class) {
				return CHECKED;
			}
			if (chiaAction==LTLtoBATransformer.class) {
				return READY;
			}
			if (chiaAction==BAReader.class) {
				return READY;
			}
			if (chiaAction==IBAReader.class) {
				return READY;
			}
			if (chiaAction==IntersectionWriter.class) {
				return CHECKED;
			}
			if(chiaAction==ConstraintGenerator.class){
				return CONSTRAINTCOMPUTED;
			}
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());
		}
	},
	CONSTRAINTCOMPUTED {
		@Override
		public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction){
			if (chiaAction== LTLReader.class) {
				return true;
			}
			if (chiaAction==BAToStringTrasformer.class) {
				return true;
			}
			if (chiaAction==LTLtoBATransformer.class) {
				return true;
			}
			if (chiaAction==BAReader.class) {
				return true;
			}
			if (chiaAction==IBAReader.class) {
				return true;
			}
			if (chiaAction==IntersectionWriter.class) {
				return true;
			}
			if (chiaAction==ConstraintToStringTrasformer.class){
				return true;
			}
			if (chiaAction==ConstraintGenerator.class) {
				return true;
			}
			if(chiaAction==ConstraintWriter.class){
				return true;
			}
			return false;
		}
		@Override
		public CHIAAutomataState perform(Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {
			if (chiaAction== LTLReader.class) {
				return READY;
			}
			if (chiaAction==BAToStringTrasformer.class) {
				return CONSTRAINTCOMPUTED;
			}
			if (chiaAction==LTLtoBATransformer.class) {
				return READY;
			}
			if (chiaAction==BAReader.class) {
				return READY;
			}
			if (chiaAction==IBAReader.class) {
				return READY;
			}
			if (chiaAction==IntersectionWriter.class) {
				return CONSTRAINTCOMPUTED;
			}
			if (chiaAction==ConstraintToStringTrasformer.class){
				return CONSTRAINTCOMPUTED;
			}
			if (chiaAction==ConstraintGenerator.class) {
				return CONSTRAINTCOMPUTED;
			}
			if(chiaAction==ConstraintWriter.class){
				return CONSTRAINTCOMPUTED;
			}
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());
		}
	};
	@Override
	public abstract CHIAAutomataState perform(Class<? extends CHIAAction<?>> chiaAction)
				throws CHIAException;
	
	@Override
	public abstract boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction);
}
