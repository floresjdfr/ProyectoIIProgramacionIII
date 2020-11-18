package protocolo;

import java.io.Serializable;
import java.time.LocalDate;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import utiles.LocalDateAdapter;

/**
 *
 * @author Jose David
 */
@XmlRootElement(name = "Mensaje")
public class Mensaje implements Serializable  {

    public Mensaje(int sentencia, String sender, String reciever, String message, LocalDate fecha) {
        this.secuencia = sentencia;
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.fecha = fecha;
    }

    public Mensaje() {
    }

    @Override
    public String toString() {
        return String.format("[%s]: %s\n", sender, message);
    }
    
    

    public String getSender() {
        return sender;
    }

    @XmlElement(name = "Sender")
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    @XmlElement(name = "Reciever")
    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    @XmlElement(name = "Mensaje")
    public void setMessage(String message) {
        this.message = message;
    }
    
    public int getSecuencia() {
        return secuencia;
    }

    
    @XmlElement(name = "Secuencia")
    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "Fecha")
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    private int secuencia;
    private String sender;
    private String reciever;
    private String message;
    private LocalDate fecha;
  
}
