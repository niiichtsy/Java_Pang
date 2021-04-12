import java.io.File; 
import java.io.FileNotFoundException;  
import java.util.Scanner; 
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class FileParsing {
  

  
  public static void main(String[] args) {
    List<Float> parseList = new ArrayList<Float>(); 
    int noOfBalls = 0;
    try {
      File config = new File("config.txt");
      Scanner parser = new Scanner(config);
      noOfBalls = Integer.parseInt(parser.nextLine());
      while (parser.hasNextLine()) {
        parseList.add(Float.parseFloat(parser.nextLine()));
      }
      parser.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    float g = parseList.get(0); /*zmienna określająca stałą grawitacji poziomu */
    float[] xpositions = new float[noOfBalls]; /*tablica pozycji x piłek*/
    float[] ypositions = new float[noOfBalls]; /*tablica pozycji y piłek*/
    for (int i=0; i<noOfBalls; i++)
    {
      int x = 1+i;
      int y = 1+i+noOfBalls;   
      xpositions[i] = parseList.get(x);
      ypositions[i] = parseList.get(y);
    }
    System.out.println(ypositions[0]);


    
}
}
