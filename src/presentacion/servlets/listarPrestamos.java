package presentacion.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.prestamo;
import negocio.negocioPrestamo;
import negocioImpl.negocioPrestamoImpl;


@WebServlet("/listarPrestamos")
public class listarPrestamos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public listarPrestamos() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // Obtener idCliente desde la sesión
        HttpSession session = request.getSession();
        Integer idCliente = (Integer) session.getAttribute("idCliente");

        // Manejar el caso en que idCliente no esté presente en la sesión
        if (idCliente == null) {
        	 List<prestamo> listaPrestamosVacia;
        	 listaPrestamosVacia = new ArrayList<>();
        	 request.setAttribute("listaPrestamos", listaPrestamosVacia);
             request.getRequestDispatcher("verPrestamos.jsp").forward(request, response);
        }else {
        // Llamar al negocio para obtener la lista de préstamos del cliente
        negocioPrestamo negocioPrestamo = new negocioPrestamoImpl();
        List<prestamo> listaPrestamos = negocioPrestamo.listarPrestamosPorCliente(idCliente);

        // Setear la lista de préstamos como atributo de solicitud y reenviar a JSP
        request.setAttribute("listaPrestamos", listaPrestamos);
        request.getRequestDispatcher("verPrestamos.jsp").forward(request, response);
        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
