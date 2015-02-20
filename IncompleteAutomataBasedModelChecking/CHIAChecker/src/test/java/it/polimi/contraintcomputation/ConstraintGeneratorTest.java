/**
 * 
 */
package it.polimi.contraintcomputation;

import static org.junit.Assert.assertNotNull;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.impl.IBAImpl;
import it.polimi.automata.impl.IntBAImpl;
import it.polimi.automata.io.IBAReader;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.TransitionFactoryIntersectionImpl;
import it.polimi.contraintcomputation.component.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

/**
 * @author claudiomenghi
 *
 */
public class ConstraintGeneratorTest {

	/*
	 * Model
	 */
	private IBA< State, Transition> model;
	private State q1;
	private State send1;
	private State send2;
	private State q2;
	private State q3;

	/*
	 * Intersection
	 */
	private IntersectionBA<State, Transition> intersection;
	private State ints1;
	private State ints2;
	private State ints3;
	private State ints4;
	private State ints5;
	private State ints6;
	private State ints7;
	private State ints8;
	private State ints9;
	private State ints10;
	private State ints11;
	private Transition intt1;
	private Transition intt2;
	private Transition intt3;
	private Transition intt4;
	private Transition intt5;
	private Transition intt6;
	private Transition intt7;
	private Transition intt8;
	private Transition intt9;
	private Transition intt10;
	private Transition intt11;
	private Transition intt12;
	private Transition intt13;
	private Transition intt14;
	private Transition intt15;
	private Transition intt16;
	private Transition intt17;
	private Transition intt18;
	private Transition intt19;
	private Transition intt20;

	private StateFactory<State> stateFactory;
	private TransitionFactory<State, Transition> transitionFactory;
	
	private Map<State, State> intersectionStateModelStateMap;

