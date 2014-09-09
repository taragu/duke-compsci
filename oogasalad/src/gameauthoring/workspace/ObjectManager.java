package gameauthoring.workspace;

import gameauthoring.error.GAError;
import gameauthoring.error.ValueNotDefinedException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.miginfocom.swing.MigLayout;


/**
 * Object Manager is the new windows for creating different aspects of the game,
 * including Tiles, Characters, and Items. 
 * @author LeeYuZhou, DanZhang
 */

@SuppressWarnings("serial")
public class ObjectManager extends Manager implements PropertyUpdatable{

	protected String myType;
	private WorkSpaceManager wsm;
	private ObjectTab objectTab;
	private PropertiesTab propertiesTab;
	
	/**
	 * Constructor for the ObjectManager
	 * @param language
	 * @param width
	 * @param height
	 * @param workSpaceManager
	 * @param type
	 */
	public ObjectManager(String language, int width, int height, WorkSpaceManager workSpaceManager, String type) {
		super(type, language, width, height);
		this.wsm = workSpaceManager;
		this.myType = type;		
		objectTab = new ObjectTab(language, 25, (Controllable) wsm, (Activable) wsm, (PropertyUpdatable) this, type);
		propertiesTab = new PropertiesTab(language, myType, (Controllable) wsm);
		addTab(this.objectTab, type);
		addTab(propertiesTab, myLanguage.getString("Properties"));
		tabbedPane.setSelectedComponent(objectTab);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(false);
	}
	
	protected ResourceBundle getResources() {
		return myLanguage;
	}
	
	protected void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu options = new JMenu(myLanguage.getString("Options"));
		JMenuItem newTile = makeMenuItem(myLanguage.getString("NewTile"), new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final JFrame frame = new JFrame();
				final String id = wsm.getNewLongID();
				final GameObjectPanel gOP = new GameObjectPanel(language, myType, (Controllable) wsm, id);
				frame.setSize(350, 350);
				frame.setLayout(new MigLayout("insets 0", "[grow,fill]"));
				JButton addImage = new JButton(myLanguage.getString("ChooseImage"));
				
				addImage.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						JFileChooser fileChooser = new JFileChooser();
				        String currentFileDirectory = System.getProperty(myLanguage.getString("FileDirectory"));
				        fileChooser.setCurrentDirectory(new File(currentFileDirectory));
				        int returnVal = fileChooser.showSaveDialog(null);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                            gOP.addObject(filePath);
                        }
					}
				});
				frame.add(addImage, "north");
				frame.add(gOP, "span");
				frame.setVisible(true);
				JButton okButton = new JButton(myLanguage.getString("Save"));
				JButton cancelButton = new JButton(myLanguage.getString("Cancel"));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						try {
							gOP.updateData();
						} catch (ValueNotDefinedException e) {
							e.printStackTrace();
							GAError.displayError(e.getMessage());
						}
						objectTab.addGameObject(id);
						frame.dispose();
					}
				});
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						frame.dispose();
					}
				});
				frame.add(okButton, "w " + frame.getWidth()/2);
				frame.add(cancelButton, "w " + frame.getWidth()/2);
			}
		});
		options.add(newTile);
		menuBar.add(options);
		setJMenuBar(menuBar);
	}

	/**
	 * Updates the view of the properties tab, implementing the interface
	 * for the properties tab.
	 */
	@Override
	public void updatePropertiesView(String id) {
		propertiesTab.updateView(id);
	}

	@Override
	public void updatePropertiesView(List<String> listOfIDs) {
		propertiesTab.updateView(listOfIDs);
		
	}
}
