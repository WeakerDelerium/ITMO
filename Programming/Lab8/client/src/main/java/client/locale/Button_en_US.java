package client.locale;

import java.util.ListResourceBundle;

public class Button_en_US extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"logIn", "log in"},
            {"signUp", "sign up"},
            {"cancel", "cancel"},
            {"create", "create"},
            {"update", "update"},
            {"delete", "delete"},
            {"visualize", "Visualize"},
            {"commands", "commands"},
            {"clear", "Clear collection"},
            {"createIfMin", "Add if min"},
            {"removeGreater", "Remove greater"},
            {"remove", "Remove"},
            {"script", "Execute script"},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
