package gameauthoring.inputfields;

import javax.swing.JCheckBox;

/**
 * A checkbox input field that extends JCheckbox
 * @author LeeYuZhou, DanZhang
 */
@SuppressWarnings("serial")
public class GACheckBox extends JCheckBox implements GAInput {

	@Override
	public String getValue() {
		return String.valueOf(isSelected());
	}

	@Override
	public void setValue(String input) {
		setSelected(Boolean.parseBoolean(input));
	}

	@Override
	public void setUneditable() {

	}

	@Override
	public boolean isEditable() {
		return this.isEnabled();
	}
	
	

}
