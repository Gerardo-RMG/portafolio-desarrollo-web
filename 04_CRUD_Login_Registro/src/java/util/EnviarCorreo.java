package util;

/**
 * Servicio de envío de correo electrónico.
 *
 * ESTADO ACTUAL: Modo demo — el enlace de verificación se muestra en pantalla.
 *
 * Para habilitar el envío real de correos (opcional):
 *  1. Descarga jakarta.mail-2.0.1.jar desde:
 *     https://mvnrepository.com/artifact/com.sun.mail/jakarta.mail/2.0.1
 *  2. Cópialo a  web/WEB-INF/lib/  y agrégalo en NetBeans:
 *     clic derecho en "Libraries" → "Add JAR/Folder".
 *  3. Descomenta el bloque SMTP de abajo, agrega los imports y pon tus datos de Gmail.
 *     (Necesitas una "Contraseña de aplicación" en myaccount.google.com/apppasswords)
 */
public class EnviarCorreo {

    /**
     * Envía un correo HTML. Devuelve true si se envió, false si no está configurado.
     *
     * Para activar, reemplaza este método (con los imports jakarta.mail.*):
     *
     *   Properties p = new Properties();
     *   p.put("mail.smtp.host","smtp.gmail.com"); p.put("mail.smtp.port","587");
     *   p.put("mail.smtp.auth","true"); p.put("mail.smtp.starttls.enable","true");
     *   Session s = Session.getInstance(p, new Authenticator() {
     *       protected PasswordAuthentication getPasswordAuthentication() {
     *           return new PasswordAuthentication("TU@gmail.com","TU_APP_PASSWORD");
     *       }
     *   });
     *   Message m = new MimeMessage(s);
     *   m.setFrom(new InternetAddress("TU@gmail.com"));
     *   m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
     *   m.setSubject(asunto);
     *   m.setContent(cuerpoHtml, "text/html; charset=utf-8");
     *   Transport.send(m);
     *   return true;
     */
    public static boolean enviar(String destinatario, String asunto, String cuerpoHtml) {
        // Sin JAR instalado → siempre muestra el enlace en pantalla (modo demo)
        System.out.println("[Correo] Modo demo. Para: " + destinatario + " | Asunto: " + asunto);
        return false;
    }

    /** Genera el cuerpo HTML del correo de verificación. */
    public static String cuerpoVerificacion(String nombre, String urlVerificacion) {
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'></head><body " +
               "style='font-family:Arial,sans-serif;background:#f0f4f8;margin:0;padding:20px;'>" +
               "<div style='max-width:480px;margin:0 auto;background:#fff;border-radius:12px;" +
               "box-shadow:0 4px 16px rgba(0,0,0,.1);overflow:hidden;'>" +
               "<div style='background:linear-gradient(135deg,#8b1538,#c0392b);padding:28px;text-align:center;'>" +
               "<h1 style='color:#fff;margin:0;font-size:22px;'>Sistema de Alumnos</h1></div>" +
               "<div style='padding:32px;'><p style='color:#1a202c;font-size:16px;margin:0 0 12px;'>" +
               "Hola, <strong>" + nombre + "</strong></p>" +
               "<p style='color:#4a5568;font-size:14px;line-height:1.6;margin:0 0 24px;'>" +
               "Haz clic en el botón para verificar tu correo y enviar tu solicitud al administrador.</p>" +
               "<div style='text-align:center;margin-bottom:24px;'>" +
               "<a href='" + urlVerificacion + "' style='background:linear-gradient(135deg,#8b1538,#c0392b);" +
               "color:#fff;text-decoration:none;padding:13px 32px;border-radius:8px;" +
               "font-size:15px;font-weight:700;display:inline-block;'>Verificar mi correo</a></div>" +
               "<p style='color:#718096;font-size:12px;'>Si no creaste esta cuenta, ignora este mensaje.</p>" +
               "</div></div></body></html>";
    }
}
