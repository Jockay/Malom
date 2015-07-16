package test;

import model.Coordinate;
import model.Game;
import model.Mill;
import model.Player;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
	Game g;
	int[][] testTable;
	
	@Test
	public void testIsNewMill() {
		g = new Game(new Player("Player 1", true), new Player("Player 2", false));
		
		testTable = new int[][] {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 1, 0, 1, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 }
		};
		g.setTable(testTable);
		assertEquals(1, g.isThereNewMill());
		
		testTable = new int[][] {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 1, 1, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 }
		};
		g.setTable(testTable);
		assertEquals(1, g.isThereNewMill());
		
		testTable = new int[][] {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 }
		};
		g.setTable(testTable);
		assertEquals(1, g.isThereNewMill());
		
		testTable = new int[][] {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 1, 1, 1 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 }
		};
		g.setTable(testTable);
		assertEquals(1, g.isThereNewMill());
		
		testTable = new int[][] {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 1, 1, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 }
		};
		g.setTable(testTable);
		assertEquals(1, g.isThereNewMill());
		
		testTable = new int[][] {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 1, 0, 1, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 }
		};
		g.setTable(testTable);
		assertEquals(1, g.isThereNewMill());
		
		testTable = new int[][] {
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 2, 0, 0, 2, 0, 0, 2 }
		};
		g.setTable(testTable);
		assertEquals(2, g.isThereNewMill());
		
		testTable = new int[][] {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 1, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 1, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 1, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 }
		};
		g.setTable(testTable);
		assertEquals(1, g.isThereNewMill());
		
		testTable = new int[][] {
				{ 0, 0, 0, 0, 0, 0, 2 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 2 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 2 }
		};
		g.setTable(testTable);
		assertEquals(2, g.isThereNewMill());
		
		// invalid states
		testTable = new int[][] {
				{ 0, 0, 0, 1, 1, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 }
		};
		g.setTable(testTable);
		assertEquals(0, g.isThereNewMill());
		
		testTable = new int[][] {
				{ 1, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 }
		};
		g.setTable(testTable);
		assertEquals(0, g.isThereNewMill());
		
		testTable = new int[][] {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 1 }
		};
		g.setTable(testTable);
		assertEquals(0, g.isThereNewMill());
		
		testTable = new int[][] {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 1, 0, 0, 0, 0, 0, 0 }
		};
		g.setTable(testTable);
		assertEquals(0, g.isThereNewMill());
		
		testTable = new int[][] {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 1 }
		};
		g.setTable(testTable);
		assertEquals(0, g.isThereNewMill());
		
		testTable = new int[][] {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 1 }, 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0 }
		};
		g.setTable(testTable);
		assertEquals(0, g.isThereNewMill());
		
		testTable = new int[][] {
				{0, 0, 0, 0, 0, 0, 0, },
				{0, 0, 0, 0, 0, 0, 0, },
				{0, 0, 0, 1, 2, 0, 0, },
				{0, 0, 1, 0, 2, 0, 1, },
				{0, 0, 2, 1, 2, 0, 0, },
				{0, 0, 0, 0, 0, 0, 0, },
				{0, 0, 0, 0, 0, 0, 0, }
		};
		g.getP2().addMill(new Mill(new Coordinate(2, 4), new Coordinate(3, 4), new Coordinate(4, 4)));
		g.setTable(testTable);
		assertEquals(0, g.isThereNewMill());
	}
}
