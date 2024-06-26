package client.locale;

import java.util.ListResourceBundle;

public class Button_tr_TR extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"logIn", "giriş yap"},
            {"signUp", "kaydol"},
            {"cancel", "iptal etmek"},
            {"create", "oluştur"},
            {"update", "güncelle"},
            {"delete", "silmek"},
            {"visualize", "Görselleştir"},
            {"commands", "komutlar"},
            {"clear", "Koleksiyonu temizle"},
            {"createIfMin", "Minimumsa ekle"},
            {"remove", "Kaldırmak"},
            {"removeGreater", "Büyük olanı kaldır"},
            {"script", "Betiği çalıştır"},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
