# Resumen del Proyecto — Sistema de Alumnos con MySQL y JSP

---

## ¿De qué trata este proyecto?

Es una aplicación web para gestionar una lista de alumnos conectada a una base de datos MySQL.
Permite registrar, ver, modificar y eliminar alumnos desde el navegador, y ahora también
cuenta con un sistema de cuentas de usuario para controlar quién puede entrar.

---

## Lo que se fue construyendo paso a paso

### Proyecto 01 — La base
Se creó la estructura inicial de la aplicación web con páginas JSP y estilos visuales básicos.
Todavía no había conexión con ninguna base de datos.

### Proyecto 02 — Formularios con Servlet
Se añadió la lógica para que los formularios pudieran enviar y recibir información usando Servlets
(el componente que actúa como intermediario entre la página y el servidor).

### Proyecto 03 — Conexión con la base de datos
Se conectó la aplicación a MySQL. Desde aquí ya era posible:
- **Agregar** alumnos a la base de datos
- **Ver** la lista completa de alumnos
- **Editar** los datos de un alumno
- **Eliminar** un alumno

### Proyecto 04 — Versión completa con Login y Registro
Es la versión final y más completa. Todo lo que se describe a continuación se agregó en esta etapa.

---

## Funciones nuevas que se agregaron al Proyecto 04

### 🔐 Inicio de sesión
Se creó una pantalla de inicio de sesión donde el usuario escribe su nombre de usuario y contraseña.
Si los datos son correctos, entra a la aplicación. Si están mal, aparece un mensaje de error.

- Si la cuenta está **desactivada**, el sistema avisa con un mensaje específico.
- El encabezado de la aplicación muestra el nombre del usuario que está conectado con una etiqueta verde de "Activo".
- Hay un botón para **cerrar sesión** en cualquier momento.
- Si alguien intenta entrar directamente a la página de alumnos sin haber iniciado sesión, el sistema lo regresa al login automáticamente.

---

### 📝 Registro de nuevos usuarios
Se creó una pantalla para que nuevas personas puedan crear su propia cuenta. Pide los siguientes datos:

- **Nombre(s)**
- **Apellido Paterno**
- **Apellido Materno** *(opcional)*
- **Correo electrónico**
- **Nombre de usuario**
- **Contraseña**
- **Confirmación de contraseña**

Al registrarse exitosamente, el sistema redirige al login con un mensaje verde de confirmación.

---

### ✅ Validaciones en tiempo real (mientras escribes)
Para evitar errores antes de enviar el formulario, se agregaron alertas que aparecen al instante:

**Nombre y apellidos**
Al terminar de escribir el apellido paterno o materno, el sistema verifica automáticamente
(sin recargar la página) si ya existe alguien registrado con exactamente ese nombre.
Si ya existe, aparece un aviso en rojo debajo del campo.

**Correo electrónico**
Al terminar de escribir el correo, el sistema verifica si ya está registrado en la base de datos.
Si ya existe, aparece un aviso para que el usuario use otro correo.

**Nombre de usuario**
Al terminar de escribir el nombre de usuario, el sistema verifica si ya está en uso.
Si ya existe, aparece un aviso para que elija otro.

---

### 🔒 Contraseña segura
Para que las contraseñas sean más seguras, se añadió lo siguiente:

- Una **barra de fortaleza** que cambia de color conforme la contraseña es más fuerte:
  `Rojo → Naranja → Amarillo → Verde claro → Verde oscuro`

- Una **lista de requisitos** que aparece al escribir, con ✓ o ✗ según se cumplan:
  - Mínimo 8 caracteres
  - Al menos una letra mayúscula (A-Z)
  - Al menos una letra minúscula (a-z)
  - Al menos un número (0-9)
  - Al menos un carácter especial (!@#$...)

- Si la contraseña es demasiado débil, **no se puede enviar** el formulario
  y aparece el mensaje: *"Contraseña demasiado débil — cumple al menos 3 requisitos"*.

---

### 👁️ Ver / ocultar contraseña
En todos los campos de contraseña (tanto en el login como en el registro) se añadió un pequeño
ícono de ojo al lado derecho del campo. Al hacer clic cambia entre:
- **Ojo abierto** → la contraseña está oculta, haz clic para verla
- **Ojo tachado** → la contraseña está visible, haz clic para ocultarla

---

### 🎨 Diseño visual mejorado
Las pantallas de login y registro se rediseñaron para verse más profesionales:

- Fondo con degradado suave
- Tarjeta central con animación de entrada
- Íconos dentro de los campos de texto
- Mensajes de error en rojo y mensajes de éxito en verde
- Enlace de *"¿No tienes cuenta? Regístrate aquí"* en el login
- Enlace de *"¿Ya tienes cuenta? Inicia sesión"* en el registro
- Botón de "Cerrar sesión" integrado en el encabezado
- Insignia verde que indica que el usuario está activo

---

### 👤 Estado de cuenta (Activo / Inactivo)
Los usuarios tienen un estado que puede ser **Activo** o **Inactivo**.
- Si está **Activo**: puede iniciar sesión normalmente.
- Si está **Inactivo**: al intentar entrar aparece el mensaje *"Tu cuenta está desactivada. Contacta al administrador"*.

---

## Cómo está guardado en GitHub

El código completo está disponible en:
**https://github.com/Gerardo-RMG/portafolio-desarrollo-web**

Está organizado en 4 carpetas que muestran la evolución del proyecto:

| Carpeta | Contenido |
|---|---|
| `01_WebApplication` | Primera versión — estructura básica |
| `02_FormServlet_Basico` | Formularios con Servlet |
| `03_FormServlet_con_BD` | CRUD conectado a MySQL |
| `04_CRUD_Login_Registro` | Versión final completa |

Cualquier persona puede ver el código o descargarlo directamente desde GitHub.

---

## Base de datos

La aplicación usa una base de datos MySQL llamada `db_registros` que contiene tres tablas:

- **alumnos** — guarda los datos de cada alumno (número de lista, nombre, apellidos)
- **usuarios** — guarda las cuentas de acceso (usuario, contraseña, correo, estado activo/inactivo)
- **registros** — tabla adicional del proyecto base

El archivo `db_registros.sql` incluido en el proyecto contiene todo lo necesario
para crear la base de datos desde cero.

---

*Documento generado el 1 de julio de 2026*
