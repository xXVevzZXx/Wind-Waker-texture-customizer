package de.piedev.core.converter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.MalformedInputException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.xml.soap.Text;

import de.piedev.core.SkinEditor;
import de.piedev.core.config.Config;
import de.piedev.core.config.ConfigManager;
import de.piedev.core.module.Module;
import de.piedev.core.preset.Preset;

public class TextureManager extends Module
{
	public static final String MADETEXTURES = "textures";
	public static final String PRESETS = "presets";

	public TextureManager(SkinEditor manager)
	{
		super(manager, "Texture Manager");
		
		if (!new File(MADETEXTURES).isDirectory())
		{
			new File(MADETEXTURES).mkdirs();
		}
		
		if (!new File(PRESETS).isDirectory())
		{
			new File(PRESETS).mkdirs();
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
			BufferedImage image = ImageIO.read(getClass().getResource(model.getPath() + "/" + model.getPNGName()));
			ColorConverter converter = new ColorConverter(image);
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
			
			if (model.hasAdditionalImages())
			{
				for (String addimageName : model.getAdditionalImages())
				{
					BufferedImage addImage = ImageIO.read(getClass().getResource(model.getPath() + "/" + addimageName));
					ColorConverter addconverter = new ColorConverter(addImage);
					for (Texture addParse : model.getAdditionalParsers())
					{
						addconverter.addReplacement(addParse.getWriterColor().getRGB(), colors.get(addParse));
					}
					addconverter.convert();
					
					BufferedImage addconverted = addconverter.getImage();
					File addfile = new File(MADETEXTURES + model.getPath() + "/" + addimageName);
					ImageIO.write(addconverted, "png", addfile);
				}
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public int getShadeOfColor(Color selectedColor, Texture shade)
	{
		Color defaultShade = shade.getDefaultColor();
		
		float[] defaultShadeHSB = Color.RGBtoHSB(defaultShade.getRed(), defaultShade.getGreen(), defaultShade.getBlue(), new float[3]);
		
		float[] selectedColorHSB = Color.RGBtoHSB(selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue(), new float[3]);
		
		selectedColorHSB[1] = defaultShadeHSB[1];
		selectedColorHSB[2] = defaultShadeHSB[2];
		
		int finalColor = Color.HSBtoRGB(selectedColorHSB[0], selectedColorHSB[01], selectedColorHSB[2]);
		return finalColor;
	}
	
	public void savePreset(Model model, HashMap<Texture, Color> colors, boolean simpleMode, String name)
	{
		Preset preset = new Preset(model, colors, simpleMode);
		
		File file = new File(PRESETS + "/" + name + ".wwpreset");
		try
		{
			if (file.exists())
			{
				JOptionPane.showMessageDialog(null, "This preset file already exists");
				return;
			}
			else
			{
				file.createNewFile();
			}
			
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(preset);
			oos.flush();
			oos.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public Preset loadPreset(String path)
	{	
		File file = new File(path);
		try
		{
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Preset preset = (Preset) ois.readObject();
			ois.close();
			return preset;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}
