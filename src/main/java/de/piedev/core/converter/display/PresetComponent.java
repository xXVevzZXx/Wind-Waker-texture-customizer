package de.piedev.core.converter.display;

import java.awt.FileDialog;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import de.piedev.core.converter.TextureManager;
import de.piedev.core.display.DisplayComponent;
import de.piedev.core.display.PIEFrame;

public class PresetComponent extends DisplayComponent
{
	public static int SAVEX = 310;
	public static int SAVEY = 510;
	public static int SAVEWIDTH = 165;
	public static int SAVEHEIGHT = 25;
	
	public static int LOADX = 310;
	public static int LOADY = 475;
	public static int LOADWIDTH = 165;
	public static int LOADHEIGHT = 25;
	
	private TextureManager _textures;
	private PIEFrame _frame;
	
	public PresetComponent(TextureManager textures, PIEFrame frame)
	{
		_textures = textures;
		_frame = frame;
	}
	
	@Override
	public void init()
	{
		JButton savePreset = new JButton("Save Preset");
		savePreset.setBounds(SAVEX, SAVEY, SAVEWIDTH, SAVEHEIGHT);
		savePreset.setFont(new Font("Arial", Font.PLAIN, 15));
		
		savePreset.addActionListener((e) ->
		{
			String name = JOptionPane.showInputDialog(_frame, "Please enter the Preset name");
			_textures.savePreset(name);
		});
		
		JButton loadPreset = new JButton("Load Preset");
		loadPreset.setFont(new Font("Arial", Font.PLAIN, 15));
		loadPreset.setBounds(LOADX, LOADY, LOADWIDTH, LOADHEIGHT);
		
		loadPreset.addActionListener((e) ->
		{
			FileDialog dialog = new FileDialog(_frame, "Select an Image", FileDialog.LOAD);
			dialog.setVisible(true);
			String file = dialog.getFile();
			if(file == null)
	        	return;
			
			String path = dialog.getDirectory() + file;
					
			if (path.endsWith("wwpreset"))
			{
				_textures.loadPreset(path);
			}
		});
		
		addObject(loadPreset);
		addObject(savePreset);
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
