package jonathanalvarez.webservice;

import java.io.Serializable;

/**
 * Created by IDS Comercial on 29/09/2017.
 */

public class Comentario implements Serializable {

    private String usuario,comentario,calificacion;

    public Comentario(String usuario, String comentario, String calificacion) {
        this.usuario = usuario;
        this.comentario = comentario;
        this.calificacion = calificacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public String getCalificacion() {
        return calificacion;
    }
}
