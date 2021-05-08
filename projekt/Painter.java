package projekt;

import java.awt.*;
import java.awt.geom.*;


public class Painter extends Canvas {
        
        

    void drawLevel(Graphics g){
        g.clearRect(0, 0, getWidth(), getHeight());
        
        Graphics2D painter = (Graphics2D) g;

        Rectangle2D player = new Rectangle2D.Float(50, 50, 100, 200);

        painter.setColor(Color.blue);
        painter.fill(player);

    }

}