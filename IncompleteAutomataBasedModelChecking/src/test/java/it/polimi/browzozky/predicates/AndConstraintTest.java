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

public class AndConstraintTest {
	/**
	 * if a is an empty constraint the empty constraint is returned
	 */
	@Test
	public void testConcat1() {
		AbstractConstraint<State> a=new EmptyConstraint<State>();
		AbstractConstraint<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> or=new AndConstraint<State>(p1, p2);
		assertTrue(or.concatenate(a).equals(a));
	}
	/**
	 * if a is an lambda constraint the and constraint is returned
	 */
	@Test
	public void testConcat2() {
		AbstractConstraint<State> a=new LambdaConstraint<State>();
		AbstractConstraint<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> or=new AndConstraint<State>(p1, p2);
		assertTrue(or.concatenate(a).equals(or));
	}
	/**
	 * if a is an EpsilonConstraint the concatenation of the and constraint and EpsilonConstraint is returned
	 */
	@Test
	public void testConcat3() {
		AbstractConstraint<State> a=new EpsilonConstraint<State>();
		AbstractConstraint<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> p2=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> or=new AndConstraint<State>(p1, p2);
		assertTrue(or.concatenate(a) instanceof AndConstraint);
		assertTrue(((AndConstraint<State>)or.concatenate(a)).getConstraints().contains(a));
		assertTrue(((AndConstraint<State>)or.concatenate(a)).getConstraints().contains(p1));
		assertTrue(((AndConstraint<State>)or.concatenate(a)).getConstraints().contains(p2));
		assertTrue(!((AndConstraint<State>)or.concatenate(a)).getConstraints().contains(or));
	}
	/**
	 *	if a is a Predicate and the last element p of this constraint is a Predicate that has the same state of a,
   	 * the regular expression of p is modified and concatenated to the one of the predicate a
	 */
	@Test
	public void testConcat4() {
		AbstractConstraint<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> p2=new Predicate<State>(new State("s2"),"aab");
		AbstractConstraint<State> a=new Predicate<State>(new State("s2"),"aac");
		
		AbstractConstraint<State> p=new AndConstraint<State>(p1, p2);
		assertTrue(p.concatenate(a) instanceof AndConstraint);
		assertTrue(((AndConstraint<State>)p.concatenate(a)).getConstraints().contains(p1));
		assertTrue(((AndConstraint<State>)p.concatenate(a)).getConstraints().contains(new Predicate<State>(new State("s2"),"aabaac")));
	}
	/**
	 *	if a is a Predicate and the last element p of this constraint is a Predicate that has a different state of a, 
  	 *	a new and constraint that contains the original constrained and the predicate a is generated
  	 */
  	@Test
	public void testConcat5() {
		AbstractConstraint<State> a=new Predicate<State>(new State("s2"),"aac");
		AbstractConstraint<State> p1=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> p2=new Predicate<State>(new State("s3"),"aab");
		AbstractConstraint<State> or=new AndConstraint<State>(p1, p2);
		assertTrue(or.concatenate(a) instanceof AndConstraint);
		assertTrue(((AndConstraint<State>)or.concatenate(a)).getConstraints().contains(a));
		assertTrue(((AndConstraint<State>)or.concatenate(a)).getConstraints().contains(p1));
		assertTrue(((AndConstraint<State>)or.concatenate(a)).getConstraints().contains(p2));
	}
	/**
	 *	if a is an or constraint a new and constraint that contains the constraint of this and constraint and the or constraint is generated
	 */
	@Test
	public void testConcat6() {
		AbstractConstraint<State> p0=new Predicate<State>(new State("s2"),"aab");
		AbstractConstraint<State> p1=new Predicate<State>(new State("s2"),"aab");
		AbstractConstraint<State> p=new AndConstraint<State>(p0,p1);
		AbstractConstraint<State> a1=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> a2=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> a=new OrConstraint<State>(a1, a2);
		assertTrue(p.concatenate(a) instanceof AndConstraint);
		assertTrue(((AndConstraint<State>)p.concatenate(a)).getConstraints().contains(p0));
		assertTrue(((AndConstraint<State>)p.concatenate(a)).getConstraints().contains(p1));
		assertTrue(((AndConstraint<State>)p.concatenate(a)).getConstraints().contains(a));
	}
	/**
	 *  if a is an and constraint and  the last predicate of this constraint and the first predicate of a have the same state, 
   	 * 	their regular expressions are merged
	 */
	@Test
	public void testConcat7() {
		AbstractConstraint<State> p0=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> p1=new Predicate<State>(new State("s2"),"aab");
		AbstractConstraint<State> p=new AndConstraint<State>(p0,p1);
		Predicate<State> a1=new Predicate<State>(new State("s2"), "zzz");
		Predicate<State> a2=new Predicate<State>(new State("s3"), "cdd");
		AndConstraint<State> a=new AndConstraint<State>(a1,a2);
		assertTrue(p.concatenate(a) instanceof AndConstraint);
		assertTrue(((AndConstraint<State>)p.concatenate(a)).getConstraints().contains(p0));
		assertTrue(((AndConstraint<State>)p.concatenate(a)).getConstraints().contains(new Predicate<State>(new State("s2"), "aabzzz")));
		assertTrue(((AndConstraint<State>)p.concatenate(a)).getConstraints().contains(a2));
	}
	/**
	 * if a is an and constraint and  the last predicate of this constraint and the first predicate of do not have 
   	 * 	the same state, a new and constraint that contains all of the constraints of these two and constraints is generated.
	 */
	@Test
	public void testConcat8() {
		AbstractConstraint<State> p0=new Predicate<State>(new State("s1"),"aab");
		AbstractConstraint<State> p1=new Predicate<State>(new State("s2"),"aab");
		AbstractConstraint<State> p=new AndConstraint<State>(p0,p1);
		Predicate<State> a1=new Predicate<State>(new State("s3"), "abb");
		Predicate<State> a2=new Predicate<State>(new State("s4"), "cdd");
		AndConstraint<State> a=new AndConstraint<State>(a1,a2);
		assertTrue(p.concatenate(a) instanceof AndConstraint);
		assertTrue(((AndConstraint<State>)p.concatenate(a)).getConstraints().contains(p0));
		assertTrue(((AndConstraint<State>)p.concatenate(a)).getConstraints().contains(p1));
		assertTrue(((AndConstraint<State>)p.concatenate(a)).getConstraints().contains(a1));
		assertTrue(((AndConstraint<State>)p.concatenate(a)).getConstraints().contains(a2));
	}
	/**
	 * the star operator when applied to an and constraint does not produce any effect
	 * @return the and constraint
	 */
	@Test
	public void testStar() {
		
		Predicate<State> p1=new Predicate<State>(new State("s1"), "abb");
		Predicate<State> p2=new Predicate<State>(new State("s2"), "cdd");
		AndConstraint<State> c1=new AndConstraint<State>(p1,p2);
		AbstractConstraint<State> c2=c1.star();
		assertTrue(c2 instanceof AndConstraint);
		assertTrue(((AndConstraint<State>)c2).getConstraints().contains(p1));
		assertTrue(((AndConstraint<State>)c2).getConstraints().contains(p2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnion0() {
		AbstractConstraint<State> a=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> b=new Predicate<State>(new State("s1"), "aab");
		AndConstraint<State> orConstraint=new AndConstraint<State>(a,b);
		orConstraint.union(null);
	}
	
	/**
	 * the union of an and constraint and an empty constraint returns the or constraint
	 */
	@Test
	public void testUnion1() {
		AbstractConstraint<State> a=new EmptyConstraint<State>();
		AbstractConstraint<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p=new AndConstraint<State>(a1,b1);
		assertTrue(p.union(a).equals(p));
	}
	/**
	 * the union of an or constraint and a LambdaConstraint is a new andConstraint that contains the and constraint and lambda
	 */
	public void testUnion2() {
		AbstractConstraint<State> a=new LambdaConstraint<State>();
		AbstractConstraint<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p=new AndConstraint<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof AndConstraint);
		AndConstraint<State> o=(AndConstraint<State>) p.union(a);
		assertTrue(o.getConstraints().contains(p));
		assertTrue(o.getConstraints().contains(a));
	}
	/**
	 * the union of an and constraint and an EpsilonConstrain is a new andConstraint that contains the and constraint and the EpsilonConstrain
	 */
	public void testUnion3() {
		AbstractConstraint<State> a=new EpsilonConstraint<State>();
		AbstractConstraint<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p=new AndConstraint<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof AndConstraint);
		AndConstraint<State> o=(AndConstraint<State>) p.union(a);
		assertTrue(o.getConstraints().contains(p));
		assertTrue(o.getConstraints().contains(a));
	}
	/**
	 * the union of an and constraint and a Predicate is a new andConstraint that contains the or constraint and the Predicate
	 */
	public void testUnion4() {
		AbstractConstraint<State> a=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p=new AndConstraint<State>(a1,b1);
		
		assertTrue(p.union(a) instanceof AndConstraint);
		AndConstraint<State> o=(AndConstraint<State>) p.union(a);
		assertTrue(o.getConstraints().contains(p));
		assertTrue(o.getConstraints().contains(a));
	}
	/**
	 *	the union of an and constraint and an andConstraint is a new orConstraint that contains the two and constraints
	 */
	public void testUnion5() {
		
		AbstractConstraint<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> a2=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p2=new AndConstraint<State>(a1,a2);
		
		AbstractConstraint<State> b1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> b2=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p1=new AndConstraint<State>(b1,b2);
		
		
		assertTrue(p1.union(p2) instanceof AndConstraint);
		AndConstraint<State> o=(AndConstraint<State>) p1.union(p2);
		assertTrue(o.getConstraints().contains(p1));
		assertTrue(o.getConstraints().contains(p2));
	}
	/**
	 * the union of an and constraint and an andConstraint is a new orConstraint that contains the and constraint and the andConstraint
	 */
	public void testUnion6() {

		AbstractConstraint<State> a1=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> a2=new Predicate<State>(new State("s1"), "aab");
		AbstractConstraint<State> p2=new AndConstraint<State>(a1,a2);
		
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
		AbstractConstraint<State> p=new AndConstraint<State>(a1,a2);
		assertTrue(p.toString().equals("(<s1,aab>^(<s1,aac>))"));
	}

}
