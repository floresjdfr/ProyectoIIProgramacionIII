
package chat.entidades;
import chat.entidades.xml.LocalDateAdapter;
import java.time.LocalDate;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Jose David Flores
 * @author Marvin Aguilar
 */

@XmlType(name = "persona", propOrder = {"id", "apellidos", "nombre", "nacimiento"})
public class Usuario {


    public Usuario(String nombreUsuario, String nombreCompleto, String clave, LocalDate ultimoAcceso) {
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
        this.clave = clave;
        this.ultimoAcceso = ultimoAcceso;
    }

    private Usuario() {
        this(null, null, null, null);
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

   

    private String nombreUsuario;
    private String nombreCompleto;
    private String clave;
    private LocalDate ultimoAcceso;
}

