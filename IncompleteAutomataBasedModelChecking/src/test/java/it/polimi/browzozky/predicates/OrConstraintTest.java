package it.polimi.browzozky.predicates;

import static org.junit.Assert.*;
import it.polimi.model.State;
import it.polimi.modelchecker.brzozowski.predicates.AbstractConstraint;
import it.polimi.modelchecker.brzozowski.predicates.AndConstraint;
import it.polimi.modelchecker.brzozowski.predicates.EmptyConstraint;
import it.polimi.modelchecker.brzozowski.predicates.EpsilonConstraint;
import it.polimi.modelchecker.brzozowski.predicates.LambdaConstraint;
import it.polimi.modelchecker.brzozowski.predicates.OrConstraint;
import it.polimi.modelchecker.brzozowski.predicates.Predicate;

import org.junit.Test;

public class OrConstraintTest {

	/**
	 * if a is an empty constraint the empty constraint is returned
	 */
	@Test
	public void testConcat1() {
		AbstractConstraint<State> a=new EmptyConstraint<State>();
		AbstractConstraint<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> or=new OrConstraint<State>(p1, p2);
		assertTrue(or.concatenate(a).equals(a));
	}
	/**
	 * if a is an lambda constraint the or constraint is returned
	 */
	@Test
	public void testConcat2() {
		AbstractConstraint<State> a=new LambdaConstraint<State>();
		AbstractConstraint<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> or=new OrConstraint<State>(p1, p2);
		assertTrue(or.concatenate(a).equals(or));
	}
	/**
	 * if a is an EpsilonConstraint the concatenation of the or constraint and EpsilonConstraint is returned
	 */
	@Test
	public void testConcat3() {
		AbstractConstraint<State> a=new EpsilonConstraint<State>();
		AbstractConstraint<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> or=new OrConstraint<State>(p1, p2);
		assertTrue(or.concatenate(a) instanceof AndConstraint);
		assertTrue(((AndConstraint<State>)or.concatenate(a)).getConstraints().contains(a));
		assertTrue(((AndConstraint<State>)or.concatenate(a)).getConstraints().contains(or));
	}
	/**
	 *	if a is a Predicate the concatenation of the or constraint and Predicate is returned
	 */
	@Test
	public void testConcat4() {
		AbstractConstraint<State> a=new Predicate<State>(new State("s2"),"aab");
		AbstractConstraint<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> or=new OrConstraint<State>(p1, p2);
		assertTrue(or.concatenate(a) instanceof AndConstraint);
		assertTrue(((AndConstraint<State>)or.concatenate(a)).getConstraints().contains(a));
		assertTrue(((AndConstraint<State>)or.concatenate(a)).getConstraints().contains(or));
	}
	/**
	 *	if a is an AndConstraint the concatenation of the or constraint and the AndConstraint is returned
	 */
	@Test
	public void testConcat5() {
		AbstractConstraint<State> a0=new Predicate<State>(new State("s2"),"aab");
		AbstractConstraint<State> a1=new Predicate<State>(new State("s2"),"aab");
		AbstractConstraint<State> a2=new AndConstraint<State>(a0,a1);
		AbstractConstraint<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> or=new OrConstraint<State>(p1, p2);
		assertTrue(or.concatenate(a2) instanceof AndConstraint);
		assertTrue(((AndConstraint<State>)or.concatenate(a2)).getConstraints().containsAll(((AndConstraint<State>)a2).getConstraints()));
		assertTrue(((AndConstraint<State>)or.concatenate(a2)).getConstraints().contains(or));
	}
	/**
	 *  if a is an OrConstraint the concatenation of the or constraint and the OrConstraint is returned
	 */
	@Test
	public void testConcat6() {
		AbstractConstraint<State> a0=new Predicate<State>(new State("s2"),"aab");
		AbstractConstraint<State> a1=new Predicate<State>(new State("s2"),"aab");
		AbstractConstraint<State> or=new OrConstraint<State>(a0,a1);
		Predicate<State> p1=new Predicate<State>(new State("s1"), "abb");
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		OrConstraint<State> c1=new OrConstraint<State>(p1,p2);
		assertTrue(or.concatenate(c1) instanceof AndConstraint);
		assertTrue(((AndConstraint<State>)or.concatenate(c1)).getConstraints().contains(c1));
		assertTrue(((AndConstraint<State>)or.concatenate(c1)).getConstraints().contains(or));
	
	}
	/**
	 * the star operator when applied to an or constraint does not produce any effect
	 * @return the or constraint
	 */
	@Test
	public void testStar() {
		
		Predicate<State> p1=new Predicate<State>(new State("s1"), "abb");
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		OrConstraint<State> c1=new OrConstraint<State>(p1,p2);
		AbstractConstraint<State> c2=c1.star();
		assertTrue(c2 instanceof OrConstraint);
		assertTrue(((OrConstraint<State>)c2).getConstraints().contains(p1));
		assertTrue(((OrConstraint<State>)c2).getConstraints().contains(p2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnion0() {
		AbstractConstraint<State> a=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> b=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> orConstraint=new OrConstraint<>(a,b);
		orConstraint.union(null);
	}
	
	/**
	 * the union of an or constraint and an empty constraint returns the or constraint
	 */
	@Test
	public void testUnion1() {
		AbstractConstraint<State> a=new EmptyConstraint<State>();
		AbstractConstraint<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p=new OrConstraint<State>(a1,b1);
		assertTrue(p.union(a).equals(p));
	}
	/**
	 * the union of an or constraint and a LambdaConstraint is a new orConstraint that contains the or constraint and lambda
	 */
	public void testUnion2() {
		AbstractConstraint<State> a=new LambdaConstraint<State>();
		AbstractConstraint<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p=new OrConstraint<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof OrConstraint);
		OrConstraint<State> o=(OrConstraint<State>) p.union(a);
		assertTrue(o.getConstraints().contains(p));
		assertTrue(o.getConstraints().contains(a));
	}
	/**
	 * the union of an or constraint and an EpsilonConstrain is a new orConstraint that contains the or constraint and the EpsilonConstrain
	 */
	public void testUnion3() {
		AbstractConstraint<State> a=new EpsilonConstraint<State>();
		AbstractConstraint<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p=new OrConstraint<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof OrConstraint);
		OrConstraint<State> o=(OrConstraint<State>) p.union(a);
		assertTrue(o.getConstraints().contains(p));
		assertTrue(o.getConstraints().contains(a));
	}
	/**
	 * the union of an or constraint and a Predicate is a new orConstraint that contains the or constraint and the Predicate
	 */
	public void testUnion4() {
		AbstractConstraint<State> a=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p=new OrConstraint<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof OrConstraint);
		OrConstraint<State> o=(OrConstraint<State>) p.union(a);
		assertTrue(o.getConstraints().contains(p));
		assertTrue(o.getConstraints().contains(a));
	}
	/**
	 *	the union of an or constraint and an orConstraint is a new orConstraint that contains the two or constraints
	 */
	public void testUnion5() {
		
		AbstractConstraint<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> a2=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p2=new OrConstraint<State>(a1,a2);
		
		AbstractConstraint<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> b2=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p1=new OrConstraint<State>(b1,b2);
		
		
		assertTrue(p1.union(p2) instanceof OrConstraint);
		OrConstraint<State> o=(OrConstraint<State>) p1.union(p2);
		assertTrue(o.getConstraints().contains(p1));
		assertTrue(o.getConstraints().contains(p2));
	}
	/**
	 * the union of an or constraint and an andConstraint is a new orConstraint that contains the or constraint and the andConstraint
	 */
	public void testUnion6() {

		AbstractConstraint<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> a2=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p2=new OrConstraint<State>(a1,a2);
		
		AbstractConstraint<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> b2=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p1=new AndConstraint<State>(b1,b2);
		
		
		assertTrue(p1.union(p2) instanceof OrConstraint);
		OrConstraint<State> o=(OrConstraint<State>) p1.union(p2);
		assertTrue(o.getConstraints().contains(p1));
		assertTrue(o.getConstraints().contains(p2));
	}
	
	@Test
	public void testToString() {
		
		AbstractConstraint<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> a2=new Predicate<State>(new State("s1"), "aac");
		AbstractConstraint<State> p=new OrConstraint<State>(a1,a2);
		assertTrue(p.toString().equals("(<s1,aab>v(<s1,aac>))"));
	}
	
}
