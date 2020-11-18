package protocolo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Jose David
 */

@XmlRootElement(name = "Chat")
public class Chat {

    public Chat() {
        mensajes = new ArrayList<>();
    }

    public boolean isEstado() {
        return estado;
    }

    @XmlTransient
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    public Chat(String contacto) {
        this.contacto = contacto;
        mensajes = new ArrayList<>();
    }

    public Chat(String contacto, List<Mensaje> mensajes) {
        this.contacto = contacto;
        this.mensajes = mensajes;
    }

    public String getContacto() {
        return contacto;
    }

    @XmlElement(name = "Contacto")
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    @XmlElementWrapper(name = "Mensajes")
    @XmlElement(name = "Mensaje")
    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
    
    public void agregarMensaje(Mensaje mensaje){
        mensajes.add(mensaje);
    }
    
    private boolean estado;
    private String contacto;
    private List<Mensaje> mensajes;
    
    
}
