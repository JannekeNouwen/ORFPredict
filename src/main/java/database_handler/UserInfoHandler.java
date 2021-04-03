package database_handler;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import static database_handler.DatabaseHandler.connect;

/**
 * Handles all actions related to users
 * @version 1
 * @author Yuri, Janneke & Max
 */
public class UserInfoHandler {

    /**
     * Add a new user to the database
     * @param username - new username
     * @param password - new password
     * @return status code, -1 or 0
     * @throws ClassNotFoundException
     */
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
        return 0;
    }

    /**
     * Remove user from database
     * @param username - username
     * @return statuscode
     */
    public static int removeUser(String username){

        return 0;
    }

    /**
     * Check if the user entered the right credentials
     * @param username - username entered by the user
     * @param password - password endered by the user
     * @return user id (or -1 if no user was found)
     * @throws ClassNotFoundException
     */
    public static int checkCredentials(String username, String password) throws ClassNotFoundException {
        Connection con = connect();
        assert con != null;

        // Hash the password for security. Username is used as SALT
        String hashedPassword = hashPassword(password, username);

        // Get user id by username and hashed password
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
            return id;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong. Please try again.");
        }
        return -1;
    }

    /**
     * Hash password
     * @param strPassword - password entered by the user
     * @param strSalt - username used as variation so users with the same
     *                password will not have the same hash
     * @return
     */
    private static String hashPassword(String strPassword, String strSalt) {
        char[] password = strPassword.toCharArray();
        byte[] salt = strSalt.getBytes();

        try {
            // Hash password to byte array
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password, salt, 65536, 99);
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );

            BigInteger bi = new BigInteger(1, res);
            // Hashed password to string
            return String.format("%0" + (res.length << 1) + "X", bi);

        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }
}
