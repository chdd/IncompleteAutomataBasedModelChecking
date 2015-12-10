package it.polimi.statemachine.events;

import it.polimi.console.CHIAAutomataConsole;

import java.io.PrintStream;
import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;

import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.console.completer.FileNameCompleter;
import jline.console.completer.StringsCompleter;

public enum AutomataEvent implements CommandEvent<CHIAAutomataConsole> {

	
	LOADMODEL {

		@Override
		public String getCommand() {
			return "lm";
		}

		@Override
		public String getDescription() {
			return "Is used to load the model from an XML file. The XML file must mach the IBA.xsd.";
		}

		@Override
		public String getCommandMeaning() {
			return "loadModel";
		}

		@Override
		public String getParams() {
			return "modelFilePath:  the path of the file that contains the model to be checked";
		}

		@Override
		public boolean requiresParams() {

			return true;
		}

		/**
		 * @throws NullPointerException
		 *             if the command to be executed or the console is null
		 * @throws IllegalArgumentException
		 *             if the command requires a parameter but no parameters are
		 *             provided
		 */
		@Override
		public void executeCommand(String command, CHIAAutomataConsole console)
				throws Exception {
			Preconditions.checkNotNull(command,
					"The command to be executed cannot be null");
			Preconditions.checkNotNull(console,
					"The console to be updated cannot be null");
			Preconditions.checkArgument(command.contains(" "),
					"No argument is provided");

			console.loadModel(command.substring(command.indexOf(" ") + 1)
					.replaceAll(" +$", ""));
		}
		/**
		 * returns the String representation of the command 
		 * @return the String representation of the command
		 */
		@Override
		public String toString() {
			return "SYNOPSIS \n \t"
					+ this.getCommand()
					+ "\n"
					+ "NAME \n \t"
					+ getCommandMeaning()
					+ "\n"
					+ ((this.getParams() != null) ? "PARAMS \n \t"
							+ this.getParams() : "") + "\n" + "DESCRIPTION "
					+ "\n \t" + this.getDescription() + " \n ";
		}
	},
	DISPLAYMODEL {
		@Override
		public String getCommand() {
			return "dispm";
		}

		@Override
		public String getDescription() {
			return "It is used to display the model into the console.";
		}

		@Override
		public String getCommandMeaning() {
			return "displayModel";
		}

		@Override
		public String getParams() {
			return null;
		}

		

		@Override
		public void executeCommand(String command, CHIAAutomataConsole console)
				throws Exception {

			console.dispModel();
		}

		@Override
		public boolean requiresParams() {
			return false;
		}
		/**
		 * returns the String representation of the command 
		 * @return the String representation of the command
		 */
		@Override
		public String toString() {
			return "SYNOPSIS \n \t"
					+ this.getCommand()
					+ "\n"
					+ "NAME \n \t"
					+ getCommandMeaning()
					+ "\n"
					+ ((this.getParams() != null) ? "PARAMS \n \t"
							+ this.getParams() : "") + "\n" + "DESCRIPTION "
					+ "\n \t" + this.getDescription() + " \n ";
		}
	},
	LOADPROPERTY {
		@Override
		public String getCommand() {
			return "lp";
		}

		@Override
		public String getDescription() {
			return "Is used to load the property from an XML file. The XML file must mach the BA.xsd.";
		}

		@Override
		public String getCommandMeaning() {
			return "loadProperty";
		}

		@Override
		public String getParams() {
			return "file";
		}

		/**
		 * returns the String representation of the command 
		 * @return the String representation of the command
		 */
		@Override
		public String toString() {
			return "SYNOPSIS \n \t"
					+ this.getCommand()
					+ "\n"
					+ "NAME \n \t"
					+ getCommandMeaning()
					+ "\n"
					+ ((this.getParams() != null) ? "PARAMS \n \t"
							+ this.getParams() : "") + "\n" + "DESCRIPTION "
					+ "\n \t" + this.getDescription() + " \n ";
		}

		@Override
		public boolean requiresParams() {
			return true;
		}

		@Override
		public void executeCommand(String command, CHIAAutomataConsole console)
				throws Exception {
			console.loadProperty(command.substring(command.indexOf(" ") + 1)
					.replaceAll(" +$", ""));
		}
	},

