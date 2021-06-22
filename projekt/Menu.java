package projekt;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

/**
 * Klasa odpowiadająca za obsługę okna gry.
 */
public class Menu extends Frame {

    static void displayMainMenu() {
        Frame frame = new Frame();
        Button startGame = new Button("Start Game");
        Button highScore = new Button("High Score");
        frame.setLayout(new GridBagLayout());
        frame.setSize(500, 500);
        frame.setVisible(true);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        frame.add(startGame, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        frame.add(highScore, c);

        startGame.addActionListener(event -> {
            try {
                frame.dispose();
                displayGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                frame.dispose();
                System.exit(0);
            }
        });

    }

    static void displayGame() throws IOException {

        Frame frame = new Frame();
        frame.removeAll();
        frame.repaint();

        FileParser.configParse();
        FileParser.levelParse(1);

        Painter painter = new Painter();

        painter.setSize(1000, 500);
       
        frame.add(painter);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                frame.dispose();
                System.exit(0);
            }
        });

    }
}
