package gameauthoring.elements;

import gameauthoring.util.GAUtilMethods;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;


import net.miginfocom.swing.MigLayout;

/**
 * Wraps around labels to contain just an image for that label.
 * Also includes methods to easily change the image, or dynamically alter it.
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class ImageContainer extends JPanel {
	protected String imageFilePath;
	protected Image image;
	protected JLabel imageLabel;
	protected int width;
	protected int height; 
		
	/**
	 * Constructor for image container
	 * @param width
	 * @param height
	 */
	public ImageContainer(int width, int height) {
		this.width = width;
		this.height = height;
		setLayout(new MigLayout("insets 0"));
		imageLabel = new JLabel();
		add(imageLabel);
	}
	
	public ImageContainer(int width, int height, String filePath) {
		this(width, height);
		addObject(filePath);
	}
	
	/**
	 * Adds an image to the label based on the file path.
	 * @param imageFilePath
	 */
	public void addObject(String imageFilePath) {
		Image originalImage = null;
		try { 
			File f = new File(imageFilePath);
		    originalImage = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image resizedImage = GAUtilMethods.resizeImage(originalImage, width-1, height-1);
		this.imageFilePath = imageFilePath;
		this.image = resizedImage;
		imageLabel.setIcon(new ImageIcon(resizedImage));	
		repaint();
	}
	
	/**
	 * Adds an image to the label using an Image object.
	 * @param originalImage
	 */
	public void addObject(Image originalImage) {
		Image resizedImage = GAUtilMethods.resizeImage(originalImage, width-1, height-1);
		this.image = resizedImage;
		imageLabel.setIcon(new ImageIcon(resizedImage));	
		repaint();
	}



	
	
}
