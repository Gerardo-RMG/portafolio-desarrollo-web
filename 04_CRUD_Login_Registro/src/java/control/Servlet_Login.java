package control;

import dao.DAOUsuario;
import modelo.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class Servlet_Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            if (Boolean.TRUE.equals(session.getAttribute("esAdmin"))) {
                response.sendRedirect("Servlet_Admin");
            } else {
                response.sendRedirect("Servlet_Alumno");
            }
            return;
        }
        RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("tfUsuario");
        String clave   = request.getParameter("tfClave");

        DAOUsuario dao = new DAOUsuario();
        Usuario u = dao.validar(usuario, clave);

        String error = null;

        if (u == null) {
            error = "Usuario o contraseña incorrectos.";
        } else if (!u.isCorreoVerificado()) {
            error = "Debes verificar tu correo electrónico antes de continuar. " +
                    "Revisa tu bandeja de entrada y haz clic en el enlace de verificación.";
        } else if ("rechazado".equals(u.getEstado())) {
            error = "Tu solicitud de registro fue rechazada. " +
                    "Contacta al administrador para más información.";
        } else if ("pendiente".equals(u.getEstado())) {
            error = "Tu solicitud está pendiente de aprobación. " +
                    "El administrador revisará tu cuenta pronto.";
        } else {
            // estado = "activo" → acceso permitido
            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", u.getUsuario());
            session.setAttribute("nombre",  u.getNombre());
            session.setAttribute("esAdmin", u.isEsAdmin());
            if (u.isEsAdmin()) {
                response.sendRedirect("Servlet_Admin");
            } else {
                response.sendRedirect("Servlet_Alumno");
            }
            return;
        }

        request.setAttribute("error", error);
        RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
        rd.forward(request, response);
    }
}
