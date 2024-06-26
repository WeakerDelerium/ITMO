package client.locale;

import java.util.ListResourceBundle;

public class Placeholder_es_HN extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"logInUsername", "Ingrese su nombre de usuario"},
            {"logInPasswd", "Ingrese su contraseña"},
            {"signUpUsername", "Crear un nombre de usuario"},
            {"signUpPasswd", "Crear una contraseña"},
            {"name", "Ingrese el nombre"},
            {"x", "Ingrese X"},
            {"y", "Ingrese Y"},
            {"z", "Ingrese Z"},
            {"distance", "Ingrese distancia"},
            {"id", "Ingrese id"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
