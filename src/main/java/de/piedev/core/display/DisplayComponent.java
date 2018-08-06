package de.piedev.core.display;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

public abstract class DisplayComponent
{
	private List<JComponent> _jcomponents = new LinkedList<>();
	
	public void addObject(JComponent component)
	{
		_jcomponents.add(component);
	}
	
	public List<JComponent> getJCpmponents()
	{
		return _jcomponents;
	}

	public abstract void init();
	public abstract void deinitialize();
	public abstract void update();
	
}
