package gui;

import static java.util.Arrays.asList;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import model.Coordinate;
import model.Game;
import model.Player;
import controller.MillDAO;

/**
 * The main class of the Malom game.
 * 
 * @version		1.0		14 jul 2014  
 * @author 		Jockay
 *
 */
public class MalomGUI extends JFrame {
	/** Size of the buttons on game the interface. */
	static int buttonSize = 40;
	/** Offset of the buttons from the edge of the window. */
	static int offset = 20;

	/**
	 * Inner class for a game button implementation.
	 * 
	 * @author jockay
	 * 
	 */
	public class MillButton extends JButton {
		/** UI identifier number. */
		private static final long serialVersionUID = -5887437505363709753L;
		/** Button coordinate on the game field. */
		private Coordinate coordinate;
		/** Determines if this button was clicked previously. */
		private boolean previousClicked = false;
		/** Game state. */
		public Game g;
		/** Object of database methods. */
		public MillDAO millDao;
		
		/**
		 * Create the button.
		 * 
		 * @param name
		 *            Text label of the button.
		 * @param c
		 *            Coordinate of the button.
		 * @param g
		 *            Game state.
		 * @throws IOException
		 *             If icon file can't be found.
		 */
		public MillButton(String name, final Coordinate c, final Game g)
				throws IOException {
			super(name);
			this.coordinate = c;
			this.g = g;
			this.millDao = new MillDAO();
			this.previousClicked = false;
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);

					/* Game is over */
					if (!g.isGameGoesOn()) {
						noteLabel.setText(g.notActualPlayer().getName()
								+ " won!");
						return;
					}

					/* Start phase and removal try from a mill. */
					if (g.isStartPhase()
							&& g.isPartOfPlacedMill(coordinate,
									g.notActualPlayer())) {
						noteLabel
								.setText("You can't remove from mill in the start phase");
						return;
					}

					/* Remove a stone from the other player. */
					if (g.actualPlayer().isRightToRemove()
							&& g.getTableValue(coordinate) != g.getSign()
							&& g.getTableValue(coordinate) != 0) {

						g.setTableValue(coordinate, 0);
						g.actualPlayer().removePlacedStone(coordinate);

						// delete contained mill
						for (int i = 0; i < g.notActualPlayer()
								.getPlacedMills().size(); i++) {
							if (g.notActualPlayer().getPlacedMills().get(i)
									.contains(coordinate)) {
								g.notActualPlayer().removeFromMyMills(
										g.notActualPlayer().getPlacedMills()
												.get(i));
								break;
							}
						}
						// delete contained mill

						g.actualPlayer().setRightToRemove(false);
						g.notActualPlayer().reduceActualStoneVal();

						g.switchActualPlayer();

						if ((g.getP1().getActualStones() < 3 || g.getP2()
								.getActualStones() < 3) && g.isMainPhase()) {
							g.setGameGoesOn(false);
						}
						clearPreviousClickedButton();

						getItself().setButtonIcon();
						refreshLabels();
						return;
					}

					/* Place a stone in start phase. */
					if (g.isStartPhase() && g.getTableValue(coordinate) == 0
							&& !g.actualPlayer().isRightToRemove()) {
						g.putStone(c);
						g.actualPlayer().addPlacedStone(coordinate);
						setButtonIcon();
						if (g.isThereNewMill() != 0) {
							if (g.isStartPhase()
									&& g.isOnlyPlacedMillsOnBoard(g
											.notActualPlayer())) {
								noteLabel.setText(g.notActualPlayer().getName()
										+ "'s ("
										+ g.notActualPlayer().getColor()
										+ ") turn ("
										+ g.notActualPlayer().getName()
										+ " has only mills placed)");
								g.actualPlayer().setRightToRemove(false);
								g.switchActualPlayer();
								return;
							}
							g.actualPlayer().setRightToRemove(true);
							// g.actualPlayer().printSavedMills();
						} else {
							g.switchActualPlayer(); // kov jatekos
						}
						refreshLabels();
						return;
					}

