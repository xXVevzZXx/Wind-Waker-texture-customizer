package de.piedev.core.preset;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;

import de.piedev.core.converter.Model;
import de.piedev.core.converter.Texture;

public class Preset implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5345118843966922630L;
	
	private Model _model;
	private HashMap<Texture, Color> _colors;
	private boolean _simpleMode;
	
	public Preset(Model model, HashMap<Texture, Color> colors, boolean simpleMode)
	{
		_model = model;
		_colors = colors;
		_simpleMode = simpleMode;
	}
	
	public HashMap<Texture, Color> getColors()
	{
		return (HashMap<Texture, Color>) _colors.clone();
	}
	
	public Model getModel()
	{
		return _model;
	}
	
	public boolean isSimpleMode()
	{
		return _simpleMode;
	}

}
