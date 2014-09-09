package gameauthoring.error;

import javax.swing.JOptionPane;

/**
 * General error class for game authoring
 * @author LeeYuZhou, DanZhang
 *
 */
public class GAError {


    /**
     * Creates a JOptionPane with an error message, showing the error that was
     * identified
     * 
     * @param {String} errorMessage
     */
    public static void displayError (String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Error!", JOptionPane.ERROR_MESSAGE);
    }

}