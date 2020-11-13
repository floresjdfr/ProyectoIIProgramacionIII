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

    public Mensaje(int sentencia, String sender, String reciever, String message, LocalDate fecha, Boolean enviado) {
        this.secuencia = sentencia;
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.fecha = fecha;
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
    
    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }
    
    private int secuencia;
    private String sender;
    private String reciever;
    private String message;
    private LocalDate fecha;
    private Boolean enviado;
  
}
