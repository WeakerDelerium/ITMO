package client.locale;

import java.util.ListResourceBundle;

public class Exception_en_US extends ListResourceBundle {
    private final Object[][] contents = {
            {"userNotFound", "User `%s` not found"},
            {"userExists", "User `%s` already exist"},
            {"wrongPassword", "Wrong password"},
            {"usernameFormat", "Username cannot be empty"},
            {"passwordFormat", "Wrong password format (min - 8 symbols)"},
            {"authorizationNotAvailable", "Authorization not available now :("},
            {"userInfoLost", "Error while checking user authorization data"},
            {"idCheck", "Id must be greater than zero"},
            {"idFormat", "Id must be an integer"},
            {"nameFormat", "Name can't be empty"},
            {"coordXCheck", "Coordinate X can't be greater than 304"},
            {"coordXFormat", "Coordinate X must be an integer"},
            {"coordYFormat", "Coordinate Y must be a fractional number"},
            {"dateFormat", "Wrong date format"},
            {"fromXFormat", "Location-from coordinate X must be an integer"},
            {"fromYFormat", "Location-from coordinate Y must be an integer"},
            {"fromZFormat", "Location-from coordinate Z must be an integer"},
            {"fromNameFormat", "Location-from name can't be empty"},
            {"toXFormat", "Location-to coordinate X must be a fractional number"},
            {"toYFormat", "Location-to coordinate Y must be a fractional number"},
            {"toNameCheck", "Location-to name can't be greater than 675"},
            {"toNameFormat", "Location-to name can't be empty"},
            {"distanceCheck", "Distance must be greater than 1"},
            {"distanceFormat", "Distance must be an integer"},
            {"incompatibleMethod", "Method is incompatible with this command"},
            {"idNotExist", "Collection item with suggested id doesn't exist"},
            {"idNotYour", "Access denied - this is not your item"},
            {"invalidScript", "Invalid script"},
            {"fileNotSelected", "File wasn't selected"},
            {"scriptRecursion", "Script recursion"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
