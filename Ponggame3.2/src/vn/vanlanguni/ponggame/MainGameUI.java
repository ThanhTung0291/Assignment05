 /*
 * 
 * 
 * 
 * 
 */
package vn.vanlanguni.ponggame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * @author Invisible Man
 *
 */
public class MainGameUI extends JFrame{
	private static final int _HEIGHT = 500;
	private static final int _WIDTH = 500;
	private PongPanel pongPanel;
	public MainGameUI(){
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setPreferredSize(new Dimension(_WIDTH, _HEIGHT));
		setLayout(new BorderLayout());
		setTitle("Pong Game - K21T Ltd.");
		pongPanel = new PongPanel();
		getContentPane().add(pongPanel, BorderLayout.CENTER);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		Sound.bgMusic.play();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				int result = JOptionPane.showConfirmDialog(MainGameUI.this, "Are you sure to exit Pong Game?");
				if(result == JOptionPane.YES_OPTION){
					Sound.bgMusic.stop();
					setVisible(false);
				}				
			}
		});
	}

    public static void main(String[] args) {
       MainGameUI mainFrame = new MainGameUI();
       mainFrame.setVisible(true);
    }
}
