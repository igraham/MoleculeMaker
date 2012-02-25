package moleculeMaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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

	final int LEFT_CLICK = 1;
	final int RIGHT_CLICK = 3;

	int highlightRow = -1;
	int highlightColumn = -1;

	int mouseX = -1;
	int mouseY = -1;

	int roundX;
	int roundY;

	boolean leftPressed = false;
	boolean rightPressed = false;

	boolean drawBondLine = false; // whether a moving arrow should be drawn (for right-click bond link formation)
	boolean drawCurvedArrow = false;

	//		boolean movingElement;

	public MoleculeGrid(MMController mmc)
	{
		this.mmc = mmc;
		elist = new ConnectionList();
		//			movingElement = false; 
		this.addMouseMotionListener(this);
		this.addMouseListener(this);

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
		drawBondDrag(g);
		drawBonds(g);

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

	private void drawBondDrag(Graphics g)
	{	
		if (drawBondLine == true)
		{	
			g.setColor(Color.MAGENTA);

			Graphics2D g2d = (Graphics2D)g;
			g2d.setStroke(new BasicStroke(3));



			g2d.drawLine(elist.getSelected().getX() * GRID_SPACING + OBJECT_OFFSET,
					elist.getSelected().getY() * GRID_SPACING_Y + OBJECT_OFFSET_Y,
					roundX * GRID_SPACING + OBJECT_OFFSET, roundY * GRID_SPACING_Y + OBJECT_OFFSET_Y);
		}
	}

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

			if (eJustClicked != null) // If user clicks on an element (non-empty grid space)
			{
				// Cases to consider:
				//		1. Clicked unselected element -> selected that element
				//		2. Clicked selected element -> unselect it

				Element ePreviouslySelected = elist.getSelected(); // get selected element

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

				mmc.view.displayElementAttributes(eJustClicked);


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

					Element newElement = new Element(roundX, roundY); // Create new element

					elist.addElement(newElement); // add it to the list
					mmc.view.displayElementAttributes(newElement);

					elist.setSelected(elist.getElementAt(newElement.getKey())); // set it as selected
				}
				else // if there is something selected
				{
					System.out.println("but since there's a selected element ("+elist.getSelected()+"), unselecting it.");
					//elist.setSelected(null); // remove selection

					// duplicate code from above if-statement (remove soon):
					Element newElement = new Element(roundX, roundY); // Create new element
					elist.addElement(newElement); // add it to the list
					elist.setSelected(elist.getElementAt(newElement.getKey())); // set it as selected
					mmc.view.displayElementAttributes(newElement);
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

				Element bondee = elist.getElementAt(roundX, roundY);

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

					elist.addBond(elist.getSelected(), bondee);

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
			roundX = getGraphCoordinateX(e.getX());
			roundY = getGraphCoordinateY(e.getY());

			if(drawCurvedArrow)
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
					drawCurvedArrow = true;
				}
			}
			else
			{
				elist.setSelected(elist.getElementAt(eArrowStart.getKey()));//Start the arrow here
				drawCurvedArrow = true;
			}


		}

		if (rightPressed && !leftPressed) // Right click is for bonding
		{
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
			Element currentElement = elist.getSelected();
			if (currentElement != null)
			{
				drawBondLine = false;
				elist.removeSelectedElement();
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
