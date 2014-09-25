package it.polimi.modelchecker.brzozowski.predicates;

import static org.junit.Assert.assertTrue;
import it.polimi.model.State;

import java.util.ArrayList;
import java.util.List;

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
		
		assertTrue(a.getPredicates().contains(p1));
		assertTrue(a.getPredicates().contains(p2));
		assertTrue(a.getPredicates().size()==2);
	}
	/**
	 *	the concatenation of an epsilon constraint and an and constraint predicate is a new and constraint that contains the epsilon constraint
	 *	and the original and constraint
	 */
	public void testConcat5() {
		
			
		Predicate<State> p1=new Predicate<State>(new State("s1"), "abb");
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		
		List<AbstractPredicate<State>> l=new ArrayList<AbstractPredicate<State>>();
		l.add(p1);
		l.add(p2);
		AndPredicate<State> c1=new AndPredicate<State>(l);
		EpsilonPredicate<State> p3=new EpsilonPredicate<State>();
		
		
		assertTrue(p3.concatenate(c1) instanceof AndPredicate);
		AndPredicate<State> a=(AndPredicate<State>) p3.concatenate(c1);
		
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
		Predicate<State> p1=new Predicate<State>(new State("s1"), "abb");
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");


		OrPredicate<State> c1=new OrPredicate<State>(p1,p2);
		EpsilonPredicate<State> p3=new EpsilonPredicate<State>();
		
		assertTrue(p3.concatenate(c1) instanceof AndPredicate);
		AndPredicate<State> a=(AndPredicate<State>) p3.concatenate(c1);
		
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
		assertTrue(p.union(a).equals(new OrPredicate<State>(p, a)));
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
		
		assertTrue(a1.union(p2) instanceof OrPredicate);
		OrPredicate<State> a=(OrPredicate<State>) a1.union(p2);
		
		assertTrue(a.getPredicates().contains(a1));
		assertTrue(a.getPredicates().contains(p2));
		assertTrue(a.getPredicates().size()==2);
	}
	/**
	 *	the union of an epsilon constraint and an epsilon constraint is an or constraint that contains the epsilon constraint and the predicate
	 */
	public void testUnion5() {
		AbstractPredicate<State> a1=new EpsilonPredicate<State>();
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		Predicate<State> p3=new Predicate<State>(new State("s3"), "cdd");
		
		OrPredicate<State> c1=new OrPredicate<State>(p2, p3);
		assertTrue(a1.union(c1) instanceof OrPredicate);
		OrPredicate<State> a=(OrPredicate<State>) a1.union(c1);
		
		assertTrue(a.getPredicates().contains(a1));
		assertTrue(a.getPredicates().contains(c1));
	}
	/**
	 *	the union of an epsilon constraint and an and constraint is an or constraint that contains the epsilon constraint and the and constraint 
	 */
	public void testUnion6() {
		AbstractPredicate<State> a1=new EpsilonPredicate<State>();
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		Predicate<State> p3=new Predicate<State>(new State("s3"), "cdd");
		
		AndPredicate<State> c1=new AndPredicate<State>(p2, p3);
		assertTrue(a1.union(c1) instanceof OrPredicate);
		OrPredicate<State> a=(OrPredicate<State>) a1.union(c1);
		
		assertTrue(a.getPredicates().contains(a1));
		assertTrue(a.getPredicates().contains(c1));
	}
	
	@Test
	public void testToString() {
		
		AbstractPredicate<State> a=new EpsilonPredicate<State>();
		assertTrue(a.toString().equals("ε"));
	}

}
