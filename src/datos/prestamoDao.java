package datos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entidad.prestamo;

public interface prestamoDao {
	public boolean guardarPrestamo(prestamo prestamo);
	boolean existePrestamoPorSolicitud(int idSolicitud);
	public List<prestamo> listarPrestamosPorCliente(int idCliente);
	public ArrayList<prestamo> obtenerPrestamosOtorgados(Date fechaInicio, Date fechaFin);
}
