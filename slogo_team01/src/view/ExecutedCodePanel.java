package view;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class ExecutedCodePanel extends JScrollPane implements MouseListener{

	//The text area that will contain the user code that has been executed
	private JList executedCode;
	private DefaultListModel codeSnippets = new DefaultListModel();
	
	public ExecutedCodePanel(){
		//this.setPreferredSize(new Dimension(250,250));
		executedCode = new JList(codeSnippets);
		executedCode.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setViewportView(executedCode);
		//getContentPane().add(executedCode);
		setFocusable(false);
		executedCode.addMouseListener(this);
	}
	
	public void addToCodeList(String code){
		//System.out.println("fire");
		codeSnippets.addElement(code);
	}

	//Will pass the specified double-clicked item in the list to the backend for parsing
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JList list = (JList)arg0.getSource();
		int index;
		if (arg0.getClickCount() == 2) 
            index = list.locationToIndex(arg0.getPoint());
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
