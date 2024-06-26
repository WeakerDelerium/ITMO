package client.locale;

import java.util.ListResourceBundle;

public class Heading_it_IT extends ListResourceBundle {
    private final Object[][] contents = {
            {"start", "iniziare"},
            {"lang", "lingua"},
            {"logIn", "bentornato"},
            {"signUp", "creare\nun account"},
            {"create", "creare"},
            {"update", "aggiornare"},
            {"createIfMin", "creare se minimo"},
            {"removeGreater", "rimuovere maggiore"},
            {"visualize", "visualizzare"},
            {"commands", "comandi"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
