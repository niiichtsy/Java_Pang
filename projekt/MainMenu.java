package projekt;

import java.awt.*; 
import java.awt.event.*; 

class MainMenu extends Frame {

    void displayMenu(){

        Button startGame=new Button("Start Game");  
        Button options=new Button("Options");

        add(startGame, BorderLayout.CENTER);

        add(options, BorderLayout.NORTH);

        setSize(500,500);  
        setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
                }
            }
        );
    }
}
