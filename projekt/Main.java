package projekt;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException
    {
        System.out.println("heyyyyy");
        FileParser.configParse();
        FileParser.levelParse(1);
    }
}
