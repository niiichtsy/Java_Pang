package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
 * klasa odpowiadająca za rysowanie obiektów oraz obsługę zdarzeń ich dotyczących.
 */
public class Painter extends Panel implements ActionListener, KeyListener
{
	Timer t = new Timer(1,this);
	int playerWidth=50;
	int playerHeight=50;
	int x0=475;
	int y0=450;
	int playerVel=2;
	int playerVelx=0;
	int playerVely=0;

	public Painter()
	{
		setBackground(Color.gray);

		setPreferredSize(new Dimension(1000,500));
		t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		drawPlayer(g);
		drawBalls(g);
	}

	public void drawPlayer(Graphics g)
	{
		g.setColor(Color.blue);
		Graphics2D g2 = (Graphics2D) g;
		g2.fill(new Rectangle2D.Double(x0,y0,playerWidth,playerHeight));

	}

	public void drawBalls(Graphics g)
	{
		g.setColor(Color.red);
		Graphics2D g2 = (Graphics2D) g;
		g2.fill(new Ellipse2D.Double(FileParser.xStart,FileParser.yStart,100,100));

	}
	
	public 	void actionPerformed(ActionEvent evt)
	{
		repaint();
		x0 += playerVelx;
		y0 += playerVely;
	}

	public void up()
	{
	
		playerVelx = 0;
		playerVely = -playerVel;
	}
	
	public void down()
	{
		
		
		playerVelx = 0;
		playerVely = playerVel;
		
	}

	public void left()
	{
		playerVelx = -playerVel;
		playerVely = 0;
	}

	public void right()
	{
		playerVelx = playerVel;
		playerVely = 0;
	}

	public void stop()
	{
		playerVelx = 0;
		playerVely = 0;
	}
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_UP)
	{
		up();
	}
		else stop();

		if(code == KeyEvent.VK_DOWN)
		{
			down();
		}
		if(code == KeyEvent.VK_RIGHT)
		{
			right();
		}
		if(code == KeyEvent.VK_LEFT)
		{	
			left();
		}
			
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) 
	{
		stop();
	}

}