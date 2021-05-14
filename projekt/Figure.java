package projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Figure extends Panel implements ActionListener, KeyListener
{
	Timer t = new Timer(5,this);
	int figureWidth=50;
	int figureHeight=50;
	int x0=0;
	int y0=0;
	int figureVel=2;
	int figureVelx=0;
	int figureVely=0;

	public Figure()
	{
		t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	public void paint(Graphics g)
	{
	
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.fill(new Rectangle2D.Double(x0,y0,figureWidth,figureHeight));
		
	
	}
	public 	void actionPerformed(ActionEvent evt)
	{
		repaint();
		x0 += figureVelx;
		y0 += figureVely;
	}

	public void up()
	{
	
		figureVelx = 0;
		figureVely = -figureVel;
	}
	
	public void down()
	{
		
		
		figureVelx = 0;
		figureVely = figureVel;
		
	}

	public void left()
	{
		figureVelx = -figureVel;
		figureVely = 0;
	}

	public void right()
	{
		figureVelx = figureVel;
		figureVely = 0;
	}

	public void stop()
	{
		figureVelx = 0;
		figureVely = 0;
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