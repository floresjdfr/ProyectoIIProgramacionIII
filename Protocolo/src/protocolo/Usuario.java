
package protocolo;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import utiles.LocalDateAdapter;

/**
 *
 * @author Jose David Flores
 * @author Marvin Aguilar
 */

//@XmlType(name = "persona", propOrder = {"id", "apellidos", "nombre", "nacimiento"})
public class Usuario implements Serializable{

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

    private Usuario() {
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

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public LocalDate getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(LocalDate ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }
    
    public List<String> getContatos() {
        return contactos;
    }

    public void setContatos(List<String> contatos) {
        this.contactos = contatos;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
    
    public void agregarContacto(String usuario){
        contactos.add(usuario);
    }

    private String nombreUsuario;
    private String nombreCompleto;
    private String clave;
    private LocalDate ultimoAcceso;
    private List<String> contactos;
    private List<Chat> chats;
}

