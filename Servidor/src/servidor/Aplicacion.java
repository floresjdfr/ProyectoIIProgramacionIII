package servidor;

import java.io.IOException;

/**
 * @author Marvin Aguilar Fuentes
 * @author Jose David Flores Rodriguez
 */
public class Aplicacion {
    public static void main(String[] args) throws IOException {
        Servidor servidor = new Servidor();
        servidor.iniciarServidor();
        System.gc();
    }
}
