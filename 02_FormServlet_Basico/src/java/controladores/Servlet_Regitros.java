
package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Registro;
import modelos.Registros;

public class Servlet_Regitros extends HttpServlet
{
    private Registros lista;
    private Registro r;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    {
        response.setContentType("text/html;charset=UTF-8");
        Registro edit =null;
        
        try
        {
            PrintWriter out = response.getWriter();
            
            if (lista == null)
            {
                lista = new Registros();
                lista.add(new Registro("A001", "Juan Perez",   "Matematicas", "8.5", "9.0", "7.5"));
                lista.add(new Registro("A002", "Maria Lopez",  "Fisica",      "9.0", "8.5", "9.5"));
            }
            String accion = request.getParameter("accion");
            if ("Agregar".equals(accion))
            {
                r = new Registro();
                r.setMatricula            (request.getParameter("tfMatricula"));
                r.setNombre               (request.getParameter("tfNombre"));
                r.setMateria              (request.getParameter("tfMateria"));
                r.setP1(Double.parseDouble(request.getParameter("tfP1")));
                r.setP2(Double.parseDouble(request.getParameter("tfP2")));
                r.setP3(Double.parseDouble(request.getParameter("tfP3")));
                lista.add(r);
                response.sendRedirect     (request.getContextPath() + "/Servlet_Regitros");
            }
            else if ("Editar".equals(accion))
            {
                edit = lista.find(request.getParameter("tfMatricula"));
                
                request.setAttribute("lista", lista);
                request.setAttribute("edit",  edit);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            }
            else if ("Modificar".equals(accion))
            {
            r = new Registro();
            r.setMatricula            (request.getParameter("tfMatricula"));
            r.setNombre               (request.getParameter("tfNombre"));
            r.setMateria              (request.getParameter("tfMateria"));
            r.setP1(Double.parseDouble(request.getParameter("tfP1")));
            r.setP2(Double.parseDouble(request.getParameter("tfP2")));
            r.setP3(Double.parseDouble(request.getParameter("tfP3")));
            lista.update              (request.getParameter("tfMatriculaOld"), r);
            response.sendRedirect     (request.getContextPath() + "/Servlet_Regitros");
            }
            else if ("Eliminar".equals(accion))
            {
                String matricula = request.getParameter("tfMatricula");
                lista.remove(matricula);
                response.sendRedirect(request.getContextPath() + "/Servlet_Regitros");
            }
            else
            {
                request.setAttribute("lista", lista);
                request.setAttribute("edit",  null);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            }

        }
        catch (IOException | ServletException ex)
        {
            Logger.getLogger(Servlet_Regitros.class.getName()).log(Level.SEVERE, null, ex);
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
