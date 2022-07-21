package constants;

import data.User;

public class Mesaje {

    public static final String WRONG_PASSWORD = "Password was invalid";

    public static String getUserAdded(User user){
        return "User with email: " + user.getEmail() + " was successfully added!";
    }

    public static String getUserAlreadyExist() {
        return "User already exists!";
    }

    public static String getCannotFindUser(String email) {
        return "Cannot find user with email: " + email;
    }
}
