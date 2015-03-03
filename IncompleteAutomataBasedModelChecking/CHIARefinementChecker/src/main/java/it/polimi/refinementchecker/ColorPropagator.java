package it.polimi.refinementchecker;

import it.polimi.constraints.Color;

class ColorPropagator {

	static Color backPropagateColor(Color incomingColor, Color outcomingColor) {
		/**
		 * if the incoming color is read I already know that from this state it
		 * is possible to reach the accepting state, i.e., the incoming color
		 * must remain red
		 */
		if (incomingColor == Color.RED) {
			return incomingColor;
		}
		/**
		 * if the incoming color is yellow the out-coming color is back
		 * propagate to the in-coming port
		 */
		if (incomingColor == Color.YELLOW) {
			return outcomingColor;
		}
		/**
		 * if the incoming color is green two cases are possible
		 */
		if (incomingColor == Color.GREEN) {
			/**
			 * if the out-coming color is red it means that the property is not
			 * satisfied and a null value is returned
			 */
			if (outcomingColor == Color.RED) {
				return null;
			} else {
				/**
				 * otherwise the out-coming color is simply returned
				 */
				return outcomingColor;
			}
		}
		return null;

	}

	static Color forwardColorPropagation(Color incomingColor,
			Color outcomingColor) {
		/**
		 * if the out-coming color is green it means that the out-coming port is
		 * already reachable from the initial state so the out-coming color is
		 * returned
		 */
		if (outcomingColor == Color.GREEN) {
			return outcomingColor;
		}
		/**
		 * if the out-coming color is yellow, the color of the incoming
		 * transition is returned
		 */
		if (outcomingColor == Color.YELLOW) {
			return incomingColor;
		}
		/**
		 * if the out-coming color is red two cases are possible
		 */
		if (outcomingColor == Color.RED) {
			/**
			 * if the incoming color is green it means that the property is not
			 * satisfied and a null value is returned
			 */
			if (incomingColor == Color.GREEN) {
				return null;
			} else {
				/**
				 * otherwise the in-coming color is simply returned
				 */
				return incomingColor;
			}
		}
		return null;
	}
}
