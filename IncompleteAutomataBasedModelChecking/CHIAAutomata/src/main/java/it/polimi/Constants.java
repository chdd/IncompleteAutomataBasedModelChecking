package it.polimi;

public class Constants {

	private Constants() {
		// Utility classes should always be final and have an private constructor
	}
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
	public static final String AND = "^";
	public static final String OR = "|";
	public static final String NOT = "!";
	
	public static final String APREGEX="^[a-zA-Z]+$";
	public static final String NOTAPREGEX="^![a-zA-Z]+$";
	public static final String LPAR="(";
	public static final String RPAR=")";

}
