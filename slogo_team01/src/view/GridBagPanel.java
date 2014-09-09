package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GridBagPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public GridBagPanel(){
		this.setLayout(new GridBagLayout());
	}
	
	
	
	   /**
     * Used to add titled and bordered components in a grid LayoutManager to this panel */
	protected void addBorderedComponent(int gridX,int gridY,double weightX,
			double weightY,int gridWidth,int gridHeight,JComponent jComponent,
			String title){
			JPanel jp = new JPanel(new BorderLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx=gridX;
			gbc.gridy=gridY;
			gbc.weightx=weightX;
			gbc.weighty=weightY;
			gbc.gridwidth=gridWidth;
			gbc.gridheight=gridHeight;
			jp.setBorder(
		            BorderFactory.createTitledBorder(
		                    BorderFactory.createEtchedBorder(
		                            EtchedBorder.RAISED, Color.GRAY
		                            , Color.DARK_GRAY), title));
			jp.add(jComponent,BorderLayout.CENTER);
			this.add(jp,gbc);
	}
}
