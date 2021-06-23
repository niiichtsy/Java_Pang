package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.io.IOException;

/**
 * Klasa odpowiadająca za rysowanie obiektów oraz obsługę zdarzeń ich
 * dotyczących (na przykład kolizji).
 */
public class Painter extends JPanel implements ActionListener {
	Frame parentFrame; /** Przechowuje informacje na temat okna głównego, z którego została wywołana */
	Label scoreLabel = new Label("Score:"); /** Napis z wynikiem */
	Label pauseLabel = new Label("PAUSED - PRESS SPACE TO UNPAUSE"); /** Napis ze stanem gry */
	Timer t = new Timer(5, this); /** Główny timer do animacji i obsługi zdarzeń */
	int[] startingPosx = java.util.Arrays.copyOf(FileParser.xStart, FileParser.xStart.length); /** Kopia tablicy pozycji startowych x piłek */
	int[] startingPosy = java.util.Arrays.copyOf(FileParser.yStart, FileParser.yStart.length);	/** Kopia tablicy pozycji startowych y piłek */
	int[] xVelocity = java.util.Arrays.copyOf(FileParser.xVelocity, FileParser.xVelocity.length); /** Kopia tablicy prędkości horyzontalnych piłek */
	int[] yVelocity = java.util.Arrays.copyOf(FileParser.yVelocity, FileParser.yVelocity.length); /** Kopia tablicy prędkości wertykalnych */
	int lives = FileParser.noOfLives; /** Obecna liczba żyć gracza */
	int score = 0; /** Bieżący wynik gracza */
	int currentLevel; /** Bieżący poziom */
	int playerWidth = 50; /** Szerokość gracz */
	int playerHeight = 50; /** Wysokość gracza */
	int x0 = 475; /** Pozycja startowa x gracza */
	int y0 = 450; /** Pozycja startowa y gracza */
	int playerVel = 2; /** Prędkość gracza */
	int playerVelx = 0; /** Chwilowa prędkość horyzontalna gracza */
	int playerVely = 0; /** Chwilowa prękość wertykalna gracza */
	int radius = 100; /** Promień rysowanych piłek */
	int pauseIndex = 1; /** Zmienna określająca, czy gra jest spauzowana, czy nie */
	ArrayList<Ellipse2D> balls = new ArrayList<Ellipse2D>(); /** Tablica istniejących w danym momencie piłek */
	Rectangle2D player; /** Model gracza */

	public Painter(Frame parentFrame, int levelIndex) {
		this.parentFrame = parentFrame;
		this.currentLevel = levelIndex;
		setPreferredSize(new Dimension(1000, 500));

		addTopMenu();
		addEntities();
		t.start();
		MovementControl controller = new MovementControl(this);
		addKeyListener(controller);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

	}

	
	public void paintComponent(Graphics g) {
		if (score < 500) {
			super.paintComponent(g);
			g.clearRect(0, 0, getWidth(), getHeight());
			drawPlayer(g);
			drawBalls(g);
		} else
			try {
				runNextLevel();
			}

			catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 *  Metoda decydująca, czy załadować następny poziom, czy pokazać menu końcowe.
	 */
	public void runNextLevel() throws IOException {
		try {
			pauseIndex = 1;
			JOptionPane.showMessageDialog(this, "Level cleared!");
			parentFrame.dispose();
			Menu.finalScore = Menu.finalScore + score;
			score = 0;
			if (FileParser.noOfLevels > currentLevel)
				Menu.displayGame(currentLevel + 1);
			else
				Menu.displayEndMenu();
				
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 *  Metoda dodająca komponent pokazujący wynik oraz stan gry.
	 */
	public void addTopMenu() {
		Container topMenu = new Container();
		topMenu.setLayout(new BoxLayout(topMenu, BoxLayout.Y_AXIS));
		topMenu.add(scoreLabel);
		topMenu.add(pauseLabel);
		add(topMenu);
	}

	/**
	 * Metoda uzupełniająca chwilowe pozycje piłek oraz gracza.
	 */
	public void addEntities() {
		balls.clear();
		for (int i = 0; i < FileParser.noOfBalls; i++) {
			balls.add(new Ellipse2D.Double(startingPosx[i], startingPosy[i], radius, radius));
		}

		player = new Rectangle2D.Double(x0, y0, playerWidth, playerHeight);
	}

	/** 
	 * Metoda rysująca gracza.
	 */
	public void drawPlayer(Graphics g) {
		g.setColor(Color.blue);
		Graphics2D g2 = (Graphics2D) g;
		g2.fill(player);

	}

	/**
	 * Metoda rysująca piłki.
	 */
	public void drawBalls(Graphics g) {
		g.setColor(Color.red);
		Graphics2D g2 = (Graphics2D) g;
		for (Ellipse2D e : balls) {
			g2.fill(e);
		}

	}
	/**
	 * Główny watek zdarzeń wykonywany przez timer. Sprawdza stan gry, kolizję piłek z graczem, odświeża wynik, zmienia pozycje piłek oraz gracza na podstawie kontrolera ruchu,
	 *  decyduje o ujęciu punktu życia oraz wyświetleniu menu w przypadku braku żyć.
	 */
	public void actionPerformed(ActionEvent evt) {
		if (pauseIndex != 1) {
			repaint();
			addEntities();
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
					if (lives == -1) {

						JOptionPane.showMessageDialog(this, "Game over! Your score: " + (Menu.finalScore + score));
						resetPositions();
						Menu.displayMainMenu();
						pauseIndex = 1;
						parentFrame.dispose();
						try{
						FileParser.scoreSave((Menu.finalScore + score), FileParser.playerName);
						} 
						catch (IOException e) {
							e.printStackTrace();
						}
					}
					else {
						JOptionPane.showMessageDialog(this, "You lost a life! Lives remaining: " + lives);
					resetPositions();
					score = 0;
					} 
				}

				startingPosx[i] += xVelocity[i];
				startingPosy[i] += yVelocity[i];
			}
			updateScore();
		}

	}

	/**
	 * Resetuje pozycje piłek oraz gracza.
	 */
	public void resetPositions() {
		startingPosx = java.util.Arrays.copyOf(FileParser.xStart, FileParser.xStart.length);
		startingPosy = java.util.Arrays.copyOf(FileParser.yStart, FileParser.yStart.length);
		playerVelx = 0;
		playerVely = 0;
		x0 = 475;
		y0 = 450;
	}

	/**
	 * Odświeża wynik.
	 */
	public void updateScore() {
		score++;
		scoreLabel.setText("Score:  " + score + "  ");
	}
}