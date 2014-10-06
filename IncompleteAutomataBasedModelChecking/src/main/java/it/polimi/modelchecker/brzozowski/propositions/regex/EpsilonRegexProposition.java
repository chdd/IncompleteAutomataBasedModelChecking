package it.polimi.modelchecker.brzozowski.propositions.regex;

import it.polimi.modelchecker.brzozowski.propositions.states.AbstractProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.AndProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.AtomicProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EpsilonProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LambdaProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.OrProposition;

public class EpsilonRegexProposition implements AbstractRegexProposition {

	
	/**
	 * the concatenation of an {@link EmptyProposition} is defined as follows:
	 * -	if a is an {@link EmptyProposition} the {@link EmptyProposition} is returned
	 * -	the concatenation of an {@link EpsilonProposition} and the {@link LambdaProposition} is equal to the {@link EpsilonProposition}
	 * -	the concatenation of an {@link EpsilonProposition} and an {@link EpsilonProposition} is equal to the {@link EpsilonProposition}
	 * -	the concatenation of an {@link EpsilonProposition} and a {@link AtomicProposition} is a new {@link AndProposition} that contains the {@link EpsilonProposition}
	 * 		and the {@link AtomicProposition}
	 * -	the concatenation of an {@link EpsilonProposition} and an {@link AndProposition} is a new {@link AndProposition} that contains the {@link EpsilonProposition}
	 *		and the original {@link AndProposition}
	 * -	the concatenation of an {@link EpsilonProposition} and an {@link OrProposition} is a new {@link AndProposition} that contains the {@link EpsilonProposition}
	 * 		and the original {@link OrProposition}
	 * @param a: is the {@link AbstractProposition} to be concatenated
	 * @return the {@link AbstractProposition} which is the concatenation of the {@link EpsilonProposition} and the {@link AbstractProposition} a
	 * @throws IllegalArgumentException is generated when the {@link AbstractProposition} to be concatenated is null
	 */
	@Override
	public AbstractRegexProposition concatenate(AbstractRegexProposition a) {
		return null;
	}
	

	@Override
	public AbstractRegexProposition union(AbstractRegexProposition a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractRegexProposition star() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractRegexProposition omega() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

}
