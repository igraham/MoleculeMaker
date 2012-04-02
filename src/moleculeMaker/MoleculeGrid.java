package moleculeMaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;


@SuppressWarnings("serial")
public class MoleculeGrid extends JButton implements MouseListener, MouseMotionListener, KeyListener {

	MMController mmc;
	ConnectionList elist;
	//		public static final int GRID_SPACING = 20;
	public static int GRID_SPACING = 100; // = 240 / 16;
	public static int OBJECT_OFFSET = GRID_SPACING / 4;

	public static int GRID_SPACING_X = 100; // = 240 / 16;
	public static int GRID_SPACING_Y = 100; // = 240 / 16;

	public static int OBJECT_OFFSET_X = GRID_SPACING / 4;
	public static int OBJECT_OFFSET_Y = GRID_SPACING / 4;

	private final int LEFT_CLICK = 1;
	private final int RIGHT_CLICK = 3;

	private int highlightRow = -1;
	private int highlightColumn = -1;

	private double currentX;
	private double currentY;

	private boolean leftPressed = false;
	private boolean rightPressed = false;

	private boolean drawBondLine = false; // whether a moving arrow should be drawn (for right-click bond link formation)
	private boolean drawArrowLine = false;
	//These values will be used in the XML problem to get the right coordinates for a 240 scale application.
	private double yConversion = 240.0 / getSize().getHeight();
	private double xConversion = 240.0 / getSize().getWidth();

	public MoleculeGrid(MMController mmc)
	{
		this.mmc = mmc;
		elist = new ConnectionList();
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,3));

		// View should set focus, remove this later on:
		this.isFocusable();
		this.requestFocusInWindow();
		this.setFocusTraversalKeysEnabled(false);
		this.addKeyListener(this);
	}

	public void paintComponent(Graphics g)
	{
		clearScreen(g);
		drawGrid(g);
		drawElements(g);
		
		if (drawBondLine == true) {
			Bond.drawDrag(g, Color.MAGENTA, elist.getSelected(), currentX, currentY);
		}
		
		if (drawArrowLine  == true) {
			Arrow.drawDrag(g, Color.BLUE, elist.getSelected(), currentX, currentY);
		}
		
		drawConnections(g);
	}

	private void drawConnections(Graphics g)
	{
		ArrayList<MoleculeConnectorComponent> connections = elist.getBondsAndArrows();
		
		if (connections == null) return;
		
		for(MoleculeConnectorComponent c : connections)
		{
			c.draw(g, OBJECT_OFFSET, OBJECT_OFFSET_Y);
		}
	}

