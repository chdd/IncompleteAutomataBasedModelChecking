package it.polimi.browzozky.predicates;

import static org.junit.Assert.*;
import it.polimi.browzozky.predicates.types.AndConstraint;
import it.polimi.browzozky.predicates.types.EmptyConstraint;
import it.polimi.browzozky.predicates.types.EpsilonConstraint;
import it.polimi.browzozky.predicates.types.LambdaConstraint;
import it.polimi.browzozky.predicates.types.OrConstraint;
import it.polimi.browzozky.predicates.types.Predicate;
import it.polimi.model.State;

import org.junit.Test;

public class EpsilonConstraintTest {
	/**
	 * the concatenation of the epsilon constraint and the empty constraint is empty
	 */
	@Test
	public void testConcat1() {
		AbstractConstraint<State> a=new EmptyConstraint<State>();
		AbstractConstraint<State> p=new EpsilonConstraint<State>();
		assertTrue(p.concatenate(a).equals(a));
	}
	/**
	 * the concatenation of an epsilon constraint and the lambda constraint is equal to the epsilon constraint
	 */
	public void testConcat2() {
		AbstractConstraint<State> p=new EpsilonConstraint<State>();
		AbstractConstraint<State> a=new LambdaConstraint<State>();
		assertTrue(p.concatenate(a).equals(p));
	}
	/**
	 * the concatenation of an epsilon constraint and an epsilon constraint is equal to the epsilon constraint
	 */
	public void testConcat3() {
		AbstractConstraint<State> p1=new EpsilonConstraint<State>();
		AbstractConstraint<State> p2=new EpsilonConstraint<State>();
		
		assertTrue(p1.concatenate(p2) instanceof EpsilonConstraint);
	}
	/**
	 *	the concatenation of an epsilon constraint and a predicate is a new and constraint that contains the epsilon constraint and the predicate
	 */
	public void testConcat4() {
		AbstractConstraint<State> p1=new EpsilonConstraint<State>();
		Predicate<State> p2=new Predicate<State>(new State("s1"), "cdd");
		
		assertTrue(p1.concatenate(p2) instanceof AndConstraint);
		AndConstraint<State> a=(AndConstraint<State>) p1.concatenate(p2);
		
		assertTrue(a.getConstraints().contains(p1));
		assertTrue(a.getConstraints().contains(p2));
		assertTrue(a.getConstraints().size()==2);
	}
	/**
	 *	the concatenation of an epsilon constraint and an and constraint predicate is a new and constraint that contains the epsilon constraint
	 *	and the original and constraint
	 */
		public void testConcat5() {
		Predicate<State> p1=new Predicate<State>(new State("s1"), "abb");
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		AndConstraint<State> c1=new AndConstraint<State>();
		c1.addConstraint(p1);
		c1.addConstraint(p2);
		EpsilonConstraint<State> p3=new EpsilonConstraint<State>();
		
		
		assertTrue(p3.concatenate(c1) instanceof AndConstraint);
		AndConstraint<State> a=(AndConstraint<State>) p3.concatenate(c1);
		
		assertTrue(a.getConstraints().contains(p3));
		assertTrue(a.getFistPredicate().equals(p3));
		assertTrue(a.getConstraints().contains(p1));
		assertTrue(a.getConstraints().contains(p2));
	}
	/**
	 * the concatenation of an epsilon constraint and an or constraint is a new and constraint that contains the epsilon constraint
	 * and the original or constraint
	 */
	public void testConcat6() {
		Predicate<State> p1=new Predicate<State>(new State("s1"), "abb");
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		OrConstraint<State> c1=new OrConstraint<State>();
		c1.addConstraint(p1);
		c1.addConstraint(p2);
		EpsilonConstraint<State> p3=new EpsilonConstraint<State>();
		
		assertTrue(p3.concatenate(c1) instanceof AndConstraint);
		AndConstraint<State> a=(AndConstraint<State>) p3.concatenate(c1);
		
		assertTrue(a.getConstraints().contains(p3));
		assertTrue(a.getFistPredicate().equals(p3));
		assertTrue(a.getConstraints().contains(c1));
	}
	
