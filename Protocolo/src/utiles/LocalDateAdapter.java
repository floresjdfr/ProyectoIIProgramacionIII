package utiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Marvin Aguilar Fuentes
 * @author Jose David Flores Rodriguez
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public String marshal(LocalDate date) {
        return date.format(D_FORMAT);
    }

    @Override
    public LocalDate unmarshal(String date) {
        return LocalDate.parse(date, D_FORMAT);
    }

    public static final DateTimeFormatter D_FORMAT
            = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
