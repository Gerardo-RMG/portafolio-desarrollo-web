package modelo;

public class Usuario {

    private String  usuario;
    private String  clave;
    private String  nombre;
    private String  correo;
    private boolean activo;

    public Usuario() {}

    public Usuario(String usuario, String clave) {
        this.usuario = usuario;
        this.clave   = clave;
    }

    public String  getUsuario()             { return usuario; }
    public void    setUsuario(String u)     { this.usuario = u; }

    public String  getClave()               { return clave; }
    public void    setClave(String c)       { this.clave = c; }

    public String  getNombre()              { return nombre; }
    public void    setNombre(String n)      { this.nombre = n; }

    public String  getCorreo()              { return correo; }
    public void    setCorreo(String e)      { this.correo = e; }

    public boolean isActivo()               { return activo; }
    public void    setActivo(boolean a)     { this.activo = a; }
}
