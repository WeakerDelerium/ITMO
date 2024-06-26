package client.locale;

import java.util.ListResourceBundle;

public class Command_ru_RU extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"addSuccess", "Объект был добавлен"},
            {"addFailed", "Условие минимального расстояния не было выполнено, объект не был добавлен"},
            {"clearSuccess", "Ваши объекты были очищены"},
            {"clearFailed", "Ваших объектов нет"},
            {"removeOne", "Объект был удален"},
            {"removeMany", "Объектов удалено - %s"},
            {"update", "Объект с id (%s) был обновлен"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
