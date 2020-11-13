/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.control;

import chat.entidades.Modelo;
import chat.entidades.Usuario;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observer;
import javax.xml.bind.JAXBException;
/**
 *
 * @author Jose David
 */
public class ControlChat {
    
    public ControlChat(Modelo datos) {
        this.datos = datos;
    }

    public ControlChat() {
        this(new Modelo());
    }

    public void registrar(Observer obs) {
        datos.addObserver(obs);
    }

    public void importar(File entrada) throws FileNotFoundException, JAXBException {
        datos.importar(entrada);
    }

    public void exportar(File salida) throws FileNotFoundException, JAXBException {
        datos.exportar(salida);
    }

    public void cerrarAplicacion() {
        System.out.println("Finalizando aplicación..");
        System.exit(0);
    }

    public Usuario recuperar(String nombreUsuario) {
        return datos.recuperar(nombreUsuario);
    }

    public void agregar(String usuario, String nombreCompleto, String password) {
        datos.agregar(usuario, nombreCompleto, password);
    }

    public void actualizar(Usuario p) {
        datos.actualizar(p);
    }

    public void eliminar(String id) {
        datos.eliminar(id);
    }

    public Modelo obtenerModelo() {
        return datos;
    }
    
    public boolean verificarDatosUsuario(String nombreUsuario, String clave){
        Usuario usuario = this.recuperar(nombreUsuario);
        if (usuario != null)
            return usuario.getClave().equals(clave);
        return false;
    }
    
    private Modelo datos;
}
