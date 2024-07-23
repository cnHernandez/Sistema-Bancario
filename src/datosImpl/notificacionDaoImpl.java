package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import datos.notificacionDao;
import entidad.cliente;
import entidad.notificacion;

public class notificacionDaoImpl implements notificacionDao {
	
	@Override
	public boolean marcarNotificacionComoLeida(int idNotificacion) {
	    Connection con = null;
	    PreparedStatement ps = null;
	    boolean resultado = false;

	    try {
	        con = Conexion.getConexion().Open();
	        con.setAutoCommit(false); // Deshabilitar el autocommit
	        
	        String sql = "UPDATE notificaciones SET leida = true WHERE id_notificacion = ?";
	        ps = con.prepareStatement(sql);
	        ps.setInt(1, idNotificacion);

	        int filasAfectadas = ps.executeUpdate();

	        if (filasAfectadas > 0) {
	            resultado = true;
	            con.commit(); // Confirmar la transacción
	        } else {
	            con.rollback(); // Hacer rollback en caso de fallo
	        }
	    } catch (SQLException e) {
	        try {
	            if (con != null) {
	                con.rollback(); // Hacer rollback en caso de excepción
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            if (ps != null) {
	                ps.close();
	            }
	            if (con != null) {
	                con.setAutoCommit(true); // Restaurar el autocommit por defecto
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return resultado;
	}

	@Override
    public ArrayList<notificacion> obtenerNotificacionesPorCliente(int idCliente) {
        ArrayList<notificacion> notificaciones = new ArrayList<>();
        Connection conn = Conexion.getConexion().Open();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM notificaciones WHERE id_cliente = ? AND leida = 0";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idCliente);
            rs = ps.executeQuery();

            while (rs.next()) {
                notificacion notif = new notificacion();
                notif.setIdNotificacion(rs.getInt("id_notificacion"));
                
                // Crear y establecer el cliente asociado
                cliente cliente = new cliente();
                cliente.setIdCliente(rs.getInt("id_cliente")); // Ajusta según la estructura de tu base de datos
                notif.setCliente(cliente);               
                notif.setFecha(rs.getDate("fecha"));
                notif.setMensaje(rs.getString("mensaje"));
                notif.setLeida(rs.getBoolean("leida"));

                notificaciones.add(notif);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	Conexion.getConexion().close();
        }

        return notificaciones;
    }

    @Override
    public boolean insertarNotificacion(notificacion notificacion) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean resultado = false;

        try {
            con = Conexion.getConexion().Open();
            con.setAutoCommit(false); // Deshabilitar el autocommit
            
            String sql = "INSERT INTO notificaciones (id_cliente, fecha, mensaje, leida) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, notificacion.getCliente().getIdCliente());
            ps.setDate(2, new java.sql.Date(notificacion.getFecha().getTime()));
            ps.setString(3, notificacion.getMensaje());
            ps.setBoolean(4, notificacion.isLeida());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                resultado = true;
                con.commit(); // Confirmar la transacción
            } else {
                con.rollback(); // Hacer rollback en caso de fallo
            }
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback(); // Hacer rollback en caso de excepción
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.setAutoCommit(true); // Restaurar el autocommit por defecto
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultado;
    }

	@Override
	public int contarNotificacionesNoLeidas(int idCliente) {
		 int cantidadNoLeidas = 0;
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        String query = "SELECT COUNT(*) AS cantidad FROM notificaciones WHERE id_cliente = ? AND leida = 0";

	        try {
	            conn = Conexion.getConexion().Open();
	            ps = conn.prepareStatement(query);
	            ps.setInt(1, idCliente);
	            rs = ps.executeQuery();

	            if (rs.next()) {
	                cantidadNoLeidas = rs.getInt("cantidad");
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

	        return cantidadNoLeidas;
	}

}
