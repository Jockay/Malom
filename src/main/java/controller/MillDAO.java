package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Coordinate;
import model.Game;
import model.Mill;

/**
 * Database services class.
 * 
 * @author Jockay
 *
 */
public class MillDAO {
	
	/**
	 * Uploads game state to database.
	 */
	public void uploadGameState(Game game) {
		final String INSERT = "INSERT INTO MALOMGAME VALUES (?, ?, ?, ?, ?, ?)";
		final String INSERTp1mills = "INSERT INTO MALOMP1MILLS VALUES (?, ?, ?, ?, ?, ?)";
		final String INSERTp2mills = "INSERT INTO MALOMP2MILLS VALUES (?, ?, ?, ?, ?, ?)";    
		PreparedStatement pstmtInsert;

		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@db.inf.unideb.hu:1521:ora11g",
					"H_HG8H7I", "kassai");
			pstmtInsert = conn.prepareStatement(INSERT);
			try {
				PreparedStatement delete = conn
						.prepareStatement("delete from malomgame");
				delete.executeUpdate();
				for (Coordinate c : game.getP1().getPlacedStones()) {
					pstmtInsert.setString(1, game.getP1().getName());
					int turn = game.getP1().isTurn() ? 1 : 0;
					pstmtInsert.setInt(2, turn);
					pstmtInsert.setInt(3, c.getX());
					pstmtInsert.setInt(4, c.getY());
					pstmtInsert.setInt(5, game.getP1().getActualStones());
					pstmtInsert.setInt(6, game.getP1().getStartStones());
					pstmtInsert.executeUpdate();
				}
				for (Coordinate c : game.getP2().getPlacedStones()) {
					pstmtInsert.setString(1, game.getP2().getName());
					int turn = game.getP2().isTurn() ? 1 : 0;
					pstmtInsert.setInt(2, turn);
					pstmtInsert.setInt(3, c.getX());
					pstmtInsert.setInt(4, c.getY());
					pstmtInsert.setInt(5, game.getP2().getActualStones());
					pstmtInsert.setInt(6, game.getP2().getStartStones());
					pstmtInsert.executeUpdate();
				}

				delete = conn.prepareStatement("delete from malomp1mills");
				delete.executeUpdate();
				pstmtInsert = conn.prepareStatement(INSERTp1mills);
				for (Mill m : game.getP1().getPlacedMills()) {
					pstmtInsert.setInt(1, m.getAX());
					pstmtInsert.setInt(2, m.getAY());
					pstmtInsert.setInt(3, m.getBX());
					pstmtInsert.setInt(4, m.getBY());
					pstmtInsert.setInt(5, m.getCX());
					pstmtInsert.setInt(6, m.getCY());
					pstmtInsert.executeUpdate();
				}

				delete = conn.prepareStatement("delete from malomp2mills");
				delete.executeUpdate();
				pstmtInsert = conn.prepareStatement(INSERTp2mills);
				for (Mill m : game.getP2().getPlacedMills()) {
					pstmtInsert.setInt(1, m.getAX());
					pstmtInsert.setInt(2, m.getAY());
					pstmtInsert.setInt(3, m.getBX());
					pstmtInsert.setInt(4, m.getBY());
					pstmtInsert.setInt(5, m.getCX());
					pstmtInsert.setInt(6, m.getCY());
					pstmtInsert.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Downloads player names from database.
	 * 
	 * @param game Game state.
	 */
	public void downloadNames(Game game) {
		final String SELECT = "SELECT * FROM MALOMNAME";
		final PreparedStatement pstmtSelect;

		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@db.inf.unideb.hu:1521:ora11g",
					"H_HG8H7I", "kassai");
			pstmtSelect = conn.prepareStatement(SELECT);
			try {
				ResultSet rs = pstmtSelect.executeQuery();
				rs.next();
				game.getP1().setName(rs.getString("name"));
				rs.next();
				game.getP2().setName(rs.getString("name"));
			} catch (SQLException e) {
				e.printStackTrace();
			}

			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Downloads saved game state from database.
	 * 
	 * @param game Game state.
	 */
	public void downloadGameState(Game game) {
		final String SELECTp1 = "SELECT * FROM MALOMGAME where name = '"
				+ game.getP1().getName() + "'";
		final String SELECTp2 = "SELECT * FROM MALOMGAME where name = '"
				+ game.getP2().getName() + "'";
		final String SELECTp1mills = "SELECT * FROM MALOMP1MILLS";
		final String SELECTp2mills = "SELECT * FROM MALOMP2MILLS";
		PreparedStatement pstmtSelect;
		ResultSet rs;

		game.newGame();
		
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@db.inf.unideb.hu:1521:ora11g",
					"H_HG8H7I", "kassai");
			try {
				pstmtSelect = conn.prepareStatement(SELECTp1);
				rs = pstmtSelect.executeQuery();
				while (rs.next()) {
					game.getP1().setName(rs.getString("name"));
					game.getP1().setTurn(rs.getInt("actual") == 0 ? false : true);
					game.getP1().setActualStones(rs.getInt("actualstones"));
					game.getP1().setStartStones(rs.getInt("startstones"));
					game.getP1().getPlacedStones().add(
							new Coordinate(rs.getInt("xcoordinate"), rs
									.getInt("ycoordinate")));
				}

				pstmtSelect = conn.prepareStatement(SELECTp2);
				rs = pstmtSelect.executeQuery();
				while (rs.next()) {
					game.getP2().setName(rs.getString("name"));
					game.getP2().setTurn(rs.getInt("actual") == 0 ? false : true);
					game.getP2().setActualStones(rs.getInt("actualstones"));
					game.getP2().setStartStones(rs.getInt("startstones"));
					game.getP2().getPlacedStones().add(
							new Coordinate(rs.getInt("xcoordinate"), rs
									.getInt("ycoordinate")));
				}

				pstmtSelect = conn.prepareStatement(SELECTp1mills);
				rs = pstmtSelect.executeQuery();
				while (rs.next()) {
					game.getP1().getPlacedMills().add(
							new Mill(new Coordinate(rs.getInt("axpoz"), rs
									.getInt("aypoz")), new Coordinate(rs
									.getInt("bxpoz"), rs.getInt("bypoz")),
									new Coordinate(rs.getInt("cxpoz"), rs
											.getInt("cypoz"))));
				}

				pstmtSelect = conn.prepareStatement(SELECTp2mills);
				rs = pstmtSelect.executeQuery();
				while (rs.next()) {
					game.getP2().getPlacedMills().add(
							new Mill(new Coordinate(rs.getInt("axpoz"), rs
									.getInt("aypoz")), new Coordinate(rs
									.getInt("bxpoz"), rs.getInt("bypoz")),               
									new Coordinate(rs.getInt("cxpoz"), rs
											.getInt("cypoz"))));
				}
				for (Coordinate c : game.getP1().getPlacedStones()) {
					game.setTableValue(c, 1);
				}

				for (Coordinate c : game.getP2().getPlacedStones()) {
					game.setTableValue(c, 2);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
