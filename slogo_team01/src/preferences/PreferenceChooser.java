package preferences;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PreferenceChooser {

	public static File choosePrefFile() {

		File preferenceFile = null;

		JFileChooser chooser = new JFileChooser();
		/*
		 * Sets current directory with respect to workspace
		 */
		chooser.setCurrentDirectory(new File("./preferences/"));
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"XML files", "xml");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			preferenceFile = chooser.getSelectedFile();
			System.out.println("You chose to open this file: "
					+ chooser.getSelectedFile().getName());

		}
		return preferenceFile;
	}
}
