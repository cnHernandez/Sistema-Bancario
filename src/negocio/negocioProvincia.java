package negocio;

import java.util.ArrayList;

import entidad.provincia;
import excepciones.InsertarProvinciaException;
import excepciones.ListarProvinciasException;
import excepciones.ObtenerProvinciaPorIdException;
import excepciones.ObtenerProvinciaPorNombreException;
import excepciones.ProvinciaExisteException;

public interface negocioProvincia {
	
	   public void insertarProvincia(provincia provincia) throws InsertarProvinciaException;
	    public boolean existe(provincia prov) throws ProvinciaExisteException;
	    public provincia obtenerPorNombre(String nombre) throws ObtenerProvinciaPorNombreException;
	    public provincia obtenerPorId(int id) throws ObtenerProvinciaPorIdException;
	    public ArrayList<provincia> listarProvincias() throws ListarProvinciasException;
}
