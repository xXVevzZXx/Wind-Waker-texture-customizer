package de.piedev.core.module;

import de.piedev.core.SkinEditor;

public abstract class Command
{
	public SkinEditor Manager;
	
	private String _cmd;
	private String[] _aliases;
	
	private int _minLength;
	private int _maxLength;
	
	private String _usage;
	
	public Command(SkinEditor manager, String cmd, String[] aliases, int minlength, int maxlenght, String usage)
	{
		Manager = manager;
		
		_cmd = cmd;
		_aliases = aliases;
		_minLength = minlength;
		_maxLength = maxlenght;
		_usage = usage;
	}
	
	public String getCMD()
	{
		return _cmd;
	}
	
	public String[] getAliases()
	{
		return _aliases;
	}
	
	public int getMinlength()
	{
		return _minLength;
	}
	
	public int getMaxlength()
	{
		return _maxLength;
	}
	
	public String getUsage()
	{
		return _usage;
	}
	
	public abstract void execute(String[] args);
}
