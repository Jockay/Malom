package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Player state class.
 * 
 * @author Jockay
 *
 */
public class Player {
	/** Name of the player. **/
	private String name;
	/** Number of actual stones on the table. */
	private int actualStones;
	/**
	 *  Number of starter stones in the start phase. 
	 *  (Not placed on the table.)
	 */
	private int startStones;
	/** Decides if the player is actual. **/
	private boolean turn;
	/** Color of the player' stones. */
	private String color;
	/** Decides if the player is the game starter. */
	private boolean gameStarter;
	/** 
	 * Decides if the player has right to remove from the placed stones of the 
	 * opponent player. 
	 */
	private boolean rightToRemove;
	/** List of the player's placed stones. */
	List<Coordinate> placedStones;
	/** List of the player's placed mills. */
	List<Mill> placedMills;
	
	/** 
	 * Class constructor.
	 * 
	 * @param name Name of the player.
	 * @param isStarter Boolean variable for game starter state.
	 */
	public Player(String name, boolean isStarter) {
		super();
		this.name = name;
		this.actualStones = 0;
		this.startStones = 9;
		this.turn = isStarter;
		this.color = (isStarter == true) ? "White" : "Black";
		this.gameStarter = isStarter;
		this.rightToRemove = false;
		this.placedStones = new ArrayList<Coordinate>();
		this.placedMills = new ArrayList<Mill>();
	}

	/**
	 * Returns the number of the starter stones.
	 * 
	 * @return Number of the starter stones.
	 */
	public int getStartStones() {
		return startStones;
	}
	
	/**
	 * Sets the number of starter stones.
	 * 
	 * @param startStones Number of starter stones.
	 */
	public void setStartStones(int startStones) {
		this.startStones = startStones;
	}
	
	/**
	 * Returns true if the player has right to remove from the opponent 
	 * player's placed stones.
	 * 
	 * @return True if the player has right to remove from the opponent 
	 * player's placed stones. Else returns false.
	 */
	public boolean isRightToRemove() {
		return rightToRemove;
	}
	
	/**
	 * Sets the right to remove.
	 * 
	 * @param rightToRemove Boolean variable of right to remove.
	 */
	public void setRightToRemove(boolean rightToRemove) {
		this.rightToRemove = rightToRemove;
	}

	/**
	 * Returns a list of coordinates about placed stones on the game table.
	 * 
	 * @return List of coordinate about the placed stones on the game table.
	 */
	public List<Coordinate> getPlacedStones() {
		return placedStones;
	}

	/**
	 * Sets the list of coordinate about the player's placed stones on the 
	 * game table.
	 * 
	 * @param placedStones Coordinate list to set.
	 */
	public void setPlacedStones(List<Coordinate> placedStones) {
		this.placedStones = placedStones;
	}
	
	/**
	 * Sets a list about the player's placed mills on the game table.
	 * @param myMills
	 */
	public void setPlacedMills(List<Mill> myMills) {
		this.placedMills = myMills;
	}
	
	/**
	 * Returns the name of the player.
	 * 
	 * @return Name of the player.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the name of the player.
	 * 
	 * @param name Name of the player.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the number of the player's placed stones on the game table.
	 * 
	 * @return Number of the player's actual stones placed on the game table.
	 */
	public int getActualStones() {
		return actualStones;
	}

	/**
	 * Sets the number of the player's actual stones placed on the game table.
	 * 
	 * @param actualStones Number of the player's actual stones placed on the 
	 * 		  game table.
	 */
	public void setActualStones(int actualStones) {
		this.actualStones = actualStones;
	}
	
	/**
	 * Returns true if the player is actual.
	 * 
	 * @return True if the player is actual, else returns false.
	 */
	public boolean isTurn() {
		return turn;
	}
	
	/**
	 * Sets the player to actual or not.
	 * 
	 * @param isTurn Boolean variable to set the player to actual or not.
	 */
	public void setTurn(boolean isTurn) {
		this.turn = isTurn;
	}

	/**
	 * Returns the color of the player's stones.
	 * 
	 * @return Color of the player's stones.
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Sets the color of the player's stones.
	 * 
	 * @param color The color of the player's stones.
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * Returns a list of the player's mills placed on the game table.
	 * 
	 * @return A list of the player's mills placed on the game table.
	 */
	public List<Mill> getPlacedMills() {
		return this.placedMills;
	}		
	
	/**
	 * Returns true if the player is game starter.
	 * 
	 * @return True if the player is game starter, else returns false.
	 */
	public boolean isGameStarter() {
		return gameStarter;
	}

	/**
	 * Sets the player to game starter or not.
	 * 
	 * @param gameStarter Boolean variable to set the player to game starter 
	 * 		  or not. 
	 */
	public void setGameStarter(boolean gameStarter) {
		this.gameStarter = gameStarter;
	}
	
	/**
	 * Adds a mill to the list of the player's placed mills on the game table.
	 * 
	 * @param m Mill on the game table.
	 */
	public void addMill(Mill m ) {
		getPlacedMills().add(m);
	}
	
	/**
	 * Removed a mill from the player's placed mills on the game table.
	 * 
	 * @param m
	 */
	public void removeMill(Mill m) {
		getPlacedMills().remove(m);
	}
	
	/**
	 * Removes a mill from the player's mill list.
	 * 
	 * @param m Mill to remove.
	 */
	public void removeFromMyMills(Mill m) {
		while(getPlacedMills().contains(m)) {
			getPlacedMills().remove(m);
			/*System.out.println("Mill removed: \n" + 
					"(" + m.getAX() + ", " + m.getAY() + ")" + " (" + this.color + ")\n" +
					"(" + m.getBX() + ", " + m.getBY() + ")" + " (" + this.color + ")\n" +	
					"(" + m.getCX() + ", " + m.getCY() + ")" + " (" + this.color + ")\n" );*/
		}
	}
	
	/**
	 * Adds a coordinate to the player's list of placed stones.
	 * 
	 * @param c Coordinate to add.
	 */
	public void addPlacedStone(Coordinate c) {
		getPlacedStones().add(c);
	}
	
	/**
	 * Removes a coordinate to the player's list of placed stones.
	 * 
	 * @param c Coordinate to remove.
	 */
	public void removePlacedStone(Coordinate c) {
		getPlacedMills().remove(c);
	}
	
	/**
	 * Increases the number of the players's actual stones by one.
	 */
	public void increaseActualStoneVal() {
		setActualStones(getActualStones() + 1);
	}
	
	/**
	 * Reduces the number of the players's actual stones by one.
	 */
	public void reduceActualStoneVal() {
		setActualStones(getActualStones() - 1);
	}
	
	/**
	 * Reduces the number of the players's starter stones by one.
	 */
	public void removeStartStone() {
		setStartStones(getStartStones() - 1);
	}
	
	/**
	 * String representation of the object.
	 * 
	 * @return String representation of the object.
	 */
	@Override
	public String toString() {
		return "Player [name=" + name + ", actualStones=" + actualStones
				+ ", startStones=" + startStones
				+ ", isTurn=" + turn + ", color=" + color
				+ "]";
	}
	
	/*public void printSavedMills() {
		for(Mill m : this.placedMills)
			System.out.println(m);
	}*/
}
