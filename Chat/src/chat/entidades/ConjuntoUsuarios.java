package chat.entidades;

import chat.utils.xml.XMLSerializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jose David
 * @author Marvin
 */

@XmlRootElement(name = "lista_usuarios")
public class ConjuntoUsuarios implements XMLSerializable {
    
    public ConjuntoUsuarios(List<Usuario> usuarios) {
        this.usuarios = new ArrayList<>();
        this.usuarios.addAll(usuarios);
    }

    private ConjuntoUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    public List<Usuario> listarTodos() {
        return Collections.unmodifiableList(usuarios);
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("[");
        usuarios.forEach((p) -> {
            r.append(String.format("\n\t%s,", p));
        });
        r.append("\n]");
        return r.toString();
    }

    @XmlElement(name = "usuarios")
    private List<Usuario> usuarios;
}


