package server.managers;

import com.opencsv.exceptions.CsvException;
import java.io.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FileManager {
    private final String filename;
    private final CSVOperator csvOperator;

    public FileManager(String filename) {
        this.filename = new File(filename).getAbsolutePath();
        this.csvOperator = new CSVOperator(filename);
    }

    public LinkedHashMap<String, ArrayList<String>> read() throws IOException, CsvException {
        return this.csvOperator.read();
    }

    public void write(ArrayList<String[]> data) throws IOException {
        this.csvOperator.write(data);
    }

    public String getFilename() {
        return this.filename;
    }
}