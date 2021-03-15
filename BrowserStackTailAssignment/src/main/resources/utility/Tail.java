package JavaTail;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.*;


public class Tail {

    public static long sleepTime = 1000;
    public static void main(String args[]) throws Exception{

        if(args.length>0){
            if(args.length>1)
                sleepTime = Long.parseLong(args[1]) * 1000;

            BufferedReader input = new BufferedReader(new FileReader(args[0]));
            String currentLine = null;
            while(true){
                if((currentLine = input.readLine()) != null){
                    System.out.println(currentLine);
                    continue;
                }
                try {
                    Thread.sleep(sleepTime);
                }
                catch (Exception e){
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            input.close();

        }
        else
            System.out.println("Error while reading file");
    }

}
