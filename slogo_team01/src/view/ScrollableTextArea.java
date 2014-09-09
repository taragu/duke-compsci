package view;

import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

//Sets up a JScrollPane surrounding a text area
public class ScrollableTextArea extends JScrollPane{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private JTextArea myTextArea;
	private int myWidth,myHeight;

	
	public ScrollableTextArea(int width,int height){
		myWidth = width;
		myHeight = height;
    	myTextArea = new JTextArea(myWidth, myHeight);
		this.setViewportView(myTextArea);
	}
	
	public ScrollableTextArea(){
    	myTextArea = new JTextArea();
		this.setViewportView(myTextArea);
	}
	
	public void setCaretPosition(int pos){
		myTextArea.setCaretPosition(pos);
	}
	
	public void append(String s){
		myTextArea.append(s);
	}
	
	public void setText(String s){
		myTextArea.setText(s);
		 firePropertyChange("command",false,true);
	}
	
	public String getText(){
		return myTextArea.getText();
	}
	
	public int getTextLength(){
		return myTextArea.getText().length();
	}
	
	public void setEditable(boolean b){
		myTextArea.setEditable(b);
	}

}
