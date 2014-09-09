package view;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

//component that takes into account the users texts - chose JPanel because this is more of a container
public class ProgrammingPanel extends GridBagPanel implements ActionListener {
	
	/**
	 * 
	 */

	private static final Integer RATIO = 12;
	
	//text field class for displaying and storing user input
	private ScrollableTextArea userTextArea;
	private ScrollableJList functions;
	private ScrollableTextArea instanceVars;
	private JButton execute;
	private ExecutedCodePanel executedCode;
	private String command;
	//private ParserTest parser;
	//private Turtle turtle;
	
//	private JPanel userInputAndButton = new JPanel();
//	private JPanel userOutput = new JPanel();
    	
	public ProgrammingPanel(){
		super();
		instanceVars = new ScrollableTextArea();
		functions = new ScrollableJList(null);
		userTextArea = new ScrollableTextArea();
		addBorderedComponent(0,0,1,1,1,1,userTextArea,"Code here!");		
		execute = new JButton("Execute!");
		addBorderedComponent(0,1,1,0,1,1,execute,"Click to run!");
		executedCode = new ExecutedCodePanel();
		addBorderedComponent(0,2,1,1,1,1,executedCode,"Previous executions:");
		addBorderedComponent(0,3,1,0,1,1,makeHyperLink("Go to basic commands page",
				"http://www.cs.duke.edu/courses/cps108/current/assign/03_slogo/commands.php",
				0,0),"Command reference:");
		addBorderedComponent(1,0,1,1,1,4,instanceVars,"Instance variables:");
		addBorderedComponent(2,0,1,1,1,4,functions,"Your functions:");
		
		revalidate();
		
		setFocusable(false);
		
		execute.addActionListener(this);
	}
	
	private static JLabel makeHyperLink(final String s, final String link, int x, int y)
	{
		final JLabel l = new JLabel(s);
		l.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent arg0)
			{
				l.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				l.setText(s);
			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				l.setCursor(new Cursor(Cursor.HAND_CURSOR));
				l.setText(String.format("<HTML><FONT color = \"#000099\"><U>%s</U></FONT></HTML>", s));
			}

			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				try
				{
					URI uri = new URI(link);
					if (Desktop.isDesktopSupported())
						Desktop.getDesktop().browse(uri);
				} catch (Exception e){}
			}
		});

		l.setBounds(x, y, s.length()*5, 20);
		l.setToolTipText(String.format("go to %s", link));
		return l;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		command = userTextArea.getText();
		executedCode.addToCodeList(command);
		userTextArea.setText("");
		
	}

	public ScrollableTextArea getTextArea(){
		return userTextArea;
	}
	
	public String getCommand(){
		return command;
	}
	
	public void addInstanceVariables(List<String> variables){
		instanceVars.setText("");
		for(String s: variables){
			instanceVars.append(s + "\n");
		}
	}
	//Called when the user clicks the execute button. Will pass the text in the text area to
	//the backend for parsing.

}
