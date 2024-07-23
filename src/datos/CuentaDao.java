package datos;

import java.util.ArrayList;


import entidad.cuenta;
import entidad.tipo_de_cuenta;

public interface CuentaDao {
	
	boolean AgregarCuenta(cuenta cuenta);
	boolean ModificarCuenta(cuenta cuenta);
	boolean EliminarCuenta(int idCuenta);
	ArrayList<cuenta> listarCuentas();
	cuenta listarIndividual(int id);
	public ArrayList<cuenta> obtenerCuentasPorCliente(int idCliente);
	public ArrayList<tipo_de_cuenta> obtenerTiposDeCuenta();
	public boolean existeCbu(String cbu);
	 public String obtenerNombreYApellidoPorCbu(String cbu);
	 public int obtenerIdCuenta(String cbu);
	 public boolean TranferirEntreCuentas(String cbu, float monto);
	 public boolean updatearSaldoTranferenciaOrigen(int idCuenta, double monto);
	 public boolean updatearSaldoTranferenciaDestino(int idCuenta, double monto);
	 public boolean actualizarSaldo(int idCuenta, double monto);
	 public String obtenerCbu(int id_cuenta);
	 public double obtenerSaldo(int idCuenta);
	 public boolean cuentaDestinoActiva(int id_cuenta_destino);
}

