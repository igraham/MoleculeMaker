package moleculeMaker;

import java.awt.Color;

public class Bond extends MoleculeConnectorComponent {

	public Bond(MoleculeComponent c1, MoleculeComponent c2)
	{
		super();

		System.out.println("Bond being created using: " + c1 + " and " + c2);

		if (c1 == null || c2 == null) {
			System.out.println("Bonder or bondee is null");
			return;
		}

		dragColor = Color.MAGENTA; // set the drag color for bonds
		setConnectionAttributes(c1, c2);
		recalculateMiddleXY();
	}


}
