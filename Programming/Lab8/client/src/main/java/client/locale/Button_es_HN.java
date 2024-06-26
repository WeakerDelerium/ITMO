package client.locale;

import java.util.ListResourceBundle;

public class Button_es_HN extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"logIn", "iniciar sesión"},
            {"signUp", "registrarse"},
            {"cancel", "cancelar"},
            {"create", "crear"},
            {"update", "actualizar"},
            {"delete", "eliminar"},
            {"visualize", "Visualizar"},
            {"commands", "comandos"},
            {"clear", "Limpiar colección"},
            {"createIfMin", "Agregar si mínimo"},
            {"remove", "Eliminar"},
            {"removeGreater", "Eliminar mayor"},
            {"script", "Ejecutar script"},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
