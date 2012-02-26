package moleculeMaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

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

	private int mouseX = -1;
	private int mouseY = -1;

	private int roundX;
	private int roundY;

	private boolean leftPressed = false;
	private boolean rightPressed = false;

	private boolean drawBondLine = false; // whether a moving arrow should be drawn (for right-click bond link formation)
//	private boolean drawCurvedArrow = false;
	private boolean drawArrowLine = false;

	//		boolean movingElement;

	public MoleculeGrid(MMController mmc)
	{
		this.mmc = mmc;
		elist = new ConnectionList();
		//			movingElement = false; 
		this.addMouseMotionListener(this);
		this.addMouseListener(this);

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
//			drawBondDrag(g);
			Bond.drawDrag(g, elist.getSelected(), roundX, roundY);
		}
		if (drawArrowLine  == true) {
			Arrow.drawDrag(g, elist.getSelected(), roundX, roundY);
//			drawArrowDrag(g);
		}
		
		drawBonds(g);
		drawArrows(g);
	}

	private void drawArrows(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	public static void setGridSpacing(int width, int height)
	{
		GRID_SPACING = width / 30;
		GRID_SPACING_X = width / 30;
		GRID_SPACING_Y = height / 30;
		OBJECT_OFFSET = GRID_SPACING / 4;
		OBJECT_OFFSET_Y = GRID_SPACING_Y / 4;
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

		// Draw columns d
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
				g.fillOval(e.getX() * GRID_SPACING + OBJECT_OFFSET/4, e.getY() * GRID_SPACING_Y + OBJECT_OFFSET_Y/4, smallestSize / 2, smallestSize / 2);
			}
		}
	}

//	private void drawBondDrag(Graphics g)
//	{	
//			
//		g.setColor(Color.MAGENTA);
//
//		Graphics2D g2d = (Graphics2D)g;
//		g2d.setStroke(new BasicStroke(3));
//
//
//
//		g2d.drawLine(elist.getSelected().getX() * GRID_SPACING + OBJECT_OFFSET,
//				elist.getSelected().getY() * GRID_SPACING_Y + OBJECT_OFFSET_Y,
//				roundX * GRID_SPACING + OBJECT_OFFSET, 
//				roundY * GRID_SPACING_Y + OBJECT_OFFSET_Y);
//	}
	
//	private void drawArrow(Graphics g)
//	{
//		if(drawCurvedArrow)
//		{
//			g.setColor(Color.MAGENTA);
//			
//			Graphics2D g2d = (Graphics2D)g;
//			g2d.setStroke(new BasicStroke(3));
//
//			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
//					RenderingHints.VALUE_ANTIALIAS_ON);
//			int x1 = elist.getSelected().getX() * GRID_SPACING + OBJECT_OFFSET;
//			int y1 = elist.getSelected().getY() * GRID_SPACING_Y + OBJECT_OFFSET_Y;
//			int x2 = roundX * GRID_SPACING + OBJECT_OFFSET;
//			int y2 = roundY * GRID_SPACING_Y + OBJECT_OFFSET_Y;
//			Point p1 = new Point(x1, y1);
//			Point p3 = new Point(x2, y2);
//			
//			g2d.drawLine(x1, y1, x2, y2);
////			drawBarbs(g2d, p3, p1);
//		}
//	}
	
