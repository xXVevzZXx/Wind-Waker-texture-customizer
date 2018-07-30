package de.piedev.core.module;

import java.util.ArrayList;

import de.piedev.core.event.Event;
import de.piedev.core.event.EventManager;
import de.piedev.core.logging.LogManager;

public class ModuleManager
{
	private Object _eventSyncLock = new Object();
	private EventManager _eventManager;
	private ArrayList<Module> _modules = new ArrayList<>();

	public ModuleManager()
	{
		_eventManager = new EventManager();

		Runtime.getRuntime().addShutdownHook(new Thread(() ->
		{
			disableModules();
		}));
	}
	
	public EventManager getEventManager()
	{
		return _eventManager;
	}
	
	public void callEvent(Event event)
	{
		synchronized (_eventSyncLock)
		{
			_eventManager.callEvent(event);
		}
	}

	public boolean commandExists(String command)
	{
		for (Module module : _modules)
		{
			for (Command cmd : module.getCommands())
			{
				if (cmd.getCMD().equalsIgnoreCase(command))
					return true;
			}
		}
		return false;
	}

	public ArrayList<Command> getCommmands()
	{
		ArrayList<Command> commands = new ArrayList<>();
		for (Module module : _modules)
			commands.addAll(module.getCommands());

		return commands;
	}

	public ArrayList<Module> getModules()
	{
		return _modules;
	}

	@SuppressWarnings("unchecked")
	public <T extends Module> T addModule(Module module)
	{
		LogManager.log("Adding Module: " + module.getName());
		_modules.add(module);
		return (T) module.getClass().cast(module);
	}

	public <T extends Module> T getManager(Class<T> clazz)
	{
		for (Module module : _modules)
		{
			if (module.getClass() == clazz)
				return clazz.cast(module);
		}
		throw new IllegalArgumentException("No Manager of " + clazz.getSimpleName() + " found");
	}

	public void removeModule(Module module)
	{
		LogManager.log("Removing Module: " + module.getName());
		_modules.remove(module);
	}

	public void enableModules()
	{
		synchronized (_eventSyncLock)
		{
			for (Module module : _modules)
			{
				module.enable();
				_eventManager.registerListener(module);
			}
		}
	}

	public void disableModules()
	{
		synchronized (_eventSyncLock)
		{
			for (Module module : _modules)
			{
				module.disable();
				_eventManager.unregisterListener(module);
			}
		}
	}
}
