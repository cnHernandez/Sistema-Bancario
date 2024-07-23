package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import datos.clientesDao;
import datosImpl.Conexion;
import entidad.cliente;
import entidad.direccion;
import entidad.localidad;
import entidad.provincia;
import entidad.usuario;
import excepciones.OperacionFallidaException;


public class clienteDaoImpl implements clientesDao {
	
	
	 final static String agregar = "INSERT INTO `banco_db`.`clientes` " +
	            "(`id_usuario`, `dni`, `cuil`, `nombre`, `apellido`, `sexo`, `nacionalidad`, `fecha_nacimiento`, `id_direccion`, `correo_electronico`, `telefono`, `estado`) " +
	            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    final static String listarClientes = "SELECT \r\n" + 
	    		"    cl.id_cliente,\r\n" + 
	    		"    cl.id_usuario,\r\n" + 
	    		"    cl.dni,\r\n" + 
	    		"    cl.cuil,\r\n" + 
	    		"    cl.nombre AS cliente_nombre,\r\n" + 
	    		"    cl.apellido,\r\n" + 
	    		"    cl.sexo,\r\n" + 
	    		"    cl.nacionalidad,\r\n" + 
	    		"    cl.fecha_nacimiento,\r\n" + 
	    		"    di.id_direccion,\r\n" + 
	    		"    di.calle,\r\n" + 
	    		"    di.numero,\r\n" + 
	    		"    lo.id_localidad,\r\n" + 
	    		"    lo.nombre AS localidad_nombre,\r\n" + 
	    		"    pr.id_provincia,\r\n" + 
	    		"    pr.nombre AS provincia_nombre,\r\n" + 
	    		"    cl.correo_electronico,\r\n" + 
	    		"    cl.telefono,\r\n" + 
	    		"    cl.estado\r\n" + 
	    		"FROM \r\n" + 
	    		"    clientes cl\r\n" + 
	    		"INNER JOIN \r\n" + 
	    		"    direccion di ON cl.id_direccion = di.id_direccion\r\n" + 
	    		"INNER JOIN \r\n" + 
	    		"    localidad lo ON di.id_localidad = lo.id_localidad\r\n" + 
	    		"INNER JOIN \r\n" + 
	    		"    provincia pr ON lo.id_provincia = pr.id_provincia;";

	    final static String eliminarCliente = "UPDATE clientes SET estado = 0 WHERE id_cliente = ?";

	    final static String existe = "SELECT * FROM clientes WHERE id_cliente = ?";

	    final static String modificarCliente = "UPDATE clientes SET " +
	            "dni = ?, cuil = ?, nombre = ?, apellido = ?, sexo = ?, nacionalidad = ?, fecha_nacimiento = ?, id_direccion = ?, correo_electronico = ?, telefono = ? " +
	            "WHERE id_cliente = ?";

	    private static final String obtenerClientePorIdUsuario = "SELECT c.*, d.*, l.*, p.* " +
	            "FROM clientes c " +
	            "JOIN direccion d ON c.id_direccion = d.id_direccion " +
	            "JOIN localidad l ON d.id_localidad = l.id_localidad " +
	            "JOIN provincia p ON l.id_provincia = p.id_provincia " +
	            "WHERE c.id_usuario = ?";
	    
	    private static final String obtenerClientePorIdCliente = "SELECT c.*, d.*, l.*, p.* " +
	            "FROM clientes c " +
	            "JOIN direccion d ON c.id_direccion = d.id_direccion " +
	            "JOIN localidad l ON d.id_localidad = l.id_localidad " +
	            "JOIN provincia p ON l.id_provincia = p.id_provincia " +
	            "WHERE c.id_cliente = ?";

	    
	    public cliente obtenerClientePorIdUsuario(usuario idUsuario) throws OperacionFallidaException {
	    	cliente cliente = null;
	        Conexion conexion = Conexion.getConexion();
	        Connection conn = conexion.Open();

	        try {
	            PreparedStatement stmt = conn.prepareStatement(obtenerClientePorIdUsuario);
	            stmt.setInt(1, idUsuario.getId_usuario());
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                cliente = new cliente();
	                cliente.setIdCliente(rs.getInt("id_cliente"));
	                cliente.setIdUsuario(rs.getInt("id_usuario"));
	                cliente.setDni(rs.getString("dni"));
	                cliente.setCuil(rs.getString("cuil"));
	                cliente.setNombre(rs.getString("nombre"));
	                cliente.setApellido(rs.getString("apellido"));
	                cliente.setSexo(rs.getString("sexo"));
	                cliente.setNacionalidad(rs.getString("nacionalidad"));
	                cliente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));

