package datosImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import datos.movimientoDao;
import entidad.movimiento;

public class movimientoDaoImpl implements movimientoDao{
	
	@Override
	public int guardarMovimientoDevolviendoID(movimiento mov) {
	    System.out.println("Debug: Entrando en guardarMovimiento...");

	    // Verificar si mov es null
	    if (mov == null) {
	        System.out.println("Debug: ¡El objeto movimiento (mov) es null!");
	        return -1; // Cambiado para devolver un valor de error
	    }
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conn = Conexion.getConexion().Open();
	        conn.setAutoCommit(false); // Deshabilitar autocommit si lo estás manejando manualmente

	        String sql = "INSERT INTO movimientos (id_cuenta, id_tipo_movimiento, fecha, detalle, importe) VALUES (?, ?, curdate(), ?, ?)";

	        ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        ps.setInt(1, mov.getIdCuenta());
	        ps.setInt(2, mov.getId_tipo_movimiento());
	        ps.setString(3, mov.getDetalle());
	        ps.setBigDecimal(4, mov.getImporte());

	        int rowsAffected = ps.executeUpdate();
	        if (rowsAffected > 0) {
	            rs = ps.getGeneratedKeys();
	            if (rs.next()) {
	                int idMovimiento = rs.getInt(1);
	                System.out.println("Debug: Movimiento guardado exitosamente. ID: " + idMovimiento);
	                conn.commit();
	                return idMovimiento;
	            }
	        } else {
	            System.out.println("Debug: No se pudo guardar el movimiento.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (ps != null) {
	                ps.close();
	            }
	            if (conn != null) {
	                conn.close(); 
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return -1; 
	}
	
	@Override
	public boolean guardarMovimiento(movimiento mov) {
		System.out.println("Debug: Entrando en guardarMovimiento...");

	    // Verificar si mov es null
	    if (mov == null) {
	        System.out.println("Debug: ¡El objeto movimiento (mov) es null!");
	        return false;
	    }

	    // Verificar los valores de mov antes de usarlos
	    System.out.println("Debug: ID de cuenta: " + mov.getIdCuenta());
	    System.out.println("Debug: ID tipo movimiento: " + mov.getId_tipo_movimiento());
	    System.out.println("Debug: Fecha: " + mov.getFecha());
	    System.out.println("Debug: Detalle: " + mov.getDetalle());
	    System.out.println("Debug: Importe: " + mov.getImporte());

	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = Conexion.getConexion().Open();
	        // Deshabilitar autocommit si lo estás manejando manualmente
	        conn.setAutoCommit(false);

	        String sql = "INSERT INTO movimientos (id_cuenta, id_tipo_movimiento, fecha, detalle, importe) VALUES (?, ?, curdate(), ?, ?)";

	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, mov.getIdCuenta());
	        ps.setInt(2, mov.getId_tipo_movimiento());
	       
	        ps.setString(3, mov.getDetalle());
	        ps.setBigDecimal(4, mov.getImporte());

	        int rowsAffected = ps.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Debug: Movimiento guardado exitosamente.");
	            // Commit explícito si deshabilitaste autocommit
	            conn.commit();
	            return true;
	        } else {
	            System.out.println("Debug: No se pudo guardar el movimiento.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (ps != null) {
	                ps.close();
	            }
	            if (conn != null) {
	                // Restaurar autocommit si lo deshabilitaste
	                // conn.setAutoCommit(true);
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return false;
	}
	
	private static final String obtenerMovimientosPorCuenta = "SELECT * FROM movimientos WHERE id_cuenta = ?";
	
	@Override
	public ArrayList<movimiento> obtenerMovimientosPorCuenta(int idCuenta) {
		ArrayList<movimiento> movimientos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConexion().Open();
            ps = conn.prepareStatement(obtenerMovimientosPorCuenta);
            ps.setInt(1, idCuenta);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                movimiento mov = new movimiento();
                mov.setIdMovimiento(rs.getInt("id_movimiento"));
                mov.setIdCuenta(rs.getInt("id_cuenta"));
                mov.setId_tipo_movimiento(rs.getInt("id_tipo_movimiento"));
                mov.setFecha(rs.getDate("fecha"));
                mov.setDetalle(rs.getString("detalle"));
                mov.setImporte(rs.getBigDecimal("importe"));
                movimientos.add(mov);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return movimientos;
	}
	
	
	
	@Override
	public ArrayList<movimiento> obtenerMovimientos() {
		ArrayList<movimiento> movimientos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConexion().Open();
            String sql = "select * from movimientos";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                movimiento mov = new movimiento();
                mov.setIdMovimiento(rs.getInt("id_movimiento"));
                mov.setIdCuenta(rs.getInt("id_cuenta"));
                mov.setId_tipo_movimiento(rs.getInt("id_tipo_movimiento"));
                mov.setFecha(rs.getDate("fecha"));
                mov.setDetalle(rs.getString("detalle"));
                mov.setImporte(rs.getBigDecimal("importe"));
                movimientos.add(mov);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return movimientos;
	}

	@Override
	public ArrayList<movimiento> obtenerMovimientosEntreFechas(Date fechaInicio, Date fechaFin) {
	    ArrayList<movimiento> movimientos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConexion().Open(); // Obtener la conexión a la base de datos (implementación específica en tu clase Conexion)
            String query = "SELECT * FROM movimientos WHERE fecha BETWEEN ? AND ?";
            stmt = conn.prepareStatement(query);
            stmt.setDate(1, fechaInicio);
            stmt.setDate(2, fechaFin);
            rs = stmt.executeQuery();

            while (rs.next()) {
                movimiento mov = new movimiento();
                mov.setIdMovimiento(rs.getInt("id_movimiento"));
                mov.setIdCuenta(rs.getInt("id_cuenta"));
                mov.setId_tipo_movimiento(rs.getInt("id_tipo_movimiento"));
                mov.setFecha(rs.getDate("fecha"));
                mov.setDetalle(rs.getString("detalle"));
                mov.setImporte(rs.getBigDecimal("importe"));
                movimientos.add(mov);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Manejo básico de excepciones, mejorar según el caso
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Manejo de excepciones al cerrar conexiones
            }
        }

        return movimientos;
    }
	}
	
	
	

	
	
