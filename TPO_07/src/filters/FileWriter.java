package filters;

import javax.servlet.ServletContext;
import java.io.*;

public class FileWriter {

    public static void printFile(PrintWriter writer, ServletContext context, String fileName) throws IOException {
        try (InputStream in = context.getResourceAsStream(fileName)) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
                for (String line; (line = br.readLine()) != null; ) {
                    writer.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
