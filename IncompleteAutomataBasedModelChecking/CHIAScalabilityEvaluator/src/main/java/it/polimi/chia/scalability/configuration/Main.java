package it.polimi.chia.scalability.configuration;

import it.polimi.automata.BA;
import it.polimi.model.ltltoba.LTLtoBATransformer;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		RandomConfigurationGenerator rc=new RandomConfigurationGenerator(Main.getClaimToBeConsidered());
		while(rc.hasNext()){
			Configuration c=rc.next();
			System.out.println(c);
		}
	}

	public	 static List<BA> getClaimToBeConsidered() throws Exception {

		List<BA> claims = new ArrayList<BA>();
		LTLtoBATransformer action = new LTLtoBATransformer("!((a)U(b))");
		BA claim = action.perform();
		claims.add(claim);

		action = new LTLtoBATransformer("!([](a->(<>b)))");
		claim = action.perform();
		claims.add(claim);

		action = new LTLtoBATransformer("!(<>((a)U(b)))");
		claim = action.perform();
		claims.add(claim);

		return claims;

	}
}
