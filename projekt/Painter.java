package projekt;

import java.awt.*;
import java.awt.geom.*;


public class Painter extends Canvas {
        
        

    public void paint(Graphics g){

        setBackground(Color.gray);
        g.clearRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.blue);
        g.fillRect(50, 50, 100, 200);

    }

}