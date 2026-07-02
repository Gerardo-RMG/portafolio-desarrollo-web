package control;

import dao.DAOUsuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class Servlet_Admin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("Servlet_Login");
            return;
        }
        if (!Boolean.TRUE.equals(session.getAttribute("esAdmin"))) {
            response.sendRedirect("Servlet_Alumno");
            return;
        }

        DAOUsuario dao = new DAOUsuario();
        request.setAttribute("todos", dao.obtenerTodos());

        // Mensajes de acciones sobre solicitudes
        String ok = request.getParameter("ok");
        if ("aceptado".equals(ok)) {
            request.setAttribute("mensaje", "Usuario aceptado. Ya puede iniciar sesión.");
        } else if ("rechazado".equals(ok)) {
            request.setAttribute("mensaje", "Solicitud rechazada correctamente.");
        }

        // Mensajes de perfil
        String perfilOk    = request.getParameter("perfil_ok");
        String perfilError = request.getParameter("perfil_error");

        if ("1".equals(perfilOk)) {
            request.setAttribute("mensaje", "Perfil actualizado correctamente.");
        } else if ("clave_incorrecta".equals(perfilError)) {
            request.setAttribute("perfilError", "La contraseña actual es incorrecta.");
        } else if ("usuario_vacio".equals(perfilError)) {
            request.setAttribute("perfilError", "El nombre de usuario no puede estar vacío.");
        } else if ("usuario_en_uso".equals(perfilError)) {
            request.setAttribute("perfilError", "Ese nombre de usuario ya está en uso.");
        } else if ("claves_no_coinciden".equals(perfilError)) {
            request.setAttribute("perfilError", "Las contraseñas nuevas no coinciden.");
        } else if ("clave_corta".equals(perfilError)) {
            request.setAttribute("perfilError", "La nueva contraseña debe tener al menos 8 caracteres.");
        } else if ("db_error".equals(perfilError)) {
            request.setAttribute("perfilError", "Error al guardar. Inténtalo de nuevo.");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/admin.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !Boolean.TRUE.equals(session.getAttribute("esAdmin"))) {
            response.sendRedirect("Servlet_Login");
            return;
        }

        String accion  = request.getParameter("accion");
        String usuario = request.getParameter("usuario");

        if (usuario != null && !usuario.trim().isEmpty()) {
            DAOUsuario dao = new DAOUsuario();
            if ("Aceptar".equals(accion)) {
                dao.aceptarUsuario(usuario.trim());
                response.sendRedirect("Servlet_Admin?ok=aceptado");
                return;
            } else if ("Rechazar".equals(accion)) {
                dao.rechazarUsuario(usuario.trim());
                response.sendRedirect("Servlet_Admin?ok=rechazado");
                return;
            }
        }
        response.sendRedirect("Servlet_Admin");
    }
}
