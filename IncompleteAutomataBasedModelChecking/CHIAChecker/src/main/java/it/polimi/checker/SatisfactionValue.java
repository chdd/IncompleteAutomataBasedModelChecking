package it.polimi.checker;

/**
 * contains the satisfaction value
 * @author Claudio Menghi
 *
 */
public enum SatisfactionValue {
	SATISFIED {
		@Override
		public String toString() {
			return "SATISFIED";
		}
	},
	POSSIBLYSATISFIED {
		@Override
		public String toString() {
			return "POSSIBLY SATISFIED";
		}
	},
	NOTSATISFIED {
		@Override
		public String toString() {
			return "NOT SATISFIED";
		}
	};
}
