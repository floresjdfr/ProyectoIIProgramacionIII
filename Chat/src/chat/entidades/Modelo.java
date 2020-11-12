package chat.entidades;

import chat.entidades.dao.UsuarioDAO;
import chat.utils.PathUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Observable;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Marvin Aguilar
 */
public class Modelo extends Observable {

    public Modelo() {
        System.out.println("Inicializando modelo..");
        usuarios = UsuarioDAO.obtenerInstancia();
    }

    public int cuentaRegistros() {
        return usuarios.cuentaPersonas();
    }

    public Usuario recuperar(String id) {
        return usuarios.recuperar(id);
    }

    public void agregar(Usuario nuevaPersona) {
        usuarios.agregar(nuevaPersona);

        setChanged();
        notifyObservers(new EventoActualizacion(
                OperacionActualizacion.OPERACION_AGREGAR, nuevaPersona));
    }

    public void actualizar(Usuario persona) {
        usuarios.actualizar(persona);

        setChanged();
        notifyObservers(new EventoActualizacion(
                OperacionActualizacion.OPERACION_EDITAR, persona));
    }

    public void eliminar(String id) {
        usuarios.eliminar(id);

        setChanged();
        notifyObservers(new EventoActualizacion(
                OperacionActualizacion.OPERACION_ELIMINAR, id));
    }

    public void importar(File entrada) throws FileNotFoundException, JAXBException {
        usuarios.restaurar(new FileInputStream(entrada));

        setChanged();
        notifyObservers();
    }

    public void exportar(File salida) throws FileNotFoundException, JAXBException {
        String nombreArchivo = salida.getAbsolutePath();
        usuarios.guardar(new FileOutputStream(String.format("%s.xml",
                PathUtils.getFileNameWithoutExtension(nombreArchivo)
        )));
    }

    public UsuarioDAO obtenerDAO() {
        return usuarios;
    }

    private final UsuarioDAO usuarios;
}
