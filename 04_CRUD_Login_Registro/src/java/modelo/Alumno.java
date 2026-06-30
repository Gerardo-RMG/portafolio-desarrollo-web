package modelo;

public class Alumno {

    private int    NL;
    private String nombre;
    private String paterno;
    private String materno;

    public Alumno() {}

    public int    getNL()          { return NL; }
    public void   setNL(int NL)    { this.NL = NL; }

    public String getNombre()           { return nombre; }
    public void   setNombre(String n)   { this.nombre  = n; }

    public String getPaterno()          { return paterno; }
    public void   setPaterno(String p)  { this.paterno = p; }

    public String getMaterno()          { return materno; }
    public void   setMaterno(String m)  { this.materno = m; }
}
