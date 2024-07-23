package negocioImpl;

import java.util.ArrayList;

import datosImpl.notificacionDaoImpl;
import entidad.notificacion;
import negocio.negocioNotificacion;

public class negocioNotificacionImpl implements negocioNotificacion {
	private notificacionDaoImpl notificacionDao;

    public negocioNotificacionImpl() {
        notificacionDao = new notificacionDaoImpl();
    }

	@Override
	public ArrayList<notificacion> obtenerNotificacionesPorCliente(int idCliente) {
		return notificacionDao.obtenerNotificacionesPorCliente(idCliente);
	}

	@Override
	public boolean insertarNotificacion(notificacion notificacion) {
		return notificacionDao.insertarNotificacion(notificacion);
	}

	@Override
	public boolean marcarNotificacionComoLeida(int idNotificacion) {
		return notificacionDao.marcarNotificacionComoLeida(idNotificacion);
	}

	@Override
	public int contarNotificacionesNoLeidas(int idCliente) {
		return notificacionDao.contarNotificacionesNoLeidas(idCliente);
	}

}
