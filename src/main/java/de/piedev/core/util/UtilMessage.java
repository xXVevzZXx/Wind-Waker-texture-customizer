package de.piedev.core.util;

public class UtilMessage
{
	public static String format(String head, String body)
	{
		if (head == null || head.equalsIgnoreCase(""))
			head = "Skin Editor";
			
		return "[" + head + "] " + body;
	}
	
	public static String combine(String[] comb)
	{
		if (comb.length == 1)
			return comb[0];
		
		String combo = comb[0];
		for (int i = 1; i < comb.length; i++)
			combo += " " + comb[i];
			
		return combo;
	}
}