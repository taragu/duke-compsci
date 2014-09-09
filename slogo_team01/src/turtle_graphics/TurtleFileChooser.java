package turtle_graphics;

import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TurtleFileChooser {

	public static File chooseImageFile() {

		File turtleFile = null;

		JFileChooser chooser = new JFileChooser();
		/*
		 * Sets current directory with respect to workspace
		 */
		chooser.setCurrentDirectory(new File("./images/"));
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"JPG & GIF Images", "jpg", "gif");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			turtleFile = chooser.getSelectedFile();
			System.out.println("You chose to open this file: "
					+ chooser.getSelectedFile().getName());

		}
		return turtleFile;
	}
}