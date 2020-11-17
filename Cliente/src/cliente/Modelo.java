package cliente;

import java.util.Observable;
import protocolo.Chat;
import protocolo.Usuario;

/**
 *
 * @author Jose David Flores
 * @author Marvin Aguilar
 */
public class Modelo extends Observable{

    public Modelo(Usuario usuarioActual, Chat chatActual) {
        this.usuarioActual = usuarioActual;
        this.chatActual = chatActual;
    }

    public Modelo() {
    }

    

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public Chat getChatActual() {
        return chatActual;
    }

    public void setChatActual(Chat chatActual) {
        this.chatActual = chatActual;
    }
    
    private Usuario usuarioActual;
    private Chat chatActual;
    
    
}