	/**
	 * the star operator applied to an epsilon constraint returns the epsilon constraint
	 * @return the epsilon constraint
	 */
	@Test
	public void testStar() {
		
		AbstractConstraint<State> a=new EpsilonConstraint<State>();
		assertTrue(a.star() instanceof EpsilonConstraint);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnion0() {
		AbstractConstraint<State> a=new EpsilonConstraint<State>();
		a.union(null);
	}
	
	/**
	 * the union of an epsilon constraint and a lambda constraint is the lambda constraint
	 */
	@Test
	public void testUnion1() {
		AbstractConstraint<State> a=new EpsilonConstraint<State>();
		Predicate<State> p=new Predicate<State>(new State("s1"), "abb");
		assertTrue(p.union(a).equals(p));
	}
	/**
	 * the union of an epsilon constraint and an EmptyConstraint is the epsilon constraint
	 */
	public void testUnion2() {
		AbstractConstraint<State> a1=new EpsilonConstraint<State>();
		AbstractConstraint<State> a2=new EmptyConstraint<State>();
		
		assertTrue(a1.union(a2) instanceof EpsilonConstraint);
	}
	/**
	 *	the union of an epsilon constraint and an epsilon constraint is the epsilon constraint
	 */
	public void testUnion3() {
		AbstractConstraint<State> a1=new EpsilonConstraint<State>();
		AbstractConstraint<State> a2=new EpsilonConstraint<State>();
		
		assertTrue(a1.union(a2) instanceof EpsilonConstraint);
	}
	/**
	 *   the union of an epsilon constraint and an epsilon constraint is an or constraint that contains the epsilon constraint
	 *    and the predicate 
	 */
	public void testUnion4() {
		AbstractConstraint<State> a1=new EpsilonConstraint<State>();
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		
		assertTrue(a1.union(p2) instanceof OrConstraint);
		OrConstraint<State> a=(OrConstraint<State>) a1.union(p2);
		
		assertTrue(a.getConstraints().contains(a1));
		assertTrue(a.getConstraints().contains(p2));
		assertTrue(a.getConstraints().size()==2);
	}
	/**
	 *	the union of an epsilon constraint and an epsilon constraint is an or constraint that contains the epsilon constraint and the predicate
	 */
	public void testUnion5() {
		AbstractConstraint<State> a1=new EpsilonConstraint<State>();
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		Predicate<State> p3=new Predicate<State>(new State("s3"), "cdd");
		
		OrConstraint<State> c1=new OrConstraint<State>(p2, p3);
		assertTrue(a1.union(c1) instanceof OrConstraint);
		OrConstraint<State> a=(OrConstraint<State>) a1.union(c1);
		
		assertTrue(a.getConstraints().contains(a1));
		assertTrue(a.getConstraints().contains(c1));
	}
	/**
	 *	the union of an epsilon constraint and an and constraint is an or constraint that contains the epsilon constraint and the and constraint 
	 */
	public void testUnion6() {
		AbstractConstraint<State> a1=new EpsilonConstraint<State>();
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		Predicate<State> p3=new Predicate<State>(new State("s3"), "cdd");
		
		AndConstraint<State> c1=new AndConstraint<State>(p2, p3);
		assertTrue(a1.union(c1) instanceof OrConstraint);
		OrConstraint<State> a=(OrConstraint<State>) a1.union(c1);
		
		assertTrue(a.getConstraints().contains(a1));
		assertTrue(a.getConstraints().contains(c1));
	}
	
	@Test
	public void testToString() {
		
		AbstractConstraint<State> a=new EpsilonConstraint<State>();
		assertTrue(a.toString().equals("ε"));
	}

}
