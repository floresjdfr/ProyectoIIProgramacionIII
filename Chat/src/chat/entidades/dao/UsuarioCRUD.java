package chat.entidades.dao;

/**
 *
 * @author Georges Alfaro S.
 * @version 1.0
 */
public class UsuarioCRUD {

    protected static final String CMD_LISTAR
            = "SELECT cedula, apellidos, nombre, nacimiento FROM persona "
            + "ORDER BY apellidos, nombre; ";
    protected static final String CMD_AGREGAR
            = "INSERT INTO persona (cedula, apellidos, nombre, nacimiento) "
            + "VALUES (?, ?, ?, ?); ";

    protected static final String CMD_RECUPERAR
            = "SELECT cedula, apellidos, nombre, nacimiento FROM persona "
            + "WHERE cedula = ?; ";
    protected static final String CMD_ACTUALIZAR
            = "UPDATE persona SET apellidos = ?, nombre = ?, nacimiento = ? "
            + "WHERE cedula = ?; ";
    protected static final String CMD_ELIMINAR
            = "DELETE FROM persona "
            + "WHERE cedula = ?; ";
}
