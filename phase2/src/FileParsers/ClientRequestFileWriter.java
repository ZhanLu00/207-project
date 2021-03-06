package FileParsers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Write all the client creation requests from the atm to a file
 */
public class ClientRequestFileWriter {

    private String fileName;
    private ArrayList<String[]> usernames;

    public ClientRequestFileWriter(String fileName, ArrayList<String[]> usernames) {
        this.fileName = fileName;
        this.usernames = usernames;
    }

    /**
     * Writes the client requests file.
     * Each line contains the username for a client request.
     */
    public void write() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write("");
        StringBuilder fileOut = new StringBuilder();

        for (String[] username:usernames) {
            fileOut.append(String.format("%s,%s,%s\n",username[0],username[1], username[2]));
        }

        writer.append(fileOut);
        writer.close();
    }

}
