package Manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
    // Properties for a connection
    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", PersonalDataField.DATABASE_USER);
            properties.setProperty("password", PersonalDataField.DATABASE_PASS);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }

    // Disconnect database
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
