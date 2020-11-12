package chat.entidades;

/**
 *
 * @author una
 */
public class EventoActualizacion {

    public EventoActualizacion(OperacionActualizacion operacion, Object info) {
        this.operacion = operacion;
        this.info = info;
    }

    public OperacionActualizacion getOperacion() {
        return operacion;
    }

    public Object getInfo() {
        return info;
    }

    private final OperacionActualizacion operacion;
    private final Object info;
}
