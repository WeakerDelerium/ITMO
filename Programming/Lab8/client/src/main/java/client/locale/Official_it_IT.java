package client.locale;

import java.util.ListResourceBundle;

public class Official_it_IT extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"info", "INFORMAZIONE"},
            {"error", "ERRORE"},
            {"route", "Percorsi"},
            {"filter", "Filtrato"},
            {"emptyTable", "Nessun dato"},
            {"script", "Scegli il file dello script"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
