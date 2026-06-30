/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function editar(matricula, nombre, materia, p1, p2, p3)

{
    document.getElementById('tfMatricula')    .value = matricula;
    document.getElementById('tfMatriculaOld') .value = matricula;
    document.getElementById('tfNombre')       .value = nombre;
    document.getElementById('tfMateria')      .value = materia;
    document.getElementById('tfP1')           .value = p1;
    document.getElementById('tfP2')           .value = p2;
    document.getElementById('tfP3')           .value = p3;
    document.getElementById('accion')         .value = 'Modificar';
    document.getElementById('btnAccion')      .value = 'Modificar';
    
}

