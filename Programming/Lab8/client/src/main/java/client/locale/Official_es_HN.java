package client.locale;

import java.util.ListResourceBundle;

public class Official_es_HN extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"info", "INFO"},
            {"error", "ERROR"},
            {"route", "Rutas"},
            {"filter", "Filtrado"},
            {"emptyTable", "Sin datos"},
            {"script", "Elija el archivo del script"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
