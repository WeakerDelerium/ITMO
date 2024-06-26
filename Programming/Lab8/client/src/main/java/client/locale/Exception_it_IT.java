package client.locale;

import java.util.ListResourceBundle;

public class Exception_it_IT extends ListResourceBundle {
    private final Object[][] contents = {
            {"userNotFound", "Utente `%s` non trovato"},
            {"userExists", "Utente `%s` esiste già"},
            {"wrongPassword", "Password errata"},
            {"emptyUsername", "Il nome utente non può essere vuoto"},
            {"passwordFormat", "Formato password errato (min - 8 simboli)"},
            {"authorizationNotAvailable", "Autorizzazione non disponibile ora :("},
            {"userInfoLost", "Errore durante il controllo dei dati di autorizzazione dell'utente"},
            {"idCheck", "L'id deve essere maggiore di zero"},
            {"idFormat", "L'id deve essere un numero intero"},
            {"nameFormat", "Il nome non può essere vuoto"},
            {"coordXCheck", "La coordinata X non può essere maggiore di 304"},
            {"coordXFormat", "La coordinata X deve essere un numero intero"},
            {"coordYFormat", "La coordinata Y deve essere un numero frazionario"},
            {"dateFormat", "Formato data errato"},
            {"fromXFormat", "La coordinata X della posizione da deve essere un numero intero"},
            {"fromYFormat", "La coordinata Y della posizione da deve essere un numero intero"},
            {"fromZFormat", "La coordinata Z della posizione da deve essere un numero intero"},
            {"fromNameFormat", "Il nome della posizione da non può essere vuoto"},
            {"toXFormat", "La coordinata X della posizione a deve essere un numero frazionario"},
            {"toYFormat", "La coordinata Y della posizione a deve essere un numero frazionario"},
            {"toNameCheck", "Il nome della posizione a non può essere maggiore di 675"},
            {"toNameFormat", "Il nome della posizione a non può essere vuoto"},
            {"distanceCheck", "La distanza deve essere maggiore di 1"},
            {"distanceFormat", "La distanza deve essere un numero intero"},
            {"incompatibleMethod", "Metodo incompatibile con questo comando"},
            {"idNotExist", "L'elemento della collezione con l'id suggerito non esiste"},
            {"idNotYour", "Accesso negato - questo non è il tuo elemento"},
            {"invalidScript", "Script non valido"},
            {"fileNotSelected", "File non selezionato"},
            {"scriptRecursion", "Ricorsione dello script"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
