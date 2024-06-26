package client.locale;

import java.util.ListResourceBundle;

public class Command_en_US extends ListResourceBundle {
    private final Object[][] contents = new Object[][]{
            {"addSuccess", "Item was added"},
            {"addFailed", "The minimum distance condition was not met, item wasn't added"},
            {"clearSuccess", "Your items was cleared"},
            {"clearFailed", "You have no items"},
            {"removeOne", "Item was removed"},
            {"removeMany", "Items removed - %s"},
            {"update", "Item with id (%s) was updated"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
