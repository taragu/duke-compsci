package gameauthoring.inputfields;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

/**
 * A slider selection tool that extends JPanel and uses the GAInput interface
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class GASlider extends JPanel implements GAInput {
	private JSlider mySlider;
	private JLabel value;
	public GASlider(int max, int ticks) {
		super(new MigLayout("insets 0"));
		mySlider = new JSlider(JSlider.HORIZONTAL, 0, max, max/2);
		mySlider.setMajorTickSpacing(max);
		mySlider.setMinorTickSpacing(ticks);
		mySlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent event)
			{
				value.setText(getValue());
			}
		});		
		value = new JLabel();
		value.setText(getValue());
		add(mySlider);
		add(value);
	}
	@Override
	public void setValue(String input) {
		mySlider.setValue(Integer.parseInt(input));
	}

	@Override
	public void setUneditable() {
	}

	@Override
	public String getValue() {
		return "" + mySlider.getValue();
	}
	@Override
	public boolean isEditable() {
		return this.isEnabled();
	}

}
