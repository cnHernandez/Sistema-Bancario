package datos;

import java.util.ArrayList;


import entidad.cliente;
import excepciones.OperacionFallidaException;

public interface clientesDao {
	public boolean agregarCliente( cliente cliente) throws OperacionFallidaException; 
	ArrayList<cliente> listarClientes();
	public boolean modificarCliente(cliente cliente) throws OperacionFallidaException ;
	public boolean eliminarCliente(int idCliente) throws OperacionFallidaException; 
	public boolean existe(int id_usuario) throws OperacionFallidaException;
	public boolean clienteActivo(int id_cuenta) throws OperacionFallidaException;
	public cliente obtenerClientePorIdCliente(int idCliente) throws OperacionFallidaException;
}
