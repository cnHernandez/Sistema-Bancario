package presentacion.servlets;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.cuenta;
import entidad.solicitudPrestamo;

import negocio.negocioCuenta;
import negocio.negocioSolicitudPrestamo;
import negocioImpl.negocioCuentaImpl;
import negocioImpl.negocioSolicitudPrestamoImpl;


@WebServlet("/ServletSolicitudPrestamo")
public class ServletSolicitudPrestamo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private negocioCuenta negocioCuenta;

    public ServletSolicitudPrestamo() {
        super();
        negocioCuenta = new negocioCuentaImpl(); 
        // TODO Auto-generated constructor stub
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 // Obtener cuentas para el cliente actual
        HttpSession session = request.getSession();
        int idCliente = (int) session.getAttribute("idCliente");
        
        ArrayList<cuenta> cuentas = negocioCuenta.obtenerCuentasPorIdCliente(idCliente);

        // Configurar cuentas como atributo de solicitud
        request.setAttribute("cuentas", cuentas);

        // Redirigir a la página de solicitud de préstamo
        request.getRequestDispatcher("solicitarPrestamo.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        HttpSession session = request.getSession();
	        int idCliente = (int) session.getAttribute("idCliente");

	        System.out.println("idCuenta: " + request.getParameter("idCuenta"));
	        System.out.println("importePedido: " + request.getParameter("importePedido"));
	        System.out.println("plazoMeses: " + request.getParameter("plazoMeses"));
	        System.out.println("idcliente: " + idCliente); // Corregido: usar la variable idCliente

	        int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
	        double importePedido = Double.parseDouble(request.getParameter("importePedido"));
	        int plazoMeses = Integer.parseInt(request.getParameter("plazoMeses"));

	        solicitudPrestamo solicitud = new solicitudPrestamo();
	        solicitud.getCliente().setIdCliente(idCliente);
	        solicitud.getCuenta().setIdCuenta(idCuenta);
	        solicitud.setImportePedido(importePedido);
	        solicitud.setPlazoMeses(plazoMeses);
	        if(plazoMeses == 3) {
	        	solicitud.setTasaInteres(30.00);
	        }else if(plazoMeses == 6) {
	        	solicitud.setTasaInteres(50.00);
	        }else {
	        	solicitud.setTasaInteres(100.00);
	        }
	        solicitud.setEstadoSolicitud("pendiente");
	        
	        // Establecer la fecha actual utilizando java.sql.Date
	        solicitud.setFecha(new java.sql.Date(System.currentTimeMillis()));

	        negocioSolicitudPrestamo negocioSolicitud = new negocioSolicitudPrestamoImpl();
	        boolean exito = negocioSolicitud.guardarSolicitudPrestamo(solicitud);

	        if (exito) {
	        	
	        	request.setAttribute("bandera", exito);
	        	request.getRequestDispatcher("solicitarPrestamo.jsp").forward(request, response);
	        } else {
	            request.setAttribute("error", "No se pudo guardar la solicitud de préstamo.");
	            request.getRequestDispatcher("solicitarPrestamo.jsp").forward(request, response);
	        }
	    } catch (NumberFormatException e) {
	        System.out.println("Error de formato de número: " + e.getMessage());

	        request.setAttribute("error", "Formato de datos inválido.");
	        request.getRequestDispatcher("solicitarPrestamo.jsp").forward(request, response);
	    } catch (Exception e) {
	        System.out.println("Error general: " + e.getMessage());

	        request.setAttribute("error", "Error al procesar la solicitud: " + e.getMessage());
	        request.getRequestDispatcher("solicitarPrestamo.jsp").forward(request, response);
	    }
	}

}


