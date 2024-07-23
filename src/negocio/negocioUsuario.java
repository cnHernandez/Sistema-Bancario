package negocio;

import java.util.ArrayList;

import entidad.cliente;
import entidad.usuario;

public interface negocioUsuario {

	public int altaUsuario(usuario nuevoUsuario);
	public boolean modificarUsuario(String pass, int id);
	public cliente buscarUusario(String dni); 
	public boolean bajaUsuario(int id_isuario);
	public boolean ActivarUsuario(int id_isuario);
	public usuario buscarUsuario(int id_usuario);
	public ArrayList<usuario> buscarTodosAdministradores();
}
