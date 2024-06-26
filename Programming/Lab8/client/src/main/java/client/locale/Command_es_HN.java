package client.locale;

import java.util.ListResourceBundle;

public class Command_es_HN extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"addSuccess", "El elemento fue agregado"},
            {"addFailed", "No se cumplió la condición de distancia mínima, el elemento no fue agregado"},
            {"clearSuccess", "Sus elementos fueron limpiados"},
            {"clearFailed", "No tienes elementos"},
            {"removeOne", "El elemento fue eliminado"},
            {"removeMany", "Elementos eliminados: %s"},
            {"update", "El elemento con id (%s) fue actualizado"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
