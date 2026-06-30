<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelos.Registro"%>
<%@page import="modelos.Registros"%>

<%!
    Registros lista;
    Registro edit = null;
%>

<%
    if (request.getAttribute("lista") != null)
    {
        lista =   (Registros) request.getAttribute("lista");
        edit  =   (Registro)  request.getAttribute("edit");
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Calificaciones</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <header id="encabezado">
            <h1>Captura de Calificaciones</h1>
        </header>
        <div id="form_registro">
            <h2> <%= (edit !=null) ? "Modificar Calificasions" : "Registro de Calificasiones"%></h2>
            <form method="post" action="Servlet_Regitros">
                <input type="hidden" name="accion"         id="accion"         value="Agregar">
                <input type="hidden" name="tfMatriculaOld" id="tfMatriculaOld" value="<%= (edit !=null) ? edit.getMatricula() : "" %>">
                <input type="text"   name="tfMatricula"    id="tfMatricula"    value="<%= (edit !=null) ? edit.getMatricula() : "" %>" placeholder="Matricula" required>
                <input type="text"   name="tfNombre"       id="tfNombre"       value="<%= (edit !=null) ? edit.getNombre() :    "" %>" placeholder="Nombre"    required>
                <input type="text"   name="tfMateria"      id="tfMateria"      value="<%= (edit !=null) ? edit.getMateria() :   "" %>" placeholder="Materia"   required>
                <input type="number" name="tfP1"           id="tfP1"           value="<%= (edit !=null) ? edit.getP1() :        "" %>" placeholder="P1"        required min="0" max="10" step="0.1">
                <input type="number" name="tfP2"           id="tfP2"           value="<%= (edit !=null) ? edit.getP2() :        "" %>" placeholder="P2"        required min="0" max="10" step="0.1">
                <input type="number" name="tfP3"           id="tfP3"           value="<%= (edit !=null) ? edit.getP3() :        "" %>" placeholder="P3"        required min="0" max="10" step="0.1">
                <input type="submit" id="btnAccion" value=<%= (edit != null) ? "Modificar" : "Agregar"%>>
            </form>
        </div>
        <h2>Listado de Alumnos</h2>
        <div class="tabla-wrapper">
            <%=lista.mostrar()%>
        </div>
        <footer id="pie_pagina">
            <p>Sistema de Calificaciones</p>
        </footer>
        <script src="index.js"></script>
    </body>
</html>
