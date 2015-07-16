package model;

/**
 * Class for a mill on the game table.
 * 
 * @author Jockay
 *
 */
public class Mill {
	/** Coordinate of the first sign in the mill. */
	private Coordinate a;
	/** Coordinate of the second sign in the mill. */
	private Coordinate b;
	/** Coordinate of the third sign in the mill. */
	private Coordinate c;

	/**
	 * Class constructor.
	 * 
	 * @param a Coordinate of the first sign in the mill.
	 * @param b Coordinate of the second sign in the mill.
	 * @param c Coordinate of the third sign in the mill.
	 */
	public Mill(Coordinate a, Coordinate b, Coordinate c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	/**
	 * Returns the x parameter of the first coordinate in the mill.
	 * 
	 * @return The x parameter of the first coordinate in the mill.
	 */
	public int getAX() {
		return a.getX();
	}

	/**
	 * Returns the y parameter of the first coordinate in the mill.
	 * 
	 * @return The y parameter of the first coordinate in the mill.
	 */
	public int getAY() {
		return a.getY();
	}
	
	/**
	 * Returns the x parameter of the second coordinate in the mill.
	 * 
	 * @return The x parameter of the second coordinate in the mill.
	 */
	public int getBX() {
		return b.getX();
	}
	
	/**
	 * Returns the y parameter of the first coordinate in the mill.
	 * 
	 * @return The y parameter of the first coordinate in the mill.
	 */
	public int getBY() {
		return b.getY();
	}

	/**
	 * Returns the x parameter of the third coordinate in the mill.
	 * 
	 * @return The x parameter of the third coordinate in the mill.
	 */
	public int getCX() {
		return c.getX();
	}
	
	/**
	 * Returns the y parameter of the third coordinate in the mill.
	 * 
	 * @return The y parameter of the third coordinate in the mill.
	 */
	public int getCY() {
		return c.getY();
	}
	
	/**
	 * Returns the first coordinate in the mill.
	 * 
	 * @return The first coordinate in the mill.
	 */
	public Coordinate getA() {
		return a;
	}
	
	/**
	 * Returns the second coordinate in the mill.
	 * 
	 * @return The second coordinate in the mill.
	 */
	public Coordinate getB() {
		return b;
	}
	
	/**
	 * Returns the third coordinate in the mill.
	 * 
	 * @return The third coordinate in the mill.
	 */
	public Coordinate getC() {
		return c;
	}
	
	/**
	 * Sets the first coordinate in the mill.
	 * 
	 * @param a First coordinate in the mill.
	 */
	public void setA(Coordinate a) {
		this.a = a;
	}
	
	/**
	 * Sets the second coordinate in the mill.
	 * 
	 * @param a The second coordinate in the mill.
	 */
	public void setB(Coordinate b) {
		this.b = b;
	}
	
	/**
	 * Sets the third coordinate in the mill.
	 * 
	 * @param a The third coordinate in the mill.
	 */
	public void setC(Coordinate c) {
		this.c = c;
	}
	
	/**
	 * Returns true if the given object is equal the this object.
	 * 
	 * @return True if the given object equals to this object.
	 */
	@Override
	public boolean equals(Object obj) {		
		if(obj == null || !(obj instanceof Mill))
			return false;
		Mill m = (Mill) obj;
		return this.a.equals(m.getA()) && this.b.equals(m.getB()) && this.c.equals(m.getC());
	}
	
	/**
	 * String representation of the object.
	 * 
	 * @return String representation of the object.
	 */
	@Override
	public String toString() {
		return "(" + this.getAX() + ", " + this.getAY() + ")\n"  +
				"(" + this.getBX() + ", " + this.getBY() + ")\n"  +	
				"(" + this.getCX() + ", " + this.getCY() + ")\n";
	}
	
	/**
	 * Returns true if the given coordinate is a part of this mill.
	 * 
	 * @param coor Coordinate to check.
	 * @return True if the given coordinate is a part of this mill, 
	 * 		   else returns false.
	 */
	public boolean contains(Coordinate coor) {
		return (getAX() == coor.getX() && getAY() == coor.getY()) || 
			   (getBX() == coor.getX() && getBY() == coor.getY()) || 
			   (getCX() == coor.getX() && getCY() == coor.getY());
	}
	
}
