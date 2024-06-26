package client.locale;

import java.util.ListResourceBundle;

public class Command_tr_TR extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"addSuccess", "Öğe eklendi"},
            {"addFailed", "Minimum mesafe koşulu karşılanmadı, öğe eklenmedi"},
            {"clearSuccess", "Öğeleriniz temizlendi"},
            {"clearFailed", "Öğeniz yok"},
            {"removeOne", "Öğe kaldırıldı"},
            {"removeMany", "Öğeler kaldırıldı: %s"},
            {"update", "Kimlik (%s) ile öğe güncellendi"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
