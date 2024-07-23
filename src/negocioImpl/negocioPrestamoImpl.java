package negocioImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import datosImpl.prestamoDaoImpl;
import entidad.prestamo;
import negocio.negocioPrestamo;

public class negocioPrestamoImpl implements negocioPrestamo{

	private prestamoDaoImpl neg = new prestamoDaoImpl();

	@Override
	public boolean guardarPrestamo(prestamo prestamo) {
		
		return neg.guardarPrestamo(prestamo);
	}

	@Override
	public boolean existePrestamoPorSolicitud(int idSolicitud) {
		// TODO Auto-generated method stub
		return neg.existePrestamoPorSolicitud(idSolicitud);
	}

	@Override
	public ArrayList<prestamo> obtenerPrestamosOtorgados(Date fechaInicio, Date fechaFin) {
		return neg.obtenerPrestamosOtorgados(fechaInicio, fechaFin);
	}
	
	@Override
	public List<prestamo> listarPrestamosPorCliente(int id) {
		return neg.listarPrestamosPorCliente(id);
	}

}
