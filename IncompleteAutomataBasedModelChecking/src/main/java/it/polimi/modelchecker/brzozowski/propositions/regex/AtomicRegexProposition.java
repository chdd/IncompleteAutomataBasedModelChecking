package it.polimi.modelchecker.brzozowski.propositions.regex;

import it.polimi.modelchecker.brzozowski.propositions.states.AbstractProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.AndProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.AtomicProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EpsilonProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LambdaProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.OrProposition;

/**
 * @author claudiomenghi
 * contains a {@link AtomicRegexProposition} which constraints the automata to recognize specific regular expressions
 */
public class AtomicRegexProposition implements AbstractRegexProposition {

	/**
	 * contains the regular expression that must be recognized by the first automaton in the intersection 
	 */
	private final String regularExpressionAutomaton1;
	
	/**
	 * contains the regular expression that must be recognized by the second automaton in the intersection
	 */
	private final String regularExpressionAutomaton2;
	
	/**
	 * creates a new {@link AtomicRegexProposition}
	 * @param regularExpressionAutomaton1 is the regular expression that must be recognized by the first automaton
	 * @param regularExpressionAutomaton2 is the regular expression that must be recognized by the second automaton
	 * @throws NullPointerException if one of the two regular expressions is null
	 */
	public AtomicRegexProposition(String regularExpressionAutomaton1, String regularExpressionAutomaton2){
		if(regularExpressionAutomaton1==null){
			throw new NullPointerException("The regular expression of the automaton 1 cannot be null");
		}
		if(regularExpressionAutomaton2==null){
			throw new NullPointerException("The regular expression of the automaton 2 cannot be null");
		}
		this.regularExpressionAutomaton1=regularExpressionAutomaton1;
		this.regularExpressionAutomaton2=regularExpressionAutomaton2;
		
	}
	
	/**
	 * @return the regularExpressionAutomaton1 which constraints the first automaton
	 */
	public String getRegularExpressionAutomaton1() {
		return regularExpressionAutomaton1;
	}

	/**
	 * @return the regularExpressionAutomaton2 which constraints the second automaton
	 */
	public String getRegularExpressionAutomaton2() {
		return regularExpressionAutomaton2;
	}
	
	/**
	 * the star operator applied to a {@link AbstractRegexProposition} <expression1, expression2> modifies the regular expressions into (expression)* generating the new {@link AbstractRegexProposition} <(expression1)*,(expression2)*>
	 * @return the new {@link AbstractRegexProposition} where the star operator is applied to each regular expression
	 */
	@Override
	public AbstractRegexProposition star() {
		return new AtomicRegexProposition("("+this.getRegularExpressionAutomaton1()+")*", "("+this.getRegularExpressionAutomaton2()+")*");
	}

	/**
	 * the omega operator applied to a {@link AbstractRegexProposition} <expression1, expression2> modifies the regular expression into (expression)ω generating the new {@link AbstractRegexProposition} <(expression1)ω,(expression2)ω>
	 * @return the new {@link AbstractRegexProposition} where the omega operator is applied to the regular expression
	 */
	@Override
	public AbstractRegexProposition omega() {
		return new AtomicRegexProposition("("+this.getRegularExpressionAutomaton1()+")ω", "("+this.getRegularExpressionAutomaton2()+")ω");
	}
	
	/**
	 * the union of a {@link AbstractRegexProposition} and a {@link AbstractRegexProposition} a is defined as follows:
	 * -	the union of a {@link AbstractRegexProposition} and the {@link EmptyRegexProposition}  is equal to the {@link AbstractRegexProposition}
	 * - 	the union of a {@link AbstractRegexProposition} and {@link LambdaRegexProposition} is a new or {@link OrRegexProposition} that contains the {@link AtomicProposition} and {@link LambdaProposition}
	 * -	the union of two {@link AtomicProposition} <state, expression1>, <state, expression2> with the same state s is a new {@link AtomicProposition} that contains the or combination 
	 * 		of their regular expressions <state, (expression1)+(expression2)> 
	 * -	the union of two {@link AbstractRegexProposition} <state1, expression1>, <state2, expression2> with a different state is a new {@link OrProposition}  that contains the two {@link AtomicProposition} 
	 * -	the union of the {@link AbstractRegexProposition} and an {@link OrProposition} is a new {@link OrProposition} where the {@link AtomicProposition} is added
	 * -	the union of the {@link AbstractRegexProposition} and an {@link AndProposition} is a new {@link OrProposition} where the {@link AtomicProposition} and the {@link OrProposition} are added
	 * -	the union of a {@link AbstractRegexProposition} and {@link EpsilonProposition} is a new {@link OrProposition} that contains the {@link AtomicProposition} and the {@link EpsilonProposition}
	 * @param a the {@link AbstractRegexProposition} to be unified with this {@link AtomicProposition}
	 * @return the union of this {@link AbstractRegexProposition} and the {@link AbstractProposition} a
	 * @throws NullPointerException id the {@link AbstractRegexProposition} a is null 
	 * @throws IllegalArgumentException if the type of proposition is not supported
	 */
	@Override
	public AbstractRegexProposition concatenate(AbstractRegexProposition a) {
		if(a==null){
			throw new NullPointerException("The constraint a cannot be null");
		}
		// the union of a proposition and the empty proposition is equal to the predicate
		if(a instanceof EmptyRegexProposition){
			return this;
		}
		// the union of a predicate and lambda is a new or constraint that contains the predicate and lambda
		if(a instanceof LambdaRegexProposition){
			return new OrRegexProposition(this, a);
		}
		
		
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the predicate is not in the set of the supported types");
	}

	@Override
	public AbstractRegexProposition union(AbstractRegexProposition a) {
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
