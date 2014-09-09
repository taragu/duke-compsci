package gameauthoring.inputfields;

import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;

import gameauthoring.util.Bundles;
import gameauthoring.workspace.GameObjectPanel;

@SuppressWarnings("serial")
public class GAItemComboBox extends GAComboBox {
	private static final String TYPE = "CharItem";
	private static final ResourceBundle myProperties = Bundles.getBundleOfType(TYPE);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GAItemComboBox(String type) {
		super();
		String[] options = myProperties.getString(type).split(GameObjectPanel.DELIMITER);
		setModel(new DefaultComboBoxModel(options));
	}

}
