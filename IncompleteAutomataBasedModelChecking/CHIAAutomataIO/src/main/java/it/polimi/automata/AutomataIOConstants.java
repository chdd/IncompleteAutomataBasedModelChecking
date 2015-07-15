package it.polimi.automata;

public final class AutomataIOConstants {
	

	public final static String XML_ELEMENT_CONSTRAINT="constraint";
	public final static String XML_ELEMENT_PORTS_REACHABILITY="portsReachability";
	public final static String XML_ELEMENT_PORTS_COLORS="portscolors";
	public final static String XML_ELEMENT_PROPOSITIONS="propositions";
	public final static String XML_ELEMENT_PROPOSITION="proposition";
	public final static String XML_ELEMENT_PROPOSITION_VALUE="value";
	public final static String XML_ELEMENT_PORTS_OUT_REACHABILITY="outcomingreachability";
	public final static String XML_ELEMENT_TRANSITIONS_OUT="outtransitions";
	public final static String XML_ELEMENT_TRANSITIONS_IN="intransitions";
	
	public final static String XML_ATTRIBUTE_PORT_TYPE="type";
	
	public final static String XML_ATTRIBUTE_VALUE_IN="inport";
	public final static String XML_ATTRIBUTE_VALUE_OUT="outport";
	
	public final static String XML_ELEMENT_PLUG_TRANSITION="plugtransition";
	public final static String XML_ELEMENT_SUBPROPERTY="subproperty";
	public final static String XML_ELEMENT_REPLACEMENT="replacement";
	
	public final static String XML_ELEMENT_REFINEMENT="refinement";
	public final static String XML_ELEMENT_STATES="states";
	
	public final static String XML_LOWER_REACHABILITY="lowerReachability";
	public final static String XML_UPPER_REACHABILITY="upperReachability";
	
	
	
	public final static String XML_ELEMENT_STATE="state";

	public final static String XML_ELEMENT_TRANSITIONS="transitions";
	public final static String XML_ELEMENT_TRANSITION="transition";
	public final static String XML_ELEMENT_REACHABILITY="reachabilityElement";
	public final static String XML_ELEMENT_REACHABILITIES="reachabilityElements";
	
	
	public final static String XML_ATTRIBUTE_ID="id";
	public final static String XML_ATTRIBUTE_PORT_SOURCE="source";
	public final static String XML_ATTRIBUTE_PORT_DESTINATION="destination";
	public final static String XML_ATTRIBUTE_INCOMING="incoming";
	public final static String XML_ATTRIBUTE_NAME="name";
	public final static String XML_ATTRIBUTE_COLOR="color";
	
	public final static String XML_ATTRIBUTE_MODEL_STATE_ID="modelstateId";
	public final static String XML_ATTRIBUTE_SUBPROPERTY_INDISPESNABLE="indispensable";
	public final static String XML_ATTRIBUTE_INITIAL="initial";
	public final static String XML_ATTRIBUTE_ACCEPTING="accepting";
	public final static String XML_ATTRIBUTE_ACCEPTING_CLAIM="acceptingclaim";
	public final static String XML_ATTRIBUTE_ACCEPTING_MODEL="acceptingmodel";
	public final static String XML_ATTRIBUTE_TRANSPARENT="transparent";
	public final static String XML_ATTRIBUTE_MIXED="mixed";
	public final static String XML_ATTRIBUTE_CONSTRAINED="constrained";
	
	public final static String XML_TAG_TRANSITION="transition";
	public final static String XML_ATTRIBUTE_TRANSITION_ID="id";
	public final static String XML_ATTRIBUTE_TRANSITION_SOURCE="source";
	public final static String XML_ATTRIBUTE_TRANSITION_DESTINATION="destination";
	public final static String XML_ATTRIBUTE_TRANSITION_PROPOSITIONS="propositions";
	public final static String XML_ATTRIBUTE_INVARIANT="invariant";
	
	public final static String XML_ELEMENT_INTERSECTION="intersection";
	public final static String XML_ELEMENT_BA="ba";
	public final static String XML_ELEMENT_IBA="iba";
	
	public final static String XML_ELEMENT_REACHABILITY_ELEMENT="reachabilityElement";
	public final static String XML_ELEMENT_REACHABILITY_ELEMENT_SOURCE="source";
	public final static String XML_ELEMENT_REACHABILITY_ELEMENT_DESTINATION="destination";
	
	
	
	/**
	 * IO Tags
	 */
	public static final String INITIALTAG = "initial";
	public static final String ACCEPTINGTAG = "accepting";
	public static final String LABELSTAG = "labels";
	public static final String TRANSPARENTTAG = "transparent";
	public static final String NAMETAG = "name";
	/**
	 * defaults
	 */
	public static final String LABELSDEFAULT = "";
	public static final String FALSEVALUE = "false";
	public static final String TRUEVALUE = "true";
	public static final String DEFAULTNAME = "";
	public static final String AND = "\\^";
	public static final String AND_NOT_ESCAPED = "^";
	public static final String NOT = "!";
	
	public static final String SIGMA="SIGMA";
	public static final String APREGEX="[a-z]+";
	public static final String NOTAPREGEX=NOT+"[a-z]+";
	public static final String AP="("+APREGEX+")|("+NOTAPREGEX+")";
	public static final String CLAIM_PROPOSITIONAL_FORMULA="("+AutomataIOConstants.SIGMA +")|(("+AP+")("+AutomataIOConstants.AND+"("+AP+"))*)";
	public static final String MODELAP=APREGEX;
	public static final String MODEL_PROPOSITIONS="("+AutomataIOConstants.SIGMA +")|(("+MODELAP+")("+AutomataIOConstants.AND+"("+MODELAP+"))*)";
	
	
	public static final String REGEXAND="\\"+AutomataIOConstants.AND+"";
	public static final String REGEXNOT="\\"+AutomataIOConstants.NOT+"";
	public static final String REGEXSIGMA="\\("+AutomataIOConstants.SIGMA+"\\)";
	public static final String WORD="[a-zA-Z]+";
	// PATTERN
	// (SIGMA)|((?!)WORD (^(?!)WORD)*)||
	public static final String LABELPATTERN="^("+AutomataIOConstants.REGEXSIGMA+")|(\\((?"+AutomataIOConstants.REGEXNOT+")"+WORD+"("+AutomataIOConstants.REGEXAND+"(?"+AutomataIOConstants.REGEXNOT+")"+WORD+")*\\))";

	private AutomataIOConstants() {
		// Utility classes should always be final and have an private constructor
	}

}
