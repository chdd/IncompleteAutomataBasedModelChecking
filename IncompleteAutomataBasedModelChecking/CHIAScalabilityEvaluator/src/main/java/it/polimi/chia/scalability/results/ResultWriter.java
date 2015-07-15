package it.polimi.chia.scalability.results;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Locale;

import com.google.common.base.Preconditions;

public class ResultWriter {

	private File file;
	private final FileWriter fileWriter;

	/**
	 * creates a new {@link ResultWriter} which is in charge of writing the
	 * verification results on a file
	 * 
	 * @param file
	 *            the file where the verification results must b written
	 * @throws IOException
	 *             if opening a file writer over the specified file generates an
	 *             exception
	 * @throws NullPointerException
	 *             if the file where the results must be written is null
	 */
	public ResultWriter(String filePath) throws IOException {

		if (!Files.exists(Paths.get(filePath), LinkOption.NOFOLLOW_LINKS)) {
			this.file = new File(filePath);
			this.fileWriter = new FileWriter(this.file);
			this.fileWriter.write("id: the id of the configuration \n");

			this.fileWriter
					.write("td: transitionDensity the number of transitions of the BA \n");
			this.fileWriter
					.write("ad: acceptingDensity the number of accepting states of the BA \n");
			this.fileWriter
					.write("ns: nStates the number of states of the BA \n");

			this.fileWriter
					.write("tsd: transparentDensity the density of the transparent states of the IBA \n");
			this.fileWriter
					.write("rd: the density of the states to be inserted into the replacement \n");
			this.fileWriter
					.write("propositions: The set of propositions to be considered in the generation of the random automaton \n");

			this.fileWriter
					.write("ist: initialSatisfactioValue the satisfaction of the property over the initial model \n");
			this.fileWriter
					.write("fst: finalSatisfactioValue the satisfaction of the property over the replacement \n");

			this.fileWriter
					.write("ts: trivially satisfied is true iff the verification of the replacement is trivially possibly satisfied \n");

			this.fileWriter
					.write("sRefv: sizeOfTheRefinementVerification the size required to verify the refinement \n");
			this.fileWriter
					.write("sRepv: sizeOfTheReplacementVerification the size required to verify the replacement \n");

			this.fileWriter
					.write("tRefv: refinementVerificationTime the time required to verify the refinement \n");
			this.fileWriter
					.write("tRepv: replacementVerificationTime the time required to verify the replacement \n \n");

			this.fileWriter
					.write("id \t td \t ad \t ns \t tsd \t rd \t propositions \t ist \t fst \t ts\t sRefv \t sRepv \t tRefv \t tRepv \n");

		} else {
			this.fileWriter = new FileWriter(filePath,true);

		}
	}

	/**
	 * appends the record to the specified file
	 * 
	 * @param record
	 *            is the record to be added to the current file
	 * @throws IOException
	 *             is generated if writing the results in the file generates an
	 *             exception
	 * @throws NullPointerException
	 *             if the record to be added is null
	 */
	public void append(Record record) throws IOException {
		Preconditions.checkNotNull(record,
				"The record to be added to the file cannot be null");
		String stringRecord = "";
		stringRecord += record.getConfiguration().getConfigurationId() + "\t";
		stringRecord += String.format(Locale.ENGLISH, "%.2f", record
				.getConfiguration().getTransitionDensity())
				+ "\t";
		stringRecord += String.format(Locale.ENGLISH, "%.2f", record
				.getConfiguration().getAcceptingDensity())
				+ "\t";
		stringRecord += record.getConfiguration().getnStates() + "\t";
		stringRecord += String.format(Locale.ENGLISH, "%.2f", record
				.getConfiguration().getTransparentDensity())
				+ "\t";
		stringRecord += String.format(Locale.ENGLISH, "%.2f", record
				.getConfiguration().getReplacementDensity())
				+ "\t";
		stringRecord += record.getConfiguration().getPropositions() + "\t";
		stringRecord += record.getInitialSatisfactioValue() + "\t";
		stringRecord += record.getFinalSatisfactioValue() + "\t";
		stringRecord += record.isTriviallySatisfied() + "\t";

		stringRecord += record.getSizeOfTheRefinementVerification() + "\t";
		stringRecord += record.getSizeOfTheReplacementVerification() + "\t";
		stringRecord += record.getRefinementVerificationTime() + "\t";
		stringRecord += record.getReplacementVerificationTime() + "\t";
		stringRecord += "\n";
		this.fileWriter.write(stringRecord);
		this.fileWriter.flush();
	}

	/**
	 * 
	 * @return the file where the results must be written
	 */
	protected File getFile() {
		return file;
	}
}
