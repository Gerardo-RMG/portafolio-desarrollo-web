package control;

import dao.DAOUsuario;
import modelo.Usuario;
import util.EnviarCorreo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

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

        String nombres  = request.getParameter("tfNombres").trim();
        String paterno  = request.getParameter("tfPaterno").trim();
        String materno  = request.getParameter("tfMaterno").trim();
        String nombre   = nombres + " " + paterno + (materno.isEmpty() ? "" : " " + materno);
        String correo   = request.getParameter("tfCorreo").trim();
        String usuario  = request.getParameter("tfUsuario").trim();
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
                // Generar token de verificación
                String token = UUID.randomUUID().toString().replace("-", "");

                Usuario u = new Usuario();
                u.setNombre(nombre);
                u.setCorreo(correo);
                u.setUsuario(usuario);
                u.setClave(clave);
                u.setEstado("pendiente");
                u.setTokenCorreo(token);

                if (dao.registrar(u)) {
                    // URL dinámica de verificación
                    String baseUrl = request.getScheme() + "://" +
                                     request.getServerName() + ":" +
                                     request.getServerPort() +
                                     request.getContextPath();
                    String urlVerif = baseUrl + "/Servlet_Verificar?token=" + token;

                    // Intentar enviar correo (falla silenciosamente si no está configurado)
                    boolean enviado = false;
                    try {
                        String cuerpo = EnviarCorreo.cuerpoVerificacion(nombre, urlVerif);
                        enviado = EnviarCorreo.enviar(correo, "Verifica tu correo — Sistema de Alumnos", cuerpo);
                    } catch (Throwable t) {
                        System.err.println("Sistema de correo no disponible: " + t.getMessage());
                    }

                    // Mostrar pantalla de éxito en registro.jsp
                    request.setAttribute("registrado",    true);
                    request.setAttribute("correoEnviado", enviado);
                    request.setAttribute("correoDestino", correo);
                    if (!enviado) {
                        request.setAttribute("urlVerificacion", urlVerif);
                    }
                    RequestDispatcher rd = request.getRequestDispatcher("/registro.jsp");
                    rd.forward(request, response);
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
