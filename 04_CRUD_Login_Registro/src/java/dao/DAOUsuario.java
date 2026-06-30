package dao;

import conexion.ConexionMySQL;
import modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUsuario {

    /** Busca al usuario por credenciales y devuelve el objeto con todos sus datos (incluyendo activo). */
    public Usuario validar(String usuario, String clave) {
        String sql = "SELECT usuario, clave, nombre, correo, activo FROM usuarios WHERE usuario = ? AND clave = ?";
        try (Connection con = ConexionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, clave);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setUsuario(rs.getString("usuario"));
                u.setClave(rs.getString("clave"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setActivo(rs.getBoolean("activo"));
                return u;
            }
        } catch (SQLException ex) {
            System.err.println("Error al validar usuario: " + ex.getMessage());
        }
        return null;
    }

    public boolean existeUsuario(String usuario) {
        String sql = "SELECT 1 FROM usuarios WHERE usuario = ?";
        try (Connection con = ConexionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            return ps.executeQuery().next();
        } catch (SQLException ex) {
            System.err.println("Error al verificar usuario: " + ex.getMessage());
        }
        return false;
    }

    public boolean existeCorreo(String correo) {
        String sql = "SELECT 1 FROM usuarios WHERE correo = ?";
        try (Connection con = ConexionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            return ps.executeQuery().next();
        } catch (SQLException ex) {
            System.err.println("Error al verificar correo: " + ex.getMessage());
        }
        return false;
    }

    public boolean existeNombre(String nombres, String paterno, String materno) {
        String nombreCompleto = (nombres + " " + paterno +
                                (materno.isEmpty() ? "" : " " + materno)).trim();
        String sql = "SELECT 1 FROM usuarios WHERE nombre = ?";
        try (Connection con = ConexionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreCompleto);
            return ps.executeQuery().next();
        } catch (SQLException ex) {
            System.err.println("Error al verificar nombre: " + ex.getMessage());
        }
        return false;
    }

    public boolean registrar(Usuario u) {
        String sql = "INSERT INTO usuarios (usuario, clave, nombre, correo, activo) VALUES (?, ?, ?, ?, 1)";
        try (Connection con = ConexionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getClave());
            ps.setString(3, u.getNombre());
            ps.setString(4, u.getCorreo());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al registrar usuario: " + ex.getMessage());
        }
        return false;
    }
}
