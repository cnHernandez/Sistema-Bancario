package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import datos.direccionDao;
import entidad.direccion;
import excepciones.DireccionInsertarException;
import excepciones.DireccionModificarException;

public class direccionDaoImpl implements direccionDao{

	@Override
	public int insertar(direccion dir) throws DireccionInsertarException {
		 Connection con = Conexion.getConexion().Open();
	        int idDireccionInsertada = -1; // Valor por defecto si la inserción falla
	        try {
	            PreparedStatement ps = con.prepareStatement("INSERT INTO direccion (calle, numero, id_localidad) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
	            ps.setString(1, dir.getCalle());
	            ps.setString(2, dir.getNumero());
	            ps.setInt(3, dir.getLocalidad().getIdLocalidad());

	            int filasInsertadas = ps.executeUpdate();
	            if (filasInsertadas > 0) {
	                // Obtener el ID generado
	                ResultSet generatedKeys = ps.getGeneratedKeys();
	                if (generatedKeys.next()) {
	                    idDireccionInsertada = generatedKeys.getInt(1); // Obtener el primer valor generado (el ID)
	                }
	            }
	        } catch (SQLException e) {
	            throw new DireccionInsertarException("Error al insertar la dirección.", e);
	        } finally {
	            try {
	                if (con != null) con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return idDireccionInsertada;
	}
	
	 @Override
	    public boolean modificar(direccion dir) throws DireccionModificarException {
		 Connection conexion = null;
	        PreparedStatement statement = null;
	        boolean isUpdateExitoso = false;

	        try {
	            conexion = Conexion.getConexion().Open();
	            statement = conexion.prepareStatement("UPDATE direccion SET calle = ?, numero = ?, id_localidad = ? WHERE id_direccion = ?");

	            statement.setString(1, dir.getCalle());
	            statement.setString(2, dir.getNumero());
	            statement.setInt(3, dir.getLocalidad().getIdLocalidad());
	            statement.setInt(4, dir.getIdDireccion());

	            if (statement.executeUpdate() > 0) {
	                conexion.commit();
	                isUpdateExitoso = true;
	            }

	        } catch (SQLException e) {
	            throw new DireccionModificarException("Error al modificar la dirección.", e);
	        } finally {
	            try {
	                if (statement != null) statement.close();
	                if (conexion != null) conexion.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        return isUpdateExitoso;
	    }
	
}