	LOADLTLPROPERTY {

		@Override
		public String getCommandMeaning() {
			return "loadLTLProperty";
		}

		@Override
		public String getCommand() {
			return "lpLTL";
		}

		@Override
		public String getDescription() {
			return "It is used to load the property from an LTL formula";
		}

		@Override
		public String getParams() {
			return "LTLFormula: is the LTL formula that represents the property."
					+ "The LTL formula can be created starting from a set of propositional symbol \n"+ 
					"true, false any lowercase string"+
					"A set of boolean operators,\n "+
					"\t ! (negation)\n"+
					"\t -> (implication) \n"+
					"\t <-> (equivalence) \n"+
					"\t ^ (and)\n"+
					"\t V (or),"+
					"A set of temporal operators,\n"+ 
					"\t [] (always)\n"+
					"\t <> (eventually)"+
					"\t U (until)"+
					"\t X (next).";
		}

		@Override
		public boolean requiresParams() {
			return true;
		}

		@Override
		public void executeCommand(String command, CHIAAutomataConsole console)
				throws Exception {

			console.loadLTLProperty(command.substring(command.indexOf(" ") + 1)
					.replaceAll(" +$", ""));
		}
		/**
		 * returns the String representation of the command 
		 * @return the String representation of the command
		 */
		@Override
		public String toString() {
			return "SYNOPSIS \n \t"
					+ this.getCommand()
					+ "\n"
					+ "NAME \n \t"
					+ getCommandMeaning()
					+ "\n"
					+ ((this.getParams() != null) ? "PARAMS \n \t"
							+ this.getParams() : "") + "\n" + "DESCRIPTION "
					+ "\n \t" + this.getDescription() + " \n ";
		}

	},

	DISPLAYPROPERTY {
		@Override
		public String getCommand() {
			return "dispp";
		}

		@Override
		public String getDescription() {
			return "It is used to display the property into the console.";
		}

		@Override
		public String getCommandMeaning() {
			return "displayProperty";
		}

		@Override
		public String getParams() {
			return null;
		}

		/**
		 * returns the String representation of the command 
		 * @return the String representation of the command
		 */
		@Override
		public String toString() {
			return "SYNOPSIS \n \t"
					+ this.getCommand()
					+ "\n"
					+ "NAME \n \t"
					+ getCommandMeaning()
					+ "\n"
					+ ((this.getParams() != null) ? "PARAMS \n \t"
							+ this.getParams() : "") + "\n" + "DESCRIPTION "
					+ "\n \t" + this.getDescription() + " \n ";
		}

		@Override
		public boolean requiresParams() {
			return false;
		}

		@Override
		public void executeCommand(String command, CHIAAutomataConsole console)
				throws Exception {
			console.dispClaim();

		}
	},
	CHECK {
		@Override
		public String getCommand() {
			return "ck";
		}

		@Override
		public String getDescription() {
			return "Is used to check the model against the specified claim. Before running the model checking procedure it is necessary to load the model and the claim to be considered.";
		}

		@Override
		public String getCommandMeaning() {
			return "check";
		}

		@Override
		public String getParams() {
			return null;
		}

		/**
		 * returns the String representation of the command 
		 * @return the String representation of the command
		 */
		@Override
		public String toString() {
			return "SYNOPSIS \n \t"
					+ this.getCommand()
					+ "\n"
					+ "NAME \n \t"
					+ getCommandMeaning()
					+ "\n"
					+ ((this.getParams() != null) ? "PARAMS \n \t"
							+ this.getParams() : "") + "\n" + "DESCRIPTION "
					+ "\n \t" + this.getDescription() + " \n ";
		}

		@Override
		public boolean requiresParams() {
			return false;
		}

		@Override
		public void executeCommand(String command, CHIAAutomataConsole console)
				throws Exception {
			console.check();

		}
	},
	EXIT {
		@Override
		public String getCommand() {
			return "ex";
		}

		@Override
		public String getDescription() {
			return "Returns to the CHIA main console.";
		}

		@Override
		public String getCommandMeaning() {
			return "exit";
		}

		@Override
		public String getParams() {
			return null;
		}

		/**
		 * returns the String representation of the command 
		 * @return the String representation of the command
		 */
		@Override
		public String toString() {
			return "SYNOPSIS \n \t"
					+ this.getCommand()
					+ "\n"
					+ "NAME \n \t"
					+ getCommandMeaning()
					+ "\n"
					+ ((this.getParams() != null) ? "PARAMS \n \t"
							+ this.getParams() : "") + "\n" + "DESCRIPTION "
					+ "\n \t" + this.getDescription() + " \n ";
		}

		@Override
		public boolean requiresParams() {
			return false;
		}

		@Override
		public void executeCommand(String command, CHIAAutomataConsole console)
				throws Exception {
			console.exit();

		}
	},

	COMPUTECONSTRAINT {

		@Override
		public String getCommandMeaning() {
			return "computeConstraint";
		}

		@Override
		public String getCommand() {
			return "cc";
		}

		@Override
		public String getDescription() {
			return "Is used to compute the constraint corresponding to the model and the specified claim.";
		}

		@Override
		public String getParams() {
			return null;
		}

		@Override
		public boolean requiresParams() {
			return false;
		}

		@Override
		public void executeCommand(String command, CHIAAutomataConsole console)
				throws Exception {
			console.computeConstraint();
		}
		
		/**
		 * returns the String representation of the command 
		 * @return the String representation of the command
		 */
		@Override
		public String toString() {
			return "SYNOPSIS \n \t"
					+ this.getCommand()
					+ "\n"
					+ "NAME \n \t"
					+ getCommandMeaning()
					+ "\n"
					+ ((this.getParams() != null) ? "PARAMS \n \t"
							+ this.getParams() : "") + "\n" + "DESCRIPTION "
					+ "\n \t" + this.getDescription() + " \n ";
		}
	},

