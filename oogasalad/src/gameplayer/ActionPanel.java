package gameplayer;

import gameengine.InventoryItem;
import gameengine.controller.HUDController;
import gameengine.gamedata.PlayerCharacterData;
import gameengine.party.PartyObject;
import gameengine.party.PlayerStats;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

/**
 * Actual panel for the heads up display
 * 
 * @author Ashley
 * 
 */


@SuppressWarnings("serial")
public class ActionPanel extends JPanel implements ActionListener {

	protected int WIDTH;
	protected int HEIGHT;
	private JTabbedPane tabs;
	private JPanel notificationPanel = new JPanel();
	private JPanel inventoryPanel = new JPanel();
	private JPanel statPanel = new JPanel();
	private JPanel questPanel = new JPanel();
	private JPanel partyPanel = new JPanel();
	private JPanel notificationAlertPanel = new JPanel();
	private JPanel notificationUserInputPanel = new JPanel();
	private JPanel partyItemsPanel = new JPanel();
	private JPanel partyMembersPanel = new JPanel();
	private HUDController myController;


	public ActionPanel(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		tabs = new JTabbedPane();

		notificationPanel.setLayout(new BorderLayout());
		notificationPanel.add(notificationAlertPanel, BorderLayout.NORTH);
		notificationUserInputPanel.setLayout(new BoxLayout(notificationUserInputPanel, BoxLayout.Y_AXIS));
		notificationPanel.add(notificationUserInputPanel, BorderLayout.SOUTH);
		tabs.addTab("Notifications", notificationPanel);

		tabs.addTab("Quests", questPanel);
		questPanel.add(partyItemsPanel, BorderLayout.NORTH);
		questPanel.add(partyMembersPanel, BorderLayout.SOUTH);

		tabs.addTab("Inventory", inventoryPanel);
		tabs.addTab("Stats", statPanel);


		tabs.addTab("Party", partyPanel);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(tabs);

	}

	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	public void updateNotificationPanel(String text) {
		notificationAlertPanel.removeAll();
		JTextArea alert = new JTextArea(10, 50);
		alert.setEditable(false);
		alert.setLineWrap(true);
		alert.setText(text);
		System.out.println("WHAT THE FUCK " + alert.getText());
		notificationAlertPanel.add(alert);
		tabs.setSelectedComponent(notificationPanel);

		validate();
		repaint();
	}

	public void updateNotificationPanelListed(List<String> stringList) {
		notificationAlertPanel.removeAll();
		notificationAlertPanel.setLayout(new BoxLayout(notificationAlertPanel, BoxLayout.Y_AXIS));
		for(int i=0; i<stringList.size(); i++) {
			JLabel alert = new JLabel();
			alert.setText(stringList.get(i));
			System.out.println("ACTION PANEL: " + stringList.get(i));
			notificationAlertPanel.add(alert);
			tabs.setSelectedComponent(notificationPanel);
		}

		validate();
		repaint();
	}

	public void updateNotificationPanel(List<String> actions){
		notificationUserInputPanel.removeAll();
		for (String action : actions){
			JButton actionButton = new JButton(action);
			notificationUserInputPanel.add(actionButton);
			actionButton.addActionListener(this);
		}
		tabs.setSelectedComponent(notificationPanel);

		revalidate();
		paintImmediately(notificationPanel.getBounds());
	}

	public void updateInventoryPanel(List<InventoryItem> inventoryList){
		inventoryPanel.removeAll();
		inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
		for (InventoryItem item : inventoryList){
			JButton itemButton = new JButton(item.getName());
			inventoryPanel.add(itemButton);
			if (!item.getType().equals("Quest Item")){
				itemButton.addActionListener(this);
			}
		}
	}

	public void updatePartyPanel(PartyObject party){
		
		partyPanel.removeAll();
		partyPanel.setLayout(new FlowLayout());

		for (PlayerCharacterData character : party.getPlayerCharacters()){
			JPanel partyMemberPanel = new JPanel();
			partyMemberPanel.setLayout(new BoxLayout(partyMemberPanel, BoxLayout.Y_AXIS));
			JLabel playerName = new JLabel();
			playerName.setText(character.getName());
			partyMemberPanel.add(playerName);

			PlayerStats playerStats = party.getAllPlayerStats().get(character.getName());
			if (playerStats != null){
				for (String key : playerStats.getStats().keySet()){
					JLabel stats = new JLabel(key + ": " + playerStats.getStats().get(key));
					partyMemberPanel.add(stats);
				}
			}
			partyPanel.add(partyMemberPanel);

		}
		tabs.setSelectedComponent(partyPanel);

		validate();
		repaint();
	}

	public void updateStatPanel(PlayerStats playerStats){
		statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.Y_AXIS));
		statPanel.removeAll();
		if (playerStats != null) {
			for (String key : playerStats.getStats().keySet()){
				JLabel stats = new JLabel(key + ": " + playerStats.getStats().get(key));
				statPanel.add(stats);
			}
		}
		tabs.setSelectedComponent(statPanel);

		validate();
		repaint();
	}

	public void updateQuestPanel(List<String> quests){
		questPanel.setLayout(new BoxLayout(questPanel, BoxLayout.Y_AXIS));
		questPanel.removeAll();
		for (String quest : quests){
			JLabel questDescription = new JLabel(quest);
			questPanel.add(questDescription);
		}

		validate();
		repaint();
	}
	
	public void clearNotificationUserInput(){
		notificationUserInputPanel.removeAll();
	}

	public void paintComponent (Graphics g) {
		super.paintComponent(g);
	}

	public int getWidth(){
		return WIDTH;
	}

	public int getHeight(){
		return HEIGHT;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		myController.getUserInputQueue().add(arg0.getActionCommand());
	}

	public void setController(HUDController controller) {
		myController = controller;
	}
}
