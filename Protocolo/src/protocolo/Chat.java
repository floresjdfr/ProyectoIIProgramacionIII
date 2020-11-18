package protocolo;

import java.util.ArrayList;
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
        mensajes = new ArrayList<>();
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
    
    public void agregarMensaje(Mensaje mensaje){
        mensajes.add(mensaje);
    }
    
    
    private String contacto;
    private List<Mensaje> mensajes;
    
    
}
