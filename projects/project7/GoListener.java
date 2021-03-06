/**
 * GoListener.java
 * 
 * @author Given Tanri
 * Wheaton College, CSCI 235, Spring 2020
 * Project 7
 * 20200502
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GoListener implements ActionListener, Painter {

    // FINAL CONSTANTS to define the colors of the 
    private final Color AXIS_COLOR = Color.BLACK;
    private final Color FUNC_COLOR = Color.RED;

    /**
     * PaintPanel to which this corresponds. 
     */
    private PaintPanel panel;

    /**
     * GraphCalc to which this corresponds. 
     */
    private GraphCalc calc; 

    /**
     * The mathematical function to be graphed on the GraphCalc.
     */
    private ExprNode func; 

    /**
     * The boundaries of the associated Graphcalc. 
     */
    private double xmin, ymin, xmax, ymax; 

    /**
     * Constructor.
     * @param panel PaintPanel to which this corresponds.
     * @param calculator GraphCalc to which this corresponds.
     */
    public GoListener(PaintPanel panel, GraphCalc calc) {
        this.panel = panel;
        this.calc = calc; 

        // Assign this as its own Painter.
        this.panel.setPainter(this);

        // Get the function and x & y ranges from the GraphCalc.
        this.getInput(calc); 
    } 

    /**
     * Update the graphPanel based on inputs on JTextField objects.
     * This method is inherited from ActionListener.
     * @param e ActionEvent object (unused)
     */
    public void actionPerformed(ActionEvent e) {

	// Reset errorFlag.
	calc.setErrorFlag(false);

	// Get the user input,
	// Error input handling is in GraphCalc class.
	this.getInput(calc);

	// Handling when min !< max.
	if (xmin >= xmax || ymin >= ymax) {
	    calc.setErrorFlag(true);
	    calc.setErrorMsg("min must be smaller than max.");
	}

	// If no error occurs, 
	// erase any error message and proceed to repaint.
	if (!calc.hasError() ) {
	    calc.setErrorMsg("     ");
	    panel.repaint();
	}
    }
    
    /**
     * Update the display using the given graphics object.
     * @param g The graphics object to manipulate
     */
    public void paint(Graphics g) {
        g.setColor(AXIS_COLOR); 
        if (containsXAxis() ) drawXAxis(g);
        if (containsYAxis() ) drawYAxis(g);

        g.setColor(FUNC_COLOR); 

        for (int xPx = 0; xPx <= calc.GRAPH_WIDTH; xPx++) {
            double x = pixelToX(xPx); 
            double y = func.evaluate(x);

            if (isYOnPanel(y) ) {
                int yPx = yToPixel(y);
                g.drawRect(xPx, yPx, 1, 1);
            }
        }
    }

    // Helper methods
    /**
     * Return whether the graph should contain the x axis. 
     * @return xAxis The boolean value
     */
    public boolean containsXAxis() {
        boolean xAxis = (ymin <= 0) && (ymax >= 0); 
        return xAxis; 
    }

    /**
     * Return whether the graph should contain the y axis. 
     * @return yAxis The boolean value
     */
    public boolean containsYAxis() {
        boolean yAxis = (xmin <= 0) && (xmax >= 0); 
        return yAxis; 
    }

    /**
     * Draw the x axis. 
     * @param g The Graphics object to be painted.
     * POSTCONDITION: x axis is drawn on GraphPanel
     */
    public void drawXAxis(Graphics g) {
        int x1 = xToPixel(xmin);
        int y1 = yToPixel(0);
        int x2 = xToPixel(xmax);
        int y2 = yToPixel(0);
        g.drawLine(x1, y1, x2, y2);
    }

    /**
     * Draw the y axis. 
     * @param g The Graphics object to be painted.
     * POSTCONDITION: y axis is drawn on GraphPanel
     */
    public void drawYAxis(Graphics g) {
        int x1 = xToPixel(0);
        int y1 = yToPixel(ymin);
        int x2 = xToPixel(0);
        int y2 = yToPixel(ymax);
        g.drawLine(x1, y1, x2, y2);
    }

    /**
     * Return whether the y value is within the y range. 
     * @param y The y value
     * @return Whether ymin < y < ymax
     * PRECONDITION: y must be a valid double
     */
    public boolean isYOnPanel(double y) {
        boolean yOnPanel = (ymin <= y) && (y <= ymax);
        return yOnPanel; 
    }

    /*
      NOTE on the following conversion methods: 
      Algebraic conversions are based on the ratio
      (pixel location/pixel range) = (var location/var range)
    */

    /**
     * Convert x position to pixel position
     * @param x The value of x position
     * @return xPx The pixel position
     * PRECONDITION: xmin < x < xmax; x must be a valid double
     */
    public int xToPixel(double x) { 
        double xRange = xmax - xmin; 
        double xPos = (x - xmin) / xRange;
        int xPx = (int)(xPos * calc.GRAPH_WIDTH);

        return xPx; 
    }

    /**
     * Convert x pixel position to x position.
     * @param xPx The pixel of the given x
     * @return x The value of the pixel position
     * PRECONDITION: 0 < xPx < GRAPH_WIDTH; xPx must be a valid int
     */
    public double pixelToX(int xPx) {
        double xRange = xmax - xmin; 
        double xPos = ( (double)xPx/calc.GRAPH_WIDTH) * xRange; 
        double x = xmin + xPos;

        return x; 
    }

    /**
     * Convert y position to pixel position
     * @param y The value of y position
     * @return yPx The pixel position
     * PRECONDITION: ymin < y < ymax; y must be a valid double
     */
    public int yToPixel(double y) {        
        double yRange = ymax - ymin; 
        double yPos = (y - ymin) / yRange;
        int yPx = (int)( (1.0 - yPos) * calc.GRAPH_HEIGHT);

        return yPx; 
    }

    /**
     * Get, parse, and assign all input values to this GoListener.
     * Error handlings are defined in the GraphCalc class.
     * @param calc The GraphCalc object that serves as the data source
     * PRECONDITION: calc must be a valid GraphCalc
     * POSTCONDITION: func, xmin, ymin, xmax, and ymax are updated
     */
    public void getInput(GraphCalc calc) {
        func = calc.getFunc(func);
        xmin = calc.getFieldValue(calc.xminField(), xmin);
        ymin = calc.getFieldValue(calc.yminField(), ymin);
        xmax = calc.getFieldValue(calc.xmaxField(), xmax);
        ymax = calc.getFieldValue(calc.ymaxField(), ymax);
    }

}
