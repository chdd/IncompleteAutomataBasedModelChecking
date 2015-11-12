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
public class ModelCheckingInfoTest {

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#ModelCheckingResults()}.
	 */
	@Test
	public void testModelCheckingResults() {
		assertNotNull(new ModelCheckingInfo(true, true, true));
	}

	/**
	 * Test method for {@link it.polimi.checker.ModelCheckingInfo#reset()}.
	 */
	@Test
	public void testReset() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		
		results.reset();
		assertTrue(results.getSubpropertyTime()==0);
		assertTrue(results.getViolationTime() == 0);
		assertTrue(results.getPossibleViolationTime() == 0);
		
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getViolationTime()}.
	 */
	@Test
	public void testGetViolationTime() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getViolationTime() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setViolationTime(double)}.
	 */
	@Test
	public void testSetViolationTime() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setViolationTime(2);
		assertTrue(results.getViolationTime() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getPossibleViolationTime()}
	 * .
	 */
	@Test
	public void testGetPossibleViolationTime() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getPossibleViolationTime() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setPossibleViolationTime(double)}
	 * .
	 */
	@Test
	public void testSetPossibleViolationTime() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setPossibleViolationTime(2);
		assertTrue(results.getPossibleViolationTime() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getResult()}.
	 */
	@Test
	public void testGetResult() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getResult() == SatisfactionValue.NOTSATISFIED);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setResult(int)}.
	 */
	@Test
	public void testSetResult() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setResult(SatisfactionValue.SATISFIED);
		assertTrue(results.getResult() == SatisfactionValue.SATISFIED);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getSubpropertyTime()}
	 * .
	 */
	@Test
	public void testGetConstraintComputationTime() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getSubpropertyTime() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setSubPropertyComputationTime(double)}
	 * .
	 */
	@Test
	public void testSetConstraintComputationTime() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setSubPropertyComputationTime(2);
		assertTrue(results.getSubpropertyTime() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getNumStatesSpecification()}
	 * .
	 */
	@Test
	public void testGetNumStatesSpecification() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getNumStatesSpecification() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setNumStatesSpecification(int)}
	 * .
	 */
	@Test
	public void testSetNumStatesSpecification() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setNumStatesSpecification(2);
		assertTrue(results.getNumStatesSpecification() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getNumAcceptStatesSpecification()}
	 * .
	 */
	@Test
	public void testGetNumAcceptStatesSpecification() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getNumAcceptStatesSpecification() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setNumAcceptStatesSpecification(int)}
	 * .
	 */
	@Test
	public void testSetNumAcceptStatesSpecification() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setNumAcceptStatesSpecification(2);
		assertTrue(results.getNumAcceptStatesSpecification() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getNumTransitionSpecification()}
	 * .
	 */
	@Test
	public void testGetNumTransitionSpecification() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getNumTransitionSpecification() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setNumTransitionSpecification(int)}
	 * .
	 */
	@Test
	public void testSetNumTransitionSpecification() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setNumTransitionSpecification(2);
		assertTrue(results.getNumTransitionSpecification() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getNumStatesModel()}.
	 */
	@Test
	public void testGetNumStatesModel() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getNumStatesModel() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setNumStatesModel(int)}.
	 */
	@Test
	public void testSetNumStatesModel() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setNumStatesModel(2);
		assertTrue(results.getNumStatesModel() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getNumAcceptStatesModel()}.
	 */
	@Test
	public void testGetNumAcceptStatesModel() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getNumAcceptStatesModel() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setNumAcceptStatesModel(int)}
	 * .
	 */
	@Test
	public void testSetNumAcceptStatesModel() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setNumAcceptStatesModel(2);
		assertTrue(results.getNumAcceptStatesModel() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getNumTransitionModel()}.
	 */
	@Test
	public void testGetNumTransitionModel() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getNumTransitionModel() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setNumTransitionModel(int)}
	 * .
	 */
	@Test
	public void testSetNumTransitionModel() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setNumTransitionModel(2);
		assertTrue(results.getNumTransitionModel() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getNumBlackBoxStatesModel()}
	 * .
	 */
	@Test
	public void testGetNumBlackBoxStatesModel() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getNumBlackBoxStatesModel() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setNumBlackBoxStatesModel(int)}
	 * .
	 */
	@Test
	public void testSetNumBlackBoxStatesModel() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setNumBlackBoxStatesModel(2);
		assertTrue(results.getNumBlackBoxStatesModel() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#toStringHeader()}.
	 */
	@Test
	public void testToStringHeader() {
		assertNotNull(new ModelCheckingInfo(true, true, true).toStringHeader());
	}

	/**
	 * Test method for {@link it.polimi.checker.ModelCheckingInfo#toString()}
	 * .
	 */
	@Test
	public void testToString() {
		assertNotNull(new ModelCheckingInfo(true, true, true).toString());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getNumStatesIntersection()}
	 * .
	 */
	@Test
	public void testGetNumStatesIntersection() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getNumStatesIntersection() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setNumStatesIntersection(int)}
	 * .
	 */
	@Test
	public void testSetNumStatesIntersection() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setNumStatesIntersection(2);
		assertTrue(results.getNumStatesIntersection() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getNumAcceptingStatesIntersection()}
	 * .
	 */
	@Test
	public void testGetNumAcceptingStatesIntersection() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getNumAcceptingStatesIntersection() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setNumAcceptingStatesIntersection(int)}
	 * .
	 */
	@Test
	public void testSetNumAcceptingStatesIntersection() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setNumAcceptingStatesIntersection(2);
		assertTrue(results.getNumAcceptingStatesIntersection() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getNumInitialStatesIntersection()}
	 * .
	 */
	@Test
	public void testGetNumInitialStatesIntersection() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getNumInitialStatesIntersection() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setNumInitialStatesIntersection(int)}
	 * .
	 */
	@Test
	public void testSetNumInitialStatesIntersection() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setNumInitialStatesIntersection(2);
		assertTrue(results.getNumInitialStatesIntersection() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getNumMixedStatesIntersection()}
	 * .
	 */
	@Test
	public void testGetNumMixedStatesIntersection() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getNumMixedStatesIntersection() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setNumMixedStatesIntersection(int)}
	 * .
	 */
	@Test
	public void testSetNumMixedStatesIntersection() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setNumMixedStatesIntersection(2);
		assertTrue(results.getNumMixedStatesIntersection() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getTotalVerificationTime()}
	 * .
	 */
	@Test
	public void testGetTotalVerificationTime() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getTotalVerificationTime() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setTotalTime(double)}.
	 */
	@Test
	public void testSetTotalTime() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setTotalTime(2);
		assertTrue(results.getTotalVerificationTime() == 2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#getSimplificationTime()}.
	 */
	@Test
	public void testGetSimplificationTime() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		assertTrue(results.getSimplificationTime() == 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelCheckingInfo#setSimplificationTime(double)}
	 * .
	 */
	@Test
	public void testSetSimplificationTime() {
		ModelCheckingInfo results = new ModelCheckingInfo(true, true, true);
		results.setSimplificationTime(2);
		assertTrue(results.getSimplificationTime() == 2);
	}
}
