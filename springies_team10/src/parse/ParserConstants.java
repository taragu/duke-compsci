package parse;

import globalforces.FCMass;
import globalforces.FGravity;
import globalforces.FViscosity;
import globalforces.FWall1;
import globalforces.FWall2;
import globalforces.FWall3;
import globalforces.FWall4;
import globalforces.GlobalForce;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jboxGlue.WorldManager;
import jgame.JGColor;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParserConstants extends Parser{

    private Node myNode;
    private Map<String, JGColor> myColorMap = new HashMap<String, JGColor>();
    
    public ParserConstants (File file, Queue<String> tags) {
        super(file, tags);
    }

    public Map<String, JGColor> doParseAndCreate() throws ParserConfigurationException, SAXException, IOException {

            if (myNode.getNodeName().equals("colors")) {
                NodeList nodes_MainType = myNode.getChildNodes();
                for (int j = 0; j < nodes_MainType.getLength(); j++) {
                    Node currNode = nodes_MainType.item(j);
                    
                    myColorMap.put(currNode.getNodeName(), doParseGeneral(currNode));
                }
            }
            WorldManager.myColorMap = myColorMap;
        return myColorMap;
    }

    private JGColor doParseGeneral(Node currNode) {
        NamedNodeMap attribute = currNode.getAttributes();
        String color = attribute.getNamedItem("color").getNodeValue();
        String[] colorStrings = new String[3];
        colorStrings = color.split(",");
        int[] colorInts = new int[3];
        for (int i=0; i<colorInts.length; i++){
            colorInts[i] = Integer.parseInt(colorStrings[i]);
        }
        return new JGColor(colorInts[0], colorInts[1], colorInts[2]);
    }

    @Override
    public void doParseSpecific (Node node_Type)
                                                                         throws NoSuchMethodException,
                                                                         InstantiationException,
                                                                         IllegalAccessException,
                                                                         InvocationTargetException,
                                                                         ParserConfigurationException,
                                                                         SAXException, IOException {
        doParseAndCreate();
        
    }
}
