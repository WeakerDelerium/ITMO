package client.locale;

import java.util.ListResourceBundle;

public class Label_tr_TR extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"username", "Kullanıcı adı"},
            {"passwd", "Şifre"},
            {"name", "Isim"},
            {"distance", "Mesafe"},
            {"removeById", "Kimliğe göre kaldır"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
