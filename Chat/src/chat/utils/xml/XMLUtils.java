package chat.utils.xml;

import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Georges Alfaro S.
 * @version 1.0
 */
public class XMLUtils {

    public static XMLSerializable loadXML(InputStream in, Class<? extends XMLSerializable> c)
            throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(c);
        Unmarshaller mrs = ctx.createUnmarshaller();
        return (XMLSerializable) mrs.unmarshal(in);
    }

    public static void saveXML(OutputStream out, XMLSerializable obj)
            throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(obj.getClass());
        Marshaller mrs = ctx.createMarshaller();
        mrs.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        mrs.marshal(obj, out);
    }

}
