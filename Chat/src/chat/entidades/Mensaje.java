package chat.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author Jose David
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Mensaje implements Serializable  {

    public Mensaje(int sentencia, String sender, String reciever, String message, LocalDate fecha, String texto, boolean enviado) {
        this.sentencia = sentencia;
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.fecha = fecha;
        this.texto = texto;
        this.enviado = enviado;
    }

    public Mensaje() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public int getSentencia() {
        return sentencia;
    }

    public void setSentencia(int sentencia) {
        this.sentencia = sentencia;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }
    
    private int sentencia;
    private String sender;
    private String reciever;
    private String message;
    private LocalDate fecha;
    private String texto;
    private boolean enviado;
  
}
