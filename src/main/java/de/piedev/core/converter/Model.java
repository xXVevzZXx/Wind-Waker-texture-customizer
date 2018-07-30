package de.piedev.core.converter;

public enum Model
{
	LINK(
			"/link",
			"tex1_160x96_6ae2a887601ba80b_14.png",
			Texture.SKIN,
			Texture.BODY,
			Texture.BODYSTRIPESDARK,
			Texture.BODYSTRIPESLIGHT,
			Texture.HAT,
			Texture.ARMS,
			Texture.ARMSSTRIPES,
			Texture.HAIR,
			Texture.BELT,
			Texture.BELTBUCKLE,
			Texture.LEGS,
			Texture.SHOES,
			Texture.SHOESSHADOWS,
			Texture.SHOESHIGHLIGHTS,
			Texture.SHOESSOLE
			);
	
	private String _path;
	private String _pngName;
	private Texture[] _textures;
	
	private Model(String path, String pngName, Texture... textures)
	{
		_path = path;
		_pngName = pngName;
		_textures = textures;
	}
	
	public String getPath()
	{
		return _path;
	}
	
	public String getPNGName()
	{
		return _pngName;
	}
	
	public Texture[] getTextures()
	{
		return _textures;
	}
}
