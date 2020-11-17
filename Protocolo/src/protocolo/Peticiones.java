/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocolo;

/**
 *
 * @author Jose David
 */
public class Peticiones {
    public static final String URL_POR_DEFECTO = "localhost";
    public static final int PUERTO_POR_DEFECTO = 1234;

    public static final String LOGIN = "LOGIN";
    public static final String LOGOUT = "LOGOUT";
    
    public static final String ENVIO_MENSAJE = "ENVIO_MENSAJE";
    public static final String CONTACTOS_EN_LINEA = "CONTACTOS_EN_LINEA";

    public static final String NOTIFICAR_LOGOUT = "NOTIFICAR_LOGOUT";
    public static final String NOTIFICAR_LOGIN = "NOTIFICAR_LOGIN";
    public static final String REGISTRAR_USUARIO = "REGISTRAR_USUARIO";

    //public static final int DELIVER = 1;

    public static final String NO_ERROR = "NO_ERROR";
    public static final String ERROR_LOGIN = "ERROR_LOGIN";
    public static final String ERROR_LOGOUT = "ERROR_LOGOUT";
    public static final String ERROR_ENVIAR_MENSAJE = "ERROR_ENVIAR_MENSAJE";
    public static final String ERROR_REGISTRAR = "ERROR_REGISTRAR";
}
