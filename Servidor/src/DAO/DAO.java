package DAO;

import java.util.List;

/**
 * @author Marvin Aguilar Fuentes
 * @author Jose David Flores Rodriguez
 */
public interface DAO<K, V> { // DAO (Data Access Object Interface)

    public List<V> listarTodos();

    // CRUD         (IMEC)
    // C(reate)     I(nsertar)
    // R(etrieve)   C(onsultar)
    // U(pdate)     M(odificar)
    // D(elete)     E(liminar)
    
    public void agregar(K id, V valor);

    public V recuperar(K id);

    public void actualizar(K id, V valor);

    public void eliminar(K id);

}
