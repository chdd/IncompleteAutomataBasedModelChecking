package it.polimi.view.automaton.transformers;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import org.apache.commons.collections15.Transformer;

public class BuchiAutomatonShapeTransformer<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S, T>> implements Transformer<S, Shape>{

	protected A a;
	
	public BuchiAutomatonShapeTransformer(A a){
		this.a=a;
	}

	@Override
	public Shape transform(S input) {
		
		if(a.isAccept(input)){
			Area ret=new Area(new Ellipse2D.Float(-10, -10, 20, 20));
			ret.subtract(new Area(new Ellipse2D.Float(-8, -8, 16, 16)));
			ret.add(new Area(new Ellipse2D.Float(-7, -7, 14, 14)));
			
			return ret;
		}
		if(a.isInitial(input)){
			Area ret=new Area(new Ellipse2D.Float(-10, -10, 20, 20));
			Polygon p=new Polygon();
			p.addPoint(11,  14);
			p.addPoint(7, 7);
			p.addPoint(14,  11);
			
			Polygon p2=new Polygon();
			p.addPoint(13,  14);
			p.addPoint(9, 9);
			p.addPoint(14,  13);
			ret.add(new Area(p));
			ret.subtract(new Area(p2));
			return ret;
		}
		else return new Area(new Ellipse2D.Float(-10, -10, 20, 20));
	}
}
