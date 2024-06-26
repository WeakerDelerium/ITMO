package client.locale;

import java.util.ListResourceBundle;

public class Heading_ru_RU extends ListResourceBundle {
    private final Object[][] contents = {
            {"start", "начнем"},
            {"lang", "язык"},
            {"logIn", "войти"},
            {"signUp", "создать\nаккаунт"},
            {"create", "создать"},
            {"update", "обновить"},
            {"createIfMin", "добавить если минимальный"},
            {"removeGreater", "удалить большие"},
            {"visualize", "визуализировать"},
            {"commands", "команды"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
