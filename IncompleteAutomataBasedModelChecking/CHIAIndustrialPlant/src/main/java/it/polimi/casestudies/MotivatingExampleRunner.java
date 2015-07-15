package it.polimi.casestudies;

import it.polimi.CHIA;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.automata.io.in.states.ElementToIBAStateTransformer;
import it.polimi.automata.io.in.transitions.ElementToIBATransitionTransformer;
import it.polimi.automata.io.transformer.states.StateElementParser;
import it.polimi.automata.io.transformer.transitions.ModelTransitionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.casestudies.utils.ExperimentLoader;
import it.polimi.casestudies.utils.ExperimentsConstants;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.IntersectionRule;
import it.polimi.constraints.Constraint;
import it.polimi.model.ltltoba.LTLtoBATransformer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class MotivatingExampleRunner {

	public static void main(String[] args) throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException {

		ExperimentLoader loader = new ExperimentLoader(new File(
				MotivatingExampleRunner.class.getClassLoader()
						.getResource(ExperimentsConstants.XML_EXPERIMENTS_FILE)
						.getFile()));

		FileWriter results = new FileWriter(
				ExperimentsConstants.XML_RESULT_FOLDER + "results.txt");

		LTLtoBATransformer<State, Transition> transformer=new LTLtoBATransformer<State, Transition>(new StateFactory(), new ClaimTransitionFactory<State>());
		BA<State, Transition> claim=transformer.transform("![](send-><>success)");
		
		File f=new File(
				MotivatingExampleRunner.class.getClassLoader()
				.getResource("it/polimi/casestudies/sendingmessage/SendingMessageModel.xml").getFile());
		
		ModelTransitionParser<State, Transition, IBA<State, Transition>> transitionParser=new ElementToIBATransitionTransformer<State, Transition, IBA<State, Transition>>(new ModelTransitionFactory<State>());
		StateElementParser<State, Transition, IBA<State, Transition>> stateElementPerser=new ElementToIBAStateTransformer(new StateFactory());
		IBA<State, Transition> model=new IBAReader<State, Transition>(f,stateElementPerser,  transitionParser).read();
		
		for(int i=0; i<100; i++){
			 CHIA<State, Transition> chia = new CHIA<State, Transition>(
						claim,
						model,
						new IntersectionRule<State, Transition>(
								new ClaimTransitionFactory<State>(),
								new StateFactory()),
						new ClaimTransitionFactory<State>(),
						new ModelCheckingResults(true, true, true));
				int res = chia.check();

				Constraint<State, Transition, BA<State, Transition>> constraint = chia
									.getConstraint();

		}
				
		CHIA<State, Transition>  chia2 = new CHIA<State, Transition>(
				claim,
				model,
				new IntersectionRule<State, Transition>(
						new ClaimTransitionFactory<State>(),
						new StateFactory()),
				new ClaimTransitionFactory<State>(),
				new ModelCheckingResults(true, true, true));
		int res = chia2.check();
		Constraint<State, Transition, BA<State, Transition>>  constraint = chia2
					.getConstraint();

		
		System.out.println(chia2.getMcResults().getTotalVerificationTime()+"\t"+chia2.getMcResults().getSubpropertyTime()+"\t"+chia2.getMcResults().getPortReachabilityTime());
	}

}
