package datos;

import java.util.ArrayList;

import entidad.localidad;
import excepciones.InsertarLocalidadException;
import excepciones.ListarLocalidadesException;
import excepciones.LocalidadExistenteException;
import excepciones.ObtenerLocalidadException;


public interface localidadDao {
	 
	 public void insertarLocalidad(localidad localidad) throws InsertarLocalidadException;
	    public boolean existe(localidad localidad) throws LocalidadExistenteException;
	    public localidad obtenerPorNombre(String nombre) throws ObtenerLocalidadException;
	    ArrayList<localidad> listarLocalidades() throws ListarLocalidadesException;
	    public localidad obtenerPorId(int id) throws ObtenerLocalidadException;
	    public ArrayList<localidad> listarLocalidadesProv(int prov) throws ListarLocalidadesException;
}
