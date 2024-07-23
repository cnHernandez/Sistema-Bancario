package datos;

import java.util.ArrayList;

import entidad.notificacion;

public interface notificacionDao {
    ArrayList<notificacion> obtenerNotificacionesPorCliente(int idCliente);
    boolean insertarNotificacion(notificacion notificacion);
    boolean marcarNotificacionComoLeida(int idNotificacion);
    int contarNotificacionesNoLeidas(int idCliente);
}
