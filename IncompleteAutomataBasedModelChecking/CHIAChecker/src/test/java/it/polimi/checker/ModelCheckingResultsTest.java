/**
 * 
 */
package it.polimi.checker;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author claudiomenghi
 * 
 */
public class ModelCheckingResultsTest {

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#ModelCheckingResults()}.
	 */
	@Test
	public void testModelCheckingResults() {
		assertNotNull(new ModelCheckingResults(true, true, true));
	}

	/**
	 * Test method for {@link it.polimi.checker.ModelCheckingResults#reset()}.
	 */
	@Test
	public void testReset() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		
		results.reset();
		assertTrue(results.getSubpropertyTime()==0);
		assertTrue(results.getViolationTime() == 0);
		assertTrue(results.getPossibleViolationTime() == 0);
		
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getViolationTime()}.
	 */
	@Test
	public void testGetViolationTime() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getViolationTime() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setViolationTime(double)}.
	 */
	@Test
	public void testSetViolationTime() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setViolationTime(2);
		assertTrue(results.getViolationTime() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getPossibleViolationTime()}
	 * .
	 */
	@Test
	public void testGetPossibleViolationTime() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getPossibleViolationTime() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setPossibleViolationTime(double)}
	 * .
	 */
	@Test
	public void testSetPossibleViolationTime() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setPossibleViolationTime(2);
		assertTrue(results.getPossibleViolationTime() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getResult()}.
	 */
	@Test
	public void testGetResult() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getResult() == SatisfactionValue.NOTSATISFIED);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setResult(int)}.
	 */
	@Test
	public void testSetResult() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setResult(SatisfactionValue.SATISFIED);
		assertTrue(results.getResult() == SatisfactionValue.SATISFIED);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getSubpropertyTime()}
	 * .
	 */
	@Test
	public void testGetConstraintComputationTime() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getSubpropertyTime() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setSubPropertyComputationTime(double)}
	 * .
	 */
	@Test
	public void testSetConstraintComputationTime() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setSubPropertyComputationTime(2);
		assertTrue(results.getSubpropertyTime() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getNumStatesSpecification()}
	 * .
	 */
	@Test
	public void testGetNumStatesSpecification() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getNumStatesSpecification() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setNumStatesSpecification(int)}
	 * .
	 */
	@Test
	public void testSetNumStatesSpecification() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setNumStatesSpecification(2);
		assertTrue(results.getNumStatesSpecification() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getNumAcceptStatesSpecification()}
	 * .
	 */
	@Test
	public void testGetNumAcceptStatesSpecification() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getNumAcceptStatesSpecification() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setNumAcceptStatesSpecification(int)}
	 * .
	 */
	@Test
	public void testSetNumAcceptStatesSpecification() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setNumAcceptStatesSpecification(2);
		assertTrue(results.getNumAcceptStatesSpecification() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getNumTransitionSpecification()}
	 * .
	 */
	@Test
	public void testGetNumTransitionSpecification() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getNumTransitionSpecification() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setNumTransitionSpecification(int)}
	 * .
	 */
	@Test
	public void testSetNumTransitionSpecification() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setNumTransitionSpecification(2);
		assertTrue(results.getNumTransitionSpecification() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getNumStatesModel()}.
	 */
	@Test
	public void testGetNumStatesModel() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getNumStatesModel() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setNumStatesModel(int)}.
	 */
	@Test
	public void testSetNumStatesModel() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setNumStatesModel(2);
		assertTrue(results.getNumStatesModel() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getNumAcceptStatesModel()}.
	 */
	@Test
	public void testGetNumAcceptStatesModel() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getNumAcceptStatesModel() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setNumAcceptStatesModel(int)}
	 * .
	 */
	@Test
	public void testSetNumAcceptStatesModel() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setNumAcceptStatesModel(2);
		assertTrue(results.getNumAcceptStatesModel() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getNumTransitionModel()}.
	 */
	@Test
	public void testGetNumTransitionModel() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getNumTransitionModel() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setNumTransitionModel(int)}
	 * .
	 */
	@Test
	public void testSetNumTransitionModel() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setNumTransitionModel(2);
		assertTrue(results.getNumTransitionModel() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getNumTransparentStatesModel()}
	 * .
	 */
	@Test
	public void testGetNumTransparentStatesModel() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getNumTransparentStatesModel() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setNumTransparentStatesModel(int)}
	 * .
	 */
	@Test
	public void testSetNumTransparentStatesModel() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setNumTransparentStatesModel(2);
		assertTrue(results.getNumTransparentStatesModel() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#toStringHeader()}.
	 */
	@Test
	public void testToStringHeader() {
		assertNotNull(new ModelCheckingResults(true, true, true).toStringHeader());
	}

	/**
	 * Test method for {@link it.polimi.checker.ModelCheckingResults#toString()}
	 * .
	 */
	@Test
	public void testToString() {
		assertNotNull(new ModelCheckingResults(true, true, true).toString());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getNumStatesIntersection()}
	 * .
	 */
	@Test
	public void testGetNumStatesIntersection() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getNumStatesIntersection() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setNumStatesIntersection(int)}
	 * .
	 */
	@Test
	public void testSetNumStatesIntersection() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setNumStatesIntersection(2);
		assertTrue(results.getNumStatesIntersection() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getNumAcceptingStatesIntersection()}
	 * .
	 */
	@Test
	public void testGetNumAcceptingStatesIntersection() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getNumAcceptingStatesIntersection() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setNumAcceptingStatesIntersection(int)}
	 * .
	 */
	@Test
	public void testSetNumAcceptingStatesIntersection() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setNumAcceptingStatesIntersection(2);
		assertTrue(results.getNumAcceptingStatesIntersection() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getNumInitialStatesIntersection()}
	 * .
	 */
	@Test
	public void testGetNumInitialStatesIntersection() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getNumInitialStatesIntersection() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setNumInitialStatesIntersection(int)}
	 * .
	 */
	@Test
	public void testSetNumInitialStatesIntersection() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setNumInitialStatesIntersection(2);
		assertTrue(results.getNumInitialStatesIntersection() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getNumMixedStatesIntersection()}
	 * .
	 */
	@Test
	public void testGetNumMixedStatesIntersection() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getNumMixedStatesIntersection() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setNumMixedStatesIntersection(int)}
	 * .
	 */
	@Test
	public void testSetNumMixedStatesIntersection() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setNumMixedStatesIntersection(2);
		assertTrue(results.getNumMixedStatesIntersection() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getTotalVerificationTime()}
	 * .
	 */
	@Test
	public void testGetTotalVerificationTime() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getTotalVerificationTime() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setTotalTime(double)}.
	 */
	@Test
	public void testSetTotalTime() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setTotalTime(2);
		assertTrue(results.getTotalVerificationTime() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#getSimplificationTime()}.
	 */
	@Test
	public void testGetSimplificationTime() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		assertTrue(results.getSimplificationTime() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingResults#setSimplificationTime(double)}
	 * .
	 */
	@Test
	public void testSetSimplificationTime() {
		ModelCheckingResults results = new ModelCheckingResults(true, true, true);
		results.setSimplificationTime(2);
		assertTrue(results.getSimplificationTime() == 2);
	}
}
