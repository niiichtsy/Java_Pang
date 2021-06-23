package projekt;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Klasa odpowiadająca za obsługę okna gry.
 */
public class Menu extends Frame {

    static int finalScore = 0; /** Zmienna przechowująca ostateczny wynik po skończeniu gry. */
    
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

        highScore.addActionListener(event -> {
                frame.dispose();
                displayHighScore();
        
        });

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                frame.dispose();
                System.exit(0);
            }
        });

    }
    /**
     * Metoda wyświetlająca menu gry.
     */
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
    /**
     * Metoda wyświetlająca menu końcowe.
     */
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
            try {
                FileParser.scoreSave(Menu.finalScore, FileParser.playerName); 
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    static void displayHighScore() {
        Frame frame = new Frame();
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setLayout(new GridBagLayout());
        frame.removeAll();
        frame.repaint();
        try {
            FileParser.scoreLoad();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Button mainMenu = new Button("Main Menu");
        Label score1 = new Label(FileParser.players[0] + FileParser.scores[0]);
        Label score2 = new Label(FileParser.players[1] + FileParser.scores[1]);
        Label score3 = new Label(FileParser.players[2] + FileParser.scores[2]);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        frame.add(score1, c);
        c.gridx = 0;
        c.gridy = 1;
        frame.add(score2, c);
        c.gridx = 0;
        c.gridy = 2;
        frame.add(score3,c);
        c.gridx = 2;
        c.gridy = 2;
        frame.add(mainMenu,c);

        mainMenu.addActionListener(event -> {
          
            frame.dispose();
            displayMainMenu();
       
    });
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                frame.dispose();
                System.exit(0);
            }
        });
    }
}
