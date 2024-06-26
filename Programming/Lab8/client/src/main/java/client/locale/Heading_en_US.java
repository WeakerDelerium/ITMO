package client.locale;

import java.util.ListResourceBundle;

public class Heading_en_US extends ListResourceBundle {
    private final Object[][] contents = {
            {"start", "get started"},
            {"lang", "language"},
            {"logIn", "welcome back"},
            {"signUp", "create\nan account"},
            {"create", "create"},
            {"update", "update"},
            {"createIfMin", "create if min"},
            {"removeGreater", "remove greater"},
            {"visualize", "visualize"},
            {"commands", "commands"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
