package vn.vanlanguni.ponggame;

import java.awt.Color;
/**
*
* @author Copy of HuyenPham
*/

public class Settings {
	private String PlayerName01, PlayerName02;
	private Color backgroundColor, paddleColor, ballColor;

	public Settings() {
	}

	public Settings(String player01, String player02, Color backgroundColor, Color paddleColor, Color ballColor) {
		super();
		this.PlayerName01 = player01;
		this.PlayerName02 = player02;
		this.backgroundColor = backgroundColor;
		this.paddleColor = paddleColor;
		this.ballColor = ballColor;

		System.out.println("User1: " + player01);
		System.out.println("User2: " + player02);
	}

	public Settings(String u1, String u2) {
		PlayerName01 = u1;
		PlayerName02 = u2;
	}

	public void setUserName2(String Player02name) {
		this.PlayerName02 = Player02name;
	}

	public String getUserName2() {
		return PlayerName02;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getPaddleColor() {
		return paddleColor;
	}

	public void setPaddleColor(Color paddleColor) {
		this.paddleColor = paddleColor;
	}

	public Color getBallColor() {
		return ballColor;
	}

	public void setBallColor(Color ballColor) {
		this.ballColor = ballColor;
	}

	public void setUserName1(String Player01name) {
		PlayerName01 = Player01name;
	}

	public String getUserName1() {
		return PlayerName01;
	}

}
