package it.polimi.chia.scalability;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.out.BAToElementTrasformer;
import it.polimi.automata.io.out.IBAToElementTrasformer;
import it.polimi.automata.io.out.XMLWriter;
import it.polimi.automata.state.StateFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.NormalAcceptingPolicy;
import it.polimi.chia.scalability.configuration.RandomConfiguration;
import it.polimi.chia.scalability.configuration.RandomConfigurationGenerator;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.io.out.constraint.ConstraintToElementTransformer;
import it.polimi.constraints.io.out.replacement.ReplacementToElementTransformer;
import it.polimi.constraints.utils.Injector;
import it.polimi.contraintcomputation.ConstraintGenerator;
import it.polimi.model.ltltoba.LTLtoBATransformer;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class ScalabilityTest {

	private static final Logger logger = LoggerFactory
			.getLogger(ScalabilityTest.class);
	
	private final static String testDirectory="/Users/Claudio1/Desktop/Test/Test";
	private final static String generalTestDirectory="/Users/Claudio1/Desktop/Test";
	
	private final static AcceptingPolicy acceptingPolicy=new KripkeAcceptingPolicy();
	
	public static void main(String[] args) throws Exception {

		Stopwatch timer = Stopwatch.createUnstarted();

		FileUtils.deleteDirectory(new File(generalTestDirectory));
		FileUtils.forceMkdir(new File(generalTestDirectory));
		
		RandomConfigurationGenerator randomConfigurationGenerator = new RandomConfigurationGenerator();
		
		LTLtoBATransformer action = new LTLtoBATransformer("!((a)U(b))");
		BA claim = action.perform();
		
		//BA claim = generateRandomClaim(new RandomConfiguration(3, 2, 0.5, 0, 0));
		
		int i=0;
		while (randomConfigurationGenerator.hasNext()) {
			
			i++;
			
			
			File dir = new File(testDirectory+i);
			dir.mkdir();

			System.out.println("--------------------------- TEST: "+i+"------------------------");
			RandomConfiguration randomConfiguration = randomConfigurationGenerator
					.next();
			
			//BA claim = generateRandomClaim(randomConfiguration);
			
			BAToElementTrasformer claimToElementTransformer=new BAToElementTrasformer();
			
			XMLWriter claimWriter=new XMLWriter(new File(testDirectory+i+"/claim.xml"), claimToElementTransformer.transform(claim));
			claimWriter.perform();
			
			BARandomGenerator modelBAgenerator = new BARandomGenerator(
					randomConfiguration.getPropositions(), new StateFactory(),
					randomConfiguration.getTransitionDensity(),
					randomConfiguration.getAcceptingDensity(),
					randomConfiguration.getnStates(), new Random());
			
			BA modelBA = modelBAgenerator.perform();

			IBARandomGenerator ibaGenerator = new IBARandomGenerator(modelBA,
					new StateFactory(),
					randomConfiguration.getTransparentDensity(),
					randomConfiguration.getReplacementDensity());
			
			IBA model=ibaGenerator.perform();
			
			IBAToElementTrasformer modelToElementTransformer=new IBAToElementTrasformer();
			
			XMLWriter writer=new XMLWriter(new File(testDirectory+i+"/model.xml"), modelToElementTransformer.transform(model));
			writer.perform();
			
			// check whether the model possibly satisfies the claim
			Checker checker = new Checker(model, claim, acceptingPolicy);
			SatisfactionValue value = checker.perform();
			
			logger.info("Random configuration: "+randomConfiguration);
			logger.info("Verification of the IBA vs the BA: "+value.toString());
			logger.info("The property is: "+value.toString());
			if(value==SatisfactionValue.POSSIBLYSATISFIED){
				
				// compute the constraint
				Constraint constraint=computeConstraint(claim, model);
				
				ConstraintToElementTransformer constraintTransformer=new ConstraintToElementTransformer();
				XMLWriter constraintWriter=new XMLWriter(new File(testDirectory+i+"/constraint.xml"), constraintTransformer.transform(constraint));
				constraintWriter.perform();
				
				
				// choose a random replacement
				List<Replacement> replacements=new ArrayList<Replacement>(ibaGenerator.getTransparentStateReplacementMap().values());
				Collections.shuffle(replacements);
				
				
				Replacement replacement=replacements.iterator().next();
				
				// VERIFICATION OF THE REFINEMENT
				IBA refinedModel=new Injector(model, replacement).perform();
				Checker refinementChecker = new Checker(refinedModel, claim, acceptingPolicy);
				
				IBAToElementTrasformer refinementTransformer=new IBAToElementTrasformer();
				XMLWriter refinementWriter=new XMLWriter(new File(testDirectory+i+"/refinement.xml"), refinementTransformer.transform(refinedModel));
				refinementWriter.perform();
				
				timer.reset();
				ThreadMXBean thradBean = ManagementFactory.getThreadMXBean();
				long startTime = thradBean.getCurrentThreadCpuTime();
				timer.start();
				SatisfactionValue refinementSatisfactionvalue =refinementChecker.perform();
				timer.stop();
				long stopTime = thradBean.getCurrentThreadCpuTime();
				logger.info("REFINEMENT VERIFICATION PERFORMED IN: "+timer.elapsed(TimeUnit.MILLISECONDS)+" ms ");
				logger.info("REFINEMENT VERIFICATION STATES OF THE INTERSECTION AUTOMATON: "+refinementChecker.getIntersectionAutomataSize());
				long refinementVerificationTime=timer.elapsed(TimeUnit.MILLISECONDS);
				
				// VERIFICATION OF THE REPLACEMENT
				ReplacementChecker replacementChecker = new ReplacementChecker(constraint.getSubProperty(replacement.getModelState()), replacement, acceptingPolicy);
				
				ReplacementToElementTransformer replacementTransformer=new ReplacementToElementTransformer();
				XMLWriter replacementWriter=new XMLWriter(new File(testDirectory+i+"/replacement.xml"), replacementTransformer.transform(replacement));
				replacementWriter.perform();
				
				
				timer.reset();
				timer.start();
				SatisfactionValue replacementSatisfactionvalue= replacementChecker.perform();
				timer.stop();
				logger.info("REPLACEMENT VERIFICATION PERFORMED IN: "+timer.elapsed(TimeUnit.MILLISECONDS)+" ms ");
				logger.info("REPLACEMENT VERIFICATION STATES OF THE INTERSECTION AUTOMATON: "+replacementChecker.getIntersectionAutomataSize());

				long replacementVerificationTime=timer.elapsed(TimeUnit.MILLISECONDS);
				
				if(refinementVerificationTime<replacementVerificationTime){
					logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
					logger.info("&&&&&&&&&&&&&&&&&&&& THE VERIFICATION OF THE REPLACEMENT IS LESS EFFICIENT &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
					logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
				}
				else{
					logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
					logger.info("&&&&&&&&&&&&&&&&&&&& THE VERIFICATION OF THE REPLACEMENT IS MORE EFFICIENT &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
					logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
				}
				
				System.gc();
				System.runFinalization ();
				if(refinementSatisfactionvalue!=replacementSatisfactionvalue){
					throw new InternalError("The verification of the refinement states that "+refinementSatisfactionvalue.toString()+" while the verification of the replacement states that  "+replacementSatisfactionvalue.toString());
				}
				logger.info("The property is: "+refinementSatisfactionvalue.toString());
			}
		}
	}
	
	private static BA generateRandomClaim(RandomConfiguration randomConfiguration){
		BARandomGenerator claimgenerator = new BARandomGenerator(
				randomConfiguration.getPropositions(), new StateFactory(),
				randomConfiguration.getTransitionDensity(),
				randomConfiguration.getAcceptingDensity(),
				randomConfiguration.getnStates(), new Random());

		return claimgenerator.perform();
	}
	

	
	private static Constraint computeConstraint(BA claim, IBA model){
		
		Checker checker = new Checker(model, claim, new NormalAcceptingPolicy());
		
		ConstraintGenerator cg = new ConstraintGenerator(checker);
		Constraint constraint = cg.perform();
		cg.coloring();
		cg.computePortReachability();
		cg.computeIndispensable();
		
		return constraint;
	}
}
