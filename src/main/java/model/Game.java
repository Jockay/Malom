package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Game state class.
 * 
 * @author Jockay
 *
 */
public class Game {
	/** First player state. */
	private Player p1;
	/** Second player state. */
	private Player p2;
	/** Game table in 2d array. */
	private int[][] table;
	/** Gameplay state. */
	private boolean gameGoesOn;
	/** Placed mills on the table. */
	private List<Mill> mills;
	
	/**
	 * Game constructor.
	 * 
	 * @param p1 First player object.
	 * @param p2 Second player object.
	 */
	public Game(Player p1, Player p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.gameGoesOn = true;
		this.table = new int[][] { { 0, 0, 0, 0, 0, 0, 0 }, // 0
				{ 0, 0, 0, 0, 0, 0, 0 }, // 1
				{ 0, 0, 0, 0, 0, 0, 0 }, // 2
				{ 0, 0, 0, 0, 0, 0, 0 }, // 3
				{ 0, 0, 0, 0, 0, 0, 0 }, // 4
				{ 0, 0, 0, 0, 0, 0, 0 }, // 5
				{ 0, 0, 0, 0, 0, 0, 0 } }; // 6
			   // 0, 1, 2, 3, 4, 5, 6
		this.mills = new ArrayList<Mill>();
		// vizszintes
		mills.add(new Mill(new Coordinate(0, 0), new Coordinate(0, 3),
				new Coordinate(0, 6))); // 0
		mills.add(new Mill(new Coordinate(1, 1), new Coordinate(1, 3),
				new Coordinate(1, 5))); // 1
		mills.add(new Mill(new Coordinate(2, 2), new Coordinate(2, 3),
				new Coordinate(2, 4))); // 2
		mills.add(new Mill(new Coordinate(3, 0), new Coordinate(3, 1),
				new Coordinate(3, 2))); // 3
		mills.add(new Mill(new Coordinate(3, 4), new Coordinate(3, 5),
				new Coordinate(3, 6))); // 4
		mills.add(new Mill(new Coordinate(4, 2), new Coordinate(4, 3),
				new Coordinate(4, 4))); // 5
		mills.add(new Mill(new Coordinate(5, 1), new Coordinate(5, 3),
				new Coordinate(5, 5))); // 6
		mills.add(new Mill(new Coordinate(6, 0), new Coordinate(6, 3),
				new Coordinate(6, 6))); // 7
		// fuggoleges
		mills.add(new Mill(new Coordinate(0, 0), new Coordinate(3, 0),
				new Coordinate(6, 0))); // 8
		mills.add(new Mill(new Coordinate(1, 1), new Coordinate(3, 1),
				new Coordinate(5, 1))); // 9
		mills.add(new Mill(new Coordinate(2, 2), new Coordinate(3, 2),
				new Coordinate(4, 2))); // 10
		mills.add(new Mill(new Coordinate(0, 3), new Coordinate(1, 3),
				new Coordinate(2, 3))); // 11
		mills.add(new Mill(new Coordinate(4, 3), new Coordinate(5, 3),
				new Coordinate(6, 3))); // 12
		mills.add(new Mill(new Coordinate(2, 4), new Coordinate(3, 4),
				new Coordinate(4, 4))); // 13
		mills.add(new Mill(new Coordinate(1, 5), new Coordinate(3, 5),                     
				new Coordinate(5, 5))); // 14
		mills.add(new Mill(new Coordinate(0, 6), new Coordinate(3, 6),
				new Coordinate(6, 6))); // 15
	}
	
	/**
	 * Returns true if the given player has only mills placed on the table.
	 * 
	 * @param p Player to check placed mills.
	 * @return True if the player has only mills placed on the table. 
	 * 		   else returns false.
	 */
	public boolean isOnlyPlacedMillsOnBoard(Player p) {
		for(Coordinate c : p.getPlacedStones())
			if( !isPartOfPlacedMill(c, p))
				return false;
		return true;
	}
	
	/**
	 * Returns true if the specified coordinate is part of a specified player's
	 * placed mills on the game field.
	 * 
	 * @param cr Coordinate to check.
	 * @param p Player to check.
	 * @return True if the coordinate is part of a placed mill of the player.
	 */
	public boolean isPartOfPlacedMill(Coordinate cr, Player p) {
		for (Mill m : p.getPlacedMills()) {
			if (m.contains(cr))
				return true;
		}
		return false;
	}
	
