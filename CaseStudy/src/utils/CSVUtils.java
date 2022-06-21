package utils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
    // ghi file
    public static <T> void write(String path, List<T> items) {
        try(PrintWriter printWriter = new PrintWriter(path)) {
            for (T item : items) {
                printWriter.println(item.toString());
            }
            printWriter.flush();
        }catch (FileNotFoundException e){
            throw new IllegalArgumentException(path + " invalid");
        }
    }

    // doc file
    public static List<String> read(String path) {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
}
