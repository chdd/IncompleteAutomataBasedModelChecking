package it.polimi.browzozky.predicates;

import static org.junit.Assert.*;
import it.polimi.browzozky.predicates.types.EmptyConstraint;
import it.polimi.browzozky.predicates.types.LambdaConstraint;
import it.polimi.browzozky.predicates.types.OrConstraint;
import it.polimi.browzozky.predicates.types.Predicate;
import it.polimi.model.State;

import org.junit.Test;

public class LambdaConstraintTest {

	@Test
	public void testConcat0() {
		
		AbstractConstraint<State> a=new LambdaConstraint<State>();
		Predicate<State> p=new Predicate<State>(new State("s1"), "abb");
		assertTrue(a.concatenate(p).equals(p));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testConcat1() {
		
		AbstractConstraint<State> a=new LambdaConstraint<State>();
		a.concatenate(null);
	}
	@Test
	public void testStar() {
		
		AbstractConstraint<State> a=new LambdaConstraint<State>();
		assertTrue(a.star() instanceof LambdaConstraint);
	}
	@Test
	public void testUnion0() {
		
		AbstractConstraint<State> a=new LambdaConstraint<State>();
		Predicate<State> p=new Predicate<State>(new State("s1"), "abb");
		assertTrue(((OrConstraint<State>)a.union(p)).getConstraints().contains(a));
		assertTrue(((OrConstraint<State>)a.union(p)).getConstraints().contains(p));
	}
	@Test
	public void testUnion1() {
		
		AbstractConstraint<State> a=new LambdaConstraint<State>();
		AbstractConstraint<State> p=new EmptyConstraint<State>();
		assertTrue(a.union(p) instanceof LambdaConstraint);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testUnion2() {
		
		AbstractConstraint<State> a=new LambdaConstraint<State>();
		a.union(null);
	}
	@Test
	public void testToString() {
		
		AbstractConstraint<State> a=new LambdaConstraint<State>();
		assertTrue(a.toString().equals("λ"));
	}

}
