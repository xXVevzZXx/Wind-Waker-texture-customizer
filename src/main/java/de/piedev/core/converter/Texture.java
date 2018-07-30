package de.piedev.core.converter;

import java.awt.Color;

public enum Texture
{
	// Link

	SKIN("Skin", new Color(247, 219, 156), new Color(247, 219, 156), TextureType.COLOR),
	BODY("Body", new Color(255, 0, 226), new Color(90, 178, 74), TextureType.COLOR),
	BODYSTRIPESLIGHT("Bodystripes Light", new Color(162, 0, 255), new Color(33, 158, 57), TextureType.COLOR),	
	BODYSTRIPESDARK("Bodystripes Dark", new Color(85, 0, 255), new Color(33, 138, 24), TextureType.COLOR),	
	HAT("Hat/Skirt", new Color(0, 255, 255), new Color(90, 178, 74), TextureType.COLOR),	
	HAIR("Hair", new Color(0, 0, 0), new Color(255, 204, 0), TextureType.COLOR),
	LEGS("Leggings", new Color(21, 255, 0), new Color(255, 255, 255), TextureType.COLOR),	
	BELT("Belt", new Color(99, 48, 8), new Color(99, 48, 8), TextureType.COLOR),	
	BELTBUCKLE("Belt Buckle", new Color(36, 30, 255), "standardbeltbuckle.png", TextureType.IMAGE),	
	ARMS("Arms", new Color(255, 158, 226), new Color(173, 227, 66), TextureType.COLOR),	
	ARMSSTRIPES("Armstripes", new Color(255, 70, 199), new Color(132, 174, 49), TextureType.COLOR),	
	SHOES("Shoes", new Color(255, 86, 113), new Color(148, 73, 8), TextureType.COLOR),	
	SHOESSHADOWS("Shoes Dark Shadows", new Color(210, 74, 97), new Color(99, 48, 8), TextureType.COLOR),	
	SHOESHIGHLIGHTS("Shoes Light Schadows", new Color(235, 78, 104), new Color(114, 58, 8), TextureType.COLOR),	
	SHOESSOLE("Shoe Sole", new Color(255, 0, 42), new Color(214, 182, 57), TextureType.COLOR);
	
	private String _friendlyName;
	
	private Color _writerColor;
	private Color _defaultColor;
	private String _defaultImage;
	private TextureType _type;
	
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
	
	public TextureType getType()
	{
		return _type;
	}
}
