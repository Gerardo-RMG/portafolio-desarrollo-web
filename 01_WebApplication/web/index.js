
function editar(matricula, nombre, materia, p1, p2, p3) {
    document.getElementById('tfMatricula'     ).value = matricula;
    document.getElementById('tfMatriculaOld'  ).value = matricula;
    document.getElementById('tfNombre'        ).value = nombre;
    document.getElementById('tfMateria'       ).value = materia;
    document.getElementById('tfP1'            ).value = p1;
    document.getElementById('tfP2'            ).value = p2;
    document.getElementById('tfP3'            ).value = p3;
    document.getElementById('accion'          ).value = 'Modificar';

    var btn = document.getElementById('btnAccion');
    btn.textContent = 'Modificar';
    btn.classList.remove('btn-success');
    btn.classList.add('btn-warning');

    document.getElementById('btnCancelar').style.display = 'block';

    var header = document.getElementById('formHeader');
    header.textContent = 'Editando registro: ' + matricula;
    header.className   = 'card-header bg-warning text-dark';

    document.getElementById('formCard').scrollIntoView({ behavior: 'smooth' });
}

function cancelar() {
    document.getElementById('tfMatricula'    ).value = '';
    document.getElementById('tfMatriculaOld' ).value = '';
    document.getElementById('tfNombre'       ).value = '';
    document.getElementById('tfMateria'      ).value = '';
    document.getElementById('tfP1'           ).value = '';
    document.getElementById('tfP2'           ).value = '';
    document.getElementById('tfP3'           ).value = '';
    document.getElementById('accion'         ).value = 'Agregar';

    var btn = document.getElementById('btnAccion');
    btn.textContent = 'Agregar';
    btn.classList.remove('btn-warning');
    btn.classList.add('btn-success');

    document.getElementById('btnCancelar').style.display = 'none';

    var header = document.getElementById('formHeader');
    header.textContent = 'Registro de Calificaciones';
    header.className = 'card-header bg-primary text-white';
}

