package parse;

import globalforces.FGravity;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jboxGlue.Fixed;
import jboxGlue.Mass;
import jboxGlue.Muscle;
import jboxGlue.Spring;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.JGObject;
import jgame.platform.JGEngine;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParserObjects extends Parser {
    private Map<String, Mass> myMassMap = new HashMap<String, Mass>();
    private List<Mass> myAllMasses = new ArrayList<Mass>();
    private List<Spring> mySpringList = new ArrayList<Spring>();
    private int myAssemblyID = 0;
    private double[] myCMLocation = new double[2];

    public ParserObjects (File file, Queue<String> tags) {
        super(file, tags);
        myAssemblyID ++;
    }
    
    public List<Mass> getAllMasses() {
        return myAllMasses;
    }

    public Map<String, Mass> getMassMap() {
        return myMassMap;
    }

    public List<Spring> getSpringList() {
        return mySpringList;
    }

    public double[] getCMLocation() {
        return myCMLocation;
    }

    private void doParseSpringMuscle(Node currNode, String type) throws NoSuchMethodException,
    InstantiationException, IllegalAccessException,
    InvocationTargetException {
        NamedNodeMap attributes = currNode.getAttributes();
        String mass1 = attributes.getNamedItem("a").getNodeValue();
        String mass2 = attributes.getNamedItem("b").getNodeValue();
        double kconstant;
        if (attributes.getNamedItem("constant") != null) {
            kconstant = Double.parseDouble(attributes.getNamedItem("constant").getNodeValue());                                                                 
        } else {
            kconstant = 1;
        }
        Mass[] massList = {myMassMap.get(mass1), myMassMap.get(mass2)};
        double restLength;
        if (attributes.getNamedItem("restlength") != null) {
            restLength = Double.parseDouble(attributes.getNamedItem("restlength").getNodeValue());                                                                      
        } else {
            double t1 = Math.pow((massList[0].x - massList[1].x), 2);
            double t2 = Math.pow((massList[0].y - massList[1].y), 2);
            restLength = Math.sqrt(t1+t2);
        }
        
        if (type.equals("muscle")) {
            double amplitude = Double.parseDouble(attributes.getNamedItem("amplitude").getNodeValue());
            Spring newMuscle = new Muscle("1", 4, JGColor.green, massList, restLength,kconstant, amplitude);
            mySpringList.add(newMuscle);
        } else if (type.equals("spring")){
            Spring newSpring = new Spring("1", 4, JGColor.green, massList, restLength,kconstant);
            mySpringList.add(newSpring);
        }
        
    }

    private List<Mass> doParseMassFixed (Node currNode, String massOrFixed) throws NoSuchMethodException, InstantiationException,
    IllegalAccessException, InvocationTargetException {
        Mass newMass;
        NamedNodeMap attributes = currNode.getAttributes();
        String name = attributes.getNamedItem("id").getNodeValue();
        double x = Double.parseDouble(attributes.getNamedItem("x").getNodeValue());
        double y = Double.parseDouble(attributes.getNamedItem("y").getNodeValue());
        boolean hasSpeed = false;
        boolean hasMass = false;
        float vx = 0;
        float vy = 0;
        float mass = 0;
        if (attributes.getNamedItem("vx")!=null && attributes.getNamedItem("vy")!=null) {
            hasSpeed = true;
            vx = Float.parseFloat(attributes.getNamedItem("vx").getNodeValue());
            vy = Float.parseFloat(attributes.getNamedItem("vy").getNodeValue());
        }
        if (attributes.getNamedItem("mass")!=null) {
            hasMass = true;
            mass = Float.parseFloat(attributes.getNamedItem("mass").getNodeValue());
        }

        if (hasSpeed && hasMass) {
            if (massOrFixed.equals("mass")) {
                newMass = new Mass(name, 1, JGColor.cyan, x, y, vx, vy, 5, mass, myAssemblyID);
            } else {
                newMass = new Fixed(name, 1, JGColor.red, x, y, vx, vy, 5, mass, myAssemblyID);
            }
        } else if (hasSpeed) {
            if (massOrFixed.equals("mass")) {
                newMass = new Mass (name, 1, JGColor.cyan, x, y, vx, vy, 5, myAssemblyID);
            } else {
                newMass = new Fixed(name, 1, JGColor.red, x, y, vx, vy, 5, myAssemblyID);
            }
        } else if (hasMass) {
            if (massOrFixed.equals("mass")) {
                newMass = new Mass(name, 1, JGColor.cyan, x, y, 5, mass, myAssemblyID);
            } else {
                newMass = new Fixed(name, 1, JGColor.red, x, y, 5, mass, myAssemblyID);
            }
        } else {
            if (massOrFixed.equals("mass")) {
                newMass = new Mass(name, 1, JGColor.cyan, x, y, 5, myAssemblyID);
            } else {
                newMass = new Fixed(name, 1, JGColor.red, x, y, 5, myAssemblyID);
            }
        }
        myMassMap.put(name, newMass);
        myAllMasses.add(newMass);
        newMass.setPos(x, y);
        //        computeCenterOfMass(myAllMasses);
        return myAllMasses;
    }

    public void computeCenterOfMass(List<Mass> massList) {
        double totalMass = 0;
        double xMassSum = 0;
        double yMassSum = 0;
        for (Mass thisMass : massList) {
            totalMass += thisMass.getMass();
            xMassSum += (thisMass.getMass()*thisMass.x);
            yMassSum += (thisMass.getMass()*thisMass.y);
        }
        myCMLocation[0] = xMassSum / totalMass;
        myCMLocation[1] = yMassSum / totalMass;	
    }


    private void doParseGeneral(Node currNode, String type) throws NoSuchMethodException, InstantiationException,
    IllegalAccessException, InvocationTargetException {
        if (type == "mass") { doParseMassFixed(currNode, "mass");}
        else if (type == "fixed") { doParseMassFixed(currNode, "fixed");}
        else if (type == "spring") { doParseSpringMuscle(currNode, "spring");}
        else if (type == "muscle") { doParseSpringMuscle(currNode, "muscle");}
    }


    @Override
    public void doParseSpecific (Node node_MainType) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if (node_MainType.getNodeName().equals("nodes")) {
            //get children: mass or fixed
            NodeList nodes_MassOrFixed = node_MainType.getChildNodes();
            //iterate through the list and create Mass or Fixed objects
            for (int k = 0; k < nodes_MassOrFixed.getLength(); k++) {
                Node currNode = nodes_MassOrFixed.item(k);
                doParseGeneral(currNode, currNode.getNodeName());
            }
        } else if (node_MainType.getNodeName().equals("links")) {
            //get Children: spring or muscle
            NodeList nodes_SpringOrMuscle = node_MainType.getChildNodes();
            //iterate through the list and create Mass or Fixed objects
            for (int k = 0; k < nodes_SpringOrMuscle.getLength(); k++) {
                Node currNode = nodes_SpringOrMuscle.item(k);
                doParseGeneral(currNode, currNode.getNodeName());
            }
        }
        computeCenterOfMass(myAllMasses);
    }
}
