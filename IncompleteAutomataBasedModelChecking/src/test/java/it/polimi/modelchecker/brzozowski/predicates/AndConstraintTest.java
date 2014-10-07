package it.polimi.modelchecker.brzozowski.predicates;

import static org.junit.Assert.*;
import it.polimi.model.automata.ba.state.State;
import it.polimi.modelchecker.brzozowski.propositions.states.AbstractProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.AndProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.AtomicProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EpsilonProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LambdaProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.OrProposition;

import org.junit.Test;

public class AndConstraintTest {
	/**
	 * if a is an empty constraint the empty constraint is returned
	 */
	@Test
	public void testConcat1() {
		AbstractProposition<State> a=new EmptyProposition<State>();
		AbstractProposition<State> p1=new AtomicProposition<State>(new State("s1"),"aab");
		AbstractProposition<State> p2=new AtomicProposition<State>(new State("s2"),"aab");
		AbstractProposition<State> or=new AndProposition<State>(p1, p2);
		assertTrue(or.concatenate(a).equals(a));
	}
	/**
	 * if a is an lambda constraint the and constraint is returned
	 */
	@Test
	public void testConcat2() {
		AbstractProposition<State> a=new LambdaProposition<State>();
		AbstractProposition<State> p1=new AtomicProposition<State>(new State("s1"),"aab");
		AbstractProposition<State> p2=new AtomicProposition<State>(new State("s2"),"aab");
		AbstractProposition<State> or=new AndProposition<State>(p1, p2);
		assertTrue(or.concatenate(a).equals(or));
	}
	/**
	 * if a is an EpsilonConstraint the concatenation of the and constraint and EpsilonConstraint is returned
	 */
	@Test
	public void testConcat3() {
		AbstractProposition<State> a=new EpsilonProposition<State>();
		AbstractProposition<State> p1=new AtomicProposition<State>(new State("s1"),"aab");
		AbstractProposition<State> p2=new AtomicProposition<State>(new State("s2"),"aab");
		AbstractProposition<State> or=new AndProposition<State>(p1, p2);
		assertTrue(or.concatenate(a) instanceof AndProposition);
		assertTrue(((AndProposition<State>)or.concatenate(a)).getPredicates().contains(a));
		assertTrue(((AndProposition<State>)or.concatenate(a)).getPredicates().contains(or));
	}
	/**
	 *	if a is a Predicate and the last element p of this constraint is a Predicate that has the same state of a,
   	 * the regular expression of p is modified and concatenated to the one of the predicate a
	 */
	@Test
	public void testConcat4() {
		AbstractProposition<State> p1=new AtomicProposition<State>(new State("s1"),"aab");
		AbstractProposition<State> p2=new AtomicProposition<State>(new State("s2"),"aab");
		AbstractProposition<State> a=new AtomicProposition<State>(new State("s2"),"aac");
		
		AbstractProposition<State> p=new AndProposition<State>(p1, p2);
		assertTrue(p.concatenate(a) instanceof AndProposition);
		assertTrue(((AndProposition<State>)p.concatenate(a)).getPredicates().contains(p1));
		assertTrue(((AndProposition<State>)p.concatenate(a)).getPredicates().contains(new AtomicProposition<State>(new State("s2"),"aabaac")));
	}
	/**
	 *	if a is a Predicate and the last element p of this constraint is a Predicate that has a different state of a, 
  	 *	a new and constraint that contains the original constrained and the predicate a is generated
  	 */
  	@Test
	public void testConcat5() {
		AbstractProposition<State> a=new AtomicProposition<State>(new State("s2"),"aac");
		AbstractProposition<State> p1=new AtomicProposition<State>(new State("s1"),"aab");
		AbstractProposition<State> p2=new AtomicProposition<State>(new State("s3"),"aab");
		AbstractProposition<State> or=new AndProposition<State>(p1, p2);
		assertTrue(or.concatenate(a) instanceof AndProposition);
		assertTrue(((AndProposition<State>)or.concatenate(a)).getPredicates().contains(a));
		assertTrue(((AndProposition<State>)or.concatenate(a)).getPredicates().contains(or));
	}
	/**
	 *	if a is an or constraint a new and constraint that contains the constraint of this and constraint and the or constraint is generated
	 */
	@Test
	public void testConcat6() {
		AbstractProposition<State> p0=new AtomicProposition<State>(new State("s1"),"aab");
		AbstractProposition<State> p1=new AtomicProposition<State>(new State("s2"),"ccc");
		AbstractProposition<State> p=new AndProposition<State>(p0,p1);
		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s3"),"aab");
		AbstractProposition<State> a2=new AtomicProposition<State>(new State("s4"),"aab");
		AbstractProposition<State> a=new OrProposition<State>(a1, a2);
		
		assertTrue(p.concatenate(a) instanceof AndProposition);
		assertTrue(((AndProposition<State>)p.concatenate(a)).getPredicates().contains(p));
		assertTrue(((AndProposition<State>)p.concatenate(a)).getPredicates().contains(a));
	}
	/**
	 *  if a is an and constraint and  the last predicate of this constraint and the first predicate of a have the same state, 
   	 * 	their regular expressions are merged
	 */
	@Test
	public void testConcat7() {
		AbstractProposition<State> p0=new AtomicProposition<State>(new State("s1"),"aab");
		AbstractProposition<State> p1=new AtomicProposition<State>(new State("s2"),"aab");
		AbstractProposition<State> p=new AndProposition<State>(p0,p1);
		AtomicProposition<State> a1=new AtomicProposition<State>(new State("s2"), "zzz");
		AtomicProposition<State> a2=new AtomicProposition<State>(new State("s3"), "cdd");
		AndProposition<State> a=new AndProposition<State>(a1,a2);
		assertTrue(p.concatenate(a) instanceof AndProposition);
		assertTrue(((AndProposition<State>)p.concatenate(a)).getPredicates().contains(p0));
		assertTrue(((AndProposition<State>)p.concatenate(a)).getPredicates().contains(new AtomicProposition<State>(new State("s2"), "aabzzz")));
		assertTrue(((AndProposition<State>)p.concatenate(a)).getPredicates().contains(a2));
	}
	/**
	 * if a is an and constraint and  the last predicate of this constraint and the first predicate of do not have 
   	 * 	the same state, a new and constraint that contains all of the constraints of these two and constraints is generated.
	 */
	@Test
	public void testConcat8() {
		AbstractProposition<State> p0=new AtomicProposition<State>(new State("s1"),"aab");
		AbstractProposition<State> p1=new AtomicProposition<State>(new State("s2"),"aab");
		AbstractProposition<State> p=new AndProposition<State>(p0,p1);
		AtomicProposition<State> a1=new AtomicProposition<State>(new State("s3"), "abb");
		AtomicProposition<State> a2=new AtomicProposition<State>(new State("s4"), "cdd");
		AndProposition<State> a=new AndProposition<State>(a1,a2);
		assertTrue(p.concatenate(a) instanceof AndProposition);
		assertTrue(((AndProposition<State>)p.concatenate(a)).getPredicates().contains(p));
		assertTrue(((AndProposition<State>)p.concatenate(a)).getPredicates().contains(a));
	}
	/**
	 * the star operator when applied to an and constraint does not produce any effect
	 * @return the and constraint
	 */
	@Test
	public void testStar() {
		
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s2"), "cdd");
		AndProposition<State> c1=new AndProposition<State>(p1,p2);
		AbstractProposition<State> c2=c1.star();
		assertTrue(c2 instanceof AndProposition);
		assertTrue(((AndProposition<State>)c2).getPredicates().contains(p1));
		assertTrue(((AndProposition<State>)c2).getPredicates().contains(p2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnion0() {
		AbstractProposition<State> a=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> b=new AtomicProposition<State>(new State("s1"), "aab");
		AndProposition<State> orConstraint=new AndProposition<State>(a,b);
		orConstraint.union(null);
	}
	
	/**
	 * the union of an and constraint and an empty constraint returns the or constraint
	 */
	@Test
	public void testUnion1() {
		AbstractProposition<State> a=new EmptyProposition<State>();
		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> b1=new AtomicProposition<State>(new State("s2"), "aab");
		AbstractProposition<State> p=new AndProposition<State>(a1,b1);
		assertTrue(p.union(a).equals(p));
	}
	/**
	 * the union of an or constraint and a LambdaConstraint is a new andConstraint that contains the and constraint and lambda
	 */
	public void testUnion2() {
		AbstractProposition<State> a=new LambdaProposition<State>();
		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> b1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> p=new AndProposition<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof AndProposition);
		AndProposition<State> o=(AndProposition<State>) p.union(a);
		assertTrue(o.getPredicates().contains(p));
		assertTrue(o.getPredicates().contains(a));
	}
	/**
	 * the union of an and constraint and an EpsilonConstrain is a new andConstraint that contains the and constraint and the EpsilonConstrain
	 */
	public void testUnion3() {
		AbstractProposition<State> a=new EpsilonProposition<State>();
		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> b1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> p=new AndProposition<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof AndProposition);
		AndProposition<State> o=(AndProposition<State>) p.union(a);
		assertTrue(o.getPredicates().contains(p));
		assertTrue(o.getPredicates().contains(a));
	}
	/**
	 * the union of an and constraint and a Predicate is a new andConstraint that contains the or constraint and the Predicate
	 */
	public void testUnion4() {
		AbstractProposition<State> a=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> b1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> p=new AndProposition<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof AndProposition);
		AndProposition<State> o=(AndProposition<State>) p.union(a);
		assertTrue(o.getPredicates().contains(p));
		assertTrue(o.getPredicates().contains(a));
	}
	/**
	 *	the union of an and constraint and an andConstraint is a new orConstraint that contains the two and constraints
	 */
	public void testUnion5() {
		
		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> a2=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> p2=new AndProposition<State>(a1,a2);
		
		AbstractProposition<State> b1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> b2=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> p1=new AndProposition<State>(b1,b2);
		
		
		assertTrue(p1.union(p2) instanceof AndProposition);
		AndProposition<State> o=(AndProposition<State>) p1.union(p2);
		assertTrue(o.getPredicates().contains(p1));
		assertTrue(o.getPredicates().contains(p2));
	}
	/**
	 * the union of an and constraint and an andConstraint is a new orConstraint that contains the and constraint and the andConstraint
	 */
	public void testUnion6() {

		AbstractProposition<State> a1=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> a2=new AtomicProposition<State>(new State("s1"), "aab");
		AbstractProposition<State> p2=new AndProposition<State>(a1,a2);
		
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
		AbstractProposition<State> p=new AndProposition<State>(a1,a2);
		assertTrue(p.toString().equals("(<s1,aab>)^(<s1,aac>)"));
	}

}
