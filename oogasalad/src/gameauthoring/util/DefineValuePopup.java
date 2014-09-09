package gameauthoring.util;

import gameauthoring.error.GAError;
import gameauthoring.error.ValueNotDefinedException;
import gameauthoring.factories.GAInputFactory;
import gameauthoring.inputfields.GAInput;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class DefineValuePopup extends JPanel {
	Map<String, String> valueMap = new HashMap<String, String>();
	Map<String, GAInput> inputMap = new HashMap<String, GAInput>();
	List<String> myKeys;
	String myTitle;
	public DefineValuePopup(String title, String type, String key) throws ValueNotDefinedException {
		this(title, new ArrayList<String>(Arrays.asList(type, key)));
	}
	public DefineValuePopup (String title, List<String> keys) throws ValueNotDefinedException {
		this(title,keys, new HashMap<String, Map<String, String>>());
	}
	
	
	public DefineValuePopup(String title, List<String> keys, Map<String, Map<String, String>> mapOfValues) throws ValueNotDefinedException {
		super(new MigLayout("wrap 2"));
		myKeys = keys;
		myTitle = title;
		for (int i = 0; i< keys.size(); i+=2) {
			String key = keys.get(i + 1);
			String type = keys.get(i);
			JLabel label = new JLabel(key + ": ");
			add(label);
			GAInput input = GAInputFactory.createGAInput(type);
			add((Component) input);
			inputMap.put(key, input);
		}
		if (!mapOfValues.isEmpty()) {
			for (String mainKey : mapOfValues.keySet()) {
				Map<String, String> currentMap = mapOfValues.get(mainKey);
					for (String key: currentMap.keySet()) {
						GAInput input = inputMap.get(key);
						input.setValue(currentMap.get(key));
					}
			}
			
		}
		showDialog();
		
		
	}
	
	private void showDialog() throws ValueNotDefinedException {
		int result = JOptionPane.showConfirmDialog(null, this,
				myTitle, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			for (int i = 0; i < myKeys.size(); i += 2) {
				String key = myKeys.get(i + 1);
				if (inputMap.get(key).getValue().equals("")) {
					GAError.displayError("Value for " + key + " not defined!");
					throw new ValueNotDefinedException(key);
				} else {
					valueMap.put(key, inputMap.get(key).getValue());
				}
			}
		} 
	}
	
	public String get(String key) {
		return valueMap.get(key);
	}
	
	public Map<String, String> getMap() {
		return valueMap;
	}
	public boolean isDefined() {
		return !valueMap.isEmpty();
	}
}