package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import datos.localidadDao;
import entidad.localidad;
import excepciones.InsertarLocalidadException;
import excepciones.ListarLocalidadesException;
import excepciones.LocalidadExistenteException;
import excepciones.ObtenerLocalidadException;
import excepciones.ObtenerProvinciaPorIdException;

public class localidadDaoImpl implements localidadDao{

	 @Override
	    public ArrayList<localidad> listarLocalidades() throws ListarLocalidadesException  {
	        ArrayList<localidad> localidades = new ArrayList<>();
	        Connection con = Conexion.getConexion().Open();
	        try {
	            PreparedStatement ps = con.prepareStatement("SELECT * FROM localidad");
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                localidad loc = new localidad();
	                loc.setIdLocalidad(rs.getInt("id_localidad"));
	                loc.setNombre(rs.getString("nombre"));
	                provinciaDaoImpl provinciaDao = new provinciaDaoImpl();
	                try {
	                    loc.setProvincia(provinciaDao.obtenerPorId(rs.getInt("id_provincia")));
	                } catch (ObtenerProvinciaPorIdException e) {
	                    // Manejar la excepción ObtenerProvinciaPorIdException
	                    throw new ListarLocalidadesException("Error al obtener la provincia para la localidad", e);
	                }
	                localidades.add(loc);
	            }
	        } catch (SQLException e) {
	        	 throw new ListarLocalidadesException("Error al listar las localidades", e);
	        } finally {
	            Conexion.getConexion().close();
	        }
	        return localidades;
	    }
	    
	    @Override
	    public ArrayList<localidad> listarLocalidadesProv(int prov) throws ListarLocalidadesException {
	        ArrayList<localidad> localidades = new ArrayList<>();
	        Connection con = Conexion.getConexion().Open();
	        try {
	            PreparedStatement ps = con.prepareStatement("SELECT * FROM localidad WHERE id_provincia = ?");
	            ps.setInt(1, prov); // Establecer el ID de provincia como parámetro
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                localidad loc = new localidad();
	                loc.setIdLocalidad(rs.getInt("id_localidad"));
	                loc.setNombre(rs.getString("nombre"));
	                
	                // Obtener la provincia asociada (opcional, dependiendo de tus necesidades)
	                provinciaDaoImpl provinciaDao = new provinciaDaoImpl();
	                try {
	                    loc.setProvincia(provinciaDao.obtenerPorId(rs.getInt("id_provincia")));
	                } catch (ObtenerProvinciaPorIdException e) {
	                    // Manejar la excepción ObtenerProvinciaPorIdException
	                    throw new ListarLocalidadesException("Error al obtener la provincia para la localidad", e);
	                }
	                
	                localidades.add(loc);
	            }
	        } catch (SQLException e) {
	        	 throw new ListarLocalidadesException("Error al listar las localidades por provincia", e);
	        } finally {
	            Conexion.getConexion().close();
	        }
	        return localidades;
	    }

	    
	@Override
	public void insertarLocalidad(localidad localidad) throws InsertarLocalidadException {
		Connection con = Conexion.getConexion().Open();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO localidad (nombre, id_provincia) VALUES (?, ?)");
            ps.setString(1, localidad.getNombre());
            ps.setInt(2, localidad.getProvincia().getIdProvincia());
            ps.executeUpdate();
        } catch (SQLException e) {
        	 throw new InsertarLocalidadException("Error al insertar la localidad", e);
        } finally {
            Conexion.getConexion().close();
        }
		
	}

	@Override
	public boolean existe(localidad localidad) throws LocalidadExistenteException {
		 Connection con = Conexion.getConexion().Open();
	        boolean exists = false;
	        try {
	            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM localidad WHERE nombre = ?");
	            ps.setString(1, localidad.getNombre());
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                exists = rs.getInt(1) > 0;
	            }
	        } catch (SQLException e) {
	        	 throw new LocalidadExistenteException("Error al verificar la existencia de la localidad", e);
	        } finally {
	            Conexion.getConexion().close();
	        }
	        return exists;
	}

	@Override
	public localidad obtenerPorNombre(String nombre) throws ObtenerLocalidadException {
		 Connection con = Conexion.getConexion().Open();
		    localidad loc = null;
		    try {
		        PreparedStatement ps = con.prepareStatement("SELECT * FROM localidad WHERE nombre = ?");
		        ps.setString(1, nombre);
		        ResultSet rs = ps.executeQuery();
		        if (rs.next()) {
		            loc = new localidad();
		            loc.setIdLocalidad(rs.getInt("id_localidad"));
		            loc.setNombre(rs.getString("nombre"));
		            // Obtener la provincia asociada
		            provinciaDaoImpl provinciaDao = new provinciaDaoImpl();
		            try {
		                loc.setProvincia(provinciaDao.obtenerPorId(rs.getInt("id_provincia")));
		            } catch (ObtenerProvinciaPorIdException e) {
		                throw new ObtenerLocalidadException("Error al obtener la provincia asociada a la localidad", e);
		            }
		        }
		    } catch (SQLException e) {
		        throw new ObtenerLocalidadException("Error al obtener la localidad por nombre", e);
		    } finally {
		        Conexion.getConexion().close();
		    }
		    return loc;
	    }

	@Override
	public localidad obtenerPorId(int id)  throws ObtenerLocalidadException{
		Connection con = Conexion.getConexion().Open();
        localidad loc = null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM localidad WHERE id_localidad = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                loc = new localidad();
                loc.setIdLocalidad(rs.getInt("id_localidad"));
                loc.setNombre(rs.getString("nombre"));
            }
        } catch (SQLException e) {
        	 throw new ObtenerLocalidadException("Error al obtener la localidad por ID", e);
        } finally {
            Conexion.getConexion().close();
        }
        return loc;
	}
	
	}

