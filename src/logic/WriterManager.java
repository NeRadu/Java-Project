package logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriterManager {

    private final BufferedWriter bufferedWriter;

    public WriterManager() throws IOException {
        // logica de instantiere de bufferedWriter

        bufferedWriter = new BufferedWriter(new FileWriter("resources/output.txt"));
    }

    public void write(String string) {
        // scrie in fisier output.txt rand nou cu stringul primit

        try {
            bufferedWriter.write(string);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();


            //     System.out.println(string);

        }
    }
}
