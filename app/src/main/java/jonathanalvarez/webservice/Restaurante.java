package jonathanalvarez.webservice;

import java.io.Serializable;

/**
 * Created by IDS Comercial on 29/09/2017.
 */

public class Restaurante implements Serializable {

    private int id;
    private String nombre;
    private int imagen;


    public Restaurante(int id, String nombre, int imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public int getId() {
        return id;
    }
}
