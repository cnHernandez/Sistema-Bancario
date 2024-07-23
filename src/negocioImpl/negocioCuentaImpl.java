package negocioImpl;

import java.util.ArrayList;

import datosImpl.cuentaDaoImpl;
import entidad.cuenta;
import entidad.tipo_de_cuenta;
import negocio.negocioCuenta;

public class negocioCuentaImpl implements negocioCuenta{

	private cuentaDaoImpl cuentaDao;

	public negocioCuentaImpl() {
        this.cuentaDao = new cuentaDaoImpl();
    }
	
	@Override
	public ArrayList<cuenta> obtenerCuentasPorIdCliente(int idCliente) {
	    return cuentaDao.obtenerCuentasPorIdCliente(idCliente);
	}

	@Override
	public ArrayList<cuenta> listarCuentas() {
		// TODO Auto-generated method stub
		return cuentaDao.listarCuentas();
	}

	@Override
	public boolean agregarCuenta(cuenta cuenta) {
		
		// Verificar si el cliente ya tiene 3 cuentas
        ArrayList<cuenta> cuentasDelCliente = cuentaDao.obtenerCuentasPorCliente(cuenta.getIdCliente());
        if (cuentasDelCliente != null && cuentasDelCliente.size() >= 3) {
            // Lanzar excepción si el cliente ya tiene 3 cuentas
            throw new IllegalStateException("El cliente ya tiene 3 cuentas.");
        }

        // Asignar saldo inicial de $10,000
        cuenta.setSaldo(10000);

        // Agregar la cuenta utilizando el DAO
        return cuentaDao.AgregarCuenta(cuenta);
		
	}

	@Override
	public boolean modificarCuenta(cuenta cuentaModificado) {
		// TODO Auto-generated method stub
		return cuentaDao.ModificarCuenta(cuentaModificado);
	}

	@Override
	public boolean eliminarCuenta(int idCuenta) {
		// TODO Auto-generated method stub
		return cuentaDao.EliminarCuenta(idCuenta);
	}

	@Override
	public boolean actualizarSaldo(int idCuenta, double monto) {
		// TODO Auto-generated method stub
		return cuentaDao.actualizarSaldo(idCuenta, monto);
	}
	
	public ArrayList<tipo_de_cuenta> obtenerTiposDeCuenta() {
		return cuentaDao.obtenerTiposDeCuenta();
	}
	
	public int obtenerIdCuenta(String cbu) {
		return cuentaDao.obtenerIdCuenta(cbu);
	}

	@Override
	public boolean existeCBU(String cbu) {
		// TODO Auto-generated method stub
		return cuentaDao.existeCbu(cbu);
	}

	@Override
	public boolean existeNumeroCuenta(String numeroCuenta) {
		return cuentaDao.existeNumeroCuenta(numeroCuenta);
	}
	
	@Override
	public double obtenerSaldo(int idCuenta) {
		return cuentaDao.obtenerSaldo(idCuenta);
	}
	
	@Override
	public String obtenerNombreYApellidoPorCbu(String cbu) {
		return cuentaDao.obtenerNombreYApellidoPorCbu(cbu);
	}
	
	@Override
	public boolean updatearSaldoTranferenciaOrigen(int idCuenta, double monto) {
		return cuentaDao.updatearSaldoTranferenciaOrigen(idCuenta, monto);
	}
	
	@Override
	public boolean updatearSaldoTranferenciaDestino(int idCuenta, double monto) {
		return cuentaDao.updatearSaldoTranferenciaDestino(idCuenta, monto);
	}

	@Override
	public boolean cuentaDestinoActiva(int id_cuenta_destino) {
		return cuentaDao.cuentaDestinoActiva(id_cuenta_destino);
	}
}
