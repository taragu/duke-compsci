package gameauthoring.util;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class Bundles extends ResourceBundle {
	
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    private static String LANGUAGE = "English";
	private static final String EVENT = "Event";
	private static final String PATHS = "PropertiesDirectory";
	private static final String GAME_OPTIONS_PROPERTIES = "GameOptions";


	@Override
	protected Object handleGetObject(String key) {
		return null;
	}

	@Override
	public Enumeration<String> getKeys() {
		return null;
	}
	
	public static ResourceBundle getLanguageBundle() {		
		return ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+LANGUAGE);
	}
	
	public static ResourceBundle getGameOption() {
		return ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + GAME_OPTIONS_PROPERTIES);
	}
	
	public static ResourceBundle getLanguageBundle(String language) {
		LANGUAGE = language;
		return ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+language);
	}
	
	public static ResourceBundle getEventProperties() {
		return ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+EVENT);
	}
	
	public static ResourceBundle getPropertiesDirectory() {
		return ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+PATHS);
	}
	
	public static ResourceBundle getBundleOfType(String type) {
		return ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+type);
	}
	

}