	/**
	 * Checks the game field for new mill, and returns an identifier number 
	 * which is 0 if there is no new mill found, 1 if player 1 has new mill,
	 * 2 if player 2 has new mill. Also adds the found mill to the player's 
	 * mill list.
	 *  
	 * @return 0 if no new mill found, 1 if the first player has new mill, 
	 * 		   2 if second player has new mill.
	 */
	public int isThereNewMill() {
		int value = 0;

		for (Mill m : mills) {
			if (table[m.getAX()][m.getAY()] == 0
					|| table[m.getBX()][m.getBY()] == 0
					|| table[m.getCX()][m.getCY()] == 0) {
				value = 0;
				continue;
			} else if (table[m.getAX()][m.getAY()] == table[m.getBX()][m
					.getBY()]
					&& table[m.getAX()][m.getAY()] == table[m.getCX()][m
							.getCY()]
					&& table[m.getBX()][m.getBY()] == table[m.getCX()][m
							.getCY()]) {
				if (p1.getPlacedMills().contains(m) || p2.getPlacedMills().contains(m)) {
					continue;
				} else {
					value = table[m.getAX()][m.getAY()];
					/*System.out.println("Mill found: \n" + "(" + m.getAX()
							+ ", " + m.getAY() + ")" + " value: " + value
							+ "\n" + "(" + m.getBX() + ", " + m.getBY() + ")"
							+ " value: " + value + "\n" + "(" + m.getCX()
							+ ", " + m.getCY() + ")" + " value: " + value
							+ "\n");*/
					if (value == 1)
						p1.addMill(m);
					else if (value == 2)
						p2.addMill(m);
					return value;
				}
			}
		}
		return value;
	}
	
	/**
	 * Puts the sign of the actual player to a specified coordinate on 
	 * the game table.
	 * 
	 * @param c Coordinate to set on game table.
	 */
	public void putStone(Coordinate c) {
		if (this.table[c.getX()][c.getY()] == 0)
			if (actualPlayer().getStartStones() > 0) {
				int value = actualPlayer().isGameStarter() ? 1 : 2;
				this.table[c.getX()][c.getY()] = value;
				actualPlayer().removeStartStone();
				actualPlayer().increaseActualStoneVal();
			}
	}
	
