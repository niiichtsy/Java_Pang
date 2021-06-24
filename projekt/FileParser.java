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
  /** Zmienna określająca wymagane punkty, aby ukończyć poziom */
  static int scoreReq;
  /** Zmienna określająca liczbę piłek */
  static int noOfBalls;
  /** zmienna określająca liczbę poziomów */
  static int noOfLevels;
  /** Zmienna określająca liczbę żyć */
  static int noOfLives;
  /** Zmienna określająca liczbę graczy */
  static int noOfPlayers;
  /** Tablica z prędkościami wertykalnymi piłek */
  static int[] yVelocity;
  /** Tablica z prędkościami horyzontalnymi piłek */
  static int[] xVelocity;
  /** Tablica z pozycjami startowymi x piłek */
  static int[] xStart;
  /** Tablica z pozycjami startowymi y piłek */
  static int[] yStart;
  /** Tablica z imionami graczy */
  static String[] players;
  /** Tablica z wynikami graczy */
  static int[] scores;
  /** Zmienna przechowująca imię obecnego gracza */
  static String playerName;

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
  static void levelParse(int levelNumber) throws IOException {
    InputStream propertiesFile = new FileInputStream(levelNumber + "level.txt");
    Properties properties = new Properties();
    properties.load(propertiesFile);
    scoreReq = Integer.parseInt(properties.getProperty("scoreReq"));
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
   * Metoda zapisująca wynik do pliku, po posortowaniu go w kolejności najwyższych
   * do najniższych.
   */
  static void scoreSave(int finalScore, String playerName) throws IOException {
    scoreLoad();
    FileOutputStream outFile = new FileOutputStream("highScore.txt");
    PrintStream out = new PrintStream(outFile);
    noOfPlayers++;
    scores[noOfPlayers - 1] = finalScore;
    players[noOfPlayers - 1] = playerName;
    out.println("noOfPlayers=" + (noOfPlayers));
   
    sort(scores, players);


    for (int i = 0; i < (noOfPlayers); i++) {
      out.println("Player" + (i + 1) + "=" + players[i]);

    }
    for (int i = 0; i < (noOfPlayers); i++) {
      out.println("Score" + (i + 1) + "=" + scores[i]);
    }
    out.close();
  }

  /**
   * Metoda wczytująca wyniki z pliku.
   */
  static void scoreLoad() throws IOException {
    InputStream propertiesFile = new FileInputStream("highScore.txt");
    Properties properties = new Properties();
    properties.load(propertiesFile);
    noOfPlayers = Integer.parseInt(properties.getProperty("noOfPlayers"));
    scores = new int[noOfPlayers + 2];
    players = new String[noOfPlayers + 2];
    for (int i = 0; i < noOfPlayers; i++) {
      scores[i] = Integer.parseInt(properties.getProperty("Score" + (i + 1)));
      players[i] = properties.getProperty("Player" + (i + 1));
    }
    sort(scores, players);
  }

  static void sort(int[] arr, String[] s)
{
  int temp = 0;
  String tempstring;
  for (int i = 0; i < arr.length; i++) {   
    for (int j = i+1; j < arr.length; j++) {   
       if(arr[i] < arr[j]) {  
           temp = arr[i];  
           arr[i] = arr[j];  
           arr[j] = temp;  
           tempstring = s[i];
           s[i] = s[j];
           s[j] = tempstring;
       }   
    }   
}  
}

}
