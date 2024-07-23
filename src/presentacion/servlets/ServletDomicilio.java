package presentacion.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.localidad;
import entidad.provincia;
import excepciones.ListarLocalidadesException;
import excepciones.ListarProvinciasException;
import negocio.negocioLocalidades;
import negocio.negocioProvincia;
import negocioImpl.negocioLocalidadesImpl;
import negocioImpl.negocioProvinciaImpl;

@WebServlet("/ServletDomicilio")
public class ServletDomicilio extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletDomicilio() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String action = request.getParameter("action");
	     String nextPage = request.getParameter("nextPage");

	        if ("cargarDatos".equals(action)) {
	            cargarDatos(request, response, nextPage);
	        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	 private void cargarDatos(HttpServletRequest request, HttpServletResponse response, String nextPage) 
	            throws ServletException, IOException {
		    negocioProvincia negocioProvincia = new negocioProvinciaImpl();
	        negocioLocalidades negocioLocalidades = new negocioLocalidadesImpl();
	        
	        try {
	            ArrayList<provincia> provincias = negocioProvincia.listarProvincias();
	            ArrayList<localidad> localidades = negocioLocalidades.listarLocalidades();

	            request.setAttribute("provincias", provincias);
	            request.setAttribute("localidades", localidades);
	        
	        } catch (ListarProvinciasException e) {
	            e.printStackTrace();
	            request.setAttribute("error", "Error al listar provincias: " + e.getMessage());
	            request.getRequestDispatcher("error.jsp").forward(request, response);
	            return;
	        } catch (ListarLocalidadesException e) {
	            e.printStackTrace();
	            request.setAttribute("error", "Error al listar localidades: " + e.getMessage());
	            request.getRequestDispatcher("error.jsp").forward(request, response);
	            return;
	        } catch (Exception e) {
	            e.printStackTrace();
	            request.setAttribute("error", "Error desconocido: " + e.getMessage());
	            request.getRequestDispatcher("error.jsp").forward(request, response);
	            return;
	        }

	        request.getRequestDispatcher(nextPage).forward(request, response);
	    }
	}
