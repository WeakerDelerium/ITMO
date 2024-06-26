package client.locale;

import java.util.ListResourceBundle;

public class Placeholder_en_US extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"logInUsername", "Enter your username"},
            {"logInPasswd", "Enter your password"},
            {"signUpUsername", "Create a username"},
            {"signUpPasswd", "Create a password"},
            {"name", "Enter name"},
            {"x", "Enter X"},
            {"y", "Enter Y"},
            {"z", "Enter Z"},
            {"distance", "Enter distance"},
            {"id", "Enter id"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
