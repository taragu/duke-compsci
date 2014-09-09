package gameauthoring.workspace;

import gameauthoring.elements.EventInspector;
import gameauthoring.elements.MapTile;
import gameauthoring.error.GAError;
import gameauthoring.error.ValueNotDefinedException;

import gameauthoring.factories.GAInputFactory;
import gameauthoring.inputfields.GAInput;
import gameauthoring.inputfields.GAScrollPane;
import gameauthoring.inputfields.GATextArea;
import gameauthoring.inputfields.GATextField;
import gameauthoring.util.Bundles;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;


import net.miginfocom.swing.MigLayout;

/**
 * Event manager to control the creation of events in the game authoring environment.
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class EventManager extends Manager implements EventUpdatable {
	GATextField eventNameTextField;
	EventList<String> eventList;
	List<String> listOfEvents = new ArrayList<String>(); 
	JPanel mainPanel;
	JComboBox<String> typeSelector;
	GAScrollPane eventDescription;
	EventPanel ePanel;
	public static final String SPLITTER = ";;";
	ResourceBundle myProperties = Bundles.getEventProperties();
	ResourceBundle myPropertyPaths = Bundles.getPropertiesDirectory();
	static ResourceBundle myLanguage = Bundles.getLanguageBundle();
	Activable myActivable;
	Controllable myController;
	
	final JComboBox<String> initiatorSelector = new JComboBox<String>();
	final JComboBox<String> terminatorSelector = new JComboBox<String>();
	final JComboBox<String> rewardSelector = new JComboBox<String>();
	
	List<Component> initiatorList;
	List<Component> terminatorList;
	List<Component> rewardList;
	List<String> myLabels;
	
	Map<Component, String> myComponents;
	Map<String, String> eventData;
	/**
	 * Constructor for the Event manager, extends Manager class. 
	 * @param language
	 * @param width
	 * @param height
	 * @param activable
	 */
	public EventManager(String language, int width, int height, Activable activable) {
		super(myLanguage.getString("Event"), language, width, height);
		setLayout(new BorderLayout());
		initiatorList = new ArrayList<Component>();
		terminatorList = new ArrayList<Component>();
		rewardList = new ArrayList<Component>();
		myComponents = new HashMap<Component, String>();
		myLabels = new ArrayList<String>();
		eventData = new HashMap<String, String>();
		mainPanel = new JPanel();
		mainPanel.setLayout(new MigLayout());
		myActivable = activable;
		myController = (Controllable) myActivable; 
		//addTab(mainPanel, myLanguage.getString("EventMaker"));
		JScrollPane mainScroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(mainScroll, BorderLayout.CENTER);
		
		//create event name
		JLabel eventName = new JLabel(myLanguage.getString("EventName"));
		addComponent(eventName);
		eventNameTextField = new GATextField(10);
		addComponent(eventNameTextField);
		JButton addButton = new JButton(myLanguage.getString("SaveEvent"));

		addComponent(addButton, "wrap");
		//event description
		JLabel description = new JLabel("Event Description");
		addComponent(description);
		eventDescription = new GAScrollPane(new GATextArea(10));
		addComponent(eventDescription, "wrap");
		// event type
		JLabel eventType = new JLabel(myLanguage.getString("EventType"));
		String[] eventTypeArray = myProperties.getString("Type").split(SPLITTER);
		typeSelector = new JComboBox<String>(eventTypeArray);
		typeSelector.setSelectedIndex(-1);
		addComponent(eventType);
		addComponent(typeSelector,"wrap");
		
		typeSelector.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ePanel != null) {
					mainPanel.remove(ePanel);
					
				}
				ePanel = new EventPanel((String) typeSelector.getSelectedItem());
				addComponent(ePanel,"span");
				
				refreshView();
			}
			
		});
		
		eventList = new EventList<String>();

		JScrollPane listScroller = new JScrollPane(eventList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScroller.setPreferredSize(new Dimension(width, height/4));
		
		MouseListener ml = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String selectedItem = eventList.getSelectedValue();
				 
			}
		};
		eventList.addMouseListener(ml);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!eventAlreadyDefined(eventNameTextField.getValue())) {
					listOfEvents.add(EventManager.this.eventNameTextField.getValue());
					eventList.addEvent(EventManager.this.eventNameTextField.getValue());
					try {
						updateData();
					} catch (ValueNotDefinedException e1) {
						
						e1.printStackTrace();
					}
					eventNameTextField.setValue("");
					eventData = new HashMap<String, String>();
					myComponents.clear();
					initiatorList = new ArrayList<Component>();
					terminatorList = new ArrayList<Component>();
					rewardList = new ArrayList<Component>();
					myLabels.clear();
					mainPanel.remove(ePanel);
					refreshView();

				} else {
					 
					GAError.displayError("Event exists already!");
				}

			}

			private boolean eventAlreadyDefined(String s) {
				

				for (String event : listOfEvents) {
					if (s.equals(event))
						return true;
				}
				return false;
			}
			
		});
		
		add(listScroller, BorderLayout.SOUTH);
		setVisible(false);
		
		
	}
	
	@SuppressWarnings("hiding")
	private class EventList<String> extends JList<String> {
		
		DefaultListModel<String> myEventList = new DefaultListModel<String>() ;
		private EventList() {
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			setLayoutOrientation(JList.VERTICAL);
			setVisibleRowCount(-1);
			setModel(myEventList);
		}
		private EventList(List<String> listOfEvents) {
			this();
			for (String event: listOfEvents) {
				myEventList.addElement(event);
			}
			
			
		}
		
		private void addEvent(String e) {
			myEventList.addElement(e);
		}
		
	}
	
	private void addComponent(Component c) {
		mainPanel.add(c);
	}
	
	private void addComponent(Component c, String restriction) {
		mainPanel.add(c,restriction);
	}
	
	protected class EventPanel extends JPanel {
		List<GAInput> listOfInputs;
		String myType;


		/*
		 * 
		 * properties file (E-E=Initiator, Quest=Init,Term,Reward)
		 */

		
		JPanel initiator = new JPanel(new MigLayout("insets 0"));
		JPanel terminator = new JPanel(new MigLayout("insets 0"));
		JPanel reward = new JPanel(new MigLayout("insets 0"));
		
		public EventPanel(String type) {
			super(new MigLayout("insets 0"));
			myType = type;
			add(new JLabel(myType), "span");
			String[] attributes = myProperties.getString(myType).split(SPLITTER);
			for (String attribute : attributes) {
				determineComboBox(attribute);
			}
			listOfInputs = new ArrayList<GAInput>();
		}
		
		
		private void determineComboBox(String attribute) {
			if (attribute.equals("Initiator"))
				generateComboBox(initiatorSelector, initiatorList,initiator, "Initiator");
			if (attribute.equals("Terminator"))
				generateComboBox(terminatorSelector, terminatorList, terminator, "Terminator");
			if (attribute.equals("Reward"))
				generateComboBox(rewardSelector, rewardList, reward, "Reward");
		}

		public void generateNewPanel(String eventStage, int i,  JPanel panel, List<Component> componentList) {
			String eventProperty = myType + eventStage + i;
			String[] eventComponents = myProperties.getString(eventProperty).split(SPLITTER);
			for (int j = 0; j < eventComponents.length; j+=2) {
				String labelString = eventComponents[j+1];
				String desiredElementType = labelString.split(" ")[0];
				GAInput input = GAInputFactory.createGAInput(eventComponents[j]);
				JLabel label = new JLabel(labelString);
				panel.add(label);
				panel.add((Component) input);
				componentList.add(label);
				componentList.add((Component) input);
				myLabels.add(labelString);
				myComponents.put((Component) input, labelString);
				if (!((GAInput) input).isEditable()) {
					final EventInspector inspector = new EventInspector(myActivable, (EventUpdatable) EventManager.this, 
							desiredElementType, input);
					JButton selectFirst = new JButton(myLanguage.getString("Selector"));
					selectFirst.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							myActivable.setActiveTile(inspector);
						}
					});
					panel.add(selectFirst, "wrap");
					componentList.add(selectFirst);
				}

				refreshView();
			}
			
		}
		public void generateComboBox(final JComboBox<String> comboBox, 
				final List<Component> componentList, final JPanel panel, final String eventStage) {
			JLabel label = new JLabel(eventStage);
			String[] options = myProperties.getString(myType + eventStage).split(SPLITTER);
			comboBox.setModel(new DefaultComboBoxModel<String>(options));
			comboBox.setSelectedIndex(-1);
			comboBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Component c: componentList) {
						remove(c);
					}
					componentList.clear();
					panel.removeAll();
					if (comboBox.getSelectedIndex() < 0)
						generateNewPanel(eventStage, 0, panel, componentList);
					else {
						generateNewPanel(eventStage, comboBox.getSelectedIndex(), panel, componentList);
						try {
							placeInMap(eventData, eventStage, (String) comboBox.getSelectedItem());
						} catch (ValueNotDefinedException e1) {
							e1.printStackTrace();
						}
					}
