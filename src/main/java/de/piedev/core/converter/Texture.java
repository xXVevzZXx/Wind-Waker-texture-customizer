package de.piedev.core.converter;

import java.awt.Color;
import java.io.Serializable;

public enum Texture implements Serializable
{
	// Link

	SKIN("Skin", new Color(247, 219, 156), new Color(247, 219, 156), TextureType.COLOR),
	BODYSTRIPESLIGHT("Bodystripes Light", new Color(162, 0, 255), new Color(94, 189, 77), TextureType.COLOR),	
	BODYSTRIPESDARK("Bodystripes Dark", new Color(85, 0, 255), new Color(57, 125, 45), TextureType.COLOR),	
	BODY("Body", new Color(255, 0, 226), new Color(90, 178, 74), TextureType.COLOR, Texture.BODYSTRIPESDARK, Texture.BODYSTRIPESLIGHT),
	HAT("Hat/Skirt", new Color(0, 255, 255), new Color(90, 178, 74), TextureType.COLOR),	
	HAIR("Hair", new Color(0, 0, 0), new Color(255, 204, 0), TextureType.COLOR),
	LEGS("Leggings", new Color(21, 255, 0), new Color(255, 255, 255), TextureType.COLOR),	
	BELT("Belt", new Color(99, 48, 8), new Color(99, 48, 8), TextureType.COLOR),	
	BELTBUCKLE("Belt Buckle", new Color(36, 30, 255), "standardbeltbuckle.png", TextureType.IMAGE),	
	ARMS("Arms", new Color(255, 158, 226), new Color(173, 227, 66), TextureType.COLOR),	
	ARMSSTRIPES("Armstripes", new Color(255, 70, 199), new Color(132, 174, 49), TextureType.COLOR),	
	SHOESSHADOWS("Shoes Shadows", new Color(210, 74, 97), new Color(99, 48, 8), TextureType.COLOR),
	SHOESHIGHLIGHTS("Shoes Highlights", new Color(235, 78, 104), new Color(114, 58, 8), TextureType.COLOR),
	SHOES("Shoes", new Color(255, 86, 113), new Color(148, 73, 8), TextureType.COLOR, Texture.SHOESSHADOWS, Texture.SHOESHIGHLIGHTS),	
	SHOESSOLE("Shoe Sole", new Color(255, 0, 42), new Color(214, 182, 57), TextureType.COLOR),

	// Master Sword
	HILTSHADOWS("Shadows", new Color(175, 1, 188), new Color(49, 69, 124), TextureType.COLOR),
	HILTHIGHTLIGHTS("Highlights", new Color(246, 134, 254), new Color(107, 142, 239), TextureType.COLOR),
	HILTLIGHTHIGHTLIGHTS("Light Hightlights", new Color(244, 90, 255), new Color(79, 118, 219), TextureType.COLOR),
	HILTLIGHTSHADOWS("Light Shadows", new Color(209, 3, 225), new Color(57, 93, 173), TextureType.COLOR),
	HILTCOLOR("Hilt", new Color(238, 0, 255), new Color(74, 113, 214), TextureType.COLOR,
			Texture.HILTSHADOWS, Texture.HILTHIGHTLIGHTS, Texture.HILTLIGHTSHADOWS, Texture.HILTLIGHTHIGHTLIGHTS),
	
	// Mirror Shield
	SHIELDHIGHTLIGHTS("Highlights", new Color(246, 134, 254), new Color(107, 142, 239), TextureType.COLOR),
	SHIELDSHADOWS("Shadows", new Color(175, 1, 188), new Color(49, 69, 124), TextureType.COLOR),
	SHIELDLIGHTHADOWS("Light Shadows", new Color(209, 3, 225), new Color(57, 93, 173), TextureType.COLOR),
	SHIELDCOLOR("Shield", new Color(238, 0, 255), new Color(74, 113, 214), TextureType.COLOR,
				Texture.SHIELDLIGHTHADOWS, Texture.SHIELDSHADOWS, Texture.SHIELDHIGHTLIGHTS),
	
