package client.exec;

import client.managers.LocaleManager;
import common.transfer.TagCarrier;

import java.util.Locale;
import java.util.ResourceBundle;

public class ModifiedLocaleException extends Exception {
    private final TagCarrier tagCarrier;

    public ModifiedLocaleException(TagCarrier tagCarrier) {
        this.tagCarrier = tagCarrier;
    }

    @Override
    public String getMessage() {
        return getLocalizedMessage();
    }

    @Override
    public String getLocalizedMessage() {
        Locale locale = LocaleManager.getInstance().getCurrentLocale();

        ResourceBundle exceptionBundle = ResourceBundle.getBundle("client.locale.Exception", locale);

        String tag = tagCarrier.tag();
        Object data = tagCarrier.data();

        if (data == null) return exceptionBundle.getString(tag);
        else return exceptionBundle.getString(tag).formatted(data);
    }
}
