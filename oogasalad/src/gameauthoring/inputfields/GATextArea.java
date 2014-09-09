package gameauthoring.inputfields;

import javax.swing.JTextArea;

/**
 * A text area input field that extends JTextArea and uses the GAInput interface
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class GATextArea extends JTextArea implements GAInput {
	
	public GATextArea(int size) {
		super(size, size);
		setLineWrap(true);
	}
	
	@Override
	public String getValue() {
		return getText().trim();
	}

	@Override
	public void setValue(String input) {
		setText(input);
	}

	@Override
	public void setUneditable() {
		setEditable(false);
	}
	

}
