package de.piedev.core.display;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import de.piedev.core.SkinEditor;
import de.piedev.core.config.Config;
import de.piedev.core.config.ConfigManager;
import de.piedev.core.converter.Model;
import de.piedev.core.converter.Texture;
import de.piedev.core.converter.TextureType;
import de.piedev.core.event.Events;
import de.piedev.core.event.Listener;
import de.piedev.core.event.types.TickEvent;
import de.piedev.core.preset.Preset;

public class EditorFrame extends JFrame implements Listener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8660903034487140233L;
	
	private SkinEditor _main;
	
	private JPanel _contentPane;
	private JComboBox<Model> _modelSelector;
	private JLabel _lblModelToEdit; 
	private JButton _createTextures; 
	private JToggleButton _simplemodeButton;
	private JButton _sourcebutton;
	private JButton _loadPreset;
	private JButton _savePreset;
	
	private Model _currentModel;
	private boolean _update;
	private boolean _simplemode;
	
	private HashMap<Texture, Color> _currentColors = new HashMap<>();
	private HashMap<Texture, String> _currentImages = new HashMap<>();

	@SuppressWarnings("unchecked")
	public EditorFrame(SkinEditor main)
	{
		_main = main;
		
		setTitle("Wind Waker Skin Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 570);
		setResizable(false);
		_contentPane = new JPanel();
		_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(_contentPane);
		
		_contentPane.setLayout(null);
		
		_savePreset = new JButton("Save Preset");
		_savePreset.setBounds(310, 510, 166, 23);
		_savePreset.setFont(new Font("Arial", Font.PLAIN, 15));
		
		_savePreset.addActionListener((e) ->
		{
			String name = JOptionPane.showInputDialog(this, "Please enter the Preset name");
			_main.getTextureMnager().savePreset(_currentModel, _currentColors, _simplemode, name);
		});
		
		_contentPane.add(_savePreset);
		
		_loadPreset = new JButton("Load Preset");
		_loadPreset.setFont(new Font("Arial", Font.PLAIN, 15));
		_loadPreset.setBounds(310, 477, 166, 23);
		
		_loadPreset.addActionListener((e) ->
		{
			FileDialog dialog = new FileDialog(this, "Select an Image", FileDialog.LOAD);
			dialog.setVisible(true);
			String file = dialog.getFile();
			if(file == null)
	        	return;
			
			String path = dialog.getDirectory() + file;
					
			if (path.endsWith("wwpreset"))
			{
				Preset preset = _main.getTextureMnager().loadPreset(path);
				
				_currentModel = preset.getModel();
				_currentColors = preset.getColors();
				_simplemode = preset.isSimpleMode();
			}
			
			_update = true;
			
			_contentPane.repaint();
		});
		
		_contentPane.add(_loadPreset);
		
		_simplemodeButton = new JToggleButton("Simplemode");
		_simplemodeButton.setFont(new Font("Arial", Font.PLAIN, 15));
		_simplemodeButton.setBounds(10, 510, 120, 23);
		
		_simplemodeButton.addActionListener((e) ->
		{
			_simplemode = _simplemodeButton.isSelected();
			_currentModel = null;
		});
		
		_contentPane.add(_simplemodeButton);
		
		_createTextures = new JButton("Generate Textures");
		_createTextures.setBounds(141, 510, 160, 23);
		_createTextures.setFont(new Font("Arial", Font.PLAIN, 15));
		
		_createTextures.addActionListener((e) ->
		{

			if (ConfigManager.getInstance().getConfig("Sourcefiles").getString(_currentModel.toString()).contentEquals("PATH"))
			{
				JOptionPane.showMessageDialog(this, "Please set a Source Texture!");
				return;
			}
			
			if (_simplemode)
			{
				for (Texture texture : ((HashMap<Texture, Color>) _currentColors.clone()).keySet())
				{
					if (!texture.hasShades())
						continue;

					Color color = _currentColors.get(texture);
					for (Texture shades : texture.getShades())
					{
						_currentColors.put(shades, new Color(_main.getTextureMnager().getShadeOfColor(color, shades)));
					}
				}
			}
			
			_main.getTextureMnager().generatetextures(_currentModel,
					(HashMap<Texture, Color>) _currentColors.clone(),
					(HashMap<Texture, String>) _currentImages.clone());

			_update = true;
			JOptionPane.showMessageDialog(this, "Convertation completed!");
		});
		
		_contentPane.add(_createTextures);
		
		_sourcebutton = new JButton("Set Source Texture");
		_sourcebutton.addActionListener((e) -> 
		{
			FileDialog dialog = new FileDialog(this, "Select an Image", FileDialog.LOAD);
			dialog.setVisible(true);
			String file = dialog.getFile();
			if(file == null)
	        	return;
			
			String source = dialog.getDirectory() + file;
					
			if (source.endsWith("png") || source.endsWith("jpg"))
			{
				Config config = ConfigManager.getInstance().getConfig("Sourcefiles"); 
				config.setString(_currentModel.toString(), source);
				ConfigManager.getInstance().safeConfigs();	
			}
		});
		_sourcebutton.setBounds(314, 17, 162, 23);
		_contentPane.add(_sourcebutton);
		
		_lblModelToEdit = new JLabel("Model to edit");
		_lblModelToEdit.setFont(new Font("Arial", Font.PLAIN, 20));
		_lblModelToEdit.setBounds(10, 11, 123, 36);
		_contentPane.add(_lblModelToEdit);
		
		_modelSelector = new JComboBox<Model>();
		_modelSelector.setFont(new Font("Arial", Font.PLAIN, 18));
		_modelSelector.setBounds(140, 19, 168, 20);
		
		for (Model model : Model.values())
		{
			_modelSelector.addItem(model);
		}
		
		_contentPane.add(_modelSelector);
		
		setVisible(true);
	}
	
	@Events
	public void updateFrame(TickEvent event)
	{
		Model currentModel = (Model) _modelSelector.getSelectedItem();
		if (_currentModel != currentModel || _update)
		{
			if (_currentModel != currentModel)
			{
				_currentColors.clear();
				_currentImages.clear();
				_simplemode = false;
				
				for (Texture texture : currentModel.getTextures())
				{
					_currentColors.put(texture, texture.getDefaultColor());
				}
			}
			_contentPane.removeAll();
			_contentPane.add(_lblModelToEdit);
			_contentPane.add(_modelSelector);
			_contentPane.add(_createTextures);
			_contentPane.add(_sourcebutton);
			_contentPane.add(_simplemodeButton);
			_contentPane.add(_loadPreset);
			_contentPane.add(_savePreset);
			
			_currentModel = currentModel;
			_update = false;
			
			int labelx = 20;
			int labely = 58;
			
			int buttonx = 150;
			int buttony = 55;
			
			for (Texture texture : currentModel.getTextures())
			{
				if (texture.isShade() && _simplemode)
				{
					_currentColors.remove(texture);
					continue;
				}
				JLabel lblNewLabel = new JLabel(texture.getFriendlyName());
				lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
				lblNewLabel.setBounds(labelx, labely, 150, 20);
				_contentPane.add(lblNewLabel);
				
				if (texture.getType() == TextureType.COLOR)
				{
					JButton btnChangeColor = new JButton("Change Color");
					btnChangeColor.setBackground(_currentColors.get(texture));
					btnChangeColor.setFont(new Font("Arial", Font.PLAIN, 15));
					btnChangeColor.setBounds(buttonx, buttony, 150, 23);
					_contentPane.add(btnChangeColor);
					
					btnChangeColor.addActionListener((bEvent) ->
					{
						Color newColor = btnChangeColor.getBackground();	
						newColor = JColorChooser.showDialog(this, "Select your prefered Color", btnChangeColor.getBackground());
						
						if (newColor != null)
							btnChangeColor.setBackground(newColor);
						
						_currentColors.put(texture, newColor);
						_contentPane.repaint();
					});
				}
				else if (texture.getType() == TextureType.IMAGE)
				{
					JButton btnChangeImage = new JButton("Standard");
					btnChangeImage.setFont(new Font("Arial", Font.PLAIN, 15));
					btnChangeImage.setBounds(buttonx, buttony, 150, 23);
					_contentPane.add(btnChangeImage);
					
					btnChangeImage.addActionListener((bEvent) ->
					{
						String newImage = btnChangeImage.getName();	
						FileDialog dialog = new FileDialog(this, "Select an Image", FileDialog.LOAD);
						dialog.setVisible(true);
						String file = dialog.getFile();
						if(file == null)
				        	return;
						
						newImage = dialog.getDirectory() + file;
								
						if (newImage.endsWith("png") || newImage.endsWith("jpg"))
						{
							btnChangeImage.setText(file);
							_currentImages.put(texture, newImage);
						}
						
						_contentPane.repaint();
					});
				}
				
				labely += 30;
				buttony += 30;
			}
			
			_contentPane.repaint();
		}
	}
}