	/**
	 * Checks two specified coordinate for valid movement.
	 * 
	 * @param a Coordinate to move from.
	 * @param b Coordinate to move for.
	 * @return True if the move is valid for the coordinates, else returns false.
	 */
	public boolean isMoveAble(Coordinate a, Coordinate b) {
		if ((getTableValue(a) != 0 && getTableValue(b) == 0)) {
			if (a.getX() == b.getX() || a.getY() == b.getY()) {
				if (a.getX() == b.getX()) {
					if ((a.getY() == 0 || a.getY() == 3 || a.getY() == 6)
							&& (Math.abs(a.getY() - b.getY()) == 3)) {
						return true;
					} else if ((a.getY() == 1 || a.getY() == 3 || a.getY() == 5)
							&& (Math.abs(a.getY() - b.getY()) == 2)) {
						return true;
					} else if ((Math.abs(a.getY() - b.getY()) == 1)) {
						return true;
					}
				} else if (a.getY() == b.getY()) {
					if ((a.getX() == 0 || a.getX() == 3 || a.getX() == 6)
							&& Math.abs(a.getX() - b.getX()) == 3) {
						return true;
					} else if ((a.getX() == 1 || a.getX() == 3 || a.getX() == 5)    
							&& (Math.abs(a.getX() - b.getX()) == 2)) {
						return true;
					} else if ((Math.abs(a.getX() - b.getX()) == 1)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * If the movement is valid, modifies two value of the game table. 
	 * On the second coordinate the table value sets to the value of 
	 * the first coordinate on the table. The first coordinate value 
	 * set to 0 at last.
	 * 
	 * @param a Coordinate to move from.
	 * @param b Coordinate to move for.
	 * @return True if the movement is valid and succeeded, 
	 * 		   else returns false.
	 */
	public boolean moveStone(Coordinate a, Coordinate b) {
		int actualStones = actualPlayer().getActualStones(); 
		if (actualStones > 3) {
			if (isMoveAble(a, b)) {
				setTableValue(b, getTableValue(a));
				setTableValue(a, 0);
				return true;
			} else {
				return false;
			}
		} else if (actualStones == 3) {
			setTableValue(b, getTableValue(a));
			setTableValue(a, 0);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Switches the actual player.
	 */
	public void switchActualPlayer() {
		if (p1.isTurn()) {
			p1.setTurn(false);
			p2.setTurn(true);
		} else {
			p1.setTurn(true);
			p2.setTurn(false);
		}
	}
	
	/**
	 * Returns the sign of the actual player.
	 * 
	 * @return 1 if the First player is actual, 2 if the second player.
	 */
	public int getSign() {
		return getP1().isTurn() == true ? 1 : 2;
	}
	
	/**
	 * Sets the game to end state.
	 */
	public void gameOver() {
		this.gameGoesOn = false;
	}
	
	/**
	 * Sets the game state of end.
	 * 
	 * @param gameGoesOn Determines if the game ended.
	 */
	public void setGameGoesOn(boolean gameGoesOn) {
		this.gameGoesOn = gameGoesOn;
	}

	/**
	 * Returns true if the game is not over.
	 * 
	 * @return True if the game is not over, else returns false.
	 */
	public boolean isGameGoesOn() {
		return this.gameGoesOn;
	}

	/**
	 * Returns the actual player.
	 * 
	 * @return Actual player.
	 */
	public Player actualPlayer() {
		return p1.isTurn() ? p1 : p2;
	}
	
	/**
	 * The other player beside the actual player.
	 * 
	 * @return Other player beside the actual player.
	 */
	public Player notActualPlayer() {
		return p1.isTurn() ? p2 : p1;
	}	
	
	/**
	 * Returns the game table value on a specified coordinate.
	 *  
	 * @param c Coordinate of the table value to get.
	 * @return Table value on the specified coordinate.
	 */
	public int getTableValue(Coordinate c) {
		return table[c.getX()][c.getY()];
	}
	
	/**
	 * Sets the table on a coordinate to the specified value.
	 * 
	 * @param c Coordinate of the table value to set.
	 * @param value Value to set.
	 */
	public void setTableValue(Coordinate c, int value) {
		table[c.getX()][c.getY()] = value;
	}

/*	public void printTable() {
		for (int i = 0; i < 7; ++i) {
			System.out.print("{ ");

			for (int j = 0; j < 7; ++j)
				if (j < 12)
					System.out.print(table[i][j] + ", ");
				else
					System.out.print(table[i][j] + " ");
			if (i < 12)
				System.out.println("},");
			else
				System.out.println("}");
		}
		System.out.println();
	}*/
	
	/**
	 * Sets the game table to a specified table.
	 * 
	 * @param t Game table to set.
	 */
	public void setTable(int[][] t) {
		this.table = t;
	}
	
	/**
	 * Returns true if he game is in start phase.
	 * 
	 * @return True if the game is in start phase, else returns false.
	 */
	public boolean isStartPhase() {
		if (p1.getStartStones() + p2.getStartStones() > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Returns true if the game is in the main phase (after start phase).
	 * 
	 * @return True if the game is in main phase, else returns false.
	 */
	public boolean isMainPhase() {
		if ((p1.getStartStones() + p2.getStartStones()) == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Returns the first player object.
	 * 
	 * @return The first player.
	 */
	public Player getP1() {
		return p1;
	}
	
	/**
	 * Returns the second player object.
	 * 
	 * @return The second player.
	 */
	public Player getP2() {
		return p2;
	}
	
	/**
	 * Swaps the color of the players.
	 */
	public void swapColor() {
		if(p1.getColor() == "White" )
			p1.setColor("Black");
		else
			p1.setColor("White");
		
		if(p2.getColor() == "Black" )
			p2.setColor("White");
		else
			p2.setColor("Black");
	}
	
	public void setP1(Player p) {
		this.p1 = p;
	}
	
	/**
	 * Sets the game to start state.
	 */
	public void newGame() {
		this.p1 = new Player("Player 1", true);
		this.p2 = new Player("Player 1", false);
		/*getP1().placedMills.clear();
		getP1().placedStones.clear();
		getP1().setTurn(true);
		getP1().setRightToRemove(false);
		getP1().setStartStones(9);
		getP1().setActualStones(0);
		
		getP2().placedMills.clear();
		getP2().placedStones.clear();
		getP2().setTurn(true);
		getP2().setRightToRemove(false);
		getP2().setStartStones(9);
		getP2().setActualStones(0);*/

		setGameGoesOn(true);

		int[][] Table = new int[][] { { 0, 0, 0, 0, 0, 0, 0 }, // 0
				{ 0, 0, 0, 0, 0, 0, 0 }, // 1
				{ 0, 0, 0, 0, 0, 0, 0 }, // 2
				{ 0, 0, 0, 0, 0, 0, 0 }, // 3
				{ 0, 0, 0, 0, 0, 0, 0 }, // 4
				{ 0, 0, 0, 0, 0, 0, 0 }, // 5
				{ 0, 0, 0, 0, 0, 0, 0 } }; // 6
		setTable(Table);
	}
}

