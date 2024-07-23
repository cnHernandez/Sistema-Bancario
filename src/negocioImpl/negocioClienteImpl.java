package negocioImpl;

import java.util.ArrayList;

import datosImpl.clienteDaoImpl;
import entidad.cliente;
import entidad.usuario;
import excepciones.ClienteNoEncontradoException;
import excepciones.OperacionFallidaException;
import negocio.negocioCliente;


public class negocioClienteImpl implements negocioCliente {
	
	 private clienteDaoImpl clienteDao;

	 public negocioClienteImpl() {
	        this.clienteDao = new clienteDaoImpl();
	    }

	    @Override
	    public cliente obtenerClientePorIdUsuario(usuario idUsuario) throws ClienteNoEncontradoException, OperacionFallidaException {
	        return clienteDao.obtenerClientePorIdUsuario(idUsuario);
	    }

	    @Override
	    public ArrayList<cliente> listarClientes() {
	        return clienteDao.listarClientes();
	    }

	    @Override
	    public boolean agregarCliente(cliente cliente) throws OperacionFallidaException {
	        return clienteDao.agregarCliente(cliente);
	    }

	    @Override
	    public boolean modificarCliente(cliente clienteModificado) throws OperacionFallidaException {
	        return clienteDao.modificarCliente(clienteModificado);
	    }

	    @Override
	    public boolean eliminarCliente(int idCliente) throws OperacionFallidaException {
	        return clienteDao.eliminarCliente(idCliente);
	    }
	    
	    @Override
	    public boolean existe(int idCliente) throws OperacionFallidaException {
	    	return clienteDao.existe(idCliente);
	    }

		@Override
		public boolean clienteActivo(int id_cuenta) throws OperacionFallidaException {
			return clienteDao.clienteActivo(id_cuenta);
		}

		@Override
		public cliente obtenerClientePorIdCliente(int idCliente) throws OperacionFallidaException {
			return clienteDao.obtenerClientePorIdCliente(idCliente);
		}
		
		
	    
	}