//					generateNewPanel(eventStage, (comboBox.getSelectedIndex() < 0) ? 0 : comboBox.getSelectedIndex(), panel, componentList);
				}
			});
			add(label);
			add(comboBox, "span");
			add(panel,"span");
			
			refreshView();
		}
	}
	
	protected void refreshView() {
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	/**
	 * This uses the data gathered from a selected tile or character or item and
	 * updates the events pane view with the proper information.
	 */
	@Override
	public void updateEventView(MapTile tile, GAInput component, String type) throws ValueNotDefinedException {
		String element = myComponents.get(component);
		System.out.println("Element: " + element);
		
		if (type.equals(myLanguage.getString("Tile"))) {
			component.setValue(tile.getMapName());
			placeInMap(eventData, element + "TileID", tile.getMapTileID());
		
		}
		else {
			Map<String, String> data = myController.getDefinitionData(tile.getSurfaceID());
			component.setValue(data.get("imagepath"));
			placeInMap(eventData, element + "ObjectID", tile.getSurfaceID());
		}
		placeInMap(eventData, element + "MapID", tile.getMapID());
		placeInMap(eventData, element + "X", tile.getTileX() + "");
		placeInMap(eventData, element + "Y", tile.getTileY() + "");


	}

	protected void updateData() throws ValueNotDefinedException {
		placeInMap(eventData, myLanguage.getString("UniqueID"), myController.getNewLongID());
		placeInMap(eventData, myLanguage.getString("Name"), eventNameTextField.getValue());
		placeInMap(eventData, "description", eventDescription.getValue());
		String type = (String) typeSelector.getSelectedItem();
		placeInMap(eventData, myLanguage.getString("Type"), type);
		myController.updateDefinition(myLanguage.getString("Event"), eventData.get(myLanguage.getString("UniqueID")), eventData);
	}


	private void placeInMap(Map<String, String> map, String key, String value) throws ValueNotDefinedException {
		if (value.isEmpty()) {
			throw new ValueNotDefinedException(key);
		} else {
			map.put(key, value);
		}
	}




	
	

}
