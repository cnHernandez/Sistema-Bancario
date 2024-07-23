package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import datosImpl.Conexion;
import datos.solicitudPrestamoDao;
import entidad.cliente;
import entidad.cuenta;
import entidad.solicitudPrestamo;

public class solicitudPrestamoDaoImpl implements solicitudPrestamoDao{

	@Override
	public boolean guardarSolicitudPrestamo(solicitudPrestamo solicitud) {
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

	        String query = "INSERT INTO solicitudesprestamos (id_cliente, id_cuenta, fecha, importe_pedido, plazo_meses, estado_solicitud, tasa_interes) "
	                     + "VALUES (?, ?, ?, ?, ?, ?, ?)";
	        
	        pst = conexion.prepareStatement(query);
	        pst.setInt(1, solicitud.getCliente().getIdCliente());
	        pst.setInt(2, solicitud.getCuenta().getIdCuenta());
	        pst.setDate(3, solicitud.getFecha());
	        pst.setDouble(4, solicitud.getImportePedido());
	        pst.setInt(5, solicitud.getPlazoMeses());
	        pst.setString(6, solicitud.getEstadoSolicitud());
	        pst.setDouble(7, solicitud.getTasaInteres());

	        int filasAfectadas = pst.executeUpdate();
	        if (filasAfectadas > 0) {
	            exito = true;
	            System.out.println("Solicitud de préstamo guardada exitosamente.");
	        } else {
	            System.out.println("No se afectaron filas.");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al guardar la solicitud de préstamo: " + e.getMessage());
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
	public List<solicitudPrestamo> listarSolicitudesPrestamo() {
		  List<solicitudPrestamo> listaSolicitudes = new ArrayList<>();
		    Connection conexion = null;
		    PreparedStatement pst = null;
		    ResultSet rs = null;

		    try {
		        conexion = Conexion.getConexion().Open();
		        
		        String query = "SELECT sp.id_solicitud, sp.id_cliente, sp.id_cuenta, sp.fecha, sp.importe_pedido, sp.plazo_meses, sp.estado_solicitud, sp.tasa_interes, c.nombre, c.apellido " +
		                       "FROM solicitudesPrestamos sp " +
		                       "JOIN clientes c ON sp.id_cliente = c.id_cliente";
		        
		        pst = conexion.prepareStatement(query);
		        rs = pst.executeQuery();
		        
		        while (rs.next()) {
		            solicitudPrestamo solicitud = new solicitudPrestamo();
		            
		            solicitud.setIdSolicitud(rs.getInt("id_solicitud"));
		           
		            cuenta cuenta = new cuenta();
		            cuenta.setIdCuenta(rs.getInt("id_cuenta"));
		            solicitud.setCuenta(cuenta);
		            
		            cliente cliente = new cliente();
		            cliente.setIdCliente(rs.getInt("id_cliente"));
		            cliente.setNombre(rs.getString("nombre"));
		            cliente.setApellido(rs.getString("apellido"));
		            solicitud.setCliente(cliente);
		            
		            solicitud.setFecha(rs.getDate("fecha"));
		            solicitud.setImportePedido(rs.getDouble("importe_pedido"));
		            solicitud.setPlazoMeses(rs.getInt("plazo_meses"));
		            solicitud.setEstadoSolicitud(rs.getString("estado_solicitud"));
		            solicitud.setTasaInteres(rs.getDouble("tasa_interes"));
		            
		            listaSolicitudes.add(solicitud);
		        }
		    } catch (SQLException e) {
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
		            e.printStackTrace();
		        }
		    }

		    return listaSolicitudes;
		    }

	@Override
	public boolean actualizarEstadoSolicitud(int idSolicitud, String nuevoEstado) {
		boolean exito = false;
        Connection conexion = null;
        PreparedStatement pst = null;

        try {
        	System.out.println("Intentando obtener la conexión de Solicitud...");
            conexion = Conexion.getConexion().Open();
            
            // Verificar si la conexión está en modo auto-commit
            if (!conexion.getAutoCommit()) {
                conexion.setAutoCommit(true); // Configurar el modo auto-commit
            }

            
            String query = "UPDATE solicitudesPrestamos SET estado_solicitud = ? WHERE id_solicitud = ?";
            pst = conexion.prepareStatement(query);
            pst.setString(1, nuevoEstado);
            pst.setInt(2, idSolicitud);

            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas > 0) {
                exito = true;
            System.out.println("Solicitud actualizada exitosamente.");
        } else {
            System.out.println("No se afectaron filas.");
        }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exito;
    }
	}


