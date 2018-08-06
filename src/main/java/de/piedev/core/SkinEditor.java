package de.piedev.core;

import de.piedev.core.converter.TextureManager;
import de.piedev.core.converter.display.EditorFrame;
import de.piedev.core.event.types.TickEvent;
import de.piedev.core.logging.LogManager;
import de.piedev.core.module.ModuleManager;
import de.piedev.core.system.SystemManager;

public class SkinEditor
{
	private static SkinEditor _instance;
	
	public static void main(String[] args)
	{
		getInstance();
	}
	
	public static SkinEditor getInstance()
	{
		if (_instance == null)
			_instance = new SkinEditor();
		
		return _instance;
	}
	
	private ModuleManager _moduleManager;
	private TextureManager _textureManager;
	private SystemManager _systemManager;
	
	private SkinEditor()
	{
		LogManager logmanager = new LogManager(this);
		LogManager.log("Starting up System please wait...");
		
		_moduleManager = new ModuleManager();
		
		_moduleManager.addModule(logmanager);
		_textureManager = _moduleManager.addModule(new TextureManager(this));
		_systemManager = _moduleManager.addModule(new SystemManager(this));
		
		_moduleManager.enableModules();
		
		initUpdater();
	}
	
	public void initUpdater()
	{
		while (true)
		{
			_moduleManager.callEvent(new TickEvent());
			try
			{
				Thread.sleep(20);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public TextureManager getTextureManager()
	{
		return _textureManager;
	}
	
	public SystemManager getSystem()
	{
		return _systemManager;
	}
	
	public ModuleManager getModuleManager()
	{
		return _moduleManager;
	}
}
