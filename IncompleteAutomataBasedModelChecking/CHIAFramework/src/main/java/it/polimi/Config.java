package it.polimi;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Config {

	private static Config _instance;

	private Config() {
	}
	
	public static Config getInstance() {
		if (_instance == null) {
			_instance = new Config();
		}
		return _instance;
	}

	public static void init(String[] args) {
		_instance = new Config();
		new JCommander(_instance, args);
	}
	
	
	@Parameter(names = { "-i", "--intersection" }, required = false)
	public String intersectionPath;
	
}
