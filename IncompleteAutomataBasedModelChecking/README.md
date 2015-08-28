# CHIA

CHIA (CHecker for Incompete Automata) is a prototype tool realized as a Java 7 stand-alone application.  

The tool has been developed as a proof of concepts and does not aim to compete with state of the art model checking tools.  

It provides a command-line shell which allows the developer to 

* load the models, the claims, the constraints and the replacements of interest,

* check the incomplete models against the corresponding claims,

* whenever the claim is possibly satisfied it is possible to compute the constraint for the unspecified parts,

* check the replacement against the corresponding constraints.

The tool is developed as a <code>Maven</code> multi-module project. It is composed by different modules which encapsulate different parts of the CHIA logic. The core of the framework is the [<code>CHIAFramework</code>](https://github.com/claudiomenghi/IncompleteAutomataBasedModelChecking/tree/master/IncompleteAutomataBasedModelChecking/CHIAFramework) Module.

## Installing CHIA

To install CHIA:

* download the <code>CHIA.jar</code> file 
* run <code>java -jar CHIA.jar</code>

By running the <code>java -jar CHIA.jar</code> command the  shell of CHIA is executed. The user interacts with the tool using a set of commands.

## CHIA Commands

####General purpose commands
The general purpose commands include the commands which are used to switch the modality and to additional assistance commands:

* <code>?list</code> (<code>?l</code>): lists all commands with no prefix; 

* <code>?help</code> (<code>?h</code>): shows the info related with the command;

* <code>exit</code>: exits the CHIA framework

* <code>automata</code> (<code>aut</code>): enters the automata mode

* <code>replacement</code> (<code>rep</code>): enters the replacement mode


#### Automata mode commands
The automata commands can be used in the automata mode when a IBA (BA) is considered against the corresponding claim

* <code>loadModel modelFilePath</code> (<code>lm modelFilePath</code>): is used to load the model from an XML file. The XML file must match the IBA.xsd. It requires the parameter <code>modelFilePath</code>, i.e., the path of the file that contains the model to be checked.

* <code>displayModel</code> (<code>dispm</code>): is used to display the model into the console.

* <code>loadProperty propertyFilePath</code> (<code>lp propertyFilePath</code>): is used to load the property from an XML file. The XML file must match the BA.xsd. It requires the parameter <code>propertyFilePath</code>, i.e., the path of the file that contains the property

* <code>displayProperty</code> (<code>dispp</code>): is used to display the property into the console.

* <code>loadLTLProperty LTLProperty</code> (<code>lpLTL LTLProperty</code>): it is used to load the property from an LTL formula <code>LTLProperty</code> is the LTL formula that represents the property. The LTL formula can be created starting from a set of propositional symbols, i.e., <i>true</i>, <i>false</i> any lowercase string, a set of boolean operators, i.e., !  (negation), -> (implication), <-> (equivalence), ^ (and),  v  (or), and a set of temporal operators, []   (always)  <>   (eventually), U   (until), R   (realease) (Spin syntax : V),  X   (next).

* <code>check</code> (<code>ck</code>): is used to check the model against the specified claim. Before running the model checking procedure it is necessary to load the model and the claim to be considered. The check command can be performed with the optional parameter

* <code>computeConstraint [-p -f]</code> (<code>cc [-p -f]</code>): is used to compute the constraint corresponding to the model and the specified claim. It can be executed with the optional parameters <code>-p</code> and <code>-f</code>. If the 
<code>-p</code> flag is specified the reachability relation is not computed. If the <code>-f</code>, if also the <code>-f</code> flag is specified the flags of the incoming and outgoing transitions are not computed.
			
* <code>saveConstraint constraintFilePath</code> (<code>sc constraintFilePath</code>): is used to save the constraint in an XML file. The parameter <code>constraintFilePath</code> is the path of the file where the constraint must be saved

* <code>displayConstraint</code> (<code>dispc</code>): is used to display the constraint into the console.

#### Replacement mode commands
The automata commands can be used in the replacement mode when a Replacement (BA) is considered against the constraint or a specific sub-property.


* <code>loadConstraint constraintFilePath</code> (<code>lc constraintFilePath</code>): is used to load the constraint from an XML file. The XML file must match the <code>Constraint.xsd</code>. It requires the parameter <code>constraintFilePath</code>, i.e., the path of the file that contains the constraint to be considered;

* <code>displayConstraint</code> (<code>dispc</code>): is used to display the constraint into the console;

* <code>loadReplacement replacementFilePath</code> (<code>lr replacementFilePath</code>): is used to load the replacement from an XML file. The XML file must mach the <code>Replacement.xsd</code>. The parameter <code>replacementFilePath</code> is the path of the file that contains the replacement to be considered;

* <code>displayReplacement</code> (<code>dispr</code>): is used to display the replacement into the console.

* <code>check</code> (<code>ck</code>): is used to check the replacement  against the corresponding sub-property. 


