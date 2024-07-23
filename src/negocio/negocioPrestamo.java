package negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entidad.prestamo;

public interface negocioPrestamo {
	public boolean guardarPrestamo(prestamo prestamo);
	boolean existePrestamoPorSolicitud(int idSolicitud);
	public ArrayList<prestamo> obtenerPrestamosOtorgados(Date fechaInicio, Date fechaFin);
	public List<prestamo> listarPrestamosPorCliente(int idCliente);
}
