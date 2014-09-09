package gameauthoring;

import java.util.ResourceBundle;

import gameauthoring.util.Bundles;
import gameauthoring.workspace.WorkSpaceManager;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceBlue;


public class Main {
	private static ResourceBundle myLanguage = Bundles.getLanguageBundle();
	
    public static void main (String[] arg0) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run () {
                try {
                    PlasticLookAndFeel laf = new Plastic3DLookAndFeel();
                    PlasticLookAndFeel.setCurrentTheme(new ExperienceBlue());
                    UIManager.setLookAndFeel(laf);
                }
                catch (javax.swing.UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
                UIManager.put(myLanguage.getString("Theme"), Boolean.FALSE);
                String language = myLanguage.getString("Language");
                new WorkSpaceManager(language, 530, 640);
            }
        });

    }
}
