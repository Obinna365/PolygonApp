import java.awt.*;

public class RegPolygon implements Comparable<RegPolygon> {
    Color pColor; // Colour of the polygon, set to a Colour object, default set to black
    int pId = 000000;    // Polygon ID should be a six-digit non-negative integer
    int pSides;          // Number of sides of the polygon, should be a non-negative value

    double pStarting_angle;   // starting angle

    double pRadius;           // radius of polygon

    int polyCenX;    // x value of the center point (pixel) of the polygon when drawn on the panel
    int polyCenY;    // y value of the center point (pixel of polygon when drawn on the panel
    double[] pointsX;  // int array containing x values of each vertex (corner point) of the polygon
    double[] pointsY;  // int array containing y values of each vertex (corner point) of the polygon

    // Constructor currently sets the number of sides, starting angle of the Polygon
    // You will need to modify the constructor to set the pId and pColour fields.
    public RegPolygon(int p_sides, double st_angle, double rad, Color pColor, int pId) {
        this.pSides = p_sides;              // user-defined number of sides should be non-negative
        this.pStarting_angle = st_angle;   // user-defined starting angle
        this.pRadius = rad;                // user-defined radius
        pointsX = new double[pSides];
        pointsY = new double[pSides];
        this.pId = pId;
        this.pColor = pColor;
    }

    // Used to populate the points array with the vertices corners (points) and construct a polygon with the
    // number of sides defined by pSides and the length of each side defined by pSideLength.
    // Dimension object that is passed in as an argument is used to get the width and height of the ContainerPanel
    // and used to determine the x and y values of its center point that will be used to position the drawn Polygon.
    private Polygon getPolygonPoints(Dimension dim) {
        polyCenX = dim.width / 2;                  // x value of the center point of the polygon
        polyCenY = dim.height / 2;                 // y value of the center point of the polygon
        double angleIncrement = 2 * Math.PI / pSides;  // increment of each angle
        Polygon p = new Polygon();                 // Polygon to be drawn

        for (int i = 0; i < pSides; i++) {
            pointsX[i] = polyCenX + pRadius * Math.cos(pStarting_angle);
            pointsY[i] = polyCenY + pRadius * Math.sin(pStarting_angle);

            p.addPoint((int) pointsX[i], (int) pointsY[i]);
            pStarting_angle += angleIncrement;
        }
        return p;
    }

    // You will need to modify this method to set the color of the Polygon to be drawn
    // Remember that Graphics2D has a setColor() method available for this purpose
    public void drawPolygon(Graphics2D g, Dimension d) {
        g.setColor(pColor);//added this and
        g.drawPolygon(getPolygonPoints(d));//this
    }

    // gets a stored ID
    public int getID() {
        return pId;
    }
//gets a stored colour
    public Color getColor() {
        return pColor;
    }

    @Override
    // method used for comparing PolygonContainer objects based on stored ids, you need to complete the method
    public int compareTo(RegPolygon o) {
        return Integer.compare(this.pId, o.pId);
    }
//Outputs the polygons details
    public String toString() {
        return String.format("ID: %d \\+ Sides: %d \\+  Angle: %.2f \\+ Radius: %.2f \\+ Color: %s \\+",
                pId, pSides, pStarting_angle, pRadius, getColorName(pColor));
    }

    private String getColorName(Color color) {
        if (color.equals(Color.BLACK)) {
            return "Black";
        } else if (color.equals(Color.RED)) {
            return "Red";
        } else if (color.equals(Color.GREEN)) {
            return "Green";
        } else if (color.equals(Color.ORANGE)) {
            return "Orange";
        } else if (color.equals(Color.YELLOW)) {
            return "Yellow";
        } else if (color.equals(Color.BLUE)) {
            return "Blue";
        } else
        return "Color Not Found";
    }
}
