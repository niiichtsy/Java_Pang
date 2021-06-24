package projekt;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
*	Klasa odpowiadająca za poruszanie się bohaterem.
*/
public class MovementControl implements KeyListener {
	/** Zawiera informacje na temat komponentu Painter */
	private Painter painter;

	MovementControl(Painter painter) {
		this.painter = painter;
	}

	/**
	 * Porusza graczem do góry.
	 */
	public void up() {

		painter.playerVelx = 0;
		painter.playerVely = -painter.playerVel;
	}

	/**
	 * Porusza graczem w dół.
	 */
	public void down() {

		painter.playerVelx = 0;
		painter.playerVely = painter.playerVel;

	}

	/**
	 * Porusza graczem w lewo.
	 */
	public void left() {
		painter.playerVelx = -painter.playerVel;
		painter.playerVely = 0;
	}

	/**
	 * Porusza graczem w prawo.
	 */
	public void right() {
		painter.playerVelx = painter.playerVel;
		painter.playerVely = 0;
	}

	/**
	 * Zatrzymuje gracza.
	 */
	public void stop() {
		painter.playerVelx = 0;
		painter.playerVely = 0;
	}

	/**
	 * Pauzuje grę.
	 */
	public void pause() {
		painter.pauseIndex = 1;
		painter.pauseLabel.setText("PAUSED - PRESS SPACE TO UNPAUSE");
	}

	/**
	 * Odpauzowuje grę.
	 */
	public void unpause() {
		painter.pauseIndex = 0;
		painter.pauseLabel.setText("RUNNING - PRESS SPACE TO PAUSE");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
			case (KeyEvent.VK_UP):
				if (painter.y0 > 0) {
					up();
				} else {
					stop();
				}
				break;
			case (KeyEvent.VK_DOWN):
				if (painter.y0 < painter.getHeight() - painter.playerHeight - 1) {
					down();
				} else {
					stop();
				}
				break;
			case (KeyEvent.VK_RIGHT):
				if (painter.x0 < painter.getWidth() - painter.playerWidth - 1) {
					right();
				} else {
					stop();
				}
				break;
			case (KeyEvent.VK_LEFT):
				if (painter.x0 > 0) {
					left();
				} else {
					stop();
				}
				break;
			case (KeyEvent.VK_SPACE):
				if (painter.pauseIndex == 0) {
					pause();
				} else {
					unpause();
				}
				break;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		stop();
	}

}
