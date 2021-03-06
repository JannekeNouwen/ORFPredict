package database_handler;

public class UserInfoHandler {


    public static int newUser(String username, String password){

        return 0;  // statuscode of user ID?
    }

    public static int removeUser(String username){

        return 0;  // statuscode oid
    }

    public static int checkCredentials(String username, String password) {
//        make conn to database()
//        if username in database:
//            check wachtwoord
//                    if wachtwoord hoort bij user
//                        return user id
//                    else
//                        gooi custom exceptie op
//        else
//            gooi custom exceptie op
        return 0; // statuscode of user ID?
    }

    private static String hashPassword(String password) {
        String hashedPassword = "";

        return hashedPassword;
    }
}
