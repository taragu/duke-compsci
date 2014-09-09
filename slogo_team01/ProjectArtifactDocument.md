#Project Artifact Document

##Introduction

##Overview:

###Frontend Modules: Talal, Viju, and Chad

####SLogo
This is where the main method of the program is kept. New workspaces are created from this method (workspace panel) as well as other key features. The menu bar is also created here.

####turtle_graphics package
This module contains classes that load an image to use for the turtle in the GUI. It has a TurtleImage class which implements the ITurtle interface and uses Graphics2D to print all turtle images and their associated trail line. TurtleDisplayPanel holds a list of all the turtles to show in the GUI as instances of the TurtleImage class. The TurtleImage class is converted to Turtle class or viceversa during communication within the controller.

####TurtleDisplayPanel and ActionDisplayPanel
The TurtleDisplayPanel sets up the environment for displaying the turles and their associated trail lines. It also hold a list of TurtleImages. This list is used by the controller (in the WorkspacePanel) while passing information to the model and also modified by the controller after processing information from the model. The ActionDisplayPanel holds an instance of the TurtleDisplayPanel along with buttons to give user ability to make changes in the display of the turtles. The ActionDisplayPanel also has a ScrollableTextArea which shows the current position and heading of each turtle. The ActionDisplayPanel is the second main component of the WorkspacePanel (the first being the ProgrammingPanel) and it allows for various display features to be implemented.


####FunctionStorage
This module deals with loading and saving the user-defined functions. The functionMenu class is called by the menu-builder, and this just serves to “package” the load and saving capabilities of this module. It hides the other classes in this module when it’s called. 

####WorkspacePanel
This class serves as a container for some of the pieces of the GUI. It contains a ProgrammingPanel and an ActionDisplayPanel. The ProgrammingPanel and the ActionDisplayPanel contain many things for the user, such as the buttons and the visual display– it is in a sense their “workspace”. The ProgrammingPanel interacts through the controller with the backend to pass commands while the ActionDisplayPanel is primarily a front-end component. The panels were divided in this manner to keep the separate functionalities separate, to keep the code more readable.

###Backend Modules: Benson and Tara


####Model Package

#####Model Class 
– Highest level abstraction in the backend and directly communicates with controller module.
- processCommands(): Is a method that is called every time a command is executed in the frontend. This method creates an instance of class Parser, which is the source of reading from the commands and updating the turtle.

#####Parser Class 
– Parses String commands which are stored in a queue. This class creates and traverses an Abstract Syntax Tree. During the traversal, nodes are executed and turtle properties are updated. 
- createTree(): Creates nodes via instantiating class NodeFactory.
- traverseTree(): Traverses tree. All commands are executed and turtle properties updated in real-time alongside traversal.

####Nodes Package

#####NodeFactory Class
– Implements reflection to create nodes based on command. 
- CommandFinder(): Uses reflection to implement differen languages.
- CommandReader(): Creates map to reference node classes.

#####AbstractNode Abstract Class
– Provides a template for the fundamental nodes that are to be implemented.
- AbstractNode(): Constructor takes in a turtle argument. This updates the properties of each turtle instance.
- setLeftNode()/setRightNode()/setParent()/addChild(): Used in creating the tree. Depending on the command, adds child nodes and parent reference nodes.
- getLeftNode()/getRightNode()/getParent()/getChildren(): Used in traversing the tree. Provides relevant data (conditional, value, etc) for traversal.
-	has/allows methods: Returns boolean for tree traversal. 
- evaluate(): Returns value from executed command.

#####Node Classes
– Each node inherits from AbstractNode or LeafNode and represents a specific command.

####Turtle Package

#####Turtle Class
– Contains properties that are created during tree traversal and are fetched by controller.

####Controller Package 

#####AbstractController
– Provides a template that allows views and model to interact. An instance of a model and a list of views are instantiated per controller.

- setModelProperty(): Sends command, language, and list of ITurtles to the model and thus the parser.

#####ModelController 
- Extends AbstractController to provide a way of interfacing the turtle properties from the backend to the frontend and vice versa.
- convertImagetoITurtle(): Method that converts class TurtleImage to class ITurtle. TurtleImage serves essentially as the frontend turtle. ITurtle is an interface implemented by class Turtle. ITurtle provides a functional approach for encapsulation; class Turtle doesn’t need to be referenced by class ModelController.
- passToModel(): Updates the backend end with commands via setModelProperty() while updating the frontend turtle.


