package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    public static Properties getProperties(String filename) throws IOException {
        Properties properties = new Properties();

        File configFile = new File(filename);
        properties.load(new FileInputStream(configFile));

        return properties;
    }
}
