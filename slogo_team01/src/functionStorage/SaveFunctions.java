package functionStorage;

import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

public class SaveFunctions {

	public SaveFunctions(JComponent component){
		JFileChooser chooser = new JFileChooser();

		int returnVal = chooser.showSaveDialog(component);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File chosen = chooser.getSelectedFile();
			downloadFunctions(chosen);
		}
	}	 
	
	private void downloadFunctions(File file){
		
	}
}
