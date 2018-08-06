package de.piedev.core.display;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.piedev.core.SkinEditor;
import de.piedev.core.event.Events;
import de.piedev.core.event.Listener;
import de.piedev.core.event.types.TickEvent;

public abstract class PIEFrame extends JFrame implements Listener
{
	private SkinEditor _main;
	private JPanel _contentPane;
	
	private boolean _postInit;
	
	private List<DisplayComponent> _components = new LinkedList<>();
	
	public PIEFrame(SkinEditor main, String name, int width, int height)
	{
		_main = main;
		
		setTitle(name);
		setBounds(500, 500, width, height);
		setResizable(false);
		_contentPane = new JPanel();
		_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(_contentPane);
		_contentPane.setLayout(null);
	}
	
	public void initComponents()
	{
		for (DisplayComponent comp : _components)
		{
			initComp(comp);
		}
		_postInit = true;
		_contentPane.repaint();
	}
	
	public void updateComponents()
	{
		for (DisplayComponent comp : _components)
		{
			comp.update();
		}
		_contentPane.repaint();
	}
	
	@Events
	public void updateFrame(TickEvent event)
	{
		updateComponents();
	}
	
	public void addComponent(DisplayComponent comp)
	{
		_components.add(comp);
		
		if (_postInit)
		{
			initComp(comp);
		}
	}
	
	public void removeComponent(DisplayComponent comp)
	{
		_components.remove(comp);

		for (JComponent jcomp : comp.getJCpmponents())
		{
			getContentPane().remove(jcomp);
		}
		comp.getJCpmponents().clear();
		
		comp.deinitialize();
	}
	
	public <T extends DisplayComponent> T getComponent(Class<T> clazz)
	{
		for (DisplayComponent comp : _components)
		{
			if (comp.getClass() == clazz)
				return clazz.cast(comp);
		}
		throw new IllegalArgumentException("No Component of " + clazz.getSimpleName() + " found");
	}
	
	public <T extends DisplayComponent> boolean hasComponent(Class<T> clazz)
	{
		for (DisplayComponent comp : _components)
		{
			if (comp.getClass() == clazz)
				return true;
		}
		return false;
	}
	
	private void initComp(DisplayComponent comp)
	{
		comp.init();
		for (JComponent jcomp : comp.getJCpmponents())
		{
			getContentPane().add(jcomp);
		}
	}
	
	public boolean isPostInit()
	{
		return _postInit;
	}
	
	public JPanel getContentPane()
	{
		return _contentPane;
	}
	
	public SkinEditor getEditor()
	{
		return _main;
	}
}
