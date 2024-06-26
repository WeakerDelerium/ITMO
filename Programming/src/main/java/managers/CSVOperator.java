package managers;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class CSVOperator {
    private final String filename;
    private final Integer DATA_LENGTH = 13;
    private final String[] head = {"id", "name", "coordinates x", "coordinates y", "creationDate",
            "from x", "from y", "from z", "from name", "to x", "to y", "to name", "distance"};

    public CSVOperator(String filename) {
        this.filename = new File(filename).getAbsolutePath();
    }

    public LinkedHashMap<String, ArrayList<String>> read() throws IOException, CsvException {
        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(this.filename)));

        List<String[]> data = reader.readAll();

        if (data.isEmpty()) throw new CsvException("Invalid file -> File is empty");

        for (String[] line : data) {
            if (String.join("", line).isEmpty()) continue;
            if (line.length != DATA_LENGTH) throw new CsvException("Invalid file -> Wrong number of line arguments");
        }

        String[] keys = data.get(0);

        for (int elInd = 0; elInd < DATA_LENGTH; elInd++) {
            if (!keys[elInd].equals(head[elInd])) throw new CsvException("Invalid file -> Wrong first-line format\n" +
                    "First-line format: id, name, coordinates x, coordinates y, creationDate, from x, from y, from z, from name, to x, to y, to name, distance");
        }

        LinkedHashMap<String, ArrayList<String>> dataMap = new LinkedHashMap<>();

        for (String key : keys) {
            key = key.trim();
            dataMap.put(key, new ArrayList<>());
        }

        for (int lineInd = 1; lineInd < data.size(); lineInd++) {
            if (String.join("", data.get(lineInd)).isEmpty()) continue;
            for (int elInd = 0; elInd < keys.length; elInd++) {
                ArrayList<String> newValue = dataMap.get(keys[elInd]);
                newValue.add(data.get(lineInd)[elInd].trim());
                dataMap.replace(keys[elInd], newValue);
            }
        }

        reader.close();

        return dataMap;
    }

    public void write(ArrayList<String[]> data) throws IOException {
        CSVWriter writer = new CSVWriter(
                new BufferedWriter(new FileWriter(this.filename)),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.NO_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        escapeChars(data);

        writer.writeNext(this.head);
        writer.writeAll(data);

        writer.close();
    }

    private void escapeChars(ArrayList<String[]> data) {
        data.forEach(dataLine -> {
            for (int i = 0; i < dataLine.length; i++) {
                if (dataLine[i].contains("\"") || dataLine[i].contains(",") || dataLine[i].contains("\\"))
                    dataLine[i] = '"' + dataLine[i]
                            .replace("\\", "\\\\")
                            .replace("\"", "\\\"")
                            .replace(",", "\\,") + '"';
            }
        });
    }
}
