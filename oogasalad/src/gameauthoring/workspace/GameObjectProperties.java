package gameauthoring.workspace;
import gameauthoring.error.GAError;
import gameauthoring.error.ValueNotDefinedException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;

/**
 * A more specific implementation of GameObjectPanel that is more specific to 
 * properties.
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class GameObjectProperties extends GameObjectPanel {
	/**
	 * 
	 * @param language
	 * @param type
	 * @param controller
	 */
	public GameObjectProperties(final String language, final String type, final Controllable controller) {
		super(language, type, controller);
		JButton save = new JButton(myLanguage.getString("Save"));
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					updateData();
				} catch (ValueNotDefinedException e) {
					e.printStackTrace();
					GAError.displayError(e.getMessage());
				}
			}
		});
		mainPanel.add(save, "wrap");
	}
	protected void updateView(String id) {
		removeAllPanels();
		Map<String, String> mapInfo = myController.getDefinitionData(id);
		currentUniqueID = mapInfo.get(myLanguage.getString("UniqueID"));
		nameField.setText(mapInfo.get(myLanguage.getString("Name")));
		imageFilePath = mapInfo.get(myLanguage.getString("ImagePath"));
		if (mapInfo.containsKey(myLanguage.getString("ObjectType"))) {
			typeSelector.setSelectedItem(mapInfo.get(myLanguage.getString("ObjectType")));
		}
		if (!imageFilePath.isEmpty()) {
			imageContainer.addObject(imageFilePath);
		}
		for (String key : myProperties.getString(myType + myLanguage.getString("Interfaces")).split(DELIMITER)){
			if(mapInfo.containsKey(key)) {
					AbilityPanel panel = new AbilityPanel(key, mapInfo.get(key));
					mainPanel.add(panel, "span");
					refreshView();
			}
		}
		
		refreshView();
	}
		
}
