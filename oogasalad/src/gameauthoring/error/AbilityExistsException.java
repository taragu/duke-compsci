package gameauthoring.error;

/**
 * Exception that checks if an ability already exists or not, specific to
 * game object properties
 * @author LeeYuZhou, DanZhang
 *
 */
@SuppressWarnings("serial")
public class AbilityExistsException extends Exception {
	public AbilityExistsException() {
		super("Ability Exists!");
	}
	public String toString() {
		return this.getMessage();
	}
}
