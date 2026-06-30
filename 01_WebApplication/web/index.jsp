<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelos.Registro"%>
<%@page import="modelos.Registros"%>
<%
    Registros listas = (Registros) application.getAttribute("listas");
    if (listas == null) {
        listas = new Registros();
        listas.add(new Registro("202501", "Maia la del Barrio", "Desarrollo Web Integral", 9, 9, 8.5));
        listas.add(new Registro("202502", "Juan Perez Garcia", "Programacion Orientada a Objetos", 8, 7.5, 9));
        application.setAttribute("listas", listas);
    }

    String accion = request.getParameter("accion");

    if ("Agregar".equals(accion)) {
        Registro r = new Registro();
            r.setMatricula            (request.getParameter("tfMatricula"));
            r.setNombre               (request.getParameter("tfNombre"));
            r.setMateria              (request.getParameter("tfMateria"));
            r.setP1(Double.parseDouble(request.getParameter("tfP1")));
            r.setP2(Double.parseDouble(request.getParameter("tfP2")));
            r.setP3(Double.parseDouble(request.getParameter("tfP3")));
            listas.add(r);
        response.sendRedirect("index.jsp");
        return;

    } else if ("Modificar".equals(accion)) {
        Registro r = new Registro();
            r.setMatricula            (request.getParameter("tfMatricula"));
            r.setNombre               (request.getParameter("tfNombre"));
            r.setMateria              (request.getParameter("tfMateria"));
            r.setP1(Double.parseDouble(request.getParameter("tfP1")));
            r.setP2(Double.parseDouble(request.getParameter("tfP2")));
            r.setP3(Double.parseDouble(request.getParameter("tfP3")));
            listas.update             (request.getParameter("tfMatriculaOld"), r);
        response.sendRedirect("index.jsp");
        return;

    } else if ("Eliminar".equals(accion)) {
        listas.remove(request.getParameter("tfMatricula"));
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Calificaciones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="style.css">
</head>
<body class="bg-light">
<div class="container py-4">
    <div class="card shadow mb-4" id="formCard">
        <div class="card-header bg-primary text-white" id="formHeader">
            <h4 class="mb-0">Registro de Calificaciones</h4>
        </div>
        <div class="card-body">
            <form method="post" action="index.jsp">
                <input type="hidden" name="accion"         id="accion"         value="Agregar">
                <input type="hidden" name="tfMatriculaOld" id="tfMatriculaOld" value="">
                <div class="row g-2 align-items-end">
                    <div class="col-md-2"><label class="form-label fw-semibold">Matrícula</label><input type="text"   class="form-control" name="tfMatricula" id="tfMatricula"placeholder="Ej: 202501"      required></div>
                    <div class="col-md-3"><label class="form-label fw-semibold">Nombre   </label><input type="text"   class="form-control" name="tfNombre"    id="tfNombre"   placeholder="Nombre completo" required></div>
                    <div class="col-md-2"><label class="form-label fw-semibold">Materia  </label><input type="text"   class="form-control" name="tfMateria"   id="tfMateria"  placeholder="Materia"         required></div>
                    <div class="col-md-1"><label class="form-label fw-semibold">P1       </label><input type="number" class="form-control" name="tfP1"        id="tfP1"       placeholder="0-10" min="0" max="10" step="0.1" required></div>
                    <div class="col-md-1"><label class="form-label fw-semibold">P2       </label><input type="number" class="form-control" name="tfP2"        id="tfP2"       placeholder="0-10" min="0" max="10" step="0.1" required></div>
                    <div class="col-md-1"><label class="form-label fw-semibold">P3       </label><input type="number" class="form-control" name="tfP3"        id="tfP3"       placeholder="0-10" min="0" max="10" step="0.1" required></div>
                    <div class="col-md-2"><button type="submit" class="btn btn-success w-100" id="btnAccion">Agregar</button>
                    <button type="button" class="btn btn-secondary w-100 mt-1"id="btnCancelar" style="display:none" onclick="cancelar()">✖ Cancelar</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="card shadow">
        <div class="card-header bg-secondary text-white">
            <h5 class="mb-0">Listado de Alumnos</h5>
        </div>
        <div class="card-body table-responsive">
            <%=listas.mostrar()%>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="index.js"></script>
</body>
</html>