//	private void drawArrows(Graphics g) {
//		ArrayList<Arrow> pair = elist.getArrows();
//
//		if (pair == null)
//			return;
//
//		for (int i=0; i < pair.size(); i++)
//		{
//			pair.get(i).draw(g, OBJECT_OFFSET, OBJECT_OFFSET_Y);
//		}
//	}

	public static void setGridSpacing(int width, int height)
	{
		GRID_SPACING = width / 30;
		GRID_SPACING_X = width / 30;
		GRID_SPACING_Y = height / 30;
		OBJECT_OFFSET = GRID_SPACING / 4;
		OBJECT_OFFSET_Y = GRID_SPACING_Y / 4;
	}
	
	public void setXMLConversion(double width, double height)
	{
		yConversion = 240.0 / height;
		xConversion = 240.0 / width;
	}

	public double getYConversion() {
		return yConversion;
	}

	public double getXConversion() {
		return xConversion;
	}

	// Clear screen
	private void clearScreen(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	// Draw (X, Y) grid lines
	private void drawGrid(Graphics g)
	{	

		// Draw rows
		for (int rows = 0; rows < this.getHeight() / GRID_SPACING_Y; rows++)
		{
			g.setColor(Color.LIGHT_GRAY);

			if (GRID_SPACING_Y * rows == highlightColumn) { g.setColor(Color.BLUE); }

			g.drawLine(0, (rows * GRID_SPACING_Y) + GRID_SPACING_Y/4, this.getWidth(), (rows * GRID_SPACING_Y) + GRID_SPACING_Y/4);
		}

		// Draw columns
		for (int columns = 0; columns < this.getWidth() / GRID_SPACING; columns++)
		{
			g.setColor(Color.LIGHT_GRAY);

			if (GRID_SPACING * columns == highlightRow) { g.setColor(Color.BLUE); }

			g.drawLine((columns * GRID_SPACING) + GRID_SPACING/4, 0, (columns * GRID_SPACING) + GRID_SPACING/4, this.getHeight());
		}
	}

	private void drawElements(Graphics g)
	{
		if (elist.hasElements())
		{
			// Smallest size is used for radius of circle so elements don't
			// get "squished in" when one of the axis is smaller than the other:
			int smallestSize = GRID_SPACING;
			if (GRID_SPACING > GRID_SPACING_Y) {
				smallestSize = GRID_SPACING_Y;
			}

			for (Element e: elist.getCoordinates())
			{

				g.setColor(e.getColor());
				g.fillOval((int)(e.getX() * GRID_SPACING + OBJECT_OFFSET/4), (int)(e.getY() * GRID_SPACING_Y + OBJECT_OFFSET_Y/4), smallestSize / 2, smallestSize / 2);
			}
		}
	}

	public static int getGraphCoordinateX(double xPixelCoordinate)
	{
		// Round to the nearest X grid space
		return (int) (float)(Math.round(xPixelCoordinate / GRID_SPACING));
	}

	public static int getGraphCoordinateY(double yPixelCoordinate)
	{
		// Round to the nearest Y grid space
		return (int) (float)(Math.round(yPixelCoordinate / GRID_SPACING_Y));
	}
	
	
	// ***************** MouseListener *****************
	public void mouseReleased(MouseEvent e)
	{
		currentX = getGraphCoordinateX(e.getX());
		currentY = getGraphCoordinateY(e.getY());
		
		MoleculeComponent clickedOn = elist.getClickedComponent(e.getX(), e.getY());
		
		if (drawArrowLine || drawBondLine) // if connection is being made
		{
			if (clickedOn != null && elist.getSelected() != null) // if released on valid component
			{
				if (clickedOn.getClass() == Element.class && (rightPressed && !leftPressed)) { // if it's a bond
					elist.add(new Bond(elist.getSelected(), clickedOn));
				}
				else if ((elist.getSelected().getClass() == Element.class && clickedOn.getClass() == Bond.class)
						|| (elist.getSelected().getClass() == Bond.class && clickedOn.getClass() == Element.class)
						|| ((elist.getSelected().getClass() == Bond.class && clickedOn.getClass() == Bond.class) && !(elist.getSelected().equals(clickedOn)))
						&& (leftPressed && !(rightPressed))) {
					System.out.println(elist.getSelected().getKey());
					System.out.println(clickedOn.getKey());
					elist.add(new Arrow(elist.getSelected(), clickedOn));
				}
				
			}
			
			drawArrowLine = false;
			drawBondLine = false;
			
			repaint();
		}
		else // connection is not being made
		{	
			if (clickedOn != null) // if released on valid component
			{
				
			}
			else // Didn't click on valid component
			{
				if (leftPressed && !rightPressed) // If mouse button clicked was left
				{
					elist.add(new Element(currentX, currentY));
				}
			}
		}
		
	}
	

	private void setHighlightedCoordinates(int currentRow, int currentColumn)
	{
		if (currentRow != highlightRow || currentColumn != highlightColumn)
		{
			highlightRow = currentRow;
			highlightColumn = currentColumn;
			repaint();
		}
	}
	
	/**
	 * To be used when debugging the mouse events. Use it in one of the mouse
	 * event methods and pass in the mouse event that is generated and it will
	 * be able to tell you which button had its 'state' changed.
	 * @param e - Mouse event from one of the mouse event methods.
	 * @return Returns the name of the button whose state was changed.
	 */
	private String translateButton(MouseEvent e)
	{
		if(e.getButton() == 0)
		{
			return "No Button";
		}
		else if(e.getButton() == 1)
		{
			return "Left Button";
		}
		else if(e.getButton() == 2)
		{
			return "Middle Button";
		}
		else
		{
			return "Right Button";
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		elist.setSelected(null);
		
		if(e.getButton() == 1)
		{
			leftPressed = true;
			rightPressed = false;
			
			MoleculeComponent clickedOn = elist.getClickedComponent(e.getX(), e.getY());
			
			if (clickedOn != null)
			{
				elist.setSelected(clickedOn);
			}
			
			mmc.view.displayAttributes(clickedOn);
		}
		if(e.getButton() == 3)
		{
			rightPressed = true;
			leftPressed = false;
		}
	}
	
	// **************** /MouseListener *****************



	// ***************** MouseMotionListener *****************

	@Override
	public void mouseMoved(MouseEvent e) {
		// Redraw highlighted row and column if mouse moves to a new set of coordinates:
		//setHighlightedCoordinates(getGraphCoordinateX(e.getX()), getGraphCoordinateY(e.getY()));

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		currentX = getGraphCoordinateX(e.getX());
		currentY = getGraphCoordinateY(e.getY());

		if(leftPressed && !rightPressed) // Left click is for arrows
		{
			
			/* If left click and drag:
			 * 		1. If clicked on nothing, don't draw anything
			 * 		2. If clicked on bond, draw line from bond (bond checked first because its
			 * 				points aren't on the grid system)
			 * 		2. If clicked on element, draw line from element		
			 */
			
			drawBondLine = false;

			if(drawArrowLine == true)
			{
				repaint();
				return;
			}
			
			MoleculeComponent component = elist.getClickedComponent(e.getX(), e.getY());

			if (component != null) {
				elist.setSelected(component);
				drawArrowLine = true;
			}
			
//			Bond bArrowStart = null; //Attempt to find a bond at this point to drag from
//			for(Bond b : elist.getBonds())
//			{
//				if (b.contains(e.getX(), e.getY()))
//				{
//					System.out.println("YES!");
//					bArrowStart = b;
//					break;
//				}
//				else
//				{
//					System.out.println("NO :(");
//				}
//			}
//			
//			Element eArrowStart = elist.getElementAt(roundX, roundY);//Attempt to see if there is an element.
//			if(eArrowStart == null) //If there is no element at this point...
//			{
//				if(bArrowStart != null)//Check to see if the bond is null.
//				{
//					System.out.println("Did it reach here?");
//					elist.setSelected(bArrowStart);
//					drawArrowLine = true;
//				}
//			}
//			else
//			{
//				elist.setSelected(elist.getElementAt(eArrowStart.getKey()));//Start the arrow here
//				drawArrowLine = true;
//			}

		}

		if (rightPressed && !leftPressed) // Right click is for bonding
		{
			drawArrowLine = false;
			/* Cases to consider:
			 * 1. If user is already dragging (this should come first!)
			 * 1. Clicked on element
			 * 2. Clicked no empty grid space
			 */

			if (drawBondLine == true) // if the element is already in the process of being bonded
			{
				repaint();
				return; // No need for further steps.
			}
			
			currentX = getGraphCoordinateX(e.getX());
			currentY = getGraphCoordinateY(e.getY());

			Element bondStart = elist.getElementAt(currentX, currentY);

			// If this is the initial movement for bonding:
			if (bondStart != null) // if bonding began at element
			{
				elist.setSelected(elist.getElementAt(bondStart.getKey())); // keep track of the bonding element
				drawBondLine = true; // indicate that an arrow should start to be drawn
			}
			else // if nothing is being dragged
			{

			}

		}//*/
	}
	// ***************** /MouseMotionListener ****************


	// ***************** KeyListener ****************
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == 8) // if delete key is pressed
		{
			MoleculeComponent currentElement = elist.getSelected();
			if (currentElement != null)
			{
				// Clear any lines being drawn (cancel connections)
				drawBondLine = false; 
				drawArrowLine = false;
				
				elist.removeSelected();
				mmc.view.displayAttributes(null);

				repaint();
			}

		}
		else if (e.getKeyCode() == e.VK_ESCAPE) // if user hits ESC
		{
			if (drawBondLine == true) // and there is a bond being drawn
			{
				drawBondLine = false; // cancel that bond
				repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	// ***************** /KeyListener ****************

}