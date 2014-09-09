package gameauthoring.inputfields;

import java.awt.Component;

import javax.swing.JScrollPane;

/**
 * A scrolling pane that extends JScrollPane and uses the 
 * GAInput interface
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class GAScrollPane extends JScrollPane implements GAInput {
	GAInput myInput; 
	public GAScrollPane(GAInput inputPanel) {
		super((Component) inputPanel);
		myInput = inputPanel;
	}

	@Override
	public String getValue() {
		return myInput.getValue();
	}

	@Override
	public void setValue(String input) {
		myInput.setValue(input);
	}

	@Override
	public void setUneditable() {
		myInput.setUneditable();
	}

	@Override
	public boolean isEditable() {
		return true;
	}
}
