package mastercode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Helper class of the Translator class: it reads the properties file and looks for items 
 *  and their types in the user-defined properties file
 *  
 * @author Tara
 *
 */
public class PropertiesFileReader {

    /**
     * the file path of the properties file
     */
    private String myFilePath;
    
    /**
     * all the information in the properties file
     */
    private Properties myProperties;
    
    /**
     * the split pattern that the user define to split multiple items from a string in the properties file
     */
    private String mySplitPattern;
    
    public PropertiesFileReader(String propertiesFilePath) {
        myFilePath = propertiesFilePath;
        FileReader commandsFile;
        try {
            commandsFile = new FileReader(myFilePath);
            Properties properties = new Properties();
            try {
                myProperties = properties;
                myProperties.load(commandsFile);
                
            }
            catch (IOException e) {
                System.out.println("PropertiesFileReader: IOException thrown!");
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e1) {
            System.out.println("PropertiesFileReader: Filepath format is wrong!");
            e1.printStackTrace();
        } 
        mySplitPattern = getSplitPattern();
        
    }
    
    /**
     * Get the split pattern the user defined in the properties file
     * @return
     */
    protected String getSplitPattern() {
        mySplitPattern = myProperties.getProperty("SplitPattern");
        return mySplitPattern;
    }
    
    /**
     * Return the list of subobjects in the properties file for the given item
     * @param item: name of the key we're looking for in the properties file
     * @return null if that item doesn't exist in the properties file
     */
    protected List<String> getSubObjects(String item) {
        if (myProperties.getProperty(item)==null) {
            return null;
        }
        String list = myProperties.getProperty(item);
        List<String> subobjects = new ArrayList<String>(Arrays.asList
                (list.split(mySplitPattern)));
        
        
        return subobjects;
    }
    
    
    
}
