package it.polimi.modelchecker.brzozowski.predicates;

import static org.junit.Assert.*;
import it.polimi.model.graph.State;
import it.polimi.modelchecker.brzozowski.propositions.AbstractProposition;
import it.polimi.modelchecker.brzozowski.propositions.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.LambdaProposition;
import it.polimi.modelchecker.brzozowski.propositions.OrProposition;
import it.polimi.modelchecker.brzozowski.propositions.AtomicProposition;

import org.junit.Test;

public class LambdaConstraintTest {

	@Test
	public void testConcat0() {
		
		AbstractProposition<State> a=new LambdaProposition<State>();
		AtomicProposition<State> p=new AtomicProposition<State>(new State("s1"), "abb");
		assertTrue(a.concatenate(p).equals(p));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testConcat1() {
		
		AbstractProposition<State> a=new LambdaProposition<State>();
		a.concatenate(null);
	}
	@Test
	public void testStar() {
		
		AbstractProposition<State> a=new LambdaProposition<State>();
		assertTrue(a.star() instanceof LambdaProposition);
	}
	@Test
	public void testUnion0() {
		
		AbstractProposition<State> a=new LambdaProposition<State>();
		AtomicProposition<State> p=new AtomicProposition<State>(new State("s1"), "abb");
		assertTrue(((OrProposition<State>)a.union(p)).getPredicates().contains(a));
		assertTrue(((OrProposition<State>)a.union(p)).getPredicates().contains(p));
	}
	@Test
	public void testUnion1() {
		
		AbstractProposition<State> a=new LambdaProposition<State>();
		AbstractProposition<State> p=new EmptyProposition<State>();
		assertTrue(a.union(p) instanceof LambdaProposition);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testUnion2() {
		
		AbstractProposition<State> a=new LambdaProposition<State>();
		a.union(null);
	}
	@Test
	public void testToString() {
		
		AbstractProposition<State> a=new LambdaProposition<State>();
		assertTrue(a.toString().equals("λ"));
	}

}
