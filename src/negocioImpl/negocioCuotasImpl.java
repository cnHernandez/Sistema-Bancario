package negocioImpl;

import java.util.List;

import datosImpl.cuotasDaoImpl;
import entidad.cuotas;
import entidad.prestamo;
import negocio.negocioCuotas;

public class negocioCuotasImpl implements negocioCuotas{
	
	private cuotasDaoImpl cuotas;
	
	public negocioCuotasImpl(){
		cuotas = new cuotasDaoImpl();
	}

	@Override
	public boolean insertarCuotas(prestamo prestamo) {
		return this.cuotas.insertarCuotas(prestamo);
	}

	@Override
	public List<cuotas> listarCuotas() {
		return this.cuotas.listarCuotas();
	}

	@Override
	public boolean actualizarEstadoCuotas(int idSolicitud, String nuevoEstado) {
		return this.cuotas.actualizarEstadoCuotas(idSolicitud, nuevoEstado);
	}
	
	@Override
	public List<cuotas> listarCuotasPorPrestamo(int id){
		return this.cuotas.listarCuotasPorPrestamo(id);
	}
	
	public boolean pagarCuota(int idCuota, int idCuenta, double monto) {
		return this.cuotas.pagarCuota(idCuota, idCuenta, monto);
	}
	
}
