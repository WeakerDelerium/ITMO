package client.locale;

import java.util.ListResourceBundle;

public class Button_ru_RU extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"logIn", "войти"},
            {"signUp", "регистрация"},
            {"cancel", "отмена"},
            {"create", "создать"},
            {"update", "обновить"},
            {"delete", "удалить"},
            {"visualize", "Визуализировать"},
            {"commands", "команды"},
            {"clear", "Очистить коллекцию"},
            {"createIfMin", "Добавить если минимальный"},
            {"remove", "Удалить"},
            {"removeGreater", "Удалить большие"},
            {"script", "Выполнить скрипт"},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
