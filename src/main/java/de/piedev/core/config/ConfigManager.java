package de.piedev.core.config;

import java.io.File;
import java.util.ArrayList;

public class ConfigManager
{

	public final static String PATH = "config";
	private static ConfigManager _instance;
	
	public static ConfigManager getInstance()
	{
		if(_instance == null)
			_instance = new ConfigManager();
		
		return _instance;
	}
	
	public static void terminate()
	{
		_instance = null;
	}
	
	private ArrayList<Config> _configs;
	
	private ConfigManager() 
	{
		_configs = new ArrayList<>();
		if(!new File(PATH).isDirectory())
			new File(PATH).mkdirs();
	}
	
	public void reloadConfigs()
	{
		for(Config config : _configs)
		{
			config.read();
		}
	}
	
	public Config getConfig(String name)
	{
		for(Config config : _configs)
		{
			if(config.getName().contentEquals(name))
				return config;
		}
		return addConfig(new Config(name));
	}
	
	public void safeConfigs()
	{
		for(Config config : _configs)
			config.write();
	}
	
	public Config addConfig(Config config)
	{
		_configs.add(config);
		return config;
	}
	
}