##User Interface Design: Talal, Viju, and Chad

The user will interact with our program through several key components including the menu bar, the command input piece, and the buttons. The menu bar houses the parts dealing with the overall functionality of the project including choosing the background color, creating a new workspace, and saving user-defined functions. And once a user creates new workspaces, they can switch between them using the tab menu that is located under the menu bar. 

The buttons include moving the turtle, choosing a pen color, turning the pen on and off, and saving the current preferences. The command input piece contains a text input component where the user enters what commands they want the turtle to do, and when they are done with this, they can press the “Execute” button to see their commands in action. The user also has a list of the current user-defined variables and functions available for them to view as well. These are not yet clickable, but because of the object that they are housed in, the ScrollableTextArea, that functionality will be able to be added rather easily. 

There is also the panel that displays the turtle to the user, so they can see their commands in action. The user is able to choose what image to put here when the program is started and whenever a new workspace is created through a dialog. There are various other dialog windows that are popped by this project. These include when the user attempts to load or save their currently defined functions and when the user tries to load or save their preferences.

Unfortunately, we do not have the front-end reporting any of the errors that the user might do when entering commands, but this functionality is present on the back-end. Because we have a higher-level container for the panels, we could simply pop a dialog through here when the controller deems it necessary, as it is the connection between the front and back ends. Thus, the framework of our project makes adding these features easy to do, speaking to the extensibility of our code. 

