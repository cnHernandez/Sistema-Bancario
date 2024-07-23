package datos;

import java.util.List;

import entidad.solicitudPrestamo;

public interface solicitudPrestamoDao {
	public boolean guardarSolicitudPrestamo(solicitudPrestamo solicitud);
	public List<solicitudPrestamo> listarSolicitudesPrestamo();
	public boolean actualizarEstadoSolicitud(int idSolicitud, String nuevoEstado) ;
}
