package de.piedev.core.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import de.piedev.core.logging.LogManager;

public class EventManager
{
	private ArrayList<Listener> _listener;
	
	public EventManager()
	{
		_listener = new ArrayList<>();
	}
	
	public void registerListener(Listener listener)
	{
		LogManager.log("Registering Events for: " + listener.getName());
		_listener.add(listener);
	}
	
	public void unregisterListener(Listener listener)
	{
		LogManager.log("Unregistering Events for: " + listener.getName());
		_listener.remove(listener);
	}
	
	public void callEvent(Event event)
	{
		Iterator<Listener> listenerIterator = _listener.iterator();
		while(listenerIterator.hasNext())
		{
			Listener listener = listenerIterator.next();
			try
			{
				for(Method method : listener.getClass().getMethods())
				{
					if(method.getParameterTypes().length > 0 && method.getParameterTypes()[0] == event.getClass())
					{
						if(method.isAnnotationPresent(Events.class))
						{
							method.invoke(listener, event);
						}
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
}
