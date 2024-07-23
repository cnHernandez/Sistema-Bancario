package negocioImpl;

import java.util.ArrayList;

import datosImpl.localidadDaoImpl;
import entidad.localidad;
import excepciones.InsertarLocalidadException;
import excepciones.ListarLocalidadesException;
import excepciones.LocalidadExistenteException;
import excepciones.ObtenerLocalidadException;
import negocio.negocioLocalidades;

public class negocioLocalidadesImpl implements negocioLocalidades{

private localidadDaoImpl loc;
	
	public negocioLocalidadesImpl() {
        this.loc = new localidadDaoImpl();
    }
	
	@Override
	public void insertarLocalidad(localidad localidad) throws InsertarLocalidadException {
		loc.insertarLocalidad(localidad);
	}

	@Override
	public boolean existe(localidad localidad) throws LocalidadExistenteException {
		return loc.existe(localidad);
	}

	@Override
	public localidad obtenerPorNombre(String nombre) throws ObtenerLocalidadException {
		return loc.obtenerPorNombre(nombre);
	}

	@Override
	public ArrayList<localidad> listarLocalidades() throws ListarLocalidadesException {
		return loc.listarLocalidades();
	}

	@Override
	public localidad obtenerPorId(int id) throws ObtenerLocalidadException {
		return loc.obtenerPorId(id);
	}

	@Override
	public ArrayList<localidad> listarLocalidadesProv(int prov) throws ListarLocalidadesException {
		return loc.listarLocalidadesProv(prov);
	}

}
