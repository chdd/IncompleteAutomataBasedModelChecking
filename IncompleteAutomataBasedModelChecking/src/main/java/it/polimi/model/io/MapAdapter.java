package it.polimi.model.io;

import it.polimi.model.State;
import it.polimi.model.LabelledTransition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MapAdapter<S extends State, T extends LabelledTransition<S>> extends XmlAdapter<MapAdapter.AdaptedMap<S,T>, Map<S, HashSet<T>>> {
	
	@Override
	public AdaptedMap<S,T> marshal(Map<S, HashSet<T>> map) throws Exception {
		AdaptedMap<S,T> adaptedMap = new AdaptedMap<S,T>();
		for(Map.Entry<S, HashSet<T>> mapEntry : map.entrySet()) {
			MapAdapter.Entry<S,T> entry = new MapAdapter.Entry<S,T>();
			entry.sourceState = mapEntry.getKey();
			entry.transition = mapEntry.getValue();
			adaptedMap.entry.add(entry);
		}
		return adaptedMap;
	}

	@Override
	public Map<S, HashSet<T>> unmarshal(MapAdapter.AdaptedMap<S,T> adapterMap) throws Exception {
		
		Map<S, HashSet<T>> map = new HashMap<S, HashSet<T>>();
		for(MapAdapter.Entry<S,T> entry : adapterMap.entry) {
			map.put(entry.sourceState, entry.transition);
		}
		
		return map;
	}

	public static class AdaptedMap<S extends State, T extends LabelledTransition<S>> {
		@XmlElement(name ="element")  
		public List<Entry<S,T>> entry = new ArrayList<Entry<S,T>>();
		
		
	}
	public static class Entry<S extends State, T extends LabelledTransition<S>> {
		@XmlIDREF 
		public S sourceState;
		@XmlElementWrapper(name = "transitions")
		@XmlElement  
		public HashSet<T> transition;
	}
	
}