package client.managers;

import java.util.HashMap;
import java.util.Locale;

public class LocaleManager {
    private static LocaleManager localeManager = null;

    private HashMap<String, Locale> localeMap;

    private Locale currentLocale;

    private LocaleManager() {
        generateLocaleMap();
        setLocale("en_US");
    }

    private void generateLocaleMap() {
        localeMap = new HashMap<>();

        localeMap.put("en_US", new Locale("en", "US"));
        localeMap.put("ru_RU", new Locale("ru", "RU"));
        localeMap.put("tr_TR", new Locale("tr", "TR"));
        localeMap.put("it_IT", new Locale("it", "IT"));
        localeMap.put("es_HN", new Locale("es", "HN"));
    }

    public void setLocale(String language) {
        this.currentLocale = localeMap.get(language);
    }

    public Locale getCurrentLocale() {
        return this.currentLocale;
    }

    public static LocaleManager getInstance() {
        if (localeManager == null) localeManager = new LocaleManager();
        return localeManager;
    }
}
