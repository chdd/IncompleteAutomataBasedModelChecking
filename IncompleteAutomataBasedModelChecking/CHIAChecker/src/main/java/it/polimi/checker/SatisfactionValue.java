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
			return "Y";
		}
	},
	POSSIBLYSATISFIED {
		@Override
		public String toString() {
			return "P";
		}
	},
	NOTSATISFIED {
		@Override
		public String toString() {
			return "N";
		}
	};
}
