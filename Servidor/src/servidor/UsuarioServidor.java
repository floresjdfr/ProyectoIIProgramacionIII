package servidor;

import com.mysql.cj.conf.ConnectionUrlParser;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocolo.Peticiones;
import protocolo.Usuario;

/**
 *
 * @author Jose David
 */
public class UsuarioServidor implements Runnable {

    public UsuarioServidor(Socket socket, ObjectInputStream entrada, ObjectOutputStream salida, Usuario usuario) {
        this.socket = socket;
        this.entrada = entrada;
        this.salida = salida;
        this.usuario = usuario;
    }

    @Override
    public void run() {
        int servicioSolitidado;
        while(continuar){
            try{
                servicioSolitidado = entrada.readInt();
                switch(servicioSolitidado){
                    case Peticiones.LOGOUT:
                        break;
                    case Peticiones.ENVIO_MENSAJE:
                        break;
                        
                    case Peticiones.CONTACTOS_EN_LINEA:
                        break;
                }
                
            } catch (IOException ex) {
                Logger.getLogger(UsuarioServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }

    public void iniciar() {
        hilo = new Thread(this);
        if (hilo != null){
            continuar = true;
            hilo.start();            
        }
    }

    private Socket socket;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private Usuario usuario;
    private Thread hilo;
    private boolean continuar;
}
