package projekt;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Klasa odpowiadająca za parsowanie plików konfiguracyjnych oraz poziomów. Informacje na temat poziomów są zapisywane w plikach o nazwie "(numerpoziomu)level.txt", natomiast
 * konfiguracja gry w pliku config.
 */
public class FileParser {
  static int noOfBalls; /*zmienna określająca liczbę piłek*/
  static int noOfLevels; /*zmienna określająca liczbę poziomów*/
  static int noOfLives; /*zmienna określająca liczbę żyć*/
  static int yVelocity; /*zmienna określająca maksymalną prędkość wertykalną piłek*/
  static int xVelocity; /*zmienna określająca maksymalną prędkość horyzontalną piłek*/
  static int xStart; /*zmienna określająca startową pozycję x*/
  static int yStart; /*zmienna określająca startową pozycję y*/

  static void configParse() throws IOException {
        InputStream propertiesFile = new FileInputStream("config.txt");
        Properties properties = new Properties();
        properties.load(propertiesFile);
        noOfLevels = Integer.parseInt(properties.getProperty("noOfLevels"));
        noOfLives = Integer.parseInt(properties.getProperty("noOfLives"));
        System.out.println("Configs parsed. Number of levels: " + noOfLevels + "\nNumber of lives: " + noOfLives);
        propertiesFile.close();
  }

  static void levelParse(int levelNumber) throws IOException {
    InputStream propertiesFile = new FileInputStream(levelNumber + "level.txt");
    Properties properties = new Properties();
    properties.load(propertiesFile);
    noOfBalls = Integer.parseInt(properties.getProperty("noOfBalls"));
    xVelocity = Integer.parseInt(properties.getProperty("xVelocity"));
    yVelocity = Integer.parseInt(properties.getProperty("yVelocity"));
    xStart = Integer.parseInt(properties.getProperty("xStart"));
    yStart = Integer.parseInt(properties.getProperty("yStart"));
    System.out.println("Number of balls in the " + levelNumber + "st level: "+noOfBalls);
    propertiesFile.close();
    
  }
}

