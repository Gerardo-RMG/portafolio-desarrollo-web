package control;

import dao.DAOUsuario;
import modelo.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Servlet_Registro extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/registro.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombres  = request.getParameter("tfNombres") .trim();
        String paterno  = request.getParameter("tfPaterno") .trim();
        String materno  = request.getParameter("tfMaterno") .trim();
        String nombre   = nombres + " " + paterno + " " + materno;
        String correo   = request.getParameter("tfCorreo")  .trim();
        String usuario  = request.getParameter("tfUsuario") .trim();
        String clave    = request.getParameter("tfClave");
        String clave2   = request.getParameter("tfClave2");

        String error = null;

        if (nombres.isEmpty() || paterno.isEmpty() || correo.isEmpty() || usuario.isEmpty() || clave.isEmpty()) {
            error = "Todos los campos son obligatorios.";
        } else if (!clave.equals(clave2)) {
            error = "Las contraseñas no coinciden.";
        } else if (clave.length() < 8) {
            error = "La contraseña debe tener al menos 8 caracteres.";
        } else {
            DAOUsuario dao = new DAOUsuario();
            if (dao.existeUsuario(usuario)) {
                error = "El nombre de usuario <strong>" + usuario + "</strong> ya está en uso.";
            } else if (dao.existeCorreo(correo)) {
                error = "El correo <strong>" + correo + "</strong> ya está registrado.";
            } else {
                Usuario u = new Usuario();
                u.setNombre(nombre);
                u.setCorreo(correo);
                u.setUsuario(usuario);
                u.setClave(clave);

                if (dao.registrar(u)) {
                    response.sendRedirect("Servlet_Login?exito=1");
                    return;
                }
                error = "Error al guardar el registro. Inténtalo de nuevo.";
            }
        }

        request.setAttribute("error",   error);
        request.setAttribute("nombres", nombres);
        request.setAttribute("paterno", paterno);
        request.setAttribute("materno", materno);
        request.setAttribute("correo",  correo);
        request.setAttribute("usuario", usuario);
        RequestDispatcher rd = request.getRequestDispatcher("/registro.jsp");
        rd.forward(request, response);
    }
}
