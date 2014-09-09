package gameauthoring.inputfields;

import gameauthoring.error.GAError;
import gameauthoring.error.ValueNotDefinedException;
import gameauthoring.factories.GAInputFactory;
import gameauthoring.util.Bundles;
import gameauthoring.util.DefineValuePopup;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JPanel;


import net.miginfocom.swing.MigLayout;
/**
 * 
 * @author LeeYuZhou
 *
 */
@SuppressWarnings("serial")
public class GADecisionTree extends JPanel implements GAInput{
	public static final String DECISION_TREE_DELIMITER = "::";
	public static final String LIST_DIVIDER = "@@";
	public static final int SPEECH_INDENTATION = 200;
	Map<SpeechGroup, List<SpeechGroup>> myDecisionTree = new HashMap<SpeechGroup, List<SpeechGroup>>();
	ResourceBundle myProperties = Bundles.getBundleOfType("DecisionTree");
	SpeechGroup myRoot;
	public GADecisionTree(int size) {
		super(new MigLayout("insets 0"));
		setSize(new Dimension(size, size));
		myRoot = new SpeechGroup(0, "Start Talking", null);
		add(myRoot,"span, x " + 300*0);
	}
	
	@Override
	public String getValue() {
		StringBuilder graphRep = new StringBuilder();
		Queue<SpeechGroup> q = new LinkedList<SpeechGroup>();
		q.add(myRoot);
		int counter = 1; 
		while (!q.isEmpty()) {
			SpeechGroup sg = q.remove();
			List<SpeechGroup> children = myDecisionTree.get(sg);
			String dialogue = "";
			try {
				dialogue = sg.getText();
			} catch (ValueNotDefinedException e) {
				e.printStackTrace();
			}
			if (children.size() == 0) {
				graphRep.append(dialogue);
			} else {
				graphRep.append(dialogue + DECISION_TREE_DELIMITER);
			}
			for (int i = 0; i < children.size(); i++) {
				SpeechGroup childSG = children.get(i);
				String nodeDescriptor = childSG.getDescriptor() + ":" + counter;
				if (i != children.size() - 1) {
					nodeDescriptor += DECISION_TREE_DELIMITER;
				}
				graphRep.append(nodeDescriptor);
				counter++;
				q.add(childSG);
			}
			graphRep.append(LIST_DIVIDER);
		}

		return (graphRep.length() == 0) ? "" : graphRep.toString().substring(0, graphRep.length() - LIST_DIVIDER.length());
	}

	@Override
	public void setValue(String input) {
		removeAll();
		myDecisionTree.clear();
		myRoot = new SpeechGroup(0, "Start Talking", null);
		
		add(myRoot,"span, x " + 300*0);
		String[] valuesArray = input.split(LIST_DIVIDER);

		Queue<SpeechGroup> q = new LinkedList<SpeechGroup>();
		Map<SpeechGroup, List<String>> speechDefMap = new HashMap<SpeechGroup,List<String>>();
		String[] rootDefinition = valuesArray[0].split(DECISION_TREE_DELIMITER);
		String rootDialogue = rootDefinition[0];
		List<String> definitions = new ArrayList<String>();
		definitions.add(valuesArray[0]);
		speechDefMap.put(myRoot, definitions);
		myRoot.setText(rootDialogue);
		if (rootDefinition.length > 1) {
			q.add(myRoot);
			
		}
		while (!q.isEmpty()) {
			SpeechGroup sg = q.remove();
			List<String> speechDefinition = speechDefMap.get(sg);
			for (int i = 0; i < speechDefinition.size(); i++) {
				String[] definitionArray = speechDefinition.get(i).split(DECISION_TREE_DELIMITER);
				for (int j = 1; j < definitionArray.length; j++) {
					String[] destinationInfo = definitionArray[j].split(":");
					String response = destinationInfo[0];
					int destination = Integer.parseInt(destinationInfo[1]);
					String destinationString = valuesArray[destination];
					String[] destinationArray = destinationString.split(DECISION_TREE_DELIMITER);
					SpeechGroup child = sg.addChild(response, destinationArray[0]);
					if (speechDefMap.containsKey(child)) {
						speechDefMap.get(child).add(valuesArray[destination]);
					} else {
						List<String> destinationList = new ArrayList<String>();
						destinationList.add(valuesArray[destination]);
						speechDefMap.put(child, destinationList);
					}
					q.add(child);
				}
			}
			
		}
	}

