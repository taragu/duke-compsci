import springies.Springies;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 * Creates window that can be moved, resized, and closed by the user.
 * 
 * @author Robert C. Duvall
 */
public class Main
{
    // constants
    public static final Dimension SIZE = new Dimension(800, 600);
    public static final String TITLE = "Springies!";

    /**
     * main --- where the program starts
     * 
     * @param args
     * @throws IOException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws NoSuchMethodException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     */
    public static void main (String args[]) throws ParserConfigurationException, SAXException, IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        // view of user's content
        final Springies sp = new Springies();
        // container that will work with user's OS
        JFrame frame = new JFrame(TITLE); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components
        frame.getContentPane().add(sp, BorderLayout.CENTER);
        
        // display them
        frame.pack();
        frame.setVisible(true);
        
    }

}
