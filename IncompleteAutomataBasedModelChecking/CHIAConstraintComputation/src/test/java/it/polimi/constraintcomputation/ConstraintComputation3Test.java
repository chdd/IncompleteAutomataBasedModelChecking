package it.polimi.constraintcomputation;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.SubProperty;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class ConstraintComputation3Test {

    private static final String path = "it.polimi.constraintcomputation/";

    private IBA model;
    private BA claim;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.model = new ModelReader(new File(getClass().getClassLoader()
                .getResource(path + "test3/model.xml").getFile())).perform();

        this.claim = new ClaimReader(new File(getClass().getClassLoader()
                .getResource(path + "test3/claim.xml").getFile())).perform();

    }

    @Test
    public void test() throws ParserConfigurationException, Exception {

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        SatisfactionValue returnValue = checker.perform();
        assertTrue("The property must be possibly satisfied",
                returnValue == SatisfactionValue.POSSIBLYSATISFIED);

        System.out.println(checker.getUpperIntersectionBA().toString());
        IntersectionBA upperIntersectionBA = checker.getUpperIntersectionBA();
        assertTrue(upperIntersectionBA.size()==31);

        ConstraintGenerator cg = new ConstraintGenerator(checker);
        Constraint constraint = cg.perform();

        assertTrue(constraint.getSubProperties().size() == 1);
        SubProperty subProperty = constraint.getSubProperties().iterator()
                .next();
        assertTrue(subProperty.isIndispensable() == true);

        BA subPropertyAutomaton = constraint.getSubProperties().iterator()
                .next().getAutomaton();

        assertTrue(subPropertyAutomaton.getStates().size()==6);
    }
}
