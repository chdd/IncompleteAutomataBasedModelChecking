package it.polimi.view.intersectionautomaton;

import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.brzozowski.Brzozowski;
import it.polimi.modelchecker.brzozowski.predicates.Constraint;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.TextArea;

import javax.xml.bind.JAXBException;

@SuppressWarnings("serial")
public class IntersectionAutTextArea<S1 extends State, T1 extends Transition<S1>,
									S extends IntersectionState<S1>, T extends Transition<S>,
									A extends IntersectionAutomaton<S1, T1, S, T>> extends TextArea {

	
	public IntersectionAutTextArea(Dimension d, Point p, A a)
			throws JAXBException {
		super();
		this.setSize(d);
		this.setLocation(p);
		Brzozowski<S1, T1,S,T> b=new Brzozowski<S1, T1,S,T>(a);
		Constraint<S1> s=b.getConstraint();
		this.setText("Constraint:\n "+s.toString());
	}

	public void update(A a, int result){
		
		Brzozowski<S1, T1,S,T> b=new Brzozowski<S1, T1,S,T>(a);
		
		if(result==1){
			this.setText("1 - the property is satisfied \n");
		}
		else{
			if(result==0){
				this.setText("0 - the property is not satisfied \n");
			}
			else{
				if(result==-1){
					this.setText("-1 - the property is satisfied with constraints \n");
					Constraint<S1> s=b.getConstraint();
					this.append("Constraint:\n "+s.toString());
				}
			}
		}
		
		

		
	}
}