					/* Move stone in the main phase. */
					if (g.isMainPhase()) {
						if (getPreviousClickedButton() != null
								&& !getItself().equals(
										getPreviousClickedButton())) {
							if (g.moveStone(getPreviousClickedButton()
									.getCoordinate(), coordinate)) {
								getPreviousClickedButton().setButtonIcon();
								setButtonIcon();
							} else {
								return;
							}
							for (int i = 0; i < g.actualPlayer()
									.getPlacedMills().size(); i++) {
								if (!g.actualPlayer().getPlacedMills()
										.isEmpty()) {
									if (g.actualPlayer()
											.getPlacedMills()
											.get(i)
											.contains(
													getPreviousClickedButton()
															.getCoordinate())) {
										g.actualPlayer().removeFromMyMills(
												g.actualPlayer()
														.getPlacedMills()
														.get(i));
										break;
									}
								}
							}
							if (g.isThereNewMill() != 0) {
								g.actualPlayer().setRightToRemove(true);
								// g.actualPlayer().printSavedMills();
							} else {
								g.switchActualPlayer();
							}
							clearPreviousClickedButton();
							refreshLabels();
							return;
						}
						/* Double click on the same button. */
						else if (getItself().equals(getPreviousClickedButton())) {
							clearPreviousClickedButton();
							setButtonIcon();
						}
						/* Set this button as last clicked. */
						else if (g.getTableValue(coordinate) != 0
								&& g.getTableValue(coordinate) == g.getSign()) {
							previousClicked = true;
							setButtonIcon();
						}
					}
				}
			});

			setButtonIcon();
			this.setVisible(true);
			this.setEnabled(true);
		}

		/**
		 * Returns this button.
		 * 
		 * @return This button.
		 */
		public MillButton getItself() {
			return this;
		}

		/**
		 * Returns previous clicked game button.
		 * 
		 * @return Previous clicked game button.
		 */
		public MillButton getPreviousClickedButton() {
			for (MillButton millButton : buttons)
				if (millButton.isPreviousClicked())
					return millButton;
			return null;
		}

		/**
		 * Clears all button from clicked state.
		 */
		public void clearPreviousClickedButton() {
			for (MillButton millButton : buttons)
				if (millButton.isPreviousClicked())
					millButton.setPreviousClicked(false);
		}

		/**
		 * Sets this buttons's icon.
		 */
		public void setButtonIcon() {
			int tableValue = g.getTableValue(coordinate);
			if (tableValue == 0)
				this.setIcon(new ImageIcon(getClass().getClassLoader()
						.getResource("woodyform.png")));
			else if (tableValue == 1 && isPreviousClicked())
				this.setIcon(new ImageIcon(getClass().getClassLoader()
						.getResource("whiteStoneChosen.png")));
			else if (tableValue == 2 && isPreviousClicked())
				this.setIcon(new ImageIcon(getClass().getClassLoader()
						.getResource("blackStoneChosen.png")));
			else if (tableValue == 1)
				this.setIcon(new ImageIcon(getClass().getClassLoader()
						.getResource("whitestone.png")));
			else if (tableValue == 2)
				this.setIcon(new ImageIcon(getClass().getClassLoader()
						.getResource("blackstone.png")));
		}

		/**
		 * Sets all buttons icon.
		 */
		public void updateIcons() {
			for (MillButton millButton : buttons)
				millButton.setButtonIcon();
		}

		/**
		 * Returns the coordinate of this button.
		 * 
		 * @return Coordinate of this button.
		 */
		public Coordinate getCoordinate() {
			return this.coordinate;
		}

		/**
		 * Returns true if this button clicked previously.
		 * 
		 * @return True if this button clicked previously.
		 */
		public boolean isPreviousClicked() {
			return previousClicked;
		}

		/**
		 * Sets this button state if being clicked previously.
		 * 
		 * @param previousClicked
		 *            State of last clicked.
		 */
		public void setPreviousClicked(boolean previousClicked) {
			this.previousClicked = previousClicked;
		}

		/**
		 * Returns string representation of this object.
		 */
		@Override
		public String toString() {
			return "Millbutton[" + this.coordinate.getX() + ", "
					+ this.coordinate.getY() + "]";
		}
	}

	/**
	 * Update all label on the interface.
	 */
	public void refreshLabels() {
		if (g.isStartPhase()) {
			phaseLabel.setText("Start phase");
			startCounter1.setText("White: "
					+ String.valueOf(g.getP1().getStartStones()));
			startCounter2.setText("Black: "
					+ String.valueOf(g.getP2().getStartStones()));
		} else {
			phaseLabel.setText("");
			startCounter1.setText("");
			startCounter2.setText("");
		}

		if (!g.isGameGoesOn()) {
			phaseLabel.setText("Game Over");
			noteLabel.setText(g.notActualPlayer().getName() + " won!");
		} else if (g.actualPlayer().isRightToRemove())
			noteLabel.setText(g.actualPlayer().getName() + " ("
					+ g.actualPlayer().getColor() + ") has right to remove.");
		else
			noteLabel.setText(g.actualPlayer().getName() + "'s turn ("
					+ g.actualPlayer().getColor() + ")");
	}

	/**
	 * Updates all game button's icon on the interface.
	 */
	public void refreshButtons() {
		for (int i = 0; i < buttons.size(); i++)
			buttons.get(i).setButtonIcon();
	}

	/** User interface identifier. */
	private static final long serialVersionUID = -3739396616105612024L;
	/** Panel for the interface. */
	public JPanel contentPane;
	/** Menu bar object. */
	JMenuBar menuBar;
	/** Menu bar item to exit program. */
	JMenuItem mntmExit;
	/** Contains actual game status. */
	static Game g = new Game(new Player("Player-1", true), new Player(
			"Player-2", false));
	/** MillDAO object for database methods. */
	public MillDAO millDao;
	/** Loop variable. */
	int i;
	/** Static list that contains buttons of the user interface. */
	public static List<MillButton> buttons;
	/** Variable for notification label. */
	public static JLabel noteLabel;
	/** Variable for game phase notification. */
	public static JLabel phaseLabel;
	/** Variable for the first player's start stone number notification. */
	public static JLabel startCounter1;
	/** Variable for the second player's start stone number notification. */
	public static JLabel startCounter2;

	/**
	 * Refreshes game frame.
	 */
	public void refreshInterface() {
		for (int i = 0; i < buttons.size(); ++i)
			buttons.get(i).setBounds(0, 0, buttonSize, buttonSize);
		buttons.get(0).setLocation(offset, offset);
		buttons.get(1).setLocation((buttonSize * 6) + offset, offset);
		buttons.get(2).setLocation((buttonSize * 12) + offset, offset);
		buttons.get(3).setLocation((buttonSize * 2) + offset,
				(buttonSize * 2) + offset);
		buttons.get(4).setLocation((buttonSize * 6) + offset,
				(buttonSize * 2) + offset);
		buttons.get(5).setLocation((buttonSize * 10) + offset,
				(buttonSize * 2) + offset);
		buttons.get(6).setLocation((buttonSize * 4) + offset,
				(buttonSize * 4) + offset);
		buttons.get(7).setLocation((buttonSize * 6) + offset,
				(buttonSize * 4) + offset);
		buttons.get(8).setLocation((buttonSize) * 8 + offset,
				(buttonSize * 4) + offset);
		buttons.get(9).setLocation(offset, (buttonSize * 6) + offset);
		buttons.get(10).setLocation((buttonSize * 2) + offset,
				(buttonSize * 6) + offset);
		buttons.get(11).setLocation((buttonSize * 4) + offset,
				(buttonSize * 6) + offset);
		buttons.get(12).setLocation((buttonSize * 8) + offset,
				(buttonSize * 6) + offset);
		buttons.get(13).setLocation((buttonSize * 10) + offset,
				(buttonSize * 6) + offset);
		buttons.get(14).setLocation((buttonSize * 12) + offset,
				(buttonSize * 6) + offset);
		buttons.get(15).setLocation((buttonSize * 4) + offset,
				(buttonSize * 8) + offset);
		buttons.get(16).setLocation((buttonSize * 6) + offset,
				(buttonSize * 8) + offset);
		buttons.get(17).setLocation((buttonSize * 8) + offset,
				(buttonSize * 8) + offset);
		buttons.get(18).setLocation((buttonSize * 2) + offset,
				(buttonSize * 10) + offset);
		buttons.get(19).setLocation((buttonSize * 6) + offset,
				(buttonSize * 10) + offset);
		buttons.get(20).setLocation((buttonSize * 10) + offset,
				(buttonSize * 10) + offset);
		buttons.get(21).setLocation(offset, (buttonSize * 12) + offset);
		buttons.get(22).setLocation((buttonSize * 6) + offset,
				(buttonSize * 12) + offset);
		buttons.get(23).setLocation(buttonSize * 12 + offset,
				buttonSize * 12 + offset);

		noteLabel.setBounds(offset, 13 * buttonSize + buttonSize / 2 + offset,
				buttonSize * 13, offset);
		if (buttonSize > 40)
			phaseLabel.setBounds((buttonSize * 5 + offset) + buttonSize / 2
					+ buttonSize / 7, (buttonSize * 3 + offset) + buttonSize
					/ 3, buttonSize * 4 + offset, buttonSize * 6 + offset);
		else if (buttonSize <= 40)
			phaseLabel.setBounds((buttonSize * 5 + offset) + buttonSize / 2,
					(buttonSize * 3 + offset) + buttonSize / 3, buttonSize * 6
							+ offset, buttonSize * 6 + offset);
		startCounter1.setBounds(offset, buttonSize * 11, buttonSize * 4
				+ offset, buttonSize * 6 + offset);
		startCounter2.setBounds(offset * 2 + buttonSize * 8, buttonSize * 11,
				buttonSize * 4 + offset, buttonSize * 6 + offset);
		frame.setSize(buttonSize * 13 + (int) (offset * 2), buttonSize * 16);
		// startCounter1.getFont().deriveFont(50);
		contentPane.updateUI();
		refreshLabels();
	}
	
	/**
	 * Returns the game frame.
	 * 
	 * @return Game frame.
	 */
	public MalomGUI getGUI() {
		return this;
	}
	
	/**
	 * Create the frame.
	 */
	public MalomGUI() throws IOException {
		// g.getP2().setStartStones(g.getP2().getStartStones() - 1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, buttonSize * 13 + offset * 2,
				(buttonSize * 13 + offset * 2) + 80);
		setResizable(false);
		millDao = new MillDAO();

		/* JPanel */
		contentPane = new JPanel() {
			private static final long serialVersionUID = 8075507437741513501L;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.setPaintMode();
				// g.setColor(Color.RED);
				// g.setColor(Color.LIGHT_GRAY);
				g.setColor(Color.gray);
				double y;
				// elso sor
				y = 2;
				g.drawLine(offset + buttonSize,
						(int) (offset + buttonSize / y), offset + buttonSize
								* 6, (int) (offset + buttonSize / y));
				g.drawLine(offset + buttonSize * 7, (int) (offset + buttonSize
						/ y), offset + buttonSize * 12,
						(int) (offset + buttonSize / y));
				// masodik sor
				y = 2.5;
				g.drawLine(offset + buttonSize * 3, offset
						+ (int) (buttonSize * y), offset + buttonSize * 6,
						offset + (int) (buttonSize * y));
				g.drawLine(offset + buttonSize * 7, offset
						+ (int) (buttonSize * y), offset + buttonSize * 10,
						offset + (int) (buttonSize * y));
				// harmadik sor
				y = 4.5;
				g.drawLine(offset + buttonSize * 5, offset
						+ (int) (buttonSize * y), offset + buttonSize * 6,
						offset + (int) (buttonSize * y));
				g.drawLine(offset + buttonSize * 7, offset
						+ (int) (buttonSize * y), offset + buttonSize * 8,
						offset + (int) (buttonSize * y));
				// negyedik sor
				y = 6.5;
				g.drawLine(offset + buttonSize * 1, offset
						+ (int) (buttonSize * y), offset + buttonSize * 2,
						offset + (int) (buttonSize * y));
				g.drawLine(offset + buttonSize * 3, offset
						+ (int) (buttonSize * y), offset + buttonSize * 4,
						offset + (int) (buttonSize * y));
				g.drawLine(offset + buttonSize * 9, offset
						+ (int) (buttonSize * y), offset + buttonSize * 10,
						offset + (int) (buttonSize * y));
				g.drawLine(offset + buttonSize * 11, offset
						+ (int) (buttonSize * y), offset + buttonSize * 12,
						offset + (int) (buttonSize * y));
				// otodik sor
				y = 8.5;
				g.drawLine(offset + buttonSize * 5, offset
						+ (int) (buttonSize * y), offset + buttonSize * 6,
						offset + (int) (buttonSize * y));
				g.drawLine(offset + buttonSize * 7, offset
						+ (int) (buttonSize * y), offset + buttonSize * 8,
						offset + (int) (buttonSize * y));
				// hatodik sor
				y = 10.5;
				g.drawLine(offset + buttonSize * 3, offset
						+ (int) (buttonSize * y), offset + buttonSize * 6,
						offset + (int) (buttonSize * y));
				g.drawLine(offset + buttonSize * 7, offset
						+ (int) (buttonSize * y), offset + buttonSize * 10,
						offset + (int) (buttonSize * y));
				// hetedik sor
				y = 12.5;
				g.drawLine(offset + buttonSize,
						(int) (offset + buttonSize * y), offset + buttonSize
								* 6, (int) (offset + buttonSize * y));
				g.drawLine(offset + buttonSize * 7, (int) (offset + buttonSize
						* y), offset + buttonSize * 12,
						(int) (offset + buttonSize * y));

				// elso oszlop
				y = 2;
				g.drawLine((int) (offset + buttonSize / y),
						offset + buttonSize, (int) (offset + buttonSize / y),
						offset + buttonSize * 6);
				g.drawLine((int) (offset + buttonSize / y), offset + buttonSize
						* 7, (int) (offset + buttonSize / y), offset
						+ buttonSize * 12);
				// masodik oszlop
				y = 2.5;
				g.drawLine(offset + (int) (buttonSize * y), offset + buttonSize
						* 3, offset + (int) (buttonSize * y), offset
						+ buttonSize * 6);
				g.drawLine(offset + (int) (buttonSize * y), offset + buttonSize
						* 7, offset + (int) (buttonSize * y), offset
						+ buttonSize * 10);
				// harmadik oszlop
				y = 4.5;
				g.drawLine(offset + (int) (buttonSize * y), offset + buttonSize
						* 5, offset + (int) (buttonSize * y), offset
						+ buttonSize * 6);
				g.drawLine(offset + (int) (buttonSize * y), offset + buttonSize
						* 7, offset + (int) (buttonSize * y), offset
						+ buttonSize * 8);
				// negyedik oszlop
				y = 6.5;
				g.drawLine(offset + (int) (buttonSize * y), offset + buttonSize
						* 1, offset + (int) (buttonSize * y), offset
						+ buttonSize * 2);
				g.drawLine(offset + (int) (buttonSize * y), offset + buttonSize
						* 3, offset + (int) (buttonSize * y), offset
						+ buttonSize * 4);
				g.drawLine(offset + (int) (buttonSize * y), offset + buttonSize
						* 9, offset + (int) (buttonSize * y), offset
						+ buttonSize * 10);
				g.drawLine(offset + (int) (buttonSize * y), offset + buttonSize
						* 11, offset + (int) (buttonSize * y), offset
						+ buttonSize * 12);
				// otodik oszlop
				y = 8.5;
				g.drawLine(offset + (int) (buttonSize * y), offset + buttonSize
						* 5, offset + (int) (buttonSize * y), offset
						+ buttonSize * 6);
				g.drawLine(offset + (int) (buttonSize * y), offset + buttonSize
						* 7, offset + (int) (buttonSize * y), offset
						+ buttonSize * 8);
				// hatodik oszlop
				y = 10.5;
				g.drawLine(offset + (int) (buttonSize * y), offset + buttonSize
						* 3, offset + (int) (buttonSize * y), offset
						+ buttonSize * 6);
				g.drawLine(offset + (int) (buttonSize * y), offset + buttonSize
						* 7, offset + (int) (buttonSize * y), offset
						+ buttonSize * 10);
				// hetedik oszlop
				y = 12.5;
				g.drawLine((int) (offset + buttonSize * y),
						offset + buttonSize, (int) (offset + buttonSize * y),
						offset + buttonSize * 6);
				g.drawLine((int) (offset + buttonSize * y), offset + buttonSize
						* 7, (int) (offset + buttonSize * y), offset
						+ buttonSize * 12);
				/*
				 * g.drawRect(offset + (buttonSize / 2), offset + (buttonSize /
				 * 2), buttonSize * 12, buttonSize * 12);
				 * 
				 * g.drawRect(offset + (buttonSize / 2) + buttonSize * 2, offset
				 * + (buttonSize / 2) + buttonSize * 2, buttonSize * 8,
				 * buttonSize * 8);
				 * 
				 * g.drawRect(offset + (buttonSize / 2) + buttonSize * 4, offset
				 * + (buttonSize / 2) + buttonSize * 4, buttonSize * 4,
				 * buttonSize * 4);
				 * 
				 * g.drawLine(buttonSize * 6 + buttonSize / 2 + offset, offset +
				 * buttonSize / 2, buttonSize * 6 + buttonSize / 2 + offset,
				 * (buttonSize * 4) + buttonSize / 2 + offset); // fuggoleges //
				 * kozepen g.drawLine(buttonSize * 6 + buttonSize / 2 + offset,
				 * offset + buttonSize * 8 + buttonSize / 2, buttonSize * 6 +
				 * buttonSize / 2 + offset, (buttonSize * 12) + buttonSize / 2 +
				 * offset); // fuggoleges kozepen
				 * 
				 * g.drawLine(offset + buttonSize / 2, buttonSize * 6 +
				 * buttonSize / 2 + offset, offset + buttonSize / 2 + buttonSize
				 * * 4, buttonSize * 6 + buttonSize / 2 + offset); // vizszintes
				 * 
				 * g.drawLine(offset + buttonSize / 2 + buttonSize * 8,
				 * buttonSize 6 + buttonSize / 2 + offset, offset + buttonSize /
				 * 2 + buttonSize * 12, buttonSize * 6 + buttonSize / 2 +
				 * offset);
				 */
				g.dispose();
			}
			/* ***** */
		};

		contentPane.setEnabled(false);
		contentPane.setFocusable(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.BLACK);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		/* ******** */

		/* Menu */
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);

		JMenuItem mntmnewgame = new JMenuItem("New game");
		mntmnewgame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.newGame();
				refreshButtons();
				refreshLabels();
			}
		});
		mnGame.add(mntmnewgame);

		JMenuItem mntmloadnames = new JMenuItem("Load names from database");
		mntmloadnames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				millDao.downloadNames(g);
				refreshLabels();
			}
		});
		mnGame.add(mntmloadnames);

		JMenuItem mntmloadfromdb = new JMenuItem("Load state from database");
		mntmloadfromdb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				millDao.downloadGameState(g);
				refreshButtons();
				refreshLabels();
				for (int i = 0; i < buttons.size(); i++) {
					buttons.get(i).setPreviousClicked(false);
				}
			}
		});
		mnGame.add(mntmloadfromdb);

		JMenuItem mntmsavetodb = new JMenuItem("Save state to database");
		mntmsavetodb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				millDao.uploadGameState(g);
			}
		});
		mnGame.add(mntmsavetodb);

		JMenuItem settings = new JMenuItem("Settings");
		settings.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new Settings(getGUI(), g);
				refreshLabels();
			}
		});
		mnGame.add(settings);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnGame.add(mntmExit);

		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);

		JMenuItem mntmaboutgame = new JMenuItem("Game");
		mntmaboutgame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new About();
			}
		});
		mnAbout.add(mntmaboutgame);
		/* ***** */

		/* Buttons */
		buttons = asList(new MillButton("", new Coordinate(0, 0), g), // 1
				new MillButton("", new Coordinate(0, 3), g), // 2
				new MillButton("", new Coordinate(0, 6), g), // 3
				new MillButton("", new Coordinate(1, 1), g), // 4
				new MillButton("", new Coordinate(1, 3), g), // 5
				new MillButton("", new Coordinate(1, 5), g), // 6
				new MillButton("", new Coordinate(2, 2), g), // 7
				new MillButton("", new Coordinate(2, 3), g), // 8
				new MillButton("", new Coordinate(2, 4), g), // 9
				new MillButton("", new Coordinate(3, 0), g), // 10
				new MillButton("", new Coordinate(3, 1), g), // 11
				new MillButton("", new Coordinate(3, 2), g), // 12
				new MillButton("", new Coordinate(3, 4), g), // 13
				new MillButton("", new Coordinate(3, 5), g), // 14
				new MillButton("", new Coordinate(3, 6), g), // 15
				new MillButton("", new Coordinate(4, 2), g), // 16
				new MillButton("", new Coordinate(4, 3), g), // 17
				new MillButton("", new Coordinate(4, 4), g), // 18
				new MillButton("", new Coordinate(5, 1), g), // 19
				new MillButton("", new Coordinate(5, 3), g), // 20
				new MillButton("", new Coordinate(5, 5), g), // 21
				new MillButton("", new Coordinate(6, 0), g), // 22
				new MillButton("", new Coordinate(6, 3), g), // 23
				new MillButton("", new Coordinate(6, 6), g)  // 24
		);

		for (MillButton millButton : buttons)
			millButton.setBounds(0, 0, buttonSize, buttonSize);

		buttons.get(0).setLocation(offset, offset);
		buttons.get(1).setLocation((buttonSize * 6) + offset, offset);
		buttons.get(2).setLocation((buttonSize * 12) + offset, offset);
		buttons.get(3).setLocation((buttonSize * 2) + offset,
				(buttonSize * 2) + offset);
		buttons.get(4).setLocation((buttonSize * 6) + offset,
				(buttonSize * 2) + offset);
		buttons.get(5).setLocation((buttonSize * 10) + offset,
				(buttonSize * 2) + offset);
		buttons.get(6).setLocation((buttonSize * 4) + offset,
				(buttonSize * 4) + offset);
		buttons.get(7).setLocation((buttonSize * 6) + offset,
				(buttonSize * 4) + offset);
		buttons.get(8).setLocation((buttonSize) * 8 + offset,
				(buttonSize * 4) + offset);
		buttons.get(9).setLocation(offset, (buttonSize * 6) + offset);
		buttons.get(10).setLocation((buttonSize * 2) + offset,
				(buttonSize * 6) + offset);
		buttons.get(11).setLocation((buttonSize * 4) + offset,
				(buttonSize * 6) + offset);
		buttons.get(12).setLocation((buttonSize * 8) + offset,
				(buttonSize * 6) + offset);
		buttons.get(13).setLocation((buttonSize * 10) + offset,
				(buttonSize * 6) + offset);
		buttons.get(14).setLocation((buttonSize * 12) + offset,
				(buttonSize * 6) + offset);
		buttons.get(15).setLocation((buttonSize * 4) + offset,
				(buttonSize * 8) + offset);
		buttons.get(16).setLocation((buttonSize * 6) + offset,
				(buttonSize * 8) + offset);
		buttons.get(17).setLocation((buttonSize * 8) + offset,
				(buttonSize * 8) + offset);
		buttons.get(18).setLocation((buttonSize * 2) + offset,
				(buttonSize * 10) + offset);
		buttons.get(19).setLocation((buttonSize * 6) + offset,
				(buttonSize * 10) + offset);
		buttons.get(20).setLocation((buttonSize * 10) + offset,
				(buttonSize * 10) + offset);
		buttons.get(21).setLocation(offset, (buttonSize * 12) + offset);
		buttons.get(22).setLocation((buttonSize * 6) + offset,
				(buttonSize * 12) + offset);
		buttons.get(23).setLocation(buttonSize * 12 + offset,
				buttonSize * 12 + offset);

		for (int i = 0; i < buttons.size(); ++i) {
			setAlwaysOnTop(false);
			buttons.get(i).setOpaque(true);
			contentPane.add(buttons.get(i));
		}
		/* ********* */

		noteLabel = new JLabel(g.actualPlayer().getName() + "'s turn ("
				+ g.actualPlayer().getColor() + ")");
		noteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noteLabel.setForeground(Color.LIGHT_GRAY);
		noteLabel.setBounds(offset, 13 * buttonSize + buttonSize / 2 + offset,
				buttonSize * 13, offset);
		contentPane.add(noteLabel);

		phaseLabel = new JLabel("Start phase");
		phaseLabel.setForeground(Color.LIGHT_GRAY);

		if (buttonSize > 40)
			phaseLabel.setBounds((buttonSize * 5 + offset) + buttonSize / 2
					+ buttonSize / 7, (buttonSize * 3 + offset) + buttonSize
					/ 3, buttonSize * 4 + offset, buttonSize * 6 + offset);
		else if (buttonSize <= 40)
			phaseLabel.setBounds((buttonSize * 5 + offset) + buttonSize / 2,
					(buttonSize * 3 + offset) + buttonSize / 3, buttonSize * 6
							+ offset, buttonSize * 6 + offset);
		contentPane.add(phaseLabel);

		startCounter1 = new JLabel("White: " + g.getP1().getStartStones());
		startCounter1.setBounds(offset, buttonSize * 11, buttonSize * 4
				+ offset, buttonSize * 6 + offset);
		startCounter1.setForeground(Color.LIGHT_GRAY);
		startCounter1.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(startCounter1);

		startCounter2 = new JLabel("Black: " + g.getP2().getStartStones());
		startCounter2.setHorizontalAlignment(SwingConstants.RIGHT);
		startCounter2.setBounds(offset * 2 + buttonSize * 8, buttonSize * 11,
				buttonSize * 4 + offset, buttonSize * 6 + offset);
		startCounter2.setForeground(Color.LIGHT_GRAY);
		contentPane.add(startCounter2);
		refreshButtons();
	}

	/**
	 * Sets the user interface look and feel.
	 * 
	 * @param lafName
	 */
	public static void setLAF(String lafName) {
		try {
			LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
			for (LookAndFeelInfo laf : lafs) {
				if (lafName.equals(laf.getName())) {
					UIManager.setLookAndFeel(laf.getClassName());
				}
			}
		} catch (Exception e) {
			System.out.println("Error setting" + lafName + "LAF: " + e);
		}
	}
	
	/** Game frame. */
	static MalomGUI frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// setLAF("Nimbus");
					// setLAF("CDE/Motif");
					// frame.setSize(buttonSize * 13 + (int) (offset * 2.7),
					// buttonSize * 16 + offset / 2 );
					frame = new MalomGUI();
					frame.setLocation(150, 50);
					frame.setResizable(false);
					frame.setVisible(true);
					frame.setTitle("Malom");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
