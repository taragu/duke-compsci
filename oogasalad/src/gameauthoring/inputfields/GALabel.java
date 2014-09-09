package gameauthoring.inputfields;

import gameauthoring.elements.ImageContainer;

/**
 * A wrapper for the ImageContainer class that implements the GAInput interface
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class GALabel extends ImageContainer implements GAInput {
	
	public GALabel(int size) {
		super(size, size);
	}

	@Override
	public String getValue() {
		return imageFilePath;
	}

	@Override
	public void setValue(String input) {
		this.addObject(input);
	}

	@Override
	public void setUneditable() {
	}

	@Override
	public boolean isEditable() {
		return false;
	}

}
