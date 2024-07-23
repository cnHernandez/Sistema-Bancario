package datos;

import entidad.direccion;
import excepciones.DireccionInsertarException;
import excepciones.DireccionModificarException;

public interface direccionDao {
	public int insertar(direccion dir) throws DireccionInsertarException;
	public boolean modificar(direccion dir) throws DireccionModificarException;
}
