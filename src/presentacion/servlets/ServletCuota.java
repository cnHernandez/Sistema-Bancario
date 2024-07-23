package presentacion.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.cuenta;
import entidad.cuotas;
import entidad.movimiento;
import negocio.negocioCuenta;
import negocio.negocioMovimiento;
import negocioImpl.negocioCuentaImpl;
import negocioImpl.negocioCuotasImpl;
import negocioImpl.negocioMovimientoImpl;


/**
 * Servlet implementation class ServletCuota
 */
@WebServlet("/ServletCuota")
public class ServletCuota extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private negocioCuenta negocioCuenta;
	private negocioMovimiento negocioMovimiento;
    
    public ServletCuota() {
        super();
        negocioCuenta = new negocioCuentaImpl();
        negocioMovimiento = new negocioMovimientoImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("action");

	    if (action != null && action.equals("verCuotas")) {
	        verCuotasPorPrestamo(request, response);
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		  if (action != null && action.equals("verCuotas")) {
		        verCuotasPorPrestamo(request, response);
		    } else if (action != null && action.equals("pagar")) {
		        // Procesar el pago de la cuota
		        pagarCuota(request, response);
		    }
	}

	private void verCuotasPorPrestamo(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        String idPrestamoStr = request.getParameter("idPrestamo");
	        negocioCuotasImpl negocioCuotas = new negocioCuotasImpl();
	        if (idPrestamoStr == null || idPrestamoStr.isEmpty()) {
	            throw new IllegalArgumentException("El ID del préstamo es nulo o vacío.");
	        }

	        int idPrestamo = Integer.parseInt(idPrestamoStr);

	        List<cuotas> listaCuotas = negocioCuotas.listarCuotasPorPrestamo(idPrestamo);

	        if (listaCuotas == null) {
	            listaCuotas = new ArrayList<>(); // Evitar nulo asignando una lista vacía
	        }

	        request.setAttribute("listaCuotas", listaCuotas);
	        request.getRequestDispatcher("verCuotas.jsp").forward(request, response);
	    } catch (NullPointerException e) {
	        e.printStackTrace();
	        request.setAttribute("error", "Error al obtener las cuotas del préstamo: " + e.getMessage());
	        request.getRequestDispatcher("error.jsp").forward(request, response);
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        request.setAttribute("error", "ID de préstamo no válido.");
	        request.getRequestDispatcher("error.jsp").forward(request, response);
	    } catch (IllegalArgumentException e) {
	        e.printStackTrace();
	        request.setAttribute("error", e.getMessage());
	        request.getRequestDispatcher("error.jsp").forward(request, response);
	    }
	}

	
	private void pagarCuota(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
		int idCuota = Integer.parseInt(request.getParameter("idCuota"));
	    int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
	    double monto = Double.parseDouble(request.getParameter("monto"));
	    negocioCuotasImpl negocioCuotas = new negocioCuotasImpl();
	    double saldoActual = negocioCuenta.obtenerSaldo(idCuenta);

	    // Verificar si el saldo es suficiente
	    if (saldoActual < monto) {
	        // Saldo insuficiente, mostrar mensaje de error
	        request.setAttribute("mensajeError", "Saldo insuficiente para realizar el pago.");
	        int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
            List<cuotas> listaCuotas = negocioCuotas.listarCuotasPorPrestamo(idPrestamo);

            request.setAttribute("listaCuotas", listaCuotas);
            request.getRequestDispatcher("verCuotas.jsp").forward(request, response);
            return;
	    }

	    boolean pagoExitoso = negocioCuotas.pagarCuota(idCuota, idCuenta, monto);

	    if (pagoExitoso) {
	        // Actualizar el saldo de la cuenta restando el monto de la cuota
	        boolean saldoActualizado = negocioCuenta.actualizarSaldo(idCuenta, -monto);

	        if (saldoActualizado) {
	            // Generar el movimiento de pago de préstamo
	            movimiento mov = new movimiento();
	            mov.setIdCuenta(idCuenta);
	            mov.setId_tipo_movimiento(3); // Pago de préstamo
	            mov.setFecha(new java.sql.Date(System.currentTimeMillis()));
	            mov.setDetalle("Pago de cuota de préstamo");
	            BigDecimal importe = BigDecimal.valueOf(monto);
	            mov.setImporte(importe);

	            boolean movimientoGuardado = negocioMovimiento.guardarMovimiento(mov);

	            if (movimientoGuardado) {
	                // Recargar las cuotas por préstamo actualizadas
	                int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
	                List<cuotas> listaCuotas = negocioCuotas.listarCuotasPorPrestamo(idPrestamo);
	                
	                HttpSession session = request.getSession();
	                Integer idCliente = (Integer) session.getAttribute("idCliente");
	                negocioCuentaImpl negocioCuenta = new negocioCuentaImpl();
                    ArrayList<cuenta> cuentas = negocioCuenta.obtenerCuentasPorIdCliente(idCliente);
                    session.setAttribute("cuentas", cuentas);
                    
	                request.setAttribute("listaCuotas", listaCuotas);
	                request.getRequestDispatcher("verCuotas.jsp").forward(request, response);
	            } else {
	                response.getWriter().println("Error al guardar el movimiento.");
	            }
	        } else {
	            response.getWriter().println("Error al actualizar el saldo de la cuenta.");
	        }
	    } else {
	        response.getWriter().println("Error al procesar el pago de la cuota.");
	    }
	}

}
