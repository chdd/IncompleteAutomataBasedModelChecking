/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.Constants;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.labeling.impl.LabelImplFactory;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author claudiomenghi
 *
 */
public class StringToLabelTransformerTest {

	private Label label;
	private LabelFactory<Label> factory;
	
	@Before
	public void setUp() {
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		propositions.add(new GraphProposition("b", true));
		
		this.factory=new LabelImplFactory();
		label=factory.create(propositions);
		
	}

	
	
	/**
	 * Test method for {@link it.polimi.automata.io.StringToLabelTransformer#transform(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNull() {
		new StringToLabelTransformer<Label>(factory).transform(null);
	}

	
	/**
	 * Test method for {@link it.polimi.automata.io.StringToLabelTransformer#transform(java.lang.String)}.
	 */
	@Test
	public void testTransform() {
		Label l=new StringToLabelTransformer<Label>(factory).transform("a"+Constants.AND+"!b");
		
		assertTrue(label.equals(l));
	}

}