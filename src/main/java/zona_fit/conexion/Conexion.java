package zona_fit.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public static Connection getConexion()
    {
        Connection conexion = null;
        var baseDatos = "zona_fit_db";
        var url = "jdbc:mysql://localhost:3306/"+baseDatos;
        var usuario = "root";
        var password = "Test12345.";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url,usuario,password);
        } catch (Exception e) {
            System.out.println("Error al conectarnos a la base de datos " + e.getMessage() );
        }
        return conexion;
    }

    static void main(String[] args) {
        var conexion  = Conexion.getConexion();
        if(conexion != null)
        {
            System.out.println("Se ha podido conectar con la DB " + conexion);
        }
        else
        {
            System.out.println("No se puede generar una coneccion a la DB");
        }
    }
}
