import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class ContainerFrame extends JFrame {
    //These are the text fields for the top section of the application.
    private JTextField idField;
    private JTextField sidesField;
    private JTextField angleField;
    private JTextField radiusField;
    //This is for the colour dropdown list
    private JComboBox<String> colorComboBox;
    //Jpanel
    private JPanel polygonPanel;
    //These boolean variables is used to:
    private boolean displayNeeded = false;//When user presses display button it shows the polygon
    private boolean displaySearchResult = false;// When user presses search it shows the polygon of the ID



    private static ArrayList<RegPolygon> polygonList = new ArrayList<>();
    // Array list used to store all the polygons the user entered

    private static final String[] COLOR_OPTIONS = {"Black", "Red", "Green", "Orange", "Yellow", "Blue"};
    //Array for the colour options
    //I originally wanted to use purple, However I would need to enter the oolour code so I opted for orange.
    public ContainerFrame() {
        setTitle("My Polygon App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //creates the text fields in the jpanel
        idField = new JTextField(10);
        sidesField = new JTextField(10);
        angleField = new JTextField(10);
        radiusField = new JTextField(10);
        //creates the dropdown list for colours
        colorComboBox = new JComboBox<>(COLOR_OPTIONS); // Use JComboBox for color selection
       //creates the buttons
        JButton searchButton = new JButton("Search");
        JButton displayButton = new JButton("Display");
        JButton addButton = new JButton("Add");
        JButton sortButton = new JButton("Sort");

        polygonPanel = new JPanel() {
            @Override
            //paint component makes the polygon for display
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //if statement to display the polygon
               if (displayNeeded){
                display((Graphics2D) g);
            }}
            //the reason behind this is because without the if statement
            // the polygon will always display
        };
// allows users to input the information
        JPanel inputPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(new JLabel("Sides:"));
        inputPanel.add(new JLabel("Angle:"));
        inputPanel.add(new JLabel("Radius:"));
        inputPanel.add(new JLabel("Color:"));

        inputPanel.add(idField);
        inputPanel.add(sidesField);
        inputPanel.add(angleField);
        inputPanel.add(radiusField);
        inputPanel.add(colorComboBox);
// this is for the middle part to be empty
        JPanel emptyRow = new JPanel();
        emptyRow.setBorder(new EmptyBorder(150, 0, 150, 0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(addButton);
        buttonPanel.add(sortButton);

//This adds functions to the buttons
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //This method shows the polygon based on the given ID
                searchPolygon();
            }
        });
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //This method shows the last polygon added to the arraylist
                displayButton();
            }
        });
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //This method sorts the arraylist in descending order
                sortPolygons();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //this method adds the polygon to the arraylist
                addPolygon();
            }
        });

        // Set layout for the main frame
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(emptyRow, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(polygonPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }
//this method sorts the arraylists ID of polygon in ascending order
    private void sortPolygons() {
        Collections.sort(polygonList);
        // Prints the IDs in ascending order
        System.out.println("Polygon IDs in Ascending Order:");
        //loops through arraylist and
        for (RegPolygon polygon : polygonList) {
            System.out.println(polygon.getID());
        }
        //As said before the polygon would show so I put these to prevent it
        displayNeeded = false;
        displaySearchResult = false;
    }
//adds all information to the list
    private void addPolygon() {
        try {
            //gets 6 digit id
            //THE NUMBER OF THE 6 DIGIT ID CANNOT START WITH 0
            int id = Integer.parseInt(idField.getText());
            //If it is not 6 digits
            if (id < 100000 || id > 999999) {
                System.out.println(id + " Is Invalid");
                //It will print on the console that the id is invalid
                JOptionPane.showMessageDialog(this, "Invalid ID. Please enter at least a 6-digit ID.", "Error", JOptionPane.ERROR_MESSAGE);
                //and you will also get an error message else if the id is valid it will add the id to the arraylist
                return;
            }
            int sides = Integer.parseInt(sidesField.getText());
            //As the gui must generate polygons
            if(sides <= 4){
                // if the user requset 4 sides they will be met with error messages
                System.out.println("Invalid number of sides. Please enter a number more than 4");
                JOptionPane.showMessageDialog(this, "Invalid number of sides. Please enter a number more than 4", "Error", JOptionPane.ERROR_MESSAGE);
                //if more than 4 then it would add it to the arraylist
                return;}
            //adds the angle and radius
            double angle = Double.parseDouble(angleField.getText());
            double radius = Double.parseDouble(radiusField.getText());

            // Gets the selected color from the JComboBox
            String selectedColor = (String) colorComboBox.getSelectedItem();
            Color color = getColorFromString(selectedColor);

            RegPolygon polygon = new RegPolygon(sides, angle, radius, color, id);
            polygonList.add(polygon);

            System.out.println("Polygon with ID " + id + " added.");

            // Clear the input fields after adding a polygon
            clearInputFields();
            //We don't want to see any polygon yet
           displayNeeded = false;
           displaySearchResult = false;


        } catch (NumberFormatException ex) {
            System.out.println("Invalid input. Please enter valid numeric values.");
            //If the user doesnt put the right input then the console will say please put the right input
        }
    }
//This displays the polygon
    private void display(Graphics2D g) {
        //if the list is not empty and the boolean variable is true
        if (!polygonList.isEmpty() && !displaySearchResult) {
            //then it will get the last polygon added to the arraylist
            RegPolygon lastPolygon = polygonList.get(polygonList.size() - 1);
            //and draw it using the drawpolygon method in RegPolygon
            lastPolygon.drawPolygon(g, polygonPanel.getSize());
        }
    }
    //This method acts as a remote controller
    //If that is a good way to describe it
    //Where Its basically saying show the display polygon
    //but don't show the searched polygon
    private void displayButton(){
        displayNeeded = true;
        polygonPanel.repaint();
        displaySearchResult = false;

    }
    //This method draws the polygon when the user presses the search button
    private void displayFoundPolygon(RegPolygon foundPolygon) {
        Graphics2D g = (Graphics2D) polygonPanel.getGraphics();
        g.setColor(foundPolygon.getColor());
        g.clearRect(0, 0, polygonPanel.getWidth(), polygonPanel.getHeight());
        foundPolygon.drawPolygon(g, polygonPanel.getSize());
        displaySearchResult = true;
        //It can show the searched polygon
    }

    private void searchPolygon() {
        //the ID the user found
        int searchId = Integer.parseInt(idField.getText());
        RegPolygon foundPolygon = null;
        //found polygon is now null

        for (RegPolygon polygon : polygonList) {
            //loops through the arraylist to find the id
            if (polygon.getID() == searchId) {
                foundPolygon = polygon;
                break;
                //if the id is found the loop will stop
            }
        }

        if (foundPolygon != null) {
            // Displays polygon details on the console
            System.out.println("ID: " + foundPolygon.getID());
            System.out.println("Sides: " + foundPolygon.pSides);
            System.out.println("Angle: " + foundPolygon.pStarting_angle);
            System.out.println("Radius: " + foundPolygon.pRadius);
            System.out.println("Color: " + foundPolygon.pColor);
            displayFoundPolygon(foundPolygon);
    } else {
        System.out.println("Polygon not found.");
    }

    }
    private void clearInputFields() {
        idField.setText("");
        sidesField.setText("");
        angleField.setText("");
        radiusField.setText("");
        // Note: Don't need to clear the colorComboBox since it's a drop-down selection
    }
//turns the colours to strings to help use it
    private Color getColorFromString(String colorString) {
        switch (colorString.toLowerCase()) {
            case "black":
                return Color.BLACK;
            case "red":
                return Color.RED;
            case "green":
                return Color.GREEN;
            case "orange":
                return Color.ORANGE;
            case "yellow":
                return Color.YELLOW;
            case "blue":
                return Color.BLUE;
            default:
                return Color.BLACK; // Default to black if color is not recognized
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContainerFrame());
    }
}