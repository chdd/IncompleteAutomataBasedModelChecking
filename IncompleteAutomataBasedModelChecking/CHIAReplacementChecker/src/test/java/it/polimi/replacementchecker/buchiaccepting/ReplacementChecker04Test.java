package it.polimi.replacementchecker.buchiaccepting;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.automata.io.out.IBAToElementTrasformer;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.RefinementGenerator;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.constraints.io.out.constraint.ConstraintToElementTransformer;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class ReplacementChecker04Test {

    private static final String path = "it.polimi.replacementchecker/";

    private IBA model;
    private BA claim;

    private Replacement replacement;
    private AcceptingType acceptingPolicy;

    @Before
    public void setUp() throws Exception {
        this.model = new ModelReader(new File(getClass().getClassLoader()
                .getResource(path + "buchiaccepting/test04/model.xml")
                .getFile())).perform();

        this.claim = new ClaimReader(new File(getClass().getClassLoader()
                .getResource(path + "buchiaccepting/test04/claim.xml")
                .getFile())).perform();

        this.replacement = new ReplacementReader(new File(getClass()
                .getClassLoader()
                .getResource(path + "buchiaccepting/test04/replacement.xml")
                .getFile())).perform();
        this.acceptingPolicy = AcceptingType.BA;
    }

    @Test
    public void test() throws ParserConfigurationException, Exception {

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(this.acceptingPolicy, model,
                        claim));
        SatisfactionValue returnValue = checker.perform();
        assertTrue("The property must be possibly satisfied",
                returnValue == SatisfactionValue.POSSIBLYSATISFIED);

        System.out.println(checker.getUpperIntersectionBA().toString());

        IBA refinement = new ModelReader(new File(getClass().getClassLoader()
                .getResource(path + "buchiaccepting/test04/refinement.xml")
                .getFile())).perform();
        
        BA claim = new ClaimReader(new File(getClass().getClassLoader()
                .getResource(path + "buchiaccepting/test03/claim.xml")
                .getFile())).perform();
        
        Checker refinementChecker = new Checker(refinement, claim,
                AcceptingPolicy.getAcceptingPolicy(this.acceptingPolicy, model,
                        claim));
        assertTrue("The property must be not satisfied",
                refinementChecker.perform() == SatisfactionValue.NOTSATISFIED);
        ConstraintGenerator cg = new ConstraintGenerator(checker);
        Constraint constraint = cg.perform();

        System.out.println(new ElementToStringTransformer()
                .transform(new ConstraintToElementTransformer()
                        .transform(constraint)));
        SubProperty subproperty = constraint.getSubProperty(replacement
                .getModelState());
        ReplacementChecker rc = new ReplacementChecker(replacement,
                subproperty, AcceptingPolicy.getAcceptingPolicy(
                        this.acceptingPolicy, replacement.getAutomaton(),
                        subproperty.getAutomaton()));

        SatisfactionValue satisfactionValue = rc.perform();

        System.out.println(rc.getLowerIntersectionBA());
        assertTrue(satisfactionValue == SatisfactionValue.NOTSATISFIED);
        System.out.println(new ElementToStringTransformer()
                .transform(new ConstraintToElementTransformer()
                        .transform(constraint)));
        System.out.println(rc.perform());
        refinement = new RefinementGenerator(model, replacement).perform();

        System.out.println(new ElementToStringTransformer()
                .transform(new IBAToElementTrasformer().transform(refinement)));

    }

}
