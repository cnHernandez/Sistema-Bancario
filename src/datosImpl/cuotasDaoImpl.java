package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import datosImpl.Conexion;
import java.util.List;
import datos.cuotasDao;
import entidad.cuotas;
import entidad.prestamo;

public class cuotasDaoImpl implements cuotasDao{

	@Override
	public boolean insertarCuotas(prestamo prestamo) {
		PreparedStatement statement = null;
        Connection conexion = Conexion.getConexion().Open();
		 boolean result = false;

		    String sql = "INSERT INTO cuotas (id_prestamo, numero_cuota, fecha_vencimiento, monto, pagada, fecha_pago) "
		               + "VALUES (?, ?, ?, ?, ?, ?)";
		    
		    try {
		        if (conexion != null) {
		            statement = conexion.prepareStatement(sql);

		            for (int i = 1; i <= prestamo.getPlazoMeses(); i++) {
		                cuotas cuota = new cuotas();
		                cuota.setIdPrestamo(prestamo.getIdPrestamo());
		                cuota.setNumeroCuota(i);
		                cuota.setMonto(prestamo.getMontoCuota());
		                cuota.setFechaVencimiento(addMonths(prestamo.getFecha(), i - 1));
		                cuota.setPagada(false);
		                cuota.setFechaPago(null);

		                statement.setInt(1, cuota.getIdPrestamo());
		                statement.setInt(2, cuota.getNumeroCuota());
		                statement.setDate(3, new java.sql.Date(cuota.getFechaVencimiento().getTime()));
		                statement.setDouble(4, cuota.getMonto());
		                statement.setBoolean(5, cuota.isPagada());
		                statement.setNull(6, java.sql.Types.DATE);

		                statement.addBatch();
		            }

		            int[] rows = statement.executeBatch();
		            result = rows.length == prestamo.getPlazoMeses();

		            conexion.commit(); // Commit the transaction
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        if (conexion != null) {
		            try {
		            	conexion.rollback(); // Rollback the transaction on error
		            } catch (SQLException ex) {
		                ex.printStackTrace();
		            }
		        }
		    } finally {
		        try {
		            if (statement != null) statement.close();
		            if (conexion != null) Conexion.getConexion().close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return result;
	}
	
	private java.util.Date addMonths(java.util.Date date, int months) {
	    java.util.Calendar cal = java.util.Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(java.util.Calendar.MONTH, months);
	    return cal.getTime();
	}

	@Override
	public List<cuotas> listarCuotas() {
		 List<cuotas> cuotasList = new ArrayList<>();
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    String sql = "SELECT id_cuota, id_prestamo, numero_cuota, fecha_vencimiento, monto, pagada, fecha_pago " +
		                 "FROM cuotas";

		    try {
		        conn = (Connection) Conexion.getConexion().Open();
		        stmt = (PreparedStatement) conn.prepareStatement(sql);
		        rs = stmt.executeQuery();

		        while (rs.next()) {
		            cuotas cuota = new cuotas();
		            cuota.setIdCuota(rs.getInt("id_cuota"));
		            cuota.setIdPrestamo(rs.getInt("id_prestamo"));
		            cuota.setNumeroCuota(rs.getInt("numero_cuota"));
		            cuota.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
		            cuota.setMonto(rs.getDouble("monto"));
		            cuota.setPagada(rs.getBoolean("pagada"));
		            cuota.setFechaPago(rs.getDate("fecha_pago"));

		            cuotasList.add(cuota);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		            if (conn != null) Conexion.getConexion().close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return cuotasList;
	}

	@Override
	public boolean actualizarEstadoCuotas(int idSolicitud, String nuevoEstado) {
	    boolean updated = false;
	    Connection conn = null;
	    PreparedStatement stmt = null;

	    String sql = "UPDATE cuotas SET estado = ? WHERE id_cuota IN (SELECT id_cuota FROM prestamos WHERE idSolicitud = ?)";

	    try {
	        conn = (Connection) Conexion.getConexion().Open();
	        stmt = (PreparedStatement) conn.prepareStatement(sql);
	        stmt.setString(1, nuevoEstado);
	        stmt.setInt(2, idSolicitud);

	        int rowsAffected = stmt.executeUpdate();
	        updated = rowsAffected > 0;

	        conn.commit();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                conn.rollback();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (conn != null) Conexion.getConexion().close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return updated;
	}

	@Override
	public List<cuotas> listarCuotasPorPrestamo(int idPrestamo) {
		List<cuotas> listaCuotas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConexion().Open();
            String sql = "SELECT id_cuota, id_prestamo, numero_cuota, fecha_vencimiento, monto, pagada, fecha_pago " +
                         "FROM cuotas WHERE id_prestamo = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idPrestamo);
            rs = ps.executeQuery();

            while (rs.next()) {
                cuotas cuota = new cuotas();

                cuota.setIdCuota(rs.getInt("id_cuota"));
                cuota.setIdPrestamo(rs.getInt("id_prestamo"));
                cuota.setNumeroCuota(rs.getInt("numero_cuota"));
                cuota.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
                cuota.setMonto(rs.getDouble("monto"));
                cuota.setPagada(rs.getBoolean("pagada"));
                cuota.setFechaPago(rs.getDate("fecha_pago"));

                listaCuotas.add(cuota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaCuotas;
	}
	
	@Override
	public boolean pagarCuota(int idCuota, int idCuenta, double monto) {
		Connection conn = null;
	    PreparedStatement psUpdate = null;
	    PreparedStatement psInsert = null;

	    try {
	        conn = Conexion.getConexion().Open();
	        conn.setAutoCommit(false); // Deshabilitar auto-commit para gestionar transacción manualmente
	        System.out.println("Conexión abierta.");

	        // Actualizar cuota
	        String sqlUpdateCuota = "UPDATE cuotas SET pagada = 1, fecha_pago = CURRENT_DATE WHERE id_cuota = ?";
	        psUpdate = conn.prepareStatement(sqlUpdateCuota);
	        psUpdate.setInt(1, idCuota);
	        int rowsUpdated = psUpdate.executeUpdate();
	        System.out.println("Filas actualizadas en cuotas: " + rowsUpdated);

	        if (rowsUpdated > 0) {
	            // Insertar en pagos
	            String sqlInsertPago = "INSERT INTO pagos (id_cuota, id_cuenta, fecha_pago, monto) VALUES (?, ?, CURRENT_DATE, ?)";
	            psInsert = conn.prepareStatement(sqlInsertPago);
	            psInsert.setInt(1, idCuota);
	            psInsert.setInt(2, idCuenta);
	            psInsert.setDouble(3, monto);
	            int rowsInserted = psInsert.executeUpdate();
	            System.out.println("Filas insertadas en pagos: " + rowsInserted);

	            if (rowsInserted > 0) {
	                // Commit de la transacción si todo fue exitoso
	                conn.commit();
	                System.out.println("Transacción confirmada.");
	                return true;
	            } else {
	                // Rollback si no se insertaron filas en pagos
	                conn.rollback();
	                System.out.println("Rollback realizado.");
	                return false;
	            }
	        } else {
	            // Rollback si no se actualizó ninguna fila en cuotas
	            conn.rollback();
	            System.out.println("Rollback realizado.");
	            return false;
	        }
	    } catch (SQLException e) {
	        try {
	            if (conn != null) {
	                conn.rollback(); // Rollback en caso de error SQL
	                System.out.println("Rollback debido a excepción SQL.");
	            }
	        } catch (SQLException rollbackException) {
	            rollbackException.printStackTrace();
	        }
	        e.printStackTrace(); // Imprime el stack trace de la excepción SQL
	        return false;
	    } finally {
	        // Cerrar recursos en el bloque finally para asegurar que se liberan correctamente
	        try {
	            if (psUpdate != null) {
	                psUpdate.close();
	                System.out.println("PreparedStatement de actualización cerrado.");
	            }
	            if (psInsert != null) {
	                psInsert.close();
	                System.out.println("PreparedStatement de inserción cerrado.");
	            }
	            if (conn != null) {
	                conn.setAutoCommit(true); // Restaurar auto-commit a su valor predeterminado
	                conn.close();
	                System.out.println("Conexión cerrada.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); // Imprime el stack trace de cualquier error al cerrar los recursos
	        }
	    }
    } 
}
