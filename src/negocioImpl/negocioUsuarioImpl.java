package negocioImpl;

import java.util.ArrayList;

import datosImpl.UsuarioDaoImpl;
import entidad.cliente;
import entidad.usuario;
import negocio.negocioUsuario;

public class negocioUsuarioImpl implements negocioUsuario{

	private UsuarioDaoImpl usuario;
	
	public negocioUsuarioImpl(){
		this.usuario = new UsuarioDaoImpl();
	}
	
	@Override
	public int altaUsuario(usuario nuevoUsuario) {
		return usuario.altaUsuario(nuevoUsuario);
	}

	@Override
	public boolean modificarUsuario(String pass, int id) {
		return usuario.modificarUsuario(pass, id);
	}

	@Override
	public cliente buscarUusario(String dni) {
		return usuario.buscarUusario(dni);
	}

	@Override
	public boolean bajaUsuario(int id_isuario) {
		return usuario.bajaUsuario(id_isuario);
	}

	@Override
	public boolean ActivarUsuario(int id_isuario) {
		return usuario.ActivarUsuario(id_isuario);
	}

	@Override
	public usuario buscarUsuario(int id_usuario) {
		return usuario.buscarUsuario(id_usuario);
	}

	@Override
	public ArrayList<usuario> buscarTodosAdministradores() {
		return usuario.buscarTodosAdministradores();
	}


}
