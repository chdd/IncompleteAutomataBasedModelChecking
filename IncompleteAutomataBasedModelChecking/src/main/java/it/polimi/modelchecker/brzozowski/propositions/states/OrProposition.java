package it.polimi.modelchecker.brzozowski.propositions.states;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author claudiomenghi contains an OrPredicate
 */
public class OrProposition<S extends State, T extends Transition> extends
		LogicalProposition<S, T> {

	private static final String type = "||";

	
	/**
	 * stores the {@link Set} of {@link LogicalItem} involved in the
	 * {@link LogicalProposition}
	 */
	protected Set<LogicalItem<S, T>> value;
	
	/**
	 * creates a new {@link OrProposition} that contains the two
	 * {@link AbstractProposition} firstPredicate, secondPredicate
	 * 
	 * @param firstPredicate
	 *            is the first {@link AbstractProposition} to be included in the
	 *            {@link OrProposition}
	 * @param secondPredicate
	 *            is the second {@link AbstractProposition} included in the
	 *            {@link OrProposition}
	 * @throws IllegalArgumentException
	 *             is generated if the first or the second
	 *             {@link AbstractProposition} are null if the first and the
	 *             second {@link AbstractProposition} are equals
	 * 
	 */
	public OrProposition(LogicalItem<S, T> firstPredicate,
			LogicalItem<S, T> secondPredicate) {
		if (firstPredicate == null) {
			throw new IllegalArgumentException(
					"The first constraint cannot be null");
		}
		if (secondPredicate == null) {
			throw new IllegalArgumentException(
					"The second constraint cannot be null");
		}
		this.value = new HashSet<LogicalItem<S, T>>();
		this.value.add(firstPredicate);
		this.value.add(secondPredicate);
		if (this.value.size() <= 1) {
			throw new InternalError(
					"It is not possible to create a And or Or predicate that contains less than two predicates");
		}
	}
		

	/**
	 * Initializes a newly created {@link AndProposition} starting from the list
	 * l of {@link AbstractProposition}
	 * 
	 * @param l
	 *            : is the list of {@link AbstractProposition} to be added in
	 *            the and predicate
	 * @throws IllegalArgumentException
	 *             if the list of the {@link AbstractProposition} contains less
	 *             than 2 {@link AbstractProposition}
	 */
	public OrProposition(List<LogicalItem<S, T>> l) {
		if (l == null) {
			throw new NullPointerException(
					"the list of logical items cannot be null");
		}
		if (l.size() <= 1) {
			throw new InternalError(
					"It is not possible to create a And or Or predicate that contains less than two predicates");
		}
		this.value = new HashSet<LogicalItem<S, T>>(l);
	}

	/**
	 * the concatenation of an {@link OrProposition} is defined as follows: - if
	 * a is an {@link EmptyProposition} the {@link OrProposition} is returned -
	 * if a is a {@link LambdaProposition} the {@link OrProposition} is returned
	 * - if a is an {@link EpsilonProposition} the {@link AndProposition} of
	 * this {@link OrProposition} and the {@link EpsilonProposition} is returned
	 * - if a is a {@link AtomicProposition} the concatenation of the
	 * {@link OrProposition} and the {@link AtomicProposition} is returned - if
	 * a is an {@link AndProposition} the concatenation of the
	 * {@link OrProposition} and the {@link AndProposition} is returned - if a
	 * is an {@link OrProposition} the concatenation of the
	 * {@link OrProposition} and the {@link OrProposition} a is returned
	 * 
	 * @param a
	 *            : is the constraint to be concatenated
	 * @return the constraint which is the concatenation of the or constraint
	 *         and a
	 * @throws IllegalArgumentException
	 *             is generated when the constraint to be concatenated is null
	 */
	@Override
	public LogicalItem<S, T> concatenate(LogicalItem<S, T> a) {
		if (a == null) {
			throw new IllegalArgumentException(
					"the constraint a cannot be null");
		}
		// if a is an empty constraint the or constraint is returned
		if (a instanceof EmptyProposition) {
			return a;
		}
		// if a is an lambda constraint the or constraint is returned
		if (a instanceof LambdaProposition) {
			return this;
		}
		// if a is an EpsilonConstraint concatenation of the or constraint and
		// EpsilonConstraint is returned
		if (a instanceof EpsilonProposition) {
			return this;
			// return new AndProposition<S, T>(this, a);
		}
		// if a is a Predicate the concatenation of the or constraint and
		// Predicate is returned
		if (a instanceof AtomicProposition) {
			return new AndProposition<S, T>(this, a);
		}
		// if a is an AndConstraint the concatenation of the or constraint and
		// the AndConstraint is returned
		if (a instanceof AndProposition) {
			return new AndProposition<S, T>(this, a);
		}
		// if a is an OrConstraint the concatenation of the or constraint and
		// the OrConstraint is returned
		if (a instanceof OrProposition) {
			return new AndProposition<S, T>(this, a);
		}
		throw new IllegalArgumentException(
				"The type:"
						+ a.getClass()
						+ " of the constraint is not in the set of the predefined types");
	}

	/**
	 * the union operator applied to an {@link OrProposition} is defined as
	 * follows: - the union of an {@link OrProposition} and an
	 * {@link EmptyProposition} returns the {@link OrProposition} - the union of
	 * an {@link OrProposition} and a {@link LambdaProposition} is a new
	 * {@link OrProposition} that contains the {@link OrProposition} and
	 * {@link LambdaProposition} - the union of an {@link OrProposition} and an
	 * {@link EpsilonProposition} is a new {@link OrProposition} that contains
	 * the {@link OrProposition} and the {@link EpsilonProposition} - the union
	 * of an {@link OrProposition} and a {@link AtomicProposition} is a new
	 * {@link OrProposition} that contains the {@link OrProposition} and the
	 * {@link AtomicProposition} - the union of an {@link OrProposition} and an
	 * {@link OrProposition} is a new {@link OrProposition} that contains the
	 * two {@link OrProposition} - the union of an {@link OrProposition} and an
	 * {@link AndProposition} is a new {@link OrProposition} that contains the
	 * {@link OrProposition} and the {@link AndProposition}
	 * 
	 * @param a
	 *            : is the {@link AbstractProposition} to be unified
	 * @return the {@link AbstractProposition} which is the union of the
	 *         {@link OrProposition} and a
	 * @throws IllegalArgumentException
	 *             is generated when the {@link AbstractProposition} to be
	 *             concatenated is null
	 */
	@Override
	public LogicalItem<S, T> union(LogicalItem<S, T> a) {

		if (a == null) {
			throw new IllegalArgumentException(
					"The constraint to be concatenated cannot be null");
		}
		// the union of an or constraint and an empty constraint returns the or
		// constraint
		if (a instanceof EmptyProposition) {
			return this;
		}
		// the union of an or constraint and a LambdaConstraint is a new
		// orConstraint that contains the or constraint and lambda
		if (a instanceof LambdaProposition) {
			return new OrProposition<S, T>(this, a);
		}
		// the union of an or constraint and an EpsilonConstrain is a new
		// orConstraint that contains the or constraint and the EpsilonConstrain
		if (a instanceof EpsilonProposition) {
			return new OrProposition<S, T>(this, a);
		}
		// the union of an or constraint and a Predicate is a new orConstraint
		// that contains the or constraint and the Predicate
		if (a instanceof AtomicProposition) {
			return new OrProposition<S, T>(this, a);
		}
		// the union of an or constraint and an orConstraint is a new
		// orConstraint that contains the two or constraints
		if (a instanceof OrProposition) {
			List<LogicalItem<S, T>> l = new ArrayList<LogicalItem<S, T>>();
			l.addAll(((OrProposition) a).getPredicates());
			l.addAll(this.getPredicates());
			OrProposition<S, T> ret = new OrProposition<S, T>(l);

			return ret;
		}
		// the union of an or constraint and an andConstraint is a new
		// orConstraint that contains the or constraint and the andConstraint
		if (a instanceof AndProposition) {
			return new OrProposition<S, T>(this, a);
		}
		throw new IllegalArgumentException(
				"The type:"
						+ a.getClass()
						+ " of the constraint is not in the set of the predefined types");
	}

	/**
	 * the star operator when applied to an or constraint does not produce any
	 * effect
	 * 
	 * @return the or constraint
	 */
	@Override
	public LogicalItem<S, T> star() {
		return this;
	}

	/**
	 * the star operator when applied to an {@link OrProposition} does not
	 * produce any effect
	 * 
	 * @return the or constraint
	 */
	@Override
	public LogicalItem<S, T> omega() {
		return this;
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
		OrProposition other = (OrProposition) obj;
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
		if (simplifiedItem.size() == 1) {
			return simplifiedItem.iterator().next();
		} else {
			List<LogicalItem<S, T>> simplifiedItem2 = new ArrayList<LogicalItem<S, T>>();

			for (LogicalItem<S, T> l : simplifiedItem) {
				if (l instanceof OrProposition) {
					simplifiedItem2.addAll(((OrProposition<S, T>) l)
							.getPredicates());
				} else {
					simplifiedItem2.add(l);
				}
			}
			if (simplifiedItem2.size() == 1) {
				return simplifiedItem2.iterator().next();
			} else {
				return new OrProposition<S, T>(simplifiedItem2);
			}
		}
	}

	@Override
	public LogicalItem<S, T> clone() {
		return new OrProposition<S, T>(new ArrayList<LogicalItem<S, T>>(
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
	
	/**
	 * returns the list of the {@link AbstractProposition} associated with the
	 * {@link AndProposition}
	 * 
	 * @return the list of the {@link AbstractProposition} associated with the
	 *         {@link AndProposition}
	 */
	public List<LogicalItem<S, T>> getPredicates() {
		return new ArrayList<LogicalItem<S, T>>(this.value);
	}
}
