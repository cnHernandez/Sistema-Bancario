package presentacion.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.pago;
import negocioImpl.negocioPagosImpl;

/**
 * Servlet implementation class ServletPagos
 */
@WebServlet("/ServletPagos")
public class ServletPagos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	   private negocioPagosImpl negocioPago;

	    public void init() {
	        this.negocioPago = new negocioPagosImpl();
	    }
	    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPagos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
        int idCliente = (int) session.getAttribute("idCliente");

        ArrayList<pago> listaPagos = negocioPago.obtenerPagosPorIdCliente(idCliente);

        request.setAttribute("listaPagos", listaPagos);
        request.getRequestDispatcher("misPagos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
