/**
 * 
 */
package it.polimi.contraintcomputation.subautomatafinder;

import static org.junit.Assert.*;
import it.polimi.automata.IBA;
import it.polimi.automata.IBAFactory;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.impl.IBAFactoryImpl;
import it.polimi.automata.impl.IntBAFactoryImpl;
import it.polimi.automata.io.IBAReader;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.labeling.impl.LabelImplFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.IntersectionTransitionFactoryImpl;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;
import it.polimi.contraintcomputation.abstractedBA.AbstractedBA;
import it.polimi.contraintcomputation.abstractedBA.AbstractedBAFactory;
import it.polimi.contraintcomputation.component.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.io.GraphIOException;

/**
 * @author claudiomenghi
 *
 */
public class SubAutomataIdentifierTest {

	/*
	 * Model
	 */
	private IBA<Label, State, Transition<Label>> model;
	private State q1;
	private State send1;
	private State send2;
	private State q2;
	private State q3;
	
	
	/*
	 * Intersection
	 */
	private IntersectionBA<Label, State, Transition<Label>> intersection;
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
	private Transition<Label> intt1;
	private Transition<Label> intt2;
	private Transition<Label> intt3;
	private Transition<Label> intt4;
	private Transition<Label> intt5;
	private Transition<Label> intt6;
	private Transition<Label> intt7;
	private Transition<Label> intt8;
	private Transition<Label> intt9;
	private Transition<Label> intt10;
	private Transition<Label> intt11;
	private Transition<Label> intt12;
	private Transition<Label> intt13;
	private Transition<Label> intt14;
	private Transition<Label> intt15;
	private Transition<Label> intt16;
	private Transition<Label> intt17;
	private Transition<Label> intt18;
	private Transition<Label> intt19;
	private Transition<Label> intt20;
	
	private StateFactory<State> stateFactory;
	private TransitionFactory<Label, Transition<Label>> transitionFactory;
	private LabelFactory<Label> labelFactory;


	private Map<State, State> intersectionStateModelStateMap;
	
	
	
