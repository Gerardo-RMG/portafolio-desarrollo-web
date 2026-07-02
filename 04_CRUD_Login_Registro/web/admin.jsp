<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Usuario"%>
<%
    if (session == null || session.getAttribute("usuario") == null
            || !Boolean.TRUE.equals(session.getAttribute("esAdmin"))) {
        response.sendRedirect("Servlet_Login");
        return;
    }

    List<Usuario> todos      = (List<Usuario>) request.getAttribute("todos");
    String        mensaje    = (String) request.getAttribute("mensaje");
    String        perfilError = (String) request.getAttribute("perfilError");

    if (todos == null) todos = new ArrayList<>();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Panel de Administración</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>

        <div id="encabezado">
            <h1>Panel de Administración</h1>
            <div id="session_bar">
                <span class="badge_activo on">&#9679; Admin</span>
                <span>
                    <%= session.getAttribute("nombre") != null ? session.getAttribute("nombre") : session.getAttribute("usuario") %>
                    &nbsp;<span style="opacity:.6;font-weight:400;">(@<%= session.getAttribute("usuario") %>)</span>
                </span>
                <button type="button" id="btn_perfil" onclick="abrirPerfil()">Editar Perfil</button>
                <form method="post" action="Servlet_Alumno" style="display:inline;">
                    <input type="hidden" name="accion" value="Salir"/>
                    <button type="submit" id="btn_salir">Cerrar Sesión</button>
                </form>
            </div>
        </div>

        <div id="contenido_admin">

            <%-- ── Mensaje de retroalimentación ─────────────────────── --%>
            <% if (mensaje != null) { %>
            <div class="login_alert login_exito" style="margin:0 0 20px;">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                     fill="none" stroke="currentColor" stroke-width="2.5"
                     stroke-linecap="round" stroke-linejoin="round">
                    <polyline points="20 6 9 17 4 12"/>
                </svg>
                <span><%= mensaje %></span>
            </div>
            <% } %>

            <%-- ── Tabla de usuarios registrados ───────────────────── --%>
            <div class="admin_section">
                <h2 class="admin_section_title">Usuarios registrados</h2>

                <% if (todos.isEmpty()) { %>
                <div class="admin_empty"><span>No hay usuarios registrados.</span></div>
                <% } else { %>
                <table class="admin_tabla">
                    <colgroup>
                        <col class="col_nombre">
                        <col class="col_correo">
                        <col class="col_usuario">
                        <col class="col_estado">
                        <col class="col_email">
                        <col class="col_fecha">
                        <col class="col_acciones">
                    </colgroup>
                    <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Correo</th>
                            <th>Usuario</th>
                            <th>Estado</th>
                            <th>Email</th>
                            <th>Fecha registro</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Usuario u : todos) {
                               if (u.isEsAdmin()) continue;
                               String est = u.getEstado() != null ? u.getEstado() : "activo";
                        %>
                        <tr>
                            <td class="td_clip"><%= u.getNombre() != null ? u.getNombre() : "—" %></td>
                            <td class="td_clip"><%= u.getCorreo()  != null ? u.getCorreo()  : "—" %></td>
                            <td>@<%= u.getUsuario() %></td>
                            <td>
                                <% if ("activo".equals(est)) { %>
                                <span class="badge_est badge_est_activo">Activo</span>
                                <% } else if ("pendiente".equals(est)) { %>
                                <span class="badge_est badge_est_pendiente">Pendiente</span>
                                <% } else { %>
                                <span class="badge_est badge_est_rechazado">Rechazado</span>
                                <% } %>
                            </td>
                            <td>
                                <% if (u.isCorreoVerificado()) { %>
                                <span class="badge_est badge_verificado">&#10003; Verificado</span>
                                <% } else { %>
                                <span class="badge_est badge_no_verificado">Sin verificar</span>
                                <% } %>
                            </td>
                            <td><%= u.getFechaRegistro() != null ? u.getFechaRegistro() : "—" %></td>
                            <td>
                                <% if ("pendiente".equals(est)) { %>
                                <form method="post" action="Servlet_Admin" style="display:inline;">
                                    <input type="hidden" name="accion"  value="Aceptar"/>
                                    <input type="hidden" name="usuario" value="<%= u.getUsuario() %>"/>
                                    <button type="submit" class="btn_admin btn_aceptar">Aceptar</button>
                                </form>
                                <form method="post" action="Servlet_Admin" style="display:inline;margin-left:5px;">
                                    <input type="hidden" name="accion"  value="Rechazar"/>
                                    <input type="hidden" name="usuario" value="<%= u.getUsuario() %>"/>
                                    <button type="submit" class="btn_admin btn_rechazar">Rechazar</button>
                                </form>
                                <% } else { %>
                                <span style="color:var(--text-muted);font-size:12px;">—</span>
                                <% } %>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                <% } %>
            </div>

        </div>

        <%-- ══════════════════════════════════════════
             MODAL: Editar Perfil
        ══════════════════════════════════════════ --%>
        <div id="modal_perfil" class="modal_overlay" style="display:none;"
             onclick="if(event.target===this)cerrarPerfil()">
            <div class="modal_card">

                <div class="modal_header">
                    <h3>Editar Perfil</h3>
                    <button type="button" class="modal_close" onclick="cerrarPerfil()" aria-label="Cerrar">&times;</button>
                </div>

                <% if (perfilError != null) { %>
                <div class="login_alert login_error" style="margin:16px 24px 0;">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <circle cx="12" cy="12" r="10"/>
                        <line x1="12" y1="8" x2="12" y2="12"/>
                        <line x1="12" y1="16" x2="12.01" y2="16"/>
                    </svg>
                    <span><%= perfilError %></span>
                </div>
                <% } %>

                <form method="post" action="Servlet_Perfil">
                    <div class="modal_body">

                        <div class="modal_field">
                            <label for="tfNuevoUsuario">Nombre de usuario</label>
                            <div class="input_wrapper">
                                <svg class="field_icon" xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <circle cx="12" cy="8" r="4"/>
                                    <path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/>
                                </svg>
                                <input type="text" id="tfNuevoUsuario" name="tfNuevoUsuario"
                                       value="<%= session.getAttribute("usuario") %>" required/>
                            </div>
                        </div>

                        <div class="modal_field">
                            <label for="tfClaveActual">
                                Contraseña actual
                                <span style="color:var(--danger);margin-left:2px;">*</span>
                            </label>
                            <div class="input_wrapper has_toggle">
                                <svg class="field_icon" xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/>
                                    <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                                </svg>
                                <input type="password" id="tfClaveActual" name="tfClaveActual"
                                       placeholder="Requerida para confirmar cambios" required/>
                                <button type="button" class="pwd_toggle" onclick="togglePwd('tfClaveActual',this)" tabindex="-1" aria-label="Mostrar contraseña">
                                    <svg class="icon_eye" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                                    <svg class="icon_eye_off" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="display:none"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/><line x1="1" y1="1" x2="23" y2="23"/></svg>
                                </button>
                            </div>
                        </div>

                        <div class="modal_divider"></div>

                        <div class="modal_field">
                            <label for="tfNuevaClave">Nueva contraseña</label>
                            <div class="input_wrapper has_toggle">
                                <svg class="field_icon" xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
                                </svg>
                                <input type="password" id="tfNuevaClave" name="tfNuevaClave"
                                       placeholder="Dejar vacío para no cambiarla"/>
                                <button type="button" class="pwd_toggle" onclick="togglePwd('tfNuevaClave',this)" tabindex="-1" aria-label="Mostrar contraseña">
                                    <svg class="icon_eye" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                                    <svg class="icon_eye_off" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="display:none"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/><line x1="1" y1="1" x2="23" y2="23"/></svg>
                                </button>
                            </div>
                            <span class="modal_hint">Mínimo 8 caracteres. Si no quieres cambiarla, déjalo vacío.</span>
                        </div>

                        <div class="modal_field">
                            <label for="tfNuevaClave2">Confirmar nueva contraseña</label>
                            <div class="input_wrapper has_toggle">
                                <svg class="field_icon" xmlns="http://www.w3.org/2000/svg" width="15" height="15" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
                                </svg>
                                <input type="password" id="tfNuevaClave2" name="tfNuevaClave2"
                                       placeholder="Repite la nueva contraseña"/>
                                <button type="button" class="pwd_toggle" onclick="togglePwd('tfNuevaClave2',this)" tabindex="-1" aria-label="Mostrar contraseña">
                                    <svg class="icon_eye" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                                    <svg class="icon_eye_off" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="display:none"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/><line x1="1" y1="1" x2="23" y2="23"/></svg>
                                </button>
                            </div>
                        </div>

                    </div>

                    <div class="modal_footer">
                        <button type="button" class="btn_modal_cancel" onclick="cerrarPerfil()">Cancelar</button>
                        <button type="submit" class="btn_modal_save">Guardar cambios</button>
                    </div>
                </form>

            </div>
        </div>

        <div id="pie_pagina">
            <p>Base de datos MySQL con JSP &amp; Servlet</p>
        </div>

        <script>
        function abrirPerfil()  { document.getElementById('modal_perfil').style.display = 'flex'; }
        function cerrarPerfil() { document.getElementById('modal_perfil').style.display = 'none'; }

        function togglePwd(id, btn) {
            var inp  = document.getElementById(id);
            var hide = inp.type === 'password';
            inp.type = hide ? 'text' : 'password';
            btn.querySelector('.icon_eye')    .style.display = hide ? 'none' : '';
            btn.querySelector('.icon_eye_off').style.display = hide ? ''     : 'none';
            btn.setAttribute('aria-label', hide ? 'Ocultar contraseña' : 'Mostrar contraseña');
        }

        // Abrir el modal automáticamente si hubo un error en el perfil
        <% if (perfilError != null) { %>
        abrirPerfil();
        <% } %>
        </script>

    </body>
</html>
