package it.polimi.modelchecker.brzozowski.predicates;

import static org.junit.Assert.*;
import it.polimi.model.automata.ba.State;
import it.polimi.modelchecker.brzozowski.propositions.states.AbstractProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.AndProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.AtomicProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EpsilonProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LambdaProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.OrProposition;

import org.junit.Test;

public class OrConstraintTest {

	/**
	 * if a is an empty constraint the empty constraint is returned
	 */
	@Test
	public void testConcat1() {
		AbstractProposition<State> a=new EmptyProposition<State>();
		AbstractProposition<State> p1=new AtomicProposition<State>(new State("s1"),"aab");
		AbstractProposition<State> p2=new AtomicProposition<State>(new State("s2"),"aab");
		AbstractProposition<State> or=new OrProposition<State>(p1, p2);
		assertTrue(or.concatenate(a).equals(a));
	}
	/**
	 * if a is an lambda constraint the or constraint is returned
	 */
	@Test
	public void testConcat2() {
		AbstractProposition<State> a=new LambdaProposition<State>();
		AbstractProposition<State> p1=new AtomicProposition<State>(new State("s1"),"aab");
		AbstractProposition<State> p2=new AtomicProposition<State>(new State("s2"),"aab");
		AbstractProposition<State> or=new OrProposition<State>(p1, p2);
		assertTrue(or.concatenate(a).equals(or));
	}
	/**
	 * if a is an EpsilonConstraint the concatenation of the or constraint and EpsilonConstraint is returned
	 */
	@Test
	public void testConcat3() {
		AbstractProposition<State> a=new EpsilonProposition<State>();
		AbstractProposition<State> p1=new AtomicProposition<State>(new State("s1"),"aab");
		AbstractProposition<State> p2=new AtomicProposition<State>(new State("s2"),"aab");
		AbstractProposition<State> or=new OrProposition<State>(p1, p2);
		assertTrue(or.concatenate(a) instanceof AndProposition);
		assertTrue(((AndProposition<State>)or.concatenate(a)).getPredicates().contains(a));
		assertTrue(((AndProposition<State>)or.concatenate(a)).getPredicates().contains(or));
	}
	/**
	 *	if a is a Predicate the concatenation of the or constraint and Predicate is returned
	 */
	@Test
	public void testConcat4() {
		AbstractProposition<State> a=new AtomicProposition<State>(new State("s2"),"aab");
		AbstractProposition<State> p1=new AtomicProposition<State>(new State("s1"),"aab");
		AbstractProposition<State> p2=new AtomicProposition<State>(new State("s2"),"aab");
		AbstractProposition<State> or=new OrProposition<State>(p1, p2);
		assertTrue(or.concatenate(a) instanceof AndProposition);
		assertTrue(((AndProposition<State>)or.concatenate(a)).getPredicates().contains(a));
		assertTrue(((AndProposition<State>)or.concatenate(a)).getPredicates().contains(or));
	}
	/**
	 *	if a is an AndConstraint the concatenation of the or constraint and the AndConstraint is returned
	 */
	@Test
	public void testConcat5() {
		AbstractProposition<State> a0=new AtomicProposition<State>(new State("s2"),"aab");
		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s3"),"aab");
		AbstractProposition<State> a2=new AndProposition<State>(a0,a1);
		AbstractProposition<State> p1=new AtomicProposition<State>(new State("s1"),"aab");
		AbstractProposition<State> p2=new AtomicProposition<State>(new State("s4"),"aab");
		AbstractProposition<State> or=new OrProposition<State>(p1, p2);
		assertTrue(or.concatenate(a2) instanceof AndProposition);
		assertTrue(((AndProposition<State>)or.concatenate(a2)).getPredicates().contains(a2));
		assertTrue(((AndProposition<State>)or.concatenate(a2)).getPredicates().contains(or));
	}
	/**
	 *  if a is an OrConstraint the concatenation of the or constraint and the OrConstraint is returned
	 */
	@Test
	public void testConcat6() {
		AbstractProposition<State> a0=new AtomicProposition<State>(new State("s3"),"aab");
		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s4"),"aab");
		AbstractProposition<State> or=new OrProposition<State>(a0,a1);
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s2"), "cdd");
		OrProposition<State> c1=new OrProposition<State>(p1,p2);
		assertTrue(or.concatenate(c1) instanceof AndProposition);
		assertTrue(((AndProposition<State>)or.concatenate(c1)).getPredicates().contains(or));
		assertTrue(((AndProposition<State>)or.concatenate(c1)).getPredicates().contains(c1));
	
	}
	/**
	 * the star operator when applied to an or constraint does not produce any effect
	 * @return the or constraint
	 */
	@Test
	public void testStar() {
		
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s2"), "cdd");
		OrProposition<State> c1=new OrProposition<State>(p1,p2);
		AbstractProposition<State> c2=c1.star();
		assertTrue(c2 instanceof OrProposition);
		assertTrue(((OrProposition<State>)c2).getPredicates().contains(p1));
		assertTrue(((OrProposition<State>)c2).getPredicates().contains(p2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnion0() {
		AbstractProposition<State> a=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> b=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> orConstraint=new OrProposition<>(a,b);
		orConstraint.union(null);
	}
	
	/**
	 * the union of an or constraint and an empty constraint returns the or constraint
	 */
	@Test
	public void testUnion1() {
		AbstractProposition<State> a=new EmptyProposition<State>();
		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> b1=new AtomicProposition<State>(new State("s2"), "aab");
		AbstractProposition<State> p=new OrProposition<State>(a1,b1);
		assertTrue(p.union(a).equals(p));
	}
	/**
	 * the union of an or constraint and a LambdaConstraint is a new orConstraint that contains the or constraint and lambda
	 */
	public void testUnion2() {
		AbstractProposition<State> a=new LambdaProposition<State>();
		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> b1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> p=new OrProposition<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof OrProposition);
		OrProposition<State> o=(OrProposition<State>) p.union(a);
		assertTrue(o.getPredicates().contains(p));
		assertTrue(o.getPredicates().contains(a));
	}
	/**
	 * the union of an or constraint and an EpsilonConstrain is a new orConstraint that contains the or constraint and the EpsilonConstrain
	 */
	public void testUnion3() {
		AbstractProposition<State> a=new EpsilonProposition<State>();
		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> b1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> p=new OrProposition<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof OrProposition);
		OrProposition<State> o=(OrProposition<State>) p.union(a);
		assertTrue(o.getPredicates().contains(p));
		assertTrue(o.getPredicates().contains(a));
	}
	/**
	 * the union of an or constraint and a Predicate is a new orConstraint that contains the or constraint and the Predicate
	 */
	public void testUnion4() {
		AbstractProposition<State> a=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> b1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> p=new OrProposition<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof OrProposition);
		OrProposition<State> o=(OrProposition<State>) p.union(a);
		assertTrue(o.getPredicates().contains(p));
		assertTrue(o.getPredicates().contains(a));
	}
	/**
	 *	the union of an or constraint and an orConstraint is a new orConstraint that contains the two or constraints
	 */
	public void testUnion5() {
		
		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> a2=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> p2=new OrProposition<State>(a1,a2);
		
		AbstractProposition<State> b1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> b2=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> p1=new OrProposition<State>(b1,b2);
		
		
		assertTrue(p1.union(p2) instanceof OrProposition);
		OrProposition<State> o=(OrProposition<State>) p1.union(p2);
		assertTrue(o.getPredicates().contains(p1));
		assertTrue(o.getPredicates().contains(p2));
	}
	/**
	 * the union of an or constraint and an andConstraint is a new orConstraint that contains the or constraint and the andConstraint
	 */
	public void testUnion6() {

		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> a2=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> p2=new OrProposition<State>(a1,a2);
		
		AbstractProposition<State> b1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> b2=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> p1=new AndProposition<State>(b1,b2);
		
		
		assertTrue(p1.union(p2) instanceof OrProposition);
		OrProposition<State> o=(OrProposition<State>) p1.union(p2);
		assertTrue(o.getPredicates().contains(p1));
		assertTrue(o.getPredicates().contains(p2));
	}
	
	@Test
	public void testToString() {
		
		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> a2=new AtomicProposition<State>(new State("s1"), "aac");
		AbstractProposition<State> p=new OrProposition<State>(a1,a2);
		assertTrue(p.toString().equals("(<s1,aab>)v(<s1,aac>)"));
	}
	
}
