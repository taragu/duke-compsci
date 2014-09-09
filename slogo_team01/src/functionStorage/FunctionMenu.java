package functionStorage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class FunctionMenu extends JMenu{

	public FunctionMenu(String label){
		super(label);
		
		JMenuItem load = new JMenuItem("Load");
		load.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoadFunctions(FunctionMenu.this);	
			}
		});
		
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new SaveFunctions(FunctionMenu.this);	
			}
		});
		
		this.add(load);
		this.add(save);
	}
}
