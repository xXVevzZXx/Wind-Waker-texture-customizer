package de.piedev.core.system;

import java.util.Scanner;

import de.piedev.core.SkinEditor;
import de.piedev.core.logging.LogManager;
import de.piedev.core.module.Module;
import de.piedev.core.threads.Task;

public class SystemManager extends Module
{
	
	public SystemManager(SkinEditor manager)
	{
		super(manager, "System");
	}
	
	@Override
	public void onEnable()
	{
		readConsole();
	}
	
	public void readConsole()
	{
		Task.POOL.execute(new Runnable()
		{
			@Override
			public void run()
			{
				Scanner scanner = new Scanner(System.in);
				while (isEnabled())
				{
					String entry = "+" + scanner.nextLine();
					for (Module module : getMain().getModuleManager().getModules())
					{
						module.compute(entry);
					}
				}
				scanner.close();
			}
		});
	}
	
	public void stopSystem()
	{
		LogManager.log("Stopping the System...");
		getMain().getModuleManager().disableModules();
		System.exit(0);
	}
	
}
