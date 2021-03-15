package JavaTail;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TailFast {

    public static void main(String args[]) {

        File file = new File(args[0]);
        try {
            RandomAccessFile readFileAccess = new RandomAccessFile(file, "r");
            long readLines = 0;
            long point = file.length()-2;
            for (; point > 0; point--) {
                readFileAccess.seek(point);
                char c;
                c = (char) readFileAccess.read();
                if (c == '\n') {
                    readLines++;
                    if (readLines == 10)
                        break;
                }
            }

            while (true){
                readFileAccess.seek(point);
                String current = null;
                if((current = readFileAccess.readLine())!=null){
                    System.out.println(current);
                }
                point = readFileAccess.getFilePointer();
            }


        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
