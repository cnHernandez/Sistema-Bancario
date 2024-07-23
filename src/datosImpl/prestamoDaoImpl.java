package datosImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import datos.notificacionDao;
import datos.prestamoDao;
import entidad.notificacion;
import entidad.prestamo;

public class prestamoDaoImpl implements prestamoDao{

	@Override
	public boolean guardarPrestamo(prestamo prestamo) {
	     boolean exito = false;
	        Connection conexion = null;
	        PreparedStatement pst = null;

	        try {
	            System.out.println("Intentando obtener la conexión...");
	            conexion = Conexion.getConexion().Open(); 

	            // Verificar si la conexión está en modo auto-commit
	            if (!conexion.getAutoCommit()) {
	                conexion.setAutoCommit(true); // Configurar el modo auto-commit
	            }

	            String query = "INSERT INTO prestamos (idCliente, idSolicitud, fecha, importeTotal, importePedido, plazoMeses, montoCuota, idCuenta, autorizado) "
	                         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	            
	            pst = conexion.prepareStatement(query);
	            pst.setInt(1, prestamo.getCliente().getIdCliente());
	            pst.setInt(2, prestamo.getSolicitudPrestamo().getIdSolicitud());
	            pst.setDate(3, (Date) prestamo.getFecha());
	            pst.setDouble(4, prestamo.getImporteTotal());
	            pst.setDouble(5, prestamo.getImportePedido());
	            pst.setInt(6, prestamo.getPlazoMeses());
	            pst.setDouble(7, prestamo.getMontoCuota());
	            pst.setInt(8, prestamo.getCuenta().getIdCuenta());
	            pst.setBoolean(9, prestamo.isAutorizado());

	            int filasAfectadas = pst.executeUpdate();
	            if (filasAfectadas > 0) {
	                exito = true;
	                System.out.println("Préstamo guardado exitosamente.");
	                notificacion notificacion = new notificacion();
	                notificacion.setCliente(prestamo.getCliente()); // Setear el cliente directamente
	                notificacion.setFecha(new java.util.Date());
	                notificacion.setMensaje("Su préstamo ha sido aprobado $" + prestamo.getImportePedido());
	                notificacion.setLeida(false);

	                notificacionDao notificacionDao = new notificacionDaoImpl(); // Utilizar la interfaz para mayor flexibilidad
	                boolean notificacionGuardada = notificacionDao.insertarNotificacion(notificacion);
	                if (!notificacionGuardada) {
	                    System.out.println("No se pudo guardar la notificación.");
	                }
	            } else {
	                System.out.println("No se afectaron filas.");
	            }
	        } catch (SQLException e) {
	            System.out.println("Error al guardar el préstamo: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            try {
	                if (pst != null) {
	                    pst.close();
	                }
	                if (conexion != null) {
	                    conexion.close();
	                }
	            } catch (SQLException e) {
	                System.out.println("Error al cerrar recursos: " + e.getMessage());
	                e.printStackTrace();
	            }
	        }

	        return exito;
	    }

	@Override
	public boolean existePrestamoPorSolicitud(int idSolicitud) {
		  boolean existe = false;
		    System.out.println("Intentando obtener la conexión...");

		    String query = "SELECT COUNT(*) FROM prestamos WHERE idSolicitud = ?";

		    try (Connection con = Conexion.getConexion().Open();
		         PreparedStatement ps = con.prepareStatement(query)) {

		        ps.setInt(1, idSolicitud);

		        try (ResultSet rs = ps.executeQuery()) {
		            if (rs.next()) {
		                existe = rs.getInt(1) > 0;
		                System.out.println("Consulta ejecutada correctamente. Existe: " + existe);
		            } else {
		                System.out.println("No se encontraron resultados para idSolicitud: " + idSolicitud);
		            }
		        }
		    } catch (SQLException e) {
		        System.out.println("Error al verificar la existencia del préstamo: " + e.getMessage());
		        e.printStackTrace();
		    }

		    return existe;
	}

	@Override
		public List<prestamo> listarPrestamosPorCliente(int idCliente) {
		    List<prestamo> listaPrestamos = new ArrayList<>();
		    Connection conexion = null;
		    PreparedStatement pst = null;
		    ResultSet rs = null;

		    try {
		        conexion = Conexion.getConexion().Open();
		        String query = "SELECT idPrestamo, fecha, importeTotal, importePedido, plazoMeses, montoCuota, autorizado " +
		                       "FROM prestamos " +
		                       "WHERE idCliente = ?";
		        pst = conexion.prepareStatement(query);
		        pst.setInt(1, idCliente);
		        rs = pst.executeQuery();

		        while (rs.next()) {
		            prestamo prestamo = new prestamo();
		            prestamo.setIdPrestamo(rs.getInt("idPrestamo"));
		            prestamo.setFecha(rs.getDate("fecha"));
		            prestamo.setImporteTotal(rs.getDouble("importeTotal"));
		            prestamo.setImportePedido(rs.getDouble("importePedido"));
		            prestamo.setPlazoMeses(rs.getInt("plazoMeses"));
		            prestamo.setMontoCuota(rs.getDouble("montoCuota"));
		            prestamo.setAutorizado(rs.getBoolean("autorizado"));

		            listaPrestamos.add(prestamo);
		        }
		    } catch (SQLException e) {
		        System.out.println("Error al listar los préstamos por cliente: " + e.getMessage());
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) {
		                rs.close();
		            }
		            if (pst != null) {
		                pst.close();
		            }
		            if (conexion != null) {
		                conexion.close();
		            }
		        } catch (SQLException e) {
		            System.out.println("Error al cerrar recursos: " + e.getMessage());
		            e.printStackTrace();
		        }
		    }

		    return listaPrestamos;
	}

	@Override
	public ArrayList<prestamo> obtenerPrestamosOtorgados(java.util.Date fechaInicio, java.util.Date fechaFin) {
		ArrayList<prestamo> prestamos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConexion().Open();
            String sql = "SELECT p.fecha, p.importeTotal, p.importePedido, p.plazoMeses " +
                         "FROM prestamos p " +
                         "WHERE p.fecha BETWEEN ? AND ?";
            ps = conn.prepareStatement(sql);
            ps.setDate(1, (Date) fechaInicio);
            ps.setDate(2, (Date) fechaFin);
            rs = ps.executeQuery();

            while (rs.next()) {
                prestamo prestamo = new prestamo();
                prestamo.setFecha(rs.getDate("fecha"));
                prestamo.setImporteTotal(rs.getDouble("importeTotal"));
                prestamo.setImportePedido(rs.getDouble("importePedido"));
                prestamo.setPlazoMeses(rs.getInt("plazoMeses"));
                prestamos.add(prestamo);
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

        return prestamos;
	}


	
}