	@Before
	public void setUp() throws FileNotFoundException, GraphIOException {
		
		this.stateFactory = new StateFactoryImpl();
		this.transitionFactory=new ModelTransitionFactoryImpl<Label>();
		this.labelFactory=new LabelImplFactory();
		this.intersectionStateModelStateMap=new HashMap<State, State>();
		/*
		 * creating the model
		 */
		IBAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, IBAFactory<Label, State, Transition<Label>>> modelReader=new IBAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, IBAFactory<Label, State, Transition<Label>>>(
				this.labelFactory, this.transitionFactory, this.stateFactory, new IBAFactoryImpl<Label, State, Transition<Label>>(),
				new BufferedReader(new FileReader(getClass().getClassLoader()
						.getResource("SendingMessageModel.xml").getFile())));
		
		this.model=modelReader.read();
		
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
	 * Test method for {@link it.polimi.contraintcomputation.subautomatafinder.SubAutomataIdentifier#SubAutomataIdentifier(it.polimi.automata.IntersectionBA, it.polimi.automata.IBA, java.util.Map, it.polimi.contraintcomputation.abstractedBA.AbstractedBAFactory)}.
	 */
	@Test
	public void testSubAutomataIdentifier() {
		//TODO
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.subautomatafinder.SubAutomataIdentifier#getSubAutomata()}.
	 */
	@Test
	public void testGetSubAutomata() {
		SubAutomataIdentifier<Label, State, Transition<Label>> identifier=
				new SubAutomataIdentifier<Label, State, Transition<Label>>(intersection, model, intersectionStateModelStateMap, new AbstractedBAFactory<Label, State, Transition<Label>, Component<Label,State,Transition<Label>>>());
	
		AbstractedBA<Label, State, Transition<Label>, Component<Label,State,Transition<Label>>> simplifiedAutomata=identifier.getSubAutomata();
	
		System.out.println(simplifiedAutomata);
		assertTrue(simplifiedAutomata.getStates().size()==7);
		assertTrue(simplifiedAutomata.getInitialStates().size()==1);
		assertTrue(simplifiedAutomata.getAcceptStates().size()==1);
		assertTrue(simplifiedAutomata.getOutTransitions(simplifiedAutomata.getInitialStates().iterator().next()).size()==1);
	}
	
	
	
	
	private void createModel(){
		this.model=new IBAFactoryImpl<Label, State, Transition<Label>>().create();
		this.q1=this.stateFactory.create("q1", 1);
		this.send1=this.stateFactory.create("send1", 2);
		this.send2=this.stateFactory.create("send2", 3);
		this.q3=this.stateFactory.create("q3", 4);
		this.q2=this.stateFactory.create("q2", 5);
		
		this.model.addState(this.q1);
		this.model.addState(this.send1);
		this.model.addState(this.send2);
		this.model.addState(this.q3);
		this.model.addState(this.q2);
		this.model.addTransparentState(send1);
		this.model.addTransparentState(send2);
	}
	
	private void createIntersection(){
		/*
		 * createing the intersection
		 */
		this.intersection = new IntBAFactoryImpl<Label, State, Transition<Label>>()
				.create();
		this.ints1=this.stateFactory.create("1", 1);
		this.ints2=this.stateFactory.create("2", 2);
		this.ints3=this.stateFactory.create("3", 3);
		this.ints4=this.stateFactory.create("4", 4);
		this.ints5=this.stateFactory.create("5", 5);
		this.ints6=this.stateFactory.create("6", 6);
		this.ints7=this.stateFactory.create("7", 7);
		this.ints8=this.stateFactory.create("8", 8);
		this.ints9=this.stateFactory.create("9", 9);
		this.ints10=this.stateFactory.create("10", 10);
		this.ints11=this.stateFactory.create("11", 11);
		
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
		
		this.intt1=new IntersectionTransitionFactoryImpl<Label>().create(1, new HashSet<Label>());
		this.intt2=new IntersectionTransitionFactoryImpl<Label>().create(2, new HashSet<Label>());
		this.intt3=new IntersectionTransitionFactoryImpl<Label>().create(3, new HashSet<Label>());
		this.intt4=new IntersectionTransitionFactoryImpl<Label>().create(4, new HashSet<Label>());
		this.intt5=new IntersectionTransitionFactoryImpl<Label>().create(5, new HashSet<Label>());
		this.intt6=new IntersectionTransitionFactoryImpl<Label>().create(6, new HashSet<Label>());
		this.intt7=new IntersectionTransitionFactoryImpl<Label>().create(7, new HashSet<Label>());
		this.intt8=new IntersectionTransitionFactoryImpl<Label>().create(8, new HashSet<Label>());
		this.intt9=new IntersectionTransitionFactoryImpl<Label>().create(9, new HashSet<Label>());
		this.intt10=new IntersectionTransitionFactoryImpl<Label>().create(10, new HashSet<Label>());
		this.intt11=new IntersectionTransitionFactoryImpl<Label>().create(11, new HashSet<Label>());
		this.intt12=new IntersectionTransitionFactoryImpl<Label>().create(12, new HashSet<Label>());
		this.intt13=new IntersectionTransitionFactoryImpl<Label>().create(13, new HashSet<Label>());
		this.intt14=new IntersectionTransitionFactoryImpl<Label>().create(14, new HashSet<Label>());
		this.intt15=new IntersectionTransitionFactoryImpl<Label>().create(15, new HashSet<Label>());
		this.intt16=new IntersectionTransitionFactoryImpl<Label>().create(16, new HashSet<Label>());
		this.intt17=new IntersectionTransitionFactoryImpl<Label>().create(17, new HashSet<Label>());
		this.intt18=new IntersectionTransitionFactoryImpl<Label>().create(18, new HashSet<Label>());
		this.intt19=new IntersectionTransitionFactoryImpl<Label>().create(19, new HashSet<Label>());
		this.intt20=new IntersectionTransitionFactoryImpl<Label>().create(20, new HashSet<Label>());
		
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
