package gameplayer;

import gameengine.controller.HUDController;
import gameengine.gamedata.GameMap;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


@SuppressWarnings("serial")
public class GamePlayer extends JFrame {
    protected String language;
    protected ResourceBundle myResources;
    protected JFileChooser fileChooser;
    protected JMenuBar menuBar;
    protected ActionPanel actionPanel;
    protected PlayerEngine playerEngine;
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    protected List<GameMap> gameMaps;
    private int myWidth;
    private int myHeight;
    private HUDController myController;
   

    public GamePlayer (String title, String language, int width, int height) {
        super(title);
        myWidth = width;
    	myHeight = height;
        this.setLanguage(language);
        this.setLayout(new BorderLayout());
        initMenu();
        initActionPanel(width/2, height-50);
        setVisible(true);
        setSize(new Dimension(width, height));
    }

    private void initController() {
    	myController = new HUDController(actionPanel);
	}

	private void initActionPanel (int width, int height) {
        actionPanel = new ActionPanel(width, height);
        this.add(actionPanel, BorderLayout.EAST);
        actionPanel.updateNotificationPanel("To begin a game, go to the menubar and choose a game cartridge.");
        new GameObjectDisplayManager(playerEngine);
    }

    private void initEngine (int width, int height) {
        playerEngine = new PlayerEngine(width, height, myController);
        this.add(playerEngine, BorderLayout.WEST);
		this.validate();
		this.repaint();		
    }

    public void setLanguage (String language) {
        this.language = language;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    }

    private void initMenu () {
        menuBar = new JMenuBar();
        JMenu file = new JMenu(myResources.getString("File"));
        JMenuItem openFile = makeMenuItem(myResources.getString("OpenFile"), new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                fileChooser = new JFileChooser();
                File currentDirectory;
                try {
                    currentDirectory = new File(new File(".").getCanonicalPath());
                    fileChooser.setCurrentDirectory(currentDirectory);
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }

                int returnVal = fileChooser.showOpenDialog(menuBar);
                String filePath = "";
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.print(fileChooser.getSelectedFile().getName());
                    File fileChosen = fileChooser.getSelectedFile();
                    filePath = fileChosen.getAbsolutePath();
                }
                
                initController();
                initEngine(myWidth/2, myHeight);
                playerEngine.initEngine(filePath);
                playerEngine.initGame();
                playerEngine.start(); 
            }
        });
        
        file.add(openFile);
        menuBar.add(file);
        setJMenuBar(menuBar);
    }

    protected JMenuItem makeMenuItem (String itemTitle, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(itemTitle);
        menuItem.addActionListener(listener);
        return menuItem;
    }

}