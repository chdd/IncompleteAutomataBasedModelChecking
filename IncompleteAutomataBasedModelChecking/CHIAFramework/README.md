# CHIAFramework

The <i>CHIAFramework</i> is the main module of <code>CHIA</code>. It contains the entry point for the use of the framework, i.e., the method to run the command line shell. The command line shell is implemented using the <code>Cliche</code> library,  which allows to create interactive command-line user interfaces.

The state of <code>CHIA</code> changes in response to the user requests as specified in the following Figure. 

![CHIAFramework](./CHIAFramework.png)



In the initial state the user can select the modality of interest, i.e., if he/she wants to use the automata checker (<code>aut</code>) or the replacement checker (<code>rep</code>). 

In the automata mode the user is  able to load the claim from an automaton saved in an appropriate file <code>loadProperty</code> or generating the automaton from an LTL formula <code>loadLTLProperty</code>. Similarly, the model of the system is loaded from an appropriate file that contains the corresponding automaton through the command <code>loadModel</code>. After both the model and the claim has been loaded, the developer may  check if the system possesses the properties of interest. If the property is possibly satisfied the computeConstraint command allows the computation of the corresponding constraint.

Whenever the replacement checking mode is activated (<code>rep</code>), the developer has to load the replacement (<code>loadReplacement</code>) and the corresponding constraint  (<code>loadConstraint</code>). The <code>check</code> command allows to verify whether the refinement of the automaton possesses the properties of interest.



