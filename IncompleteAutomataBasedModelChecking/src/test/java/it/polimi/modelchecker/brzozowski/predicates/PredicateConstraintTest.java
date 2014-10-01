package it.polimi.modelchecker.brzozowski.predicates;

import static org.junit.Assert.assertTrue;
import it.polimi.model.graph.State;
import it.polimi.modelchecker.brzozowski.propositions.AbstractProposition;
import it.polimi.modelchecker.brzozowski.propositions.AndProposition;
import it.polimi.modelchecker.brzozowski.propositions.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.EpsilonProposition;
import it.polimi.modelchecker.brzozowski.propositions.LambdaProposition;
import it.polimi.modelchecker.brzozowski.propositions.OrProposition;
import it.polimi.modelchecker.brzozowski.propositions.AtomicProposition;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PredicateConstraintTest {

	@Test
	public void testConstructor1() {
		AtomicProposition<State> p=new AtomicProposition<State>(new State("s1"), "abb");
		assertTrue(p.getState().equals(new State("s1")));
		assertTrue(p.getRegularExpression().equals("abb"));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor2() {
		new AtomicProposition<State>(null, "abb");
	}
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor3() {
		new AtomicProposition<State>(new State("s1"), null);
	}
	@Test
	public void testGetterAndSetters1() {
		AtomicProposition<State> p=new AtomicProposition<State>(new State("s1"), "abb");
		p.setRegularExpression("cdd");
		assertTrue(p.getState().equals(new State("s1")));
		assertTrue(p.getRegularExpression().equals("cdd"));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testGetterAndSetters2() {
		new AtomicProposition<State>(null, "abb");
	}
	@Test(expected=IllegalArgumentException.class)
	public void testGetterAndSetters3() {
		AtomicProposition<State> p=new AtomicProposition<State>(new State("s1"), "abb");
		p.setRegularExpression(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConcat0() {
		AbstractProposition<State> a=new AtomicProposition<State>(new State("s1"), "aab");
		a.concatenate(null);
	}
	
	/**
	 * the concatenation of the predicate and the empty constraint is empty
	 */
	@Test
	public void testConcat1() {
		AbstractProposition<State> a=new EmptyProposition<State>();
		AtomicProposition<State> p=new AtomicProposition<State>(new State("s1"), "abb");
		assertTrue(p.concatenate(a).equals(a));
	}
	/**
	 * the concatenation of the predicate and lambda is equal to the predicate
	 */
	public void testConcat2() {
		AtomicProposition<State> p=new AtomicProposition<State>(new State("s1"), "abb");
		AbstractProposition<State> a=new LambdaProposition<State>();
		assertTrue(p.concatenate(a).equals(p));
	}
	/**
	 * the concatenation of two predicate with the same state constrained is a new predicate where the regular expressions are concatenated
	 */
	public void testConcat3() {
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s1"), "cdd");
		
		assertTrue(p1.concatenate(p2) instanceof AtomicProposition);
		AtomicProposition<State> a=(AtomicProposition<State>) p1.concatenate(p2);
		assertTrue(a.getState().equals(new State("s1")));
		assertTrue(a.getRegularExpression().equals("abbcdd"));
	}
	/**
	 * the concatenation of two predicate with different state constrained is a new and constraint that contains the two predicates
	 */
	public void testConcat4() {
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s1"), "cdd");
		
		assertTrue(p1.concatenate(p2) instanceof AndProposition);
		AndProposition<State> a=(AndProposition<State>) p1.concatenate(p2);
		
		assertTrue(a.getPredicates().contains(p1));
		assertTrue(a.getPredicates().contains(p2));
		assertTrue(a.getPredicates().size()==2);
	}
	/**
	 * the concatenation of a predicate and an AndConstraint mixes the regular expression of this predicate of the regular expression of 
	 * the first predicate of the and constraint if they have the same state
	 */
	public void testConcat5() {
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s2"), "cdd");

		List<AbstractProposition<State>> l=new ArrayList<AbstractProposition<State>>();
		l.add(p1);
		l.add(p2);
		AndProposition<State> c1=new AndProposition<State>(l);
		AtomicProposition<State> p3=new AtomicProposition<State>(new State("s1"), "sss");
		
		
		assertTrue(p3.concatenate(c1) instanceof AndProposition);
		AndProposition<State> a=(AndProposition<State>) p3.concatenate(c1);
		
		assertTrue(a.getPredicates().contains(p3));
		assertTrue(a.getFistPredicate().equals(new AtomicProposition<State>(new State("s1"), "sssabb")));
	}
	/**
	 * the concatenation of a predicate and an AndConstraint add the predicate to the and constraint if this predicate and
	 * the first predicate of the and constraint have a different state
	 */
	public void testConcat6() {
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s2"), "cdd");

		List<AbstractProposition<State>> l=new ArrayList<AbstractProposition<State>>();
		l.add(p1);
		l.add(p2);
		AndProposition<State> c1=new AndProposition<State>(l);
		AtomicProposition<State> p3=new AtomicProposition<State>(new State("s3"), "sss");
		
		
		assertTrue(p3.concatenate(c1) instanceof AndProposition);
		AndProposition<State> a=(AndProposition<State>) p3.concatenate(c1);
		
		assertTrue(a.getPredicates().contains(p3));
		assertTrue(a.getFistPredicate().equals(p3));
		assertTrue(a.getPredicates().contains(p1));
		assertTrue(a.getPredicates().contains(p2));
	}
	/**
	 * the concatenation of a predicate and an OrConstraint is a new AndConstraint where the first element is the predicate and the second 
	 * is the orconstraint
	 * */
	public void testConcat7() {
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s2"), "cdd");
		
		
		OrProposition<State> c1=new OrProposition<State>(p1,p2);
		
		AtomicProposition<State> p3=new AtomicProposition<State>(new State("s3"), "sss");
		
		
		assertTrue(p3.concatenate(c1) instanceof AndProposition);
		AndProposition<State> a=(AndProposition<State>) p3.concatenate(c1);
		
		assertTrue(a.getPredicates().contains(p3));
		assertTrue(a.getFistPredicate().equals(p3));
		assertTrue(a.getPredicates().contains(c1));
	}
	/**
	 * the concatenation of a predicate and an EpsilonConstraint creates a new AndConstraint where the first element is the predicate 
	 * and the second one is the EpsilonConstraint
	 * */
	public void testConcat8() {
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		EpsilonProposition<State> eps=new EpsilonProposition<State>();
		
		assertTrue(p1.concatenate(eps) instanceof AndProposition);
		AndProposition<State> a=(AndProposition<State>) p1.concatenate(eps);
		
		assertTrue(a.getFistPredicate().equals(p1));
		assertTrue(a.getLastPredicate().equals(eps));
	}
	/**
	 * the star operator applied to a predicate <s,exp> modifies the regular expression into (exp)* generating the new constraint <s,(exp)*>
	 */
	@Test
	public void testStar() {
		
		AbstractProposition<State> p=new AtomicProposition<State>(new State("s1"), "abb");
		assertTrue(p.star() instanceof AtomicProposition);
		AtomicProposition<State> a=(AtomicProposition<State>) p.star();
		
		assertTrue(a.getState().equals(new State("s1")));
		assertTrue(a.getRegularExpression().equals("(abb)*"));

	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnion0() {
		AbstractProposition<State> a=new AtomicProposition<State>(new State("s1"), "aab");
		a.union(null);
	}
	
	/**
	 * the union of a predicate and the empty constraint is equal to the predicate
	 */
	@Test
	public void testUnion1() {
		AbstractProposition<State> a=new EmptyProposition<State>();
		AtomicProposition<State> p=new AtomicProposition<State>(new State("s1"), "abb");
		assertTrue(p.union(a).equals(p));
	}
	/**
	 * the union of a predicate and lambda is a new or constraint that contains the predicate and lambda
	 */
	public void testUnion2() {
		AtomicProposition<State> p=new AtomicProposition<State>(new State("s1"), "abb");
		AbstractProposition<State> a=new LambdaProposition<State>();
		
		assertTrue(p.union(a) instanceof OrProposition);
		OrProposition<State> o=(OrProposition<State>) p.union(a);
		assertTrue(o.getPredicates().contains(p));
		assertTrue(o.getPredicates().contains(a));
	}
	/**
	 * if a is a predicate the union of two predicates <s, reg1>, <s, reg2> with the same state s is a new predicate that contains the or combination 
	 * of their regular expressions <s, (reg1)+(reg2)> 
	 */
	public void testUnion3() {
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s1"), "cdd");
		
		assertTrue(p1.union(p2) instanceof AtomicProposition);
		AtomicProposition<State> a=(AtomicProposition<State>) p1.union(p2);
		assertTrue(a.getState().equals(new State("s1")));
		assertTrue(a.getRegularExpression().equals("(abb)+(cdd)"));
	}
	/**
	 * if a is a predicate the union of two predicates <s1, reg1>, <s2, reg2> with a different state is a new or constraint that 
	 * contains the two predicates 
	 */
	public void testUnion4() {
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s2"), "cdd");
		
		assertTrue(p1.union(p2) instanceof OrProposition);
		AndProposition<State> a=(AndProposition<State>) p1.union(p2);
		
		assertTrue(a.getPredicates().contains(p1));
		assertTrue(a.getPredicates().contains(p2));
		assertTrue(a.getPredicates().size()==2);
	}
	/**
	 *	the union of the predicate and an or constraint is a new or constraint where the predicate is added
	 */
	public void testUnion5() {
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s2"), "cdd");
		AtomicProposition<State> p3=new AtomicProposition<State>(new State("s3"), "cdd");
		
		OrProposition<State> c1=new OrProposition<State>(p2, p3);
		assertTrue(p1.union(c1) instanceof OrProposition);
		OrProposition<State> a=(OrProposition<State>) p1.union(c1);
		
		assertTrue(a.getPredicates().contains(p1));
		assertTrue(a.getPredicates().contains(c1));
	}
	/**
	 * the union of the predicate and an and constraint is a new or constraint where the predicate and the and constraint are added
	 */
	public void testUnion6() {
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		AtomicProposition<State> p2=new AtomicProposition<State>(new State("s2"), "cdd");
		AtomicProposition<State> p3=new AtomicProposition<State>(new State("s3"), "cdd");
		
		AndProposition<State> c1=new AndProposition<State>(p2, p3);
		assertTrue(p1.union(c1) instanceof OrProposition);
		OrProposition<State> a=(OrProposition<State>) p1.union(c1);
		
		assertTrue(a.getPredicates().contains(p1));
		assertTrue(a.getPredicates().contains(c1));
	}
	/**
	 * the union of a predicate and epsilon is a new or constraint that contains the predicate
	 * */
	public void testUnion7() {
		AtomicProposition<State> p1=new AtomicProposition<State>(new State("s1"), "abb");
		EpsilonProposition<State> eps=new EpsilonProposition<State>();
		
		assertTrue(p1.union(eps) instanceof AtomicProposition);
		assertTrue(p1.union(eps).equals(p1));
	}
	
	@Test
	public void testToString() {
		
		AtomicProposition<State> p=new AtomicProposition<State>(new State("s1"), "abb");
		assertTrue(p.toString().equals("<s1,abb>"));
	}

}
