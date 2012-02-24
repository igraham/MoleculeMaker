package moleculeMaker;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class DrawAnArrow {

	
	public DrawAnArrow()
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(240,240);
		frame.getContentPane().add(new Arrow());
		frame.setVisible(true);
	}
	class Arrow extends JPanel
	{
		double angle;
		int barbLength;
		
		public Arrow()
		{
			angle = Math.toRadians(35);
			barbLength = 10;
		}
		
		public void paint(Graphics g)
		{
			super.paint(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
								RenderingHints.VALUE_ANTIALIAS_ON);
			int width = 50;
			int height = 40;
			Point sw = new Point(0,0);
			Point ne = new Point(width, height);
			g2.draw(new Line2D.Double(sw, ne));
			drawBarbs(g2, ne, sw);
		}
		
		private void drawBarbs(Graphics2D g2, Point tip, Point tail)
		{
			double dy = tip.y - tail.y;
			double dx = tip.x - tail.x;
			System.out.println("Change in Y: "+dy);
			System.out.println("Change in X: "+dx);
			double theta = Math.atan2(dy, dx);
			System.out.println("Angle of Theta: "+theta);
			double x, y, rho = theta + angle;
			for(int i = 0; i < 2; i++)
			{
				System.out.println("Value of angle Rho: "+Math.toDegrees(rho));
				x = tip.x - barbLength * Math.cos(rho);
				y = tip.y - barbLength * Math.sin(rho);
				g2.draw(new Line2D.Double(tip.x, tip.y, x, y));
				rho = theta - angle;
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DrawAnArrow();

	}

}
