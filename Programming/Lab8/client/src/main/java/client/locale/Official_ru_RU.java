package client.locale;

import java.util.ListResourceBundle;

public class Official_ru_RU extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"info", "ИНФОРМАЦИЯ"},
            {"error", "ОШИБКА"},
            {"route", "Маршрутов"},
            {"filter", "Фильтр"},
            {"emptyTable", "Нет данных"},
            {"script", "Выберите файл скрипта"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
