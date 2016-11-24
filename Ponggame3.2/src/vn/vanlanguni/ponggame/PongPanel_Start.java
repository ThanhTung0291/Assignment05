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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	
	// Ball Icon
	ImageIcon imgBall01 = new ImageIcon("Ball/Ball01.png");
	ImageIcon imgBall02 = new ImageIcon("Ball/Ball02.png");
	ImageIcon imgBall03 = new ImageIcon("Ball/Ball03.png");
	ImageIcon imgBall04 = new ImageIcon("Ball/Ball04.png");
	ImageIcon imgBall05 = new ImageIcon("Ball/Ball05.png");
	ImageIcon imgBall06 = new ImageIcon("Ball/Ball06.png");
	// Ball Numbers
	public int BallNum = 1;
	
	@SuppressWarnings("deprecation")
	public PongPanel_Start() {
		
		setPreferredSize(new Dimension(260, 400));
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

		JLabel lblChooseBall = new JLabel("CHOOSE BALL:");
		lblChooseBall.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 17));
		lblChooseBall.setForeground(Color.blue);
		lblChooseBall.setBounds(10, 145, 200, 25);
		getContentPane().add(lblChooseBall);

		JButton btnBall01 = new JButton(imgBall01);
		JButton btnBall02 = new JButton(imgBall02);
		JButton btnBall03 = new JButton(imgBall03);
		JButton btnBall04 = new JButton(imgBall04);
		JButton btnBall05 = new JButton(imgBall05);
		JButton btnBall06 = new JButton(imgBall06);
		btnBall01.setBorderPainted(false);
		btnBall02.setBorderPainted(false);
		btnBall03.setBorderPainted(false);
		btnBall04.setBorderPainted(false);
		btnBall05.setBorderPainted(false);
		btnBall06.setBorderPainted(false);
		btnBall01.setContentAreaFilled(false);
		btnBall02.setContentAreaFilled(false);
		btnBall03.setContentAreaFilled(false);
		btnBall04.setContentAreaFilled(false);
		btnBall05.setContentAreaFilled(false);
		btnBall06.setContentAreaFilled(false);
		getContentPane().add(btnBall01);
		getContentPane().add(btnBall02);
		getContentPane().add(btnBall03);
		getContentPane().add(btnBall04);
		getContentPane().add(btnBall05);
		getContentPane().add(btnBall06);
		btnBall01.setBounds(35, 185, 50, 50);
		btnBall02.setBounds(100, 185, 50, 50);
		btnBall03.setBounds(165, 185, 50, 50);
		btnBall04.setBounds(35, 245, 50, 50);
		btnBall05.setBounds(100, 245, 50, 50);
		btnBall06.setBounds(165, 245, 50, 50);
		
		ActionListener ChooseBall = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == btnBall01){
					BallNum = 1;
					btnBall01.setContentAreaFilled(true);
					btnBall02.setContentAreaFilled(false);
					btnBall03.setContentAreaFilled(false);
					btnBall04.setContentAreaFilled(false);
					btnBall05.setContentAreaFilled(false);
					btnBall06.setContentAreaFilled(false);
				}else if(e.getSource() == btnBall02){
					BallNum = 2;
					btnBall02.setContentAreaFilled(true);
					btnBall01.setContentAreaFilled(false);
					btnBall03.setContentAreaFilled(false);
					btnBall04.setContentAreaFilled(false);
					btnBall05.setContentAreaFilled(false);
					btnBall06.setContentAreaFilled(false);
				}else if(e.getSource() == btnBall03){
					BallNum = 3;
					btnBall03.setContentAreaFilled(true);
					btnBall01.setContentAreaFilled(false);
					btnBall02.setContentAreaFilled(false);
					btnBall04.setContentAreaFilled(false);
					btnBall05.setContentAreaFilled(false);
					btnBall06.setContentAreaFilled(false);
				}else if(e.getSource() == btnBall04){
					BallNum = 4;
					btnBall04.setContentAreaFilled(true);
					btnBall01.setContentAreaFilled(false);
					btnBall02.setContentAreaFilled(false);
					btnBall03.setContentAreaFilled(false);
					btnBall05.setContentAreaFilled(false);
					btnBall06.setContentAreaFilled(false);
				}else if(e.getSource() == btnBall05){
					BallNum = 5;
					btnBall05.setContentAreaFilled(true);
					btnBall01.setContentAreaFilled(false);
					btnBall02.setContentAreaFilled(false);
					btnBall03.setContentAreaFilled(false);
					btnBall04.setContentAreaFilled(false);
					btnBall06.setContentAreaFilled(false);
				}else if(e.getSource() == btnBall06){
					BallNum = 6;
					btnBall06.setContentAreaFilled(true);
					btnBall01.setContentAreaFilled(false);
					btnBall02.setContentAreaFilled(false);
					btnBall03.setContentAreaFilled(false);
					btnBall04.setContentAreaFilled(false);
					btnBall05.setContentAreaFilled(false);
				}
			}
		};
		btnBall01.addActionListener(ChooseBall);
		btnBall02.addActionListener(ChooseBall);
		btnBall03.addActionListener(ChooseBall);
		btnBall04.addActionListener(ChooseBall);
		btnBall05.addActionListener(ChooseBall);
		btnBall06.addActionListener(ChooseBall);
		
		MouseListener Mouse = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				btnBall01.setBorderPainted(false);
				btnBall02.setBorderPainted(false);
				btnBall03.setBorderPainted(false);
				btnBall04.setBorderPainted(false);
				btnBall05.setBorderPainted(false);
				btnBall06.setBorderPainted(false);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == btnBall01){
					BallNum = 1;
					btnBall01.setBorderPainted(true);
					btnBall02.setBorderPainted(false);
					btnBall03.setBorderPainted(false);
					btnBall04.setBorderPainted(false);
					btnBall05.setBorderPainted(false);
					btnBall06.setBorderPainted(false);
				}else if(e.getSource() == btnBall02){
					BallNum = 2;
					btnBall02.setBorderPainted(true);
					btnBall01.setBorderPainted(false);
					btnBall03.setBorderPainted(false);
					btnBall04.setBorderPainted(false);
					btnBall05.setBorderPainted(false);
					btnBall06.setBorderPainted(false);
				}else if(e.getSource() == btnBall03){
					BallNum = 3;
					btnBall03.setBorderPainted(true);
					btnBall01.setBorderPainted(false);
					btnBall02.setBorderPainted(false);
					btnBall04.setBorderPainted(false);
					btnBall05.setBorderPainted(false);
					btnBall06.setBorderPainted(false);
				}else if(e.getSource() == btnBall04){
					BallNum = 4;
					btnBall04.setBorderPainted(true);
					btnBall01.setBorderPainted(false);
					btnBall02.setBorderPainted(false);
					btnBall03.setBorderPainted(false);
					btnBall05.setBorderPainted(false);
					btnBall06.setBorderPainted(false);
				}else if(e.getSource() == btnBall05){
					BallNum = 5;
					btnBall05.setBorderPainted(true);
					btnBall01.setBorderPainted(false);
					btnBall02.setBorderPainted(false);
					btnBall03.setBorderPainted(false);
					btnBall04.setBorderPainted(false);
					btnBall06.setBorderPainted(false);
				}else if(e.getSource() == btnBall06){
					BallNum = 6;
					btnBall06.setBorderPainted(true);
					btnBall01.setBorderPainted(false);
					btnBall02.setBorderPainted(false);
					btnBall03.setBorderPainted(false);
					btnBall04.setBorderPainted(false);
					btnBall05.setBorderPainted(false);
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		btnBall01.addMouseListener(Mouse);
		btnBall02.addMouseListener(Mouse);
		btnBall03.addMouseListener(Mouse);
		btnBall04.addMouseListener(Mouse);
		btnBall05.addMouseListener(Mouse);
		btnBall06.addMouseListener(Mouse);

		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialogResult = MyDialogResult.YES;
				setVisible(false);
				sPlayer1 = getSetings().getUserName1();
				sPlayer2 = getSetings().getUserName2();
			}
		});
		btnPlay.setBounds(30, 325, 90, 25);
		getContentPane().add(btnPlay);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogResult = MyDialogResult.CANCEL;
				setVisible(false);
			}
		});
		btnCancel.setBounds(140, 325, 90, 25);
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


