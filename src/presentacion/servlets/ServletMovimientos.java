package presentacion.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entidad.movimiento;
import negocioImpl.negocioMovimientoImpl;

@WebServlet("/ServletMovimientos")
public class ServletMovimientos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private negocioMovimientoImpl negocioMovimiento;
	
    public ServletMovimientos() {
        super();
        negocioMovimiento = new negocioMovimientoImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
    	String idCuentaStr = request.getParameter("idCuenta");
    	
    	if (idCuentaStr != null && !idCuentaStr.isEmpty()) {
    		int idCuenta = Integer.parseInt(idCuentaStr);
	    	 if ("listar".equalsIgnoreCase(action)) {
	        	 listarMovimientos(request, response, idCuenta);
	         }
    	} else {
            response.sendRedirect("Error.jsp");
        }
	}
	
	private void listarMovimientos(HttpServletRequest request, HttpServletResponse response, int idCuenta)
            throws ServletException, IOException {

        ArrayList<movimiento> listarMovimientos = negocioMovimiento.obtenerMovimientosPorCuenta(idCuenta);
        request.setAttribute("listarMovimientos", listarMovimientos);
        request.getRequestDispatcher("listarMovimientos.jsp").forward(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}