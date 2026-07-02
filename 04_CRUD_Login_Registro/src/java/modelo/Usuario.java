package modelo;

public class Usuario {

    private String  usuario;
    private String  clave;
    private String  nombre;
    private String  correo;
    private boolean activo;

    // Nuevos campos para solicitudes y verificación de correo
    private String  estado;           // "pendiente" | "activo" | "rechazado"
    private boolean correoVerificado;
    private String  tokenCorreo;
    private boolean esAdmin;
    private String  fechaRegistro;

    public Usuario() {}

    public Usuario(String usuario, String clave) {
        this.usuario = usuario;
        this.clave   = clave;
    }

    public String  getUsuario()                   { return usuario; }
    public void    setUsuario(String u)           { this.usuario = u; }

    public String  getClave()                     { return clave; }
    public void    setClave(String c)             { this.clave = c; }

    public String  getNombre()                    { return nombre; }
    public void    setNombre(String n)            { this.nombre = n; }

    public String  getCorreo()                    { return correo; }
    public void    setCorreo(String e)            { this.correo = e; }

    public boolean isActivo()                     { return activo; }
    public void    setActivo(boolean a)           { this.activo = a; }

    public String  getEstado()                    { return estado; }
    public void    setEstado(String e)            { this.estado = e; }

    public boolean isCorreoVerificado()           { return correoVerificado; }
    public void    setCorreoVerificado(boolean v) { this.correoVerificado = v; }

    public String  getTokenCorreo()               { return tokenCorreo; }
    public void    setTokenCorreo(String t)       { this.tokenCorreo = t; }

    public boolean isEsAdmin()                    { return esAdmin; }
    public void    setEsAdmin(boolean a)          { this.esAdmin = a; }

    public String  getFechaRegistro()             { return fechaRegistro; }
    public void    setFechaRegistro(String f)     { this.fechaRegistro = f; }
}
