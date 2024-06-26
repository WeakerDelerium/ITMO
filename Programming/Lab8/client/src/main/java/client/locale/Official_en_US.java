package client.locale;

import java.util.ListResourceBundle;

public class Official_en_US extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"info", "INFO"},
            {"error", "ERROR"},
            {"route", "Routes"},
            {"filter", "Filtered"},
            {"emptyTable", "No data"},
            {"script", "Choose script file"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
