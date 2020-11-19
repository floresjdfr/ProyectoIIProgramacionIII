package servidor;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Marvin Aguilar Fuentes
 * @author Jose David Flores Rodriguez
 */
public class BaseDatos extends MysqlDataSource {

    private BaseDatos() throws IOException {
        this.configuration = new Properties();
        configuration.load(getClass().getResourceAsStream(CONFIGURATION_PATH));

        setURL(String.format("%s//%s/%s",
                this.configuration.getProperty("protocol"),
                this.configuration.getProperty("server_url"),
                this.configuration.getProperty("database")

        ));
        setUser(this.configuration.getProperty("user"));
        setPassword(this.configuration.getProperty("password"));
    }

    @Override
    public Connection getConnection() throws SQLException {
        return super.getConnection();
    }

    public static BaseDatos obtenerInstancia() throws IOException {
        if (instancia == null) {
            try {
                instancia = new BaseDatos();
            } catch (IOException ex) {
                System.err.printf("Excepción: '%s'%n", ex.getMessage());
                throw ex;
            }
        }
        return instancia;
    }

    private static final String CONFIGURATION_PATH = "db.properties";
    private static BaseDatos instancia = null;
    private Properties configuration = null;
}
