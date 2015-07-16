package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * About window frame class.
 * 
 * @author Jockay
 *
 */
public class About extends JFrame {
	/** Serial version identifier. */
	private static final long serialVersionUID = 1906421480446559568L;
	/** Content pane for the frame elements. */
	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public About() {
		setTitle("About Game");
		setResizable(false);
		setBounds(100, 100, 270, 158);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(Color.BLACK);
		contentPane.setLayout(null);
		
		JLabel lblMadeBy = new JLabel("Made By");
		lblMadeBy.setForeground(Color.LIGHT_GRAY);
		lblMadeBy.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMadeBy.setBounds(90, 11, 73, 31);
		contentPane.add(lblMadeBy);
		
		JLabel lblDebrecen = new JLabel("Debrecen, 2014");
		lblDebrecen.setForeground(Color.LIGHT_GRAY);
		lblDebrecen.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblDebrecen.setBounds(76, 81, 107, 31);
		contentPane.add(lblDebrecen);
		
		JLabel lblKerkgyrtJzsef = new JLabel("Kerékgyártó József");
		lblKerkgyrtJzsef.setForeground(Color.LIGHT_GRAY);
		lblKerkgyrtJzsef.setBounds(63, 54, 151, 19);
		lblKerkgyrtJzsef.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblKerkgyrtJzsef);
		setVisible(true);
	}
}
