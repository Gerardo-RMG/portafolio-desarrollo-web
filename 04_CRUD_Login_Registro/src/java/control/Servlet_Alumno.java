package control;

import modelo.Alumno;
import dao.DAOAlumno;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/SAlumno") public class Servlet_Alumno extends HttpServlet 
{

    private Alumno alumno;
    private DAOAlumno dao;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            try { response.sendRedirect("Servlet_Login"); } catch (IOException ex) {}
            return;
        }

        if ("Salir".equals(request.getParameter("accion"))) {
            session.invalidate();
            try { response.sendRedirect("Servlet_Login"); } catch (IOException ex) {}
            return;
        }

        Alumno edit = null;
        try
        {
            String accion = request.getParameter("accion");
            if ("Agregar".equals(accion)) 
                {
                alumno = new Alumno();
                alumno.setNL(Integer.parseInt(request.getParameter("tfNL")));
                alumno.setNombre(request.getParameter("tfNombre"));
                alumno.setPaterno(request.getParameter("tfPaterno"));
                alumno.setMaterno(request.getParameter("tfMaterno"));

                dao = new DAOAlumno();
                dao.agregar(alumno);

                request.setAttribute("edit", null);

                RequestDispatcher rd = request.getRequestDispatcher("/alumnos.jsp");
                rd.forward(request, response);
            }
            else if ("Editar".equals(accion)) 
            {
                dao = new DAOAlumno();
                edit = dao.buscar(Integer.parseInt(request.getParameter("tfNL")));

                request.setAttribute("lista", Boolean.TRUE);
                request.setAttribute("edit", edit);

                RequestDispatcher rd = request.getRequestDispatcher("/alumnos.jsp");
                rd.forward(request, response);
            }
            else if ("Modificar".equals(accion)) 
            {
                alumno = new Alumno();
                alumno.setNL(Integer.parseInt(request.getParameter("tfNL")));
                alumno.setNombre(request.getParameter("tfNombre"));
                alumno.setPaterno(request.getParameter("tfPaterno"));
                alumno.setMaterno(request.getParameter("tfMaterno"));


                dao = new DAOAlumno();
                dao.editar(alumno, Integer.parseInt(request.getParameter("tfNLold")));

                edit = null;
                request.setAttribute("edit", edit);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/alumnos.jsp");
                rd.forward(request, response);

            }
            else if ("Eliminar".equals(accion)) 
            {
                int nl = Integer.parseInt(request.getParameter("tfNL"));
                dao = new DAOAlumno();
                dao.eliminar(nl);

                request.setAttribute("edit", null);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/alumnos.jsp");
                rd.forward(request, response);

            }
            else 
            {
                request.setAttribute("edit", null);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/alumnos.jsp");
                rd.forward(request, response);
            }
        }catch (ServletException | IOException ex) 
        {
            Logger.getLogger(Servlet_Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

