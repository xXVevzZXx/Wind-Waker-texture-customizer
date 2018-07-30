package de.piedev.core.module;

import java.util.ArrayList;

import de.piedev.core.SkinEditor;
import de.piedev.core.event.Listener;
import de.piedev.core.logging.LogManager;
import de.piedev.core.util.UtilMessage;

public class Module implements Listener
{	
	private String _name;
	
	private ArrayList<Command> _commands;
	
	private boolean _isEnabled;
	
	public SkinEditor _main;
	
	public Module(SkinEditor main, String name)
	{
		_main = main;
		_name = name;
		_commands = new ArrayList<>();
		addCommands(main);
	}
	
	public void compute(String message)
	{
		for(Command cmd : _commands)
		{
			boolean success = checkCommand(cmd.getCMD(), message);
			for(String str : cmd.getAliases())
			{
				if(success)
					break;
				
				success = checkCommand(str, message);
			}
			
			if(!success)
				continue;
			
			String[] args = new String[message.length() - 1];
			int i = 0;
			for(int e = 1; e < message.length(); e++)
			{
				args[i] = message.split(" ")[e];
				i++;
			}
			
			if(args.length > cmd.getMaxlength() || args.length < cmd.getMinlength())
				success = false;
			
			LogManager.log("Issued the following command: " + cmd.getCMD() + (args.length != 0 ? " " + UtilMessage.combine(args) : ""));
			
			if(success)
			{
				cmd.execute(args);
				continue;
			}
			else
			{
				LogManager.log(UtilMessage.format("Command", "Correct usage: " + cmd.getUsage()));
				continue;
			}
		}
	}
	
	public boolean checkCommand(String cmd, String message)
	{
		String entered = message;
		entered = entered.toLowerCase();
		cmd = cmd.toLowerCase();
		
		return entered.split(" ")[0].startsWith(cmd);
	}
	
	public void addCommands(SkinEditor main)
	{
		
	}
	
	public void addCommand(Command command)
	{
		_commands.add(command);
	}

	public SkinEditor getMain()
	{
		return _main;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public ArrayList<Command> getCommands()
	{
		return _commands;
	}
	
	public boolean isEnabled()
	{
		return _isEnabled;
	}
	
	public void disable()
	{
		_isEnabled = false;
		onDisable();
	}
	
	public void enable()
	{
		_isEnabled = true;
		onEnable();
	}
	
	public void onEnable() {}
	
	public void onDisable() {}
	
}
