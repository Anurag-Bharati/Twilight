package main.test;


import Dashboard.User;
import Manager.DatabaseManager;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <h2>Unit Test | Database</h2>
 * <p>Unit tests for database</p>
 *
 * @author Anurag-Bharati
 * @since 2021
 * @version 1.0
 *
 */
public class UnitTest_DATABASE {

    DatabaseManager databaseManager = new DatabaseManager();
    String gmail = "";
    String app_pass = "";

    /**
     * <h2>User Detail Check</h2>
     * <p>This test checks if entered user details is in the db and is it correct or not</p>
     * @throws SQLException if sth is wrong w/ sql query or database
     */
    @Test
    public void database_userDetailCheck() throws SQLException {

        String firstName = "";
        String lastName = "";
        String Country = "";
        String City = "";

        // test
        assertEquals(gmail, (Objects.requireNonNull(fetchUser(gmail))).getGmail());
        assertEquals(firstName, Objects.requireNonNull(fetchUser(gmail)).getGivenName());
        assertEquals(lastName, Objects.requireNonNull(fetchUser(gmail)).getFamilyName());
        assertEquals(Country, Objects.requireNonNull(fetchUser(gmail)).getCountry());
        assertEquals(City, Objects.requireNonNull(fetchUser(gmail)).getCity());
    }

    /**
     * <h2>User In Database Check</h2>
     * <p>This test checks if a user is added to the database or not.</p>
     * @throws SQLException if sth is wrong w/ sql query or database
     */
    @Test
    public void database_isThisUserThere() throws SQLException {

        assertTrue(checkUser(gmail));
    }

    /**
     * <h2>Login Validation Check</h2>
     * <p>This test checks if a user's gmail and pass matches or not</p>
     * @throws SQLException if sth is wrong w/ sql query or database
     */
    @Test
    public void database_isUserLoginWorking() throws SQLException {
        assertTrue(checkLogin(gmail,app_pass));
    }


    // FUNCTIONS TO CONNECT AND RETRIEVE OF DATA FROM THE DATABASE

    /**
     * <h2>Fetch User</h2>
     * <p>This method provides the data of user only if that user exist</p>
     * @param gmail is gmail provided for the test
     * @return user object and its data
     * @throws SQLException if sth is wrong w/ sql query or database
     */
    private User fetchUser(String gmail) throws SQLException {
        User user = new User();
        Connection connection = databaseManager.connect();
        PreparedStatement checkGmail = connection.prepareStatement(
                "SELECT * FROM person WHERE gmail = ?");
        checkGmail.setString(1, gmail.toLowerCase(Locale.ROOT).strip());
        ResultSet resultSet = checkGmail.executeQuery();
        if (resultSet.next()) {
            user.setGivenName(resultSet.getString("given_name"));
            user.setFamilyName(resultSet.getString("family_name"));
            user.setGmail(resultSet.getString("gmail"));
            user.setCountry(resultSet.getString("country"));
            user.setCity(resultSet.getString("city"));

            resultSet.close();
            checkGmail.close();
            connection.close();
            databaseManager.disconnect();
            return user;
        }
        resultSet.close();
        checkGmail.close();
        connection.close();
        databaseManager.disconnect();
        return null;

    }

    /**
     * <h2>Check User</h2>
     * <p>This method checks the availability of user</p>
     * @param gmail is gmail provided for the test
     * @return boolean, true if user is listed in the db and false if not
     * @throws SQLException if sth is wrong w/ sql query or database
     */
    private boolean checkUser(String gmail) throws SQLException {
        Connection connection = databaseManager.connect();

        PreparedStatement checkGmail = connection.prepareStatement(
                "SELECT gmail FROM person WHERE gmail = ?");
        checkGmail.setString(1, gmail.toLowerCase(Locale.ROOT).strip());
        ResultSet result = checkGmail.executeQuery();
        if (result.next()){
            if(gmail.strip().toLowerCase(Locale.ROOT).equals(
                    result.getString("gmail").strip())){
                checkGmail.close();
                result.close();
                connection.close();
                databaseManager.disconnect();
                return true;
            }

        }
        checkGmail.close();
        result.close();
        connection.close();
        databaseManager.disconnect();
        return false;
    }

    /**
     * <h2>Check Login</h2>
     * <p>This method checks for login credential match</p>
     * @param gmail is provided for the test
     * @param pass is provided for the test
     * @return boolean, true if gmail, pass matches and false if not
     * @throws SQLException if sth is wrong w/ sql query or database
     */
    private boolean checkLogin(String gmail, String pass) throws SQLException {
        Connection connection = databaseManager.connect();

        PreparedStatement checkGmail = connection.prepareStatement(
                "SELECT gmail FROM person WHERE gmail = ?");
        checkGmail.setString(1, gmail.toLowerCase(Locale.ROOT).strip());
        ResultSet result = checkGmail.executeQuery();
        if (result.next()){
            if(gmail.strip().toLowerCase(Locale.ROOT).equals(
                    result.getString("gmail").strip())){
                PreparedStatement checkPass = connection.prepareStatement("SELECT pass FROM person " +
                        "WHERE gmail = ?");
                checkPass.setString(1, gmail.toLowerCase(Locale.ROOT).strip());
                ResultSet result0 = checkPass.executeQuery();
                if(result0.next()){
                    if(pass.equals(result0.getString("pass"))){
                        result0.close();
                        result.close();
                        checkPass.close();
                        checkGmail.close();
                        connection.close();
                        databaseManager.disconnect();
                        return true;
                    }
                    else {
                        result0.close();
                        result.close();
                        checkPass.close();
                        checkGmail.close();
                        connection.close();
                        databaseManager.disconnect();
                        return false;
                    }

                }
                checkGmail.close();
                checkPass.close();
                result0.close();
                result.close();
                connection.close();
                databaseManager.disconnect();
                return false;
            }

        }
        checkGmail.close();
        result.close();
        connection.close();
        databaseManager.disconnect();
        return false;
    }

}


