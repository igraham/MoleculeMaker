import javax.swing.JFrame;


public class BonderTester extends JFrame
{
	public BonderTester()
	{	
		Element a = new Element(0, 0);
		Element b = new Element(40,20);
		
		Bond pair = new Bond(a, b);
		
//		System.out.println(pair.getPairKey());
		
//		setLayout(null);
//		pair.setBounds(pair.getBonder().getX(), pair.getBonder().getY(),
//				pair.getBondee().getX(), pair.getBondee().getY());
		
//		this.add(pair);
		createGUI();
	}
	
	private void createGUI()
	{
		this.setSize(300, 300);
		this.setVisible(true);
	}
	
	public static void main(String args[])
	{
		new BonderTester();
	}
}
