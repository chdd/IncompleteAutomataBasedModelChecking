package it.polimi.statemachine;

import action.CHIAAction;
import action.CHIAException;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.automata.io.out.ClaimToStringTrasformer;
import it.polimi.automata.io.out.IntersectionWriter;
import it.polimi.checker.Checker;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.constraint.ConstraintWriter;
import it.polimi.contraintcomputation.ConstraintGenerator;
import it.polimi.model.ltltoba.ClaimLTLReader;
import it.polimi.model.ltltoba.LTLtoBATransformer;

/**
 * represents a possible state of CHIA. It offers a method perform that given
 * the current state and a <code>CHIAAction</code> returns the next state of the
 * automaton.
 * 
 * @author Claudio
 */
public enum CHIAAutomataState implements CHIAState {
	
	/**
	 * is the initial state of the automaton
	 */
	INIT {
		/**
		 *  {@inheritDoc}
		 */
		@Override
		public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction){
			/**
			 * in the initial state it is possible to load the model or the claim of interest
			 */
			if (chiaAction==ModelReader.class || isAClaimReadingAction(chiaAction)) {
				return true;
			}
			return false;
		}
		
		/**
		 *  {@inheritDoc}
		 */
		@Override
		public CHIAAutomataState perform(Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {
			/**
			 * if the action concerns the model reading, the model loaded state is reached
			 */
			if (chiaAction==ModelReader.class) {
				return MODELLOADED;
			}
			if(isAClaimReadingAction(chiaAction)){
				return CLAIMLOADED;
			}
			
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());
		}

		
	},

	/**
	 * in this state the model has been already loaded 
	 */
	MODELLOADED {
		/**
		 *  {@inheritDoc}
		 */
		@Override
		public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction){
			if (isAClaimReadingAction(chiaAction)) {
				return true;
			}
			/**
			 * if the action concerns the model reading, the model loaded state is reached
			 */
			if (chiaAction==ModelReader.class) {
				return true;
			}
			return false;
		}
		/**
		 *  {@inheritDoc}
		 */
		@Override
		public CHIAAutomataState perform(Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {
			if (isAClaimReadingAction(chiaAction)) {
				return READY;
			}
			if (chiaAction==ModelReader.class) {
				return MODELLOADED;
			}
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());

		}
	},
	CLAIMLOADED {
		/**
		 *  {@inheritDoc}
		 */
		@Override
		public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction){
			if (chiaAction==ModelReader.class) {
				return true;
			}
			if (isAClaimReadingAction(chiaAction)) {
				return true;
			}
			if (chiaAction==ClaimToStringTrasformer.class) {
				return true;
			}
			return false;
		}
		/**
		 *  {@inheritDoc}
		 */
		@Override
		public CHIAAutomataState perform(Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {
			if (chiaAction==ModelReader.class) {
				return READY;
			}
			if (isAClaimReadingAction(chiaAction) || chiaAction==ClaimToStringTrasformer.class) {
				return CLAIMLOADED;
			}
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());
		}
	},
	
	READY {
		/**
		 *  {@inheritDoc}
		 */
		@Override
		public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction){
			if (chiaAction==Checker.class) {
				return true;
			}
			if (isAClaimReadingAction(chiaAction)) {
				return true;
			}
			if (chiaAction==ModelReader.class) {
				return true;
			}
			if (chiaAction==ClaimToStringTrasformer.class) {
				return true;
			}
			return false;
		}
		/**
		 *  {@inheritDoc}
		 */
		@Override
		public CHIAAutomataState perform(Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {
			if (chiaAction==Checker.class) {
				return CHECKED;
			}
			if (isAClaimReadingAction(chiaAction)) {
				return READY;
			}
			if (chiaAction==ClaimToStringTrasformer.class) {
				return READY;
			}
			if (chiaAction==ModelReader.class) {
				return READY;
			}
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());
		}
	},
	CHECKED {
		/**
		 *  {@inheritDoc}
		 */
		@Override
		public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction){
			
			if (isAClaimReadingAction(chiaAction)) {
				return true;
			}
			if (chiaAction==ClaimToStringTrasformer.class) {
				return true;
			}
			if (chiaAction==ModelReader.class) {
				return true;
			}
			if(chiaAction==ConstraintGenerator.class){
				return true;
			}
			if (chiaAction==IntersectionWriter.class) {
				return true;
			}
			return false;
		}
		/**
		 *  {@inheritDoc}
		 */
		@Override
		public CHIAAutomataState perform(Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {
			if(chiaAction==ConstraintGenerator.class){
				return CONSTRAINTCOMPUTED;
			}
				
			if (isAClaimReadingAction(chiaAction)) {
				return READY;
			}
			if (chiaAction==ClaimToStringTrasformer.class) {
				return CHECKED;
			}
			if (chiaAction==ModelReader.class) {
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
		/**
		 *  {@inheritDoc}
		 */
		@Override
		public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction){
			if (isAClaimReadingAction(chiaAction)) {
				return true;
			}
			if (chiaAction==ModelReader.class) {
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
		/**
		 *  {@inheritDoc}
		 */
		@Override
		public CHIAAutomataState perform(Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {
			if (isAClaimReadingAction(chiaAction)) {
				return READY;
			}
			if (chiaAction==ModelReader.class) {
				return READY;
			}
			if (chiaAction==ClaimToStringTrasformer.class) {
				return CONSTRAINTCOMPUTED;
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
	
	/**
	 * @param chiaAction
	 */
	private static boolean isAClaimReadingAction(
			Class<? extends CHIAAction<?>> chiaAction) {
		/**
		 * if the action concerns the claim readin, the claim loaded state is reached
		 */
		if (chiaAction==ClaimReader.class || chiaAction==ClaimLTLReader.class || chiaAction==LTLtoBATransformer.class) {
			return true;
		}
		return false;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	@Override
	public abstract CHIAAutomataState perform(Class<? extends CHIAAction<?>> chiaAction)
				throws CHIAException;
	
	/**
	 *  {@inheritDoc}
	 */
	@Override
	public abstract boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction);
}
