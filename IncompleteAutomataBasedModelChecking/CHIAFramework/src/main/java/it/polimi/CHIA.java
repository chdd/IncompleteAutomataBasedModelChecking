package it.polimi;

import it.polimi.console.CHIAAutomataConsole;
import it.polimi.console.CHIAReplacementConsole;
import it.polimi.statemachine.events.AutomataEvent;
import it.polimi.statemachine.events.ReplacementEvent;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.List;

import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.CandidateListCompletionHandler;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;
import jline.console.history.FileHistory;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * is the main file of CHIA
 * 
 * @author Claudio Menghi
 *
 */
public class CHIA {

    /**
     * The console reader
     */
	private ConsoleReader console;

	/**
	 * contains the CHIA logger
	 */
	private static final Logger LOGGER = Logger.getLogger(CHIA.class);

	/**
	 * getting the output stream
	 */
	private static final PrintStream out = System.out;

	/**
	 * print the CHIA usage information
	 */
	protected void usage() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("********************************************************************\n");
		stringBuilder
				.append("WELCOME in CHIA (Checker for Incomplete Automata)\n");
		stringBuilder
				.append("You can change between the replacement and the automata mode at any time\n");
		stringBuilder.append("rep: enables replacement mode\n");
		stringBuilder.append("aut: enables repautomata mode\n");
		stringBuilder
				.append("********************************************************************\n");
		stringBuilder.append("aut: automata mode enabled\n");
		out.println(stringBuilder);
		

	}

	public CHIA() throws IOException {

		this.console = new ConsoleReader();
		this.console.setExpandEvents(false);
		console.setPrompt("CHIA> ");
		usage();

		if (ClassLoader.getSystemResource("History.txt") != null) {
			console.setHistory(new FileHistory(new File(ClassLoader
					.getSystemResource("History.txt").getPath())));
		} else {
			LOGGER.warn("The History file cannot be loaded");
		}
		LOGGER.info("CHIA Started");
		PropertyConfigurator.configure(ClassLoader
				.getSystemResource("log4j.properties"));

	}

	public void run() throws IOException {
		CHIAState chiaState = CHIAState.AUTOMATAMODE;

		CandidateListCompletionHandler ch = new CandidateListCompletionHandler();
		console.setCompletionHandler(ch);
		ch.setPrintSpaceAfterFullCompletion(false);

		CHIAAutomataConsole chiaAutomataConsole = new CHIAAutomataConsole(out);
		CHIAReplacementConsole chiaReplacementConsole = new CHIAReplacementConsole();

		List<Completer> chiaCompletors = AutomataEvent.computeCompleters();
		chiaCompletors.add(new StringsCompleter("rep"));
		console.addCompleter(new AggregateCompleter(chiaCompletors));

		String line;

		while ((line = console.readLine()) != null) {
			try {
				if (line.equals("rep")) {

					chiaState = CHIAState.REPLACEMENTMODE;
					this.removeCompleter();
					chiaCompletors = ReplacementEvent.computeCompleters();
					chiaCompletors.add(new StringsCompleter("aut"));
					console.addCompleter(new AggregateCompleter(chiaCompletors));
					LOGGER.info("replacement mode enabled");

				} else {
					if (line.equals("aut")) {
						chiaState = CHIAState.AUTOMATAMODE;
						this.removeCompleter();
						chiaCompletors = AutomataEvent.computeCompleters();
						chiaCompletors.add(new StringsCompleter("rep"));
						console.addCompleter(new AggregateCompleter(
								chiaCompletors));
						LOGGER.info("automata mode enabled");

					} else {
						if (line.equalsIgnoreCase("quit")
								|| line.equalsIgnoreCase("exit")) {

						} else {
							if (line.equalsIgnoreCase("cls")) {
								console.clearScreen();
							} else {
								if (chiaState.equals(CHIAState.REPLACEMENTMODE)) {
									try {
										ReplacementEvent.parse(line,
												chiaReplacementConsole);
									} catch (ParseException e) {
										LOGGER.info(e.getMessage());
									}

								} else {
									if (chiaState
											.equals(CHIAState.AUTOMATAMODE)) {
										try {
											AutomataEvent.parse(line,
													chiaAutomataConsole);
										} catch (ParseException e) {
											LOGGER.info(e.getMessage());
										}

									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				LOGGER.info(e.getMessage());
			}

		}
	}

	private void removeCompleter() {
		for (Completer completer : console.getCompleters()) {
			console.removeCompleter(completer);

		}
	}

	public static void main(String[] args) throws IOException {
		CHIA chia = new CHIA();
		chia.run();
	}

}