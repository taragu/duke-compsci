package view.menuComponents;

import java.awt.Color;

public enum ColorMenuComponent {
	Gray(Color.GRAY),
	Red(Color.RED),
	Black(Color.BLACK),
	Green(Color.GREEN),
	Yellow(Color.YELLOW);
	
	private final Color myColor;
	ColorMenuComponent(Color color){
		myColor = color;
	}
	
	public Color getColor(){
		return myColor;
	}
}
