package it.polimi;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.impl.IntBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.impl.LabelFactoryImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.impl.IntersectionTransitionFactoryImpl;
import it.polimi.checker.ModelChecker;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.impl.IntersectionRuleImpl;
import it.polimi.contraintcomputation.ConstraintGenerator;

public class CHIA {

	private BA<Label, State, Transition<Label>> claim;
	private IBA<Label, State, Transition<Label>> model;
	private ModelChecker<Label, State, Transition<Label>> mc;
	private ConstraintGenerator<Label, State, Transition<Label>> cg;
	private ModelCheckingResults mcResults;
	int result;
	
	public CHIA(BA<Label, State, Transition<Label>> claim, IBA<Label, State, Transition<Label>> model){
		
		this.claim=claim;
		this.model=model;
	}
	
	public int check(){
		mcResults=new ModelCheckingResults();
		mc=new ModelChecker<Label, State, Transition<Label>>(model, claim,
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(),
				mcResults);
		result=mc.check();
		
		return result;
	}
	
	public String getConstraint(){
		if(result!=-1){
			return null;
		}
		cg=new ConstraintGenerator<Label, State, Transition<Label>>(
				this.mc.getIntersectionAutomaton(), this.model, this.mc.getIntersectionStateModelStateMap(),
				new LabelFactoryImpl(), new IntersectionTransitionFactoryImpl<Label>());
		return cg.generateConstraint();
		
	}
}
