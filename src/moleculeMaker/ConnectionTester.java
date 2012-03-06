package moleculeMaker;

public class ConnectionTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MoleculeComponent a = new Element(10, 10);
		System.out.println("Created element at " + a.toString());
		System.out.println();
		
		MoleculeComponent b = new Element(20, 20);
		System.out.println("Created element at " + b.toString());
		System.out.println();
		
		MoleculeComponent bondAB = new Bond(a, b);
		System.out.println("Created bond at " + bondAB.toString());
		System.out.println("\tBond's getX(), getY() (aka 'midpoint') is: (" + bondAB.getX() + ", " + bondAB.getY() + ")");
		System.out.println("\tBond's x and y variables are: (" + bondAB.x + ", " + bondAB.y + ")");
	}

}
