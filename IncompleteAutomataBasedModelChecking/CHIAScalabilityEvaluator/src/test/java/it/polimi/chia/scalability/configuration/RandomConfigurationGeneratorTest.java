package it.polimi.chia.scalability.configuration;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RandomConfigurationGeneratorTest {

	@Test
	public void test() {
		int configurationNum=0;
		
		RandomConfigurationGenerator configurationGenerator=new RandomConfigurationGenerator();
		
		while(configurationGenerator.hasNext()){
			configurationNum++;
			Configuration conf=configurationGenerator.next();
			System.out.println(conf);
			
		}
		assertTrue("The number of possible configurations must be 1000 while it is "+configurationGenerator.getNumberOfPossibleConfigurations(), configurationGenerator.getNumberOfPossibleConfigurations()==10000);
		assertTrue("The number of generated configuration is "+configurationNum, configurationNum==10000);
	}

}