	@Before
	public void setUp() throws FileNotFoundException {

		this.stateFactory = new StateFactoryImpl();
		this.transitionFactory = new TransitionFactoryIntersectionImpl<State>(Transition.class);
		this.intersectionStateModelStateMap = new HashMap<State, State>();
		/*
		 * creating the model
		 */
		IBAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>> modelReader = new IBAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>>(
				this.transitionFactory, this.stateFactory,
				new File(getClass().getClassLoader()
						.getResource("sendingmessage/SendingMessageModel.xml").getFile()));

		this.model = modelReader.read();

		this.createModel();
		this.createIntersection();

		this.intersectionStateModelStateMap.put(ints1, q1);
		this.intersectionStateModelStateMap.put(ints2, send1);
		this.intersectionStateModelStateMap.put(ints3, send2);
		this.intersectionStateModelStateMap.put(ints4, q2);
		this.intersectionStateModelStateMap.put(ints5, q3);
		this.intersectionStateModelStateMap.put(ints6, send1);
		this.intersectionStateModelStateMap.put(ints7, q3);
		this.intersectionStateModelStateMap.put(ints8, send2);
		this.intersectionStateModelStateMap.put(ints9, q2);
		this.intersectionStateModelStateMap.put(ints10, q2);
		this.intersectionStateModelStateMap.put(ints11, q2);

	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.ConstraintGenerator#ConstraintGenerator(it.polimi.automata.IntersectionBA, java.util.Map, it.polimi.automata.labeling.LabelFactory, it.polimi.automata.state.StateFactory, it.polimi.automata.transition.TransitionFactory)}.
	 */
	@Test
	public void testConstraintGenerator() {
		//TODO
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.ConstraintGenerator#generateConstraint()}.
	 */
	@Test
	public void testGenerateConstraint() {
		ConstraintGenerator<State, Transition> cg=new ConstraintGenerator<State, Transition>(
				this.intersection, this.model, this.intersectionStateModelStateMap,
				new TransitionFactoryIntersectionImpl<Component<State, Transition>>(Transition.class), 
				this.transitionFactory);
		assertNotNull(cg.generateConstraint());
	}
	


	private void createModel() {
		this.model = new IBAImpl<State,Transition>(transitionFactory);
		this.q1 = this.stateFactory.create("q1", 1);
		this.send1 = this.stateFactory.create("send1", 2);
		this.send2 = this.stateFactory.create("send2", 3);
		this.q3 = this.stateFactory.create("q3", 4);
		this.q2 = this.stateFactory.create("q2", 5);

		this.model.addState(this.q1);
		this.model.addState(this.send1);
		this.model.addState(this.send2);
		this.model.addState(this.q3);
		this.model.addState(this.q2);

		this.model.addTransparentState(send1);
		this.model.addTransparentState(send2);
	}

	private void createIntersection() {
		/*
		 * createing the intersection
		 */
		this.intersection = new IntBAImpl<State, Transition>(this.transitionFactory);
		this.ints1 = this.stateFactory.create("1", 1);
		this.ints2 = this.stateFactory.create("2", 2);
		this.ints3 = this.stateFactory.create("3", 3);
		this.ints4 = this.stateFactory.create("4", 4);
		this.ints5 = this.stateFactory.create("5", 5);
		this.ints6 = this.stateFactory.create("6", 6);
		this.ints7 = this.stateFactory.create("7", 7);
		this.ints8 = this.stateFactory.create("8", 8);
		this.ints9 = this.stateFactory.create("9", 9);
		this.ints10 = this.stateFactory.create("10", 10);
		this.ints11 = this.stateFactory.create("11", 11);

		this.intersection.addState(this.ints1);
		this.intersection.addState(this.ints2);
		this.intersection.addState(this.ints3);
		this.intersection.addState(this.ints4);
		this.intersection.addState(this.ints5);
		this.intersection.addState(this.ints6);
		this.intersection.addState(this.ints7);
		this.intersection.addState(this.ints8);
		this.intersection.addState(this.ints9);
		this.intersection.addState(this.ints10);
		this.intersection.addState(this.ints11);

		this.intersection.addInitialState(this.ints1);
		this.intersection.addAcceptState(this.ints10);
		
		
		Set<IGraphProposition> label1=new HashSet<IGraphProposition>();
		label1.add(new GraphProposition("start", false));
		this.intt1 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(1,label1);
		Set<IGraphProposition> label2=new HashSet<IGraphProposition>();
		label2.add(new SigmaProposition());
		this.intt2 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(2,
				label2);
		Set<IGraphProposition> label3=new HashSet<IGraphProposition>();
		label3.add(new GraphProposition("fail", false));
		this.intt3 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(3,
				label3);
		Set<IGraphProposition> label4=new HashSet<IGraphProposition>();
		label4.add(new GraphProposition("ok", false));
		this.intt4 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(4,
				label4);
		Set<IGraphProposition> propositions=new HashSet<>();
		propositions.add(new GraphProposition("send", false));
		propositions.add(new GraphProposition("success", true));
		this.intt5 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(5,
				propositions);
		
		Set<IGraphProposition> label6=new HashSet<IGraphProposition>();
		label6.add(new GraphProposition("success", true));
		this.intt6 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(6,
				label6);
		Set<IGraphProposition> label7=new HashSet<IGraphProposition>();
		label7.add(new GraphProposition("fail", false));
		this.intt7 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(7,
				label7);
		Set<IGraphProposition> label8=new HashSet<IGraphProposition>();
		label8.add(new GraphProposition("abort", false));
		this.intt8 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(8,
				label8);
		Set<IGraphProposition> label9=new HashSet<IGraphProposition>();
		label9.add(new GraphProposition("success", false));
		this.intt9 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(9,
				label9);
		Set<IGraphProposition> label10=new HashSet<IGraphProposition>();
		label10.add(new GraphProposition("ok", false));
		this.intt10 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(10,
				label10);
		Set<IGraphProposition> label11=new HashSet<IGraphProposition>();
		label11.add(new SigmaProposition());
		this.intt11 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(11,
				label11);
		Set<IGraphProposition> label12=new HashSet<IGraphProposition>();
		propositions=new HashSet<>();
		propositions.add(new GraphProposition("send", false));
		propositions.add(new GraphProposition("success", true));
		label12.addAll(propositions);
		this.intt12 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(12,
				label12);
		Set<IGraphProposition> label13=new HashSet<IGraphProposition>();
		label13.add(new GraphProposition("fail", false));
		this.intt13 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(13,
				label13);
		Set<IGraphProposition> label14=new HashSet<IGraphProposition>();
		label14.add(new GraphProposition("success", false));
		this.intt14 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(14,
				label14);
		Set<IGraphProposition>label15=new HashSet<IGraphProposition>();
		label15.add(new GraphProposition("ok", false));
		this.intt15 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(15,
				label15);
		Set<IGraphProposition>label16=new HashSet<IGraphProposition>();
		label16.add(new GraphProposition("ok", false));
		this.intt16 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(16,
				label16);
		Set<IGraphProposition>label17=new HashSet<IGraphProposition>();
		label17.add(new GraphProposition("fail", false));
		this.intt17 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(17,
				label17);
		Set<IGraphProposition>label18=new HashSet<IGraphProposition>();
		label18.add(new GraphProposition("abort", false));
		this.intt18 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(18,
				label18);
		Set<IGraphProposition>label19=new HashSet<IGraphProposition>();
		label19.add(new GraphProposition("abort", false));
		this.intt19 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(19,
				label19);
		Set<IGraphProposition>label20=new HashSet<IGraphProposition>();
		label20.add(new GraphProposition("abort", false));
		this.intt20 = new TransitionFactoryIntersectionImpl<State>(Transition.class).create(20,
				label20);

		this.intersection.addCharacter(new GraphProposition("fail", false));
		this.intersection.addCharacter(new GraphProposition("abort", false));
		this.intersection.addCharacter(new GraphProposition("ok", false));
		this.intersection.addCharacter(new GraphProposition("success", false));
		this.intersection.addCharacter(new GraphProposition("success", true));
		this.intersection.addCharacter(new GraphProposition("send", false));
		propositions=new HashSet<>();
		propositions.add(new GraphProposition("send", false));
		propositions.add(new GraphProposition("success", true));
		this.intersection.addCharacters(propositions);
		
		this.intersection.addCharacter(new GraphProposition("start", false));
		this.intersection.addCharacter(new SigmaProposition());
		this.intersection.addTransition(this.ints1, this.ints2, this.intt1);
		this.intersection.addTransition(this.ints2, this.ints2, this.intt2);
		this.intersection.addTransition(this.ints2, this.ints3, this.intt3);
		this.intersection.addTransition(this.ints2, this.ints5, this.intt4);
		this.intersection.addTransition(this.ints2, this.ints6, this.intt5);
		this.intersection.addTransition(this.ints6, this.ints6, this.intt6);
		this.intersection.addTransition(this.ints3, this.ints4, this.intt7);
		this.intersection.addTransition(this.ints4, this.ints4, this.intt8);
		this.intersection.addTransition(this.ints5, this.ints5, this.intt9);
		this.intersection.addTransition(this.ints3, this.ints5, this.intt10);
		this.intersection.addTransition(this.ints3, this.ints3, this.intt11);
		this.intersection.addTransition(this.ints3, this.ints8, this.intt12);
		this.intersection.addTransition(this.ints6, this.ints8, this.intt13);
		this.intersection.addTransition(this.ints8, this.ints8, this.intt14);
		this.intersection.addTransition(this.ints6, this.ints7, this.intt15);
		this.intersection.addTransition(this.ints8, this.ints7, this.intt16);
		this.intersection.addTransition(this.ints8, this.ints10, this.intt17);
		this.intersection.addTransition(this.ints9, this.ints10, this.intt18);
		this.intersection.addTransition(this.ints10, this.ints11, this.intt19);
		this.intersection.addTransition(this.ints11, this.ints9, this.intt20);
	}
}