	@Override
	public void setUneditable() {

	}

	@Override
	public boolean isEditable() {
		return true;
	}
	
	private class SpeechGroup extends JPanel {
		private GAInput myDialogue;
		private List<SpeechGroup> children;
		private int myLevel;
		private String myDescriptor;
		private SpeechGroup myParent;
		private GAInput myDescriptorLabel;
		public SpeechGroup(int level, String descriptor, SpeechGroup parent) {
			super(new MigLayout("wrap 3"));
			myLevel = level;
			myDialogue = GAInputFactory.createGAInput("LongString");
			myDescriptor = descriptor;
			myParent = parent;
			myDescriptorLabel= GAInputFactory.createGAInput("LongStringu");
			myDescriptorLabel.setValue("Response Level " + (level + 1) + ":\n" + myDescriptor);
			add((Component) myDescriptorLabel);
			add((Component) myDialogue);
			children = new ArrayList<SpeechGroup>();
			JPanel panelButtons = new JPanel(new MigLayout());
			JButton addSG = new JButton("+");
			myDecisionTree.put(this, children);
			System.out.println(myDecisionTree);
			addSG.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						DefineValuePopup createResponse = new DefineValuePopup("Please create a possible response!", "ShortString", "Response");
						if (createResponse.isDefined()) {
							String response = createResponse.get("Response");
							addChild(response);
						}
					} catch (ValueNotDefinedException e1) {
						e1.printStackTrace();
					}
				}
				
			});
			
			JButton removeSG = new JButton("-");
			removeSG.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (myParent != null) {
						removeChildren();
						refreshView();
					}
				}
				
			});
			panelButtons.add(addSG);
			panelButtons.add(removeSG);
			add(panelButtons);
		}
		private SpeechGroup addChild(String response) {
			SpeechGroup child = new SpeechGroup(myLevel + 1, response, SpeechGroup.this);
			children.add(child);
			myDecisionTree.put(SpeechGroup.this, children);
			add(child, "span, x " + SPEECH_INDENTATION);
			refreshView();
			return child;
		}
		
		private SpeechGroup addChild(String response, String dialogue) {
			SpeechGroup child = addChild(response);
			child.setText(dialogue);
			return child;
		}
		
		private String getText() throws ValueNotDefinedException {
			String dialogue = myDialogue.getValue();
			if (dialogue.trim().isEmpty()) {
				GAError.displayError("Value for " + myDescriptor + " not defined!");
				throw new ValueNotDefinedException(myDescriptor);
			}
			return dialogue;
		}
		
		public String toString() {
			return myDescriptor + ": " + myDialogue.getValue();
		}
		
		private void setText(String text) {
			myDialogue.setValue(text);
		}
		
		private String getDescriptor() {
			return myDescriptor;
		}
		
		private int getLevel() {
			return myLevel;
		}
		
		private void removeChildren() {
			if (myParent != null) {
				List<SpeechGroup> listOfSpeeches = myDecisionTree.get(myParent);
				listOfSpeeches.remove(this);
				myDecisionTree.put(myParent, listOfSpeeches);
				removeChildren(this);
				myParent.remove(this);
			}
			
		}
		
		private void removeChildren(SpeechGroup sg) {
			if (!myDecisionTree.containsKey(sg)) return;
			List<SpeechGroup> children = myDecisionTree.get(sg);
			for (SpeechGroup speech: children) {
				removeChildren(speech);
			}
			myDecisionTree.remove(sg);
			return;
		}
		
		public int hashCode() {
			char[] descriptorArray = myDescriptor.toCharArray();
			int code = 0;
			for (int i = 0; i < descriptorArray.length; i++) {
				code += 31 * descriptorArray[i];
			}
			return (myLevel+ 17) * code;
			
		}
		
		public boolean equals(Object o) {
			if (o.getClass() != this.getClass()) return false;
			SpeechGroup sg = (SpeechGroup) o;
			return (myDescriptor == sg.getDescriptor()) && (sg.getLevel() == myLevel);
			
		}
	}
	
	private void refreshView() {
		revalidate();
		repaint();
	}

}
