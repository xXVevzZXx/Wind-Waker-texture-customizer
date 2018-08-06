package de.piedev.core.converter.display;

import java.awt.Font;

import javax.swing.JToggleButton;

import de.piedev.core.converter.TextureManager;
import de.piedev.core.display.DisplayComponent;

public class SimplemodeComponent extends DisplayComponent
{
	public static int X = 10;
	public static int Y = 510;
	public static int WIDTH = 120;
	public static int HEIGHT = 25;
	
	private TextureManager _textures;
	
	public SimplemodeComponent(TextureManager textures)
	{
		_textures = textures;
	}

	@Override
	public void init()
	{
		JToggleButton toggle = new JToggleButton("Simplemode");
		toggle.setFont(new Font("Arial", Font.PLAIN, 15));
		toggle.setBounds(X, Y, WIDTH, HEIGHT);
		
		toggle.addActionListener((e) ->
		{
			_textures.setSimpleMode(toggle.isSelected());
		});
		
		addObject(toggle);
	}

	@Override
	public void deinitialize()
	{
		
	}

	@Override
	public void update()
	{
		
	}

}
