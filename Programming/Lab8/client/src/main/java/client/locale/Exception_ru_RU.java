package client.locale;

import java.util.ListResourceBundle;

public class Exception_ru_RU extends ListResourceBundle {
    private final Object[][] contents = {
            {"userNotFound", "Пользователь `%s` не найден"},
            {"userExists", "Пользователь `%s` уже существует"},
            {"wrongPassword", "Неверный пароль"},
            {"emptyUsername", "Пустое имя пользователя"},
            {"passwordFormat", "Неправильный формат пароля (мин - 8 символов)"},
            {"authorizationNotAvailable", "Авторизация не доступна сейчас :("},
            {"userInfoLost", "Ошибка при проверке данных авторизации пользователя"},
            {"idCheck", "ID должен быть больше 0"},
            {"idFormat", "ID должен быть целым числом"},
            {"nameFormat", "Имя не должно быть пустым"},
            {"coordXCheck", "Координата X не может быть больше 304"},
            {"coordXFormat", "Координата X должна быть целым числом"},
            {"coordYFormat", "Координата Y должна быть дробным числом"},
            {"dateFormat", "Неверный формат даты"},
            {"fromXFormat", "Координата X местоположения-до должна быть целым числом"},
            {"fromYFormat", "Координата Y местоположения-до должна быть целым числом"},
            {"fromZFormat", "Координата Z местоположения-до должна быть целым числом"},
            {"fromNameFormat", "Имя местоположения-до не должно быть пустым"},
            {"toXFormat", "Координата X местоположения-после должна быть дробным числом"},
            {"toYFormat", "Координата Y местоположения-после должна быть дробным числом"},
            {"toNameCheck", "Имя местоположения-после не должно быть длиннее 675 символов"},
            {"toNameFormat", "Имя местоположения-после не должно быть пустым"},
            {"distanceCheck", "Расстояние должно быть больше 1"},
            {"distanceFormat", "Расстояние должно быть целым числом"},
            {"incompatibleMethod", "Метод несовместим с этой командой"},
            {"idNotExist", "Объекта с таким ID не существует"},
            {"idNotYour", "Доступ запрещен - это не ваш объект"},
            {"invalidScript", "Невалидный скрипт"},
            {"fileNotSelected", "Файл не был выбран"},
            {"scriptRecursion", "Рекурсивный вызов скрипта"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
