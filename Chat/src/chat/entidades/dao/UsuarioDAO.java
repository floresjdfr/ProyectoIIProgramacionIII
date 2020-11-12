package chat.entidades.dao;

import chat.entidades.ConjuntoUsuarios;
import chat.entidades.Usuario;
import chat.entidades.db.BaseDatos;
import chat.utils.xml.XMLUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Georges Alfaro S.
 * @version 2.0
 */
public class UsuarioDAO implements DAO<String, Usuario> {

    private UsuarioDAO() {
        try {
            System.out.println("Configurando acceso a la base de datos..");
            bd = BaseDatos.obtenerInstancia();
        } catch (IOException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> r = new ArrayList<>();
        try (Connection cnx = bd.getConnection();
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(UsuarioCRUD.CMD_LISTAR)) {
            while (rs.next()) {
                r.add(new Usuario(
                        rs.getString("cedula"),
                        rs.getString("apellidos"),
                        rs.getString("nombre"),
                        rs.getDate("nacimiento").toLocalDate()
                ));
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return r;
    }

    @Override
    public void agregar(String id, Usuario nuevaPersona) {
        try (Connection cnx = bd.getConnection();
                PreparedStatement stm = cnx.prepareStatement(UsuarioCRUD.CMD_AGREGAR)) {
            stm.clearParameters();
            stm.setString(1, nuevaPersona.getId());
            stm.setString(2, nuevaPersona.getApellidos());
            stm.setString(3, nuevaPersona.getNombre());
            stm.setDate(4, Date.valueOf(nuevaPersona.getNacimiento()));
            if (stm.executeUpdate() != 1) {
                throw new IllegalArgumentException(
                        String.format("No se pudo agregar el registro: '%s'",
                                nuevaPersona.getId()));
            }
        } catch (IllegalArgumentException | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public void agregar(Usuario p) {
        agregar(p.getId(), p);
    }

    @Override
    public Usuario recuperar(String id) {
        Usuario resultado = null;
        try {
            try (Connection cnx = bd.getConnection();
                    PreparedStatement stm = cnx.prepareStatement(UsuarioCRUD.CMD_RECUPERAR)) {
                stm.clearParameters();
                stm.setString(1, id);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        resultado = new Usuario(
                                rs.getString("cedula"),
                                rs.getString("apellidos"),
                                rs.getString("nombre"),
                                rs.getDate("nacimiento").toLocalDate()
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
    public void actualizar(String id, Usuario persona) {
        try (Connection cnx = bd.getConnection();
                PreparedStatement stm = cnx.prepareStatement(UsuarioCRUD.CMD_ACTUALIZAR)) {
            stm.clearParameters();
            stm.setString(1, persona.getApellidos());
            stm.setString(2, persona.getNombre());
            stm.setDate(3, Date.valueOf(persona.getNacimiento()));
            stm.setString(4, persona.getId());
            if (stm.executeUpdate() != 1) {
                throw new IllegalArgumentException(
                        String.format("No se pudo actualizar el registro: '%s'",
                                persona.getId()));
            }
        } catch (IllegalArgumentException | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public void actualizar(Usuario p) {
        actualizar(p.getId(), p);
    }

    @Override
    public void eliminar(String id) {
        try {
            try (Connection cnx = bd.getConnection();
                    PreparedStatement stm = cnx.prepareStatement(UsuarioCRUD.CMD_ELIMINAR)) {
                stm.clearParameters();
                stm.setString(1, id);
                if (stm.executeUpdate() != 1) {
                    throw new IllegalArgumentException();
                }
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public int cuentaPersonas() {
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

    public List<Usuario> seleccionarAñoNacimiento(int año) {
        List<Usuario> r = new ArrayList<>();
        try (Connection cnx = bd.getConnection();
                PreparedStatement stm = cnx.prepareStatement(CMD_SELECCIONAR)) {
            stm.clearParameters();
            stm.setInt(1, año);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    r.add(new Usuario(
                            rs.getString("cedula"),
                            rs.getString("apellidos"),
                            rs.getString("nombre"),
                            rs.getDate("nacimiento").toLocalDate()
                    ));
                }
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return r;
    }

    public static UsuarioDAO obtenerInstancia() {
        if (instancia == null) {
            instancia = new UsuarioDAO();
        }
        return instancia;
    }

    public void guardar(OutputStream salida) throws JAXBException {
        XMLUtils.saveXML(salida, new ConjuntoUsuarios(listarTodos()));
    }

    public void restaurar(InputStream entrada) throws JAXBException {
        ConjuntoUsuarios cjto = (ConjuntoUsuarios) XMLUtils.loadXML(entrada, ConjuntoUsuarios.class);

        cjto.listarTodos().forEach((p) -> {
            try {
                System.out.printf("Agregando: %s..%n", p);
                agregar(p);
            } catch (Exception ex1) {
                System.err.printf("No se pudo agregar: %s (%s)%n",
                        p.getId(), ex1.getMessage());
                try {
                    System.out.printf("Actualizando: %s..%n", p);
                    actualizar(p);
                } catch (Exception ex2) {
                    System.err.printf("No se pudo actualizar: %s (%s)%n",
                            p.getId(), ex2.getMessage());
                }
            }
        });
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

    private static final String CMD_CUENTA_REGISTROS
            = "SELECT COUNT(*) AS cuenta FROM persona; ";
    private static final String CMD_SELECCIONAR
            = "SELECT cedula, apellidos, nombre, nacimiento FROM persona "
            + "WHERE EXTRACT(YEAR FROM nacimiento) = ? "
            + "ORDER BY apellidos, nombre; ";

    private static UsuarioDAO instancia = null;
    private BaseDatos bd;
}
