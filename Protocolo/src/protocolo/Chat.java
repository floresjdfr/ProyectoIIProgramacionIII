package protocolo;

import java.util.List;

/**
 *
 * @author Jose David
 */
public class Chat {

    public Chat() {
    }

    public Chat(String contacto) {
        this.contacto = contacto;
    }

    public Chat(String contacto, List<Mensaje> mensajes) {
        this.contacto = contacto;
        this.mensajes = mensajes;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
    
    
    private String contacto;
    private List<Mensaje> mensajes;
    
    
}
