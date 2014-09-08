package it.polimi.modelchecker.brzozowski.predicates;

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

public class AndConstraintTest {
	/**
	 * if a is an empty constraint the empty constraint is returned
	 */
	@Test
	public void testConcat1() {
		AbstractPredicate<State> a=new EmptyPredicate<State>();
		AbstractPredicate<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> or=new AndPredicate<State>(p1, p2);
		assertTrue(or.concatenate(a).equals(a));
	}
	/**
	 * if a is an lambda constraint the and constraint is returned
	 */
	@Test
	public void testConcat2() {
		AbstractPredicate<State> a=new LambdaPredicate<State>();
		AbstractPredicate<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> or=new AndPredicate<State>(p1, p2);
		assertTrue(or.concatenate(a).equals(or));
	}
	/**
	 * if a is an EpsilonConstraint the concatenation of the and constraint and EpsilonConstraint is returned
	 */
	@Test
	public void testConcat3() {
		AbstractPredicate<State> a=new EpsilonPredicate<State>();
		AbstractPredicate<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> or=new AndPredicate<State>(p1, p2);
		assertTrue(or.concatenate(a) instanceof AndPredicate);
		assertTrue(((AndPredicate<State>)or.concatenate(a)).getConstraints().contains(a));
		assertTrue(((AndPredicate<State>)or.concatenate(a)).getConstraints().contains(p1));
		assertTrue(((AndPredicate<State>)or.concatenate(a)).getConstraints().contains(p2));
		assertTrue(!((AndPredicate<State>)or.concatenate(a)).getConstraints().contains(or));
	}
	/**
	 *	if a is a Predicate and the last element p of this constraint is a Predicate that has the same state of a,
   	 * the regular expression of p is modified and concatenated to the one of the predicate a
	 */
	@Test
	public void testConcat4() {
		AbstractPredicate<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> p2=new Predicate<State>(new State("s2"),"aab");
		AbstractPredicate<State> a=new Predicate<State>(new State("s2"),"aac");
		
		AbstractPredicate<State> p=new AndPredicate<State>(p1, p2);
		assertTrue(p.concatenate(a) instanceof AndPredicate);
		assertTrue(((AndPredicate<State>)p.concatenate(a)).getConstraints().contains(p1));
		assertTrue(((AndPredicate<State>)p.concatenate(a)).getConstraints().contains(new Predicate<State>(new State("s2"),"aabaac")));
	}
	/**
	 *	if a is a Predicate and the last element p of this constraint is a Predicate that has a different state of a, 
  	 *	a new and constraint that contains the original constrained and the predicate a is generated
  	 */
  	@Test
	public void testConcat5() {
		AbstractPredicate<State> a=new Predicate<State>(new State("s2"),"aac");
		AbstractPredicate<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> p2=new Predicate<State>(new State("s3"),"aab");
		AbstractPredicate<State> or=new AndPredicate<State>(p1, p2);
		assertTrue(or.concatenate(a) instanceof AndPredicate);
		assertTrue(((AndPredicate<State>)or.concatenate(a)).getConstraints().contains(a));
		assertTrue(((AndPredicate<State>)or.concatenate(a)).getConstraints().contains(p1));
		assertTrue(((AndPredicate<State>)or.concatenate(a)).getConstraints().contains(p2));
	}
	/**
	 *	if a is an or constraint a new and constraint that contains the constraint of this and constraint and the or constraint is generated
	 */
	@Test
	public void testConcat6() {
		AbstractPredicate<State> p0=new Predicate<State>(new State("s2"),"aab");
		AbstractPredicate<State> p1=new Predicate<State>(new State("s2"),"aab");
		AbstractPredicate<State> p=new AndPredicate<State>(p0,p1);
		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> a2=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> a=new OrConstraint<State>(a1, a2);
		assertTrue(p.concatenate(a) instanceof AndPredicate);
		assertTrue(((AndPredicate<State>)p.concatenate(a)).getConstraints().contains(p0));
		assertTrue(((AndPredicate<State>)p.concatenate(a)).getConstraints().contains(p1));
		assertTrue(((AndPredicate<State>)p.concatenate(a)).getConstraints().contains(a));
	}
	/**
	 *  if a is an and constraint and  the last predicate of this constraint and the first predicate of a have the same state, 
   	 * 	their regular expressions are merged
	 */
	@Test
	public void testConcat7() {
		AbstractPredicate<State> p0=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> p1=new Predicate<State>(new State("s2"),"aab");
		AbstractPredicate<State> p=new AndPredicate<State>(p0,p1);
		Predicate<State> a1=new Predicate<State>(new State("s2"), "zzz");
		Predicate<State> a2=new Predicate<State>(new State("s3"), "cdd");
		AndPredicate<State> a=new AndPredicate<State>(a1,a2);
		assertTrue(p.concatenate(a) instanceof AndPredicate);
		assertTrue(((AndPredicate<State>)p.concatenate(a)).getConstraints().contains(p0));
		assertTrue(((AndPredicate<State>)p.concatenate(a)).getConstraints().contains(new Predicate<State>(new State("s2"), "aabzzz")));
		assertTrue(((AndPredicate<State>)p.concatenate(a)).getConstraints().contains(a2));
	}
	/**
	 * if a is an and constraint and  the last predicate of this constraint and the first predicate of do not have 
   	 * 	the same state, a new and constraint that contains all of the constraints of these two and constraints is generated.
	 */
	@Test
	public void testConcat8() {
		AbstractPredicate<State> p0=new Predicate<State>(new State("s1"),"aab");
		AbstractPredicate<State> p1=new Predicate<State>(new State("s2"),"aab");
		AbstractPredicate<State> p=new AndPredicate<State>(p0,p1);
		Predicate<State> a1=new Predicate<State>(new State("s3"), "abb");
		Predicate<State> a2=new Predicate<State>(new State("s4"), "cdd");
		AndPredicate<State> a=new AndPredicate<State>(a1,a2);
		assertTrue(p.concatenate(a) instanceof AndPredicate);
		assertTrue(((AndPredicate<State>)p.concatenate(a)).getConstraints().contains(p0));
		assertTrue(((AndPredicate<State>)p.concatenate(a)).getConstraints().contains(p1));
		assertTrue(((AndPredicate<State>)p.concatenate(a)).getConstraints().contains(a1));
		assertTrue(((AndPredicate<State>)p.concatenate(a)).getConstraints().contains(a2));
	}
	/**
	 * the star operator when applied to an and constraint does not produce any effect
	 * @return the and constraint
	 */
	@Test
	public void testStar() {
		
		Predicate<State> p1=new Predicate<State>(new State("s1"), "abb");
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		AndPredicate<State> c1=new AndPredicate<State>(p1,p2);
		AbstractPredicate<State> c2=c1.star();
		assertTrue(c2 instanceof AndPredicate);
		assertTrue(((AndPredicate<State>)c2).getConstraints().contains(p1));
		assertTrue(((AndPredicate<State>)c2).getConstraints().contains(p2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnion0() {
		AbstractPredicate<State> a=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> b=new Predicate<State>(new State("s1"), "aab");
		AndPredicate<State> orConstraint=new AndPredicate<State>(a,b);
		orConstraint.union(null);
	}
	
	/**
	 * the union of an and constraint and an empty constraint returns the or constraint
	 */
	@Test
	public void testUnion1() {
		AbstractPredicate<State> a=new EmptyPredicate<State>();
		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p=new AndPredicate<State>(a1,b1);
		assertTrue(p.union(a).equals(p));
	}
	/**
	 * the union of an or constraint and a LambdaConstraint is a new andConstraint that contains the and constraint and lambda
	 */
	public void testUnion2() {
		AbstractPredicate<State> a=new LambdaPredicate<State>();
		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p=new AndPredicate<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof AndPredicate);
		AndPredicate<State> o=(AndPredicate<State>) p.union(a);
		assertTrue(o.getConstraints().contains(p));
		assertTrue(o.getConstraints().contains(a));
	}
	/**
	 * the union of an and constraint and an EpsilonConstrain is a new andConstraint that contains the and constraint and the EpsilonConstrain
	 */
	public void testUnion3() {
		AbstractPredicate<State> a=new EpsilonPredicate<State>();
		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p=new AndPredicate<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof AndPredicate);
		AndPredicate<State> o=(AndPredicate<State>) p.union(a);
		assertTrue(o.getConstraints().contains(p));
		assertTrue(o.getConstraints().contains(a));
	}
	/**
	 * the union of an and constraint and a Predicate is a new andConstraint that contains the or constraint and the Predicate
	 */
	public void testUnion4() {
		AbstractPredicate<State> a=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p=new AndPredicate<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof AndPredicate);
		AndPredicate<State> o=(AndPredicate<State>) p.union(a);
		assertTrue(o.getConstraints().contains(p));
		assertTrue(o.getConstraints().contains(a));
	}
	/**
	 *	the union of an and constraint and an andConstraint is a new orConstraint that contains the two and constraints
	 */
	public void testUnion5() {
		
		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> a2=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p2=new AndPredicate<State>(a1,a2);
		
		AbstractPredicate<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> b2=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p1=new AndPredicate<State>(b1,b2);
		
		
		assertTrue(p1.union(p2) instanceof AndPredicate);
		AndPredicate<State> o=(AndPredicate<State>) p1.union(p2);
		assertTrue(o.getConstraints().contains(p1));
		assertTrue(o.getConstraints().contains(p2));
	}
	/**
	 * the union of an and constraint and an andConstraint is a new orConstraint that contains the and constraint and the andConstraint
	 */
	public void testUnion6() {

		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> a2=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p2=new AndPredicate<State>(a1,a2);
		
		AbstractPredicate<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> b2=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> p1=new AndPredicate<State>(b1,b2);
		
		
		assertTrue(p1.union(p2) instanceof OrConstraint);
		OrConstraint<State> o=(OrConstraint<State>) p1.union(p2);
		assertTrue(o.getConstraints().contains(p1));
		assertTrue(o.getConstraints().contains(p2));
	}
	
	@Test
	public void testToString() {
		
		AbstractPredicate<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractPredicate<State> a2=new Predicate<State>(new State("s1"), "aac");
		AbstractPredicate<State> p=new AndPredicate<State>(a1,a2);
		assertTrue(p.toString().equals("(<s1,aab>^(<s1,aac>))"));
	}

}
