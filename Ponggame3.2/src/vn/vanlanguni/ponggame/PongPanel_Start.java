package vn.vanlanguni.ponggame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
*
* @author Preferent of HuyenPham
*/

@SuppressWarnings("serial")
public class PongPanel_Start extends JDialog{
	public JTextField txtPlayer1 = new JTextField();
	public JTextField txtPlayer2;
	public String sInputName,sPlayer1,sPlayer2;
	private Color backgroundDia = Color.LIGHT_GRAY;
	//Xem khai bao MyDialogResult o cuoi class nay
	public MyDialogResult dialogResult;
	private JTextFieldLimit nameLimit1 = new JTextFieldLimit(10);
	private JTextFieldLimit nameLimit2 = new JTextFieldLimit(10);
	
	@SuppressWarnings("deprecation")
	public PongPanel_Start() {
		
		setPreferredSize(new Dimension(260, 260));
		setTitle("Settings");
		getContentPane().setLayout(null);
		setModal(true);
		this.getContentPane().setBackground(backgroundDia);
		this.setResizable(false);
		this.pack();
		
		
		dialogResult = MyDialogResult.DEFAULT;
		
		txtPlayer1.setDocument(nameLimit1);
		txtPlayer2 = new JTextField();
		txtPlayer2.setDocument(nameLimit2);
		getContentPane().add(txtPlayer1);
		getContentPane().add(txtPlayer2);
		
		txtPlayer1.setFont(new Font(Font.DIALOG_INPUT, Font.CENTER_BASELINE, 12));
		txtPlayer1.setBounds(30, 65, 100, 20);
		txtPlayer2.setFont(new Font(Font.DIALOG_INPUT, Font.CENTER_BASELINE, 12));
		txtPlayer2.setBounds(30, 115, 100, 20);
		
		
		JLabel lblInputName = new JLabel("INPUT PLAYER'S NAME:");
		lblInputName.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 17));
		lblInputName.setForeground(Color.blue);
		lblInputName.setBounds(10, 0, 200, 25);
		getContentPane().add(lblInputName);
		
		JLabel lblPlayer01 = new JLabel("Player 01:");
		lblPlayer01.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		lblPlayer01.setForeground(Color.BLUE);
		lblPlayer01.setBounds(30, 35, 200, 25);
		getContentPane().add(lblPlayer01);
		
		JLabel lblPlayer02 = new JLabel("Player 02:");
		lblPlayer02.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		lblPlayer02.setForeground(Color.BLUE);
		lblPlayer02.setBounds(30, 90, 200, 25);
		getContentPane().add(lblPlayer02);
		
	
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialogResult = MyDialogResult.YES;
				setVisible(false);
				sPlayer1 = getSetings().getUserName1(); 
				sPlayer2 = getSetings().getUserName2();
			}
		});
		btnSave.setBounds(30, 180, 90, 25);
		getContentPane().add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogResult = MyDialogResult.CANCEL;
				setVisible(false);
			}
		});
		btnCancel.setBounds(140, 180, 90, 25);
		getContentPane().add(btnCancel);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		pack();
		
//		addWindowListener(new WindowAdapter() {
//
//			@Override
//			public void windowClosing(WindowEvent arg0) {
//				int result = JOptionPane.showConfirmDialog(PongPanel_Start.this, "Exit Settings?");
//				if(result == JOptionPane.YES_OPTION){
//					setVisible(false);
//				}				
//			}
//		});
	}
	
	public Settings getSetings(){ //Goi chuc nang add ten tu class Settings
		Settings st = new Settings();
		
		st.setUserName1(txtPlayer1.getText()); //add du lieu playerName vao Settings.setUserName1
		st.setUserName2(txtPlayer2.getText()); //add du lieu playerName vao Settings.setUserName2
		return st;
	}
	
}


