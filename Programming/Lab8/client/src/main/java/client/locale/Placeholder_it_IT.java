package client.locale;

import java.util.ListResourceBundle;

public class Placeholder_it_IT extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"logInUsername", "Inserisci il tuo nome utente"},
            {"logInPasswd", "Inserisci la tua password"},
            {"signUpUsername", "Creare un nome utente "},
            {"signUpPasswd", "Creare una password "},
            {"name", "Inserisci il nome"},
            {"x", "Inserisci X"},
            {"y", "Inserisci Y"},
            {"z", "Inserisci Z"},
            {"distance", "Inserisci distanza "},
            {"id", "Ingrese id"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
