package client.locale;

import java.util.ListResourceBundle;

public class Heading_es_HN extends ListResourceBundle {
    private final Object[][] contents = {
            {"start", "empezar"},
            {"lang", "idioma"},
            {"logIn", "bienvenido"},
            {"signUp", "crear\nuna cuenta"},
            {"create", "crear"},
            {"update", "actualizar"},
            {"createIfMin", "crear si mínimo"},
            {"removeGreater", "eliminar mayor"},
            {"visualize", "visualizar"},
            {"commands", "comandos"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
