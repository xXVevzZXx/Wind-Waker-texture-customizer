package de.piedev.core.converter.display;

import javax.swing.JFrame;

import de.piedev.core.SkinEditor;
import de.piedev.core.display.PIEFrame;
import de.piedev.core.event.Listener;

public class EditorFrame extends PIEFrame implements Listener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8660903034487140233L;

	@SuppressWarnings("unchecked")
	public EditorFrame(SkinEditor main)
	{
		super(main, "Wind Waker Skin Editor", 500, 570);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
