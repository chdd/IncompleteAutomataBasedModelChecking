package it.polimi.modelchecker.brzozowski.predicates;

import static org.junit.Assert.*;
import it.polimi.model.State;
import it.polimi.modelchecker.brzozowski.predicates.AbstractPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EmptyPredicate;
import it.polimi.modelchecker.brzozowski.predicates.LambdaPredicate;
import it.polimi.modelchecker.brzozowski.predicates.OrPredicate;
import it.polimi.modelchecker.brzozowski.predicates.Predicate;

import org.junit.Test;

public class LambdaConstraintTest {

	@Test
	public void testConcat0() {
		
		AbstractPredicate<State> a=new LambdaPredicate<State>();
		Predicate<State> p=new Predicate<State>(new State("s1"), "abb");
		assertTrue(a.concatenate(p).equals(p));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testConcat1() {
		
		AbstractPredicate<State> a=new LambdaPredicate<State>();
		a.concatenate(null);
	}
	@Test
	public void testStar() {
		
		AbstractPredicate<State> a=new LambdaPredicate<State>();
		assertTrue(a.star() instanceof LambdaPredicate);
	}
	@Test
	public void testUnion0() {
		
		AbstractPredicate<State> a=new LambdaPredicate<State>();
		Predicate<State> p=new Predicate<State>(new State("s1"), "abb");
		assertTrue(((OrPredicate<State>)a.union(p)).getPredicates().contains(a));
		assertTrue(((OrPredicate<State>)a.union(p)).getPredicates().contains(p));
	}
	@Test
	public void testUnion1() {
		
		AbstractPredicate<State> a=new LambdaPredicate<State>();
		AbstractPredicate<State> p=new EmptyPredicate<State>();
		assertTrue(a.union(p) instanceof LambdaPredicate);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testUnion2() {
		
		AbstractPredicate<State> a=new LambdaPredicate<State>();
		a.union(null);
	}
	@Test
	public void testToString() {
		
		AbstractPredicate<State> a=new LambdaPredicate<State>();
		assertTrue(a.toString().equals("λ"));
	}

}
