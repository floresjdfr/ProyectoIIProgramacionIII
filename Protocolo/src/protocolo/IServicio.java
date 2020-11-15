/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocolo;

import java.util.List;

/**
 *
 * @author Jose David
 */
public interface IServicio {
    public Usuario login(Usuario usuario) throws Exception;

    public void logout(Usuario usuario) throws Exception;

    public void enviarMensaje(Mensaje mensaje);

    public void verificaContactosEnLinea(String nombreUsuario, List<String> listaContactos);

    public void NotificarLogout(String nombreUsuario);
}
