package negocioImpl;

import datosImpl.direccionDaoImpl;
import entidad.direccion;
import excepciones.DireccionInsertarException;
import excepciones.DireccionModificarException;
import negocio.negocioDireccion;

public class negocioDireccionImpl implements negocioDireccion {

private direccionDaoImpl dir;
	
	public negocioDireccionImpl() {
        this.dir = new direccionDaoImpl();
    }
	@Override
	public int insertar(direccion dir) {
		 try {
	            return this.dir.insertar(dir);
	        } catch (DireccionInsertarException e) {
	          
	            System.err.println("Error al insertar la direcci�n: " + e.getMessage());
	            
	        }
	        return -1; 
	}
	@Override
	public boolean modificar(direccion dir) {
	    try {
            return this.dir.modificar(dir);
        } catch (DireccionModificarException e) {
            // Manejo de la excepci�n, por ejemplo, registrando el error o lanzando una nueva excepci�n
            System.err.println("Error al modificar la direcci�n: " + e.getMessage());
         
        }
        return false; // O cualquier valor que indiques para representar el fallo en la modificaci�n
    }

}
