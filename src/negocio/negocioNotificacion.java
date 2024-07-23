package negocio;

import java.util.ArrayList;

import entidad.notificacion;

public interface negocioNotificacion {
	ArrayList<notificacion> obtenerNotificacionesPorCliente(int idCliente);
	 boolean insertarNotificacion(notificacion notificacion);
	 boolean marcarNotificacionComoLeida(int idNotificacion);
	 int contarNotificacionesNoLeidas(int idCliente);
}
