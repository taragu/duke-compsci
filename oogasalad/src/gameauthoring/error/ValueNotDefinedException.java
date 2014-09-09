package gameauthoring.error;

/**
 * An exception to check if certain user inputs are properly filled out
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class ValueNotDefinedException extends Exception {
	public ValueNotDefinedException(String keyNotDefined, String location) {
		super("The value for " + keyNotDefined + " in " + location + " is not defined!");
	}
	
	public ValueNotDefinedException(String keyNotDefined) {
		super("The value for " + keyNotDefined + " is not defined!");
	}
}
