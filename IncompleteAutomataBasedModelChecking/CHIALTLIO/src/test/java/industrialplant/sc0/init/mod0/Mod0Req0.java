package industrialplant.sc0.init.mod0;

import industrialplant.Constants;
import it.polimi.model.ltltoba.LTLtoBATransformer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class Mod0Req0 {

	
	@Test
	public void test() throws IOException {
		BufferedReader reader=new BufferedReader(new FileReader(getClass().getClassLoader()
				.getResource(Constants.req0).getFile()));
		String ltlFormula=reader.readLine();
		reader.close();
		LTLtoBATransformer ltlToBaTransformer;
		ltlToBaTransformer=new LTLtoBATransformer(ltlFormula);
		ltlToBaTransformer.perform();
	}

}
