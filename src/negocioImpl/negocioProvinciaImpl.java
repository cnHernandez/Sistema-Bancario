package negocioImpl;

import java.util.ArrayList;

import datosImpl.provinciaDaoImpl;
import entidad.provincia;
import excepciones.InsertarProvinciaException;
import excepciones.ListarProvinciasException;
import excepciones.ObtenerProvinciaPorIdException;
import excepciones.ObtenerProvinciaPorNombreException;
import excepciones.ProvinciaExisteException;
import negocio.negocioProvincia;

public class negocioProvinciaImpl implements negocioProvincia{

	private provinciaDaoImpl pro;
	
	public negocioProvinciaImpl() {
        this.pro = new provinciaDaoImpl();
    }
	
	@Override
	public void insertarProvincia(provincia provincia) throws InsertarProvinciaException {
        try {
            pro.insertarProvincia(provincia);
        } catch (InsertarProvinciaException e) {
            throw new InsertarProvinciaException("Error al insertar la provincia en la capa de negocio", e);
        }
	}

	@Override
	public boolean existe(provincia prov) throws ProvinciaExisteException {
        try {
            return pro.existe(prov);
        } catch (ProvinciaExisteException e) {
            throw new ProvinciaExisteException("Error al verificar si la provincia existe en la capa de negocio", e);
        }
	}

	@Override
	public provincia obtenerPorNombre(String nombre) throws ObtenerProvinciaPorNombreException {
        try {
            return pro.obtenerPorNombre(nombre);
        } catch (ObtenerProvinciaPorNombreException e) {
            throw new ObtenerProvinciaPorNombreException("Error al obtener la provincia por nombre en la capa de negocio", e);
        }
	}

	@Override
	public provincia obtenerPorId(int id) throws ObtenerProvinciaPorIdException {
        try {
            return pro.obtenerPorId(id);
        } catch (ObtenerProvinciaPorIdException e) {
            throw new ObtenerProvinciaPorIdException("Error al obtener la provincia por ID en la capa de negocio", e);
        }
	}

	@Override
	public ArrayList<provincia> listarProvincias() throws ListarProvinciasException {
        try {
            return pro.listarProvincias();
        } catch (ListarProvinciasException e) {
            throw new ListarProvinciasException("Error al listar las provincias en la capa de negocio", e);
        }
	}

}
