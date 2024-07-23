package negocio;

import java.util.ArrayList;

import entidad.cuenta;
import entidad.tipo_de_cuenta;

public interface negocioCuenta {

	ArrayList<cuenta> obtenerCuentasPorIdCliente(int idCliente);
    ArrayList<cuenta> listarCuentas();
    boolean agregarCuenta(cuenta cuenta);
    boolean modificarCuenta(cuenta cuentaModificado);
    boolean eliminarCuenta(int idCuenta);
    boolean actualizarSaldo(int idCuenta, double monto);
    ArrayList<tipo_de_cuenta> obtenerTiposDeCuenta();
    int obtenerIdCuenta(String cbu);
    public boolean existeCBU(String cbu);
    public boolean existeNumeroCuenta(String numeroCuenta);
    public double obtenerSaldo(int idCuenta);
    public String obtenerNombreYApellidoPorCbu(String cbu);
    public boolean updatearSaldoTranferenciaOrigen(int idCuenta, double monto);
    public boolean updatearSaldoTranferenciaDestino(int idCuenta, double monto);
    public boolean cuentaDestinoActiva(int id_cuenta_destino);
}

