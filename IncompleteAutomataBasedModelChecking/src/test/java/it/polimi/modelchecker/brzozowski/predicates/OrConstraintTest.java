package it.polimi.modelchecker.brzozowski.predicates;

import static org.junit.Assert.*;
import it.polimi.model.State;
import it.polimi.modelchecker.brzozowski.predicates.AbstractPredicate;
import it.polimi.modelchecker.brzozowski.predicates.AndPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EmptyPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EpsilonPredicate;
import it.polimi.modelchecker.brzozowski.predicates.LambdaPredicate;
import it.polimi.modelchecker.brzozowski.predicates.OrPredicate;
import it.polimi.modelchecker.brzozowski.predicates.Predicate;

import org.junit.Test;

public class OrConstraintTest {

	/**
	 * if a is an empty constraint the empty constraint is returned
	 */
	@Test
	public void testConcat1() {
		AbstractPredicate<State> a=new EmptyPredicate<State>();
		AbstractPredicate<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> or=new OrPredicate<State>(p1, p2);
		assertTrue(or.concatenate(a).equals(a));
	}
	/**
	 * if a is an lambda constraint the or constraint is returned
	 */
	@Test
	public void testConcat2() {
		AbstractPredicate<State> a=new LambdaPredicate<State>();
		AbstractPredicate<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> or=new OrPredicate<State>(p1, p2);
		assertTrue(or.concatenate(a).equals(or));
	}
	/**
	 * if a is an EpsilonConstraint the concatenation of the or constraint and EpsilonConstraint is returned
	 */
	@Test
	public void testConcat3() {
		AbstractPredicate<State> a=new EpsilonPredicate<State>();
		AbstractPredicate<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> or=new OrPredicate<State>(p1, p2);
		assertTrue(or.concatenate(a) instanceof AndPredicate);
		assertTrue(((AndPredicate<State>)or.concatenate(a)).getConstraints().contains(a));
		assertTrue(((AndPredicate<State>)or.concatenate(a)).getConstraints().contains(or));
	}
	/**
	 *	if a is a Predicate the concatenation of the or constraint and Predicate is returned
	 */
	@Test
	public void testConcat4() {
		AbstractPredicate<State> a=new Predicate<State>(new State("s2"),"aab");
		AbstractPredicate<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> or=new OrPredicate<State>(p1, p2);
		assertTrue(or.concatenate(a) instanceof AndPredicate);
		assertTrue(((AndPredicate<State>)or.concatenate(a)).getConstraints().contains(a));
		assertTrue(((AndPredicate<State>)or.concatenate(a)).getConstraints().contains(or));
	}
	/**
	 *	if a is an AndConstraint the concatenation of the or constraint and the AndConstraint is returned
	 */
	@Test
	public void testConcat5() {
		AbstractPredicate<State> a0=new Predicate<State>(new State("s2"),"aab");
		AbstractPredicate<State> a1=new Predicate<State>(new State("s2"),"aab");
		AbstractPredicate<State> a2=new AndPredicate<State>(a0,a1);
		AbstractPredicate<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> or=new OrPredicate<State>(p1, p2);
		assertTrue(or.concatenate(a2) instanceof AndPredicate);
		assertTrue(((AndPredicate<State>)or.concatenate(a2)).getConstraints().containsAll(((AndPredicate<State>)a2).getConstraints()));
		assertTrue(((AndPredicate<State>)or.concatenate(a2)).getConstraints().contains(or));
	}
	/**
	 *  if a is an OrConstraint the concatenation of the or constraint and the OrConstraint is returned
	 */
	@Test
	public void testConcat6() {
		AbstractPredicate<State> a0=new Predicate<State>(new State("s2"),"aab");
		AbstractPredicate<State> a1=new Predicate<State>(new State("s2"),"aab");
		AbstractPredicate<State> or=new OrPredicate<State>(a0,a1);
		Predicate<State> p1=new Predicate<State>(new State("s1"), "abb");
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		OrPredicate<State> c1=new OrPredicate<State>(p1,p2);
		assertTrue(or.concatenate(c1) instanceof AndPredicate);
		assertTrue(((AndPredicate<State>)or.concatenate(c1)).getConstraints().contains(c1));
		assertTrue(((AndPredicate<State>)or.concatenate(c1)).getConstraints().contains(or));
	
	}
	/**
	 * the star operator when applied to an or constraint does not produce any effect
	 * @return the or constraint
	 */
	@Test
	public void testStar() {
		
		Predicate<State> p1=new Predicate<State>(new State("s1"), "abb");
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		OrPredicate<State> c1=new OrPredicate<State>(p1,p2);
		AbstractPredicate<State> c2=c1.star();
		assertTrue(c2 instanceof OrPredicate);
		assertTrue(((OrPredicate<State>)c2).getConstraints().contains(p1));
		assertTrue(((OrPredicate<State>)c2).getConstraints().contains(p2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnion0() {
		AbstractPredicate<State> a=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> b=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> orConstraint=new OrPredicate<>(a,b);
		orConstraint.union(null);
	}
	
	/**
	 * the union of an or constraint and an empty constraint returns the or constraint
	 */
	@Test
	public void testUnion1() {
		AbstractPredicate<State> a=new EmptyPredicate<State>();
		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p=new OrPredicate<State>(a1,b1);
		assertTrue(p.union(a).equals(p));
	}
	/**
	 * the union of an or constraint and a LambdaConstraint is a new orConstraint that contains the or constraint and lambda
	 */
	public void testUnion2() {
		AbstractPredicate<State> a=new LambdaPredicate<State>();
		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p=new OrPredicate<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof OrPredicate);
		OrPredicate<State> o=(OrPredicate<State>) p.union(a);
		assertTrue(o.getConstraints().contains(p));
		assertTrue(o.getConstraints().contains(a));
	}
	/**
	 * the union of an or constraint and an EpsilonConstrain is a new orConstraint that contains the or constraint and the EpsilonConstrain
	 */
	public void testUnion3() {
		AbstractPredicate<State> a=new EpsilonPredicate<State>();
		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p=new OrPredicate<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof OrPredicate);
		OrPredicate<State> o=(OrPredicate<State>) p.union(a);
		assertTrue(o.getConstraints().contains(p));
		assertTrue(o.getConstraints().contains(a));
	}
	/**
	 * the union of an or constraint and a Predicate is a new orConstraint that contains the or constraint and the Predicate
	 */
	public void testUnion4() {
		AbstractPredicate<State> a=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p=new OrPredicate<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof OrPredicate);
		OrPredicate<State> o=(OrPredicate<State>) p.union(a);
		assertTrue(o.getConstraints().contains(p));
		assertTrue(o.getConstraints().contains(a));
	}
	/**
	 *	the union of an or constraint and an orConstraint is a new orConstraint that contains the two or constraints
	 */
	public void testUnion5() {
		
		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> a2=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p2=new OrPredicate<State>(a1,a2);
		
		AbstractPredicate<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> b2=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p1=new OrPredicate<State>(b1,b2);
		
		
		assertTrue(p1.union(p2) instanceof OrPredicate);
		OrPredicate<State> o=(OrPredicate<State>) p1.union(p2);
		assertTrue(o.getConstraints().contains(p1));
		assertTrue(o.getConstraints().contains(p2));
	}
	/**
	 * the union of an or constraint and an andConstraint is a new orConstraint that contains the or constraint and the andConstraint
	 */
	public void testUnion6() {

		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> a2=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p2=new OrPredicate<State>(a1,a2);
		
		AbstractPredicate<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> b2=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p1=new AndPredicate<State>(b1,b2);
		
		
		assertTrue(p1.union(p2) instanceof OrPredicate);
		OrPredicate<State> o=(OrPredicate<State>) p1.union(p2);
		assertTrue(o.getConstraints().contains(p1));
		assertTrue(o.getConstraints().contains(p2));
	}
	
	@Test
	public void testToString() {
		
		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> a2=new Predicate<State>(new State("s1"), "aac");
		AbstractPredicate<State> p=new OrPredicate<State>(a1,a2);
		assertTrue(p.toString().equals("(<s1,aab>v(<s1,aac>))"));
	}
	
}
