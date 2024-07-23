package datos;

import java.util.List;

import entidad.cuotas;
import entidad.prestamo;

public interface cuotasDao {
	public boolean insertarCuotas(prestamo prestamo);
	public List<cuotas> listarCuotas();
	public boolean actualizarEstadoCuotas(int idSolicitud, String nuevoEstado);
	public List<cuotas> listarCuotasPorPrestamo(int idPrestamo); 
	public boolean pagarCuota(int idCuota, int idCuenta, double monto);
}
