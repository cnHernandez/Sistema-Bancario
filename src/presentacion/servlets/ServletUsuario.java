package presentacion.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.cliente;
import entidad.usuario;
import negocio.negocioUsuario;
import negocioImpl.negocioUsuarioImpl;


@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	negocioUsuario negocioUsuario = new negocioUsuarioImpl();
	
    public ServletUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorMsg = null;
        boolean operacionExitosa = false;
        String action = request.getParameter("action");

        if (request.getParameter("btnBuscarParaBaja") != null) {
            String dni = request.getParameter("dniCliente");

            cliente obj = negocioUsuario.buscarUusario(dni);
            usuario objUsuario = null;

            if (obj != null) {
                objUsuario = negocioUsuario.buscarUsuario(obj.getIdUsuario());
            }

            if (obj != null && objUsuario != null) {
                request.setAttribute("cliente", obj);
                request.setAttribute("usuario", objUsuario);
            } else {
                errorMsg = "No se encontró usuario con este DNI. Por favor, ingrese un DNI válido.";
            }

            request.setAttribute("error", errorMsg);
            RequestDispatcher rd = request.getRequestDispatcher("/EliminarUsuario.jsp");
            rd.forward(request, response);
            return;
        }
        
        if ("listarTodos".equals(action)) {
            ArrayList<usuario> usuarios = new ArrayList<>();
            negocioUsuarioImpl neg = new negocioUsuarioImpl();
            usuarios = neg.buscarTodosAdministradores();

            if (usuarios != null) {
                request.setAttribute("administradores", usuarios);
            } else {
                errorMsg = "No se encontraron admins";
            }

            request.setAttribute("error", errorMsg);
            RequestDispatcher rd = request.getRequestDispatcher("/EliminarUsuario.jsp");
            rd.forward(request, response);
            return;
        }
        
        if (request.getParameter("btnAceptar") != null) {
            String nombre = request.getParameter("txtNombre");
            String contraseña = request.getParameter("txtPass");
            String confirmContraseña = request.getParameter("txtPassConfirm");
            String tipo = request.getParameter("tipo");
            int tipoUsuario = Integer.parseInt(tipo);
            
            if( nombre.startsWith(".") || nombre.startsWith("-") || nombre.startsWith("_") ||
            		nombre.endsWith(".") || nombre.endsWith("-") || nombre.endsWith("_")) {
            	
            }
            if (ValidarPass(contraseña, confirmContraseña)){
            	if(ValidarNombre(nombre)) {
            		
                usuario nuevo = new usuario();
                nuevo.setNombre(nombre);
                nuevo.setContraseña(contraseña);
                nuevo.setTipo_usuario(tipoUsuario); // Tipo 2 para cliente
                nuevo.setEstado(true);

                negocioUsuarioImpl negocio = new negocioUsuarioImpl();
                int filas = negocio.altaUsuario(nuevo);

                if (filas == 1) {
                    operacionExitosa = true;
                    request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("/AgregarUsuario.jsp");
                    rd.forward(request, response);
                    return;
                } else if (filas == -1) {
                    errorMsg = "El nombre de usuario ya existe. Por favor, elija otro nombre.";
                    request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("/AgregarUsuario.jsp");
                    rd.forward(request, response);
                    return;
                } else {
                    errorMsg = "Error al registrar el usuario. Inténtelo de nuevo.";
                    request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("/AgregarUsuario.jsp");
                    rd.forward(request, response);
                    return;
                	}
            	}else {
            		errorMsg = "Nombre invalido.";
                    request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("/AgregarUsuario.jsp");
                    rd.forward(request, response);
                    return;
            	}	
            } else {
                errorMsg = "Las contraseñas no coinciden, o no es valida";
                request.setAttribute("error", errorMsg);
                request.setAttribute("bandera", operacionExitosa);
                RequestDispatcher rd = request.getRequestDispatcher("/AgregarUsuario.jsp");
                rd.forward(request, response);
                return;
            }
        }

        request.setAttribute("error", errorMsg);
        request.setAttribute("bandera", operacionExitosa);

        if (request.getParameter("action") == null) {
            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        } else {
            errorMsg = null;
            operacionExitosa = false;

            if (request.getParameter("action") != null) {

                if ("eliminar".equals(action)) {
                    int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

                    operacionExitosa = negocioUsuario.bajaUsuario(idUsuario);


                    request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("/EliminarUsuario.jsp");
                    rd.forward(request, response);
                    return;
                } else if ("modificar".equals(action)) {
                	
                    int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                    String nuevaContraseña = request.getParameter("newPassword");
                    operacionExitosa = negocioUsuario.modificarUsuario(nuevaContraseña, idUsuario);

                    // Configurar atributos para mostrar mensaje en JSP
                    request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);

                    // Redireccionar a la página adecuada (EliminarUsuario.jsp en este caso)
                    RequestDispatcher rd = request.getRequestDispatcher("/EliminarUsuario.jsp");
                    rd.forward(request, response);
                
                } else if ("activar".equals(action)) {
                	
                    int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                    operacionExitosa = negocioUsuario.ActivarUsuario(idUsuario);

                    request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("/EliminarUsuario.jsp");
                    rd.forward(request, response);
                    return;
                }
            }
        }
    }

    public boolean ValidarNombre(String username) {
        // Longitud
        if (username.length() < 3 || username.length() > 20) {
            return false;
        }

        // Caracteres permitidos
        if (!username.matches("^[a-zA-Z0-9._-]+$")) {
            return false;
        }

        // No comenzar ni terminar con caracteres especiales
        if (username.startsWith(".") || username.startsWith("-") || username.startsWith("_") ||
            username.endsWith(".") || username.endsWith("-") || username.endsWith("_")) {
            return false;
        }

        // No caracteres repetidos consecutivamente
        if (username.contains("..") || username.contains("__") || username.contains("--")) {
            return false;
        }
        // No solo números
        if (username.matches("^[0-9]+$")) {
            return false;
        }
        return true;
    }
    
    public boolean ValidarPass(String password, String confirmPassword) {
        // Comprobar si las contraseñas coinciden
        if (!password.equals(confirmPassword)) {
            return false;
        }

        // Longitud mínima
        if (password.length() < 5) {
            return false;
        }

        // No comenzar ni terminar con caracteres especiales
        if (password.startsWith(".") || password.startsWith("-") || password.startsWith("_") ||
        		password.endsWith(".") || password.endsWith("-") || password.endsWith("_")) {
            return false;
        }

        // No caracteres repetidos consecutivamente
        if (password.contains("..") || password.contains("__") || password.contains("--")) {
            return false;
        }

        // No espacios en blanco
        if (password.contains(" ")) {
            return false;
        }
        return true;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
        rd.forward(request, response);
    }

}

