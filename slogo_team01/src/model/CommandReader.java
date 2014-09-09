package model;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommandReader {

	public static Map<String, String> readCommands(String fileName)
			throws IOException {

		Map<String, String> commandsMap = new HashMap<String, String>();
		FileReader commandsFile = new FileReader(fileName); 
		Properties properties = new Properties();
		
		properties.load(commandsFile);
		
		for(String key : properties.stringPropertyNames()){
			String value = properties.getProperty(key);
			commandsMap.put(key, value);
		}

		return commandsMap;
	}
}
