package datosImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import datosImpl.Conexion;
import entidad.cuenta;
import entidad.tipo_de_cuenta;
import datos.CuentaDao;


public class cuentaDaoImpl implements CuentaDao {
   // private static final String AgregarCuenta = "INSERT INTO cuentas (id_cliente, id_tipo_cuenta, fecha_creacion, numero_cuenta, cbu, estado) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String listarCuentas = "select * from banco_db.cuentas;";
    private static final String ModificarCuenta = "UPDATE cuentas SET id_cliente = ?, id_tipo_cuenta = ?, fecha_creacion = ?, numero_cuenta = ?, cbu = ?, saldo = ? WHERE id_cuenta = ?";
    private static final String listarIndividual= "SELECT * FROM cuentas WHERE id_cliente = ?";
    //private static final String listarTiposDeCuenta = "SELECT * FROM tipo_de_cuenta";
    
 // Método para obtener tipos de cuenta
    @Override
    public ArrayList<tipo_de_cuenta> obtenerTiposDeCuenta() {
        ArrayList<tipo_de_cuenta> tiposCuenta = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String listarTiposDeCuenta = "SELECT id_tipo_cuenta, tipo_cuenta FROM tipo_de_cuenta";
        
        try {
            conexion = Conexion.getConexion().Open();
            stmt = conexion.prepareStatement(listarTiposDeCuenta);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                tipo_de_cuenta tipoCuenta = new tipo_de_cuenta();
                tipoCuenta.setId_tipo_cuenta(rs.getInt("id_tipo_cuenta"));
                tipoCuenta.setTipo_cuenta(rs.getString("tipo_cuenta"));
                tiposCuenta.add(tipoCuenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return tiposCuenta;
    }
    
    @Override
    public boolean AgregarCuenta(cuenta cuenta) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        PreparedStatement stmtCuentaCliente = null;
        PreparedStatement statement = null;
        Connection conexion = Conexion.getConexion().Open();
        boolean isInsertExitoso = false;

        try {
            // Verificar si el cliente ya tiene 3 cuentas activas
            String verificarCuentasActivas = "SELECT COUNT(*) AS total FROM cuentas WHERE id_cliente = ? AND estado = 1";
            stmtCuentaCliente = conexion.prepareStatement(verificarCuentasActivas);
            stmtCuentaCliente.setInt(1, cuenta.getIdCliente());
            ResultSet rsCuentaCliente = stmtCuentaCliente.executeQuery();

            int cuentasActivas = 0;
            if (rsCuentaCliente.next()) {
                cuentasActivas = rsCuentaCliente.getInt("total");
            }

            if (cuentasActivas >= 3) {
                throw new IllegalStateException("El cliente ya tiene el máximo de 3 cuentas permitidas.");
            }

            // Proceder a agregar la nueva cuenta
            String insertarCuenta = "INSERT INTO cuentas (id_cliente, id_tipo_cuenta, fecha_creacion, numero_cuenta, cbu) VALUES (?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(insertarCuenta);
            statement.setInt(1, cuenta.getIdCliente());
            statement.setInt(2, cuenta.getId_tipo_cuenta());
            statement.setDate(3, new java.sql.Date(cuenta.getFechaCreacion().getTime()));
            statement.setString(4, cuenta.getNumeroCuenta());
            statement.setString(5, cuenta.getCbu());

            if (statement.executeUpdate() > 0) {
                conexion.commit();
                isInsertExitoso = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conexion != null) {
                    conexion.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (stmtCuentaCliente != null) stmtCuentaCliente.close();
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isInsertExitoso;
    }

    public boolean existeCbu(String cbu) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean existe = false;

        String query = "SELECT COUNT(*) AS total FROM cuentas WHERE cbu = ?";

        try {
            conexion = Conexion.getConexion().Open();
            stmt = conexion.prepareStatement(query);
            stmt.setString(1, cbu);
            rs = stmt.executeQuery();

            if (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return existe;
    }

    public boolean existeNumeroCuenta(String numeroCuenta) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean existe = false;

        String query = "SELECT COUNT(*) AS total FROM cuentas WHERE numero_cuenta = ?";

        try {
            conexion = Conexion.getConexion().Open();
            stmt = conexion.prepareStatement(query);
            stmt.setString(1, numeroCuenta);
            rs = stmt.executeQuery();

            if (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return existe;
    }

    private static final String obtenerCuentaPorIdCliente = "SELECT * FROM cuentas WHERE id_cliente = ? AND estado = 1";

    public ArrayList<cuenta> obtenerCuentasPorIdCliente(int idCliente) {
        ArrayList<cuenta> cuentas = new ArrayList<>();
        Conexion conexion = Conexion.getConexion();
        Connection conn = conexion.Open();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(obtenerCuentaPorIdCliente);
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                cuenta cuenta = new cuenta();
                cuenta.setIdCliente(rs.getInt("id_cliente"));
                cuenta.setIdCuenta(rs.getInt("id_cuenta"));
                cuenta.setId_tipo_cuenta(rs.getInt("id_tipo_cuenta"));
                cuenta.setFechaCreacion(rs.getDate("fecha_creacion"));
                cuenta.setNumeroCuenta(rs.getString("numero_cuenta"));
                cuenta.setCbu(rs.getString("cbu"));
                cuenta.setSaldo(rs.getFloat("saldo"));
                cuenta.setEstado(rs.getBoolean("estado"));
                cuentas.add(cuenta);
            }
            
            rs.close();
            stmt.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return cuentas;
    }
	
    @Override
	public boolean ModificarCuenta(cuenta cuenta) {
		 try {
	            Class.forName("com.mysql.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }

	        PreparedStatement statement;
	        Connection conexion = Conexion.getConexion().Open(); 
	        boolean isUpdateExitoso = false;

	        try {
	            statement = conexion.prepareStatement(ModificarCuenta);
	            statement.setInt(1, cuenta.getIdCliente());
	            statement.setInt(2, cuenta.getId_tipo_cuenta());
	            statement.setDate(3, new Date(cuenta.getFechaCreacion().getTime()));
	            statement.setString(4, cuenta.getNumeroCuenta());
	            statement.setString(5, cuenta.getCbu());
	            statement.setFloat(6, cuenta.getSaldo());
	            statement.setInt(7, cuenta.getIdCuenta()); 
	            

	            if (statement.executeUpdate() > 0) {
	                conexion.commit();
	                isUpdateExitoso = true;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            try {
	                conexion.rollback();
	            } catch (SQLException e1) {
	                e1.printStackTrace();
	            }
	        } finally {
	            Conexion.getConexion().close(); 
	        }

	        return isUpdateExitoso;
	    }

	@Override
	public boolean EliminarCuenta(int idCuenta) {
		PreparedStatement statement = null;
	    Connection conexion = Conexion.getConexion().Open();
	    boolean isUpdateExitoso = false;

	    try {
	        
	        final String eliminarCuenta = "UPDATE cuentas SET estado = 0 WHERE id_cuenta = ?";
	        
	        statement = conexion.prepareStatement(eliminarCuenta);
	        statement.setInt(1, idCuenta);

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
	    }

	    return isUpdateExitoso;
	}

	@Override
	public ArrayList<cuenta> listarCuentas() {
	    ArrayList<cuenta> listaCuentas = new ArrayList<cuenta>();
	    Connection conexion = null;
	    Statement st = null;
	    ResultSet rs = null;
	    
	    try {
	        conexion = Conexion.getConexion().Open();
	        st = conexion.createStatement();
	        rs = st.executeQuery(listarCuentas);
	        while (rs.next()) {
	            cuenta cuenta = new cuenta();
	            cuenta.setIdCuenta(rs.getInt("id_cuenta"));
	            cuenta.setIdCliente(rs.getInt("id_cliente"));
	            cuenta.setId_tipo_cuenta(rs.getInt("id_tipo_cuenta"));
	            cuenta.setFechaCreacion(rs.getDate("fecha_creacion"));
	            cuenta.setNumeroCuenta(rs.getString("numero_cuenta"));
	            cuenta.setCbu(rs.getString("cbu"));
	            cuenta.setSaldo(rs.getFloat("saldo"));
	            listaCuentas.add(cuenta);
	        }
	    } catch (SQLException e) {
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
	    
	    return listaCuentas;
	}

	@Override
	public cuenta listarIndividual(int idCliente) {
	    cuenta cuenta = null;
	    Connection conexion = null;
	    PreparedStatement psCuenta = null;
	    ResultSet rsCuenta = null;
	    String sqlCuenta = listarIndividual;

	    try {
	        conexion = Conexion.getConexion().Open();
	        psCuenta = conexion.prepareStatement(sqlCuenta);
	        psCuenta.setInt(1, idCliente);
	        rsCuenta = psCuenta.executeQuery();

	        if (rsCuenta.next()) {
	            cuenta = new cuenta();
	            cuenta.setIdCuenta(rsCuenta.getInt("id_cuenta"));
	            cuenta.setIdCliente(rsCuenta.getInt("id_cliente"));
	            cuenta.setId_tipo_cuenta(rsCuenta.getInt("id_tipo_cuenta"));
	            cuenta.setFechaCreacion(rsCuenta.getDate("fecha_creacion"));
	            cuenta.setNumeroCuenta(rsCuenta.getString("numero_cuenta"));
	            cuenta.setCbu(rsCuenta.getString("cbu"));
	            cuenta.setSaldo(rsCuenta.getFloat("saldo"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rsCuenta != null) rsCuenta.close();
	            if (psCuenta != null) psCuenta.close();
	            if (conexion != null) conexion.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return cuenta;
	}
	
	@Override
	public ArrayList<cuenta> obtenerCuentasPorCliente(int idCliente) {
	    ArrayList<cuenta> cuentas = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = Conexion.getConexion().Open(); // Obtener la conexión a la base de datos
	        String sql = "SELECT * FROM cuentas WHERE id_cliente = ? AND estado = 1";

	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, idCliente);
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            cuenta cuenta = new cuenta();
	            cuenta.setIdCuenta(rs.getInt("id_cuenta"));
	            cuenta.setIdCliente(rs.getInt("id_cliente"));
	            cuenta.setId_tipo_cuenta(rs.getInt("id_tipo_cuenta"));
	            cuenta.setFechaCreacion(rs.getDate("fecha_creacion"));
	            cuenta.setNumeroCuenta(rs.getString("numero_cuenta"));
	            cuenta.setCbu(rs.getString("cbu"));
	            cuenta.setSaldo(rs.getFloat("saldo"));
	            cuenta.setEstado(rs.getBoolean("estado"));

	            cuentas.add(cuenta);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Manejo de excepciones
	    } finally {
	        // Cerrar conexiones y liberar recursos
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return cuentas;
	}
	
	@Override
	public String obtenerNombreYApellidoPorCbu(String cbu) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String mensaje = "No encontrado. Verifique el CBU o cliente inexistente.";

	    try {
	        conn = Conexion.getConexion().Open(); // Obtener la conexión a la base de datos
	        String sql = "SELECT c.nombre, c.apellido FROM cuentas ct JOIN clientes c ON ct.id_cliente = c.id_cliente WHERE ct.cbu = ?";

	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, cbu);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            String nombre = rs.getString("nombre");
	            String apellido = rs.getString("apellido");
	            mensaje = "Encontrado: " + nombre + " " + apellido;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        mensaje = "Error al acceder a la base de datos.";
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return mensaje;
	}
	
	@Override
	public boolean TranferirEntreCuentas(String cbu, float monto) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int obtenerIdCuenta(String cbu) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    int idCuenta = -1; // Valor predeterminado si no se encuentra la cuenta

	    try {
	        conn = Conexion.getConexion().Open(); // Obtener la conexión a la base de datos
	        String sql = "SELECT id_cuenta FROM cuentas WHERE cbu = ?";

	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, cbu);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            idCuenta = rs.getInt("id_cuenta");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return idCuenta;
	}
	
	@Override
	public boolean updatearSaldoTranferenciaOrigen(int idCuenta, double monto) {
	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = Conexion.getConexion().Open();
	        conn.setAutoCommit(false);

	        String sql = "UPDATE cuentas SET saldo = saldo - ? WHERE id_cuenta = ?";

	        ps = conn.prepareStatement(sql);
	        ps.setDouble(1, monto);
	        ps.setInt(2, idCuenta);

	        int rowsAffected = ps.executeUpdate();
	        if (rowsAffected > 0) {
	            conn.commit();
	            return true;
	        } else {
	            conn.rollback();
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            if (conn != null) {
	                conn.rollback();
	            }
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	    } finally {
	        try {
	            if (ps != null) {
	                ps.close();
	            }
	            if (conn != null) {
	                conn.setAutoCommit(true);
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}

	@Override
	public boolean updatearSaldoTranferenciaDestino(int idCuenta, double monto) {
	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = Conexion.getConexion().Open();
	        conn.setAutoCommit(false);

	        String sql = "UPDATE cuentas SET saldo = saldo + ? WHERE id_cuenta = ?";

	        ps = conn.prepareStatement(sql);
	        ps.setDouble(1, monto);
	        ps.setInt(2, idCuenta);

	        int rowsAffected = ps.executeUpdate();
	        if (rowsAffected > 0) {
	            conn.commit();
	            return true;
	        } else {
	            conn.rollback();
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            if (conn != null) {
	                conn.rollback();
	            }
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	    } finally {
	        try {
	            if (ps != null) {
	                ps.close();
	            }
	            if (conn != null) {
	                conn.setAutoCommit(true);
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}
	
	@Override
	public String obtenerCbu(int id_cuenta) {
		
		 Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
		    String cbu = "no encontrado";
		    
		    try {
		        conn = Conexion.getConexion().Open(); // Obtener la conexión a la base de datos
		        String sql = "SELECT cbu FROM cuentas WHERE id_cuenta = ?";
		        
		        stmt = conn.prepareStatement(sql);
		        stmt.setInt(1, id_cuenta);
		        rs = stmt.executeQuery();
		        
		        if (rs.next()) {
		            cbu = rs.getString("cbu");
		        } else {
		            // En caso de que no se encuentre ninguna fila (lo cual no debería suceder con id_cuenta único)
		            cbu = "no encontrado";
		        }
		    } catch (SQLException e) {
		        // Manejo de la excepción, podrías lanzar una excepción personalizada o loguear el error
		        e.printStackTrace();
		        // Aquí podrías lanzar una excepción personalizada si lo deseas
		    } finally {
		        // Cerrar recursos
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		            if (conn != null) conn.close();
		        } catch (SQLException e) {
		            e.printStackTrace(); // Manejo del error de cierre de recursos
		        }
		    }
		    
		    return cbu;
		
		
		
		
	}
	
	@Override
	public boolean actualizarSaldo(int idCuenta, double monto) {
		System.out.println("Debug: Entrando en actualizarSaldo...");
	    System.out.println("Debug: ID de cuenta: " + idCuenta);
	    System.out.println("Debug: Monto a actualizar: " + monto);

	    Connection conn = null;
	    PreparedStatement ps = null;
	    
	    try {
	        conn = Conexion.getConexion().Open();
	        // Deshabilitar autocommit para gestionar la transacción manualmente
	        conn.setAutoCommit(false);
	        
	        String sql = "UPDATE cuentas SET saldo = saldo + ? WHERE id_cuenta = ?";
	        
	        ps = conn.prepareStatement(sql);
	        ps.setDouble(1, monto);
	        ps.setInt(2, idCuenta);

	        int rowsAffected = ps.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Debug: Se actualizó el saldo correctamente.");
	            // Confirmar la transacción
	            conn.commit();
	            return true;
	        } else {
	            System.out.println("Debug: No se pudo actualizar el saldo.");
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            // En caso de error, hacer rollback para revertir los cambios
	            if (conn != null) {
	                conn.rollback();
	            }
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	    } finally {
	        try {
	            if (ps != null) {
	                ps.close();
	            }
	            if (conn != null) {
	                conn.setAutoCommit(true); // Restaurar autocommit por defecto
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return false;
	}
	
	@Override
	public double obtenerSaldo(int idCuenta) {
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    double saldo = 0.0;

	    try {
	        conn = Conexion.getConexion().Open();
	        String sql = "SELECT saldo FROM cuentas WHERE id_cuenta = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, idCuenta);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            saldo = rs.getDouble("saldo");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return saldo;
	}
	
	@Override
	public boolean cuentaDestinoActiva(int id_cuenta_destino) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    boolean cuentaActiva = false;
	    
	    try {
	        conn = Conexion.getConexion().Open(); // Obtener la conexión a la base de datos
	        String sql = "SELECT estado FROM cuentas WHERE id_cuenta = ?";
	        
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, id_cuenta_destino);
	        rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            int estado = rs.getInt("estado");
	            cuentaActiva = (estado == 1); // Estado 1 indica cuenta activa
	        } else {
	            // En caso de que no se encuentre ninguna fila (lo cual no debería suceder con id_cuenta único)
	            cuentaActiva = false; // Cuenta no encontrada o inactiva
	        }
	    } catch (SQLException e) {
	        // Manejo de la excepción, podrías lanzar una excepción personalizada o loguear el error
	        e.printStackTrace();
	        // Aquí podrías lanzar una excepción personalizada si lo deseas
	    } finally {
	        // Cerrar recursos
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace(); // Manejo del error de cierre de recursos
	        }
	    }
	    
	    return cuentaActiva;
	}

}
