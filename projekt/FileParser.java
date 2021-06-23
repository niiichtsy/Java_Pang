package projekt;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Klasa odpowiadająca za parsowanie plików konfiguracyjnych oraz poziomów.
 * Informacje na temat poziomów są zapisywane w plikach o nazwie
 * "(numerpoziomu)level.txt", natomiast konfiguracja gry w pliku config.
 */
public class FileParser {
  static int noOfBalls; /** Zmienna określająca liczbę piłek */  
  static int noOfLevels; /** zmienna określająca liczbę poziomów  */
  static int noOfLives; /** Zmienna określająca liczbę żyć*/ 
  static int noOfPlayers; /** Zmienna określająca liczbę graczy */ 
  static int[] yVelocity; /** Tablica z prędkościami wertykalnymi piłek*/ 
  static int[] xVelocity; /** Tablica z prędkościami horyzontalnymi piłek */ 
  static int[] xStart; /** Tablica z pozycjami startowymi x  piłek  */ 
  static int[] yStart; /** Tablica z pozycjami startowymi y  piłek */ 
  static String[] players; /** Tablica z imionami graczy */
  static int[] scores; /** Tablica z wynikami graczy */
  static String playerName; /** Zmienna przechowująca imię obecnego gracza */
  /**
   * Metoda parsująca plik konfiguracyjny.
   */
  static void configParse() throws IOException {
    InputStream propertiesFile = new FileInputStream("config.txt");
    Properties properties = new Properties();
    properties.load(propertiesFile);
    noOfLevels = Integer.parseInt(properties.getProperty("noOfLevels"));
    noOfLives = Integer.parseInt(properties.getProperty("noOfLives"));
    playerName = properties.getProperty("playerName");
    propertiesFile.close();
  }

  /**
   * Metoda parsująca poziom.
   */
  static void levelParse(int levelNumber) throws IOException  {
    InputStream propertiesFile = new FileInputStream(levelNumber + "level.txt");
    Properties properties = new Properties();
    properties.load(propertiesFile);
    noOfBalls = Integer.parseInt(properties.getProperty("noOfBalls"));
    xVelocity = new int[noOfBalls];
    yVelocity = new int[noOfBalls];
    xStart = new int[noOfBalls];
    yStart = new int[noOfBalls];
    for (int i = 0; i < noOfBalls; i++) {

      xVelocity[i] = Integer.parseInt(properties.getProperty("xVelocity" + (i + 1)));
      yVelocity[i] = Integer.parseInt(properties.getProperty("yVelocity" + (i + 1)));
      xStart[i] = Integer.parseInt(properties.getProperty("xStart" + (i + 1)));
      yStart[i] = Integer.parseInt(properties.getProperty("yStart" + (i + 1)));
    }
    propertiesFile.close();

  }

  /**
   * Metoda zapisująca wynik do pliku, po posortowaniu go w kolejności najwyższych do najniższych.
   */
  static void scoreSave(int finalScore, String playerName) throws IOException {
    scoreLoad();
    FileOutputStream outFile = new FileOutputStream("highScore.txt");
    PrintStream out = new PrintStream(outFile);
    noOfPlayers++;
    scores[noOfPlayers-1] = finalScore;
    players[noOfPlayers-1] = playerName;
    out.println("noOfPlayers="+(noOfPlayers));

    for (int i = 0; i < (noOfPlayers); i++)
    {
      if (scores[i] < scores[i+1]) 
      {
        int tempint = scores[i];
        scores[i] = scores[i+1];
        scores[i+1] = tempint;
        String tempstring = players[i];
        players[i] = players[i+1];
        players[i+1] = tempstring;
      }
    }
    for (int i = 0; i < (noOfPlayers); i++)
    {
      out.println("Player"+(i+1)+"="+players[i]);
      
    }
    for (int i = 0; i < (noOfPlayers); i++)
    {
      out.println("Score"+(i+1)+"="+scores[i]);
    }
    
  }

  
  /**
   * Metoda wczytująca wyniki z pliku.
   */
  static void scoreLoad() throws IOException {
    InputStream propertiesFile = new FileInputStream("highScore.txt");
    Properties properties = new Properties();
    properties.load(propertiesFile);
    noOfPlayers = Integer.parseInt(properties.getProperty("noOfPlayers"));
    scores = new int[noOfPlayers+2];
    players = new String[noOfPlayers+2];
    for (int i = 0; i< noOfPlayers; i++)
    {
      scores[i] = Integer.parseInt(properties.getProperty("Score" + (i + 1)));
      players[i] = properties.getProperty("Player" + (i + 1));
    }
    for (int i = 0; i < (noOfPlayers); i++)
    {
      if (scores[i] < scores[i+1]) 
      {
        int tempint = scores[i];
        scores[i] = scores[i+1];
        scores[i+1] = tempint;
        String tempstring = players[i];
        players[i] = players[i+1];
        players[i+1] = tempstring;
      }
    }
  }


}
