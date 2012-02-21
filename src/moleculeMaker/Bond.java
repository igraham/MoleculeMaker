package moleculeMaker;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Bond //extends JButton //implements ActionListener
{	
	// This is the most important comment of all.
	private Element bonder; //BONDER
	private Element bondee; //BONDEE
	private String bondKey; //BONDKEYS
	private int middleX;
	private int middleY;
	private int direction; // 0 horizontal or /; 1 vertical or \
	
	public Bond(Element bonder, Element bondee)
	{
		System.out.println("Bond being created using: " + bonder + " and " + bondee);
		
		if (bonder == null)
			System.out.println("Bonder is null");
		if (bondee == null)
			System.out.println("Bondee is null");
		
		// Determine which element the bonder, and which is the bondee
		// Hint: the bonder has the lowest (X, Y)
		if(bonder.getX() == bondee.getX()) // if line is vertical
		{
			direction = 0;
//			if(bonder.getY() == bondee.getY()) // if X=X and Y=Y, they're the same! Don't bond!
			if(bonder.getY() < bondee.getY())
			{
				this.bonder = bonder;
				this.bondee = bondee;
			}
			else
			{
				this.bonder = bondee;
				this.bondee = bonder;
			}
		}
		else if(bonder.getX() < bondee.getX()) // line looks like this: \
		{
			direction = 1; // line looks like: \
			this.bonder = bonder;
			this.bondee = bondee;
		}
		else // line looks like this: /
		{
			direction = 0;
			this.bonder = bondee;
			this.bondee = bonder;
		}
		
		if(bonder.getY() == bondee.getY())
		{
			direction = 1;
		}
		
		
		bondKey = getBondKey(bonder, bondee);
		
		middleX = Math.abs(bonder.getX() - bondee.getX()) / 2;
		middleY = Math.abs(bonder.getY() - bondee.getY()) / 2;
		
		System.out.println("middleX: " + middleX);
		System.out.println("middleY: " + middleY );
	}
	
	public static String getBondKey(Element bonder, Element bondee)
	{		
		return bonder.getKey() + ";" + bondee.getKey();
	}
	
	public void draw(Graphics g)
	{
		draw(g, 0);
	}
	
	public void draw(Graphics g, int offset)
	{
		g.setColor(Color.BLACK);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(3));
		
		g2d.drawLine(bonder.getX() + offset, bonder.getY() + offset,
				bondee.getX() + offset, bondee.getY() + offset);
		
		if(direction == 0)
		{
			g.drawOval(bonder.getX() + middleX, bonder.getY() + middleY, MoleculeGrid.GRID_SPACING/2, MoleculeGrid.GRID_SPACING/2);
		}
		else
		{
			g.drawOval(bonder.getX() + middleX, bonder.getY() - middleY, MoleculeGrid.GRID_SPACING/2, MoleculeGrid.GRID_SPACING/2);
		}
		
	}
	
	public Element getBonder()
	{
		return bonder;
	}
	
	public Element getBondee()
	{
		return bondee;
	}
	
	public String toString()
	{
		return "(" + bonder.getX() + ", " + bonder.getY() + ") --> ("
				+ bondee.getX() + ", " + bondee.getY() + ")";
	}
	
	public String getBondKey()
	{
		return bondKey;
	}
	
}
