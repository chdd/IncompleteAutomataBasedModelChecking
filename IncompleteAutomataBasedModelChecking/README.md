# CHIA

CHIA (CHecker for Incompete Automata) is a prototype tool realized as a Java 7 stand-alone application.  

The tool has been developed as a proof of concepts and does not aim to compete with state of the art model checking tools.  

It provides a command-line shell which allows the developer to 

* load the models, the claims, the constraints and the replacements of interest,

* check the incomplete models against the corresponding claims,

* whenever the claim is possibly satisfied it is possible to compute the constraint for the unspecified parts,

* check the replacement against the corresponding constraints.

The tool is developed as a <code>Maven</code> multi-module project. It is composed by different modules which encapsulate different parts of the CHIA logic. The core of the framework is the [<code>CHIAFramework</code>](https://github.com/claudiomenghi/IncompleteAutomataBasedModelChecking/tree/master/IncompleteAutomataBasedModelChecking/CHIAFramework) Module.