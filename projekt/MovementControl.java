package projekt;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
*	Klasa odpowiadająca za poruszanie się bohaterem.
*/
public class MovementControl implements KeyListener {
    private Painter painter;


    MovementControl(Painter painter){
        this.painter = painter;
    }

    public void up() {

		painter.playerVelx = 0;
		painter.playerVely = -painter.playerVel;
	}

	public void down() {

		painter.playerVelx = 0;
		painter.playerVely = painter.playerVel;

	}

	public void left() {
		painter.playerVelx = -painter.playerVel;
		painter.playerVely = 0;
	}

	public void right() {
		painter.playerVelx = painter.playerVel;
		painter.playerVely = 0;
	}

	public void stop() {
		painter.playerVelx = 0;
		painter.playerVely = 0;
	}

	public void pause() {
		painter.pauseIndex = 1;
		painter.pauseLabel.setText("PAUSED - PRESS SPACE TO UNPAUSE");
	}
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
