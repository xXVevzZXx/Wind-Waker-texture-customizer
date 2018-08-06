package de.piedev.core.converter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.annotation.Generated;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import de.piedev.core.SkinEditor;
import de.piedev.core.config.Config;
import de.piedev.core.config.ConfigManager;
import de.piedev.core.converter.display.ColorOptionsComponent;
import de.piedev.core.converter.display.EditorFrame;
import de.piedev.core.converter.display.GenerateTexturesComponent;
import de.piedev.core.converter.display.ModelSelectorComponent;
import de.piedev.core.converter.display.PresetComponent;
import de.piedev.core.converter.display.SimplemodeComponent;
import de.piedev.core.module.Module;
import de.piedev.core.preset.Preset;

public class TextureManager extends Module
{
	public static final String MADETEXTURES = "textures";
	public static final String PRESETS = "presets";
	
	private Model _currentModel;
	private boolean _simplemode;
	
	private HashMap<Texture, Color> _currentColors = new HashMap<>();
	private HashMap<Texture, String> _currentImages = new HashMap<>();
	
	private EditorFrame _frame;

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
	}
	
	@Override
	public void onEnable()
	{
		_frame = new EditorFrame(getMain());
		getMain().getModuleManager().getEventManager().registerListener(_frame);
		
		_frame.addComponent(new ModelSelectorComponent(this, _frame));
		_frame.addComponent(new SimplemodeComponent(this));
		_frame.addComponent(new PresetComponent(this, _frame));
		_frame.addComponent(new GenerateTexturesComponent(this, _frame));
		
		_frame.initComponents();
		
		changeModel(Model.LINK);
	};
	
	public void generatetextures()
	{
		try
		{
			BufferedImage image = ImageIO.read(getClass().getResource(_currentModel.getPath() + "/" + _currentModel.getPNGName()));
			ColorConverter converter = new ColorConverter(image);
			_currentColors.entrySet().stream().forEach((entry) -> 
			{
				converter.addReplacement(entry.getKey().getWriterColor().getRGB(), entry.getValue());
			});
			_currentImages.entrySet().stream().forEach((entry) ->
			{
				converter.addReplacement(entry.getKey().getWriterColor().getRGB(), entry.getValue());
			});
			
			converter.convert();
			
			BufferedImage converted = converter.getImage();
			if (!new File(MADETEXTURES + _currentModel.getPath()).isDirectory())
			{
				new File(MADETEXTURES + _currentModel.getPath()).mkdirs();
			}
			File file = new File(MADETEXTURES + _currentModel.getPath() + "/" + _currentModel.getPNGName());
			ImageIO.write(converted, "png", file);
			
			if (_currentModel.hasAdditionalImages())
			{
				for (String addimageName : _currentModel.getAdditionalImages())
				{
					BufferedImage addImage = ImageIO.read(getClass().getResource(_currentModel.getPath() + "/" + addimageName));
					ColorConverter addconverter = new ColorConverter(addImage);
					for (Texture addParse : _currentModel.getAdditionalParsers())
					{
						addconverter.addReplacement(addParse.getWriterColor().getRGB(), _currentColors.get(addParse));
					}
					addconverter.convert();
					
					BufferedImage addconverted = addconverter.getImage();
					File addfile = new File(MADETEXTURES + _currentModel.getPath() + "/" + addimageName);
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
	
	public void savePreset(String name)
	{
		Preset preset = new Preset(_currentModel, _currentColors, _simplemode);
		
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
	
	public void loadPreset(String path)
	{	
		File file = new File(path);
		try
		{
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Preset preset = (Preset) ois.readObject();
			ois.close();
			
			changeModel(preset.getModel());
			
			for (Texture texture : preset.getColors().keySet())
			{
				setTextureColor(texture, preset.getColors().get(texture));
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void changeModel(Model model)
	{
		_currentModel = model;
		_currentColors.clear();
		_currentImages.clear();
	
		for (Texture texture : model.getTextures())
		{
			_currentColors.put(texture, texture.getDefaultColor());
		}
		
		if (_frame.hasComponent(ColorOptionsComponent.class))
		{
			_frame.removeComponent(_frame.getComponent(ColorOptionsComponent.class));
		}
		
		_frame.addComponent(new ColorOptionsComponent(_frame, this, model));
	}
	
	public void setTextureColor(Texture tex, Color color)
	{
		_currentColors.put(tex, color);
		_frame.getComponent(ColorOptionsComponent.class).getButtons().get(tex).setBackground(color);
		
		if (_simplemode)
		{
			if (!tex.hasShades())
				return;

			Color shadecolor = _currentColors.get(tex);
			for (Texture shades : tex.getShades())
			{
				Color shade = new Color(getShadeOfColor(shadecolor, shades));
				_currentColors.put(shades, shade);
				_frame.getComponent(ColorOptionsComponent.class).getButtons().get(shades).setBackground(shade);
			}
		}
	}
	
	public void setSimpleMode(boolean simplemode)
	{
		_simplemode = simplemode;
	}
	
	public void setTextureImage(Texture tex, String image)
	{
		_currentImages.put(tex, image);
	}
	
	public boolean isSimpleMode()
	{
		return _simplemode;
	}
	
	public Model getCurrentModel()
	{
		return _currentModel;
	}
	
}
