package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Game;

/**
 * Settings window frame class.
 * 
 * @author Jockay
 *
 */
public class Settings extends JFrame {

	/** Serial version identifier. */
	private static final long serialVersionUID = 5000018669398022716L;
	/** Contains elements of the frame. */
	private JPanel contentPane;
	/** Text field for player 1 name. */
	private JTextField txtPlayername;
	/** Text field for player 2 name. */
	private JTextField txtPlayername_1;
	/** Text field for button size of the game. */
	private JTextField buttonSizeField;
	/** Text label for player 1 name. */
	private JLabel lblPlayerName;
	/** Text label for player 2 name. */
	private JLabel lblPlayerName_1;
	/** Label to view player 1 stone color. */
	private JLabel lblP1Color;
	/** Label to view player 2 stone color. */
	private JLabel lblP2Color;
	/** 
	 * Warning for color switch (if necessary). 
	 * Color switch can be done before the game starts only. 
	 */
	private JLabel lblOnlyBeforeGame;
	
	/**
	 * Create the frame.
	 * 
	 * @param mgui The game's graphical user interface.
	 * @param g Game state.
	 */
	public Settings(final MalomGUI mgui, final Game g) {
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		//setLocation(190, 70);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(Color.BLACK);
		setContentPane(contentPane);

		txtPlayername = new JTextField();
		txtPlayername.setText(g.getP1().getName());
		txtPlayername.setBounds(124, 30, 114, 25);
		contentPane.add(txtPlayername);
		txtPlayername.setColumns(10);

		txtPlayername_1 = new JTextField();
		txtPlayername_1.setText(g.getP2().getName());
		txtPlayername_1.setBounds(124, 59, 114, 25);
		contentPane.add(txtPlayername_1);
		txtPlayername_1.setColumns(10);

		lblPlayerName = new JLabel("Player 1 name:");
		lblPlayerName.setForeground(Color.LIGHT_GRAY);
		lblPlayerName.setBounds(10, 30, 114, 15);
		contentPane.add(lblPlayerName);

		lblPlayerName_1 = new JLabel("Player 2 name:");
		lblPlayerName_1.setForeground(Color.LIGHT_GRAY);
		lblPlayerName_1.setBounds(10, 63, 114, 15);
		contentPane.add(lblPlayerName_1);

		lblP1Color = new JLabel(g.getP1().getColor());
		lblP1Color.setBounds(244, 30, 70, 15);
		lblP1Color.setForeground(Color.LIGHT_GRAY);
		contentPane.add(lblP1Color);

		lblP2Color = new JLabel(g.getP2().getColor());
		lblP2Color.setForeground(Color.LIGHT_GRAY);
		lblP2Color.setBounds(244, 61, 70, 15);
		contentPane.add(lblP2Color);

		JButton btnSwapColors = new JButton("Swap colors");
		btnSwapColors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(g.getP1().getStartStones() == 9 &&
						g.getP2().getStartStones() == 9) {
					g.swapColor();
					lblP1Color.setText(g.getP1().getColor());
					lblP2Color.setText(g.getP2().getColor());
				} else {
					lblOnlyBeforeGame.setText("Only before game starts!");
				}
			}
		});
		btnSwapColors.setBounds(308, 40, 128, 25);
		contentPane.add(btnSwapColors);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancel.setBounds(43, 224, 117, 25);
		contentPane.add(btnCancel);

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.getP1().setName(txtPlayername.getText());
				g.getP2().setName(txtPlayername_1.getText());
				mgui.buttonSize = Integer.parseInt(buttonSizeField.getText());
				mgui.refreshInterface();
				setVisible(false);
			}
		});
		btnOk.setBounds(268, 224, 117, 25);
		contentPane.add(btnOk);

		JLabel lblButtonSize = new JLabel("Table size:");
		lblButtonSize.setForeground(Color.LIGHT_GRAY);
		lblButtonSize.setBounds(12, 134, 89, 15);
		contentPane.add(lblButtonSize);

		buttonSizeField = new JTextField();
		buttonSizeField.setText(String.valueOf(mgui.buttonSize));
		buttonSizeField.setBounds(124, 129, 57, 25);
		buttonSizeField.setColumns(10);
		contentPane.add(buttonSizeField);
		
		lblOnlyBeforeGame = new JLabel("");
		lblOnlyBeforeGame.setForeground(Color.LIGHT_GRAY);
		lblOnlyBeforeGame.setBounds(254, 88, 182, 15);
		contentPane.add(lblOnlyBeforeGame);
		
		setResizable(false);
		setVisible(true);
	}
}
