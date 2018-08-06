package de.piedev.core.converter.display;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;

import de.piedev.core.converter.Model;
import de.piedev.core.converter.Texture;
import de.piedev.core.converter.TextureManager;
import de.piedev.core.converter.TextureType;
import de.piedev.core.display.DisplayComponent;
import de.piedev.core.display.PIEFrame;

public class ColorOptionsComponent extends DisplayComponent
{
	public static int XSTART = 20;
	public static int YSTART = 55;
	
	public static int XBUTTONOFFSET = 130;
	public static int YROWOFFSET = 30;
	
	public static int LABELWIDTH = 150;
	public static int LABELHEIGHT = 20;
	public static int BUTTONWIDTH = 150;
	public static int BUTTONHEIGHT = 23;
	
	private Model _model;
	private TextureManager _textures;
	private PIEFrame _frame;
	
	private HashMap<Texture, JButton> _colorButtons = new HashMap<>();
	
	public ColorOptionsComponent(PIEFrame frame, TextureManager textures, Model model)
	{
		_frame = frame;
		_model = model;
		_textures = textures;
	}

	@Override
	public void init()
	{
		int labelx = XSTART;
		int labely = YSTART;
		
		int buttonx = XSTART + XBUTTONOFFSET;
		int buttony = YSTART;
		
		for (Texture texture : _model.getTextures())
		{
			JLabel label = new JLabel(texture.getFriendlyName());
			label.setFont(new Font("Arial", Font.PLAIN, 15));
			label.setBounds(labelx, labely, LABELWIDTH, LABELHEIGHT);
			
			JButton colorbutton = new JButton();
			colorbutton.setFont(new Font("Arial", Font.PLAIN, 15));
			colorbutton.setBounds(buttonx, buttony, BUTTONWIDTH, BUTTONHEIGHT);
			
			if (texture.getType() == TextureType.COLOR)
			{
				colorbutton.setText("Change Color");
				colorbutton.setBackground(texture.getDefaultColor());
				
				colorbutton.addActionListener((bEvent) ->
				{
					Color newColor = colorbutton.getBackground();	
					newColor = JColorChooser.showDialog(_frame, "Select your prefered Color", colorbutton.getBackground());
					
					if (newColor != null)
					{
						colorbutton.setBackground(newColor);
						_textures.setTextureColor(texture, newColor);
					}
				});
			}
			else if (texture.getType() == TextureType.IMAGE)
			{
				colorbutton.setText("Standard");	
				colorbutton.addActionListener((bEvent) ->
				{
					String newImage = colorbutton.getName();	
					FileDialog dialog = new FileDialog(_frame, "Select an Image", FileDialog.LOAD);
					dialog.setVisible(true);
					String file = dialog.getFile();
					if(file == null)
			        	return;
					
					newImage = dialog.getDirectory() + file;
							
					if (newImage.endsWith("png") || newImage.endsWith("jpg"))
					{
						colorbutton.setText(file);
						_textures.setTextureImage(texture, newImage);
					}
				});
			}
			
			addObject(label);
			addObject(colorbutton);
			_colorButtons.put(texture, colorbutton);
			
			labely += YROWOFFSET;
			buttony += YROWOFFSET;
		}
	}
	
	public HashMap<Texture, JButton> getButtons()
	{
		return _colorButtons;
	}

	@Override
	public void update()
	{
		
	}

	@Override
	public void deinitialize()
	{
		
	}

}
