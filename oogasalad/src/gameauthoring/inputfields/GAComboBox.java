package gameauthoring.inputfields;


import javax.swing.JComboBox;
/**
 * 
 * @author LeeYuZhou
 *
 */

@SuppressWarnings({ "rawtypes", "serial" })
public class GAComboBox extends JComboBox implements GAInput{
	public static final String OPTIONS_DELIMITER = "@@";
	public static final String DEFAULT_OPTION_DELIMITER = "::";
	public GAComboBox() {
		super();
	}
	
	@Override
	public String getValue() {
		return getSelectedItem() + "";
	}

	@Override
	public void setValue(String input) {
        setSelectedItem(input);
	}

	@Override
	public void setUneditable() {
		setEditable(false);
	}

}
