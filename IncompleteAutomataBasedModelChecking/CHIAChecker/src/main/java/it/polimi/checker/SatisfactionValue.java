package it.polimi.checker;

public enum SatisfactionValue {
	SATISFIED {
		@Override
		public String toString() {
			return "The property is satisfied";
		}
	},
	POSSIBLYSATISFIED {
		@Override
		public String toString() {
			return "The property is possibly satisfied";
		}
	},
	NOTSATISFIED {
		@Override
		public String toString() {
			return "The property is not satisfied";
		}
	};
}
