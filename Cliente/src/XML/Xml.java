package XML;

import cliente.Modelo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import protocolo.Usuario;

/**
 * @author Marvin Aguilar Fuentes
 * @author Jose David Flores Rodriguez
 */
public class Xml {
    public static void guardaUsuarioXML(Modelo modelo) {
        Usuario u = modelo.getUsuarioActual();
        String nombreArchivo = String.format("%s_%s.xml","Chats", u.getNombreUsuario());
        try {
            JAXBContext ctx = JAXBContext.newInstance(Usuario.class);
            Marshaller mrs = ctx.createMarshaller();
            mrs.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            mrs.marshal(u, new FileOutputStream(nombreArchivo));

        } catch (FileNotFoundException | JAXBException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
    }

    public static void obtieneProductosXML(Usuario u, Modelo modelo) {
        String nombreArchivo = String.format("%s_%s.xml","Chats", u.getNombreUsuario());
        Usuario usuario;
        try {
            JAXBContext ctx = JAXBContext.newInstance(Usuario.class);
            Unmarshaller mrs = ctx.createUnmarshaller();
            usuario = (Usuario) mrs.unmarshal(new FileInputStream(nombreArchivo));
            modelo.getUsuarioActual().setContatos(usuario.getContatos());
            modelo.getUsuarioActual().setChats(usuario.getChats());

        } catch (FileNotFoundException | JAXBException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
    }

}
