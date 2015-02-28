package it.polimi.automata;

public final class Constants {
	

	public final static String XML_ELEMENT_CONSTRAINTS="constraints";
	public final static String XML_ELEMENT_PORTS_REACHABILITY="portsReachability";
	public final static String XML_ELEMENT_PORTS_COLORT="portscolors";
	
	
	public final static String XML_ELEMENT_PORTS_OUT_REACHABILITY="outcomingreachability";
	public final static String XML_ELEMENT_PORTS_OUT="outports";
	public final static String XML_ELEMENT_PORTS_IN="inports";
	public final static String XML_ELEMENT_PORT="port";
	public final static String XML_ELEMENT_CONSTRAINT="constraint";
	public final static String XML_ELEMENT_STATE="state";
	public final static String XML_ELEMENT_TRANSITION="transition";
	public final static String XML_ATTRIBUTE_ID="id";
	public final static String XML_ATTRIBUTE_NAME="name";
	public final static String XML_ATTRIBUTE_COLOR="color";
	
	public final static String XML_ATTRIBUTE_MODEL_STATE_ID="modelstateId";
	public final static String XML_ATTRIBUTE_INITIAL="initial";
	public final static String XML_ATTRIBUTE_ACCEPTING="accepting";
	public final static String XML_ATTRIBUTE_TRANSPARENT="transparent";
	
	public final static String XML_TAG_TRANSITION="transition";
	public final static String XML_ATTRIBUTE_TRANSITION_ID="id";
	public final static String XML_ATTRIBUTE_TRANSITION_SOURCE="source";
	public final static String XML_ATTRIBUTE_TRANSITION_DESTINATION="destination";
	public final static String XML_ATTRIBUTE_TRANSITION_PROPOSITIONS="propositions";
	public final static String XML_ATTRIBUTE_INVARIANT="invariant";
	
	public final static String XML_ELEMENT_BA="ba";

	
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
	public static final String CLAIM_PROPOSITIONAL_FORMULA="("+Constants.SIGMA +")|(("+AP+")("+Constants.AND+"("+AP+"))*)";
	public static final String MODELAP=APREGEX;
	public static final String MODEL_PROPOSITIONS="("+Constants.SIGMA +")|(("+MODELAP+")("+Constants.AND+"("+MODELAP+"))*)";
	
	
	public static final String REGEXAND="\\"+Constants.AND+"";
	public static final String REGEXNOT="\\"+Constants.NOT+"";
	public static final String REGEXSIGMA="\\("+Constants.SIGMA+"\\)";
	public static final String WORD="[a-zA-Z]+";
	// PATTERN
	// (SIGMA)|((?!)WORD (^(?!)WORD)*)||
	public static final String LABELPATTERN="^("+Constants.REGEXSIGMA+")|(\\((?"+Constants.REGEXNOT+")"+WORD+"("+Constants.REGEXAND+"(?"+Constants.REGEXNOT+")"+WORD+")*\\))";

	private Constants() {
		// Utility classes should always be final and have an private constructor
	}

}
