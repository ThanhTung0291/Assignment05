/*
 * PONG GAME REQUIREMENTS
 * This simple "tennis like" game features two paddles and a ball, 
 * the goal is to defeat your opponent by being the first one to gain 3 point,
 *  a player gets a point once the opponent misses a ball. 
 *  The game can be played with two human players, one on the left and one on 
 *  the right. They use keyboard to start/restart game and control the paddles. 
 *  The ball and two paddles should be red and separating lines should be green. 
 *  Players score should be blue and background should be black.
 *  Keyboard requirements:
 *  + P key: start
 *  + Space key: restart
 *  + W/S key: move paddle up/down
 *  + Up/Down key: move paddle up/down
 *  
 *  Version: 0.5
 */
package vn.vanlanguni.ponggame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author Invisible Man
 *
 */
public class PongPanel extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = -1097341635155021546L;

	private boolean showTitleScreen = true;
	public boolean playing;
	private boolean gameOver;

	/** Background. */
	private Color backgroundColor = Color.BLACK;
	ImageIcon backgroundscreen;
	ImageIcon Backgroundsplay;
	ImageIcon backgroundsover;

	/** State on the control keys. */
	private boolean upPressed;
	private boolean downPressed;
	private boolean wPressed;
	private boolean sPressed;
	private boolean spPressed;
	/** The ball: position, diameter */
	private int ballX = 240;
	private int ballY = 240;
	private int diameter = 20;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;

	/** Player 1's paddle: position and size */
	private int playerOneX = 0;
	private int playerOneY = 250;
	private int playerOneWidth = 10;
	private int playerOneHeight = 50;

	/** Player 2's paddle: position and size */
	private int playerTwoX = 484;
	private int playerTwoY = 250;
	private int playerTwoWidth = 10;
	private int playerTwoHeight = 50;

	/** Speed of the paddle - How fast the paddle move. */
	private int paddleSpeed = 5;

	/** Player score, show on upper left and right. */
	private int playerOneScore;
	private int playerTwoScore;
	/** Player Name */
	private String playerOneName = "";
	private String playerTwoName = "";

	// Call Setting Dialog
	private PongPanel_Start dialogStart = new PongPanel_Start();
	private Settings settingFunction = new Settings();
	private JButton btnStart = new JButton("START");

	private Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 15);
	private Font nameFont = new Font(Font.MONOSPACED, Font.BOLD, 20);
	private Font winFont = new Font(Font.DIALOG, Font.BOLD, 35);

	// Draw Rectangle to set center name
	private Rectangle rect1 = new Rectangle(30, 30, 200, 30);
	private Rectangle rect2 = new Rectangle(270, 30, 200, 30);
	private Rectangle rectWinner = new Rectangle(95, 180, 320, 100);

	// private ImageIcon imPlay = new ImageIcon("../Images/play.png");

	/** Construct a PongPanel. */
	public PongPanel() {
		setBackground(backgroundColor);

		// listen to key presses
		setFocusable(true);
		addKeyListener(this);
		this.setLayout(null);

		// call step() 60 fps
		Timer timer = new Timer(600 / 60, this);
		timer.start();
		
		

		// Add Button Start
		// ----------------------------------------------------------------------
		this.add(btnStart);
		btnStart.setBounds(200, 240, 100, 50);
		btnStart.setFont(myFont);
		btnStart.setBackground(Color.BLACK);
		btnStart.setForeground(Color.WHITE);
		btnStart.setContentAreaFilled(false);
		btnStart.setOpaque(true);
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showTitleScreen = false;
				dialogStart.setLocationRelativeTo(PongPanel.this);
				dialogStart.pack();
				dialogStart.setVisible(true);
				dialogStart.txtPlayer1.setFocusable(true);
				if (dialogStart.dialogResult != MyDialogResult.YES) {
					showTitleScreen = true;
					playing = false;
				} else {
					btnStart.setVisible(false);
					showTitleScreen = false;
					playing = true;
				}

			}
		});
		// -------------------------------------------------------------------------------------------------

	}

	/** Implement actionPerformed */
	public void actionPerformed(ActionEvent e) {
		step();
	}

	/** Repeated task */
	public void step() {

		if (playing) {

			/* Playing mode */

			// move player 1
			// Move up if after moving, paddle is not outside the screen
			if (wPressed && playerOneY - paddleSpeed > 0) {
				playerOneY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (sPressed && playerOneY + playerOneHeight + paddleSpeed < getHeight()) {
				playerOneY += paddleSpeed;
			}

			// move player 2
			// Move up if after moving paddle is not outside the screen
			if (upPressed && playerTwoY - paddleSpeed > 0) {
				playerTwoY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (downPressed && playerTwoY + playerTwoHeight + paddleSpeed < getHeight()) {
				playerTwoY += paddleSpeed;
			}

			/*
			 * where will the ball be after it moves? calculate 4 corners: Left,
			 * Right, Top, Bottom of the ball used to determine whether the ball
			 * was out yet
			 */
			int nextBallLeft = ballX + ballDeltaX;
			int nextBallRight = ballX + diameter + ballDeltaX;
			// FIXME Something not quite right here
			int nextBallTop = ballY + ballDeltaY;
			int nextBallBottom = ballY + diameter + ballDeltaY;

			// Player 1's paddle position
			int playerOneRight = playerOneX + playerOneWidth;
			int playerOneTop = playerOneY;
			int playerOneBottom = playerOneY + playerOneHeight;

			// Player 2's paddle position
			float playerTwoLeft = playerTwoX;
			float playerTwoTop = playerTwoY;
			float playerTwoBottom = playerTwoY + playerTwoHeight;

			// ball bounces off top and bottom of screen
			if (nextBallTop < 0 || nextBallBottom > getHeight()) {
				ballDeltaY *= -1;
			}
			if(ballDeltaY ==3){
				if (nextBallLeft <= playerOneTop +15 || nextBallLeft >= playerOneBottom -15){
					ballDeltaY = 4;
				}else if(nextBallLeft < playerOneTop +30){
				    ballDeltaY = 2;
				}else if (nextBallLeft < playerOneTop+45){
					ballDeltaY = 3;
				
				}
				
			}else if (ballDeltaY ==-3){
				if (nextBallLeft <= playerOneTop +15 || nextBallLeft >= playerOneBottom -15){
					ballDeltaY = -4;
				}else if(nextBallLeft < playerOneTop +30){
				    ballDeltaY = -2;
				}else if (nextBallLeft < playerOneTop+45){
					ballDeltaY = -3;
				
				}
				
			}
			if(ballDeltaX ==3){
				if (nextBallRight <= playerOneTop +15 || nextBallRight >= playerOneBottom -15){
					ballDeltaY = 4;
				}else if(nextBallRight < playerOneTop +30){
				    ballDeltaY = 2;
				}else if (nextBallRight < playerOneTop+45){
					ballDeltaY = 3;
				
				}
			}else if (ballDeltaX==-3){
				if (nextBallRight <= playerOneTop +15 || nextBallRight >= playerOneBottom -15){
					ballDeltaY = -4;
				}else if(nextBallRight < playerOneTop +30){
				    ballDeltaY = -2;
				}else if (nextBallRight < playerOneTop+45){
					ballDeltaY = -3;
				
				}
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {

					playerTwoScore++;

					// Player 2 Win, restart the game
					if (playerTwoScore == 3) {
						playing = false;
						gameOver = true;
					}
					ballX = 240;
					ballY = 240;
				} else {
					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {

					playerOneScore++;

					// Player 1 Win, restart the game
					if (playerOneScore == 3) {
						playing = false;
						gameOver = true;
					}
					ballX = 240;
					ballY = 240;
				} else {

					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
				}
			}

			// move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;
		}

		// stuff has moved, tell this JPanel to repaint itself
		repaint();
	}

	/** Paint the game screen. */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (showTitleScreen) {

			/* Show welcome screen */
			backgroundscreen = new ImageIcon("hinh/11.jpg.");
			g.drawImage(backgroundscreen.getImage(), 0, 0,500, 500, null);
	
			// Draw game title and start message
			g.setColor(Color.cyan);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString("Pong Game", 150, 100);////

			// FIXME Removed Mesage: "Press P to play!"

		} else if (playing) {

			/* Game is playing */
			Backgroundsplay = new ImageIcon("hinh/3.jpg.");
			g.drawImage(Backgroundsplay.getImage(), 0, 0,500, 500, null);
			// set the coordinate limit
			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;

			// draw dashed line down center
			for (int lineY = 0; lineY < getHeight(); lineY += 50) {
				g.setColor(Color.green);
				g.drawLine(250, lineY, 250, lineY + 25);
			}

			// draw "goal lines" on each side
			g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
			g.drawLine(playerTwoLeft - 1, 0, playerTwoLeft - 1, getHeight());

			// Draw the Player's Name
			g.setColor(Color.gray);
			g.drawRect(30, 30, 200, 30); // draw frame for Player's name
			g.drawRect(270, 30, 200, 30); // draw frame for Player's name
			//
			g.setColor(Color.lightGray);
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
			centerString(g, rect1, dialogStart.sPlayer1, nameFont); // Player01
																	// name
			centerString(g, rect2, dialogStart.sPlayer2, nameFont); // Player02
																	// name

			// draw the scores
			g.setColor(Color.blue);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 120, 100); // Player 1
																	// score
			g.drawString(String.valueOf(playerTwoScore), 360, 100); // Player 2
																	// score

			// draw the ball
			g.setColor(Color.RED);
			g.fillOval(ballX, ballY, diameter, diameter);

			// draw the paddles
			g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
			g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
		} else if (gameOver) {

			/* Show End game screen with winner name and score */
			 backgroundsover = new ImageIcon("hinh/13.jpg.");
				g.drawImage(backgroundsover.getImage(), 0, 0,500, 500, null);
			// Draw scores
			// TODO Set Blue color
			g.setColor(Color.blue);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 120, 100);
			g.drawString(String.valueOf(playerTwoScore), 360, 100);

			// Draw the Player's Name
			g.setColor(Color.gray);
			g.drawRect(30, 30, 200, 30); // draw frame for Player's name
			g.drawRect(270, 30, 200, 30); // draw frame for Player's name
			//
			g.setColor(Color.lightGray);
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
			centerString(g, rect1, dialogStart.sPlayer1, nameFont); // Player01
																	// name
			centerString(g, rect2, dialogStart.sPlayer2, nameFont); // Player02
																	// name

			// Draw the winner name
			g.setColor(Color.cyan);
			//g.drawRect(95, 180, 320, 100);
//			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			if (playerOneScore > playerTwoScore) {
				centerString(g, rectWinner, dialogStart.sPlayer1 + " win!", winFont);
			} else {
				centerString(g, rectWinner, dialogStart.sPlayer2 + " win!", winFont);
			}

			// Draw Restart message
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
			// TODO Draw a restart message
			g.drawString("Press 'space' to restart game!", 125, 400);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		// Removed function: "Press P to Play!" - by Thuan Nguyen
		if (playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				spPressed = true;
			}
		} else if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
			gameOver = false;
			showTitleScreen = true;
			playerOneY = 250;
			playerTwoY = 250;
			ballX = 240;
			ballY = 240;
			playerOneScore = 0;
			playerTwoScore = 0;
			btnStart.setVisible(true);
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			wPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			sPressed = false;
		}
	}

	// Draw Player's name always center
	public void centerString(Graphics g, Rectangle rect, String sName, Font font) {
		FontRenderContext frc = new FontRenderContext(null, true, true);

		Rectangle2D r2D = font.getStringBounds(sName, frc);
		int rWidth = (int) Math.round(r2D.getWidth());
		int rHeight = (int) Math.round(r2D.getHeight());
		int rX = (int) Math.round(r2D.getX());
		int rY = (int) Math.round(r2D.getY());

		int a = (rect.width / 2) - (rWidth / 2) - rX;
		int b = (rect.height / 2) - (rHeight / 2) - rY;

		g.setFont(font);
		g.drawString(sName, rect.x + a, rect.y + b);
	}

}
