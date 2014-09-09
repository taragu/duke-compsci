package parse;


import globalforces.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.platform.JGEngine;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParserEnvironment extends Parser{
    private Node myNode;
    private HashMap<String, GlobalForce> myGlobalForceMap = new HashMap<String, GlobalForce>(); 

    public ParserEnvironment (File file, Queue<String> tags) {
        super(file, tags);
    }

    public HashMap<String, GlobalForce> doParseAndCreate() throws ParserConfigurationException, SAXException, IOException {
//        if (myNode.getNodeName().equals("environment")) {
            NodeList nodes_MainType = myNode.getChildNodes();
            for (int j = 0; j < nodes_MainType.getLength(); j++) {
                Node currNode = nodes_MainType.item(j);
                myGlobalForceMap.put(currNode.getNodeName(), doParseGeneral(currNode, currNode.getNodeName()));
            }
//        }
        WorldManager.myGlobalForceMapList.add(myGlobalForceMap);
        return myGlobalForceMap;
    }

    private GlobalForce doParseGeneral(Node currNode, String type) {
        NamedNodeMap attributes = currNode.getAttributes();
        if (type.equals("gravity")) { return doParseGravity(attributes);}
        else if (type.equals("viscosity")) { return doParseViscosity(attributes);}
        else if (type.equals("centermass")) { return doParseCM(attributes);}
        else if (type.equals("wall")) { return doParseWall(attributes);}
        else {  return null; }
    }

    private GlobalForce doParseWall (NamedNodeMap attributes) {
        double exponent = Double.parseDouble(attributes.getNamedItem("exponent").getNodeValue());
        double magnitude = Double.parseDouble(attributes.getNamedItem("magnitude").getNodeValue());
        int id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());
        GlobalForce wallForce;

        if (id == 1) {
            wallForce = new FWall1(magnitude, exponent, id);
            myGlobalForceMap.put("wall" + id, wallForce);
        } else if (id == 2) {
            wallForce = new FWall2(magnitude, exponent, id);
            myGlobalForceMap.put("wall" + id, wallForce);
        } else if (id == 3) {
            wallForce = new FWall3(magnitude, exponent, id);
            myGlobalForceMap.put("wall" + id, wallForce);
        } else {
            wallForce = new FWall4(magnitude, exponent, id);
            myGlobalForceMap.put("wall" + id, wallForce);
        }

        return wallForce;
    }

    private GlobalForce doParseCM (NamedNodeMap attributes) {
        double exponent = Double.parseDouble(attributes.getNamedItem("exponent").getNodeValue());
        double cmconstant = Double.parseDouble(attributes.getNamedItem("magnitude").getNodeValue());
        FCMass cmass = new FCMass(cmconstant, exponent);
        myGlobalForceMap.put("centermass", cmass);
        return cmass;
    }

    private GlobalForce doParseViscosity (NamedNodeMap attributes) {
        double vconstant = Double.parseDouble(attributes.getNamedItem("magnitude").getNodeValue());
        FViscosity viscosity = new FViscosity(vconstant);
        myGlobalForceMap.put("viscosity", viscosity);
        return viscosity;
    }

    private GlobalForce doParseGravity (NamedNodeMap attributes) {
        double angle = (Double.parseDouble(attributes.getNamedItem("direction").getNodeValue()));
        double gconstant = Double.parseDouble(attributes.getNamedItem("magnitude").getNodeValue());
        GlobalForce gravity = new FGravity(gconstant, angle);
        myGlobalForceMap.put("gravity", gravity);
        return gravity;
    }

    @Override
    public void doParseSpecific (Node node_Type)
            throws NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException, ParserConfigurationException, SAXException, IOException {
        myNode = node_Type;
        doParseAndCreate();

    }
}
