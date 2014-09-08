package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public abstract class  AbstractPredicate<S extends State>{
	
	public abstract AbstractPredicate<S> concatenate(AbstractPredicate<S> a);
	public abstract AbstractPredicate<S> star();
	public abstract AbstractPredicate<S> omega();
	public abstract AbstractPredicate<S> union(AbstractPredicate<S> a);
	/**
	 * returns the empty string
	 * @return the empty string
	 */
	public abstract String toString();
	
	public abstract boolean equals(Object obj);
	public abstract int hashCode();
	
	public String toXMLString() throws JAXBException{
		try {
			StringWriter sw = new StringWriter();
			// create JAXB context and initializing Marshaller
			JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			// for getting nice formatted output
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			// Writing to console
			jaxbMarshaller.marshal(this, sw);
			return sw.toString();
		} catch (JAXBException e) {
		
			// some exception occured
			e.printStackTrace();
			return null;
		}
	}
}
