package dao;

import conexion.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Alumno;

public class DAOAlumno {

    private Connection        con;
    private PreparedStatement ps;
    private ResultSet         rs;
    private Alumno            alumno;

    
    private ArrayList<Alumno> lista() 
    {
        ArrayList<Alumno> lista = new ArrayList<>();
        String sql="SELECT * FROM alumnos";
        
        try 
        {
            con = ConexionMySQL.getConexion();
            ps  = con.prepareStatement(sql);
            rs  = ps.executeQuery();
            while (rs.next()) 
            {
                alumno = new Alumno();
                alumno.setNL(rs.getInt("NL"));
                alumno.setNombre(rs.getString("Nombre"));
                alumno.setPaterno(rs.getString("Paterno"));
                alumno.setMaterno(rs.getString("Materno"));
                lista.add(alumno);
            }
            rs.close(); 
            ps.close(); 
            con.close();
        } 
        catch (SQLException e) {}
        return lista;
    }

    public String mostrar() 
    {
        String r, fila;
        r = """
             <tr><tr>
             <table border="0">
             <caption>Listado de Alumnos</caption>
             <thead>
             <tr>
             <th>NL</th>   
                <th>Nombre</th>
                <th>Paterno</th>
                <th>Materno</th>
                <th colspan="2">Acciones</th>
            </tr>
            </thead>
             <tbody>
            """;
        for (Alumno reg : lista())
            {
            /*
            r=r + "<tr>"
            + "<td>" + reg.getNL() + "</td>\n"
            + "<td>" + reg.getNombre() + "</td>\n"
            + "<td>" + reg.getPaterno() + "</td>\n"
            + "<td>" + reg.getMaterno() + "</td>\n"
            + "<td>\n"
            + "<form method='post'>\n"
            + "<input type='hidden' name='accion' value='Editar'>\n"
            + "<input type='hidden' name='tfNL' value='" + reg.getNL() + "'>\n"
            + "<input type='submit' value='Editar'>\n"
            + "</form>\n"
            + "</td>\n"
            + "<td>\n"
            + "<form method='post'>\n"
            + "<input type='hidden' name='accion' value='Eliminar'>\n"
            + "<input type='hidden' name='tfNL' value='" + reg.getNL() + "'>\n"
            + "<input type='submit' value='Eliminar' onclick=\"return confirm('¿Eliminar?')\">\n"
            + "</form>\n"
            + "</td>\n"
            + "</tr>\n"
             */
                
            fila ="""
                 <tr>
                    <td>%d</td>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%s</td>
                    <td>
                        <form method='post'>
                            <input type='hidden' name='accion' value='Editar'>
                            <input type='hidden' name='tfNL' value='%d'>
                            <input type='submit' class='btn-editar' value='Editar'>
                        </form>
                    </td>
                    <td>
                        <form method='post'>
                            <input type='hidden' name='accion' value='Eliminar'>
                            <input type='hidden' name='tfNL' value='%d'>
                            <input type='submit' class='btn-eliminar' value='Eliminar' onclick="return confirm('¿Eliminar?')">
                        </form>
                    </td>
                </tr>
                """;
               r=r+String.format(fila, reg.getNL(), reg.getNombre(), reg.getPaterno(), reg.getMaterno(), reg.getNL(), reg.getNL());
            }
            r = r + """
                 </tbody>
                 </table>
                """;
        return r;
       
    }

    public Alumno buscar(int nl) 
    {
        Alumno alumno = null;
        String sql = "SELECT * FROM alumnos WHERE NL = ?";

        try {
            con = ConexionMySQL.getConexion();
            ps  = con.prepareStatement(sql);
            ps.setInt(1, nl);
            rs  = ps.executeQuery();

            while (rs.next())
                {
                alumno = new Alumno();
                alumno.setNL(rs.getInt("NL"));
                alumno.setNombre(rs.getString("Nombre"));
                alumno.setPaterno(rs.getString("Paterno"));
                alumno.setMaterno(rs.getString("Materno"));
            }
            rs.close(); 
            ps.close(); 
            con.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return alumno;
    }

    public boolean agregar(Alumno alumno)
    {
        String sql = "INSERT INTO alumnos VALUES (?, ?, ?, ?)";

        try
        {
            con = ConexionMySQL.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, alumno.getNL());
            ps.setString(2, alumno.getNombre());
            ps.setString(3, alumno.getPaterno());
            ps.setString(4, alumno.getMaterno());
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return true;
    }

    public boolean editar(Alumno alumno, int old)
    {
        String sql = "UPDATE alumnos SET " +
                "NL = " + alumno.getNL() + "," +
                "Nombre = '" + alumno.getNombre() + "'," +
                "Paterno = '" + alumno.getPaterno() + "'," +
                "Materno = '" + alumno.getMaterno() + "' " +
                "WHERE NL = " + old;

        try 
        {
            con = ConexionMySQL.getConexion();
            ps  = con.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return true;
    }

    public boolean eliminar(int nl)
    {
        String sql = "DELETE FROM alumnos WHERE NL = " + nl;

        try 
        {
            con = ConexionMySQL.getConexion();
            ps  = con.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return true;
    }


}
