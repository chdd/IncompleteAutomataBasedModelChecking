package it.polimi.casestudies;

import it.polimi.CHIA;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.automata.io.in.states.ElementToBAStateTransformer;
import it.polimi.automata.io.in.states.ElementToIBAStateTransformer;
import it.polimi.automata.io.in.transitions.ElementToBATransitionTransformer;
import it.polimi.automata.io.in.transitions.ElementToIBATransitionTransformer;
import it.polimi.automata.io.transformer.transitions.ModelTransitionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.casestudies.utils.Experiment;
import it.polimi.casestudies.utils.ExperimentLoader;
import it.polimi.casestudies.utils.ExperimentsConstants;
import it.polimi.checker.intersection.IntersectionRule;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Refinement;
import it.polimi.constraints.io.in.BAConstraintReader;
import it.polimi.constraints.io.in.RefinementReader;
import it.polimi.constraints.io.out.ConstraintWriter;
import it.polimi.model.ltltoba.LTLtoBATransformer;
import it.polimi.refinementchecker.ReplacementChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ExperimentRunner {

	public static void main(String[] args) throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException {

		ExperimentLoader loader = new ExperimentLoader(new File(
				ExperimentRunner.class.getClassLoader()
						.getResource(ExperimentsConstants.XML_EXPERIMENTS_FILE)
						.getFile()));

		FileWriter results = new FileWriter(
				ExperimentsConstants.XML_RESULT_FOLDER + "results.txt");

		for (Experiment e : loader.read()) {
			
			if(e.isRefinement()){
				
				ElementToBAStateTransformer stateElementParser = new ElementToBAStateTransformer(
						new StateFactory());
				ElementToBATransitionTransformer transitionParser = new ElementToBATransitionTransformer(
						new ClaimTransitionFactory<State>());

				BAConstraintReader<State, Transition> contraintReader = new BAConstraintReader<State, Transition>(
						e.getClaimFile(), stateElementParser, transitionParser);
				Constraint<State, Transition, BA<State, Transition>> constraint = contraintReader
						.read();

				IntersectionRule<State, Transition> intersectionRule = new IntersectionRule<State, Transition>(
						new ClaimTransitionFactory<State>(),
						new StateFactory());

				RefinementReader<State, Transition> refinementReader = new RefinementReader<State, Transition>(
						e.getModelFile(),
						new ElementToIBAStateTransformer(new StateFactory()),
						new ElementToIBATransitionTransformer<State, Transition, IBA<State, Transition>>(
								new ClaimTransitionFactory<State>()));

				Refinement<State, Transition, IBA<State, Transition>> refinement = refinementReader
						.read();

				ReplacementChecker<State, Transition> refinementChecker = new ReplacementChecker<State, Transition>(
						constraint, refinement, intersectionRule);
				refinementChecker.check();
				Constraint<State, Transition, BA<State, Transition>> newConstraint = refinementChecker
						.newConstraint();

				new ConstraintWriter<State, Transition, BA<State, Transition>>(
						newConstraint, e.getResultsFile()).write();

			}
			else{
				ModelTransitionParser<State, Transition, IBA<State,Transition>> modelRransitionElementParser=new 
						ElementToIBATransitionTransformer<State, Transition, IBA<State,Transition>>(new ModelTransitionFactory<State>());
				
				ElementToIBAStateTransformer ibaStateElementParser=new ElementToIBAStateTransformer(new StateFactory());
				IBAReader<State, Transition>  modelReader=new IBAReader<State, Transition>(e.getModelFile(), ibaStateElementParser, modelRransitionElementParser);
				
				/*IBAWithInvariantsReader<State, Transition> modelReader = new IBAWithInvariantsReader<State,  Transition>(
						e.getModelFile(),
								ibaStateElementParser,
								modelRransitionElementParser);*/
				
				IBA<State, Transition> model = modelReader.read();
				
				
				Scanner ltlScanner = new Scanner(e.getClaimFile());
				String ltlFormula = ltlScanner.nextLine();
				ltlScanner.close();

				LTLtoBATransformer<State, Transition> ltlToBa = new LTLtoBATransformer<State, Transition>(
						new StateFactory(),
						new ClaimTransitionFactory<State>());
				BA<State, Transition> claim = ltlToBa.transform("!("+ltlFormula+")");

				
				CHIA<State, Transition> chia = new CHIA<State, Transition>(
						claim, model,
						new IntersectionRule<State, Transition>(new ClaimTransitionFactory<State>(),new StateFactory()), new ClaimTransitionFactory<State>());
				int res=chia.check();
				
				Constraint<State, Transition, BA<State, Transition>> constraint = chia
						.getConstraint();

				
				results.write(e.getModelFile().getName() + "\t"
						+ model.getStates().size() + "\t"
						+ model.getTransitions().size() + "\t"
						+ e.getClaimFile().getName() + "\t"
						+ claim.getStates().size() + "\t"
						+ claim.getTransitions().size()+"\t"+
						res+"\t"+
						+ constraint.getTotalStates()+"\t"+
						constraint.getTotalTransitions()+"\n"
						);
				new ConstraintWriter<State, Transition, BA<State, Transition>>(
						constraint, e.getResultsFile()).write();
			}
		}
		results.close();

	}

}
