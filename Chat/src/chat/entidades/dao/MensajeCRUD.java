/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.entidades.dao;

/**
 *
 * @author Jose David
 */
public class MensajeCRUD {
    protected static final String CMD_LISTAR
            = "SELECT secuencia, usuario_emisor, usuario_receptor, fecha, texto, enviado FROM usuario "
            + "ORDER BY secuencia; ";
    protected static final String CMD_AGREGAR
            = "INSERT INTO mensaje (secuencia, usuario_emisor, usuario_receptor, fecha, texto, enviado) "
            + "VALUES (?, ?, ?, ?, ?, ?); ";

    protected static final String CMD_RECUPERAR
            = "SELECT secuencia, usuario_emisor, usuario_receptor, fecha, texto, enviado FROM mensaje "
            + "WHERE secuencia = ?; ";
    protected static final String CMD_ACTUALIZAR
            = "UPDATE mensaje SET usuario_emisor = ?, usuario_receptor = ?, fecha = ?, texto = ?, enviado = ? "
            + "WHERE nombre = ?; ";
    protected static final String CMD_ELIMINAR
            = "DELETE FROM mensaje "
            + "WHERE secuencia = ?; ";
}
