package it.polimi.statemachine.events;

import it.polimi.console.CHIAReplacementConsole;

import java.io.PrintStream;
import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.console.completer.FileNameCompleter;
import jline.console.completer.StringsCompleter;

public enum ReplacementEvent implements CommandEvent<CHIAReplacementConsole> {

    LOADCONSTRAINT {

        @Override
        public String getCommandMeaning() {
            return "loadConstraint";
        }

        @Override
        public String getCommand() {
            return "lc";
        }

        @Override
        public String getDescription() {
            return "It is used to load the constraint from an XML file. The XML file must mach the Constraint.xsd";
        }

        @Override
        public String getParams() {
            return "constraintFilePath:  the path of the file that contains the constraint to be loaded";
        }

        @Override
        public boolean requiresParams() {
            return true;
        }

        @Override
        public void executeCommand(String command,
                CHIAReplacementConsole console) throws Exception {
            console.loadConstraint(command.substring(command.indexOf(" ") + 1)
                    .replaceAll(" +$", ""));
        }

        /**
         * returns the String representation of the command
         * 
         * @return the String representation of the command
         */
        @Override
        public String toString() {
            return "SYNOPSIS \n \t" + this.getCommand() + "\n" + "NAME \n \t"
                    + getCommandMeaning() + "\n" + "PARAMS \n \t"
                    + this.getParams() + "\n" + "DESCRIPTION " + "\n \t"
                    + this.getDescription() + " \n ";
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
        public void executeCommand(String command,
                CHIAReplacementConsole console) throws Exception {
            PrintStream out = System.out;
            boolean founded = false;
            String commandOfInterest = command
                    .substring(command.indexOf(" ") + 1);
            for (ReplacementEvent analyzedCommand : ReplacementEvent.values()) {
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
        public void executeCommand(String command,
                CHIAReplacementConsole console) throws Exception {
            console.dispConstraint();
        }

        /**
         * returns the String representation of the command
         * 
         * @return the String representation of the command
         */
        @Override
        public String toString() {
            return "SYNOPSIS \n \t" + this.getCommand() + "\n" + "NAME \n \t"
                    + getCommandMeaning() + "\n" + "PARAMS \n \t"
                    + this.getParams() + "\n" + "DESCRIPTION " + "\n \t"
                    + this.getDescription() + " \n ";
        }
    },

    LOADREPLACEMENT {

        @Override
        public String getCommandMeaning() {
            return "loadReplacement";
        }

        @Override
        public String getCommand() {
            return "lr";
        }

        @Override
        public String getDescription() {
            return "IT is used to load the replacement from an XML file. The XML file must mach the Replacement.xsd";
        }

        @Override
        public String getParams() {
            return "replacementFilePath: is the path of the file that contains the replacement to be considered";
        }

        @Override
        public boolean requiresParams() {
            return true;
        }

        @Override
        public void executeCommand(String command,
                CHIAReplacementConsole console) throws Exception {
            console.loadReplacement(command.substring(command.indexOf(" ") + 1)
                    .replaceAll(" +$", ""));
        }

        /**
         * returns the String representation of the command
         * 
         * @return the String representation of the command
         */
        @Override
        public String toString() {
            return "SYNOPSIS \n \t" + this.getCommand() + "\n" + "NAME \n \t"
                    + getCommandMeaning() + "\n" + "PARAMS \n \t"
                    + this.getParams() + "\n" + "DESCRIPTION " + "\n \t"
                    + this.getDescription() + " \n ";
        }
    },
    DISPLAYREPLACEMENT {

        @Override
        public String getCommandMeaning() {
            return "displayReplacement";
        }

        @Override
        public String getCommand() {
            return "dispr";
        }

        @Override
        public String getDescription() {
            return "It is used to display the replacement into the console.";
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
        public void executeCommand(String command,
                CHIAReplacementConsole console) throws Exception {
            console.dispReplacement();
        }

        /**
         * returns the String representation of the command
         * 
         * @return the String representation of the command
         */
        @Override
        public String toString() {
            return "SYNOPSIS \n \t" + this.getCommand() + "\n" + "NAME \n \t"
                    + getCommandMeaning() + "\n" + "PARAMS \n \t"
                    + this.getParams() + "\n" + "DESCRIPTION " + "\n \t"
                    + this.getDescription() + " \n ";
        }

    },

    CHECK {

        @Override
        public String getCommandMeaning() {
            return "check";
        }

        @Override
        public String getCommand() {
            return "ck";
        }

        @Override
        public String getDescription() {
            return "It used to check the replacement against the constraint previously generated.";
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
        public void executeCommand(String command,
                CHIAReplacementConsole console) throws Exception {
            console.replacementChecking();
        }

        /**
         * returns the String representation of the command
         * 
         * @return the String representation of the command
         */
        @Override
        public String toString() {
            return "SYNOPSIS \n \t" + this.getCommand() + "\n" + "NAME \n \t"
                    + getCommandMeaning() + "\n" + "PARAMS \n \t" + "\n"
                    + "DESCRIPTION " + "\n \t" + this.getDescription() + " \n ";
        }

    },
    SAVEREFINEMENT {

        @Override
        public String getCommandMeaning() {
            return "save refinement";
        }

        @Override
        public String getCommand() {
            return "sref";
        }

        @Override
        public String getDescription() {
            return "starting from the intial model and the replacement associated to one of its black box states generate the corresponding"
                    + "refinement.";
        }

        @Override
        public String getParams() {
            return "The path of the file where the refinement must be saved";
        }

        @Override
        public boolean requiresParams() {
            return true;
        }

        @Override
        public void executeCommand(String command,
                CHIAReplacementConsole console) throws Exception {

            console.saveRefinement(command.substring(command.indexOf(" ") + 1)
                    .replaceAll(" +$", ""));
        }

    };
    public static void parse(String toBeParsed, CHIAReplacementConsole console)
            throws Exception {

        boolean founded = false;
        for (ReplacementEvent command : ReplacementEvent.values()) {
            if (toBeParsed.split(" ")[0].equals(command.getCommand())
                    || toBeParsed.equals(command.getCommand())) {
                command.executeCommand(toBeParsed, console);
                founded = true;
            }
        }
        if (!founded) {
            throw new ParseException("The command " + toBeParsed
                    + " is not a valid command", 0);
        }
    }

    public static Set<String> getShortCuts() {
        Set<String> shortcuts = new HashSet<String>();
        for (ReplacementEvent command : ReplacementEvent.values()) {
            shortcuts.add(command.getCommand());
        }
        return shortcuts;
    }

    public static Set<String> getCommands() {
        Set<String> commands = new HashSet<String>();
        for (ReplacementEvent command : ReplacementEvent.values()) {
            commands.add(command.getCommand());
        }
        return commands;
    }

    public static List<Completer> computeCompleters() {
        List<Completer> chiaCompletors = new LinkedList<Completer>();

        for (ReplacementEvent alternative : ReplacementEvent.values()) {

            if (alternative.getParams() != null) {
                chiaCompletors.add(new ArgumentCompleter(new StringsCompleter(
                        alternative.getCommand()), new FileNameCompleter()));

            } else {
                chiaCompletors.add(new StringsCompleter(alternative
                        .getCommand()));

            }
        }

        return chiaCompletors;

    }
}
