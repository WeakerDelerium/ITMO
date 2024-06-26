package client.locale;

import java.util.ListResourceBundle;

public class Heading_tr_TR extends ListResourceBundle {
    private final Object[][] contents = {
            {"start", "başlayalım"},
            {"lang", "dil"},
            {"logIn", "giriş yapmak"},
            {"signUp", "hesap\noluştur"},
            {"create", "oluştur"},
            {"update", "güncelle"},
            {"createIfMin", "minimumsa oluştur"},
            {"removeGreater", "büyük olanı kaldır"},
            {"visualize", "görselleştir"},
            {"commands", "komutlar"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
