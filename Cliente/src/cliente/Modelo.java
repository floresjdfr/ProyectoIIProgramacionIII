package cliente;

import java.util.List;
import java.util.Observable;
import protocolo.Chat;
import protocolo.Mensaje;
import protocolo.Usuario;

/**
 * @author Marvin Aguilar Fuentes
 * @author Jose David Flores Rodriguez
 */
public class Modelo extends Observable {

    public Modelo(Usuario usuarioActual, Chat chatActual) {
        this.usuarioActual = usuarioActual;
        this.chatActual = chatActual;
    }

    public Modelo() {
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void logout() {
        chatActual = null;
        usuarioActual = null;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;

        setChanged();
        notifyObservers();
    }

    public Chat getChatActual() {
        return chatActual;
    }

    public void setChatActual(Chat chatActual) {
        this.chatActual = chatActual;

        setChanged();
        notifyObservers();
    }

    public String getNombreUsuario() {
        return usuarioActual.getNombreUsuario();
    }

    public void agregarContacto(String usuario) {
        usuarioActual.agregarContacto(usuario);
        Chat chat = new Chat(usuario);
        usuarioActual.agregarChat(chat);
    }

    public void enviarMensaje(Mensaje mensaje) {
        chatActual.agregarMensaje(mensaje);

        setChanged();
        notifyObservers();
    }

    public String obtenerContactoActual() {
        return chatActual.getContacto();
    }

    public List<String> obtenerContactos() {
        return usuarioActual.getContatos();
    }

    public String obtenerMensajesChatActual() {
        if (chatActual != null) {
            StringBuilder mensajes = new StringBuilder();
            for (Mensaje mensaje : chatActual.getMensajes()) {
                mensajes.append(mensaje);
            }
            return mensajes.toString();
        }
        return null;
    }

    public void seleccionarNuevoChat(String nombreContacto) {
        Chat chat = usuarioActual.buscarChat(nombreContacto);
        if (chat != null) {
            actualizarChat();
            chatActual = chat;
        } else {
            chat = new Chat(nombreContacto);
            actualizarChat();
            chatActual = chat;
        }
        setChanged();
        notifyObservers();
    }

    public void actualizarChat() {
        usuarioActual.actualizarChat(chatActual);
    }

    private Usuario usuarioActual;
    private Chat chatActual;

    void recibirMensaje(Mensaje mensaje) {
        if (chatActual != null) {
            if (mensaje.getSender().equals(chatActual.getContacto())) {
                chatActual.agregarMensaje(mensaje);
            } else {
                String sender = mensaje.getSender();
                Chat chat = usuarioActual.buscarChat(sender);
                chat.agregarMensaje(mensaje);
            }
        } else {
            String sender = mensaje.getSender();
            Chat chat = usuarioActual.buscarChat(sender);
            chat.agregarMensaje(mensaje);
        }

        setChanged();
        notifyObservers();
    }

    public void recibirContactosEnLinea(List<String> contactosEnLinea) {
        List<Chat> chatsUsuarioActual = usuarioActual.getChats();
        for (Chat chat : chatsUsuarioActual) {
            if (contactosEnLinea.contains(chat.getContacto())) {
                chat.setEstado(true);
            }
        }

        setChanged();
        notifyObservers();
    }

    public List<Chat> getChats() {
        return usuarioActual.getChats();
    }

    public void cambiarEstadoUsuario(String usuario, boolean estado) {
        List<Chat> chatsUsuarioActual = usuarioActual.getChats();
        for (Chat chat : chatsUsuarioActual) {
            if (usuario.equals(chat.getContacto())) {
                chat.setEstado(estado);
            }
        }

        setChanged();
        notifyObservers();
    }

    public void actualizar() {
        setChanged();
        notifyObservers();
    }

    void notificarLogoutUsuario(String usuario) {
        List<Chat> chats = usuarioActual.getChats();
        for (Chat chat:chats){
            if (chat.getContacto().equals(usuario))
                chat.setEstado(false);
        }
        
        actualizar();
    }

}
