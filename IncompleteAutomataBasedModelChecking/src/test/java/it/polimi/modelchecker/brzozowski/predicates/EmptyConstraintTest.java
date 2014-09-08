package it.polimi.modelchecker.brzozowski.predicates;

import static org.junit.Assert.*;
import it.polimi.model.State;
import it.polimi.modelchecker.brzozowski.predicates.AbstractPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EmptyPredicate;
import it.polimi.modelchecker.brzozowski.predicates.LambdaPredicate;
import it.polimi.modelchecker.brzozowski.predicates.Predicate;

import org.junit.Test;

public class EmptyConstraintTest {

	@Test
	public void testConcat0() {
		
		AbstractPredicate<State> a=new EmptyPredicate<State>();
		Predicate<State> p=new Predicate<State>(new State("s1"), "abb");
		assertTrue(a.concatenate(p) instanceof EmptyPredicate);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testConcat1() {
		
		AbstractPredicate<State> a=new EmptyPredicate<State>();
		a.concatenate(null);
	}
	@Test
	public void testStar() {
		
		AbstractPredicate<State> a=new EmptyPredicate<State>();
		assertTrue(a.star() instanceof LambdaPredicate);
	}
	@Test
	public void testUnion0() {
		
		AbstractPredicate<State> a=new EmptyPredicate<State>();
		Predicate<State> p=new Predicate<State>(new State("s1"), "abb");
		assertTrue(a.union(p).equals(p));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testUnion1() {
		
		AbstractPredicate<State> a=new EmptyPredicate<State>();
		a.union(null);
	}
	@Test
	public void testToString() {
		
		AbstractPredicate<State> a=new EmptyPredicate<State>();
		assertTrue(a.toString().equals("∅"));
	}
}
