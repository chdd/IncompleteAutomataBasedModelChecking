package it.polimi.modelchecker.brzozowski.propositions.states;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author claudiomenghi contains an {@link AndProposition}, which is a set of
 *         {@link AbstractProposition} that must be simultaneously satisfied to
 *         get the final property satisfied
 */
public class AndProposition<S extends State, T extends Transition> extends
		LogicalProposition<S, T> {

	private static final String type = "&&";
	
	/**
	 * stores the {@link List} of {@link LogicalItem} involved in the
	 * {@link LogicalProposition}
	 */
	protected List<LogicalItem<S, T>> value;
	
	/**
	 * returns the list of the {@link AbstractProposition} associated with the
	 * {@link AndProposition}
	 * 
	 * @return the list of the {@link AbstractProposition} associated with the
	 *         {@link AndProposition}
	 */
	public List<LogicalItem<S, T>> getPredicates() {
		return this.value;
	}
	
	/**
	 * Initializes a newly created {@link AndProposition} starting from the list
	 * l of {@link AbstractProposition}
	 * 
	 * @param l
	 *            : is the list of {@link AbstractProposition} to be added in
	 *            the and predicate
	 * @throws InternalError
	 *             if the list of the {@link AbstractProposition} contains less
	 *             than 2 {@link AbstractProposition}
	 * @throws NullPointerException
	 *             if the {@link List} of {@link LogicalItem} is null
	 */
	public AndProposition(List<LogicalItem<S, T>> l) {
		if (l == null) {
			throw new NullPointerException(
					"the list of logical items cannot be null");
		}
		if (l.size() <= 1) {
			throw new InternalError(
					"It is not possible to create a And or Or predicate that contains less than two predicates");
		}
		this.value = l;
	}

	/**
	 * creates a new {@link AndProposition} that contains the two
	 * {@link AbstractProposition} firstPredicate, secondPredicate
	 * 
	 * @param firstPredicate
	 *            is the first {@link AbstractProposition} to be included in the
	 *            {@link AndProposition}
	 * @param secondPredicate
	 *            is the second {@link AbstractProposition} included in the
	 *            {@link AndProposition}
	 * @throws InternalError
	 *             is the first or the second {@link AbstractProposition} are
	 *             null
	 */
	public AndProposition(
			LogicalItem<S, T> firstPredicate,
			LogicalItem<S, T> secondPredicate) {
		if (firstPredicate == null) {
			throw new IllegalArgumentException(
					"The first constraint cannot be null");
		}
		if (secondPredicate == null) {
			throw new IllegalArgumentException(
					"The second constraint cannot be null");
		}
		this.value = new ArrayList<LogicalItem<S, T>>();
		this.value.add(firstPredicate);
		this.value.add(secondPredicate);
		if (this.value.size() <= 1) {
			throw new InternalError(
					"It is not possible to create a And or Or predicate that contains less than two predicates");
		}
	}
	

	
	/**
	 * the concatenation of an {@link AndProposition} is defined as follows: -
	 * if a is an {@link EmptyProposition} the {@link EmptyProposition} is
	 * returned - if a is an {@link LambdaProposition} the
	 * {@link AndProposition} is returned - if a is an
	 * {@link EpsilonProposition} the concatenation of the
	 * {@link AndProposition} constraint and {@link EpsilonProposition} is
	 * returned - if a is a {@link AtomicProposition} and the last element p is
	 * a {@link AtomicProposition} that has the same state of a, the regular
	 * expression of p is modified and concatenated to the one of the
	 * {@link AtomicProposition} a - if a is a {@link AtomicProposition} and the
	 * last element p of this {@link AndProposition} is a
	 * {@link AtomicProposition} that has a different state of a, a new
	 * {@link AndProposition} that contains the original {@link AndProposition}
	 * and the {@link AtomicProposition} a is generated - if a is an
	 * {@link OrProposition} a new and {@link AndProposition} that contains the
	 * and of this {@link AndProposition} and {@link OrProposition}is generated
	 * - if a is an {@link AndProposition} and the last
	 * {@link AbstractProposition} of this {@link AndProposition} and the first
	 * {@link AbstractProposition} of a have the same state, their regular
	 * expressions are merged - if a is an {@link AndProposition} and the last
	 * {@link AbstractProposition} of this {@link AndProposition} and the first
	 * {@link AbstractProposition} of a do not have the same state, a new
	 * {@link AndProposition} that contains all {@link AbstractProposition} of
	 * these two {@link AndProposition} is generated.
	 * 
	 * @param a
	 *            : is the {@link AbstractProposition} to be concatenated
	 * @return the {@link AndProposition} which is the concatenation of the
	 *         {@link AndProposition} and a
	 * @throws IllegalArgumentException
	 *             is generated when the {@link AbstractProposition} to be
	 *             concatenated is null
	 */
	@Override
	public LogicalItem<S, T> concatenate(LogicalItem<S, T> a) {

		if (a == null) {
			throw new IllegalArgumentException(
					"the constraint a cannot be null");
		}
		// if a is an empty constraint the empty constraint is returned
		if (a instanceof EmptyProposition) {
			return a;
		}
		// if a is an lambda constraint the and constraint is returned
		if (a instanceof LambdaProposition) {
			return this;
		}
		// if a is an EpsilonConstraint the concatenation of the and constraint
		// and EpsilonConstraint is returned
		if (a instanceof EpsilonProposition) {
			return this;
			/*
			 * if(this.getLastPredicate() instanceof EpsilonProposition){ return
			 * this; } else{ return new AndProposition<S, T>(this, a); }
			 */
		}
		// - if a is a Predicate and the last element p of this constraint is a
		// Predicate that has the same state of a,
		// the regular expression of p is modified and concatenated to the one
		// of the predicate a
		// - if a is a Predicate and the last element p of this constraint is a
		// Predicate that has a different state of a,
		// a new and constraint that contains the original constrained and the
		// predicate a is generated
		if (a instanceof AtomicProposition) {
			if (this.getLastPredicate() instanceof AtomicProposition
					&& ((AtomicProposition<S, T>) a).getState().equals(
							((AtomicProposition<S, T>) this.getLastPredicate())
									.getState())) {

				AtomicProposition<S, T> lastPredicate = (AtomicProposition<S, T>) this
						.getLastPredicate();
				Set<T> transitions = new HashSet<T>();
				transitions.addAll(lastPredicate.getTransitions());
				transitions.addAll(((AtomicProposition<S, T>) a)
						.getTransitions());

				List<LogicalItem<S, T>> l = new ArrayList<LogicalItem<S, T>>();
				l.addAll(this.getPredicates().subList(0,
						this.getPredicates().size() - 1));
				l.add(new AtomicProposition<S, T>(transitions, lastPredicate
						.getState(),
						"("
								+ lastPredicate.getRegularExpression()
								+ ").("
								+ (((AtomicProposition<S, T>) a)
										.getRegularExpression()) + ")",
						lastPredicate.isFinalStateReacher()
								|| ((AtomicProposition<S, T>) a)
										.isFinalStateReacher()));
				AndProposition<S, T> cret = new AndProposition<S, T>(l);
				return cret;
			} else {
				return new AndProposition<S, T>(this, a);
			}
		}
		// if a is an or constraint a new and constraint that contains the
		// constraint of this and constraint and the or constraint is generated
		if (a instanceof OrProposition) {
			return new AndProposition<S, T>(this, a);
		}
		// if a is an and constraint and the last predicate of this constraint
		// and the first predicate of a have the same state, their regular
		// expressions are merged
		// if a is an and constraint and the last predicate of this constraint
		// and the first predicate of do not have the same state, a new and
		// constraint
		// that contains all of the constraints of these two and constraints is
		// generated.
		if (a instanceof AndProposition) {
			AndProposition<S, T> c = (AndProposition<S, T>) a;

			if ((c.getFistPredicate() instanceof AtomicProposition)
					&& (this.getLastPredicate() instanceof AtomicProposition)
					&& ((AtomicProposition<S, T>) c.getFistPredicate())
							.getState().equals(
									((AtomicProposition<S, T>) this
											.getLastPredicate()).getState())) {

				List<LogicalItem<S, T>> l = new ArrayList<LogicalItem<S, T>>();
				l.addAll(this.getPredicates().subList(0,
						this.getPredicates().size() - 1));

				AtomicProposition<S, T> lastPredicate = (AtomicProposition<S, T>) this
						.getLastPredicate();
				AtomicProposition<S, T> initialPredicate = (AtomicProposition<S, T>) c
						.getFistPredicate();
				Set<T> transitions = new HashSet<T>();
				transitions.addAll(lastPredicate.getTransitions());
				transitions.addAll(initialPredicate.getTransitions());

				l.add(new AtomicProposition<S, T>(
						transitions,
						lastPredicate.getState(),
						"(" + lastPredicate.getRegularExpression() + ").("
								+ initialPredicate.getRegularExpression() + ")",
						lastPredicate.isFinalStateReacher()
								|| initialPredicate.isFinalStateReacher()));
				l.addAll(c.getPredicates().subList(1, c.getPredicates().size()));
				AndProposition<S, T> cret = new AndProposition<S, T>(l);
				return cret;
			} else {
				return new AndProposition<S, T>(this, a);
			}

		}
		throw new IllegalArgumentException("The type:" + a.getClass()
				+ " of the predicate is not in the set of the predefined types");
	}

	/**
	 * the union operator applied to an {@link AndProposition} is defined as
	 * follows: - the union of an {@link AndProposition} and an
	 * {@link EmptyProposition} returns the {@link AndProposition} - the union
	 * of an {@link AndProposition} and a {@link LambdaProposition} is a new
	 * {@link OrProposition} that contains the {@link AndProposition} and the
	 * {@link LambdaProposition} - the union of an {@link AndProposition} and an
	 * {@link EpsilonProposition} is a new {@link OrProposition} that contains
	 * the {@link AndProposition} and the {@link EpsilonProposition} - the union
	 * of an {@link AndProposition} and a {@link AtomicProposition} is a new
	 * {@link OrProposition} that contains the {@link AndProposition} and the
	 * {@link AtomicProposition} - the union of an {@link AndProposition} and an
	 * {@link OrProposition} is a new {@link OrProposition} that contains the
	 * and {@link AndProposition} the {@link OrProposition} - the union of an
	 * {@link AndProposition} and an {@link AndProposition} is a new
	 * {@link OrProposition} that contains the two {@link AndProposition}
	 * 
	 * @param a
	 *            : is the {@link AbstractProposition} to be unified
	 * @return the {@link AbstractProposition} which is the union of the
	 *         {@link AndProposition} and the {@link AbstractProposition} a
	 * @throws NullPointerException
	 *             is generated when the {@link AbstractProposition} to be
	 *             concatenated is null
	 */
	@Override
	public LogicalItem<S, T> union(LogicalItem<S, T> a) {
		if (a == null) {
			throw new NullPointerException(
					"The constraint to be concatenated cannot be null");
		}

		// the union of an and constraint and an empty constraint returns the or
		// constraint
		if (a instanceof EmptyProposition) {
			return this;
		}
		// the union of an and constraint and a LambdaConstraint is a new
		// orConstraint that contains the and constraint and lambda
		if (a instanceof LambdaProposition) {
			return new OrProposition<S, T>(this, a);
		}
		// the union of an and constraint and an EpsilonConstrain is a new
		// orConstraint that contains the and constraint and the
		// EpsilonConstrain
		if (a instanceof EpsilonProposition) {
			return new OrProposition<S, T>(this, a);
		}
		// the union of an and constraint and a Predicate is a new orConstraint
		// that contains the and constraint and the Predicate
		if (a instanceof AtomicProposition) {
			return new OrProposition<S, T>(this, a);
		}
		// the union of an and constraint and an orConstraint is a new
		// orConstraint that contains the and and the or constraints
		if (a instanceof OrProposition) {
			return new OrProposition<S, T>(this, a);
		}
		// the union of an and constraint and an andConstraint is a new
		// orConstraint that contains the two and constraints
		if (a instanceof AndProposition) {
			if(a.equals(this)){
				return this;
			}
			else{
				return new OrProposition<S, T>(this, a);
			}
		}

		throw new IllegalArgumentException(
				"The type:"
						+ a.getClass()
						+ " of the constraint is not in the set of the predefined types");
	}

	/**
	 * the star operator applied to an {@link AndProposition} does not produce
	 * any effect
	 * 
	 * @return the {@link AndProposition}
	 */
	@Override
	public LogicalItem<S, T> star() {
		return this;
	}

	/**
	 * the omega operator applied to an {@link AndProposition} does not produce
	 * any effect
	 * 
	 * @return the {@link AndProposition}
	 */
	@Override
	public LogicalItem<S, T> omega() {
		return this;
	}

	/**
	 * returns the first {@link AbstractProposition} of the {@link List}
	 * 
	 * @return the first {@link AbstractProposition} of the {@link List}
	 */
	public LogicalItem<S, T> getFistPredicate() {
		return this.getPredicates().get(0);
	}

	/**
	 * returns the last {@link AbstractProposition} of the {@link List}
	 * 
	 * @return the last {@link AbstractProposition} of the {@link List}
	 */
	public LogicalItem<S, T> getLastPredicate() {
		return this.getPredicates().get(this.getPredicates().size() - 1);
	}

	public String getType() {
		return this.type;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AndProposition<S,T> other = (AndProposition<S,T>) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public LogicalItem<S, T> simplify() {

		List<LogicalItem<S, T>> simplifiedItem = this.simplifyValues();

		// contains the list of AND path after the or operator is remived
		Set<LogicalItem<S, T>> andItems = new HashSet<LogicalItem<S, T>>();

		for (LogicalItem<S, T> l : simplifiedItem) {
			if (!(l instanceof OrProposition)) {
				andItems = this.addItemToEachClause(andItems, l);
			}
			if (l instanceof OrProposition) {
				Set<LogicalItem<S, T>> newAndItemsList = new HashSet<LogicalItem<S, T>>();

				for (LogicalItem<S, T> elementOfOr : ((OrProposition<S, T>) l)
						.getPredicates()) {
					for (LogicalItem<S, T> item : andItems) {

						LogicalItem<S, T> andItemCopy = item.clone();
						LogicalItem<S, T> concatenation=andItemCopy.concatenate(elementOfOr);
						newAndItemsList.add(concatenation);
					}
				}
				andItems = newAndItemsList;
			}
		}

		if (andItems.size() == 1) {
			return andItems.iterator().next();
		} else {
			return new OrProposition<S, T>(new ArrayList<LogicalItem<S, T>>(
					andItems));
		}
	}

	/**
	 * adds toe each {@link LogicalItem} in the {@link Set} of andItems the
	 * itemToBeAdded
	 * 
	 * @param andItems
	 *            contains the {@link Set} of {@link LogicalItem} (
	 *            {@link AndProposition} ) to be modified
	 * @param itemToBeAdded
	 *            contains the {@link LogicalItem} to be added
	 * @return a new {@link Set} which contains the items modified
	 * @throws NullPointerException
	 *             if the andItems or the itemToBeAdded is null
	 * @throws InternalError
	 *             if one of the {@link LogicalItem} is an
	 *             {@link OrProposition}
	 */
	private Set<LogicalItem<S, T>> addItemToEachClause(
			Set<LogicalItem<S, T>> andItems, LogicalItem<S, T> itemToBeAdded) {

		if (andItems == null) {
			throw new InternalError("The set of items cannot be null");
		}
		if (itemToBeAdded == null) {
			throw new InternalError("The item to be added cannot be null");
		}
		// creates a new set of logical items
		Set<LogicalItem<S, T>> newItemSet = new HashSet<LogicalItem<S, T>>();
		// if the set of and items is empty
		if (andItems.isEmpty()) {
			// the item to be added is added in the new set and this set is
			// returned
			newItemSet.add(itemToBeAdded);
			return newItemSet;
		} else {
			// each and item is analyzed
			for (LogicalItem<S, T> l : andItems) {
				if ((l instanceof OrProposition)) {
					throw new InternalError(
							"The logical item must not be and OrProposition :"+l.toString());
				}
				newItemSet.add(l.concatenate(itemToBeAdded));
			}
			return newItemSet;
		}
	}

	@Override
	public LogicalItem<S, T> clone() {
		return new AndProposition<S, T>(new ArrayList<LogicalItem<S, T>>(
				this.getPredicates()));
	}
	
	/**
	 * removes the duplicated values, the {@link EpsilonProposition}, the
	 * {@link EmptyProposition} and the {@link LambdaProposition} from the
	 * {@link List}
	 * 
	 * @return s new {@link List} where duplicated values are not present and
	 *         the {@link EpsilonProposition}, the {@link EmptyProposition} and
	 *         the {@link LambdaProposition} are removed
	 */
	public List<LogicalItem<S, T>> simplifyValues() {

		List<LogicalItem<S, T>> simplifiedItems = new ArrayList<LogicalItem<S, T>>();

		for (LogicalItem<S, T> l : this.value) {

			// simplifies the value
			LogicalItem<S, T> simplifiedl = l
					.simplify().clone();
			if (!simplifiedItems.contains(simplifiedl)) {
				if (l instanceof LogicalProposition
						|| l instanceof AtomicProposition) {
					simplifiedItems.add(simplifiedl);
				}
			}
		}
		return simplifiedItems;
	}
}
