package it.polimi.view.trasformers;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.impl.BAImpl;
import it.polimi.model.elements.states.State;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import org.apache.commons.collections15.Transformer;

public class BuchiAutomatonShapeTransformer<S extends State, T extends LabelledTransition, A extends BAImpl<S, T>> implements Transformer<S, Shape>{

	private final int stateRadius=10;
	
	protected A a;
	
	public BuchiAutomatonShapeTransformer(A a){
		this.a=a;
	}

	@Override
	public Shape transform(S input) {
		if(a.isAccept(input) && a.isInitial(input)){
			Area ret=new Area(new Ellipse2D.Float(-stateRadius, -stateRadius, stateRadius*2, stateRadius*2));
			ret.subtract(new Area(new Ellipse2D.Float(-stateRadius+2, -stateRadius+2, stateRadius*2-4, stateRadius*2-4)));
			ret.add(new Area(new Ellipse2D.Float(-stateRadius+3, -stateRadius+3, stateRadius*2-6, stateRadius*2-6)));
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
		if(a.isAccept(input)){
			Area ret=new Area(new Ellipse2D.Float(-stateRadius, -stateRadius, stateRadius*2, stateRadius*2));
			ret.subtract(new Area(new Ellipse2D.Float(-stateRadius+2, -stateRadius+2, stateRadius*2-4, stateRadius*2-4)));
			ret.add(new Area(new Ellipse2D.Float(-stateRadius+3, -stateRadius+3, stateRadius*2-6, stateRadius*2-6)));
			
			return ret;
		}
		if(a.isInitial(input)){
			Area ret=new Area(new Ellipse2D.Float(-stateRadius, -stateRadius, stateRadius*2, stateRadius*2));
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
		else return new Area(new Ellipse2D.Float(-stateRadius, -stateRadius, stateRadius*2, stateRadius*2));
	}
}
