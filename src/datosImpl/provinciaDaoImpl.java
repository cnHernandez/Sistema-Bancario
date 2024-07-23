package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import datos.provinciaDao;
import entidad.provincia;
import excepciones.InsertarProvinciaException;
import excepciones.ListarProvinciasException;
import excepciones.ObtenerProvinciaPorIdException;
import excepciones.ObtenerProvinciaPorNombreException;
import excepciones.ProvinciaExisteException;

public class provinciaDaoImpl implements provinciaDao{

	public provinciaDaoImpl() {
        Conexion.getConexion();
    }
	
    @Override
    public ArrayList<provincia> listarProvincias() throws ListarProvinciasException {
    	ArrayList<provincia> provincias = new ArrayList<>();
        Connection con = Conexion.getConexion().Open();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM provincia");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                provincia prov = new provincia();
                prov.setIdProvincia(rs.getInt("id_provincia"));
                prov.setNombre(rs.getString("nombre"));
                provincias.add(prov);
            }
        } catch (SQLException e) {
        	 throw new ListarProvinciasException("Error al listar las provincias", e);
        } finally {
            Conexion.getConexion().close();
        }
        return provincias;
    }
    
	@Override
	public void insertarProvincia (provincia provincia) throws InsertarProvinciaException {
		  
	    Connection con = Conexion.getConexion().Open();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO provincia (nombre) VALUES (?)");
            ps.setString(1, provincia.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
        	 throw new InsertarProvinciaException("Error al insertar la provincia", e);
        } finally {
            Conexion.getConexion().close();
        }
	}

	@Override
	public boolean existe(provincia prov) throws ProvinciaExisteException {
		Connection con = Conexion.getConexion().Open();
        boolean exists = false;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM provincia WHERE nombre = ?");
            ps.setString(1, prov.getNombre());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
        	 throw new ProvinciaExisteException("Error al verificar si la provincia existe", e);
        } finally {
            Conexion.getConexion().close();
        }
        return exists;
	}

	@Override
	public provincia obtenerPorNombre(String nombre) throws ObtenerProvinciaPorNombreException {
		Connection con = Conexion.getConexion().Open();
        provincia prov = null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM provincia WHERE nombre = ?");
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                prov = new provincia();
                prov.setIdProvincia(rs.getInt("id_provincia"));
                prov.setNombre(rs.getString("nombre"));
            }
        } catch (SQLException e) {
        	throw new ObtenerProvinciaPorNombreException("Error al obtener la provincia por nombre", e);
        } finally {
            Conexion.getConexion().close();
        }
        return prov;
    }

	@Override
	public provincia obtenerPorId(int id) throws ObtenerProvinciaPorIdException {
		Connection con = Conexion.getConexion().Open();
        provincia prov = null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM provincia WHERE id_provincia = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                prov = new provincia();
                prov.setIdProvincia(rs.getInt("id_provincia"));
                prov.setNombre(rs.getString("nombre"));
            }
        } catch (SQLException e) {
        	throw new ObtenerProvinciaPorIdException("Error al obtener la provincia por ID", e);
        } finally {
            Conexion.getConexion().close();
        }
        return prov;
    }
	}



