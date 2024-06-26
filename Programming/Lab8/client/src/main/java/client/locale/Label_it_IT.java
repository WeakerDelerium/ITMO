package client.locale;

import java.util.ListResourceBundle;

public class Label_it_IT extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"username", "Nome utente"},
            {"passwd", "Password"},
            {"name", "Nome"},
            {"distance", "Distanza"},
            {"removeById", "Rimuovere per id"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
