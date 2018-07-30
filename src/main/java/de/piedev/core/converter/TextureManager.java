package de.piedev.core.converter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import de.piedev.core.SkinEditor;
import de.piedev.core.config.Config;
import de.piedev.core.config.ConfigManager;
import de.piedev.core.module.Module;

public class TextureManager extends Module
{
	public static final String MADETEXTURES = "textures";

	public TextureManager(SkinEditor manager)
	{
		super(manager, "Texture Manager");
		
		if (!new File(MADETEXTURES).isDirectory())
		{
			new File(MADETEXTURES).mkdirs();
		}
		
		ConfigManager.getInstance().addConfig(new Config("Sourcefiles")
		{
			@Override
			public void buildConfig(File file)
			{
				for (Model model : Model.values())
				{
					setString(model.toString(), "PATH");	
				}
			}
		});
	}
	
	public void generatetextures(Model model, HashMap<Texture, Color> colors, HashMap<Texture, String> images)
	{
		try
		{
			BufferedImage source = ImageIO.read(new File(ConfigManager.getInstance().getConfig("Sourcefiles").getString(model.toString())));
			BufferedImage overlay = ImageIO.read(getClass().getResource(model.getPath() + "/" + model.getPNGName()));
			ColorConverter converter = new ColorConverter(source, overlay);
			colors.entrySet().stream().forEach((entry) -> 
			{
				converter.addReplacement(entry.getKey().getWriterColor().getRGB(), entry.getValue());
			});
			images.entrySet().stream().forEach((entry) ->
			{
				converter.addReplacement(entry.getKey().getWriterColor().getRGB(), entry.getValue());
			});
			
			converter.convert();
			
			BufferedImage converted = converter.getImage();
			if (!new File(MADETEXTURES + model.getPath()).isDirectory())
			{
				new File(MADETEXTURES + model.getPath()).mkdirs();
			}
			File file = new File(MADETEXTURES + model.getPath() + "/" + model.getPNGName());
			ImageIO.write(converted, "png", file);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
