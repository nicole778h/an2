package org.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JdbcUtils {

    private Properties jdbcProps;

    private static final Logger logger = LogManager.getLogger(JdbcUtils.class);

    public JdbcUtils(Properties props){
        jdbcProps = props;
    }

    private Connection instance = null;

    private Connection getNewConnection(){
        logger.traceEntry();

        String url = jdbcProps.getProperty("jdbc.url");
        String user = jdbcProps.getProperty("jdbc.user");
        String pass = jdbcProps.getProperty("jdbc.pass");

        logger.info("trying to connect to database ... {} - user: {}", url, user);

        Connection con = null;
        try {
            // Se încarcă driverul PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Se creează conexiunea utilizând URL-ul, utilizatorul și parola
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            System.out.println("Error getting connection "+e);
        }
        return con;
    }

    public Connection getConnection(){
        logger.traceEntry();
        try {
            if (instance == null || instance.isClosed())
                instance = getNewConnection();
        } catch (SQLException e) {
            logger.error("Error DB", e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(instance);
        return instance;
    }
}
