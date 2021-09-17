package Manager;

import java.io.*;

/**
 * <h2>Basic IO</h2>
 * <p>This class is responsible for reading and writing data in .txt file</p>
 * @author Anurag-Bharati
 * @since 2021
 * @version 1.0
 */
public class FileIO {
    BufferedReader reader = null;
    BufferedWriter writer = null;
    @SuppressWarnings("FieldCanBeLocal")
    boolean debug = false;

    /**
     *<h2>Reads Data</h2>
     * <p>This method reads data.txt and returns</p>
     * @return the read data in string format
     * @throws IOException if file not found
     */
    public String read() throws IOException {
        String data;
        try {
            reader = new BufferedReader(new FileReader("src/main/bin/data.txt"));
        } catch (IOException e) {
            if (debug) {
                e.printStackTrace();
            } else {
                System.out.println("[Warning] File Not Found!");
                System.out.println("[SelfDebug] Now Creating One...");
            }
            writer = new BufferedWriter(new FileWriter("src/main/bin/data.txt"));
            reader = new BufferedReader(new FileReader("src/main/bin/data.txt"));

        } finally {

            data = reader.readLine();
            reader.close();

        }
        return data;
    }

    /**
     * <h2>Writes Data</h2>
     * <p>This method writes data into data.txt</p>
     * @param city is data given by the user (guest)
     * @throws IOException if file went missing while closing
     */
    public void write(String city) throws IOException {
        writer = new BufferedWriter(new FileWriter("src/main/bin/data.txt"));
        writer.write(city);
        writer.flush();
        writer.close();
    }

    /**
     * <h2>Closes the file</h2>
     * <p>This method properly closes the file</p>
     * @throws IOException if file not found
     */
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
            writer = null;
        }
        if (reader != null) {
            reader.close();
            reader = null;
        }
    }

}
