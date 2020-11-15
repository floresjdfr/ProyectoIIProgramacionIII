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

    public static final int LOGIN = 1;
    public static final int LOGOUT = 2;
    public static final int ENVIO_MENSAJE = 3;
    public static final int CONTACTOS_EN_LINEA = 4;

    public static final int NOTIFICAR_LOGOUT = 5;
    public static final int NOTIFICAR_LOGIN = 6;
    public static final int REGISTRAR_USUARIO = 7;

    //public static final int DELIVER = 1;

    //public static final int ERROR_NO_ERROR = 0;
    //public static final int ERROR_LOGIN = 1;
    //public static final int ERROR_LOGOUT = 2;
    //public static final int ERROR_POST = 3;
    //public static final int ERROR_REGISTER = 4;
}
