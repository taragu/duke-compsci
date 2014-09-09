package functionStorage;

import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

public class LoadFunctions {

	public LoadFunctions(JComponent component){
		JFileChooser chooser = new JFileChooser();

		int returnVal = chooser.showOpenDialog(component);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File chosen = chooser.getSelectedFile();
			uploadFunctions(chosen);
		}
	}
	
	private void uploadFunctions(File file){
		
	}
	
}
