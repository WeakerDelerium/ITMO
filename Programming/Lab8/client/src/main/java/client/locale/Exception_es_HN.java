package client.locale;

import java.util.ListResourceBundle;

public class Exception_es_HN extends ListResourceBundle {
    private final Object[][] contents = {
            {"userNotFound", "Usuario `%s` no encontrado"},
            {"userExists", "Usuario `%s` ya existe"},
            {"wrongPassword", "Contraseña incorrecta"},
            {"emptyUsername", "El nombre de usuario no puede estar vacío"},
            {"passwordFormat", "Formato de contraseña incorrecto (min - 8 símbolos)"},
            {"authorizationNotAvailable", "La autorización no está disponible ahora :("},
            {"userInfoLost", "Error al verificar los datos de autorización del usuario"},
            {"idCheck", "El id debe ser mayor que cero"},
            {"idFormat", "El id debe ser un número entero"},
            {"nameFormat", "El nombre no puede estar vacío"},
            {"coordXCheck", "La coordenada X no puede ser mayor que 304"},
            {"coordXFormat", "La coordenada X debe ser un número entero"},
            {"coordYFormat", "La coordenada Y debe ser un número fraccionario"},
            {"dateFormat", "Formato de fecha incorrecto"},
            {"fromXFormat", "La coordenada X de la ubicación desde debe ser un número entero"},
            {"fromYFormat", "La coordenada Y de la ubicación desde debe ser un número entero"},
            {"fromZFormat", "La coordenada Z de la ubicación desde debe ser un número entero"},
            {"fromNameFormat", "El nombre de la ubicación desde no puede estar vacío"},
            {"toXFormat", "La coordenada X de la ubicación a debe ser un número fraccionario"},
            {"toYFormat", "La coordenada Y de la ubicación a debe ser un número fraccionario"},
            {"toNameCheck", "El nombre de la ubicación a no puede ser mayor que 675"},
            {"toNameFormat", "El nombre de la ubicación a no puede estar vacío"},
            {"distanceCheck", "La distancia debe ser mayor que 1"},
            {"distanceFormat", "La distancia debe ser un número entero"},
            {"incompatibleMethod", "El método es incompatible con este comando"},
            {"idNotExist", "El elemento de la colección con el id sugerido no existe"},
            {"idNotYour", "Acceso denegado - este no es tu elemento"},
            {"invalidScript", "Script inválido"},
            {"fileNotSelected", "No se seleccionó archivo"},
            {"scriptRecursion", "Recursión del script"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
