package view;

import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

public class ScrollableJList extends JScrollPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private JList myJList;
	//private DefaultListModel myListModel = new DefaultListModel();
	private int myWidth,myHeight;
	private MouseListener myMouseListener;
		
	public ScrollableJList(int width,int height,MouseListener listener){
		myWidth = width;
		myHeight = height;
		myMouseListener = listener;
		this.setPreferredSize(new Dimension(myWidth,myHeight));
		myJList = new JList();
		myJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setViewportView(myJList);
		myJList.addMouseListener(myMouseListener);
	}
	
	public ScrollableJList(MouseListener listener){
		myMouseListener = listener;
		myJList = new JList();
		myJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setViewportView(myJList);
		myJList.addMouseListener(myMouseListener);
	}
	
	public void setModel(ListModel model){
		myJList.setModel(model);
	}
}
