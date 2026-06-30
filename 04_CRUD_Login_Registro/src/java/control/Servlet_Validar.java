package control;

import dao.DAOUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Servlet_Validar extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store");

        String tipo = request.getParameter("tipo");
        DAOUsuario dao = new DAOUsuario();
        boolean existe = false;

        if ("usuario".equals(tipo)) {
            String valor = request.getParameter("valor");
            if (valor != null && !valor.trim().isEmpty())
                existe = dao.existeUsuario(valor.trim());

        } else if ("correo".equals(tipo)) {
            String valor = request.getParameter("valor");
            if (valor != null && !valor.trim().isEmpty())
                existe = dao.existeCorreo(valor.trim());

        } else if ("nombre".equals(tipo)) {
            String nombres = request.getParameter("nombres");
            String paterno = request.getParameter("paterno");
            String materno = request.getParameter("materno");
            if (nombres != null && paterno != null)
                existe = dao.existeNombre(
                    nombres.trim(),
                    paterno.trim(),
                    materno != null ? materno.trim() : ""
                );
        }

        PrintWriter out = response.getWriter();
        out.print("{\"existe\":" + existe + "}");
    }
}
