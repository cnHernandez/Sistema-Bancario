package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import datos.pagosDao;
import entidad.cuenta;
import entidad.cuotas;
import entidad.pago;

public class pagosDaoImpl implements pagosDao{

	private static final String obtenerPagosPorIdCliente = 
		    "SELECT p.id_pago, p.id_cuota, p.id_cuenta, p.fecha_pago, p.monto " +
		    "FROM pagos p " +
		    "INNER JOIN cuentas c ON p.id_cuenta = c.id_cuenta " +
		    "WHERE c.id_cliente = ?";
	
	@Override
	public ArrayList<pago> obtenerPagosPorIdCliente(int idCliente) {
		
		ArrayList<pago> pagos = new ArrayList<>();
		    Connection conn = Conexion.getConexion().Open();

		    try {
		        PreparedStatement stmt = conn.prepareStatement(obtenerPagosPorIdCliente);
		        stmt.setInt(1, idCliente);
		        ResultSet rs = stmt.executeQuery();

		        while (rs.next()) {
		            pago pago = new pago();
		            pago.setIdPago(rs.getInt("id_pago"));
		            
		            cuotas cuota = new cuotas();
		            cuota.setIdCuota(rs.getInt("id_cuota"));
		            // Si tienes más detalles de la cuota en la tabla 'cuotas', podrías configurarlos aquí

		            cuenta cuenta = new cuenta();
		            cuenta.setIdCuenta(rs.getInt("id_cuenta"));
		            // Si necesitas más detalles de la cuenta, puedes configurarlos aquí

		            pago.setIdCuota(cuota);
		            pago.setIdCuenta(cuenta);
		            pago.setFechaPago(rs.getDate("fecha_pago"));
		            pago.setMonto(rs.getBigDecimal("monto"));

		            pagos.add(pago);
		        }

		        rs.close();
		        stmt.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return pagos;
	}

}
