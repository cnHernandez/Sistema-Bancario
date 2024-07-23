package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import datos.transferenciaDao;

public class transferenciaDaoImpl implements transferenciaDao{

	@Override
	public boolean insertarTransferencia(int idMovimientoOrigen, int idMovimientoDestino) {
	    Connection con = null;
	    PreparedStatement ps = null;

	    try {
	        con = Conexion.getConexion().Open();
	        con.setAutoCommit(false);

	        String sql = "INSERT INTO transferencias (id_movimiento_origen, id_movimiento_destino) VALUES (?, ?)";
	        ps = con.prepareStatement(sql);
	        ps.setInt(1, idMovimientoOrigen);
	        ps.setInt(2, idMovimientoDestino);

	        int rowsAffected = ps.executeUpdate();
	        if (rowsAffected > 0) {
	            con.commit(); 
	            System.out.println("Debug: Transferencia insertada con éxito.");
	            return true;
	        } else {
	            con.rollback(); 
	            System.out.println("Debug: No se pudo insertar la transferencia.");
	        }
	    } catch (SQLException e) {
	        try {
	            if (con != null) {
	                con.rollback();
	            }
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            if (ps != null) {
	                ps.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return false;
	}



}
