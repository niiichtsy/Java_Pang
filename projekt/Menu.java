package projekt;

import java.awt.*; 
import java.awt.event.*;
import java.io.IOException;

class Menu extends Frame {


    void displayMainMenu(){

        Button startGame=new Button("Start Game");  
        Button options=new Button("Options");

        add(startGame, BorderLayout.CENTER);
        add(options, BorderLayout.NORTH);

        setSize(500,500);  
        setVisible(true);
        
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
                }
            }
        );


    }

    void displayGame() throws IOException {

        removeAll();
        repaint();
        

        FileParser.configParse();
        FileParser.levelParse(1);
        
	Figure figure = new Figure();
        Painter painter = new Painter();
        painter.setSize(1000,500);
        add(painter);                                 // bez tego dziala(ledwo)
        pack();
        setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
                }
            }
        );
	add(figure);
    }
}
