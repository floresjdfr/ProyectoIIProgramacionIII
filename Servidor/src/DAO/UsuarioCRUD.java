package DAO;

/**
 *
 * @author Georges Alfaro S.
 * @version 1.0
 */
public class UsuarioCRUD {

    protected static final String CMD_LISTAR
            = "SELECT nombre, nombre_Completo, clave, ultimoAcceso FROM usuario "
            + "ORDER BY nombre_Completo; ";
    protected static final String CMD_AGREGAR
            = "INSERT INTO usuario (nombre, nombre_Completo, clave, ultimoAcceso) "
            + "VALUES (?, ?, ?, ?); ";

    protected static final String CMD_RECUPERAR
            = "SELECT nombre, nombre_Completo, clave, ultimoAcceso FROM usuario "
            + "WHERE nombre = ?; ";
    protected static final String CMD_ACTUALIZAR
            = "UPDATE usuario SET nombre_completo = ?, clave = ?, ultimoAcceso = ? "
            + "WHERE nombre = ?; ";
    protected static final String CMD_ELIMINAR
            = "DELETE FROM usuario "
            + "WHERE nombre = ?; ";
}
