package parse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class Parser {
    private File myFile;
    private Queue<String> myTags = new LinkedList<String>();

    public Parser(File file, Queue<String> tags) {
        myFile = file;
        myTags = tags;
    }

    public void doParse() throws SAXException, IOException, ParserConfigurationException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(myFile);
        doc.getDocumentElement().normalize();
        NodeList nodes_Type = doc.getChildNodes();
        Node node_Type = nodes_Type.item(0);
        while (!myTags.isEmpty()) {
            for (int i = 0; i < nodes_Type.getLength(); i++) {
                node_Type = nodes_Type.item(i);
                String thisTag = myTags.poll();
                if (node_Type.getNodeName().equals(thisTag)) {
                    thisTag = myTags.poll();
                }
            }
        }
        doParseSpecific(node_Type);
    }

    public abstract void doParseSpecific (Node node_Type) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ParserConfigurationException, SAXException, IOException;
}
