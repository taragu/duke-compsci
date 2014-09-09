package gameauthoring.workspace;

import gameauthoring.elements.ImageContainer;
import gameauthoring.factories.GAInputFactory;
import gameauthoring.inputfields.GAInput;
import gameauthoring.util.Bundles;
import gameauthoring.util.GAUtilMethods;
import gameauthoring.error.GAError;
import gameauthoring.error.ValueNotDefinedException;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/**
 * General class that defines what is necessary in the properties tab. Also used
 * when we create a new object
 * 
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class GameObjectPanel extends JPanel {
	public static final String SWING_BASE_PATH = "javax.swing.";
	protected String myType;
	protected Controllable myController;
	protected ResourceBundle myProperties;
	protected ResourceBundle myLanguage = Bundles.getLanguageBundle();
	protected List<String> listOfDefinedAbilities;
	protected List<AbilityPanel> listOfPanels;
	protected JTextField nameField;
	protected JComboBox<String> typeSelector = new JComboBox<String>();
	protected String currentUniqueID = "";
	protected JPanel mainPanel;
	protected ImageContainer imageContainer;
	protected String imageFilePath = "";
	public static final String DELIMITER = ";;";
	public static final int SCROLL_PANEL_HEIGHT = 300;
	public static final int SCROLL_PANEL_WIDTH = 365;
	protected JScrollPane mainScroll;
	protected boolean isMultipleInterface;
	/**
	 * A more robust constructor for the propertiesTab
	 * @param language
	 * @param type
	 * @param controller
	 */
	public GameObjectPanel(String language, String type, Controllable controller) {
		listOfDefinedAbilities = new ArrayList<String>();
		listOfPanels = new ArrayList<AbilityPanel>();
		myType = type;
		myController = controller;
		ResourceBundle myPaths = Bundles.getPropertiesDirectory();
		myProperties = Bundles.getBundleOfType(myPaths.getString(myType));
		setLayout(new BorderLayout());
		mainPanel = new JPanel(new MigLayout("wrap 2"));
     	isMultipleInterface = new Boolean(myProperties.getString(type + "MultipleInterface?"));
        add(mainPanel, BorderLayout.CENTER);
		populateView(myType);
		refreshView();
	}
	
	public GameObjectPanel(String language, String type, Controllable controller, String uniqueid) {
		this(language, type, controller);
		currentUniqueID = uniqueid;
	}
	
	private boolean hasSpecificType() {
		return myProperties.containsKey(myType + myLanguage.getString("Type"));
	}
	
	private void populateView(String type) {
		
		JLabel name = new JLabel(myLanguage.getString("ObjectName"), JLabel.TRAILING);
		nameField = new JTextField(10);
		name.setLabelFor(nameField);
		mainPanel.add(name);
		mainPanel.add(nameField);
		JLabel imagelabel = new JLabel(myLanguage.getString("ObjectImage"), JLabel.TRAILING);
		imageContainer = new ImageContainer(20, 20);
		mainPanel.add(imagelabel);
		mainPanel.add(imageContainer, "wrap");
		if (hasSpecificType()) {
			JLabel typeLabel = new JLabel("Type: ");
			String[] types = myProperties.getString(myType + myLanguage.getString("Type")).split(DELIMITER);
			typeSelector = new JComboBox<String>(types);
			mainPanel.add(typeLabel);
			mainPanel.add(typeSelector,"wrap");
		}
		JLabel ability = new JLabel(myLanguage.getString("AddAbility"), JLabel.TRAILING);
		final String[] options = myProperties.getString(myType + myLanguage.getString("Interfaces")).split(DELIMITER);
		final JComboBox<String> abilitySelector = new JComboBox<String>(options);
		abilitySelector.setSelectedIndex(-1);
		JButton addAbility = new JButton("+"); 
		addAbility.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(GameObjectPanel.this.listOfDefinedAbilities.contains(abilitySelector.getSelectedItem())) {
					GAError.displayError("Ability already defined!");
				} else {
					if (!isMultipleInterface) {
						removeAllAbilityPanels();
					}
					String defaultDefinitions = myProperties.getString(options[abilitySelector.getSelectedIndex()] + "Defaults");
					AbilityPanel panel = new AbilityPanel((String) abilitySelector.getSelectedItem(), defaultDefinitions);
					mainPanel.add(panel, "span");
					refreshView();
				}
			}
			
		});
		mainPanel.add(ability);
		mainPanel.add(abilitySelector);
		mainPanel.add(addAbility, "wrap");
	}
	
	protected void addObject(String filePath) {
		imageContainer.addObject(filePath);
		imageFilePath = filePath;
	}
	
	private void removeAbilityPanel(String ability, AbilityPanel panelToRemove) {
		listOfDefinedAbilities.remove(ability);
		listOfPanels.remove(panelToRemove);
		mainPanel.remove(panelToRemove);
		refreshView();
	}
	
	private void removeAllAbilityPanels() {
		for (AbilityPanel panel : listOfPanels) {
			mainPanel.remove(panel);
		}
		listOfDefinedAbilities.clear();
		listOfPanels.clear();
		refreshView();
	}
	
	protected class AbilityPanel extends JPanel {
		String myAbility;
		Map<String, GAInput> inputFieldMap;
		Map<String, String> valuesMap = new HashMap<String, String>();
		List<String> valueList = new ArrayList<String>();
		public AbilityPanel(String ability, String abilityDefinitions) {
			super(new MigLayout("insets 0"));
			myAbility = ability;
			inputFieldMap = new HashMap<String,GAInput>();
			populateMap(abilityDefinitions);
			JPanel newPanel = generateNewPanel(myAbility);
			listOfPanels.add(this);
			listOfDefinedAbilities.add(myAbility);
			add(newPanel, "span");
			refreshView();
			JButton remove = new JButton(myLanguage.getString("Delete"));
			remove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					removeAbilityPanel(myAbility, AbilityPanel.this);
				}
			});
			add(remove, "wrap");
			
		}
		
		private void populateMap(String abilityDefinitions) {
			String[] arrayOfDefinitions = abilityDefinitions.split(DELIMITER);
			for (int i = 0; i < arrayOfDefinitions.length && !abilityDefinitions.isEmpty(); i += 2) {
				String value = arrayOfDefinitions[i];
				String key = arrayOfDefinitions[i+1];
				valuesMap.put(key, value);
				valueList.add(key);
				
			}
		}
		
		protected JPanel generateNewPanel(String s) {
			MigLayout layout = new MigLayout("insets 0");
			String[] optionsArray = myProperties.getString(s).split(DELIMITER);
			List<String[]> options = generateOptionsList(optionsArray);
			
			JPanel p = new JPanel(layout);

			JLabel title = new JLabel(s, JLabel.TRAILING);
			p.add(title, "wrap");
			for (String[] pair : options) {
				String inputDescription = pair[1];
				String inputType = pair[0];
				JLabel l = new JLabel(inputDescription, JLabel.TRAILING);
				GAInput inputField = GAInputFactory.createGAInput(inputType);
				if (valuesMap.containsKey(inputDescription)) {
					String desiredValue = valuesMap.get(inputDescription);
					inputField.setValue(desiredValue);
				}
				p.add(l);
				p.add((Component) inputField, "wrap");
				inputFieldMap.put(inputDescription, inputField);
			}
			return p;
		}
		
		protected String convertToString() throws ValueNotDefinedException {
			StringBuilder sb = new StringBuilder();
			for (String key : valueList) {
				GAInput inputField= inputFieldMap.get(key);
				
				String value = inputField.getValue();
				
				if (value.isEmpty()) {
					throw new ValueNotDefinedException(key, myAbility);
				}
				sb.append(value + DELIMITER + key + DELIMITER);
			}
			return sb.toString().substring(0, sb.length() - DELIMITER.length());
		}
	}
	


	private List<String[]> generateOptionsList(String[] o) {
		List<String[]> list = new ArrayList<String[]>();
		for (int i = 0; i < o.length; i += 2) {
			String[] temp = {o[i], o[i+1]};
			list.add(temp);
		}
		return list;
	}

	protected void refreshView() {
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	protected void updateView(String id) {
	}
	
	
	protected List<AbilityPanel> getListOfPanels() {
		return listOfPanels;
	}
	
	
	protected void removeAllPanels() {
		for (JPanel panel : listOfPanels) {
			mainPanel.remove(panel);
		}
		listOfPanels.clear();
	}
	
	private void placeInMap(Map<String, String> map, String key, String value) throws ValueNotDefinedException {
		if (value.isEmpty()) {
			throw new ValueNotDefinedException(key);
		} else {
			map.put(key, value);
		}
	}
	
	protected void updateData() throws ValueNotDefinedException {
		if (!currentUniqueID.isEmpty() && !imageFilePath.isEmpty()) {
			Map<String, String> map = new HashMap<String, String>();
			placeInMap(map, "name", nameField.getText());
			if (hasSpecificType()) {
				placeInMap(map, myLanguage.getString("ObjectType"), typeSelector.getSelectedItem().toString());
			}
			map.put(myLanguage.getString("UniqueID"), currentUniqueID);
			map.put(myLanguage.getString("ImagePath"), imageFilePath);
			
			for (AbilityPanel panel : listOfPanels) {
				String ability = panel.myAbility;
				String abilityDefinition = panel.convertToString();
				map.put(ability, abilityDefinition);
			}
			String abilities = GAUtilMethods.joinString(listOfDefinedAbilities, DELIMITER);
			map.put(myLanguage.getString("Abilities"), abilities);
			myController.updateDefinition(myType, currentUniqueID, map);
		}
	}
	
}
