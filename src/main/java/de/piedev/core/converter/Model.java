package de.piedev.core.converter;

import java.io.Serializable;

public enum Model implements Serializable
{
	LINK(
			"/link",
			"tex1_160x96_6ae2a887601ba80b_14.png",
			new String[]
			{
				"tex1_192x96_4e6ae2dc14731977_14.png",
				"tex1_192x96_5e2a09481e6d772f_14.png",
				"tex1_192x96_53b0b51318701298_14.png",
				"tex1_192x96_8233b3445ed9c835_14.png",
				"tex1_192x96_683802b0119a42c0_14.png",
				"tex1_192x96_913324e9e02819d5_14.png",
				"tex1_192x96_b61803b6a4a58334_14.png",
				"tex1_192x96_ca2176116f733723_14.png",
				"tex1_192x96_e3b9f46b66368552_14.png",
			},
			new Texture[]
			{
				Texture.SKIN	
			},
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
			),
	
	MASTERSWORD(
			"/mastersword",
			"tex1_64x64_1c6082243909111c_5b758cd08f77ae63_8.png",
			Texture.HILTCOLOR,
			Texture.HILTLIGHTHIGHTLIGHTS,
			Texture.HILTHIGHTLIGHTS,
			Texture.HILTLIGHTSHADOWS,
			Texture.HILTSHADOWS
			),
	
	MIRRORSHIELD(
			"/mirrorshield",
			"tex1_32x64_451874260317a7eb_051b4d66fbe2ae6f_8.png",
			Texture.SHIELDCOLOR,
			Texture.SHIELDHIGHTLIGHTS,
			Texture.SHIELDSHADOWS,
			Texture.SHIELDLIGHTHADOWS
			),
	
	BOAT(
			"/boat",
			"tex1_64x64_899d7460fb73dc7b_14.png",
			new String[]
					{
						"tex1_128x64_f4fe2c60e166db9c_55dd5ea6b09746d6_9.png",
						"tex1_128x64_1f28c476cfc154c5_acf04a37384e565e_9.png",
					},
					new Texture[]
					{
							Texture.BOAT,
							Texture.BOATHIGHLIGHTS,
							Texture.BOATLIGHTHIGHLIGHTS,
							Texture.BOATACCESORIES,
							Texture.BOATACCESORIESSECONDARY,
							Texture.BOATBEARD,
							Texture.BOATBEARDSHADOWS,
							Texture.BOATTEETH,
							Texture.BOATTEETHSHADOWS,
							Texture.BOATMOUTH
					},
			Texture.BOAT,
			Texture.BOATHIGHLIGHTS,
			Texture.BOATLIGHTHIGHLIGHTS,
			Texture.BOATACCESORIES,
			Texture.BOATACCESORIESSECONDARY,
			Texture.BOATBEARD,
			Texture.BOATBEARDSHADOWS,
			Texture.BOATTEETH,
			Texture.BOATTEETHSHADOWS,
			Texture.BOATMOUTH
			),
	
	BOMBS(
			"/bombs",
			"tex1_32x16_7d0e9e696ee96333_28ebb11177f365d9_8.png",
			new String[]
					{
						"tex1_48x48_720cbf8e0506d646_8590b1b62dc3ed9f_9.png",
					},
					new Texture[]
					{
							Texture.BOMBCOLOR,
							Texture.BOMBLIGHTHADOWS,
							Texture.BOMBSHADOWS	
					},
			Texture.BOMBCOLOR,
			Texture.BOMBLIGHTHADOWS,
			Texture.BOMBSHADOWS	
			),
	
	LEAF(
			"/leaf",
			"tex1_16x16_365803c775a33baa_14.png",
			new String[]
					{
						"tex1_32x64_36742a99f3bb8a44_14.png",
						"tex1_48x48_afe25e0ae57527a5_641a8507aa816a10_9.png",
						"tex1_64x64_7863986a36ef8787_14.png",
						"tex1_64x128_ca312daacff9a954_14.png",
					},
					new Texture[]
					{
							Texture.LEAFCOLOR,
							Texture.LEAFHIGHLIGHT,
							Texture.LEAFSHADOW	
					},
				Texture.LEAFCOLOR,
				Texture.LEAFHIGHLIGHT,
				Texture.LEAFSHADOW
			)
	
	;
	
	private String _path;
	private String _pngName;
	private Texture[] _textures;
	
	private String[] _additionalImages;
	private Texture[] _additionalParsers;
	
	private Model(String path, String pngName, String[] additionalImages, Texture[] additionalImageParsers, Texture... textures)
	{
		_path = path;
		_pngName = pngName;
		_textures = textures;
		
		_additionalImages = additionalImages;
		_additionalParsers = additionalImageParsers;
	}
	
	private Model(String path, String pngName, Texture... textures)
	{
		this(path, pngName, null, null, textures);
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
	
	public String[] getAdditionalImages()
	{
		return _additionalImages;
	}
	
	public Texture[] getAdditionalParsers()
	{
		return _additionalParsers;
	}
	
	public boolean hasAdditionalImages()
	{
		return _additionalImages != null && _additionalParsers != null;
	}
}