![](https://raw.githubusercontent.com/duke-compsci308-spring2014/slogo_team01/master/1.PNG?token=6359679__eyJzY29wZSI6IlJhd0Jsb2I6ZHVrZS1jb21wc2NpMzA4LXNwcmluZzIwMTQvc2xvZ29fdGVhbTAxL21hc3Rlci8xLlBORyIsImV4cGlyZXMiOjEzOTYwNjczNDF9--7d73e301dd549e6e7d2b7c9ab809a48ecf742978)

The snapshot above shows how a new image selection dialog pops up after clicking the "Create Turtle" button

![] (https://raw.githubusercontent.com/duke-compsci308-spring2014/slogo_team01/master/2.PNG?token=6359679__eyJzY29wZSI6IlJhd0Jsb2I6ZHVrZS1jb21wc2NpMzA4LXNwcmluZzIwMTQvc2xvZ29fdGVhbTAxL21hc3Rlci8yLlBORyIsImV4cGlyZXMiOjEzOTYwNjc0MjB9--6eda03e10b08dc57d0bb273bdd79435f0120172d)

The snapshot above shows how a color chooser for the pen pops up after clicking "Choose a Pen Color" button

![] (https://raw.githubusercontent.com/duke-compsci308-spring2014/slogo_team01/master/3.png?token=6359679__eyJzY29wZSI6IlJhd0Jsb2I6ZHVrZS1jb21wc2NpMzA4LXNwcmluZzIwMTQvc2xvZ29fdGVhbTAxL21hc3Rlci8zLnBuZyIsImV4cGlyZXMiOjEzOTYwNjc5Njl9--89c6d3451b3c3bab9cb37fd658cf35be589a2596)

The snapshot above shows the contents of the Display menu that allows the selection of a background color. It also shows multiple turtles in the workspace along with their traces. Previously executed commands and current variables can also be observed.

![] (https://raw.githubusercontent.com/duke-compsci308-spring2014/slogo_team01/master/4.PNG?token=6359679__eyJzY29wZSI6IlJhd0Jsb2I6ZHVrZS1jb21wc2NpMzA4LXNwcmluZzIwMTQvc2xvZ29fdGVhbTAxL21hc3Rlci80LlBORyIsImV4cGlyZXMiOjEzOTYwNjg2MTZ9--38498c3cfb802ae2208cca8c1367dce862c7627c)

The snapshot above shows the existence of multiple workspaces.


##Design Details: Benson and Tara

###Backend

The backend of our design includes three main packages: model, turtle, and nodes. The package parser contains class Parser, NodeFactory, and Model. Each workspace has one model and one controller, and each model keeps track of all of its respective Parser objects. NodeFactory creates nodes using reflection, and its method of creating new nodes is called in class Parser.

Class Parser has two main methods: createTree() and traverseTree(). Method createTree() creates an Abstract Syntax Tree for the commands user enters into one workspace. A queue structure is used to store the individual words to be read. Each word is then passed into method createNode(), which is a method within class NodeFactory. 

As stated before, class NodeFactory’s purpose is to create nodes that will then be used in the creation of an Abstract Syntax Tree. Within the method createNode(), each passed in “word” is then filtered: is this String numerical or not? If the “word” is a number, then an instance of class NumberNode is created. 

Given the situation in which the String isn’t a number, reflection is then used. Within the method createNode() a map structure is propagated with data types (String, String), where each word is the key and each value is the referenced command. How do we find the referenced commands? The classes CommandFinder and CommandReader make it possible to reference these commands. Provided a language and word, CommandFinder uses reflection to reference the correct language properties file. CommandReader then parses the file and creates the Map.

Method createNode() then uses the already generated map to fetch the command. The fetched command is then used to create an instance of each node class via reflection. Let's resurface back to class Parser. 

In class Parser's createTree(), command nodes, number nodes, and a variety of other nodes are created as we read each word that is popped out from the queue. A BlockNode is created whenever an expression is anticipated. A ConditionNode is created for the left node of control structures ('if', 'ifelse', 'repeat', 'for', etc). When creating the Abstract Syntax Tree, methods in each node, hasTwoChildren() and hasMoreThanTwoChildren(), decide whether a child node will be added.

A tree structure is chosen because it represents the structure of program code. A tree structure is made of nodes, and each node can be edited and changed. Also, the tree we constructed contains essential information and eliminated brackets and semicolons, which are non-essential. Furthermore, the implementation of each nodes can be easily changed, and new nodes can be easily added. 

A post-order traversal is implemented when traversing the tree after it is created. When a node is reached, the .evaluate() method in the node class is called, which resolves the node and returns its value. For more complex control commands such as 'repeat', 'ifelse', 'dotimes', the .traverseSubtree() method is implemented in their respective node class, which helps keep traversing the tree in post-order.

From Part 2 to Part 3, the methods of creating and traversing the tree are not changed. New control commands 'dotimes', 'for', and 'to' are compared with existing node structures. For example, they are all similar to the 'ifelse' command because two block nodes are required for their child nodes. Therefore, they can be easily incorporated into .createTree(). The methods to resolve these nodes are implemented in their respective node classes, and a .traverseSubtree() method is also implemented if required. 

User defined variables and functions are stored in the Parser class. When they are first encountered, they are added to the variable or function map, and when they are encountered after they have been declared, the FunctionNode or VariableNode that are already created are obtained from the map. In cases when the value of a VariableNode changes, such as in the 'for' command, the value of the VariableNode is changed in the .traverseAndReplace() method in the ForNode.

Class Model is created in Part 3. We did not anticipate that there would be multiple workspaces and turtles when we implemented Part 2. We created the Model class, which is the "workspace manager" to deal with the change. We also changed the constructors of all nodes from one turtle to a list of turtles. Class Model calls the .doParse() method in the Parser class to pass the commands. A list of turtles, commands, and user's choice of language are passed to the Parser class through the Parser constructor.

The turtle package is the central module of SLogo. Within this package, class Turtle contains all the properties that are updated by class Parser and are required by the view. The properties within each turtle instance are updated in real-time as traverseTree() is called. The methodology in which the information is extracted from class Turtle to the view leads us to the next component of our project.

The proceeding discussion will pertain to the interface design between the model and view where information is sent from the view to the model and fetched from the model to the view. The controller package and the turtle package are the two main components that make up such interface.

The controller package contains abstract class AbstractController and class ModelController.  Abstract class AbstractController was created on the premise of extendability.

AbstractController was designed in a way that allows us to add registered views and a registered model, given the situation that we wanted to reference multiple workspaces. Methods within AbstractController such as getTurtleList() and getVariables() make it possible to fetch information from the backend.

Moreover, one instance of class Model is created per instance of a subclass that inherits AbstractController. This design choice was based on the fact that only one model was needed to fufill the requirements from each view.

Given such time constraints, an all-encompassing controller, class ModelController, was created. This class extends AbstractController and has method passToModel(), which is used to pass information to the backend. This method takes in a list of instances of TurtleImage (class TurtleImage is displayed in the GUI and is used to represent a frontend replication of the backend turtle), a command, and a language.

Within passToModel(), TurtleImage is converted into an ITurtle, which is an interface implemented by class Turtle. The purpose of ITurtle was to create a level of encapsulation between the model and view; there’s no need for the controller to have access to a complete instantiation of class turtle.

Using all of these parameters, method passToModel() calls method processCommands(), which is a method residing in class Model. Method processCommands() is the highest level method within the backend.

Despite the nomenclature, passToController() also contains method updateTurtleState() that updates the x-coordinates, y-coordinates, and heading of a list of class TurtleImage (the frontend turtles). This design choice was influenced by the impending deadline.

Class Workspace panel, the “top” module of the frontend, calls method passToEnglishModel() within a property listener method to bridge the connection between the frontend and the controller. Method passToEnglishModel() is a wrapper function of passToModel(). In other words, passToModel() is passed in with ‘English’ as the language of choice within passToEnglishModel(). Any language could have been chosen to be passed into passToModel(). This wrapper function was created because the frontend lacks the ability to choose a language of choice.

The controller was conceptually designed to reduce the amount of direct communication between the model and view. The controller’s interaction with frontend is through the IView interface. Although there are no implemented methods from IView, this interface was planned to ping the controller whenever any changes occurred in the view.

Lastly, class Turtle extends abstract class AbstractModel. AbstractModel was created to improve communication between the controller and ITurtle by notifying the controller whenever a turtle property changed. Although none of the methods from AbstractModel were implemented, the extension of AbstractModel provides a template for feasible changes in the future.


###Frontend

####MenuComponents
This module is for the components that are present in the menu bar. It contains two classes, ColorMenuComponent and FileMenuComponent. Both of these are enum objects. This module compartmentalizes the busy work for some of the menu creation. For extensibility, to add a new color choice for the background, one would modify the ColorMenuComponent file in a very intuitive way and to add a new option for the fileMenu, one just needs to add the name of the button and the method that it calls in the FileMenuComponent. Thus, in the class where the menu is created, it just refers to these two classes instead of listing all the components in there, making for more readable code as well. 

The individual components in the two classes are called and created in different ways – this shows that they can be implemented differently. ColorMenuComponent is created by just calling the different “variables” in each part, but the FileMenuComponent is created through reflection. The FileMenuComponent pieces contain names of functions that are called when different menu buttons are clicked, which is why this class implements reflection.  

This module was created to abstract some of the menu bar creation, making the code more readable and more easily extensible. This adheres to the design principles we learned in class as well. 

####FunctionStorage
This class handles the saving and loading of the user-defined functions to this project. It will ideally have contact with the controller to obtain the functions that it loads and saves. It is essentially just a saving and loading device right now – it will get the data that it needs to load and save from the controller. And this code is easily extensible as well. To add additional functionality, one would just create another class and add it to this module and the functionMenu class. For example, if a developer wanted to add the ability to combine files of functions together, they would just need to create a class called FunctionCombiner (for example) and then add the functionality and then add it to the FunctionMenu Class. This menu could be created in a manner similar to the other menus in this project, but this functionality was not implemented. The names of this module might also be confusing, as they are verbs rather than nouns, but this could also be changed easily. 

The functionality for this class was not completely implemented, but we were planning on having the controller pass the functions to be saved through this class, thus keeping the communication between the front and back ends to a minimum, managed by the controller. 

This module was created to abstract the saving and loading of functions to the project. It could have been named “DataManager” and then the saving and loading preferences could also have been included here – this would reduce the repeated code and adhere to design principles of keeping this abstract and distant from their “implementations”. Unfortunately, we were not able to fully implement the functionality of either of these things, but we our design would have allowed us to easily add them. And even though the naming indicates otherwise, this module deals with interacting with different data, thus it adheres to design principles of keeping things separate, abstract, and extensible.

####turtle_graphics package
This package has two classes: TurtleFileChooser and TurtleImage. The TurtleFileChooser does not need a constructor since this class only has a static method chooseImageFile() so an instance is not required. This static method is called in the TurtleImage class constructor to define the image to use for that particular instance of the TurtleImage class. The TurtleImage class acts a front-end turtle and basically displays an image for particular turtle at its particular coordinates. The TurtleImage class implements the ITurtle interface. The interface allows back and forth conversion between TurtleImage instances and their corresponding Turtle instances during communication through the controller. The front-end does not need to know everything about the Turtle instances so the interface enables encaspulations. Ids were not added to TurtleImage instances due to time constraints; hence, so the user had to manipulate all the turtles in the workspace with a command and not a set of particular turtles based on ids. This can be easily extended by adding necessary id creation and detection methods.  All the TurtleImages are created in the TurtleDisplayPanel.

The current movement methods need to be modified because currently lines are drawn between the last position to the position at the end of a command i.e. a shortest path vector. The actual command may have many intermediate positions so the drawn line would not represent the actual trail. This issue can be corrected through the appropriate use of PropertyChangeListeners in the backend while updating a turtle's position and passing the new position to the front-end via the controller every time the position (property) is modified.

####TurtleDisplayPanel
This module is basically the panel of the GUI that displays all the current turtles and their associated trail lines. Every time a turtle’s position is update or its heading is changed, this panel will display the new position. The panel has a list of all the TurtleImages in existence for the current workspace. It overrides the paintComponent method to print all the TurtleImages and their associated lines. It also has a method for creating a new TurtleImage at the center of the panel. This method is used by the class internally for creating the first TurtleImage in the panel’s constructor. It is also used in the ActionDisplayPanel (which holds an instance of the TurtleDisplayPanel) which uses it in the ActionListener for the ‘Create Turtle’ button. The TurtleDisplayPanel also makes use of the TurtleImage class’s getCanvasStateInfo method to return the heading and coordinates of all the TurtleImages in existence. This information is displayed by the ActionDisplayPanel. The TurtleDisplayPanel also has methods to move each of the TurtleImage in the list by a set distance horizontally (left or right) and vertically (up or down) w.r.t the center of the panel. These methods obtain the movement by using the movement methods of the TurtleImage class. The movement methods of the TurtleDisplayPanel are used by the movement buttons created in the ActionDisplayPanel. 

TurtleDisplayPanel also has a method for centering all TurtleImages in its list of TurtleImages and clearing their trail lines. This method depends on setTurtleCenter method of TurtleImage class and it is in turn used during the creation of the ‘Clear’ button in the ActionDisplayPanel. Similarly methods exists in the TurtleDisplayPanel for setting the pen color, setting the pen toggle etc. and they are used by the appropriate buttons in the ActionDisplayPanel. Finally, the TurtleDisplayPanel has methods for setting and getting its list of TurtleImages which are used (in the workspace panel) for communication with the model through the controller. The TurtleDisplayPanel performs its task, of displaying all the turtles and their lines, well. Many getters and setters were used in the panel which can potentially be reduced to improve encapsulation. The problem of incorrect trail path from user commands is not caused by this module but by the TurtleImage and the Controller module as discussed in the turtle_graphics package design section.

####preferences package
This package has classes that should ideally allow the user to save and load workspace preferences. The package has two classes: PreferencesChooser and PreferenceHelper. Both these classes don’t need constructors as they only have static classes. The choosePrefFile() method of the PreferenceChooser basically selects and returns an XML file that has saved preferences for a workspace. This method is called in the static read() method of the PreferenceHelper which uses an XML decoder to decode the preferences. The static write() method of this class used an XML encoder  to save the current workspace properties by encoding the TurtleDisplayPanel of the workspace into an XML file. These methods are used in the makePreferenceButtons() method of the ActionDisplayPanel. 

The classes in the preferences package fail to perform their function properly because the entire approach was not completely thought through due to time constraints. However, the general idea is on track and the code can debugged to ensure that the correct functionality is obtained. Instead of directly trying to encode the entire TurtleDispayPanel instance as an XML, a Map or list of current preferences to be saved can be maintained to help the process.

####ActionDisplayPanel
This module holds an instance of the TurtleDisplayPanel and also creates various buttons that allow the user to manipulate the turtles. It has a ScrollableTextArea that displays the coordinates and headings of all the turtles. The movement buttons for standard horizontal and vertical movement of the turtles are created using the TurtleDisplayPanel movement along with reflection to minimize duplication of code. JColorChooser is used within the ActionListener for the pen color chooser button to choose the current color for the pen. This allows a more attractive display. Preference buttons are also created but they are not functional because the classes within the preference package are faulty as discussed above. This module also has a method for getting its TurtleDisplayPanel. An instance of the ActionDisplayPanel is created in the WorkspacePanel. The WorkspacePanel gets the TurtleDisplay panel from the ActionDisplayPanel and then lets the controller manipulate the list of TurtleImages as necessitated by the communication with the backend. The ActionDisplayPanel basically is one of the two main components added to WorkspacePanel and it gives the main workspace the needed functionality of displaying turtles and their information, along with performing necessary functions like choosing pen colors or optional functions like moving turtles with buttons.

####WorkspacePanel
A majority of this class’s functions deal with interacting with the panels that it contains. It creates and sets each one, which are all separate methods. It is essentially a container for the other panels – streamlining the communication that the controller makes between the front and the back ends. Thus, a majority of the functionality of this panel is essentially discussed in the ActionDisplayPanel piece and the ProgrammingPanel piece of this design details document.
This follows the principles of increased readability and naming by keeping the pieces of our project that do separate things in separate functions and classes. While there may be a lot of classes, it is easier to see how they fit together and what the individual components do because we separated them.

The WorkspacePanel has several get/set methods. These are created so the controller can gain access to these variables and use them for interaction between the front and back ends. 

The PropertyChangeListener was created so the user can interact with the ProgrammingPanel. When the button is clicked the listener is activated and several events occur. The data is passed as a string to the back-end through the controller, the text area where the data was entered is cleared, and then the other panels are updated. 


####GridBagPanel():
This class extends JPanel and has one method in it for adding bordered components. This class was created because we wanted to organize our ActionDisplayPanel and ProgrammingDisplayPanel utilizing GridBagLayout. Inititally, both ProgrammingPanel and ActionDisplayPanel both had a method called addBorderedComponent() but we realized that instead of writing redundant code we should have both classes extend a class with that method. The method addBorderedComponent takes in GridBagConstraint parameters used to set the given JComponent into the desired area of the grid with specified height and width in grid cells The method puts a border around the component with a title. Finally, the method adds the component to the class object which is a type of JPanel. The only thing the contructor does is set the LayoutManager of the GridbagPanel to be GridBagLayout(). This class was created for the purpose of organizing code via a simple hierarchy and to avoid repeating code.

####ScrollableJList():
This class is essentially a JList set inside of a JScrollPane. The reason I created this class is because I realized we needed to use JLists within JScrollPanes for our ProgrammingPanel: one for a list of the previous commands, one for a list of the current variables, and another for a list of valid functions. Instead of manually typing the code to insert a JList into a JScrollPane each time we needed to, I decided to make a class that did this for us to avoid repeating code. As a parameter, the constructor takes a MouseListener that the user could set to listen for mouse clicks. There is another constructor that takes in the MouseListener as well as the preferred height and width of the JList in pixels. Finally, there is a method for setting the ListModel of the JList. If we had more time, I would have modified the constructors of this class and have one that just took in the preferred size of the text area and a default constructor with no inputs and implement a separate function for adding a MouseListener. This class was created for the purpose of reusability and convenience as well as to satisfy the requirement that there be lists containing previous valid commands, variables, and functions that the user could interact with.

####ExecutedCodePanel():
This class is essentially a JList inside of a JScrollPane and contains a list of previous valid commands that the user executed. If we had more time, I would have used a ScrollableJList instead which would have made for simpler code and more efficiency. There are no input parameters to the constructor. The constructor simply adds a JList into the ExecutedCodePanel (ExecutedCodePanel extends JScrollPane), makes the JList uneditable, and adds itself as a MouseListener. The purpose of the MouseListener was so that whenever the user clicked on an item within the JList, that item would be sent to the backend for execution. We never ended up allowing the user to run previous valid commands but if we had more time it would have been straightforward to implement. Also, we would have added an addition to the PropertyChangeListener function in WorspacePanel to monitor changes in the ExecutedCodePanel whenever an item in the JList is double-clicked by the user and ready to be executed. The method addToCodeList simply adds whatever string is passed as a parameter to the JList contained in within this panel. Finally, this class implements MouseListener and is thus required to implement the MouseClicked method. This method essentially monitors the JList for whenever an item is double-clicked which signals that the user wants to execute the specified code snippet. The purpose of this class was to satisfy the requirement that previous commands be listed for the user to interact with. If we had more time, I would have found a way to avoid implementing MouseListener on this class in order to avoid useless code because only 1 out of the 5 required methods for MouseListener are actually implemented.

####SLogo:
This class is is where the main functionality of the program is kept. New workspaces are created through this method. It keeps a list of all the workspaces and allows the user to choose them from a tabbed display. The menu bar is also created in this class. This is essentially the highest container - it also extends JFrame, which reinforces that idea. This has the main() method of the program meaning that it is where the first bits of code are ran. 

This code houses some special design principles. The menu bars are created using reflection and enums. This is described in more detail in the MenuComponents module, but to keep it brief, the enum values give strings that point to various functions in the SLogo class. These are the buttons that are created through reflection. Enums are also used to create the background color chooser which is in the menu bar as well, but this one does not use reflection, rather, the values are coded in to the class itself.

Ideally, we would have moved the MenuBar creation to a separate class. This would have made the design of this class simpler. We were also planning on moving the Workspace list to a separate place that would manage it, meaning that when the program is executed, the SLogo instance will not need to do as much. But this class currently functions as the highest level container, meaning that it contains very important and far-reaching functionality.

####ProgrammingPanel():
This class extends GridBagPanel. It’s main purpose is to organize all programming related components into a user-friendly and presentable orientation. The compoenents contained within this are two scrollableJList’s (one for the variables, another for the functions), one ExecutedCodePanel to display previous commands, a JTextArea for entering commands, an Execute button for executing said commands, and a JLabel that is a hyperlink pointer to the basic commands reference on the class website. The organization of these components is determined within the default constructor according to the GridBagConstraints passed as parameters to the addBorderedComponent method of GridBagPanel class which this class extends. The makeHyperLink method creates a JLabel in the form of a hyperlink reference to the basic commands. This is achieved by adding a MouseListener to the JLabel that navigates to the webpage when the label is clicked. This class implements an ActionListener and thus the actionPerformed method. This method updates the local private variable myCommand with the text on the userTextArea when the Execute button is clicked (the execute button adds this class as an ActionListener). The method also clears the text contained within the userTextArea and adds the executed code to the JList contained in the ExecutedCodePanel via a call to addToCodeList. The method getTextArea returns the userTextArea object which is an instance of a ScrollableTextArea. The getCommand method is called within WorkspacePanel in order to retrieve the executed code and send it to the backend for processing. The addInstanceVariables method was added and used to update the instanceVars with a list of the variables contained in the userTextArea. This class also acts a medium between the WorkspacePanel and ScrollabletextArea for passing commands to the backend. The WorkspacePanel class has a method addPropertyListener which adds a new propertyChangeListener to the ScrollableTextArea contained within the ProgrammingPanel. When the execute button is clicked, a call to firePropertyChange is made within the ScrollableTextArea which passes the text contained in the text area as an argument and the PropertyChangeListener setup in the WorkspacePanel passes said command to the Workspace Panel's associated controller for execution.

####ScrollableTextArea():
This class was created for reasons similar to those for ScrollableJList. In the case of this class, we required scrollable text areas so this class automatically adds a JTextArea to a JScrollPane. As with the ScrollableJList, there are two constructors: the first takes in the text area’s height and width. The second constructor is the default with no parameters. There a 6 methods to this class that are essentially methods available for JTextArea that I needed to make accessible to the coder. The append method is what is used to add text to what already exists in the text area. The setText method is used to set the text in the text area and fire a PropertyChange event. Whenever the execute button (created in the ProgrammingPanel) is pressed the text is set to an empty string and a property change event is fired which is used by a PropertyChangeListener in the WorkSpacePanel to send the command to the model through the controller. The getText method returns the text in the text area. The getTextLength method returns the length of the String contained within the text area. The setEditable function is used to determine if the user should be able to manually edit the text via keyboard input. Finally, the setCaretPosition method is used to determine where new text is inserted in the text area. This class was created for the purpose of efficient reusability as well as to satisfy the requirement of allowing the user to input commands that they could execute. 
