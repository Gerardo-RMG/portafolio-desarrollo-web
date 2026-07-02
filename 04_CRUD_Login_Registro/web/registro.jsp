<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    boolean registrado    = Boolean.TRUE.equals(request.getAttribute("registrado"));
    boolean correoEnviado = Boolean.TRUE.equals(request.getAttribute("correoEnviado"));
    String  urlVerif      = (String) request.getAttribute("urlVerificacion");
    String  correoDestino = (String) request.getAttribute("correoDestino");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear Cuenta</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>

        <div id="encabezado">
            <h1>Bases de datos MySQL con JSP</h1>
        </div>

        <div id="login_wrapper">
            <div id="login_card">

                <div id="login_header">
                    <svg xmlns="http://www.w3.org/2000/svg" width="44" height="44" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="1.8"
                         stroke-linecap="round" stroke-linejoin="round">
                        <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/>
                        <circle cx="9" cy="7" r="4"/>
                        <line x1="19" y1="8" x2="19" y2="14"/>
                        <line x1="22" y1="11" x2="16" y2="11"/>
                    </svg>
                    <h2><%= registrado ? "¡Solicitud enviada!" : "Crear Cuenta" %></h2>
                    <p><%= registrado ? "Revisa los pasos a continuación" : "Regístrate con tu correo institucional" %></p>
                </div>

                <% if (registrado) { %>
                <%-- ── Panel de éxito ─────────────────────────────────────── --%>
                <div class="registro_ok_panel">

                    <div class="registro_ok_icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24"
                             fill="none" stroke="currentColor" stroke-width="2"
                             stroke-linecap="round" stroke-linejoin="round">
                            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                            <polyline points="22 4 12 14.01 9 11.01"/>
                        </svg>
                    </div>

                    <% if (correoEnviado) { %>
                    <p class="registro_ok_msg">
                        Enviamos un enlace de verificación a<br>
                        <strong><%= correoDestino %></strong>
                    </p>
                    <p class="registro_ok_sub">
                        Abre tu correo y haz clic en el enlace para verificar tu cuenta.
                        Después, el administrador revisará tu solicitud.
                    </p>
                    <% } else { %>
                    <p class="registro_ok_msg">
                        Cuenta creada. Verifica tu correo para continuar.
                    </p>
                    <p class="registro_ok_sub">
                        Haz clic en el botón de abajo para verificar tu correo electrónico:
                    </p>
                    <a href="<%= urlVerif %>" class="btn_verificar_link">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                             fill="none" stroke="currentColor" stroke-width="2.5"
                             stroke-linecap="round" stroke-linejoin="round" style="flex-shrink:0">
                            <rect x="2" y="4" width="20" height="16" rx="2"/>
                            <path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7"/>
                        </svg>
                        Verificar mi correo
                    </a>
                    <p class="registro_ok_sub" style="margin-top:12px;">
                        Una vez verificado, el administrador aprobará tu solicitud<br>y podrás iniciar sesión.
                    </p>
                    <% } %>

                    <div class="registro_ok_pasos">
                        <div class="paso"><span class="paso_num paso_done">1</span><span>Registro completado</span></div>
                        <div class="paso_linea"></div>
                        <div class="paso"><span class="paso_num <%= correoEnviado ? "" : "paso_done" %>">2</span><span>Verificar correo</span></div>
                        <div class="paso_linea"></div>
                        <div class="paso"><span class="paso_num">3</span><span>Aprobación admin</span></div>
                        <div class="paso_linea"></div>
                        <div class="paso"><span class="paso_num">4</span><span>Acceso</span></div>
                    </div>

                    <a href="Servlet_Login" class="link_volver">← Volver al inicio de sesión</a>
                </div>

                <% } else { %>
                <%-- ── Formulario de registro ───────────────────────────── --%>

                <% if (request.getAttribute("error") != null) { %>
                <div class="login_alert login_error">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <circle cx="12" cy="12" r="10"/>
                        <line x1="12" y1="8" x2="12" y2="12"/>
                        <line x1="12" y1="16" x2="12.01" y2="16"/>
                    </svg>
                    <span><%= request.getAttribute("error") %></span>
                </div>
                <% } %>

                <form method="post" action="Servlet_Registro" id="formRegistro" novalidate>

                    <%-- ── NOMBRES ────────────────────────────── --%>
                    <div class="login_field" id="field_nombres">
                        <label for="tfNombres">Nombre(s)</label>
                        <div class="input_wrapper">
                            <svg class="field_icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                 fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                <circle cx="12" cy="7" r="4"/>
                            </svg>
                            <input type="text" id="tfNombres" name="tfNombres"
                                   placeholder="Nombre(s)" required autofocus
                                   value="<%= request.getAttribute("nombres") != null ? request.getAttribute("nombres") : "" %>"/>
                        </div>
                        <div class="campo_feedback" id="fb_nombres"></div>
                    </div>

                    <%-- ── APELLIDO PATERNO ───────────────────── --%>
                    <div class="login_field" id="field_paterno">
                        <label for="tfPaterno">Apellido Paterno</label>
                        <div class="input_wrapper">
                            <svg class="field_icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                 fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                <circle cx="12" cy="7" r="4"/>
                            </svg>
                            <input type="text" id="tfPaterno" name="tfPaterno"
                                   placeholder="Apellido paterno" required
                                   value="<%= request.getAttribute("paterno") != null ? request.getAttribute("paterno") : "" %>"/>
                        </div>
                        <div class="campo_feedback" id="fb_paterno"></div>
                    </div>

                    <%-- ── APELLIDO MATERNO ───────────────────── --%>
                    <div class="login_field" id="field_materno">
                        <label for="tfMaterno">Apellido Materno <span class="campo_opcional">(opcional)</span></label>
                        <div class="input_wrapper">
                            <svg class="field_icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                 fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                                <circle cx="12" cy="7" r="4"/>
                            </svg>
                            <input type="text" id="tfMaterno" name="tfMaterno"
                                   placeholder="Apellido materno"
                                   value="<%= request.getAttribute("materno") != null ? request.getAttribute("materno") : "" %>"/>
                        </div>
                        <div class="campo_feedback" id="fb_materno"></div>
                    </div>

                    <%-- ── CORREO ─────────────────────────────── --%>
                    <div class="login_field" id="field_correo">
                        <label for="tfCorreo">Correo electrónico</label>
                        <div class="input_wrapper">
                            <svg class="field_icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                 fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <rect x="2" y="4" width="20" height="16" rx="2"/>
                                <path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7"/>
                            </svg>
                            <input type="email" id="tfCorreo" name="tfCorreo"
                                   placeholder="correo@ejemplo.com" required
                                   value="<%= request.getAttribute("correo") != null ? request.getAttribute("correo") : "" %>"/>
                        </div>
                        <div class="campo_feedback" id="fb_correo"></div>
                    </div>

                    <%-- ── USUARIO ─────────────────────────────── --%>
                    <div class="login_field" id="field_usuario">
                        <label for="tfUsuario">Nombre de usuario</label>
                        <div class="input_wrapper">
                            <svg class="field_icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                 fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <circle cx="12" cy="8" r="4"/>
                                <path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/>
                            </svg>
                            <input type="text" id="tfUsuario" name="tfUsuario"
                                   placeholder="Elige un usuario" required
                                   value="<%= request.getAttribute("usuario") != null ? request.getAttribute("usuario") : "" %>"/>
                        </div>
                        <div class="campo_feedback" id="fb_usuario"></div>
                    </div>

                    <%-- ── CONTRASEÑA ──────────────────────────── --%>
                    <div class="login_field" id="field_clave">
                        <label for="tfClave">Contraseña</label>
                        <div class="input_wrapper has_toggle">
                            <svg class="field_icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                 fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/>
                                <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                            </svg>
                            <input type="password" id="tfClave" name="tfClave"
                                   placeholder="Mínimo 8 caracteres" required/>
                            <button type="button" class="pwd_toggle" onclick="togglePwd('tfClave',this)" tabindex="-1" aria-label="Mostrar contraseña">
                                <svg class="icon_eye" xmlns="http://www.w3.org/2000/svg" width="17" height="17" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                                <svg class="icon_eye_off" xmlns="http://www.w3.org/2000/svg" width="17" height="17" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="display:none"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/><line x1="1" y1="1" x2="23" y2="23"/></svg>
                            </button>
                        </div>
                        <div class="pwd_strength_wrap" id="pwd_strength_wrap" style="display:none;">
                            <div class="pwd_bar_track"><div id="pwd_bar" class="pwd_bar_fill"></div></div>
                            <span id="pwd_label" class="pwd_strength_label"></span>
                        </div>
                        <div id="pwd_requisitos" style="display:none;">
                            <div id="req_longitud"  class="req_item"><span class="req_icon">✗</span> Mínimo 8 caracteres</div>
                            <div id="req_mayuscula" class="req_item"><span class="req_icon">✗</span> Al menos una mayúscula (A-Z)</div>
                            <div id="req_minuscula" class="req_item"><span class="req_icon">✗</span> Al menos una minúscula (a-z)</div>
                            <div id="req_numero"    class="req_item"><span class="req_icon">✗</span> Al menos un número (0-9)</div>
                            <div id="req_especial"  class="req_item"><span class="req_icon">✗</span> Al menos un carácter especial (!@#$…)</div>
                        </div>
                        <div class="campo_feedback fb_error" id="pwd_weak_msg" style="display:none;">
                            Contraseña demasiado débil — cumple al menos 3 requisitos.
                        </div>
                    </div>

                    <%-- ── CONFIRMAR CONTRASEÑA ──────────────── --%>
                    <div class="login_field" id="field_clave2">
                        <label for="tfClave2">Confirmar contraseña</label>
                        <div class="input_wrapper has_toggle">
                            <svg class="field_icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                 fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
                            </svg>
                            <input type="password" id="tfClave2" name="tfClave2"
                                   placeholder="Repite tu contraseña" required/>
                            <button type="button" class="pwd_toggle" onclick="togglePwd('tfClave2',this)" tabindex="-1" aria-label="Mostrar contraseña">
                                <svg class="icon_eye" xmlns="http://www.w3.org/2000/svg" width="17" height="17" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                                <svg class="icon_eye_off" xmlns="http://www.w3.org/2000/svg" width="17" height="17" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="display:none"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/><line x1="1" y1="1" x2="23" y2="23"/></svg>
                            </button>
                        </div>
                        <div class="campo_feedback" id="fb_clave2"></div>
                    </div>

                    <input type="submit" value="Crear cuenta"/>

                </form>

                <div class="auth_divider">o</div>
                <div class="auth_footer">
                    ¿Ya tienes cuenta? <a href="Servlet_Login">Inicia sesión</a>
                </div>

                <% } %>

            </div>
        </div>

        <div id="pie_pagina">
            <p>Base de datos MySQL con JSP &amp; Servlet</p>
        </div>

        <% if (!registrado) { %>
        <script>
        /* ── Ver / ocultar contraseña ────────────────────────── */
        function togglePwd(id, btn) {
            var inp  = document.getElementById(id);
            var hide = inp.type === 'password';
            inp.type = hide ? 'text' : 'password';
            btn.querySelector('.icon_eye')    .style.display = hide ? 'none' : '';
            btn.querySelector('.icon_eye_off').style.display = hide ? ''     : 'none';
            btn.setAttribute('aria-label', hide ? 'Ocultar contraseña' : 'Mostrar contraseña');
        }

        (function () {

            var estados = {};

            function setEstado(fieldId, estado, mensaje) {
                var field = document.getElementById(fieldId);
                if (!field) return;
                field.dataset.estado = estado;
                estados[fieldId] = estado;
                var fb = field.querySelector('.campo_feedback:not(#pwd_weak_msg)');
                if (!fb) return;
                fb.className = 'campo_feedback' +
                    (estado === 'ok'       ? ' fb_ok'   :
                     estado === 'error'    ? ' fb_error' :
                     estado === 'checking' ? ' fb_info'  : '');
                fb.textContent = mensaje || '';
            }

            function verificar(tipo, params, fieldId, msgExiste) {
                setEstado(fieldId, 'checking', 'Verificando…');
                fetch('Servlet_Validar?tipo=' + tipo + '&' + params)
                    .then(function(r) { return r.json(); })
                    .then(function(data) {
                        if (data.existe) {
                            setEstado(fieldId, 'error', msgExiste);
                        } else {
                            setEstado(fieldId, 'ok', '');
                        }
                    })
                    .catch(function() { setEstado(fieldId, '', ''); });
            }

            function checkNombre() {
                var nombres = document.getElementById('tfNombres').value.trim();
                var paterno = document.getElementById('tfPaterno').value.trim();
                var materno = document.getElementById('tfMaterno').value.trim();
                if (!nombres || !paterno) return;
                verificar('nombre',
                    'nombres=' + encodeURIComponent(nombres) +
                    '&paterno=' + encodeURIComponent(paterno) +
                    '&materno=' + encodeURIComponent(materno),
                    'field_materno',
                    'Ya existe un usuario registrado con ese nombre y apellidos.'
                );
            }
            document.getElementById('tfPaterno').addEventListener('blur', checkNombre);
            document.getElementById('tfMaterno').addEventListener('blur', checkNombre);

            document.getElementById('tfCorreo').addEventListener('blur', function () {
                var val = this.value.trim();
                if (!val) return;
                verificar('correo',
                    'valor=' + encodeURIComponent(val),
                    'field_correo',
                    'Este correo ya está registrado.'
                );
            });

            document.getElementById('tfUsuario').addEventListener('blur', function () {
                var val = this.value.trim();
                if (!val) return;
                verificar('usuario',
                    'valor=' + encodeURIComponent(val),
                    'field_usuario',
                    'Este nombre de usuario ya está en uso.'
                );
            });

            var REQS = {
                longitud:  function(p) { return p.length >= 8; },
                mayuscula: function(p) { return /[A-Z]/.test(p); },
                minuscula: function(p) { return /[a-z]/.test(p); },
                numero:    function(p) { return /[0-9]/.test(p); },
                especial:  function(p) { return /[^A-Za-z0-9]/.test(p); }
            };
            var NIVELES = ['', 'Muy débil', 'Débil', 'Regular', 'Fuerte', 'Muy fuerte'];
            var COLORES = ['', '#ef4444', '#f97316', '#eab308', '#22c55e', '#16a34a'];

            function checkPassword() {
                var pwd   = document.getElementById('tfClave').value;
                var score = 0;
                var keys  = Object.keys(REQS);
                for (var i = 0; i < keys.length; i++) {
                    var ok  = REQS[keys[i]](pwd);
                    if (ok) score++;
                    var el  = document.getElementById('req_' + keys[i]);
                    if (el) {
                        el.className = 'req_item ' + (ok ? 'req_ok' : 'req_fail');
                        el.querySelector('.req_icon').textContent = ok ? '✓' : '✗';
                    }
                }
                var bar   = document.getElementById('pwd_bar');
                var label = document.getElementById('pwd_label');
                bar.style.width      = (pwd.length ? score * 20 : 0) + '%';
                bar.style.background = COLORES[score] || '';
                label.textContent    = pwd.length ? NIVELES[score] : '';
                label.style.color    = COLORES[score] || '';
                estados['pwd_score'] = score;
                document.getElementById('pwd_weak_msg').style.display = 'none';
            }

            document.getElementById('tfClave').addEventListener('focus', function () {
                document.getElementById('pwd_requisitos').style.display    = 'flex';
                document.getElementById('pwd_strength_wrap').style.display = 'flex';
            });
            document.getElementById('tfClave').addEventListener('blur', function () {
                if (!this.value) {
                    document.getElementById('pwd_requisitos').style.display    = 'none';
                    document.getElementById('pwd_strength_wrap').style.display = 'none';
                }
            });
            document.getElementById('tfClave').addEventListener('input', checkPassword);

            document.getElementById('tfClave2').addEventListener('blur', function () {
                var pwd  = document.getElementById('tfClave').value;
                var pwd2 = this.value;
                if (!pwd2) return;
                if (pwd !== pwd2) {
                    setEstado('field_clave2', 'error', 'Las contraseñas no coinciden.');
                } else {
                    setEstado('field_clave2', 'ok', '');
                }
            });

            document.getElementById('formRegistro').addEventListener('submit', function (e) {
                var tieneError = false;
                var campos = ['field_nombres', 'field_paterno', 'field_materno',
                              'field_correo', 'field_usuario', 'field_clave2'];
                for (var i = 0; i < campos.length; i++) {
                    if (estados[campos[i]] === 'error') { tieneError = true; break; }
                }
                if (tieneError) { e.preventDefault(); return; }
                var score = estados['pwd_score'] || 0;
                if (score < 3) {
                    e.preventDefault();
                    document.getElementById('pwd_weak_msg').style.display = 'flex';
                    document.getElementById('pwd_requisitos').style.display    = 'flex';
                    document.getElementById('pwd_strength_wrap').style.display = 'flex';
                    document.getElementById('tfClave').focus();
                    return;
                }
                var pwd  = document.getElementById('tfClave').value;
                var pwd2 = document.getElementById('tfClave2').value;
                if (pwd !== pwd2) {
                    e.preventDefault();
                    setEstado('field_clave2', 'error', 'Las contraseñas no coinciden.');
                }
            });

        })();
        </script>
        <% } %>

    </body>
</html>
