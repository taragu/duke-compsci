package gameauthoring.workspace;

import gameauthoring.util.Bundles;

import javax.swing.*;

import java.awt.*;
import java.util.ResourceBundle;


/**
 * Component to be used as tabComponent;
 * Contains a JLabel to show the text and
 * a JButton to close the tab it belongs to
 * 
 * @author LeeYuZhou, DanZhang
 */
@SuppressWarnings("serial")
public class Tab extends JPanel {
	public ResourceBundle myLanguage;
	protected String myDefaultLanguage;
	protected JPanel mainPanel;
	protected JScrollPane scrollPanel;

	/**
     * Constructor to create a Tab within a TabContainer.
     * 
     * @param {String} language
     */
    public Tab (String language) {
        super(new BorderLayout());
        myDefaultLanguage = language;
        mainPanel = new JPanel();
//        scrollPanel =
//                new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        scrollPanel.setMinimumSize(new Dimension(getWidth(), getHeight()));
        myLanguage = Bundles.getLanguageBundle(myDefaultLanguage);
        add(mainPanel);
    }

    protected void setPanelLayout (LayoutManager layout) {
        mainPanel.setLayout(layout);
    }
    

    protected void setPanelSize (int width, int height) {
        scrollPanel.setSize(width, height);
    }
    
    protected void refreshView() {
    	mainPanel.revalidate();
    	mainPanel.repaint();
//    	scrollPanel.revalidate();
//    	scrollPanel.repaint();
    }

}
