package de.piedev.core.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Config
{

	private File _file;
	private String _name;
	
	private LinkedHashMap<String, String> _dataValues;
	
	public Config(String name) 
	{
		ConfigManager.getInstance().addConfig(this);
		_name = name;
		_file = new File(ConfigManager.PATH + File.separator + name + ".yml");
		_dataValues = new LinkedHashMap<>();
		if(!_file.exists())
		{
			try 
			{
				_file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			buildConfig(_file);
			write();
		}
		else
		{
			read();
		}
	}
	
	public String getName()
	{
		return _name;
	}
	
	public File getFile()
	{
		return _file;
	}
	
	public void setString(String data, String value)
	{
		_dataValues.put(data, value);
	}
	
	public void setInt(String data, int value)
	{
		setString(data, String.valueOf(value));
	}
	
	public void setBoolean(String data, boolean value)
	{
		setString(data, String.valueOf(value));
	}
	
	public String getString(String data)
	{	
		return _dataValues.get(data);
	}
	
	public boolean getBoolean(String data)
	{
		return Boolean.valueOf(_dataValues.get(data));
	}
	
	public int getInt(String data)
	{
		return Integer.parseInt(_dataValues.get(data));
	}
	
	protected String read()
	{
		String str = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(_file));
		    String line = br.readLine();

		    while (line != null)
		    {
		    	if(!line.startsWith("#"))
		    		_dataValues.put(line.split("-")[0], line.split("-")[1]);
		    	else
		    		_dataValues.put(line, "COMMENT");
		    	
		        line = br.readLine();
		    }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		finally 
		{
		    try 
		    {
				br.close();
			} 
		    catch (IOException e)
		    {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	protected void write()
	{
		try
		{
			FileWriter writer = new FileWriter(_file);
			for(String data : _dataValues.keySet())
			{
				String value = _dataValues.get(data);
				if(!value.contentEquals("COMMENT"))
					writer.append(data + "-" + value);
				else
					writer.append(data);
				
				writer.append(System.lineSeparator());
			}
			writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setComment(String string)
	{
		_dataValues.put("#" + string, "COMMENT");
	}
	
	public HashMap<String, String> getDataValues()
	{
		return _dataValues;
	}
	
	public void buildConfig(File file){}
	
}