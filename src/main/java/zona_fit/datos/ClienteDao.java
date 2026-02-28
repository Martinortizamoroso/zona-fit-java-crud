package zona_fit.datos;

import zona_fit.dominio.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static zona_fit.conexion.Conexion.getConexion;

public class ClienteDao implements IClienteDAO {
    @Override
    public List<Cliente> listaClientes() {
        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        var sql = "SELECT * FROM Cliente ORDER BY id";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                var cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar la coneccion " + e.getMessage());
            }
        }
        return clientes;
    }

    @Override
    public boolean buscarClientePorId(Cliente cliente) {
        PreparedStatement ps;
        ResultSet rs;
        try (Connection con = getConexion()) {
            var sql = "SELECT * FROM Cliente WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));

                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar Cliente por id " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        PreparedStatement ps;
        try (Connection con = getConexion()) {
            try {
                var sql = "INSERT INTO Cliente(nombre,apellido,membresia) " +
                        " VALUES(?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getApellido());
                ps.setInt(3, cliente.getMembresia());
                ps.execute();
                return true;
            } catch (Exception e) {
                System.out.println("Error al agregar Cliente " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error al cerar la conexion: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        PreparedStatement ps;
        try (Connection con = getConexion()) {
            try {
                var sql = "UPDATE Cliente SET nombre=?, apellido=?, membresia=? WHERE id=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getApellido());
                ps.setInt(3, cliente.getMembresia());
                ps.setInt(4, cliente.getId());
                ps.execute();
                return true;
            } catch (Exception e) {
                System.out.println("Error al modificar Cliente " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar la conexion: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        PreparedStatement ps;
        try (Connection con = getConexion()) {
            try {
                var sql = "DELETE FROM cliente WHERE id = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, cliente.getId());
                ps.execute();
                return true;
            } catch (SQLException e) {
                System.out.println("Error al borrar el cliente " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error al cerrar la conexion " + e.getMessage());
        }
        return false;
    }
}
