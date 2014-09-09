package gameauthoring.util;

import gameauthoring.error.GAError;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JEditorPane;


@SuppressWarnings("serial")
public class HTMLViewer extends JEditorPane {
	
	private BufferedReader in;

	/**
     * Creates a popup that displays a HTML formatted help page that 
     * includes instructions to slogo. The parameter filePath takes is 
     * a String that is the filePath of the help page to be displayed.
     * 
     * @param filePath
     */
	public HTMLViewer(String filePath) {
		setEditable(false);
		StringBuilder sb = new StringBuilder();
		setContentType("text/html");
		setPreferredSize(new Dimension(800,600));
		try {
			in = new BufferedReader(new FileReader(filePath));
			String str;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
			setText(sb.toString());
			setSelectionStart(0);
			setSelectionEnd(0);
		} catch (IOException e) {
			GAError.displayError("Unable to load HTML file!");
			setText("<html>Could not load</html>");
		} 
	}
}
