package it.polimi.controller;

import it.polimi.model.IntersectionState;
import it.polimi.model.Model;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.ModelChecker;
import it.polimi.modelchecker.ModelCheckerParameters;
import it.polimi.view.Actions;
import it.polimi.view.ViewInterface;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Observable;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Controller implements ControllerInterface{

	private ViewInterface<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> view;
	private Model model;
	
	public Controller(Model model, ViewInterface<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> view) throws JAXBException, SAXException, IOException, ParserConfigurationException{
		
		this.model=model;
		this.view=view;
		this.update();
	}

	@Override
	public void update(Observable o, Object arg) {
		try {
			
			ActionEvent e=(ActionEvent) arg;
			if(e.getActionCommand()== Actions.LOADMODEL.name()){
				String file=this.view.getFile();
				model.changeModel(file);
				this.update();
			}
			if(e.getActionCommand()== Actions.LOADSPECIFICATION.name()){
				String file=this.view.getFile();
				model.changeSpecification(file);
				this.update();
			}
			if(e.getActionCommand()==Actions.SAVEMODEL.name()){
				String filePath=this.view.createFile();
				this.model.loadModel(this.view.getModelXML());
				this.model.saveModel(filePath);
			}
			if(e.getActionCommand()==Actions.SAVESPECIFICATION.name()){
				String filePath=this.view.createFile();
				this.model.loadSpecification(this.view.getSpecificationXML());
				this.model.saveSpecification(filePath);
			}
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	private void update() throws JAXBException{
		
		this.view.updateModel(model.getModel());
		this.view.updateSpecification(model.getSpecification());
		this.view.updateIntersection(model.getIntersection());
		ModelCheckerParameters<State> mp=new ModelCheckerParameters<State>();
		
		ModelChecker<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> mc=new ModelChecker<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>>(model.getModel(), model.getSpecification(), mp);
		mc.check();
		this.view.updateVerificationResults(mp);
	}
	

}