	HELP {

		@Override
		public String getCommandMeaning() {
			return "help";
		}

		@Override
		public String getCommand() {
			return "help";
		}

		@Override
		public String getDescription() {
			return "shows the params of the command.";
		}

		@Override
		public String getParams() {
			return "command: is the command to be performed";
		}

		@Override
		public boolean requiresParams() {
			return true;
		}

		@Override
		public void executeCommand(String command, CHIAAutomataConsole console)
				throws Exception {
			PrintStream out = System.out;
			boolean founded = false;
			String commandOfInterest = command
					.substring(command.indexOf(" ") + 1);
			for (AutomataEvent analyzedCommand : AutomataEvent.values()) {
				if (commandOfInterest.equals(analyzedCommand.getCommand())
						&& !commandOfInterest.equals(HELP.getCommand())) {

					out.println(analyzedCommand.toString());
					founded = true;
				}
			}
			if (!founded) {
				out.println("The command: " + commandOfInterest
						+ " is not a valid command");
			}

		}

	},

	SAVECONSTRAINT {

		@Override
		public String getCommandMeaning() {
			return "saveConstraint";
		}

		@Override
		public String getCommand() {
			return "sc";
		}

		@Override
		public String getDescription() {
			return "It is used to save the constraint in an XML file.";
		}

		@Override
		public String getParams() {
			return "constraintFilePath: is the path of the file where the constraint must be saved";
		}

		@Override
		public boolean requiresParams() {
			return true;
		}

		@Override
		public void executeCommand(String command, CHIAAutomataConsole console)
				throws Exception {
			console.saveConstraint(command.substring(command.indexOf(" ") + 1)
					.replaceAll(" +$", ""));

		}
		/**
		 * returns the String representation of the command 
		 * @return the String representation of the command
		 */
		@Override
		public String toString() {
			return "SYNOPSIS \n \t"
					+ this.getCommand()
					+ "\n"
					+ "NAME \n \t"
					+ getCommandMeaning()
					+ "\n"
					+ ((this.getParams() != null) ? "PARAMS \n \t"
							+ this.getParams() : "") + "\n" + "DESCRIPTION "
					+ "\n \t" + this.getDescription() + " \n ";
		}

	},
	DISPLAYCONSTRAINT {

		@Override
		public String getCommandMeaning() {
			return "displayConstraint";
		}

		@Override
		public String getCommand() {
			return "dispc";
		}

		@Override
		public String getDescription() {
			return "It is used to display the constraint into the console.";
		}

		@Override
		public String getParams() {
			return null;
		}

		@Override
		public boolean requiresParams() {
			return false;
		}

		@Override
		public void executeCommand(String command, CHIAAutomataConsole console)
				throws Exception {
			console.dispConstraint();
		}
		/**
		 * returns the String representation of the command 
		 * @return the String representation of the command
		 */
		@Override
		public String toString() {
			return "SYNOPSIS \n \t"
					+ this.getCommand()
					+ "\n"
					+ "NAME \n \t"
					+ getCommandMeaning()
					+ "\n"
					+ ((this.getParams() != null) ? "PARAMS \n \t"
							+ this.getParams() : "") + "\n" + "DESCRIPTION "
					+ "\n \t" + this.getDescription() + " \n ";
		}

	};

	public static Set<String> getShortCuts() {
		Set<String> shortcuts = new HashSet<String>();
		for (AutomataEvent command : AutomataEvent.values()) {
			shortcuts.add(command.getCommand());
		}
		return shortcuts;
	}

	public static Set<String> getCommands() {
		Set<String> commands = new HashSet<String>();
		for (AutomataEvent command : AutomataEvent.values()) {
			commands.add(command.getCommandMeaning());
		}
		return commands;
	}

	public static void parse(String toBeParsed, CHIAAutomataConsole console)
			throws Exception {

		boolean founded=false;
		for (AutomataEvent command : AutomataEvent.values()) {
			if (toBeParsed.split(" ")[0].equals(command.getCommand())
					|| toBeParsed.equals(command.getCommand())) {
				command.executeCommand(toBeParsed, console);
				founded=true;
			}
		}
		if(!founded){
			throw new ParseException("The command "+toBeParsed+" is not a valid command", 0);
		}
	}

	public static List<Completer> computeCompleters() {
		List<Completer> chiaCompletors = new LinkedList<Completer>();

		for (AutomataEvent alternative : AutomataEvent.values()) {

			if (alternative.getParams() != null) {
				if (alternative.getCommand() != null) {
					chiaCompletors.add(new ArgumentCompleter(
							new StringsCompleter(alternative.getCommand()),
							new FileNameCompleter()));
				}

			} else {
				if (alternative.getCommand() != null) {
					chiaCompletors.add(new StringsCompleter(alternative
							.getCommand()));
				}
			}
		}

		return chiaCompletors;

	}
}
