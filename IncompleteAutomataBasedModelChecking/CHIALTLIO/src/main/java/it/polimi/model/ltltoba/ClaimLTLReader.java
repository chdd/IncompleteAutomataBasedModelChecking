package it.polimi.model.ltltoba;

import it.polimi.automata.BA;

import java.io.BufferedReader;
import java.io.FileReader;

import action.CHIAAction;

import com.google.common.base.Preconditions;

/**
 * The claim LTL reader is used to load a claim from a given file, where the
 * formula is stored as an LTL property
 * 
 * @author Claudio Menghi
 *
 */
public class ClaimLTLReader extends CHIAAction<BA> {

	/**
	 * is the path of the file from which the formula must be readed
	 */
	private String filePath;

	/**
	 * is the name of the action to be performed
	 */
	private static final String NAME = "LOADING FROM LTL FILE";

	/**
	 * creates a new reader that reads the LTL formula from a file
	 * 
	 * @param filePath
	 *            is the path of the file from which the formula must be readed
	 * 
	 * @throws NullPointerException
	 *             if the path of the file is null
	 */
	public ClaimLTLReader(String filePath) {
		super(NAME);
		Preconditions.checkNotNull(filePath,
				"The path of the file cannot be null");

		this.filePath = filePath;
	}

	/**
	 * returns the BA corresponding to the LTL formula stored in the file
	 * 
	 * @return the BA corresponding to the LTL formula stored in the file
	 * @throws Exception 
	 */
	public BA perform() throws Exception {

		String ltlFormula = "";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			boolean first = true;
			while (line != null) {
				if (!line.startsWith("//") && !line.isEmpty()) {
					if (first) {
						sb.append(line);
						first = false;
					} else {
						sb.append("&&" + line);
					}

				}
				line = br.readLine();
			}
			ltlFormula = sb.toString();
		} finally {
			br.close();
		}

		System.out.println(ltlFormula);
		BA ba = new LTLtoBATransformer("!(" + ltlFormula + ")").perform();

		return ba;
	}
}