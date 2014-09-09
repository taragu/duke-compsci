package gameauthoring.workspace;

import gameauthoring.util.Bundles;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * A general manager class that defines object manager, event manager
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class Manager extends JFrame {

    protected String language;
    protected JTabbedPane tabbedPane = new JTabbedPane();
    protected ResourceBundle myLanguage;
    protected int width;
    protected int height;

    /**
     * Constructor for the TabContainer, which is the main view where slogo is run from. It contains
     * tabs as well as
     * menu options for the user to customize their slogo experience
     * 
     * @param {String} title
     * @param {String} language
     * @param {int} width
     * @param {height} height
     */
    public Manager (String title, String language, int width, int height) {
        super(title);
        this.setLanguage(language);
        myLanguage = Bundles.getLanguageBundle(language);
        initMenu();
		JScrollPane mainScroll = new JScrollPane(tabbedPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(mainScroll);
        this.width = width;
        this.height = height;
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(new Dimension(width, height));

    }

    /**
     * Sets the language of the Manager
     */
    public void setLanguage (String language) {
        this.language = language;
        myLanguage = Bundles.getLanguageBundle(language);
    }

    public void addTab (JPanel tab, String tabTitle) {
        tabbedPane.addTab(null, tab);
        int pos = tabbedPane.indexOfComponent(tab);

        JPanel panelTab = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelTab.setOpaque(false);
        JLabel lblTitle = new JLabel(tabTitle);
        panelTab.add(lblTitle);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 3, 5));
        tabbedPane.setTabComponentAt(pos, panelTab);
        tabbedPane.setSelectedComponent(tab);
    }

    protected void initMenu () {
    }

    protected JMenuItem makeMenuItem (String itemTitle, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(itemTitle);
        menuItem.addActionListener(listener);
        return menuItem;
    }
}
