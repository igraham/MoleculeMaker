package moleculeMaker;

public class Element extends MoleculeComponent {
	
	// Element-only attributes
	private String name;
	private int electrons;
	private int charge;
	private int type;
	
	public Element(double x, double y)
	{
		super();
		
		this.x = x;
		this.y = y;
		selected = false;
		dragging = false;
		name = "C";
		electrons = 3;
		charge = 4;
		type = 0;
	}
	
	// ================ Getters and Setters Below ================
	@Override
	public String getKey()
	{
		return x + "," + y;	
	}
	
	public String toString()
	{
		return "Element at ("+ x + ", " + y + ")";	
	}

	@Override
	public double getX() {
		return x;
	}
	
	@Override
	public double getY() {
		return y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public int getElectrons() {
		return electrons;
	}

	public void setElectrons(int electrons) {
		this.electrons = electrons;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	// ===========================================================
	
}
