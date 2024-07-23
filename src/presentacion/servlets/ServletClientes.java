package presentacion.servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.cliente;
import entidad.direccion;
import entidad.localidad;
import entidad.provincia;
import entidad.usuario;
import excepciones.ClienteNoEncontradoException;
import excepciones.DireccionModificarException;
import excepciones.InsertarLocalidadException;
import excepciones.InsertarProvinciaException;
import excepciones.LocalidadExistenteException;
import excepciones.ObtenerLocalidadException;
import excepciones.ObtenerProvinciaPorIdException;
import excepciones.ObtenerProvinciaPorNombreException;
import excepciones.OperacionFallidaException;
import excepciones.ProvinciaExisteException;
import negocio.negocioCliente;
import negocio.negocioDireccion;
import negocio.negocioLocalidades;
import negocio.negocioProvincia;
import negocioImpl.negocioClienteImpl;
import negocioImpl.negocioDireccionImpl;
import negocioImpl.negocioLocalidadesImpl;
import negocioImpl.negocioProvinciaImpl;

@WebServlet("/ServletClientes")
public class ServletClientes extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private negocioCliente negocioCliente;

    public ServletClientes() {
        super();
        negocioCliente = new negocioClienteImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	  String action = request.getParameter("action");

    	    try {
    	        if ("modificar".equals(action)) {
    	            usuario user = new usuario();
    	            user.setId_usuario(Integer.parseInt(request.getParameter("idUsuario")));
    	            cliente cliente = negocioCliente.obtenerClientePorIdUsuario(user);
    	            request.setAttribute("cliente", cliente);

    	            // Redirigir a ServletDomicilio para cargar provincias y localidades
    	            request.getRequestDispatcher("ServletDomicilio?action=cargarDatos&nextPage=ModificarCliente.jsp").forward(request, response);
    	        } else if ("eliminar".equals(action)) {
    	            eliminarCliente(request, response);
    	        } else if ("completarMisDatos".equals(action)) {
    	            HttpSession session = request.getSession(false);
    	            if (session != null) {
    	                usuario user = new usuario();
    	                user.setId_usuario((int) session.getAttribute("idUsuario"));

    	                cliente cliente = negocioCliente.obtenerClientePorIdUsuario(user);
    	                request.setAttribute("cliente", cliente);

    	                // Redirigir a ServletDomicilio para cargar provincias y localidades
    	                request.getRequestDispatcher("ServletDomicilio?action=cargarDatos&nextPage=ModificarCliente.jsp").forward(request, response);
    	            } else {
    	                response.sendRedirect("login.jsp");
    	            }
    	        } else if ("listar".equals(action)) {
    	            ArrayList<cliente> lista = negocioCliente.listarClientes();
    	            request.setAttribute("listarClientes", lista);
    	            request.getRequestDispatcher("ListarCliente.jsp").forward(request, response);
    	        }
    	    } catch (OperacionFallidaException e) {
    	        e.printStackTrace(); 
    	        response.sendRedirect("error.jsp"); // Redirige a una página de error
    	    } catch (ClienteNoEncontradoException e) {
    	        e.printStackTrace(); 
    	        response.sendRedirect("error.jsp"); 
    	    }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
    	  boolean esModificacion = Boolean.parseBoolean(request.getParameter("esModificacion"));
    	   try {
    	        if (esModificacion) {
    	            modificarCliente(request, response);
    	        } else {
    	            agregarCliente(request, response);
    	        }
    	       } catch (LocalidadExistenteException e) {
    	           // Manejar la excepción LocalidadExistenteException
    	           response.sendRedirect("error.jsp");
    	       } catch (InsertarLocalidadException e) {
    	           // Manejar la excepción InsertarLocalidadException
    	           response.sendRedirect("error.jsp");
    	       } catch (ObtenerProvinciaPorNombreException e) {
    	           // Manejar la excepción ObtenerProvinciaPorNombreException
    	           response.sendRedirect("error.jsp");
    	       } catch (ProvinciaExisteException e) {
    	           // Manejar la excepción ProvinciaExisteException
    	           response.sendRedirect("error.jsp");
    	       } catch (InsertarProvinciaException e) {
    	           // Manejar la excepción InsertarProvinciaException
    	           response.sendRedirect("error.jsp");
    	       } catch (DireccionModificarException e) {
    	           // Manejar la excepción DireccionModificarException
    	           response.sendRedirect("error.jsp");
    	       }
      }

      private void agregarCliente(HttpServletRequest request, HttpServletResponse response) 
              throws ServletException, IOException, InsertarProvinciaException, ProvinciaExisteException, ObtenerProvinciaPorNombreException, InsertarLocalidadException, LocalidadExistenteException {
    	  boolean bandera = false;
    	    String error = null;
    	    HttpSession session = request.getSession(false);

    	    try {
    	        cliente cliente = new cliente();

    	        if (session != null) {
    	            Object idUsuarioObj = session.getAttribute("idUsuario");

    	            if (idUsuarioObj != null) {
    	                int idUsuario = (Integer) idUsuarioObj;
    	                cliente.setIdUsuario(idUsuario);
    	            }
    	        }
    	        cliente.setDni(request.getParameter("dni"));
    	        cliente.setCuil(request.getParameter("cuil"));
    	        cliente.setNombre(request.getParameter("nombre"));
    	        cliente.setApellido(request.getParameter("apellido"));
    	        cliente.setSexo(request.getParameter("sexo"));
    	        cliente.setNacionalidad(request.getParameter("nacionalidad"));
    	        cliente.setFechaNacimiento(Date.valueOf(request.getParameter("fechaNacimiento")));

    	        // Configuración de la dirección
    	        direccion direccion = new direccion();
    	        direccion.setCalle(request.getParameter("direccionCalle"));
    	        direccion.setNumero(request.getParameter("direccionNumero"));

    	        // Obtener ID de provincia y localidad desde los desplegables
    	        int idProvincia = Integer.parseInt(request.getParameter("provincia"));
    	        int idLocalidad = Integer.parseInt(request.getParameter("localidad"));

    	        // Obtener la provincia y la localidad desde la base de datos
    	        negocioProvincia provinciaNeg = new negocioProvinciaImpl();
    	        provincia provincia = provinciaNeg.obtenerPorId(idProvincia);

    	        negocioLocalidades localidadNeg = new negocioLocalidadesImpl();
    	        localidad localidad = localidadNeg.obtenerPorId(idLocalidad);

    	        if (provincia != null && localidad != null) {
    	            localidad.setProvincia(provincia);
    	            direccion.setLocalidad(localidad);
    	            cliente.setDireccion(direccion);
    	            cliente.setCorreoElectronico(request.getParameter("correoElectronico"));
    	            cliente.setTelefono(request.getParameter("telefono"));
    	            cliente.setEstado(true);

    	            bandera = negocioCliente.agregarCliente(cliente);
    	        } else {
    	            error = "Error al obtener la provincia o localidad.";
    	        }
    	    } catch (NumberFormatException e) {
    	        error = "Error al convertir un número.";
    	        e.printStackTrace();
    	    } catch (ObtenerProvinciaPorIdException e) {
    	        error = "Error relacionado con la provincia: " + e.getMessage();
    	        e.printStackTrace();
    	    } catch (ObtenerLocalidadException e) {
    	        error = "Error relacionado con la localidad: " + e.getMessage();
    	        e.printStackTrace();
    	    } catch (OperacionFallidaException e) {
    	        error = "Error al agregar cliente: " + e.getMessage();
    	        e.printStackTrace();
    	    } catch (Exception e) {
    	        error = "Error inesperado al procesar la solicitud: " + e.getMessage();
    	        e.printStackTrace();
    	    }

    	    request.setAttribute("bandera", bandera);
    	    request.setAttribute("error", error);
    	    request.getRequestDispatcher("ModificarCliente.jsp").forward(request, response);
    	}
     

      private void modificarCliente(HttpServletRequest request, HttpServletResponse response) 
    	        throws ServletException, IOException, DireccionModificarException, InsertarProvinciaException, ProvinciaExisteException, ObtenerProvinciaPorNombreException, InsertarLocalidadException, LocalidadExistenteException {
    	  try {
              int idCliente = Integer.parseInt(request.getParameter("idCliente"));
              int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
              String dni = request.getParameter("dni");
              String cuil = request.getParameter("cuil");
              String nombre = request.getParameter("nombre");
              String apellido = request.getParameter("apellido");
              String sexo = request.getParameter("sexo");
              String nacionalidad = request.getParameter("nacionalidad");
              Date fechaNacimiento = Date.valueOf(request.getParameter("fechaNacimiento"));

              // Configuración de la dirección existente
              direccion direccion = new direccion();
              direccion.setIdDireccion(Integer.parseInt(request.getParameter("idDireccion")));
              direccion.setCalle(request.getParameter("direccionCalle"));
              direccion.setNumero(request.getParameter("direccionNumero"));

              // Obtener ID de provincia y localidad desde los desplegables
              int idProvincia = Integer.parseInt(request.getParameter("provincia"));
              int idLocalidad = Integer.parseInt(request.getParameter("localidad"));

              // Obtener la provincia y la localidad desde la base de datos
              negocioProvincia provinciaNeg = new negocioProvinciaImpl();
              provincia provincia = provinciaNeg.obtenerPorId(idProvincia);

              negocioLocalidades localidadNeg = new negocioLocalidadesImpl();
              localidad localidad = localidadNeg.obtenerPorId(idLocalidad);

              if (provincia != null && localidad != null) {
                  localidad.setProvincia(provincia);
                  direccion.setLocalidad(localidad);

                  // Modificar la dirección
                  negocioDireccion direccionNeg = new negocioDireccionImpl();
                  boolean modificacionDireccionExitosa = direccionNeg.modificar(direccion); // Utilizando el método modificar

                  if (modificacionDireccionExitosa) {
                      String correoElectronico = request.getParameter("correoElectronico");
                      String telefono = request.getParameter("telefono");

                      // Estado del cliente
                      boolean estado = Boolean.parseBoolean(request.getParameter("estado"));

                      cliente clienteModificado = new cliente(idCliente, idUsuario, dni, cuil, nombre, apellido, sexo, nacionalidad,
                              fechaNacimiento, direccion, correoElectronico, telefono, estado);

                      // Modificar el cliente
                      negocioCliente.modificarCliente(clienteModificado);

                      request.setAttribute("bandera", true); // Indicar éxito en la modificación
                  } else {
                      request.setAttribute("error", "Error al modificar la dirección.");
                  }
              } else {
                  request.setAttribute("error", "Error al obtener la provincia o localidad.");
              }}
              catch (ObtenerProvinciaPorIdException e) {
                  request.setAttribute("error", "Error relacionado con la provincia: " + e.getMessage());
                  e.printStackTrace();
              } catch (ObtenerLocalidadException e) {
                  request.setAttribute("error", "Error relacionado con la localidad: " + e.getMessage());
                  e.printStackTrace();
              } 
          catch (NumberFormatException e) {
              request.setAttribute("error", "Formato de número inválido.");
          } catch (NullPointerException e) {
              request.setAttribute("error", "Uno de los campos obligatorios está vacío.");
          }
    	  catch (OperacionFallidaException e) {
              request.setAttribute("error", "Error al modificar el cliente: " + e.getMessage());
          }
    	  catch (Exception e) {
              request.setAttribute("error", "Ocurrió un error inesperado: " + e.getMessage());
          }
    	  
    	  response.sendRedirect("ServletClientes?action=listar");
    	}


    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	  String idClienteStr = request.getParameter("idCliente");
    	    int idCliente = Integer.parseInt(idClienteStr);

    	    try {
    	        boolean eliminado = negocioCliente.eliminarCliente(idCliente);

    	        ArrayList<cliente> lista = negocioCliente.listarClientes();
    	        request.setAttribute("bandera", eliminado);
    	        request.setAttribute("listarClientes", lista);
    	        request.setAttribute("eliminado", eliminado);
    	        request.getRequestDispatcher("ListarCliente.jsp").forward(request, response);
    	    } catch (OperacionFallidaException e) {
    	        e.printStackTrace(); // Opcional: imprime el stack trace para debug
    	        response.sendRedirect("error.jsp"); // Redirige a una página de error
    	    }   
    }
    }
     
 
