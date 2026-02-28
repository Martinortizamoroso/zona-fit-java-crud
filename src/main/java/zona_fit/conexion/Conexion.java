package zona_fit.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public static Connection getConexion() {
        Connection conexion = null;

        // Leemos de las variables de entorno del sistema
        var host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
        var port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
        var dbName = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "zona_fit_db";
        var usuario = System.getenv("DB_USER");
        var password = System.getenv("DB_PASS");

        var url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, password);
        } catch (Exception e) {
            System.out.println("Error al conectarnos a la base de datos: " + e.getMessage());
        }
        return conexion;
    }
}
