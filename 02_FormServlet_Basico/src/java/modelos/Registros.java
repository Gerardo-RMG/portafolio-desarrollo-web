package modelos;

import java.util.ArrayList;

public class Registros {

    private ArrayList<Registro> item;

    public Registros() {
        item = new ArrayList<>();
    }

    public void add(Registro reg) {
        item.add(reg);
    }

    public Registro get(int posicion) {
        return item.get(posicion);
    }

    public int getSize() {
        return item.size();
    }

    public Registro remove(int posicion) {
        return item.remove(posicion);
    }

    public Registro remove(String matricula) {
        for (Registro r : item) {
            if (matricula.equals(r.getMatricula())) {
                item.remove(r);
                return r;
            }
        }
        return null;
    }

    public boolean update(int posicion, Registro actualizado) {
        item.set(posicion, actualizado);
        return true;
    }

    public boolean update(String matricula, Registro actualizado) {
        for (int i = 0; i < item.size(); i++) {
            if (matricula.equals(item.get(i).getMatricula())) {
                item.set(i, actualizado);
                return true;
            }
        }
        return false;
    }

    public Registro find(String matricula) {
        for (Registro r : item) {
            if (matricula.equals(r.getMatricula())) {
                return r;
            }
        }
        return null;
    }

    public ArrayList<Registro> getList() {
        return item;
    }

    public String mostrar() {
        String r;

        r = "<table>\n"
          + "  <thead>\n"
          + "    <tr>\n"
          + "      <th>Matricula</th>\n"
          + "      <th>Nombre</th>\n"
          + "      <th>Materia</th>\n"
          + "      <th>P1</th>\n"
          + "      <th>P2</th>\n"
          + "      <th>P3</th>\n"
          + "      <th>PROM</th>\n"
          + "      <th colspan=\"2\">ACCIONES</th>\n"
          + "    </tr>\n"
          + "  </thead>\n"
          + "  <tbody>\n";

        for (Registro reg : getList()) {
            r = r + "    <tr>\n"
              + "      <td>" + reg.getMatricula() + "</td>\n"
              + "      <td>" + reg.getNombre()    + "</td>\n"
              + "      <td>" + reg.getMateria()   + "</td>\n"
              + "      <td>" + reg.getP1()        + "</td>\n"
              + "      <td>" + reg.getP2()        + "</td>\n"
              + "      <td>" + reg.getP3()        + "</td>\n"
              + "      <td>" + String.format("%.2f", reg.calcProm()) + "</td>\n"
              + "      <td><button onclick=\"editar('" + reg.getMatricula() + "','" + reg.getNombre() + "','" + reg.getMateria() + "'," + reg.getP1() + "," + reg.getP2() + "," + reg.getP3() + ")\">EDITAR</button></td>\n"
              + "      <td>\n"
              + "        <form method='post' action='Servlet_Regitros'>\n"
              + "          <input type='hidden' name='accion' value='Eliminar'/>\n"
              + "          <input type='hidden' name='tfMatricula' value='" + reg.getMatricula() + "'/>\n"
              + "          <input type='submit' value='Eliminar'>\n"
              + "        </form>\n"
              + "      </td>\n"
              + "    </tr>\n";
        }

        r = r + "  </tbody>\n</table>\n";
        return r;
    }
}
