package projekt;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

/**
 * Klasa odpowiadająca za obsługę okna gry.
 */
class Menu extends Frame {

    void displayMainMenu() {

        Button startGame = new Button("Start Game");
        Button options = new Button("Options");
        setLayout(new GridBagLayout());
        setSize(500, 500);
        setVisible(true);

        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridwidth = 3;
	    c.gridx = 1;
	    c.gridy = 1;
        add(startGame, c);
	    c.gridwidth = 1;
	    c.gridx = 3;
	    c.gridy = 0;
        add(options, c);

        

        startGame.addActionListener(event -> {
            try {
                dispose();
                displayGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
                System.exit(0);
            }
        });

    }

    void displayGame() throws IOException {
    
        removeAll();
        repaint();

        JLabel scoreLabel = new JLabel("Score:");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
	    c.gridx = 2;
	    c.gridy = 0;
        add(scoreLabel, c);

        FileParser.configParse();
        FileParser.levelParse(1);

        Painter painter = new Painter();

        painter.setSize(1000, 500);
        add(painter);

        pack();
        setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
                System.exit(0);
            }
        });

    }
}
