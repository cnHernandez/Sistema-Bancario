package presentacion.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.cliente;
import entidad.cuenta;
import entidad.movimiento;
import entidad.notificacion;
import entidad.prestamo;
import entidad.solicitudPrestamo;
import entidad.tipo_de_movimiento;
import excepciones.OperacionFallidaException;
import negocio.negocioCuenta;
import negocio.negocioMovimiento;
import negocio.negocioSolicitudPrestamo;
import negocio.negocioPrestamo;
import negocioImpl.negocioClienteImpl;
import negocioImpl.negocioCuentaImpl;
import negocioImpl.negocioMovimientoImpl;
import negocioImpl.negocioNotificacionImpl;
import negocioImpl.negocioSolicitudPrestamoImpl;
import negocioImpl.negocioPrestamoImpl;

@WebServlet("/ServletPrestamo")
public class ServletPrestamo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private negocioPrestamo negocioPrestamo;
	private negocioSolicitudPrestamo negocioSolicitudPrestamo;
	private negocioCuenta negocioCuenta;
	private negocioMovimiento negocioMovimiento;
	public ServletPrestamo() {
        super();
        
        negocioPrestamo = new negocioPrestamoImpl();
        negocioSolicitudPrestamo = new negocioSolicitudPrestamoImpl();
        negocioCuenta = new negocioCuentaImpl();
        negocioMovimiento = new negocioMovimientoImpl();
        
        new negocioClienteImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

	        if ("aceptar".equals(action)) {
	            int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud"));
	            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
	            int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
	            Date fecha = Date.valueOf(request.getParameter("fecha"));
	            double importePedido = Double.parseDouble(request.getParameter("importePedido"));
	            int plazoMeses = Integer.parseInt(request.getParameter("plazoMeses"));
	            double tasa = Double.parseDouble(request.getParameter("TasaInteres"));

	            boolean existePrestamo = negocioPrestamo.existePrestamoPorSolicitud(idSolicitud);
	            if (existePrestamo) {
	                request.setAttribute("mensaje", "Ya existe un préstamo asociado con esta solicitud.");
	            } else {
	                prestamo nuevoPrestamo = new prestamo();
	                cliente cliente = new cliente();
	                cuenta cuenta = new cuenta();
	                solicitudPrestamo solicitud = new solicitudPrestamo();

	                cliente.setIdCliente(idCliente);
	                cuenta.setIdCuenta(idCuenta);
	                solicitud.setIdSolicitud(idSolicitud);

	                nuevoPrestamo.setCliente(cliente);
	                nuevoPrestamo.setCuenta(cuenta);
	                nuevoPrestamo.setSolicitudPrestamo(solicitud);
	                nuevoPrestamo.setFecha(fecha);
	                // Calcular el importe total con interés
	                double importeTotal = importePedido + (importePedido * tasa / 100);
	                nuevoPrestamo.setImporteTotal(importeTotal);
	                nuevoPrestamo.setImportePedido(importePedido);
	                // Calcular el monto de la cuota
	                double montoCuota = importeTotal / plazoMeses;
	                nuevoPrestamo.setMontoCuota(montoCuota);
	                nuevoPrestamo.setPlazoMeses(plazoMeses);
	                
	                
	                boolean exito = negocioPrestamo.guardarPrestamo(nuevoPrestamo);
                  
	                request.setAttribute("bandera", exito);
	                if (exito) {
	                	negocioSolicitudPrestamo.actualizarEstadoSolicitud(idSolicitud, "aprobado");
	                    request.setAttribute("mensaje", "Solicitud aceptada y préstamo guardado exitosamente.");

	                    // Registrar el movimiento
	                    BigDecimal importePedidoBigDecimal = BigDecimal.valueOf(importePedido);
	                    movimiento mov = new movimiento();
	                    mov.setIdCuenta(cuenta.getIdCuenta());
	                    
	                 // Asignación del tipo de movimiento
	                    tipo_de_movimiento tipoMovimiento = new tipo_de_movimiento();
	                    tipoMovimiento.setId_tipo_movimiento(2); // Este debería ser el ID correcto del tipo de movimiento
	                    mov.setId_tipo_movimiento(tipoMovimiento.getId_tipo_movimiento()); // Asignación del tipo de movimiento al movimiento
	                    
	                    mov.setFecha(fecha); 
	                    mov.setDetalle("Alta de préstamo");
	        	        mov.setImporte(importePedidoBigDecimal);
	                   

	        	        negocioMovimiento.guardarMovimiento(mov);

	                    // Actualizar el saldo de la cuenta
	        	        negocioCuenta.actualizarSaldo(cuenta.getIdCuenta(), importePedido);
	        	        
	        	        //actualiza movimientos en session
	        	        HttpSession session = request.getSession();	
	        	        session = request.getSession();
	    	            negocioMovimientoImpl movNeg = new negocioMovimientoImpl();
	                    ArrayList<movimiento> movimientos = movNeg.obtenerMovimientos();
	                    session.setAttribute("movimientos", movimientos);
	                } else {
	                    request.setAttribute("mensaje", "Error al guardar el préstamo.");
	                }
	            }
	        } else if ("rechazar".equals(action)) {
	            int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud"));
	            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
	            double importePedido = Double.parseDouble(request.getParameter("importePedido"));
	            negocioClienteImpl clienteNeg = new negocioClienteImpl();
	            cliente cliente = new cliente();
	            
	            try {
	                cliente = clienteNeg.obtenerClientePorIdCliente(idCliente);
	            } catch (OperacionFallidaException e) {
	                // Manejar la excepción
	                request.setAttribute("error", "Error al obtener el cliente: " + e.getMessage());
	                request.getRequestDispatcher("/error.jsp").forward(request, response);
	                return;
	            }
	            
	            notificacion notificacion = new notificacion();
	            notificacion.setCliente(cliente);
	            notificacion.setFecha(new java.util.Date());
	            notificacion.setMensaje("Su prestamo de $" +importePedido+ " a sido rechazado ");
	            notificacion.setLeida(false);
	            
	            negocioNotificacionImpl noti = new negocioNotificacionImpl();
	            boolean insertarNoti = noti.insertarNotificacion(notificacion);
	            
	            if(!insertarNoti) {
	            	request.setAttribute("mensaje", "Error al guardar el préstamo.");
	            	return;
	            }
	            
	            negocioSolicitudPrestamo.actualizarEstadoSolicitud(idSolicitud, "rechazado");
	            request.setAttribute("mensaje", "Solicitud rechazada exitosamente.");
	        }

	        System.out.println("Mensaje antes de reenviar: " + request.getAttribute("mensaje"));

	        List<solicitudPrestamo> listaSolicitudes = negocioSolicitudPrestamo.listarSolicitudesPrestamo();
	        request.setAttribute("listaSolicitudes", listaSolicitudes);

	        request.getRequestDispatcher("SolicitudesPrestamo.jsp").forward(request, response);
	}
}
