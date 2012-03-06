package moleculeMaker;

import java.awt.Color;

public class Bond extends MoleculeConnectorComponent {

	/**
	 * Used to determine the bondOrder attribute in the XML file.
	 * Bond Order relates to how many electrons are shared in this
	 * bond.
	 */
	private int bondOrder;
	
	public Bond(MoleculeComponent c1, MoleculeComponent c2)
	{
		super();

//		System.out.println("Bond being created using: " + c1 + " and " + c2);

		if (c1 == null || c2 == null) {
			System.out.println("Bonder or bondee is null");
			return;
		}

		dragColor = Color.MAGENTA; // set the drag color for bonds
		setConnectionAttributes(c1, c2);
//		recalculateMiddleXY();
		
		bondOrder = 1; //Default to 1. If it exists, it has at least one electron shared.
	}
	
	public Bond(MoleculeComponent c1, MoleculeComponent c2, int bondOrder)
	{
		super();

//		System.out.println("Bond being created using: " + c1 + " and " + c2);

		if (c1 == null || c2 == null) {
			System.out.println("Bonder or bondee is null");
			return;
		}

		dragColor = Color.MAGENTA; // set the drag color for bonds
		setConnectionAttributes(c1, c2);
		
//		recalculateMiddleXY();
		setBondOrder(bondOrder);
	}

	public Color getColor() {
		if (bonding)
			return Color.BLUE; // Never called, I don't think.
		if (selected)
			return Color.GREEN;
		if (dragging)
			return Color.LIGHT_GRAY;
		
		return Color.BLACK;
	}

	public int getBondOrder() {
		return bondOrder;
	}

	public void setBondOrder(int bondOrder) {
		if(bondOrder >= 1 && bondOrder <= 3) //Check to see if it's valid input
		{
			this.bondOrder = bondOrder; //If so, set it.
		}
		else //If not...
		{
			this.bondOrder = 1; //Default to 1.
		}
	}

	
}
