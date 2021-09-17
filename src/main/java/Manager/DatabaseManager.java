package Manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * <h2>Database Manager</h2>
 * <h3>This model provides an connection with other useful methods</h3>
 * @author Anurag-Bharati
 * @since 2021
 * @version 1.0
 */

public class DatabaseManager {
    private Properties properties;
    private static final String MAX_POOL = "250";
    Connection connection = null;


    public Connection connect(){
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost/twilight", getProperties());

            } catch (SQLException throwable) {
                throwable.printStackTrace();
                System.out.println("[DATABASE] NO CONNECTION!");
            }
        }
        return connection;
    }

    /**
     * <h2>DBProperties Packager</h2>
     * <p>Packages the properties that is used to get the db connection</p>
     * @return Returns property containing database username, database pass and maxPoolSize
     */
    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", PersonalDataField.DATABASE_USER);
            properties.setProperty("password", PersonalDataField.DATABASE_PASS);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }

    /**
     * <h2>Disconnects Connection</h2>
     * <p>Checks for null connection and if its not closes and sets it null</p>
     */
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
