package gameauthoring.workspace;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gameauthoring.elements.MapCanvas;
import gameauthoring.factories.GAInputFactory;
import gameauthoring.inputfields.GAInput;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Refers to the specific tab window that contains the Game Map.
 * @author LeeYuZhou, DanZhang
 */

@SuppressWarnings("serial")
public class MapTab extends Tab {

	private MapCanvas map;
	private String myTitle;
	private JCheckBox premiumMap;
	private GAInput myCode;
	public static final int TILE_SIZE = 25;
	public static final int DEFAULT_MAP_SIZE = 5;
	/**
	 * Constructor for a MapTab. 
	 * @param language
	 * @param title
	 * @param wsm
	 */
	public MapTab(String language, String title, int rows, int cols, Controllable controller, Activable activable, InspectorLike inspector) {
		super(language);
		map = new MapCanvas(rows, cols, TILE_SIZE, title, controller, activable, inspector);
		myTitle = title;
        //mainPanel.add(map);
		scrollPanel =
				new JScrollPane(map, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setPreferredSize(new Dimension(480, 500));
        mainPanel.add(scrollPanel);
        JPanel mapOptions = new JPanel();
        JButton saveMap = new JButton(myLanguage.getString("SaveMap"));
        myCode = GAInputFactory.createGAInput("ShortString");
        saveMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                map.saveMap(premiumMap.isSelected(), myCode.getValue());
            }

        });
        
        premiumMap = new JCheckBox("Premium Map");
        
        
        
        mapOptions.add(premiumMap);
        mapOptions.add((Component) myCode);
        mapOptions.add(saveMap, BorderLayout.SOUTH);
        add(mapOptions, BorderLayout.SOUTH);
    }
	
	public String getTitle() {
		return myTitle;
	}

	public void saveMap() {
		map.saveMap(premiumMap.isSelected(), myCode.getValue());
	}
	
	public boolean isPremium() {
		return premiumMap.isSelected();
	}
	
	public String getCode() {
		return ( myCode.getValue().trim().isEmpty() ) ? "" : myCode.getValue();
	}
	


}
