package presentacion.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.movimiento;
import entidad.prestamo;
import negocio.negocioMovimiento;
import negocio.negocioPrestamo;
import negocioImpl.negocioMovimientoImpl;
import negocioImpl.negocioPrestamoImpl;


@WebServlet("/ServletReportes")
public class ServletReportes extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletReportes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 String fechaInicioPrestamosStr = request.getParameter("fechaInicio");
    	    String fechaFinPrestamosStr = request.getParameter("fechaFin");
    	    String fechaInicioBalanceStr = request.getParameter("fechaInicioBalance");
    	    String fechaFinBalanceStr = request.getParameter("fechaFinBalance");
    	    String fechaInicioInteresesStr = request.getParameter("fechaInicioIntereses");
    	    String fechaFinInteresesStr = request.getParameter("fechaFinIntereses");

    	    if (fechaInicioPrestamosStr != null && fechaFinPrestamosStr != null) {
    	        try {
    	            java.sql.Date fechaInicioPrestamos = java.sql.Date.valueOf(fechaInicioPrestamosStr);
    	            java.sql.Date fechaFinPrestamos = java.sql.Date.valueOf(fechaFinPrestamosStr);
    	            procesarReportePrestamosOtorgados(request, response, fechaInicioPrestamos, fechaFinPrestamos);

    	        } catch (IllegalArgumentException e) {
    	            request.setAttribute("error", "Formato de fecha inválido para reporte de préstamos. Utilice yyyy-mm-dd.");
    	            RequestDispatcher dispatcher = request.getRequestDispatcher("Reportes.jsp");
    	            dispatcher.forward(request, response);
    	        }
    	    } else if (fechaInicioBalanceStr != null && fechaFinBalanceStr != null) {
    	        try {
    	            java.sql.Date fechaInicioBalance = java.sql.Date.valueOf(fechaInicioBalanceStr);
    	            java.sql.Date fechaFinBalance = java.sql.Date.valueOf(fechaFinBalanceStr);
    	            procesarBalance(request, response, fechaInicioBalance, fechaFinBalance);

    	        } catch (IllegalArgumentException e) {
    	            request.setAttribute("error", "Formato de fecha inválido para generar balance. Utilice yyyy-mm-dd.");
    	            RequestDispatcher dispatcher = request.getRequestDispatcher("Reportes.jsp");
    	            dispatcher.forward(request, response);
    	        }
    	    }else if (fechaInicioInteresesStr != null && fechaFinInteresesStr != null) {
    	        try {
    	            java.sql.Date fechaInicioIntereses = java.sql.Date.valueOf(fechaInicioInteresesStr);
    	            java.sql.Date fechaFinIntereses = java.sql.Date.valueOf(fechaFinInteresesStr);
    	            procesarInteresesGenerados(request, response, fechaInicioIntereses, fechaFinIntereses);

    	        } catch (IllegalArgumentException e) {
    	            request.setAttribute("error", "Formato de fecha inválido para reporte de intereses. Utilice yyyy-mm-dd.");
    	            RequestDispatcher dispatcher = request.getRequestDispatcher("Reportes.jsp");
    	            dispatcher.forward(request, response);
    	        }} else {
    	        request.setAttribute("error", "Fechas de inicio y fin son requeridas.");
    	        RequestDispatcher dispatcher = request.getRequestDispatcher("Reportes.jsp");
    	        dispatcher.forward(request, response);
    	    }
    }

    private void procesarReportePrestamosOtorgados(HttpServletRequest request, HttpServletResponse response, java.sql.Date fechaInicio, java.sql.Date fechaFin) throws ServletException, IOException {
        negocioPrestamo negocioPrestamo = new negocioPrestamoImpl();
        ArrayList<prestamo> prestamos = negocioPrestamo.obtenerPrestamosOtorgados(fechaInicio, fechaFin);
        
        request.setAttribute("prestamos", prestamos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Reportes.jsp");
        dispatcher.forward(request, response);
    }
    
    private void procesarBalance(HttpServletRequest request, HttpServletResponse response, java.sql.Date fechaInicio, java.sql.Date fechaFin) throws ServletException, IOException {
        BigDecimal pagoPrestamoTotal = BigDecimal.ZERO;
        BigDecimal altaPrestamoTotal = BigDecimal.ZERO;
        BigDecimal altaCuentaTotal = BigDecimal.ZERO;

        negocioMovimiento neg = new negocioMovimientoImpl();
        ArrayList<movimiento> movimientos = neg.obtenerMovimientosEntreFechas(fechaInicio, fechaFin);
        if (movimientos != null) {
            for (movimiento mov : movimientos) {
                if (mov.getId_tipo_movimiento() == 1) {
                    altaCuentaTotal = altaCuentaTotal.add(mov.getImporte());
                } else if (mov.getId_tipo_movimiento() == 2) {
                    altaPrestamoTotal = altaPrestamoTotal.add(mov.getImporte());
                } else if (mov.getId_tipo_movimiento() == 3) {
                    pagoPrestamoTotal = pagoPrestamoTotal.add(mov.getImporte());
                }
            }
        }

        BigDecimal balanceCalculado = pagoPrestamoTotal.subtract(altaPrestamoTotal).subtract(altaCuentaTotal);
        request.setAttribute("balance", balanceCalculado);

        RequestDispatcher dispatcher = request.getRequestDispatcher("Reportes.jsp");
        dispatcher.forward(request, response);
    }
    
    private void procesarInteresesGenerados(HttpServletRequest request, HttpServletResponse response, java.sql.Date fechaInicio, java.sql.Date fechaFin) throws ServletException, IOException {
    	  BigDecimal interesesGeneradosTotal = BigDecimal.ZERO;

    	    negocioPrestamo negocioPrestamo = new negocioPrestamoImpl();
    	    ArrayList<prestamo> prestamos = negocioPrestamo.obtenerPrestamosOtorgados(fechaInicio, fechaFin);

    	    if (prestamos != null) {
    	        for (prestamo prestamo : prestamos) {
    	            // Calcular intereses generados como la diferencia entre importeTotal e importePedido
    	            BigDecimal importeTotal = BigDecimal.valueOf(prestamo.getImporteTotal());
    	            BigDecimal importePedido = BigDecimal.valueOf(prestamo.getImportePedido());

    	           

    	            BigDecimal interesesGenerados = importeTotal.subtract(importePedido);
    	            interesesGeneradosTotal = interesesGeneradosTotal.add(interesesGenerados);

    	           
    	        }
    	    }


    	    request.setAttribute("interesesGenerados", interesesGeneradosTotal);

    	    RequestDispatcher dispatcher = request.getRequestDispatcher("Reportes.jsp");
    	    dispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
