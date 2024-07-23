package presentacion.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import datosImpl.Conexion;
import datosImpl.clienteDaoImpl;
import entidad.cliente;
import entidad.cuenta;
import entidad.movimiento;
import entidad.notificacion;
import entidad.usuario;
import entidad.solicitudPrestamo;
import negocioImpl.negocioCuentaImpl;
import negocioImpl.negocioMovimientoImpl;
import negocioImpl.negocioNotificacionImpl;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false); 

            if (session != null) {
                session.invalidate(); 
            }
            response.sendRedirect("login.jsp");
            
        } else {
            response.sendRedirect("Principal.jsp");
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombre = request.getParameter("nombre");
        String contraseña = request.getParameter("contraseña");
        usuario user = new usuario();
        String errorMsg = null;
        boolean operacionExitosa = false;

        try {
            Conexion conexion = Conexion.getConexion();
            Connection conn = conexion.Open();

            String sql = "SELECT * FROM usuario WHERE nombre = ? AND contraseña = ? ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, contraseña);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                user.setId_usuario(idUsuario);
                int tipoUsuario = rs.getInt("tipo_Usuario");
                boolean estado = rs.getBoolean("estado");
                operacionExitosa = true;
                  
                if (estado) {
                    HttpSession session = request.getSession();
                    session.setAttribute("nombre", nombre);
                    session.setAttribute("tipoUsuario", tipoUsuario);
                    session.setAttribute("idUsuario", idUsuario);

                    if (tipoUsuario == 1) {
                    	negocioMovimientoImpl mov = new negocioMovimientoImpl();
                        ArrayList<movimiento> movimientos = mov.obtenerMovimientos();
                        session.setAttribute("movimientos", movimientos);
                        response.sendRedirect("Principal.jsp");
                    } else if (tipoUsuario == 2) {
                    	clienteDaoImpl clienteDao = new clienteDaoImpl();
                        cliente cliente = clienteDao.obtenerClientePorIdUsuario(user);

                        if (cliente != null) {
                            session.setAttribute("cliente", cliente);
                            session.setAttribute("idCliente", cliente.getIdCliente());
                            
                            //
                            int idCliente = (int) request.getSession().getAttribute("idCliente");
                            negocioNotificacionImpl neg = new negocioNotificacionImpl();
                            ArrayList<notificacion> notificaciones = neg.obtenerNotificacionesPorCliente(idCliente);                          
                            session.setAttribute("notificaciones", notificaciones);
                            
                            // Obtener cuentas del cliente y agregarlas a la sesión
                            negocioCuentaImpl negocioCuenta = new negocioCuentaImpl();
                            ArrayList<cuenta> cuentas = negocioCuenta.obtenerCuentasPorIdCliente(cliente.getIdCliente());

                            if (cuentas != null && !cuentas.isEmpty()) {
                                session.setAttribute("cuentas", cuentas);
                            } else {
                                session.setAttribute("cuentas", new ArrayList<cuenta>()); // Crear una lista vacía si no hay cuentas
                            }
                        }
                        response.sendRedirect("PrincipalCliente.jsp");
                    }
                    return;
                } else {
                	errorMsg = "Usuario inactivo";
                	operacionExitosa = false;
                	request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    rd.forward(request, response);
                }
            } else {
            		errorMsg = "Usuario o contraseña incorrectos.";
            		request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    rd.forward(request, response);
            }

            rs.close();
            stmt.close();
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error en la conexión a la base de datos.");
        }
    }
	
	
	
	}


