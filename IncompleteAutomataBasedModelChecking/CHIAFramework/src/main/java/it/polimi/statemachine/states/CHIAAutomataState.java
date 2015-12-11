package it.polimi.statemachine.states;

import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.automata.io.out.ClaimToStringTrasformer;
import it.polimi.checker.Checker;
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.constraint.ConstraintWriter;
import it.polimi.model.ltltoba.ClaimLTLReader;
import it.polimi.model.ltltoba.LTLtoBATransformer;
import action.CHIAAction;
import action.CHIAException;

/**
 * represents a possible state of CHIA. It offers a method perform that given
 * the current state and a <code>CHIAAction</code> returns the next state of the
 * automaton.
 * 
 * @author Claudio Menghi
 */
public enum CHIAAutomataState implements CHIAStateInterface {
	
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
				return PROPERTYLOADED;
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
	/**
	 * The property is loaded but the model still has to be loaded
	 */
	PROPERTYLOADED {
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
				return PROPERTYLOADED;
			}
			throw new CHIAException("You cannot perform the action: "
					+ chiaAction.getName() + " into the state "
					+ this.toString());
		}
	},
	/**
	 * the property and the model have been loaded
	 */
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
	/**
	 * the model is checked versus the corresponding property
	 */
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
