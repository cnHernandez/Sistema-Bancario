package negocio;

import java.util.ArrayList;

import entidad.cliente;
import entidad.usuario;
import excepciones.ClienteNoEncontradoException;
import excepciones.OperacionFallidaException;

public interface negocioCliente {

	    cliente obtenerClientePorIdUsuario(usuario idUsuario) throws ClienteNoEncontradoException, OperacionFallidaException;
	    ArrayList<cliente> listarClientes();
	    boolean agregarCliente(cliente cliente) throws OperacionFallidaException;
	    boolean modificarCliente(cliente clienteModificado) throws OperacionFallidaException;
	    boolean eliminarCliente(int idCliente) throws OperacionFallidaException;
	    boolean existe(int idCliente) throws OperacionFallidaException;
	    public boolean clienteActivo(int id_cuenta) throws OperacionFallidaException;
	    public cliente obtenerClientePorIdCliente(int idCliente) throws OperacionFallidaException;
}
