package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import datos.UsuarioDao;
import entidad.cliente;
import entidad.direccion;
import entidad.localidad;
import entidad.provincia;
import entidad.usuario;

public class UsuarioDaoImpl implements UsuarioDao{

	
	@Override
	public int altaUsuario(usuario nuevoUsuario) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		String query = "insert into usuario(nombre,contraseña,tipo_usuario) values (?,?,?);";
		int filas = 0; 
		PreparedStatement statement = null;
		try {
			
			Connection cn=Conexion.getConexion().Open();
			statement = cn.prepareStatement(query);
			 	
	           statement.setString(1, nuevoUsuario.getNombre());
	            statement.setString(2, nuevoUsuario.getContraseña());
	            statement.setInt(3, nuevoUsuario.getTipo_usuario());
	            if (statement.executeUpdate() > 0) {
	                cn.commit();
	                filas = 1;
	            }

			
			
		}catch(SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			filas=-1;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return filas ;
		
	}


	
	@Override
	public cliente buscarUusario(String dni) {
		 try {
		        Class.forName("com.mysql.jdbc.Driver");
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    }

		    cliente cliente = null;
		    String query = "SELECT c.*, d.*, l.*, p.* " +
		                   "FROM clientes c " +
		                   "JOIN direccion d ON c.id_direccion = d.id_direccion " +
		                   "JOIN localidad l ON d.id_localidad = l.id_localidad " +
		                   "JOIN provincia p ON l.id_provincia = p.id_provincia " +
		                   "WHERE c.dni = ?;";
		    ResultSet rs = null;
		    PreparedStatement statement = null;
		    try {
		        Connection cn = Conexion.getConexion().Open();
		        statement = cn.prepareStatement(query);
		        statement.setString(1, dni);
		        rs = statement.executeQuery();
		        if (rs.next()) {
		            cliente = new cliente();
		            
		            cliente.setIdCliente(rs.getInt("c.id_cliente"));
		            cliente.setIdUsuario(rs.getInt("c.id_usuario"));
		            cliente.setDni(rs.getString("c.dni"));
		            cliente.setCuil(rs.getString("c.cuil"));
		            cliente.setNombre(rs.getString("c.nombre"));
		            cliente.setApellido(rs.getString("c.apellido"));
		            cliente.setSexo(rs.getString("c.sexo"));
		            cliente.setNacionalidad(rs.getString("c.nacionalidad"));
		            cliente.setFechaNacimiento(rs.getDate("c.fecha_nacimiento"));

		            // Configuración de la dirección
		            direccion direccion = new direccion();
		            direccion.setIdDireccion(rs.getInt("d.id_direccion"));
		            direccion.setCalle(rs.getString("d.calle"));
		            direccion.setNumero(rs.getString("d.numero"));

		            // Configuración de la localidad
		            localidad localidad = new localidad();
		            localidad.setIdLocalidad(rs.getInt("l.id_localidad"));
		            localidad.setNombre(rs.getString("l.nombre"));

		            // Configuración de la provincia
		            provincia provincia = new provincia();
		            provincia.setIdProvincia(rs.getInt("p.id_provincia"));
		            provincia.setNombre(rs.getString("p.nombre"));

		            localidad.setProvincia(provincia);
		            direccion.setLocalidad(localidad);
		            cliente.setDireccion(direccion);

		            cliente.setCorreoElectronico(rs.getString("c.correo_electronico"));
		            cliente.setTelefono(rs.getString("c.telefono"));         
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        if (rs != null) {
		            try {
		                rs.close();
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		        if (statement != null) {
		            try {
		                statement.close();
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		        Conexion.getConexion().close();
		    }
		    
		    return cliente;
	}
	
	@Override
	public ArrayList<usuario> buscarTodosAdministradores() {
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        return null; // O manejar la excepción de alguna otra manera según tu caso
	    }

	    ArrayList<usuario> usuarios = new ArrayList<>();
	    String query = "SELECT * FROM usuario WHERE tipo_usuario = 1;";
	    ResultSet rs = null;
	    PreparedStatement statement = null;
	    try {
	        Connection cn = Conexion.getConexion().Open();
	        statement = cn.prepareStatement(query);
	        rs = statement.executeQuery();
	        while (rs.next()) {
	            usuario user = new usuario();
	            
	            user.setId_usuario(rs.getInt("id_usuario"));
	            user.setNombre(rs.getString("nombre"));
	            user.setContraseña(rs.getString("contraseña"));
	            user.setTipo_usuario(rs.getInt("tipo_usuario"));
	            user.setEstado(rs.getBoolean("estado"));

	            usuarios.add(user);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null; // O manejar la excepción de alguna otra manera según tu caso
	    } finally {
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        Conexion.getConexion().close();
	    }
	    
	    return usuarios;
	}

	


	@Override
	public boolean bajaUsuario(int id_usuario) {
	    Connection cn = null;
	    PreparedStatement statement = null;
	    boolean updateExistoso = false;

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    String query = "update usuario set estado = 0 where id_usuario = ?;";

	    try {
	        cn = Conexion.getConexion().Open();
	        cn.setAutoCommit(false);  // Habilitar el manejo de transacciones
	        statement = cn.prepareStatement(query);
	        statement.setInt(1, id_usuario);

	        if (statement.executeUpdate() > 0) {
	            cn.commit();
	            updateExistoso = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (cn != null) {
	            try {
	                cn.rollback();
	            } catch (SQLException e1) {
	                e1.printStackTrace();
	            }
	        }
	    } finally {
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (cn != null) {
	            try {
	                cn.setAutoCommit(true);  // Restaurar el valor por defecto de AutoCommit
	                cn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return updateExistoso;
	}

	@Override
	public boolean ActivarUsuario(int id_usuario) {
	    Connection cn = null;
	    PreparedStatement statement = null;
	    boolean updateExistoso = false;

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    String query = "update usuario set estado = 1 where id_usuario = ?;";

	    try {
	        cn = Conexion.getConexion().Open();
	        cn.setAutoCommit(false);  // Habilitar el manejo de transacciones
	        statement = cn.prepareStatement(query);
	        statement.setInt(1, id_usuario);

	        if (statement.executeUpdate() > 0) {
	            cn.commit();
	            updateExistoso = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (cn != null) {
	            try {
	                cn.rollback();
	            } catch (SQLException e1) {
	                e1.printStackTrace();
	            }
	        }
	    } finally {
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (cn != null) {
	            try {
	                cn.setAutoCommit(true);  // Restaurar el valor por defecto de AutoCommit
	                cn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return updateExistoso;
	}




	@Override
	public boolean modificarUsuario(String pass, int id) {
	    Connection cn = null;
	    PreparedStatement statement = null;
	    boolean exito = false;

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    String query = "update usuario set contraseña = ? where id_usuario = ?;";

	    try {
	        cn = Conexion.getConexion().Open();
	        cn.setAutoCommit(false); // Habilitar el manejo de transacciones
	        statement = cn.prepareStatement(query);
	        statement.setString(1, pass);
	        statement.setInt(2, id);

	        int filasAfectadas = statement.executeUpdate();
	        if (filasAfectadas > 0) {
	            cn.commit();
	            exito = true;
	        } else {
	            cn.rollback();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (cn != null) {
	            try {
	                cn.rollback();
	            } catch (SQLException e1) {
	                e1.printStackTrace();
	            }
	        }
	    } finally {
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (cn != null) {
	            try {
	                cn.setAutoCommit(true); // Restaurar el valor por defecto de AutoCommit
	                cn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    return exito;
	}



	@Override
	public usuario buscarUsuario(int id_usuario) {
	    String query = "select * from usuario where id_usuario= ?;";

	    try (Connection cn = Conexion.getConexion().Open();
	         PreparedStatement statement = cn.prepareStatement(query)) {

	        statement.setInt(1, id_usuario);
	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                usuario usuario = new usuario();
	                usuario.setId_usuario(rs.getInt("id_usuario"));
	                usuario.setNombre(rs.getString("nombre"));
	                usuario.setContraseña(rs.getString("contraseña"));
	                usuario.setTipo_usuario(rs.getInt("tipo_usuario"));
	                usuario.setEstado(rs.getBoolean("estado"));
	                
	                System.out.println("Usuario encontrado: " + usuario.getNombre());
	                return usuario;
	            } else {
	                System.out.println("No se encontró usuario con ID: " + id_usuario);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}








}
