package client.locale;

import java.util.ListResourceBundle;

public class Label_es_HN extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"username", "Nombre de usuario"},
            {"passwd", "Contraseña"},
            {"name", "Nombre"},
            {"distance", "Distancia"},
            {"removeById", "Eliminar por id"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
