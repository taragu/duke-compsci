package gameauthoring.util;

import gameplayer.PlayerEngine;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class GAUtilMethods {
	public static String joinString(List<String> listOfStrings, String delimiter) {
    	StringBuilder sb = new StringBuilder();
    	for (String str: listOfStrings) {
    		sb.append(str);
    		sb.append(delimiter);
    	}
    	return (sb.length() == 0) ? "" : sb.substring(0, sb.length()-delimiter.length());
    }
	
	public static Image resizeImage(Image original, int width, int height) {
		original = original.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		return original;
	}
	
	public static void resaveImage(String filePath, String destinationPath, int width, int height) {
		File sourceFile = new File(filePath);
		Image image = null;
		try {
			image = ImageIO.read(sourceFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedImage buffImage = toBufferedImage(resizeImage(image, width, height));
		try {
			File destinationFile = new File(destinationPath);
			ImageIO.write(buffImage, "png", destinationFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();
	    return bimage;
	}
	
	public static JFileChooser createFileChooser () {
		ResourceBundle myLanguage = Bundles.getLanguageBundle();
		JFileChooser fileChooser = new JFileChooser();
		String currentFileDirectory = System.getProperty(myLanguage.getString("FileDirectory"));
		fileChooser.setCurrentDirectory(new File(currentFileDirectory));
		return fileChooser;
	}
	public static String translateValue(String value) {
		return Integer.parseInt(value) * PlayerEngine.DEFAULT_TILE_SIZE + "";
	}
	
	public static String unTranslateValue(String value){
		return Integer.parseInt(value) / PlayerEngine.DEFAULT_TILE_SIZE + "";
	}
	
}
