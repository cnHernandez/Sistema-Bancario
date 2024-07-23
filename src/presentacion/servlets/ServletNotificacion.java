package presentacion.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.notificacion;
import negocioImpl.negocioNotificacionImpl;

/**
 * Servlet implementation class ServletNotificacion
 */
@WebServlet("/ServletNotificacion")
public class ServletNotificacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletNotificacion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el id del cliente desde la sesión
        Integer idCliente = (Integer) request.getSession().getAttribute("idCliente");

        negocioNotificacionImpl neg = new negocioNotificacionImpl();
        int cantidadNotificaciones = 0;
        if (idCliente != null) {
            cantidadNotificaciones = neg.contarNotificacionesNoLeidas(idCliente); // Ajusta según tu implementación
        }

        // Escribir la cantidad como respuesta
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(cantidadNotificaciones));
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int idNotificacion = Integer.parseInt(request.getParameter("idNotificacion"));

	    negocioNotificacionImpl neg = new negocioNotificacionImpl();
	    boolean actualizacionExitosa = neg.marcarNotificacionComoLeida(idNotificacion);

	    HttpSession session = request.getSession();

	    if (actualizacionExitosa) {
	        // Recargar la lista de notificaciones actualizadas
	        Integer idCliente = (Integer) session.getAttribute("idCliente");
	        ArrayList<notificacion> notificaciones = neg.obtenerNotificacionesPorCliente(idCliente);

	        // Establecer la lista actualizada en la sesión
	        session.setAttribute("notificaciones", notificaciones);

	        // Redirigir a la página de notificaciones
	        response.sendRedirect("notificaciones.jsp");
	    } else {
	        // Manejar el error o redirigir a una página de error
	        response.sendRedirect("error.jsp");
	    }
	}


}
