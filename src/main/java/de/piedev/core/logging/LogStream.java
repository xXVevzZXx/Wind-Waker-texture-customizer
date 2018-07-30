package de.piedev.core.logging;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogStream extends PrintStream
{
	private LogManager _manager;
	private StringBuilder _lastMessage = new StringBuilder();

	public LogStream(PrintStream out, LogManager manager)
	{
		super(out);
		
		_manager = manager;
	}
	
	@Override
	public void println()
	{
		if (_lastMessage.length() > 0)
		{
			_manager.addMessage(_lastMessage.toString());
			_lastMessage.setLength(0);
		}
		super.println();
	}
	
	@Override
	public void println(String x)
	{
		println();
		
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		
		String format = "[" + df.format(date) + "] " + x;
		_manager.addMessage(format);
		super.print(format);
	}
	
	@Override
	public void print(String s)
	{
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		
		String format = "";
		if (_lastMessage.length() <= 0)
		{
			println();
			format += "[" + df.format(date) + "] ";
		}
		format += s;
		_lastMessage.append(format);
		super.print(format);
	}

}
