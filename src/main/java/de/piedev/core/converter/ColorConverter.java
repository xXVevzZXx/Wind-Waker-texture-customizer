package de.piedev.core.converter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ColorConverter
{
	private HashMap<Integer, Color> _colorsToReplace = new HashMap<>();
	private HashMap<Integer, String> _imagesToReplace = new HashMap<>();
	private BufferedImage _source;
	
	public ColorConverter(BufferedImage source)
	{
		_source = source;
	}
	
	public void addReplacement(int oldColor, Color newColor)
	{
		_colorsToReplace.put(oldColor, newColor);
	}
	
	public void addReplacement(int oldColor, String newImage)
	{
		_imagesToReplace.put(oldColor, newImage);
	}
	
	public void convert() 
	{
		changeColors();
	}
	
	private void changeColors()
	{
	    int width = _source.getWidth();
	    int height = _source.getHeight();

	    for (int x = 0; x < width; x++) 
	    {
	        for (int y = 0; y < height; y++) 
	        {
	        	int oldColor = _source.getRGB(x, y);
	            if (_colorsToReplace.containsKey(oldColor))
	            {
	            	try
	            	{
		                _source.setRGB(x, y, _colorsToReplace.get(oldColor).getRGB());
	            	}
	            	catch (Exception e) {}
	            }
	            else if (_imagesToReplace.containsKey(oldColor))
	            {
	                drawImage(x, y, oldColor, _source, _imagesToReplace.get(oldColor));
	                _imagesToReplace.remove(oldColor);
	            }
	        }
	    }
	}
	
	private void drawImage(int startx, int starty, int writercolor, BufferedImage writerImage, String newimage)
	{
		try
		{
			BufferedImage toDraw = null;
			if (newimage.startsWith("/"))
			{
				toDraw = ImageIO.read(getClass().getResource(newimage));
			}
			else
			{
				toDraw = ImageIO.read(new File(newimage));
			}

		    int width = toDraw.getWidth();
		    int height = toDraw.getHeight();
		    
		    int normalWidth = 0;
		    int normalheight = 0;
		    try 
		    {
			    while (writerImage.getRGB(startx + normalWidth,  starty + normalheight + 1) != writercolor)
			    {
			    	normalheight++;
			    }
		    }
		    catch (Exception e) {}
	    	normalheight++;
		    try 
		    {
			    while (writerImage.getRGB(startx + normalWidth + 1,  starty + normalheight) != writercolor)
			    {
			    	normalWidth++;
			    }
		    }
		    catch (Exception e) {}
	    	normalWidth++;
		    System.out.println(normalheight);
		    System.out.println(normalWidth);
		    
		    double xmult = (double) width/normalWidth;
		    double ymult = (double) height/normalheight;
		    
		    int normalX = 0;
		    int normalY = 0;
		    for (double x = 0; x < width; x += xmult) 
		    {
		        for (double y = 0; y < height; y += ymult) 
		        {
		        	try 
				    {
		        		writerImage.setRGB(startx + normalX, starty + normalY, toDraw.getRGB((int) x, (int) y));
				    }
				    catch (Exception e) {}
		        	normalY++;
		        }
		        normalX++;
		        normalY = 0;
		    }
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
	}
	
	public BufferedImage getImage()
	{
		return _source;
	}
}
