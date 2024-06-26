package client.locale;

import java.util.ListResourceBundle;

public class Placeholder_ru_RU extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"logInUsername", "Введите имя пользователя"},
            {"logInPasswd", "Введите пароль"},
            {"signUpUsername", "Придумайте имя пользователя"},
            {"signUpPasswd", "Придумайте пароль"},
            {"name", "Введите название"},
            {"x", "Введите координату X"},
            {"y", "Введите координату Y"},
            {"z", "Введите координату Z"},
            {"distance", "Введите дистанцию"},
            {"id", "Введите ID"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
