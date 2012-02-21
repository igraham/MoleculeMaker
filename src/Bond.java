import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;


public class Bond //extends JButton //implements ActionListener
{	
	private Element bonder; //BONDER
	private Element bondee; //BONDEE
	private String bondKey; //BONDKEYS
	
	public Bond(Element bonder, Element bondee)
	{
		System.out.println("Bond being created using: " + bonder + " and " + bondee);
		
		if (bonder == null)
			System.out.println("Bonder is null");
		if (bondee == null)
			System.out.println("Bondee is null");
		
		this.bonder = bonder;
		this.bondee = bondee;
		
		
		bondKey = getBondKey(bonder, bondee); 
	}
	
	public static String getBondKey(Element bonder, Element bondee)
	{
		// When setting the key pairs, the first set of coordinates should
		// always be the lowest point (for consistency in comparing if a bond already exists)
		// I.e. So a bond at (0, 0) and (10, 10) returns the same as a bond from (10, 10) to (0, 0) 
		if(bonder.getX() == bondee.getX())
		{
//			if(bonder.getY() == bondee.getY()) // if X=X and Y=Y, they're the same! Don't bond!
			if(bonder.getY() > bondee.getY())
			{
				return bonder.getKey() + ";" + bondee.getKey();
			}
			else
			{
				return bondee.getKey() + ";" + bonder.getKey();
			}
		}
		else if(bonder.getX() < bondee.getX())
		{
			return bondee.getKey() + ";" + bonder.getKey();
		}
		
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
