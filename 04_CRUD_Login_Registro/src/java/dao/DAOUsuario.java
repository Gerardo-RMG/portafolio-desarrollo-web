package dao;

import conexion.ConexionMySQL;
import modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOUsuario {

    /** Busca al usuario por credenciales; devuelve el objeto con todos sus datos. */
    public Usuario validar(String usuario, String clave) {
        String sql = "SELECT usuario, clave, nombre, correo, activo, estado, " +
                     "correo_verificado, es_admin FROM usuarios WHERE usuario = ? AND clave = ?";
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
                u.setEstado(rs.getString("estado"));
                u.setCorreoVerificado(rs.getBoolean("correo_verificado"));
                u.setEsAdmin(rs.getBoolean("es_admin"));
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

    /** Registra el usuario como pendiente con el token de verificación. */
    public boolean registrar(Usuario u) {
        String sql = "INSERT INTO usuarios (usuario, clave, nombre, correo, activo, estado, correo_verificado, token_correo) " +
                     "VALUES (?, ?, ?, ?, 0, 'pendiente', 0, ?)";
        try (Connection con = ConexionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getClave());
            ps.setString(3, u.getNombre());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getTokenCorreo());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al registrar usuario: " + ex.getMessage());
        }
        return false;
    }

    /** Verifica el correo usando el token; devuelve true si el token era válido. */
    public boolean verificarCorreo(String token) {
        String sql = "UPDATE usuarios SET correo_verificado = 1, token_correo = NULL " +
                     "WHERE token_correo = ? AND correo_verificado = 0";
        try (Connection con = ConexionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, token);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al verificar correo: " + ex.getMessage());
        }
        return false;
    }

    /** Devuelve todos los usuarios con estado 'pendiente'. */
    public List<Usuario> obtenerPendientes() {
        String sql = "SELECT usuario, nombre, correo, correo_verificado, " +
                     "DATE_FORMAT(fecha_registro,'%d/%m/%Y %H:%i') AS fecha " +
                     "FROM usuarios WHERE estado = 'pendiente' ORDER BY fecha_registro ASC";
        List<Usuario> lista = new ArrayList<>();
        try (Connection con = ConexionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setUsuario(rs.getString("usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setCorreoVerificado(rs.getBoolean("correo_verificado"));
                u.setFechaRegistro(rs.getString("fecha"));
                lista.add(u);
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener pendientes: " + ex.getMessage());
        }
        return lista;
    }

    /** Devuelve todos los usuarios (para la vista de administrador). */
    public List<Usuario> obtenerTodos() {
        String sql = "SELECT usuario, nombre, correo, correo_verificado, estado, es_admin, " +
                     "DATE_FORMAT(fecha_registro,'%d/%m/%Y %H:%i') AS fecha " +
                     "FROM usuarios ORDER BY fecha_registro DESC";
        List<Usuario> lista = new ArrayList<>();
        try (Connection con = ConexionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setUsuario(rs.getString("usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setCorreoVerificado(rs.getBoolean("correo_verificado"));
                u.setEstado(rs.getString("estado"));
                u.setEsAdmin(rs.getBoolean("es_admin"));
                u.setFechaRegistro(rs.getString("fecha"));
                lista.add(u);
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener usuarios: " + ex.getMessage());
        }
        return lista;
    }

    /** Devuelve [total, pendientes, activos, rechazados]. */
    public int[] obtenerEstadisticas() {
        String sql = "SELECT COUNT(*) AS total, " +
                     "SUM(estado='pendiente') AS pendientes, " +
                     "SUM(estado='activo') AS activos, " +
                     "SUM(estado='rechazado') AS rechazados FROM usuarios";
        try (Connection con = ConexionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new int[]{
                    rs.getInt("total"),
                    rs.getInt("pendientes"),
                    rs.getInt("activos"),
                    rs.getInt("rechazados")
                };
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener estadísticas: " + ex.getMessage());
        }
        return new int[]{0, 0, 0, 0};
    }

    /** Acepta la solicitud: activa la cuenta del usuario. */
    public boolean aceptarUsuario(String usuario) {
        String sql = "UPDATE usuarios SET estado = 'activo', activo = 1 WHERE usuario = ?";
        try (Connection con = ConexionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al aceptar usuario: " + ex.getMessage());
        }
        return false;
    }

    /** Rechaza la solicitud: bloquea la cuenta del usuario. */
    public boolean rechazarUsuario(String usuario) {
        String sql = "UPDATE usuarios SET estado = 'rechazado', activo = 0 WHERE usuario = ?";
        try (Connection con = ConexionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al rechazar usuario: " + ex.getMessage());
        }
        return false;
    }

    /** Comprueba que la contraseña coincida con la almacenada en la BD. */
    public boolean verificarClave(String usuario, String clave) {
        String sql = "SELECT 1 FROM usuarios WHERE usuario = ? AND clave = ?";
        try (Connection con = ConexionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, clave);
            return ps.executeQuery().next();
        } catch (SQLException ex) {
            System.err.println("Error al verificar clave: " + ex.getMessage());
        }
        return false;
    }

    /** Actualiza el nombre de usuario y/o la contraseña del administrador. */
    public boolean actualizarPerfil(String usuarioActual, String nuevoUsuario, String nuevaClave) {
        String sql = "UPDATE usuarios SET usuario = ?, clave = ? WHERE usuario = ?";
        try (Connection con = ConexionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoUsuario);
            ps.setString(2, nuevaClave);
            ps.setString(3, usuarioActual);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al actualizar perfil: " + ex.getMessage());
        }
        return false;
    }
}
