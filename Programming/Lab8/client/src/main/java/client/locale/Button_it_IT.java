package client.locale;

import java.util.ListResourceBundle;

public class Button_it_IT extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"logIn", "accedere"},
            {"signUp", "iscriversi"},
            {"cancel", "annullare"},
            {"create", "creare"},
            {"update", "aggiornare"},
            {"delete", "eliminare"},
            {"visualize", "Visualizzare"},
            {"commands", "comandi"},
            {"clear", "Cancellare collezione"},
            {"createIfMin", "Aggiungi se minimo"},
            {"remove", "Rimuovere"},
            {"removeGreater", "Rimuovere maggiore"},
            {"script", "Eseguire script"},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
