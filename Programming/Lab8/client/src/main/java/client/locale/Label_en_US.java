package client.locale;

import java.util.ListResourceBundle;

public class Label_en_US extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"username", "Username"},
            {"passwd", "Password"},
            {"name", "Name"},
            {"distance", "Distance"},
            {"removeById", "Remove by id"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
