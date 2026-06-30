<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Alumno"%>
<%@page import="dao.DAOAlumno"%>

<%!
    DAOAlumno lista = new DAOAlumno();
    Alumno edit = null;
%>

<%
    edit = null;
    if (request.getAttribute("lista") != null)
    {
        edit = (Alumno) request.getAttribute("edit");
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BD Alumnos con Servlet</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>

        <div id="encabezado">
            <h1>Bases de datos MySQL con JSP</h1>
        </div>

        <div id="contenido">

            <div id="form_registro">
                <h2><%= (edit != null) ? "Modificar Alumno" : "Registro de Alumnos" %></h2>
                <form method="post">
                    <input type="hidden" name="accion"   value="<%= (edit != null) ? "Modificar" : "Agregar" %>"/>
                    <input type="hidden" name="tfNLold"  value="<%= (edit != null) ? edit.getNL() : "" %>"/>
                    <input type="text"   name="tfNL"     value="<%= (edit != null) ? edit.getNL()      : "" %>" placeholder="Número de lista" required/>
                    <input type="text"   name="tfNombre" value="<%= (edit != null) ? edit.getNombre()  : "" %>" placeholder="Nombre"          required/>
                    <input type="text"   name="tfPaterno"value="<%= (edit != null) ? edit.getPaterno() : "" %>" placeholder="Apellido Paterno" required/>
                    <input type="text"   name="tfMaterno"value="<%= (edit != null) ? edit.getMaterno() : "" %>" placeholder="Apellido Materno" required/>
                    <input type="submit" value="<%= (edit != null) ? "Modificar" : "Agregar" %>">
                </form>
            </div>

            <div id="tabla_wrapper">
                <%= lista.mostrar() %>
            </div>

        </div>

        <div id="pie_pagina">
            <p> Base de datos MySQL con JSP &amp; Servlet</p>
        </div>

    </body>
</html>
