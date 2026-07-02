package control;

import dao.DAOUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Servlet_Verificar extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");

        if (token == null || token.trim().isEmpty()) {
            response.sendRedirect("Servlet_Login?token_invalido=1");
            return;
        }

        DAOUsuario dao = new DAOUsuario();
        boolean verificado = dao.verificarCorreo(token.trim());

        if (verificado) {
            response.sendRedirect("Servlet_Login?verificado=1");
        } else {
            response.sendRedirect("Servlet_Login?token_invalido=1");
        }
    }
}
