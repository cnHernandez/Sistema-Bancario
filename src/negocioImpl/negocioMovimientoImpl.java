package negocioImpl;

import java.sql.Date;
import java.util.ArrayList;

import datosImpl.movimientoDaoImpl;
import entidad.movimiento;
import negocio.negocioMovimiento;

public class negocioMovimientoImpl implements negocioMovimiento{

	private movimientoDaoImpl movimientoDao; 


    public negocioMovimientoImpl() {
        this.movimientoDao = new movimientoDaoImpl();
    }

    @Override
    public boolean guardarMovimiento(movimiento mov) {
        return movimientoDao.guardarMovimiento(mov);
    }

    @Override
    public ArrayList<movimiento> obtenerMovimientosPorCuenta(int idCuenta) {
        return movimientoDao.obtenerMovimientosPorCuenta(idCuenta);
    }

	@Override
	public ArrayList<movimiento> obtenerMovimientos() {
		return movimientoDao.obtenerMovimientos();
	}

	@Override
	public int guardarMovimientoDevolviendoID(movimiento mov) {
		return movimientoDao.guardarMovimientoDevolviendoID(mov);
	}

	@Override
	public ArrayList<movimiento> obtenerMovimientosEntreFechas(Date fechaInicio, Date fechaFin) {
		// TODO Auto-generated method stub
		return movimientoDao.obtenerMovimientosEntreFechas(fechaInicio, fechaFin);
	}

}
