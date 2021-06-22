package projekt;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

/**
 * Klasa odpowiadająca za obsługę okna gry.
 */
public class Menu extends Frame {

    static int finalScore;
    /**
     * Metoda wyświetlająca główne menu.
     */
    static void displayMainMenu() {
        Frame frame = new Frame();
        Button startGame = new Button("Start Game");
        Button highScore = new Button("High Score");
        frame.setLayout(new GridBagLayout());
        frame.setSize(200, 200);
        frame.setVisible(true);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        frame.add(startGame, c);
        c.gridx = 0;
        c.gridy = 0;
        frame.add(highScore, c);

        startGame.addActionListener(event -> {
            try {
                finalScore = 0;
                frame.dispose();
                displayGame(1);
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

    static void displayGame(int levelIndex) throws IOException {

        Frame frame = new Frame();
        frame.removeAll();
        frame.repaint();

        FileParser.configParse();
        FileParser.levelParse(levelIndex);

        Painter painter = new Painter(frame, levelIndex);

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

    static void displayEndMenu() {
        Frame frame = new Frame();
        frame.setSize(200, 200);
        frame.setVisible(true);
        frame.setLayout(new GridBagLayout());
        frame.removeAll();
        frame.repaint();
        Label mainText = new Label("Your score: " + finalScore);
        Button mainMenu = new Button("Main Menu");
        Button exit = new Button("Exit");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        frame.add(mainText, c);
        c.gridx = 0;
        c.gridy = 1;
        frame.add(mainMenu, c);
        c.gridx = 0;
        c.gridy = 2;
        frame.add(exit,c);

       
       
       

        mainMenu.addActionListener(event -> {
          
                frame.dispose();
                displayMainMenu();
           
        });

        exit.addActionListener(event -> {
          
                frame.dispose();
                System.exit(0);
            
        });

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                frame.dispose();
                System.exit(0);
            }
        });

    }
}
