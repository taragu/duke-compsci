package gameengine;


public class InventoryItem {

	private String myName;
	private int[] myPowers;
	private String myType;

	public InventoryItem(String name, int[] powers, String type){
		myName = name; myPowers = powers; myType = type;
	}
	
	public String getName(){ // GETTERS...
		return myName;
	}
	
	public int[] getPowers(){
		return myPowers;
	}
	
	public String getType(){
		return myType;
	}

}