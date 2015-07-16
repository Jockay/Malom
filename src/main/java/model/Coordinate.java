package model;

/**
 * Game table coordinate class.
 * 
 * @author Jockay
 *
 */
public class Coordinate {
	/**
	 * The x parameter of the coordinate.
	 */
	private int x;
	/**
	 * The y parameter of the coordinate.
	 */
	private int y;
	
	/**
	 * Returns x parameter of the coordinate.
	 * 
	 * @return x parameter of the coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Sets x parameter of the coordinate.
	 * 
	 * @param x The x parameter of the coordinate.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Returns y parameter of the coordinate.
	 * 
	 * @return y parameter of the coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Sets the y parameter of the coordinate.
	 * 
	 * @param y The y parameter of the coordinate.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	 /**
	  * Coordinate constructor.
	  * 
	  * @param x The x parameter of the coordinate.
	  * @param y The y parameter of the coordinate.
	  */
	public Coordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
                      
	/**
	 * Prints the object's state.
	 */
	@Override
	public String toString() {
		return "Coordinate [x=" + x + ", y=" + y + "]";
	}
	
	/**
	 * Decides if the parameter object equals to this object.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Coordinate))
				return false;
		Coordinate c = (Coordinate) obj;
		return (this.x == c.getX() && this.y == c.getY());
	}
	
}
