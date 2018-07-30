package de.piedev.core.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import de.piedev.core.SkinEditor;
import de.piedev.core.module.Module;

public class LogManager extends Module
{
	private static String PATH = "logs";
	
	private static LogManager _instance;
	
	private File _file;
	
	private ArrayList<String> _messages;
	
	public LogManager(SkinEditor main)
	{
		super(main, "Log Manager");
		
		_instance = this;
		
		_messages = new ArrayList<>();
		
		if(!new File(PATH).isDirectory())
			new File(PATH).mkdirs();
		
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy HH-mm");
		
		_file = new File(PATH, "log_" + df.format(date) + ".txt");
		if (!_file.exists())
		{
			try
			{
				_file.createNewFile();
			} 
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		System.setOut(new LogStream(System.out, this));
	}
	
	public void onDisable()
	{
		saveLog();
	}

	public static void log(String message)
	{
		if (getInstance() == null)
		{
			System.out.println("Log Manager not instanciated");
			return;
		}	
		getInstance().logMessage(message, Level.INFO);
	}
	
	public static void log(String message, Level level)
	{
		if (getInstance() == null)
		{
			System.out.println("Log Manager not instanciated");
			return;
		}	
		getInstance().logMessage(message, level);
	}
	
	public void logMessage(String message, Level level)
	{	
		String format = "[" + level.getName() + "] " + message;
		System.out.println(format);
	}
	
	public void addMessage(String message)
	{
		_messages.add(message);
	}
	
	public void saveLog()
	{
		try
		{
			FileWriter writer = new FileWriter(_file, true);
			for (String string : _messages)
			{
				writer.write(string + "\n");
				writer.flush();
			}
			writer.close();
			_messages.clear();
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static LogManager getInstance()
	{
		return _instance;
	}

}
