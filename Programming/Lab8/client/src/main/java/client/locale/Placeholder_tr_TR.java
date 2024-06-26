package client.locale;

import java.util.ListResourceBundle;

public class Placeholder_tr_TR extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"logInUsername", "Kullanıcı adınızı girin"},
            {"logInPasswd", "Şifrenizi girin"},
            {"signUpUsername", "Kullanıcı adı oluştur"},
            {"signUpPasswd", "Şifre oluştur"},
            {"name", "Ismi girin"},
            {"x", "X girin"},
            {"y", "Y girin"},
            {"z", "Z girin"},
            {"distance", "Mesafe girin"},
            {"id", "Kimlik girin"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
