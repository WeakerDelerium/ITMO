package client.locale;

import java.util.ListResourceBundle;

public class Command_it_IT extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"addSuccess", "Elemento aggiunto"},
            {"addFailed", "La condizione della distanza minima non è stata soddisfatta, l'elemento non è stato aggiunto"},
            {"clearSuccess", "I tuoi elementi sono stati cancellati"},
            {"clearFailed", "Non hai elementi"},
            {"removeOne", "Elemento rimosso"},
            {"removeMany", "Elementi rimossi: %s"},
            {"update", "Elemento con id (%s) è stato aggiornato"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
