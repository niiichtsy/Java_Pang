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
	/** Przechowuje informacje na temat okna głównego, z którego została wywołana */
	Frame parentFrame;
	/** Napis z wynikiem */
	Label scoreLabel = new Label("Score:");
	/** Napis ze stanem gry */
	Label pauseLabel = new Label("PAUSED - PRESS SPACE TO UNPAUSE");
	/** Główny timer do animacji i obsługi zdarzeń */
	Timer t = new Timer(5, this);
	/** Kopia tablicy pozycji startowych x piłek */
	int[] startingPosx = java.util.Arrays.copyOf(FileParser.xStart, FileParser.xStart.length);
	/** Kopia tablicy pozycji startowych y piłek */
	int[] startingPosy = java.util.Arrays.copyOf(FileParser.yStart, FileParser.yStart.length);
	/** Kopia tablicy prędkości horyzontalnych piłek */
	int[] xVelocity = java.util.Arrays.copyOf(FileParser.xVelocity, FileParser.xVelocity.length);
	/** Kopia tablicy prędkości wertykalnych */
	int[] yVelocity = java.util.Arrays.copyOf(FileParser.yVelocity, FileParser.yVelocity.length);
	/** Obecna liczba żyć gracza */
	int lives = FileParser.noOfLives;
	/** Bieżący wynik gracza */
	int score = 0;
	/** Bieżący poziom */
	int currentLevel;
	/** Szerokość gracz */
	int playerWidth = 50;
	/** Wysokość gracza */
	int playerHeight = 50;
	/** Pozycja startowa x gracza */
	int x0 = 475;
	/** Pozycja startowa y gracza */
	int y0 = 450;
	/** Prędkość gracza */
	int playerVel = 2;
	/** Chwilowa prędkość horyzontalna gracza */
	int playerVelx = 0;
	/** Chwilowa prękość wertykalna gracza */
	int playerVely = 0;
	/** Promień rysowanych piłek */
	int radius = 100;
	/** Zmienna określająca, czy gra jest spauzowana, czy nie */
	int pauseIndex = 1;
	/** Tablica istniejących w danym momencie piłek */
	ArrayList<Ellipse2D> balls = new ArrayList<Ellipse2D>();
	/** Model gracza */
	Rectangle2D player;

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

		super.paintComponent(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		drawPlayer(g);
		drawBalls(g);

	}

	/**
	 * Metoda decydująca, czy załadować następny poziom, czy pokazać menu końcowe.
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
	 * Metoda dodająca komponent pokazujący wynik oraz stan gry.
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
	 * Główny watek zdarzeń wykonywany przez timer. Sprawdza stan gry, kolizję piłek
	 * z graczem, odświeża wynik, zmienia pozycje piłek oraz gracza na podstawie
	 * kontrolera ruchu, decyduje o ujęciu punktu życia oraz wyświetleniu menu w
	 * przypadku braku żyć.
	 */
	public void actionPerformed(ActionEvent evt) {
		if (score < FileParser.scoreReq) {
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
							try {
								FileParser.scoreSave((Menu.finalScore + score), FileParser.playerName);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
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
		} else
			try {
				runNextLevel();
			}

			catch (IOException e) {
				e.printStackTrace();
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