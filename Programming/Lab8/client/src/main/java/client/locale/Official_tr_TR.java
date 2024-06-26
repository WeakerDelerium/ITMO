package client.locale;

import java.util.ListResourceBundle;

public class Official_tr_TR extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"info", "BİLGİ"},
            {"error", "HATA"},
            {"route", "Rotalar"},
            {"filter", "Filtrelendi"},
            {"emptyTable", "Veri yok"},
            {"script", "Betik dosyasını seçin"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
