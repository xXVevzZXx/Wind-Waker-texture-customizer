package de.piedev.core.converter.display;

import java.awt.Font;
import java.awt.event.ItemEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import de.piedev.core.converter.Model;
import de.piedev.core.converter.TextureManager;
import de.piedev.core.display.DisplayComponent;
import de.piedev.core.display.PIEFrame;

public class ModelSelectorComponent extends DisplayComponent
{
	public static int LABELX = 10;
	public static int LABELY = 10;
	public static int LABELWIDTH = 120;
	public static int LABELHEIGHT = 35;

	public static int SELECTORX = 140;
	public static int SELECTORY = 15;
	public static int SELECTORWIDTH = 170;
	public static int SELECTORHEIGHT = 25;

	private TextureManager _textures;
	private PIEFrame _frame;
	
	public ModelSelectorComponent(TextureManager textures, PIEFrame frame)
	{
		_textures = textures;
		_frame = frame;
	}
	
	@Override
	public void init()
	{
		JLabel modelLabel = new JLabel("Model to edit");
		modelLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		modelLabel.setBounds(LABELX, LABELY, LABELWIDTH, LABELHEIGHT);
		
		JComboBox<Model> modelSelector = new JComboBox<Model>();
		modelSelector.setFont(new Font("Arial", Font.PLAIN, 20));
		modelSelector.setBounds(SELECTORX, SELECTORY, SELECTORWIDTH, SELECTORHEIGHT);
		
		modelSelector.addItemListener((e) ->
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				if (_frame.isPostInit())
				{
					_textures.changeModel((Model) e.getItem());
				}
			}
		});
		
		for (Model model : Model.values())
		{
			modelSelector.addItem(model);
		}
		
		addObject(modelLabel);
		addObject(modelSelector);
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
