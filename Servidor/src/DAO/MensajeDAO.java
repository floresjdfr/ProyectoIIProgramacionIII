package DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import protocolo.Mensaje;
import servidor.BaseDatos;

/**
 * @author Marvin Aguilar Fuentes
 * @author Jose David Flores Rodriguez
 */
public class MensajeDAO implements DAO<Integer, Mensaje> {

    private MensajeDAO() {
        try {
            System.out.println("Configurando acceso a la base de datos..");
            bd = BaseDatos.obtenerInstancia();
        } catch (IOException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public List<Mensaje> listarTodos() {
        List<Mensaje> r = new ArrayList<>();
        try (Connection cnx = bd.getConnection();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(MensajeCRUD.CMD_LISTAR)) {
            while (rs.next()) {
                r.add(new Mensaje(
                        rs.getInt("secuencia"),
                        rs.getString("usuario_emisor"),
                        rs.getString("usuario_receptor"),
                        rs.getString("texto"),
                        rs.getDate("fecha").toLocalDate()
                ));
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return r;
    }

    @Override
    public void agregar(Integer id, Mensaje nuevoMensaje) {
        try (Connection cnx = bd.getConnection();
                PreparedStatement stm = cnx.prepareStatement(MensajeCRUD.CMD_AGREGAR)) {
            stm.clearParameters();
            stm.setInt(1, nuevoMensaje.getSecuencia());
            stm.setString(2, nuevoMensaje.getSender());
            stm.setString(3, nuevoMensaje.getReciever());
            stm.setDate(4, Date.valueOf(nuevoMensaje.getFecha()));
            stm.setString(5, nuevoMensaje.getMessage());
            stm.setBoolean(6, true);
            if (stm.executeUpdate() != 1) {
                throw new IllegalArgumentException(
                        String.format("No se pudo agregar el registro: '%s'",
                                nuevoMensaje.getSecuencia()));
            }
        } catch (IllegalArgumentException | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public void agregar(Mensaje m) {
        this.agregar(m.getSecuencia(), m);
    }

    @Override
    public Mensaje recuperar(Integer secuencia) {
        Mensaje resultado = null;
        try {
            try (Connection cnx = bd.getConnection();
                    PreparedStatement stm = cnx.prepareStatement(UsuarioCRUD.CMD_RECUPERAR)) {
                stm.clearParameters();
                stm.setInt(1, secuencia);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        resultado = new Mensaje(
                                rs.getInt("secuencia"),
                                rs.getString("usuario_emisor"),
                                rs.getString("usuario_receptor"),
                                rs.getString("texto"),
                                rs.getDate("fecha").toLocalDate()
                        );
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return resultado;
    }

    @Override
    public void actualizar(Integer id, Mensaje mensaje) {
        try (Connection cnx = bd.getConnection();
                PreparedStatement stm = cnx.prepareStatement(UsuarioCRUD.CMD_ACTUALIZAR)) {
            stm.clearParameters();
            stm.setInt(1, mensaje.getSecuencia());
            stm.setString(2, mensaje.getSender());
            stm.setString(3, mensaje.getReciever());
            stm.setDate(4, Date.valueOf(mensaje.getFecha()));
            stm.setString(5, mensaje.getMessage());
            
            if (stm.executeUpdate() != 1) {
                throw new IllegalArgumentException(
                        String.format("No se pudo actualizar el registro: '%s'",
                                mensaje.getSecuencia()));
            }
        } catch (IllegalArgumentException | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public void eliminar(Integer secuencia) {
        try {
            try (Connection cnx = bd.getConnection();
                    PreparedStatement stm = cnx.prepareStatement(UsuarioCRUD.CMD_ELIMINAR)) {
                stm.clearParameters();
                stm.setInt(1, secuencia);
                if (stm.executeUpdate() != 1) {
                    throw new IllegalArgumentException();
                }
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("[");
        listarTodos().forEach((p) -> {
            r.append(String.format("%n\t%s,", p));
        });
        r.append("\n]");
        return r.toString();
    }

    public int cuentaMensajes() {
        int r = 0;
        try (Connection cnx = bd.getConnection();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(CMD_CUENTA_REGISTROS)) {
            if (rs.next()) {
                r = rs.getInt("cuenta");
            } else {
                throw new IllegalArgumentException();
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return r;
    }
    
    public static MensajeDAO obtenerInstancia() {
        if (instancia == null) {
            instancia = new MensajeDAO();
        }
        return instancia;
    }

    
    private static final String CMD_CUENTA_REGISTROS
            = "SELECT COUNT(*) AS cuenta FROM mensaje; ";

    private static final String CMD_SELECCIONAR
            = "SELECT secuencia, usuario_emisor, usuario_receptor, fecha, texto, enviado FROM mensaje"
            + "WHERE EXTRACT(YEAR FROM fecha) = ? "
            + "ORDER BY secuencia; ";
    private static MensajeDAO instancia = null;
    private BaseDatos bd;
}
