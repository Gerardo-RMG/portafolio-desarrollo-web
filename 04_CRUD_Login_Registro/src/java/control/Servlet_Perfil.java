package control;

import dao.DAOUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class Servlet_Perfil extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !Boolean.TRUE.equals(session.getAttribute("esAdmin"))) {
            response.sendRedirect("Servlet_Login");
            return;
        }

        String usuarioActual = (String) session.getAttribute("usuario");
        String nuevoUsuario  = request.getParameter("tfNuevoUsuario").trim();
        String claveActual   = request.getParameter("tfClaveActual");
        String nuevaClave    = request.getParameter("tfNuevaClave");
        String nuevaClave2   = request.getParameter("tfNuevaClave2");

        DAOUsuario dao = new DAOUsuario();

        // Verificar contraseña actual
        if (!dao.verificarClave(usuarioActual, claveActual)) {
            response.sendRedirect("Servlet_Admin?perfil_error=clave_incorrecta");
            return;
        }

        // Validar nuevo usuario
        if (nuevoUsuario.isEmpty()) {
            response.sendRedirect("Servlet_Admin?perfil_error=usuario_vacio");
            return;
        }

        // Si el usuario cambia, verificar que no esté en uso
        if (!nuevoUsuario.equals(usuarioActual) && dao.existeUsuario(nuevoUsuario)) {
            response.sendRedirect("Servlet_Admin?perfil_error=usuario_en_uso");
            return;
        }

        // Determinar la contraseña final
        String claveAGuardar;
        if (nuevaClave != null && !nuevaClave.isEmpty()) {
            if (!nuevaClave.equals(nuevaClave2)) {
                response.sendRedirect("Servlet_Admin?perfil_error=claves_no_coinciden");
                return;
            }
            if (nuevaClave.length() < 8) {
                response.sendRedirect("Servlet_Admin?perfil_error=clave_corta");
                return;
            }
            claveAGuardar = nuevaClave;
        } else {
            claveAGuardar = claveActual;   // sin cambios en la contraseña
        }

        // Guardar en la BD
        if (dao.actualizarPerfil(usuarioActual, nuevoUsuario, claveAGuardar)) {
            session.setAttribute("usuario", nuevoUsuario);
            response.sendRedirect("Servlet_Admin?perfil_ok=1");
        } else {
            response.sendRedirect("Servlet_Admin?perfil_error=db_error");
        }
    }
}
