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
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ThreadLocalRandom;

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
	private int interval = 800 / 60;

	private static final long serialVersionUID = -1097341635155021546L;

	private boolean showTitleScreen = true;
	public boolean playing;
	private boolean gameOver;

	/** Background. */
	private Color backgroundColor = Color.BLACK;
	ImageIcon backgroundscreen;
	ImageIcon Backgroundsplay;
	ImageIcon backgroundsover;
	ImageIcon ponggame;
	/** State on the control keys. */
	private boolean upPressed;
	private boolean downPressed;
	private boolean wPressed;
	private boolean sPressed;
	private boolean spPressed;

	// Random +/-
	private int timeToDisplay;
	private int timeToDisplay1;
	private boolean showRandomMinus;
	private boolean showRandomPlus;
	private int xRan;
	private int yRan;
	private int x1Ran;
	private int y1Ran;
	private int lastHitBall;
	private ImageIcon imgPlus = new ImageIcon("hinh/Plus.png");
	private ImageIcon imgMinus = new ImageIcon("hinh/Minus.png");

	/** The ball: position, diameter */
	private int ballX = 235;
	private int ballY = 235;
	private int diameter = 30;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;

	/** Ball Images */
	ImageIcon imgBall01 = new ImageIcon("Ball/Ball001.png");
	ImageIcon imgBall02 = new ImageIcon("Ball/Ball002.png");
	ImageIcon imgBall03 = new ImageIcon("Ball/Ball003.png");
	ImageIcon imgBall04 = new ImageIcon("Ball/Ball004.png");
	ImageIcon imgBall05 = new ImageIcon("Ball/Ball005.png");
	ImageIcon imgBall06 = new ImageIcon("Ball/Ball006.png");
	private int ballNumber = 1;

	/** BackGround Numbers */
	private int BGNumber = 1;
	private int testBGNum = 0;
	/** Player 1's paddle: position and size */
	private int playerOneX = 0;
	private int playerOneY = 250;
	private int playerOneWidth = 10;
	private int playerOneHeight = 60;

	/** Player 2's paddle: position and size */
	private int playerTwoX = 484;
	private int playerTwoY = 250;
	private int playerTwoWidth = 10;
	private int playerTwoHeight = 60;

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
		Timer timer = new Timer(interval, this);
		timer.start();

		// Random time
		timeToDisplay = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 1000;
		timeToDisplay1 = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 1000;

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
				Sound.bgMusic.stop();
				btnStart.setVisible(false);
				showTitleScreen = true;
				dialogStart.setLocationRelativeTo(PongPanel.this);
				dialogStart.pack();
				dialogStart.setVisible(true);
				dialogStart.txtPlayer1.setFocusable(true);
				if (dialogStart.dialogResult != MyDialogResult.YES) {
					btnStart.setVisible(true);
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
				Sound.hitPaddle.play();
				ballDeltaY *= -1;
			}
			if (ballDeltaY == 3) {
				if (nextBallLeft <= playerOneTop + 15 || nextBallLeft >= playerOneBottom - 15) {
					ballDeltaY = 4;
				} else if (nextBallLeft < playerOneTop + 30) {
					ballDeltaY = 2;
				} else if (nextBallLeft < playerOneTop + 45) {
					ballDeltaY = 3;

				}

			} else if (ballDeltaY == -3) {
				if (nextBallLeft <= playerOneTop + 15 || nextBallLeft >= playerOneBottom - 15) {
					ballDeltaY = -4;
				} else if (nextBallLeft < playerOneTop + 30) {
					ballDeltaY = -2;
				} else if (nextBallLeft < playerOneTop + 45) {
					ballDeltaY = -3;

				}

			}
			if (ballDeltaX == 3) {
				if (nextBallRight <= playerOneTop + 15 || nextBallRight >= playerOneBottom - 15) {
					ballDeltaY = 4;
				} else if (nextBallRight < playerOneTop + 30) {
					ballDeltaY = 2;
				} else if (nextBallRight < playerOneTop + 45) {
					ballDeltaY = 3;

				}
			} else if (ballDeltaX == -3) {
				if (nextBallRight <= playerOneTop + 15 || nextBallRight >= playerOneBottom - 15) {
					ballDeltaY = -4;
				} else if (nextBallRight < playerOneTop + 30) {
					ballDeltaY = -2;
				} else if (nextBallRight < playerOneTop + 45) {
					ballDeltaY = -3;

				}
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {
					Sound.overPaddle.play();
					playerTwoScore++;

					// Player 2 Win, restart the game
					if (playerTwoScore == 3) {
						Sound.winPlayer.play();
						playing = false;
						gameOver = true;
					}
					ballX = 235;
					ballY = 235;
				} else {
					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					Sound.hitPaddle.play();
					ballDeltaX *= -1;
					lastHitBall = 1;
				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {
					Sound.overPaddle.play();
					playerOneScore++;

					// Player 1 Win, restart the game
					if (playerOneScore == 3) {
						Sound.winPlayer.play();
						playing = false;
						gameOver = true;
					}
					ballX = 235;
					ballY = 235;
				} else {

					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					Sound.hitPaddle.play();
					ballDeltaX *= -1;
					lastHitBall = 2;
				}
			}

			// move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;
			//
			timeToDisplay -= interval;
			timeToDisplay1 -= interval;
			if (timeToDisplay < 0) {
				if (showRandomMinus == false) {
					showRandomMinus = true;
					xRan = ThreadLocalRandom.current().nextInt(50, 450 + 1);
					yRan = ThreadLocalRandom.current().nextInt(0, 450 + 1);
				} else {
					Point ballCenter = new Point(ballX + diameter / 2, ballY + diameter / 2);
					Point ranCenter = new Point(xRan + 15, yRan + 15);
					double distance = getPointDistance(ballCenter, ranCenter);
					if (distance < diameter / 2 + 15) {
						showRandomMinus = false;
						timeToDisplay = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 1000;
						if (lastHitBall == 1) {
							if (playerOneHeight > 40) {
								playerOneHeight = playerOneHeight - playerOneHeight * 25 / 100;
							}
						} else if (lastHitBall == 2) {
							if (playerTwoHeight > 40) {
								playerTwoHeight = playerTwoHeight - playerTwoHeight * 25 / 100;
							}
						}
					}
				}
				if (timeToDisplay < -5000) {
					showRandomMinus = false;
					timeToDisplay = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 1000;
				}
			}
			if (timeToDisplay1 < 0) {
				if (showRandomPlus == false) {
					showRandomPlus = true;
					x1Ran = ThreadLocalRandom.current().nextInt(50, 450 + 1);
					y1Ran = ThreadLocalRandom.current().nextInt(0, 450 + 1);
				} else {
					Point ballCenter1 = new Point(ballX + diameter / 2, ballY + diameter / 2);
					Point ranCenter1 = new Point(x1Ran + 15, y1Ran + 15);
					double distance1 = getPointDistance(ballCenter1, ranCenter1);
					if (distance1 < diameter / 2 + 15) {
						showRandomPlus = false;
						timeToDisplay1 = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 1000;
						if (lastHitBall == 1) {
							if (playerOneHeight < 90) {
								playerOneHeight = playerOneHeight + playerOneHeight * 25 / 100;
							}
						} else if (lastHitBall == 2) {
							if (playerTwoHeight < 90) {
								playerTwoHeight = playerTwoHeight + playerTwoHeight * 25 / 100;
							}
						}
					}
				}
				if (timeToDisplay1 < -5000) {
					showRandomPlus = false;
					timeToDisplay1 = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 1000;
				}
			}
		}
		// stuff has moved, tell this JPanel to repaint itself
		repaint();
	}

	public double getPointDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}

	/** Paint the game screen. */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (showTitleScreen) {

			/* Show welcome screen */
			testBGNum = dialogStart.testBg;
			if (testBGNum == 0) {
				backgroundscreen = new ImageIcon("hinh/BGMain.jpg");
			} else if (testBGNum == 1) {
				backgroundscreen = new ImageIcon("hinh/BG01.jpg");
			} else if (testBGNum == 2) {
				backgroundscreen = new ImageIcon("hinh/BG02.jpg");
			} else if (testBGNum == 3) {
				backgroundscreen = new ImageIcon("hinh/BG03.jpg");
			} else if (testBGNum == 4) {
				backgroundscreen = new ImageIcon("hinh/BG04.jpg");
			} else if (testBGNum == 5) {
				backgroundscreen = new ImageIcon("hinh/BG05.jpg");
			} else if (testBGNum == 6) {
				backgroundscreen = new ImageIcon("hinh/BG06.jpg");
			}
			g.drawImage(backgroundscreen.getImage(), 0, 0, 500, 500, null);
			ponggame = new ImageIcon("hinh/Name.png.");
			g.drawImage(ponggame.getImage(), 70, 50, 349, 81, null);
			// Draw game title and start message
			g.setColor(Color.cyan);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			// g.drawString("Pong Game", 150, 100);////

			// FIXME Removed Mesage: "Press P to play!"

		} else if (playing) {

			/* Game is playing */
			BGNumber = dialogStart.BGNum;
			if (BGNumber == 1) {
				Backgroundsplay = new ImageIcon("hinh/BG01.jpg");
			} else if (BGNumber == 2) {
				Backgroundsplay = new ImageIcon("hinh/BG02.jpg");
			} else if (BGNumber == 3) {
				Backgroundsplay = new ImageIcon("hinh/BG03.jpg");
			} else if (BGNumber == 4) {
				Backgroundsplay = new ImageIcon("hinh/BG04.jpg");
			} else if (BGNumber == 5) {
				Backgroundsplay = new ImageIcon("hinh/BG05.jpg");
			} else if (BGNumber == 6) {
				Backgroundsplay = new ImageIcon("hinh/BG06.jpg");
			}
			g.drawImage(Backgroundsplay.getImage(), 0, 0, 500, 500, null);
			// set the coordinate limits
			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;

			// draw dashed line down center
			// for (int lineY = 0; lineY < getHeight(); lineY += 50) {
			// g.setColor(Color.green);
			// g.drawLine(250, lineY, 250, lineY + 25);
			// }

			// draw "goal lines" on each side
			g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
			g.drawLine(playerTwoLeft - 1, 0, playerTwoLeft - 1, getHeight());

			// Draw the Player's Name

			// g.setColor(Color.gray); //FIX Remove RectFrame for name
			// g.drawRect(30, 30, 200, 30); // draw frame for Player's name
			// g.drawRect(270, 30, 200, 30); // draw frame for Player's name
			//
			g.setColor(Color.red);
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));

			if (dialogStart.sPlayer1.isEmpty()) {
				centerString(g, rect1, "Player 01", nameFont);
			} else {
				centerString(g, rect1, dialogStart.sPlayer1, nameFont); // Player01
																		// name
			}
			if (dialogStart.sPlayer2.isEmpty()) {
				centerString(g, rect2, "Player 02", nameFont);
			} else {
				centerString(g, rect2, dialogStart.sPlayer2, nameFont); // Player02
																		// name
			}

			// draw the scores
			g.setColor(Color.blue);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 120, 100); // Player 1
																	// score
			g.drawString(String.valueOf(playerTwoScore), 360, 100); // Player 2
																	// score

			// draw the ball
			ballNumber = dialogStart.BallNum;
			if (ballNumber == 1) {
				g.drawImage(imgBall01.getImage(), ballX, ballY, diameter, diameter, null);
			} else if (ballNumber == 2) {
				g.drawImage(imgBall02.getImage(), ballX, ballY, diameter, diameter, null);
			} else if (ballNumber == 3) {
				g.drawImage(imgBall03.getImage(), ballX, ballY, diameter, diameter, null);
			} else if (ballNumber == 4) {
				g.drawImage(imgBall04.getImage(), ballX, ballY, diameter, diameter, null);
			} else if (ballNumber == 5) {
				g.drawImage(imgBall05.getImage(), ballX, ballY, diameter, diameter, null);
			} else if (ballNumber == 6) {
				g.drawImage(imgBall06.getImage(), ballX, ballY, diameter, diameter, null);
			}

			// draw the paddles
			g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
			g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
			// draw plus and minus
			if (showRandomMinus) {
				g.drawImage(imgMinus.getImage(), xRan, yRan, 30, 30, null);
			}
			if (showRandomPlus) {
				// g.setColor(Color.white);
				g.drawImage(imgPlus.getImage(), x1Ran, y1Ran, 30, 30, null);
			}

		} else if (gameOver) {

			/* Show End game screen with winner name and score */
			backgroundsover = new ImageIcon("hinh/BGOver.jpg.");
			g.drawImage(backgroundsover.getImage(), 0, 0, 500, 500, null);
			// Draw scores
			// TODO Set Blue color
			g.setColor(Color.blue);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 120, 100);
			g.drawString(String.valueOf(playerTwoScore), 360, 100);

			// Draw the Player's Name
			// g.setColor(Color.gray);
			// g.drawRect(30, 30, 200, 30); // draw frame for Player's name
			// g.drawRect(270, 30, 200, 30); // draw frame for Player's name
			//
			g.setColor(Color.red);
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));

			if (dialogStart.sPlayer1.isEmpty()) {
				centerString(g, rect1, "Player 01", nameFont);
			} else {
				centerString(g, rect1, dialogStart.sPlayer1, nameFont); // Player01
																		// name
			}
			if (dialogStart.sPlayer2.isEmpty()) {
				centerString(g, rect2, "Player 02", nameFont);
			} else {
				centerString(g, rect2, dialogStart.sPlayer2, nameFont); // Player02
																		// name
			}

			// DRAW THE WINNER
			g.setColor(Color.BLACK);
			// g.drawRect(95, 180, 320, 100);
			// g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			if (playerOneScore > playerTwoScore) {
				// centerString(g, rectWinner, dialogStart.sPlayer1 + " win!",
				// winFont);
				if (dialogStart.sPlayer1.isEmpty()) {
					centerString(g, rectWinner, "Player 01 win!", winFont);
				} else {
					centerString(g, rectWinner, dialogStart.sPlayer1 + " win!", winFont); // Player01
																							// win

				}
			} else {
				// centerString(g, rectWinner, dialogStart.sPlayer2 + " win!",
				// winFont);
				if (dialogStart.sPlayer2.isEmpty()) {
					centerString(g, rectWinner, "Player 02 win!", winFont);
				} else {
					centerString(g, rectWinner, dialogStart.sPlayer2 + " win!", winFont); // Player02
																							// win
				}
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
			Sound.winPlayer.stop();
			Sound.bgMusic.play();
			gameOver = false;
			showTitleScreen = true;
			playerOneY = 250;
			playerTwoY = 250;
			ballX = 235;
			ballY = 235;
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
