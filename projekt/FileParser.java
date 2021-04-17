package projekt;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


class FileParser {
  static int noOfBalls; /*zmienna określająca liczbę piłek*/
  static int noOfLevels; /*zmienna określająca liczbę poziomów*/
  static int noOfLives; /*zmienna określająca liczbę żyć*/
  static float yvelocity; /*zmienna określająca maksymalną prędkość wertykalną piłek*/
  static float xvelocity; /*zmienna określająca maksymalną prędkość horyzontalną piłek*/
  
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
    System.out.println("Number of balls in the " + levelNumber + "st level: "+noOfBalls);
    propertiesFile.close();

  }
}

