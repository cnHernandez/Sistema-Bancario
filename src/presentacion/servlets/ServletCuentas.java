package presentacion.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.cuenta;
import entidad.movimiento;
import negocio.negocioCliente;
import negocio.negocioCuenta;
import negocio.negocioMovimiento;
import negocioImpl.negocioClienteImpl;
import negocioImpl.negocioCuentaImpl;
import negocioImpl.negocioMovimientoImpl;


    @WebServlet("/ServletCuentas")
    public class ServletCuentas extends HttpServlet {
    	private static final long serialVersionUID = 1L;
    	
    	private negocioCuenta negocioCuenta;
    	private negocioCliente negocioCliente;
    	private negocioMovimiento negocioMovimiento;

        public ServletCuentas() {
            super();
            negocioCuenta = new negocioCuentaImpl();
            negocioCliente = new negocioClienteImpl();
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String action = request.getParameter("action");
            String idClienteStr = request.getParameter("idCliente");

            if (idClienteStr != null && !idClienteStr.isEmpty()) {           
                int idCliente = Integer.parseInt(idClienteStr);
                
                if ("insertar".equalsIgnoreCase(action)) {
                    request.getRequestDispatcher("InsertarCuenta.jsp").forward(request, response);
                } else if ("listar".equalsIgnoreCase(action)) {
                    listarCuentas(request, response, idCliente);
                } else if ("eliminar".equalsIgnoreCase(action)) {
                    eliminarCuenta(request, response, idCliente);
                }
            } else {
                // Manejo si no se proporciona idCliente
                if ("listar".equalsIgnoreCase(action)) {
                    listarCuentas(request, response, null);
                } else {
                    response.sendRedirect("Error.jsp");
                }
            }
        }

        private void listarCuentas(HttpServletRequest request, HttpServletResponse response, Integer idCliente)
                throws ServletException, IOException {
            ArrayList<cuenta> listarCuentas;

            if (idCliente != null) {
                // Obtener cuentas del cliente
                listarCuentas = obtenerCuentasPorCliente(idCliente);
            } else {
                // Inicializar una lista vacía si idCliente es nulo
                listarCuentas = new ArrayList<>();
            }

            request.setAttribute("listarCuentas", listarCuentas);
            request.getRequestDispatcher("ListarCuentas.jsp").forward(request, response);
        }
        private void eliminarCuenta(HttpServletRequest request, HttpServletResponse response, int idCliente)
                throws ServletException, IOException {
            String idCuentaStr = request.getParameter("idCuenta");

            if (idCuentaStr != null && !idCuentaStr.isEmpty()) {
                int idCuenta = Integer.parseInt(idCuentaStr);

                // Llama al método para eliminar la cuenta
                boolean eliminado = negocioCuenta.eliminarCuenta(idCuenta);

                // Actualiza los atributos de la solicitud con los resultados
                ArrayList<cuenta> listarCuentas = obtenerCuentasPorCliente(idCliente);
                request.setAttribute("listarCuentas", listarCuentas);
                request.setAttribute("bandera", eliminado);
                request.setAttribute("error", eliminado ? null : "No se pudo eliminar la cuenta");
                request.getRequestDispatcher("ListarCuentas.jsp").forward(request, response);
            } else {
                // Manejo si no se proporciona idCuenta
                response.sendRedirect("Error.jsp");
            }
        }
        
        private ArrayList<cuenta> obtenerCuentasPorCliente(int idCliente) {
            return negocioCuenta.obtenerCuentasPorIdCliente(idCliente);
        }
       

    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		 boolean bandera = false;
    	        String error = null;
    	        String numeroCuenta = "El número de cuenta se generará automáticamente al agregar la cuenta";
    		    String cbu = "El CBU se generará automáticamente al agregar la cuenta";


    	        try {
    	            String cliente = request.getParameter("idCliente");
    	            String id_tipo_cuenta = request.getParameter("id_tipo_cuenta");
    	          

    	            if (cliente == null || cliente.isEmpty() || id_tipo_cuenta == null || id_tipo_cuenta.isEmpty()) {
    	                error = "Todos los campos son obligatorios.";
    	                throw new IllegalArgumentException(error);
    	            }

    	            int id = Integer.parseInt(cliente);
    	            boolean existe = negocioCliente.existe(id);

    	            if (!existe) {
    	                error = "No existe el cliente";
    	                throw new IllegalArgumentException(error);
    	            }
    	            
    	            // Generar y validar automáticamente el número de cuenta y CBU
    	            
    	            do {
    	                numeroCuenta = generarNumeroCuenta();
    	            } while (negocioCuenta.existeNumeroCuenta(numeroCuenta));
    	            
    	            do {
    	                cbu = generarCBU();
    	            } while (negocioCuenta.existeCBU(cbu));


    	            cuenta cuenta = new cuenta();
    	            cuenta.setIdCliente(id);
    	            cuenta.setId_tipo_cuenta(Integer.parseInt(id_tipo_cuenta));
    	            cuenta.setFechaCreacion(Date.valueOf(LocalDate.now())); 
    	            cuenta.setNumeroCuenta(numeroCuenta);
    	            cuenta.setCbu(cbu);
    	            cuenta.setEstado(true);

    	            bandera = negocioCuenta.agregarCuenta(cuenta);

    	            
    	         // Generar movimiento de Alta de cuenta
    	            if (bandera) {
    	                // Obtener el ID de cuenta generado
    	                int idCuentaGenerado = negocioCuenta.obtenerIdCuenta(cuenta.getCbu()); 

    	                movimiento mov = new movimiento();
    	                mov.setIdCuenta(idCuentaGenerado); 
    	                mov.setId_tipo_movimiento(1); // 1 para Alta de cuenta
    	                mov.setFecha(new java.sql.Date(System.currentTimeMillis()));
    	                mov.setDetalle("Alta de cuenta: " + cuenta.getNumeroCuenta());
    	                BigDecimal importe = BigDecimal.valueOf(10000); 
    	                mov.setImporte(importe);

    	                negocioMovimiento = new negocioMovimientoImpl();
    	                boolean movimientoGuardado = negocioMovimiento.guardarMovimiento(mov);

    	                if (!movimientoGuardado) {
    	                    throw new Exception("Error al guardar el movimiento de Alta de cuenta.");
    	                }
    	            }
    	            HttpSession session = request.getSession();
    	            negocioMovimientoImpl mov = new negocioMovimientoImpl();
                    ArrayList<movimiento> movimientos = mov.obtenerMovimientos();
                    session.setAttribute("movimientos", movimientos);
    	            
    	        } catch (IllegalArgumentException e) {
    	            error = e.getMessage();
    	        } catch (Exception e) {
    	            error = "Error al procesar la solicitud: " + e.getMessage();
    	            e.printStackTrace();
    	        }

    	        request.setAttribute("bandera", bandera);
    	        request.setAttribute("error", error);
    	        request.setAttribute("numeroCuenta", numeroCuenta);
    		    request.setAttribute("cbu", cbu);
    	        request.getRequestDispatcher("InsertarCuenta.jsp").forward(request, response);
    	    }
    	
    	private String generarNumeroCuenta() {
	        Random rand = new Random();
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < 15; i++) {
	            sb.append(rand.nextInt(10));
	        }
	        return sb.toString();
	    }

	    private String generarCBU() {
	        Random rand = new Random();
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < 22; i++) {
	            sb.append(rand.nextInt(10));
	        }
	        return sb.toString();
	    }

    }
