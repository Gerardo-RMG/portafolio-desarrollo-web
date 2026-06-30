
package modelos;

public class Registro {

    private String matricula;
    private String nombre;
    private String materia;
    private double p1;
    private double p2;
    private double p3;

    public Registro() 
    {
    }
    public Registro(String matricula, String nombre, String materia, double p1, double p2, double p3) 
    {
        this.matricula = matricula;
        this.nombre    = nombre;
        this.materia   = materia;
        this.p1        = p1;
        this.p2        = p2;
        this.p3        = p3;
    }
   
    
    
    public String getMatricula() 
    { 
        return matricula; 
    }
    public void setMatricula(String matricula) 
    { 
        this.matricula = matricula; 
    }

    
    
    public String getNombre() 
    { 
        return nombre; 
    }
    public void setNombre(String nombre) 
    { 
        this.nombre = nombre; 
    }

    
    
    public String getMateria() 
    { 
        return materia; 
    }   
    public void setMateria(String materia) 
    { 
        this.materia = materia; 
    }

    
    
    public double getP1() 
    { 
        return p1; 
    }
    public void setP1(double p1) 
    { 
        this.p1 = p1; 
    }

    
    
    public double getP2() 
    { 
        return p2; 
    }
    public void setP2(double p2) 
    { 
        this.p2 = p2; 
    }

  
    
    public double getP3() 
    { 
        return p3; 
    }
    public void setP3(double p3) 
    { 
        this.p3 = p3; 
    }

    
    
    public double calcProm() 
    {
        return (p1 + p2 + p3) / 3.0;
    }
}
