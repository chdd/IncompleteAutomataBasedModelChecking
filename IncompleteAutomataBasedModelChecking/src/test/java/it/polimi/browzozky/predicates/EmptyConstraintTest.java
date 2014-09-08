package it.polimi.browzozky.predicates;

import static org.junit.Assert.*;
import it.polimi.browzozky.predicates.types.EmptyConstraint;
import it.polimi.browzozky.predicates.types.LambdaConstraint;
import it.polimi.browzozky.predicates.types.Predicate;
import it.polimi.model.State;

import org.junit.Test;

public class EmptyConstraintTest {

	@Test
	public void testConcat0() {
		
		AbstractConstraint<State> a=new EmptyConstraint<State>();
		Predicate<State> p=new Predicate<State>(new State("s1"), "abb");
		assertTrue(a.concatenate(p) instanceof EmptyConstraint);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testConcat1() {
		
		AbstractConstraint<State> a=new EmptyConstraint<State>();
		a.concatenate(null);
	}
	@Test
	public void testStar() {
		
		AbstractConstraint<State> a=new EmptyConstraint<State>();
		assertTrue(a.star() instanceof LambdaConstraint);
	}
	@Test
	public void testUnion0() {
		
		AbstractConstraint<State> a=new EmptyConstraint<State>();
		Predicate<State> p=new Predicate<State>(new State("s1"), "abb");
		assertTrue(a.union(p).equals(p));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testUnion1() {
		
		AbstractConstraint<State> a=new EmptyConstraint<State>();
		a.union(null);
	}
	@Test
	public void testToString() {
		
		AbstractConstraint<State> a=new EmptyConstraint<State>();
		assertTrue(a.toString().equals("∅"));
	}
}
