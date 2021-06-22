package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;

/**
 * Klasa odpowiadająca za rysowanie obiektów oraz obsługę zdarzeń ich
 * dotyczących (na przykład kolizji).
 */
public class Painter extends Panel implements ActionListener {
	Label scoreLabel = new Label("Score:");
	Label pauseLabel = new Label("RUNNING - PRESS SPACE TO PAUSE");
	Timer t = new Timer(5, this);
	int[] startingPosx = java.util.Arrays.copyOf(FileParser.xStart, FileParser.xStart.length);
	int[] startingPosy = java.util.Arrays.copyOf(FileParser.yStart, FileParser.yStart.length);
	int[] xVelocity = java.util.Arrays.copyOf(FileParser.xVelocity, FileParser.xVelocity.length);
	int[] yVelocity = java.util.Arrays.copyOf(FileParser.yVelocity, FileParser.yVelocity.length);
	int lives = FileParser.noOfLives;
	int score = 0;
	int playerWidth = 50;
	int playerHeight = 50;
	int x0 = 475;
	int y0 = 450;
	int playerVel = 2;
	int playerVelx = 0;
	int playerVely = 0;
	int radius = 100;
	int pauseIndex = 0;
	ArrayList<Ellipse2D> balls = new ArrayList<Ellipse2D>();
	Rectangle2D player;

	public Painter() {
		setBackground(Color.gray);
		setPreferredSize(new Dimension(1000, 500));

		addTopMenu();
		addEntities();
		t.start();
		MovementControl controller = new MovementControl(this);
		addKeyListener(controller);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		drawPlayer(g);
		drawBalls(g);

	}

	public void addTopMenu() {
		Container topMenu = new Container();
		topMenu.setLayout(new BoxLayout(topMenu, BoxLayout.Y_AXIS));
		topMenu.add(scoreLabel);
		topMenu.add(pauseLabel);
		add(topMenu);
	}

	public void addEntities() {
		balls.clear();
		for (int i = 0; i < FileParser.noOfBalls; i++) {
			balls.add(new Ellipse2D.Double(startingPosx[i], startingPosy[i], radius, radius));
		}
		player = new Rectangle2D.Double(x0, y0, playerWidth, playerHeight);
	}

	public void drawPlayer(Graphics g) {
		g.setColor(Color.blue);
		Graphics2D g2 = (Graphics2D) g;
		g2.fill(player);

	}

	public void drawBalls(Graphics g) {
		g.setColor(Color.red);
		Graphics2D g2 = (Graphics2D) g;
		for (Ellipse2D e : balls) {
			g2.fill(e);
		}

	}

	public void actionPerformed(ActionEvent evt) {
		repaint();
		addEntities();
		
		if (pauseIndex !=1)
		{
		x0 += playerVelx;
		y0 += playerVely;
		for (int i = 0; i < FileParser.noOfBalls; i++) {
			if (startingPosx[i] < 0 || startingPosx[i] > getWidth() - radius - 1) {
				xVelocity[i] = -xVelocity[i];
			}
			if (startingPosy[i] < 0 || startingPosy[i] > getHeight() - radius - 1) {
				yVelocity[i] = -yVelocity[i];
			}
			if (balls.get(i).intersects(player) == true) {
				lives--;
				if (FileParser.noOfLives < 0) {

					JOptionPane.showMessageDialog(this, "Game over! Your score: " + score);
					resetPositions();
				}
				JOptionPane.showMessageDialog(this, "You lost a life! Lives remaining: " + lives);
				resetPositions();
			}

			startingPosx[i] += xVelocity[i];
			startingPosy[i] += yVelocity[i];
		}
	    updateScore();
	}
		
	}

	public void resetPositions() {
	 	startingPosx = java.util.Arrays.copyOf(FileParser.xStart, FileParser.xStart.length);
		startingPosy = java.util.Arrays.copyOf(FileParser.yStart, FileParser.yStart.length);
		playerVelx = 0;
		playerVely = 0;
	}

	public void updateScore() {
		score++;
		scoreLabel.setText("Score:  " + score + "  ");
	}
}