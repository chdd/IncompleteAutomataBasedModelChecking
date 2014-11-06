package it.polimi.io;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.automata.IBAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.util.Map;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.NodeMetadata;

/**
 * contains an {@link DrawableIBA} reader which loads the {@link DrawableIBA} from a {@link BufferedReader}
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the {@link State} of the {@link IBA}
 * @param <TRANSITION> is the type of the {@link LabelledTransition} of the {@link IBA}
 * @param <TRANSITIONFACTORY> is the {@link Factory} which creates the {@link LabelledTransition}
 * @param <STATEFACTORY> is the {@link Factory} which creates the {@link State} of the {@link IBA}
 * @param <AUTOMATON> is the type of the {@link DrawableIBA} to be generated
 * @param <AUTOMATONFACTORY> is the {@link Factory} which creates an empty {@link DrawableIBA}
 */
public class IBAReader<
CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>, 
	STATEFACTORY extends StateFactory<STATE>,
	AUTOMATON extends DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>,
	AUTOMATONFACTORY extends IBAFactory<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY, AUTOMATON>>
	extends BAReader<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY, STATEFACTORY, AUTOMATON, AUTOMATONFACTORY>{

	/**
	 * creates a new {@link IBAReader}
	 * @param transitionFactory is the {@link Factory} which creates the {@link LabelledTransition}
	 * @param stateFactory is the {@link Factory} which creates the {@link State} of the {@link IBA}
	 * @param automatonFactory is the {@link Factory} which creates a new empty {@link DrawableIBA}
	 * @param fileReader is the {@link BufferedReader} which is able to create an empty {@link DrawableIBA}
	 * @throws NullPointerException if the transitionFactory or the stateFactory or the automatonFactory or the fileReader is null
	 */
	public IBAReader( 
			TRANSITIONFACTORY transitionFactory,
			STATEFACTORY stateFactory, 
			AUTOMATONFACTORY automatonFactory,
			BufferedReader fileReader) {
		super(transitionFactory, stateFactory, automatonFactory, fileReader);
	}
	
	/**
	 * returns the {@link Transformer} that given a {@link State} returns its {@link NodeMetadata}
	 * @param stateFactory is the {@link StateFactory}
	 * @return the {@link Transformer} that given a {@link State} returns its {@link NodeMetadata}
	 * @throws NullPointerException if the {@link StateFactory} is null
	 */
	protected Transformer<NodeMetadata, STATE> getStateTransformer(STATEFACTORY stateFactory){
		if(stateFactory==null){
			throw new NullPointerException("The stateFactory cannot be null");
		}
		return new IBAMetadataStateTransformer(this.ba, this.statesandlocations, stateFactory); 
	}
	
	/**
	 * creates a new IBAMetadataStateTransformer
	 * @param a is the {@link DrawableIBA} that must be updated by the {@link Transformer}
	 * @param stateFactory contains the {@link Factory} which creates the {@link State} of the {@link DrawableIBA}
	 * @throws NullPointerException if the {@link DrawableIBA} or the {@link StateFactory} is null
	 */
	protected class IBAMetadataStateTransformer extends BAMetadataStateTransformer{

		/**
		 * creates a new IBAMetadataStateTransformer
		 * @param a is the {@link DrawableIBA} that must be updated by the {@link Transformer}
		 * @param stateFactory contains the {@link Factory} which creates the {@link State} of the {@link DrawableIBA}
		 * @throws NullPointerException if the {@link DrawableIBA} or the {@link StateFactory} is null
		 */
		public IBAMetadataStateTransformer(AUTOMATON a, Map<STATE, Point2D> statesandlocations, STATEFACTORY transitionFactory) {
			super(a, statesandlocations, transitionFactory);
		}

		/**
		 * transforms the {@link NodeMetadata} into the corresponding {@link State}
		 * @param input contains the {@link NodeMetadata} to be converted into the corresponding {@link State}
		 * @throws NullPointerException if the input is null
		 */
		@Override
		public STATE transform(NodeMetadata input) {
			STATE s=super.transform(input);
			if(Boolean.parseBoolean(input.getProperty("transparent"))){
				this.a.addTransparentState(s);
			}
			return s;
		}
	}
}
