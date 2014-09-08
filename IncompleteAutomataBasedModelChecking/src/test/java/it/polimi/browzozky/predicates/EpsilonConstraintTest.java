package it.polimi.browzozky.predicates;

import static org.junit.Assert.*;
import it.polimi.model.State;
import it.polimi.modelchecker.brzozowski.predicates.AbstractPredicate;
import it.polimi.modelchecker.brzozowski.predicates.AndPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EmptyPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EpsilonPredicate;
import it.polimi.modelchecker.brzozowski.predicates.LambdaPredicate;
import it.polimi.modelchecker.brzozowski.predicates.OrConstraint;
import it.polimi.modelchecker.brzozowski.predicates.Predicate;

import org.junit.Test;

public class EpsilonConstraintTest {
	/**
	 * the concatenation of the epsilon constraint and the empty constraint is empty
	 */
	@Test
	public void testConcat1() {
		AbstractPredicate<State> a=new EmptyPredicate<State>();
		AbstractPredicate<State> p=new EpsilonPredicate<State>();
		assertTrue(p.concatenate(a).equals(a));
	}
	/**
	 * the concatenation of an epsilon constraint and the lambda constraint is equal to the epsilon constraint
	 */
	public void testConcat2() {
		AbstractPredicate<State> p=new EpsilonPredicate<State>();
		AbstractPredicate<State> a=new LambdaPredicate<State>();
		assertTrue(p.concatenate(a).equals(p));
	}
	/**
	 * the concatenation of an epsilon constraint and an epsilon constraint is equal to the epsilon constraint
	 */
	public void testConcat3() {
		AbstractPredicate<State> p1=new EpsilonPredicate<State>();
		AbstractPredicate<State> p2=new EpsilonPredicate<State>();
		
		assertTrue(p1.concatenate(p2) instanceof EpsilonPredicate);
	}
	/**
	 *	the concatenation of an epsilon constraint and a predicate is a new and constraint that contains the epsilon constraint and the predicate
	 */
	public void testConcat4() {
		AbstractPredicate<State> p1=new EpsilonPredicate<State>();
		Predicate<State> p2=new Predicate<State>(new State("s1"), "cdd");
		
		assertTrue(p1.concatenate(p2) instanceof AndPredicate);
		AndPredicate<State> a=(AndPredicate<State>) p1.concatenate(p2);
		
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
		AndPredicate<State> c1=new AndPredicate<State>();
		c1.addConstraint(p1);
		c1.addConstraint(p2);
		EpsilonPredicate<State> p3=new EpsilonPredicate<State>();
		
		
		assertTrue(p3.concatenate(c1) instanceof AndPredicate);
		AndPredicate<State> a=(AndPredicate<State>) p3.concatenate(c1);
		
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
		EpsilonPredicate<State> p3=new EpsilonPredicate<State>();
		
		assertTrue(p3.concatenate(c1) instanceof AndPredicate);
		AndPredicate<State> a=(AndPredicate<State>) p3.concatenate(c1);
		
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
		
		AbstractPredicate<State> a=new EpsilonPredicate<State>();
		assertTrue(a.star() instanceof EpsilonPredicate);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnion0() {
		AbstractPredicate<State> a=new EpsilonPredicate<State>();
		a.union(null);
	}
	
	/**
	 * the union of an epsilon constraint and a lambda constraint is the lambda constraint
	 */
	@Test
	public void testUnion1() {
		AbstractPredicate<State> a=new EpsilonPredicate<State>();
		Predicate<State> p=new Predicate<State>(new State("s1"), "abb");
		assertTrue(p.union(a).equals(p));
	}
	/**
	 * the union of an epsilon constraint and an EmptyConstraint is the epsilon constraint
	 */
	public void testUnion2() {
		AbstractPredicate<State> a1=new EpsilonPredicate<State>();
		AbstractPredicate<State> a2=new EmptyPredicate<State>();
		
		assertTrue(a1.union(a2) instanceof EpsilonPredicate);
	}
	/**
	 *	the union of an epsilon constraint and an epsilon constraint is the epsilon constraint
	 */
	public void testUnion3() {
		AbstractPredicate<State> a1=new EpsilonPredicate<State>();
		AbstractPredicate<State> a2=new EpsilonPredicate<State>();
		
		assertTrue(a1.union(a2) instanceof EpsilonPredicate);
	}
	/**
	 *   the union of an epsilon constraint and an epsilon constraint is an or constraint that contains the epsilon constraint
	 *    and the predicate 
	 */
	public void testUnion4() {
		AbstractPredicate<State> a1=new EpsilonPredicate<State>();
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
		AbstractPredicate<State> a1=new EpsilonPredicate<State>();
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
		AbstractPredicate<State> a1=new EpsilonPredicate<State>();
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		Predicate<State> p3=new Predicate<State>(new State("s3"), "cdd");
		
		AndPredicate<State> c1=new AndPredicate<State>(p2, p3);
		assertTrue(a1.union(c1) instanceof OrConstraint);
		OrConstraint<State> a=(OrConstraint<State>) a1.union(c1);
		
		assertTrue(a.getConstraints().contains(a1));
		assertTrue(a.getConstraints().contains(c1));
	}
	
	@Test
	public void testToString() {
		
		AbstractPredicate<State> a=new EpsilonPredicate<State>();
		assertTrue(a.toString().equals("ε"));
	}

}
