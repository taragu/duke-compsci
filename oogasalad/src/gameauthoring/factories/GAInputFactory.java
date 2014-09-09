package gameauthoring.factories;

import gameauthoring.inputfields.GACheckBox;
import gameauthoring.inputfields.GADecisionTree;
import gameauthoring.inputfields.GAImageSelector;
import gameauthoring.inputfields.GAInput;
import gameauthoring.inputfields.GAItemComboBox;
import gameauthoring.inputfields.GALabel;
import gameauthoring.inputfields.GAScrollPane;
import gameauthoring.inputfields.GASlider;
import gameauthoring.inputfields.GATextArea;
import gameauthoring.inputfields.GATextField;

/**
 * A factory that generates different input fields based on the type. 
 * @author LeeYuZhou, DanZhang
 */
public class GAInputFactory {
	public static final int SIZE = 10;
	public static final int SLIDER_LONG = 100;
	public static final int SLIDER_NORMAL = 10;
	public static final int LABEL_SIZE = 20;
	public static final int TICK_SIZE = 1;
	/**
	 * creates an input field based on the type. 
	 * @param type
	 * @return
	 */
	public static GAInput createGAInput(String type) {
		
		GAInput input = null;
		switch(type) {
			case ("boolean"):
				input = new GACheckBox();
				break;
			case ("ShortString"):
				input = new GATextField(SIZE);
				break;
			case ("int"):
				input = new GATextField(SIZE);
				break;
			case ("LongString"):
				input = new GAScrollPane(new GATextArea(SIZE));
				break;
			case ("LongStringu"):
				input = new GATextArea(SIZE);
				input.setUneditable();
				break;
			case ("ShortStringu"):
				input = new GATextField(SIZE);
				input.setUneditable();
				break;
			case ("intu"):
				input = new GATextField(SIZE);
				input.setUneditable();
				break;
			case ("Label"):
				input = new GALabel(LABEL_SIZE);
				break;
			case ("intSliderNormal"):
				input = new GASlider(SLIDER_NORMAL, TICK_SIZE);
				break;
			case ("intSliderLong"):
				input = new GASlider(SLIDER_LONG, TICK_SIZE);
				break;
			case ("DecisionTree"):
				input = new GADecisionTree(SIZE);
				break;
			case ("Image"):
				input = new GAImageSelector();
				break;
			case ("ItemCombo"):
				input = new GAItemComboBox(type);
				break;
		}
		return input;
	}
	
}
