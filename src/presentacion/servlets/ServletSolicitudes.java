package presentacion.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.solicitudPrestamo;
import negocio.negocioSolicitudPrestamo;
import negocioImpl.negocioSolicitudPrestamoImpl;


@WebServlet("/ServletSolicitudes")
public class ServletSolicitudes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       private negocioSolicitudPrestamo solicitud;

    public ServletSolicitudes() {
        super();
        solicitud = new negocioSolicitudPrestamoImpl();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		List<solicitudPrestamo> listaSolicitudes = solicitud.listarSolicitudesPrestamo();
	    request.setAttribute("listaSolicitudes", listaSolicitudes);
	    request.getRequestDispatcher("SolicitudesPrestamo.jsp").forward(request, response);       
	    }
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
