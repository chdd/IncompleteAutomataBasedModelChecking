package it.polimi.modelchecker.brzozowski.predicates;

import static org.junit.Assert.assertTrue;
import it.polimi.model.graph.State;
import it.polimi.modelchecker.brzozowski.propositions.AbstractProposition;
import it.polimi.modelchecker.brzozowski.propositions.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.EpsilonProposition;
import it.polimi.modelchecker.brzozowski.propositions.LambdaProposition;
import it.polimi.modelchecker.brzozowski.propositions.state.AndProposition;
import it.polimi.modelchecker.brzozowski.propositions.state.AtomicProposition;
import it.polimi.modelchecker.brzozowski.propositions.state.OrProposition;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class EpsilonConstraintTest {
	/**
	 * the concatenation of the epsilon constraint and the empty constraint is empty
	 */
	@Test
	public void testConcat1() {
		AbstractProposition<State> a=new EmptyProposition<State>();
		AbstractProposition<State> p=new EpsilonProposition<State>();
		assertTrue(p.concatenate(a).equals(a));
	}
	/**
	 * the concatenation of an epsilon constraint and the lambda constraint is equal to the epsilon constraint
	 */
	public void testConcat2() {
		AbstractProposition<State> p=new EpsilonProposition<State>();
		AbstractProposition<State> a=new LambdaProposition<State>();
		assertTrue(p.concatenate(a).equals(p));
	}
	/**
	 * the concatenation of an epsilon constraint and an epsilon constraint is equal to the epsilon constraint
	 */
	public void testConcat3() {
		AbstractProposition<State> p1=new EpsilonProposition<State>();
		AbstractProposition<State> p2=new EpsilonProposition<State>();
		
		assertTrue(p1.concatenate(p2) instanceof EpsilonProposition);
	}
	/**
	 *	the concatenation of an epsilon constraint and a predicate is a new and constraint that contains the epsilon constraint and the predicate
	 */
	public void testConcat4() {
		AbstractProposition<State> p1=new EpsilonProposition<State>();
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s1"), "cdd");
		
		assertTrue(p1.concatenate(p2) instanceof AndProposition);
		AndProposition<State> a=(AndProposition<State>) p1.concatenate(p2);
		
		assertTrue(a.getPredicates().contains(p1));
		assertTrue(a.getPredicates().contains(p2));
		assertTrue(a.getPredicates().size()==2);
	}
	/**
	 *	the concatenation of an epsilon constraint and an and constraint predicate is a new and constraint that contains the epsilon constraint
	 *	and the original and constraint
	 */
	public void testConcat5() {
		
			
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s2"), "cdd");
		
		List<AbstractProposition<State>> l=new ArrayList<AbstractProposition<State>>();
		l.add(p1);
		l.add(p2);
		AndProposition<State> c1=new AndProposition<State>(l);
		EpsilonProposition<State> p3=new EpsilonProposition<State>();
		
		
		assertTrue(p3.concatenate(c1) instanceof AndProposition);
		AndProposition<State> a=(AndProposition<State>) p3.concatenate(c1);
		
		assertTrue(a.getPredicates().contains(p3));
		assertTrue(a.getFistPredicate().equals(p3));
		assertTrue(a.getPredicates().contains(p1));
		assertTrue(a.getPredicates().contains(p2));
	}
	/**
	 * the concatenation of an epsilon constraint and an or constraint is a new and constraint that contains the epsilon constraint
	 * and the original or constraint
	 */
	public void testConcat6() {
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s2"), "cdd");


		OrProposition<State> c1=new OrProposition<State>(p1,p2);
		EpsilonProposition<State> p3=new EpsilonProposition<State>();
		
		assertTrue(p3.concatenate(c1) instanceof AndProposition);
		AndProposition<State> a=(AndProposition<State>) p3.concatenate(c1);
		
		assertTrue(a.getPredicates().contains(p3));
		assertTrue(a.getFistPredicate().equals(p3));
		assertTrue(a.getPredicates().contains(c1));
	}
	
	/**
	 * the star operator applied to an epsilon constraint returns the epsilon constraint
	 * @return the epsilon constraint
	 */
	@Test
	public void testStar() {
		
		AbstractProposition<State> a=new EpsilonProposition<State>();
		assertTrue(a.star() instanceof EpsilonProposition);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnion0() {
		AbstractProposition<State> a=new EpsilonProposition<State>();
		a.union(null);
	}
	
	/**
	 * the union of an epsilon constraint and a lambda constraint is the lambda constraint
	 */
	@Test
	public void testUnion1() {
		AbstractProposition<State> a=new EpsilonProposition<State>();
		AtomicProposition<State> p=new AtomicProposition<State>(new State("s1"), "abb");
		assertTrue(p.union(a).equals(new OrProposition<State>(p, a)));
	}
	/**
	 * the union of an epsilon constraint and an EmptyConstraint is the epsilon constraint
	 */
	public void testUnion2() {
		AbstractProposition<State> a1=new EpsilonProposition<State>();
		AbstractProposition<State> a2=new EmptyProposition<State>();
		
		assertTrue(a1.union(a2) instanceof EpsilonProposition);
	}
	/**
	 *	the union of an epsilon constraint and an epsilon constraint is the epsilon constraint
	 */
	public void testUnion3() {
		AbstractProposition<State> a1=new EpsilonProposition<State>();
		AbstractProposition<State> a2=new EpsilonProposition<State>();
		
		assertTrue(a1.union(a2) instanceof EpsilonProposition);
	}
	/**
	 *   the union of an epsilon constraint and an epsilon constraint is an or constraint that contains the epsilon constraint
	 *    and the predicate 
	 */
	public void testUnion4() {
		AbstractProposition<State> a1=new EpsilonProposition<State>();
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s2"), "cdd");
		
		assertTrue(a1.union(p2) instanceof OrProposition);
		OrProposition<State> a=(OrProposition<State>) a1.union(p2);
		
		assertTrue(a.getPredicates().contains(a1));
		assertTrue(a.getPredicates().contains(p2));
		assertTrue(a.getPredicates().size()==2);
	}
	/**
	 *	the union of an epsilon constraint and an epsilon constraint is an or constraint that contains the epsilon constraint and the predicate
	 */
	public void testUnion5() {
		AbstractProposition<State> a1=new EpsilonProposition<State>();
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s2"), "cdd");
		AtomicProposition<State> p3=new AtomicProposition<State>(new State("s3"), "cdd");
		
		OrProposition<State> c1=new OrProposition<State>(p2, p3);
		assertTrue(a1.union(c1) instanceof OrProposition);
		OrProposition<State> a=(OrProposition<State>) a1.union(c1);
		
		assertTrue(a.getPredicates().contains(a1));
		assertTrue(a.getPredicates().contains(c1));
	}
	/**
	 *	the union of an epsilon constraint and an and constraint is an or constraint that contains the epsilon constraint and the and constraint 
	 */
	public void testUnion6() {
		AbstractProposition<State> a1=new EpsilonProposition<State>();
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s2"), "cdd");
		AtomicProposition<State> p3=new AtomicProposition<State>(new State("s3"), "cdd");
		
		AndProposition<State> c1=new AndProposition<State>(p2, p3);
		assertTrue(a1.union(c1) instanceof OrProposition);
		OrProposition<State> a=(OrProposition<State>) a1.union(c1);
		
		assertTrue(a.getPredicates().contains(a1));
		assertTrue(a.getPredicates().contains(c1));
	}
	
	@Test
	public void testToString() {
		
		AbstractProposition<State> a=new EpsilonProposition<State>();
		assertTrue(a.toString().equals("ε"));
	}

}
