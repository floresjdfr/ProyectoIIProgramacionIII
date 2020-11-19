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
import protocolo.Usuario;
import servidor.BaseDatos;

/**
 * @author Marvin Aguilar Fuentes
 * @author Jose David Flores Rodriguez
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
                        rs.getString("nombre"),
                        rs.getString("nombre_completo"),
                        rs.getString("clave"),
                        rs.getDate("ultimoAcceso").toLocalDate()
                ));
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return r;
    }

    @Override
    public void agregar(String nombre, Usuario nuevoUsuario) {
        try (Connection cnx = bd.getConnection();
                PreparedStatement stm = cnx.prepareStatement(UsuarioCRUD.CMD_AGREGAR)) {
            stm.clearParameters();
            stm.setString(1, nuevoUsuario.getNombreUsuario());
            stm.setString(2, nuevoUsuario.getNombreCompleto());
            stm.setString(3, nuevoUsuario.getClave());
            stm.setDate(4, Date.valueOf(nuevoUsuario.getUltimoAcceso()));
            if (stm.executeUpdate() != 1) {
                throw new IllegalArgumentException(
                        String.format("No se pudo agregar el registro: '%s'",
                                nuevoUsuario.getNombreUsuario()));
            }
        } catch (IllegalArgumentException | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public void agregar(Usuario p) {
        agregar(p.getNombreUsuario(), p);
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
                                rs.getString("nombre"),
                                rs.getString("nombre_completo"),
                                rs.getString("clave"),
                                rs.getDate("ultimoAcceso").toLocalDate()
                        );
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return resultado;
    }
    
    public String recuperarNombreUsuario(String usuario){
        String resultado = null;
        try {
            try (Connection cnx = bd.getConnection();
                    PreparedStatement stm = cnx.prepareStatement(UsuarioCRUD.CMD_RECUPERAR)) {
                stm.clearParameters();
                stm.setString(1, usuario);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        resultado = rs.getString("nombre");
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return resultado;
    }

    @Override
    public void actualizar(String nombre, Usuario usuario) {
        try (Connection cnx = bd.getConnection();
                PreparedStatement stm = cnx.prepareStatement(UsuarioCRUD.CMD_ACTUALIZAR)) {
            stm.clearParameters();
            stm.setString(1, usuario.getNombreUsuario());
            stm.setString(2, usuario.getNombreCompleto());
            stm.setString(3, usuario.getClave());
            stm.setDate(4, Date.valueOf(usuario.getUltimoAcceso()));
            
            if (stm.executeUpdate() != 1) {
                throw new IllegalArgumentException(
                        String.format("No se pudo actualizar el registro: '%s'",
                                usuario.getNombreUsuario()));
            }
        } catch (IllegalArgumentException | SQLException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public void actualizar(Usuario p) {
        actualizar(p.getNombreUsuario(), p);
    }

    @Override
    public void eliminar(String nombreUsuario) {
        try {
            try (Connection cnx = bd.getConnection();
                    PreparedStatement stm = cnx.prepareStatement(UsuarioCRUD.CMD_ELIMINAR)) {
                stm.clearParameters();
                stm.setString(1, nombreUsuario);
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
                            rs.getString("nombre"),
                            rs.getString("nombre_completo"),
                            rs.getString("clave"),
                            rs.getDate("ultimoAcceso").toLocalDate()
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
/*
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
                        p.getNombreUsuario(), ex1.getMessage());
                try {
                    System.out.printf("Actualizando: %s..%n", p);
                    actualizar(p);
                } catch (Exception ex2) {
                    System.err.printf("No se pudo actualizar: %s (%s)%n",
                            p.getNombreUsuario(), ex2.getMessage());
                }
            }
        });
    }
*/
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
            = "SELECT COUNT(*) AS cuenta FROM usuario; ";
    private static final String CMD_SELECCIONAR
            = "SELECT nombre, nombre_completo, clave, ultimoAcceso FROM usuario"
            + "WHERE EXTRACT(YEAR FROM ultimoAcceso) = ? "
            + "ORDER BY nombre_completo; ";

    private static UsuarioDAO instancia = null;
    private BaseDatos bd;
}
