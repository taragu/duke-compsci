package model;

import java.lang.reflect.Field;

public class CommandFinder {

	/**
	 * Benson to Tara: Do you think this class is necessary? I implemented the
	 * this class to unclutter NodeFactory.
	 */

	/*
	 * Field needs to be public to use Reflection
	 */

	public final String ENGLISH_LIST = "./Languages/English.Properties";
	public final String CHINESE_LIST = "./Languages/Chinese.Properties";
	public final String FRENCH_LIST = "./Languages/French.Properties";
	public final String GERMAN_LIST = "./Languages/German.Properties";
	public final String ITALIAN_LIST = "./Languages/Italian.Properties";
	public final String PORTUGUESE_LIST = "./Languages/Portuguese.Properties";
	public final String SPANISH_LIST = "./Languages/Spanish.Properties";

	public static String aliasLookup(String language)
			throws ClassNotFoundException, IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException {

		String myLanguage = language.toUpperCase() + "_LIST";
		String aliasDirectory = null;

		Class<?> Languages = Class.forName("model.CommandFinder");

		Field[] fields = Languages.getDeclaredFields();
		for (Field f : fields) {
			if (myLanguage.equals(f.getName())) {

				Class aClass = CommandFinder.class;
				Field field = aClass.getField(myLanguage);

				CommandFinder objectInstance = new CommandFinder();
				aliasDirectory = (String) field.get(objectInstance);
				return aliasDirectory;

			}

		}
		return aliasDirectory;

	}


}
