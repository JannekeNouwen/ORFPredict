package database_handler;

import java.sql.*;

import static database_handler.DatabaseHandler.connect;

public class UserInfoHandler {


    public static int newUser(String username, String password){

        return 0;  // statuscode of user ID?
    }

    public static int removeUser(String username){

        return 0;  // statuscode oid
    }

    public static int checkCredentials(String username, String password) throws ClassNotFoundException, SQLException {
        Connection con = connect();
        assert con != null;
        Statement use = con.createStatement();

//        String hashedPassword = hashPassword(password);
        String hashedPassword = password;

        String query = "select id from user where username = '" +
                username + "'" + " and " +
                "password = '" + hashedPassword + "';";
        System.out.println(query);
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.next()) {
                con.close();
                return -1;
            }
            int id = Integer.parseInt(rs.getString("id"));
            con.close();
            // statuscode of user ID. Let op: statuscode is altijd
            // kleiner dan 0
            return id;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong. Please try again.");
        }

//        make conn to database()
//        if username in database:
//            check wachtwoord
//                    if wachtwoord hoort bij user
//                        return user id
//                    else
//                        gooi custom exceptie op
//        else
//            gooi custom exceptie op
        return -1;
    }

    private static String hashPassword(String password) {
        String hashedPassword = "";

        return hashedPassword;
    }
}
