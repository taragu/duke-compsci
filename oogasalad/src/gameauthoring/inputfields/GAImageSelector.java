package gameauthoring.inputfields;

import gameauthoring.factories.GAInputFactory;
import gameauthoring.util.Bundles;
import gameauthoring.util.GAUtilMethods;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
/**
 * 
 * @author LeeYuZhou
 *
 */
@SuppressWarnings("serial")
public class GAImageSelector extends JPanel implements GAInput {
	GAInput image;
	ResourceBundle myLanguage;
	public GAImageSelector() {
		super(new MigLayout("wrap 2"));
		image = GAInputFactory.createGAInput("Label");
		add((Component) image);
		myLanguage = Bundles.getLanguageBundle();
		JButton imageSelectButton = new JButton("Select Image");
		imageSelectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = GAUtilMethods.createFileChooser();
				int returnVal = fileChooser.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getSelectedFile().getPath();
					image.setValue(path);
				}
			}
		});
		
		add(imageSelectButton);
	}
	@Override
	public String getValue() {
		return image.getValue();
	}
	@Override
	public void setValue(String input) {
		System.out.println(input);
		image.setValue(input);
	}
	@Override
	public void setUneditable() {
		image.setUneditable();
	}
	@Override
	public boolean isEditable() {
		return image.isEditable();
	}
	
	
	
}
