package de.piedev.core.converter.display;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import de.piedev.core.converter.TextureManager;
import de.piedev.core.display.DisplayComponent;
import de.piedev.core.display.PIEFrame;

public class GenerateTexturesComponent extends DisplayComponent
{
	public static int X = 140;
	public static int Y = 510;
	public static int WIDTH = 160;
	public static int HEIGHT = 25;

	private TextureManager _textures;
	private PIEFrame _frame;
	
	public GenerateTexturesComponent(TextureManager textures, PIEFrame frame)
	{
		_textures = textures;
		_frame = frame;
	}
	@Override
	public void init()
	{
		JButton generateTextures = new JButton("Generate Textures");
		generateTextures.setBounds(X, Y, WIDTH, HEIGHT);
		generateTextures.setFont(new Font("Arial", Font.PLAIN, 15));
		
		generateTextures.addActionListener((e) ->
		{
			_textures.generatetextures();

			JOptionPane.showMessageDialog(_frame, "Convertation completed!");
		});
		
		addObject(generateTextures);
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
