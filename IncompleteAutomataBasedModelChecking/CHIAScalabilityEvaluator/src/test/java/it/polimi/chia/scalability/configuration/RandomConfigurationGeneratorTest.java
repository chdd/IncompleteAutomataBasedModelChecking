package it.polimi.chia.scalability.configuration;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.automata.BA;

import org.junit.Test;

public class RandomConfigurationGeneratorTest {

	
	
	@Test
	public void test1() throws Exception {
	
		List<BA> bas=Main.getClaimToBeConsidered();
		RandomConfigurationGenerator configurationGenerator=new RandomConfigurationGenerator(bas);
	
		Map<BA, Integer> numMap=new HashMap<BA, Integer>();
	
		for(BA ba: bas){
			numMap.put(ba, 0);
		}
		while(configurationGenerator.hasNext()){
			Configuration conf=configurationGenerator.next();
			numMap.put(conf.getCurrentClaim(),numMap.get(conf.getCurrentClaim())+1);
		}
		
		for(BA ba: numMap.keySet()){
			assertTrue("The number of configuration of the claim is "+numMap.get(ba)+" instead of 80000",numMap.get(ba).equals(80000));
		}
	}
	
	@Test
	public void test2() throws Exception {
	
		List<BA> bas=Main.getClaimToBeConsidered();
		RandomConfigurationGenerator configurationGenerator=new RandomConfigurationGenerator(bas);
	
		Map<Integer, Integer> numMap=new HashMap<Integer, Integer>();
	
		int id=0;
		for(BA ba: bas){
			numMap.put(id, 0);
			id++;
		}
		while(configurationGenerator.hasNext()){
			Configuration conf=configurationGenerator.next();
			numMap.put(conf.getClaimNumber(),numMap.get(conf.getClaimNumber())+1);
		}
		
		for(int index=0; index<numMap.keySet().size();index++){
			assertTrue("The number of configuration of the claim is "+numMap.get(index)+" instead of 80000",numMap.get(index).equals(80000));
		}
	}

}
