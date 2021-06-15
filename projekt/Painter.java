package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;

/**
 * klasa odpowiadająca za rysowanie obiektów oraz obsługę zdarzeń ich
 * dotyczących.
 */
public class Painter extends Panel implements ActionListener, KeyListener {
	Timer t = new Timer(5, this);
	int playerWidth = 50;
	int playerHeight = 50;
	int x0 = 475;
	int y0 = 450;
	int playerVel = 2;
	int playerVelx = 0;
	int playerVely = 0;
	int radius = 100;

	public Painter() {
		setBackground(Color.gray);

		setPreferredSize(new Dimension(1000, 500));
		t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		drawPlayer(g);
		drawBalls(g);
	}

	public void drawPlayer(Graphics g) {
		g.setColor(Color.blue);
		Graphics2D g2 = (Graphics2D) g;
		g2.fill(new Rectangle2D.Double(x0, y0, playerWidth, playerHeight));

	}

	public void drawBalls(Graphics g) {
		g.setColor(Color.red);
		Graphics2D g2 = (Graphics2D) g;
		for (int i=0; i<FileParser.noOfBalls; i++)
		{
			g2.fill(new Ellipse2D.Double(FileParser.xStart[i], FileParser.yStart[i], radius, radius));
		}
		

	}

	public void actionPerformed(ActionEvent evt) {
		repaint();
		x0 += playerVelx;
		y0 += playerVely;
		for (int i=0; i < FileParser.noOfBalls; i++)
		{
		if (FileParser.xStart[i] < 0 || FileParser.xStart[i]> getWidth() - radius - 1) {
			FileParser.xVelocity[i] = -FileParser.xVelocity[i];
		}
		if (FileParser.yStart[i] < 0 || FileParser.yStart[i] > getHeight() - radius - 1) {
			FileParser.yVelocity[i] = -FileParser.yVelocity[i];
		}
		FileParser.xStart[i]+= FileParser.xVelocity[i];
		FileParser.yStart[i] += FileParser.yVelocity[i];
		}
	}

	public void up() {

		playerVelx = 0;
		playerVely = -playerVel;
	}

	public void down() {

		playerVelx = 0;
		playerVely = playerVel;

	}

	public void left() {
		playerVelx = -playerVel;
		playerVely = 0;
	}

	public void right() {
		playerVelx = playerVel;
		playerVely = 0;
	}

	public void stop() {
		playerVelx = 0;
		playerVely = 0;
	}

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
			case (KeyEvent.VK_UP):
				if (y0 > 0) {
					up();
				} else {
					stop();
				}
				break;
			case (KeyEvent.VK_DOWN):
				if (y0 < getHeight() - playerHeight - 1) {
					down();
				} else {
					stop();
				}
				break;
			case (KeyEvent.VK_RIGHT):
				if (x0 < getWidth() - playerWidth - 1) {
					right();
				} else {
					stop();
				}
				break;
			case (KeyEvent.VK_LEFT):
				if (x0 > 0) {
					left();
				} else {
					stop();
				}
				break;
		}

	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		stop();
	}

}