//	private void drawBarbs(Graphics2D g2, Point tip, Point tail)
//	{
//		double barbLength = 10;
//		double angle = Math.toRadians(35);
//		double dy = tip.y - tail.y;
//		double dx = tip.x - tail.x;
//		System.out.println("Change in Y: "+dy);
//		System.out.println("Change in X: "+dx);
//		double theta = Math.atan2(dy, dx);
//		System.out.println("Angle of Theta: "+theta);
//		double x, y, rho = theta + angle;
//		for(int i = 0; i < 2; i++)
//		{
//			System.out.println("Value of angle Rho: "+Math.toDegrees(rho));
//			x = tip.x - barbLength * Math.cos(rho);
//			y = tip.y - barbLength * Math.sin(rho);
//			g2.draw(new Line2D.Double(tip.x, tip.y, x, y));
//			rho = theta - angle;
//		}
//	}

	// Probably doesn't work!
	private void drawBonds(Graphics g)
	{
		ArrayList<Bond> pair = elist.getBonds();

		if (pair == null)
			return;

		for (int i=0; i < pair.size(); i++)
		{
			g.setColor(pair.get(i).getColor());
			pair.get(i).draw(g, OBJECT_OFFSET, OBJECT_OFFSET_Y);
		}

	}

	public int getGraphCoordinateX(int xPixelCoordinate)
	{
		// Round to the nearest X grid space
		return (int) (float)(Math.round(xPixelCoordinate / GRID_SPACING));
	}

	public int getGraphCoordinateY(int yPixelCoordinate)
	{
		// Round to the nearest Y grid space
		return (int) (float)(Math.round(yPixelCoordinate / GRID_SPACING_Y));
	}

	// ***************** MouseListener *****************
	public void mouseReleased(MouseEvent e)
	{
		if (e.getButton() == LEFT_CLICK)
		{
			roundX = getGraphCoordinateX(e.getX());
			roundY = getGraphCoordinateY(e.getY());	

			Element eJustClicked = elist.getElementAt(roundX, roundY);
			Bond bJustClicked = null;

			for(Bond b : elist.getBonds())
			{
				if (b.contains(e.getX(), e.getY()))
				{
					System.out.println("YES!");
					bJustClicked = b;
					break;
				}
				else
				{
					System.out.println("NO :(");
				}
			}

			if(bJustClicked != null)
			{
				System.out.println("Tried to select a bond.");
				elist.setSelected(bJustClicked);
			}

			for(Bond b : elist.getBonds())
			{
				if (b.contains(e.getX(), e.getY()))
				{
					System.out.println("YES!");
				}
				else
				{
					System.out.println("NO :(");
				}
			}
			
			MoleculeComponent bondee = elist.getElementAt(roundX, roundY);
			MoleculeComponent bonder = elist.getSelected();
			if(bonder == null || bondee == null)
			{
				drawArrowLine = false;
			}
			else
			{
				//Add an arrow to the connection list here.
			}

			if (eJustClicked != null) // If user clicks on an element (non-empty grid space)
			{
				// Cases to consider:
				//		1. Clicked unselected element -> selected that element
				//		2. Clicked selected element -> unselect it

				MoleculeComponent ePreviouslySelected = elist.getSelected(); // get selected element

				System.out.println("You clicked an existing element!");

				// If there is a previous element selected
				if (ePreviouslySelected != null)
				{
					System.out.println("And a previous element is selected.");
					elist.setSelected(eJustClicked);
				}
				else // if there is NOT a previously selected element
				{
					System.out.println("And there is no currently selected element.");
					elist.setSelected(eJustClicked);
					System.out.println("That element has been selected!");
				}

				mmc.view.displayAttributes(eJustClicked);


			}
			else // if user clicks on an empty grid space
			{
				/* Cases to consider:
				 * 1. Coordinates have current selection     -> deselect selected element
				 * 2. Coordinates have no current selection  -> add element to grid
				 */
				System.out.print("No element where you clicked... ");

				if (elist.getSelected() == null) // if nothing is currently selected
				{
					System.out.println("nothing is currently selected, so adding a new element.");

					MoleculeComponent newElement = new Element(roundX, roundY); // Create new element

					elist.add(newElement); // add it to the list
					mmc.view.displayAttributes(newElement);

					elist.setSelected(elist.getElementAt(newElement.getKey())); // set it as selected
				}
				else // if there is something selected
				{
					System.out.println("but since there's a selected element ("+elist.getSelected()+"), unselecting it.");
					//elist.setSelected(null); // remove selection

					// duplicate code from above if-statement (remove soon):
					MoleculeComponent newElement = new Element(roundX, roundY); // Create new element
					elist.add(newElement); // add it to the list
					elist.setSelected(elist.getElementAt(newElement.getKey())); // set it as selected
					mmc.view.displayAttributes(newElement);
				}
			}

			repaint();
		}
		else if (e.getButton() == RIGHT_CLICK) // On right button release
		{
			System.out.println("Right mouse release!");
			System.out.println("DrawArrow: "+drawBondLine);
			if (drawBondLine == true) // If the user releases the button when a bond is being drawn
			{
				roundX = getGraphCoordinateX(e.getX());
				roundY = getGraphCoordinateY(e.getY());

				MoleculeComponent bondee = elist.getElementAt(roundX, roundY);

				if (bondee == null) // user didn't let go on element
				{
					System.out.println("Bonding failed: No element to bond with at these coordinates!");
					drawBondLine = false;
				}
				else
				{
					// Make a bond connection between selected element and 
					// the element the mouse is hovering over

					if (elist.getSelected() == null) // if there is no selected element, you can't bond!
					{
						System.out.println("No selected element.");
						return;
					}


					System.out.println("Bonding " + elist.getSelected() + " to " + bondee + ".");

					elist.add(new Bond(elist.getSelected(), bondee));

					System.out.println("Current bonds are " + elist.getBonds());

					drawBondLine = false; // and stop drawing the arrow!
				}

				repaint();
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
		if(e.getButton() == 1)
		{
			leftPressed = true;
			rightPressed = false;
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
		setHighlightedCoordinates(getGraphCoordinateX(e.getX()), getGraphCoordinateY(e.getY()));

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//System.out.println("Mouse Dragged: "+translateButton(e));

		if(leftPressed && !rightPressed)
		{
			
			/* If left click and drag:
			 * 		1. If clicked on nothing, don't draw anything
			 * 		2. If clicked on bond, draw line from bond (bond checked first because its
			 * 				points aren't on the grid system)
			 * 		2. If clicked on element, draw line from element		
			 */
			
			
			drawBondLine = false;
			
			
			roundX = getGraphCoordinateX(e.getX());
			roundY = getGraphCoordinateY(e.getY());

			if(drawArrowLine)
			{
				repaint();
				return;
			}

			Bond bArrowStart = null; //Attempt to find a bond at this point to drag from
			for(Bond b : elist.getBonds())
			{
				if (b.contains(e.getX(), e.getY()))
				{
					System.out.println("YES!");
					bArrowStart = b;
					break;
				}
				else
				{
					System.out.println("NO :(");
				}
			}
			Element eArrowStart = elist.getElementAt(roundX, roundY);//Attempt to see if there is an element.
			if(eArrowStart == null) //If there is no element at this point...
			{
				if(bArrowStart != null)//Check to see if the bond is null.
				{
					System.out.println("Did it reach here?");
					elist.setSelected(bArrowStart);
					drawArrowLine = true;
				}
			}
			else
			{
				elist.setSelected(elist.getElementAt(eArrowStart.getKey()));//Start the arrow here
				drawArrowLine = true;
			}

		}

		if (rightPressed && !leftPressed) // Right click is for bonding
		{
			drawArrowLine = false;
			/* Cases to consider:
			 * 1. If user is already dragging (this should come first!)
			 * 1. Clicked on element
			 * 2. Clicked no empty grid space
			 */

			roundX = getGraphCoordinateX(e.getX());
			roundY = getGraphCoordinateY(e.getY());

			//				System.out.println("X: "+roundX);
			//				System.out.println("Y: "+roundY);

			if (drawBondLine == true) // if the element is already in the process of being bonded
			{
				repaint();
				return; // No need for further steps.
			}

			Element bondStart = elist.getElementAt(roundX, roundY);

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
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 8)
		{
			MoleculeComponent currentElement = elist.getSelected();
			if (currentElement != null)
			{
				drawBondLine = false;
				elist.removeSelected();
				//mmc.view.displayElementAttributes(null);

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
