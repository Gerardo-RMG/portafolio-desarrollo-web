package modelos;

import java.util.ArrayList;

public class Registros 

{

    private ArrayList<Registro> item;

    public Registros() 
    {
        item = new ArrayList<>();
    }

    public void add(Registro reg) 
    {
        item.add(reg);
    }

    public Registro get(int posicion) 
    {
        return item.get(posicion);
    }

    public int getSize() 
    {
        return item.size();
    }

    public Registro remove(int posicion) 
    {
        return item.remove(posicion);
    }

    public Registro remove(String matricula) 
    {
        for (int i = 0; i < item.size(); i++) 
        {
            if (matricula.equals(item.get(i).getMatricula())) 
            {
                return item.remove(i);
            }
        }
        return null;
    }

    public boolean update(int posicion, Registro update) 
    {
        item.set(posicion, update);
        return true;
    }

    public boolean update(String matricula, Registro actualizado) 
    {
        for (int i = 0; i < item.size(); i++) 
        {
            if (matricula.equals(item.get(i).getMatricula())) 
            {
                item.set(i, actualizado);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Registro> getList() 
    {
        return item;
    }

    public String mostrar() 
    {
        StringBuilder r = new StringBuilder();
        r.append("<table class=\"table table-bordered table-hover table-striped\">\n");
        r.append("  <thead class=\"table-dark\">\n");
        r.append("    <tr>\n");
        r.append("      <th>Matrícula</th>\n");
        r.append("      <th>Nombre</th>\n");
        r.append("      <th>Materia</th>\n");
        r.append("      <th>P1</th>\n");
        r.append("      <th>P2</th>\n");
        r.append("      <th>P3</th>\n");
        r.append("      <th>Promedio</th>\n");
        r.append("      <th colspan=\"2\" class=\"text-center\">Acciones</th>\n");
        r.append("    </tr>\n");
        r.append("  </thead>\n");
        r.append("  <tbody>\n");

        for (Registro reg : getList()) 
        {
            r.append("    <tr>\n");
            r.append("      <td>").append(reg.getMatricula()).append("</td>\n");
            r.append("      <td>").append(reg.getNombre()).append("</td>\n");
            r.append("      <td>").append(reg.getMateria()).append("</td>\n");
            r.append("      <td>").append(reg.getP1()).append("</td>\n");
            r.append("      <td>").append(reg.getP2()).append("</td>\n");
            r.append("      <td>").append(reg.getP3()).append("</td>\n");
            r.append("      <td><strong>").append(String.format("%.2f", reg.calcProm())).append("</strong></td>\n");
            r.append("      <td>")
             .append("<button type='button' class='btn btn-warning btn-sm' ")
             .append("onclick=\"editar('").append(reg.getMatricula()).append("','")
             .append(reg.getNombre()).append("','")
             .append(reg.getMateria()).append("',")
             .append(reg.getP1()).append(",")
             .append(reg.getP2()).append(",")
             .append(reg.getP3()).append(")\">")
             .append(" Editar</button></td>\n");
            r.append("      <td>\n");
            r.append("        <form method='post' action='index.jsp'>\n");
            r.append("          <input type='hidden' name='accion' value='Eliminar'/>\n");
            r.append("          <input type='hidden' name='tfMatricula' value='").append(reg.getMatricula()).append("'/>\n");
            r.append("          <button type='submit' class='btn btn-danger btn-sm'> Eliminar</button>\n");
            r.append("        </form>\n");
            r.append("      </td>\n");
            r.append("    </tr>\n");
        }

        r.append("  </tbody>\n");
        r.append("</table>\n");
        return r.toString();
    }
}
