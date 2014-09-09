package gameauthoring.inputfields;

import javax.swing.JTextField;

/**
 * A text field input field that extends JTextField and uses the GAInput interface
 * 
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class GATextField extends JTextField implements GAInput {

	public GATextField(int size) {
		super(size);
	}

	@Override
	public String getValue() {
		return getText().trim();
	}

	@Override
	public void setValue(String input) {
		setText(input);
	}
	public void setUneditable() {
		setEditable(false);
	}
}
