package database_handler;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;


import static database_handler.DatabaseHandler.connect;

public class UserInfoHandler {


    public static int newUser(String username, String password) throws ClassNotFoundException {
        Connection con = connect();
        assert con != null;

        String hashedPassword = hashPassword(password, username);

        String query =
                "select id from user where username = '" + username +  "';";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                con.close();
                return -1;
            } else {
                query = "insert into user(username, password) "
                        + "values ('" + username + "', '" + hashedPassword + "');";
                Statement stmt2 = con.createStatement();
                stmt2. executeUpdate(query);
                con.close();
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong. Please try again.");
        }
        return 0;  // statuscode of user ID?
    }

    public static int removeUser(String username){

        return 0;  // statuscode oid
    }

    public static int checkCredentials(String username, String password) throws ClassNotFoundException {
        Connection con = connect();
        assert con != null;

        String hashedPassword = hashPassword(password, username);
//        String hashedPassword = password;

        String query = "select id from user where username = '" +
                username + "'" + " and " +
                "password = '" + hashedPassword + "';";

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

    private static String hashPassword(String strPassword, String strSalt) {
        char[] password = strPassword.toCharArray();
        byte[] salt = strSalt.getBytes();

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password, salt, 65536, 99);
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );

            BigInteger bi = new BigInteger(1, res);
            return String.format("%0" + (res.length << 1) + "X", bi);

        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }
}
