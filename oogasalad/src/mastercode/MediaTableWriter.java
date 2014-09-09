package mastercode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Write information to JGame's media table
 * @author Trey
 *
 */
public class MediaTableWriter {

    /*
     * graphics name of the image
     */
    private String myName;
    
    /*
     * collision id
     */
    private String myCID;
  
    /*
     * file path of image
     */
    private String myPathOfImage;
    
    /*
     * file path of media.tbl
     */
    private String myPathToWrite;
    
    /**
     * 
     * @param name
     * @param symbol FOR MAP TILES ONLY; PUT IN NULL IF IT'S NOT A MAP TILE
     * @param cid 
     * @param pathOfImage
     * @param pathToWrite
     */
    public MediaTableWriter(String name, String symbol, int cid, String pathOfImage, String pathToWrite){
        myName = name;
        myPathToWrite = pathToWrite;
        assignValue(cid);
        myPathOfImage = pathOfImage;
        myPathToWrite = pathToWrite;
    }
    
    /**
     * write to media.tbl file 
     * 
     */
    protected void write () {
        BufferedReader br = null;
        try {
        	System.out.println("MEDIA TABLE WRITER PRINTING myPathToWrite =  " + myPathToWrite);
            File file = new File(myPathToWrite);
            if (!file.exists()) {
                file.createNewFile(); // if file doesn't already exist, create it
            }
            String sCurrentLine;
            br = new BufferedReader(new FileReader(myPathToWrite)); // examine file
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.startsWith(myName)) { 
                    return; // if the media table entry already
                                                             // exists, don't write it in
                }
            }
            try {
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); // otherwise,
                // allow it to append onto the end
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(myName + "\t\t" + "-" + "\t\t" + myCID + "\t\t" + myPathOfImage + "\t\t-" +
                             System.getProperty("line.separator")); // media table format

                bw.flush();
                fw.flush();
                bw.close();
                br.close();
                fw.close();
                
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null) br.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return;
    }

    /*
     * assign CIDs that will be written to the media table
     */
    private void assignValue (int cid) {
        myCID = Integer.toString(cid);
        if (cid==0) {
            myCID = "-";
        }
    }
}
