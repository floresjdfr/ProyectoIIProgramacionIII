package protocolo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;
import utiles.LocalDateAdapter;

/**
 *
 * @author Jose David Flores
 * @author Marvin Aguilar
 */
@XmlRootElement(name = "Usuario")
public class Usuario implements Serializable {

    public Usuario(String nombreUsuario, String nombreCompleto, String clave, LocalDate ultimoAcceso,
            List<String> contactos, List<Chat> chats) {
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
        this.clave = clave;
        this.ultimoAcceso = ultimoAcceso;
        this.contactos = contactos;
        this.chats = chats;
    }

    public Usuario(String nombreUsuario, String nombreCompleto, String clave, LocalDate ultimoAcceso) {
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
        this.clave = clave;
        this.ultimoAcceso = ultimoAcceso;
        this.contactos = new ArrayList<>();
        this.chats = new ArrayList<>();
    }

    public Usuario() {
        this.contactos = new ArrayList<>();
        this.chats = new ArrayList<>();
    }

    public Usuario(String nombreUsuario, String clave) {
        this(nombreUsuario, null, clave, null, null, null);
    }

    @Override
    public String toString() {
        return String.format("{%s, %s, %s, %s}",
                getNombreUsuario(), getNombreCompleto(), getClave(),
                getUltimoAcceso().format(LocalDateAdapter.D_FORMAT));
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    @XmlElement(name = "Nombre_Usuario")
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    @XmlTransient
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getClave() {
        return clave;
    }

    @XmlTransient
    public void setClave(String clave) {
        this.clave = clave;
    }

    public LocalDate getUltimoAcceso() {
        return ultimoAcceso;
    }

    @XmlTransient
    public void setUltimoAcceso(LocalDate ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public List<String> getContatos() {
        return contactos;
    }

    @XmlElementWrapper(name = "Contactos")
    public void setContatos(List<String> contatos) {
        this.contactos = contatos;
    }

    public List<Chat> getChats() {
        return chats;
    }

    @XmlElementWrapper(name = "Chats")
    @XmlElement(name = "Chat")
    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public void agregarContacto(String usuario) {
        contactos.add(usuario);
    }

    public void actualizarChat(Chat chat) {
        if (chat != null) {
            Chat resultado = this.buscarChat(chat.getContacto());
            if (resultado != null) {
                chats.remove(resultado);
            }
            chats.add(chat);
        }

    }
    
    public void agregarChat(Chat chat){
        chats.add(chat);
    }

    public Chat buscarChat(String usuarioContacto) {
        for (Chat chat : chats) {
            if (chat.getContacto().equals(usuarioContacto)) {
                return chat;
            }
        }
        return null;
    }

    private String nombreUsuario;
    private String nombreCompleto;
    private String clave;
    private LocalDate ultimoAcceso;
    private List<String> contactos;
    private List<Chat> chats;
}