	// Boat
	BOATHIGHLIGHTS("Highlights", new Color(214, 130, 99), new Color(214, 130, 99), TextureType.COLOR),
	BOATLIGHTHIGHLIGHTS("Light Highlights", new Color(206, 85, 49), new Color(206, 85, 49), TextureType.COLOR),
	BOAT("Boat", new Color(198, 60, 24), new Color(198, 60, 24), TextureType.COLOR,
						Texture.BOATLIGHTHIGHLIGHTS, Texture.BOATHIGHLIGHTS),
	BOATACCESORIES("Accesories", new Color(0, 0, 255), new Color(239, 215, 49), TextureType.COLOR),
	BOATACCESORIESSECONDARY("Acces Secondary", new Color(255, 0, 255), new Color(175, 139, 0), TextureType.COLOR),

	BOATMOUTH("Mouth", new Color(255, 255, 0), new Color(104, 0, 43), TextureType.COLOR),

	BOATTEETHSHADOWS("Teeth Shadows", new Color(0, 255, 0), new Color(222, 214, 197), TextureType.COLOR),
	BOATTEETH("Teeth", new Color(0, 255, 255), new Color(239, 232, 230), TextureType.COLOR, Texture.BOATTEETHSHADOWS),

	BOATBEARDSHADOWS("Beard Shadows", new Color(0, 101, 101), new Color(184, 167, 156), TextureType.COLOR),
	BOATBEARD("Beard", new Color(0, 101, 0), new Color(217, 202, 195), TextureType.COLOR, Texture.BOATBEARDSHADOWS),
	
	// Bombs
	BOMBSHADOWS("Shadows", new Color(175, 1, 188), new Color(49, 69, 124), TextureType.COLOR),
	BOMBLIGHTHADOWS("Light Shadows", new Color(209, 3, 225), new Color(57, 93, 173), TextureType.COLOR),
	BOMBCOLOR("Bombs", new Color(238, 0, 255), new Color(74, 113, 214), TextureType.COLOR,
					Texture.BOMBSHADOWS, Texture.BOMBLIGHTHADOWS),
	
	// Leaf
	LEAFHIGHLIGHT("Highlights", new Color(0, 0, 255), new Color(0, 154, 0), TextureType.COLOR),
	LEAFSHADOW("Shadows", new Color(255, 0, 255), new Color(8, 101, 16), TextureType.COLOR),
	LEAFCOLOR("Leaf", new Color(255, 0, 0), new Color(33, 178, 41), TextureType.COLOR,
						Texture.LEAFSHADOW, Texture.LEAFHIGHLIGHT)
	;
	
	private String _friendlyName;
	
	private Color _writerColor;
	private Color _defaultColor;
	private String _defaultImage;
	private TextureType _type;
	
	private Texture[] _shades;
	
	private Texture(String friendlyname, Color writer, Color defaultColor, TextureType type)
	{
		_friendlyName = friendlyname;
		_writerColor = writer;
		_defaultColor = defaultColor;
		_type = type;
	}
	
	private Texture(String friendlyname, Color writer, String defaultImage, TextureType type)
	{
		_friendlyName = friendlyname;
		_writerColor = writer;
		_defaultImage = defaultImage;
		_type = type;
	}
	
	private Texture(String friendlyname, Color writer, Color defaultColor, TextureType type, Texture... shades)
	{
		this(friendlyname, writer, defaultColor, type);
		
		_shades = shades;
	}
	
	public String getFriendlyName()
	{
		return _friendlyName;
	}
	
	public Color getDefaultColor()
	{
		return _defaultColor;
	}
	
	public Color getWriterColor()
	{
		return _writerColor;
	}
	
	public String getDefaultImage()
	{
		return _defaultImage;
	}
	
	public Texture[] getShades()
	{
		return _shades;
	}
	
	public boolean hasShades()
	{
		return _shades != null;
	}
	
	public boolean isShade()
	{
		for (Texture tex : values())
		{
			if (tex.getShades() == null)
				continue;
			
			for (Texture shade : tex.getShades())
			{
				if (shade == this)
					return true;
			}
		}
		return false;
	}
	
	public Texture getTextureofShade()
	{
		for (Texture tex : values())
		{
			if (tex.getShades() == null)
				continue;
			
			for (Texture shade : tex.getShades())
			{
				if (shade == this)
					return tex;
			}
		}
		return null;
	}
	
	public TextureType getType()
	{
		return _type;
	}
}
