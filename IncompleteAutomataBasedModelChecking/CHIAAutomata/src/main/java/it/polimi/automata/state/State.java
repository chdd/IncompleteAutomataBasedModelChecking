package it.polimi.automata.state;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import it.polimi.automata.state.impl.StateImpl;

/**
 * <p>Represents a state of an automaton. <br>
 * A state is identified by an id and has a name</p>
 * 
 * @author claudiomenghi
 * @see StateImpl
 */
@XmlRootElement(name="State")
public interface State {
	
	/**
	 * <p>returns the <br>id</br> of the state<br>
	 * The id uniquely identifies the state
	 * </p>
	 * 
	 * @return the <br>id</br> of the state
	 */
	@XmlID
	public int getId();

	/**
	 * returns the <br>name</br> of the state
	 * 
	 * @return the <br>name</br> of the state
	 */
	@XmlAttribute
	public String getName();
	
	
}