	                // Crear y establecer la dirección
	                direccion direccion = new direccion();
	                direccion.setIdDireccion(rs.getInt("id_direccion"));
	                direccion.setCalle(rs.getString("calle"));
	                direccion.setNumero(rs.getString("numero"));

	                // Crear y establecer la localidad
	                localidad localidad = new localidad();
	                localidad.setIdLocalidad(rs.getInt("id_localidad"));
	                localidad.setNombre(rs.getString("nombre"));

	                // Crear y establecer la provincia
	                provincia provincia = new provincia();
	                provincia.setIdProvincia(rs.getInt("id_provincia"));
	                provincia.setNombre(rs.getString("nombre"));

	                localidad.setProvincia(provincia);
	                direccion.setLocalidad(localidad);
	                cliente.setDireccion(direccion);

	                cliente.setCorreoElectronico(rs.getString("correo_electronico"));
	                cliente.setTelefono(rs.getString("telefono"));
	                cliente.setEstado(rs.getBoolean("estado"));
	            } 

	            rs.close();
	            stmt.close();
	            conexion.close();
	        } catch (SQLException e) {
	        	throw new OperacionFallidaException("Error al obtener el cliente: " + e.getMessage());
	        }

	        return cliente;
	    }
	    
	    public cliente obtenerClientePorIdCliente(int idCliente) throws OperacionFallidaException {
	    	cliente cliente = null;
	        Conexion conexion = Conexion.getConexion();
	        Connection conn = conexion.Open();

	        try {
	            PreparedStatement stmt = conn.prepareStatement(obtenerClientePorIdCliente);
	            stmt.setInt(1, idCliente);
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                cliente = new cliente();
	                cliente.setIdCliente(rs.getInt("id_cliente"));
	                cliente.setIdUsuario(rs.getInt("id_usuario"));
	                cliente.setDni(rs.getString("dni"));
	                cliente.setCuil(rs.getString("cuil"));
	                cliente.setNombre(rs.getString("nombre"));
	                cliente.setApellido(rs.getString("apellido"));
	                cliente.setSexo(rs.getString("sexo"));
	                cliente.setNacionalidad(rs.getString("nacionalidad"));
	                cliente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));

	                // Crear y establecer la dirección
	                direccion direccion = new direccion();
	                direccion.setIdDireccion(rs.getInt("id_direccion"));
	                direccion.setCalle(rs.getString("calle"));
	                direccion.setNumero(rs.getString("numero"));

	                // Crear y establecer la localidad
	                localidad localidad = new localidad();
	                localidad.setIdLocalidad(rs.getInt("id_localidad"));
	                localidad.setNombre(rs.getString("nombre"));

	                // Crear y establecer la provincia
	                provincia provincia = new provincia();
	                provincia.setIdProvincia(rs.getInt("id_provincia"));
	                provincia.setNombre(rs.getString("nombre"));

	                localidad.setProvincia(provincia);
	                direccion.setLocalidad(localidad);
	                cliente.setDireccion(direccion);

	                cliente.setCorreoElectronico(rs.getString("correo_electronico"));
	                cliente.setTelefono(rs.getString("telefono"));
	                cliente.setEstado(rs.getBoolean("estado"));
	            } 

	            rs.close();
	            stmt.close();
	            conexion.close();
	        } catch (SQLException e) {
	        	throw new OperacionFallidaException("Error al obtener el cliente: " + e.getMessage());
	        }

	        return cliente;
	    }

	    @Override
	    public ArrayList<cliente> listarClientes() {
	    	ArrayList<cliente> listaCliente = new ArrayList<>();
	        Connection conexion = null;
	        PreparedStatement st = null;
	        ResultSet rs = null;

	        try {
	            conexion = Conexion.getConexion().Open();
	            String query = "SELECT cl.id_cliente, cl.id_usuario, cl.dni, cl.cuil, " +
	                           "cl.nombre AS cliente_nombre, cl.apellido, cl.sexo, " +
	                           "cl.nacionalidad, cl.fecha_nacimiento, " +
	                           "di.id_direccion, di.calle, di.numero, " +
	                           "lo.id_localidad, lo.nombre AS localidad_nombre, " +
	                           "pr.id_provincia, pr.nombre AS provincia_nombre, " +
	                           "cl.correo_electronico, cl.telefono, cl.estado " +
	                           "FROM clientes cl " +
	                           "INNER JOIN direccion di ON cl.id_direccion = di.id_direccion " +
	                           "INNER JOIN localidad lo ON di.id_localidad = lo.id_localidad " +
	                           "INNER JOIN provincia pr ON lo.id_provincia = pr.id_provincia";

	            st = conexion.prepareStatement(query);
	            rs = st.executeQuery();

	            while (rs.next()) {
	                cliente obj = new cliente();
	                obj.setIdCliente(rs.getInt("id_cliente"));
	                obj.setIdUsuario(rs.getInt("id_usuario"));
	                obj.setDni(rs.getString("dni"));
	                obj.setCuil(rs.getString("cuil"));
	                obj.setNombre(rs.getString("cliente_nombre"));
	                obj.setApellido(rs.getString("apellido"));
	                obj.setSexo(rs.getString("sexo"));
	                obj.setNacionalidad(rs.getString("nacionalidad"));
	                obj.setFechaNacimiento(rs.getDate("fecha_nacimiento"));

	                // Crear y establecer la dirección
	                direccion direccion = new direccion();
	                direccion.setIdDireccion(rs.getInt("id_direccion"));
	                direccion.setCalle(rs.getString("calle"));
	                direccion.setNumero(rs.getString("numero"));

	                // Crear y establecer la localidad
	                localidad localidad = new localidad();
	                localidad.setIdLocalidad(rs.getInt("id_localidad"));
	                localidad.setNombre(rs.getString("localidad_nombre"));

	                // Crear y establecer la provincia
	                provincia provincia = new provincia();
	                provincia.setIdProvincia(rs.getInt("id_provincia"));
	                provincia.setNombre(rs.getString("provincia_nombre"));

	                // Establecer la localidad y provincia en la dirección
	                localidad.setProvincia(provincia);
	                direccion.setLocalidad(localidad);
	                obj.setDireccion(direccion);

	                obj.setCorreoElectronico(rs.getString("correo_electronico"));
	                obj.setTelefono(rs.getString("telefono"));
	                obj.setEstado(rs.getBoolean("estado"));
	                
	                listaCliente.add(obj);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (st != null) st.close();
	                if (conexion != null) conexion.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        return listaCliente;
	    }

	    @Override
	    public boolean agregarCliente(cliente cliente) throws OperacionFallidaException {
	    	 PreparedStatement statement = null;
	         Connection conexion = Conexion.getConexion().Open();
	         boolean isInsertExitoso = false;

	         try {
	             // Primero, insertar la dirección y obtener el ID generado
	             final String insertarDireccion = "INSERT INTO direccion (calle, numero, id_localidad) VALUES (?, ?, ?)";
	             statement = conexion.prepareStatement(insertarDireccion, Statement.RETURN_GENERATED_KEYS);
	             statement.setString(1, cliente.getDireccion().getCalle());
	             statement.setString(2, cliente.getDireccion().getNumero());
	             statement.setInt(3, cliente.getDireccion().getLocalidad().getIdLocalidad());
	             statement.executeUpdate();

	             ResultSet rs = statement.getGeneratedKeys();
	             int direccionId = 0;
	             if (rs.next()) {
	                 direccionId = rs.getInt(1);
	             }
	             rs.close();
	             statement.close();

	             // Luego, insertar el cliente con el ID de dirección
	             statement = conexion.prepareStatement(agregar);
	             statement.setInt(1, cliente.getIdUsuario());
	             statement.setString(2, cliente.getDni());
	             statement.setString(3, cliente.getCuil());
	             statement.setString(4, cliente.getNombre());
	             statement.setString(5, cliente.getApellido());
	             statement.setString(6, cliente.getSexo());
	             statement.setString(7, cliente.getNacionalidad());
	             statement.setDate(8, new java.sql.Date(cliente.getFechaNacimiento().getTime()));
	             statement.setInt(9, direccionId);
	             statement.setString(10, cliente.getCorreoElectronico());
	             statement.setString(11, cliente.getTelefono());
	             statement.setBoolean(12, cliente.isEstado());

	             if (statement.executeUpdate() > 0) {
	                 conexion.commit();
	                 isInsertExitoso = true;
	             }

	         } catch (Exception e) {
	        	 throw new OperacionFallidaException("Error al listar los clientes: " + e.getMessage());
	         } finally {
	             try {
	                 if (statement != null) statement.close();
	                 if (conexion != null) conexion.close();
	             } catch (Exception e) {
	            	 throw new OperacionFallidaException("Error al cerrar recursos: " + e.getMessage());
	             }
	         }

	         return isInsertExitoso;
	    }

	    @Override
	    public boolean eliminarCliente(int idCliente) throws OperacionFallidaException {
	    	PreparedStatement statement = null;
	        Connection conexion = Conexion.getConexion().Open();
	        boolean isUpdateExitoso = false;

	        try {
	            statement = conexion.prepareStatement(eliminarCliente);
	            statement.setInt(1, idCliente);

	            if (statement.executeUpdate() > 0) {
	                conexion.commit();
	                isUpdateExitoso = true;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            if (conexion != null) {
	                try {
	                    conexion.rollback();
	                } catch (SQLException e1) {
	                	throw new OperacionFallidaException("Error al eliminar el cliente: " + e.getMessage());
	                }
	            }
	        } finally {
	            if (statement != null) {
	                try {
	                    statement.close();
	                } catch (SQLException e) {
	                	throw new OperacionFallidaException("Error al cerrar recursos: " + e.getMessage());
	                }
	            }
	        }

	        return isUpdateExitoso;
	    }

	    @Override
	    public boolean modificarCliente(cliente cliente) throws OperacionFallidaException  {
	    	Connection conexion = null;
	        PreparedStatement statement = null;
	        boolean isUpdateExitoso = false;

	        try {
	            conexion = Conexion.getConexion().Open();
	            statement = conexion.prepareStatement(modificarCliente);

	            statement.setString(1, cliente.getDni());
	            statement.setString(2, cliente.getCuil());
	            statement.setString(3, cliente.getNombre());
	            statement.setString(4, cliente.getApellido());
	            statement.setString(5, cliente.getSexo());
	            statement.setString(6, cliente.getNacionalidad());
	            statement.setDate(7, new java.sql.Date(cliente.getFechaNacimiento().getTime()));
	            statement.setInt(8, cliente.getDireccion().getIdDireccion());
	            statement.setString(9, cliente.getCorreoElectronico());
	            statement.setString(10, cliente.getTelefono());
	            statement.setInt(11, cliente.getIdCliente());

	            if (statement.executeUpdate() > 0) {
	                conexion.commit();
	                isUpdateExitoso = true;
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            if (conexion != null) {
	                try {
	                    conexion.rollback();
	                } catch (SQLException e1) {
	                	throw new OperacionFallidaException("Error al modificar el cliente: " + e.getMessage());
	                }
	            }
	        } finally {
	            if (statement != null) {
	                try {
	                    statement.close();
	                } catch (SQLException e) {
	                	throw new OperacionFallidaException("Error al cerrar recursos: " + e.getMessage());
	                }
	            }
	        }

	        return isUpdateExitoso;
	    }

	    @Override
	    public boolean existe(int id) throws OperacionFallidaException {
	    	PreparedStatement statement = null;
	        Connection conexion = Conexion.getConexion().Open();
	        boolean existe = false;

	        try {
	            // Consulta SQL para verificar la existencia de un cliente
	            final String consultaExiste = "SELECT * FROM clientes WHERE id_cliente = ?";
	            statement = conexion.prepareStatement(consultaExiste);
	            statement.setInt(1, id);
	            ResultSet resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                existe = true;
	            }

	            resultSet.close();
	        } catch (SQLException e) {
	        	throw new OperacionFallidaException("Error al verificar la existencia del cliente: " + e.getMessage());
	        } finally {
	            if (statement != null) {
	                try {
	                    statement.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	            try {
	                if (conexion != null) {
	                    conexion.close();
	                }
	            } catch (SQLException e) {
	            	throw new OperacionFallidaException("Error al cerrar recursos: " + e.getMessage());
	            }
	        }

	        return existe;
	    }
	    
	    @Override
	    public boolean clienteActivo(int id_cuenta) throws OperacionFallidaException {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        boolean clienteActivo = false;
	        
	        try {
	            conn = Conexion.getConexion().Open(); // Obtener la conexión a la base de datos
	            String sql = "SELECT cl.estado " +
	                         "FROM cuentas c " +
	                         "JOIN clientes cl ON c.id_cliente = cl.id_cliente " +
	                         "WHERE c.id_cuenta = ?";
	            
	            stmt = conn.prepareStatement(sql);
	            stmt.setInt(1, id_cuenta);
	            rs = stmt.executeQuery();
	            
	            if (rs.next()) {
	                boolean estadoCliente = rs.getBoolean("estado");
	                clienteActivo = estadoCliente; // El cliente está activo si su estado es true
	            } else {
	                // En caso de que no se encuentre ninguna fila (lo cual no debería suceder con id_cuenta único)
	                clienteActivo = false; // Consideramos que el cliente no está activo si no se encuentra la cuenta
	            }
	        } catch (SQLException e) {
	            // Manejo de la excepción, podrías lanzar una excepción personalizada o loguear el error
	        	throw new OperacionFallidaException("Error al verificar el estado del cliente: " + e.getMessage());
	            // Aquí podrías lanzar una excepción personalizada si lo deseas
	        } finally {
	            // Cerrar recursos
	            try {
	                if (rs != null) rs.close();
	                if (stmt != null) stmt.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	            	 throw new OperacionFallidaException("Error al cerrar recursos: " + e.getMessage());
	            }
	        }
	        
	        return clienteActivo;
	    }

	}

