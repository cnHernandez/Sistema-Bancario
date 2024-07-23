package datos;

import java.sql.Date;
import java.util.ArrayList;

import entidad.movimiento;

public interface movimientoDao {
	public boolean guardarMovimiento(movimiento mov);
	public int guardarMovimientoDevolviendoID(movimiento mov);
	public ArrayList<movimiento> obtenerMovimientosPorCuenta (int idCuenta);
	public ArrayList<movimiento> obtenerMovimientos ();
	 public ArrayList<movimiento> obtenerMovimientosEntreFechas(Date fechaInicio, Date fechaFin);
}
