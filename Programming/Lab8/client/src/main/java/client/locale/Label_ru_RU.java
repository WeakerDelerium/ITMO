package client.locale;

import java.util.ListResourceBundle;

public class Label_ru_RU extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"username", "Имя пользователя"},
            {"passwd", "Пароль"},
            {"name", "Название"},
            {"distance", "Дистанция"},
            {"removeById", "Удалить по ID"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
