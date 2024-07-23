package negocioImpl;

import java.util.List;

import datosImpl.solicitudPrestamoDaoImpl;
import entidad.solicitudPrestamo;
import negocio.negocioSolicitudPrestamo;

public class negocioSolicitudPrestamoImpl implements negocioSolicitudPrestamo{

	private solicitudPrestamoDaoImpl solicitud;
	
	public negocioSolicitudPrestamoImpl(){
		this.solicitud = new solicitudPrestamoDaoImpl();
	}
	@Override
	public boolean guardarSolicitudPrestamo(solicitudPrestamo solicitud) {
		// TODO Auto-generated method stub
		return this.solicitud.guardarSolicitudPrestamo(solicitud);
	}
	@Override
	public List<solicitudPrestamo> listarSolicitudesPrestamo() {
		// TODO Auto-generated method stub
		return solicitud.listarSolicitudesPrestamo();
	}
	@Override
	public boolean actualizarEstadoSolicitud(int idSolicitud, String nuevoEstado) {
		// TODO Auto-generated method stub
		return solicitud.actualizarEstadoSolicitud(idSolicitud, nuevoEstado);
	}

}
