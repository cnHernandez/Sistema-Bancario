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
import javax.servlet.http.HttpSession;

import entidad.cuenta;
import entidad.movimiento;
import entidad.notificacion;
import excepciones.OperacionFallidaException;
import negocio.negocioMovimiento;
import negocio.negocioCuenta;
import negocioImpl.negocioMovimientoImpl;
import negocioImpl.negocioNotificacionImpl;
import negocioImpl.negocioTransferenciaImpl;
import negocioImpl.negocioClienteImpl;
import negocioImpl.negocioCuentaImpl;


@WebServlet("/ServletTranferencia")
public class ServletTranferencia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	negocioMovimiento negocioMovimiento = new negocioMovimientoImpl();
	negocioCuenta negocioCuenta = new negocioCuentaImpl();
	
    public ServletTranferencia() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 if (request.getParameter("btnBuscarCbu") != null) {
             String cbu = request.getParameter("txtCbu");

             if (cbu == null || cbu.isEmpty()) {
                 request.setAttribute("msj", "CBU inválido. No puede estar vacío.");
             } else {
                 String msj = negocioCuenta.obtenerNombreYApellidoPorCbu(cbu);
                 HttpSession session = request.getSession();
                 session.setAttribute("cbu", cbu);
                 request.setAttribute("msj", msj);
             }
             request.getRequestDispatcher("Transferencia.jsp").forward(request, response);
         }
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		 HttpSession session = request.getSession();
		 String errorMsg = null;
	     boolean operacionExitosa = false;
	     
        if (request.getParameter("btnConfirmarTransferencia") != null) {
            movimiento origen = new movimiento();
            movimiento destino = new movimiento();

            String montoStr = request.getParameter("txtMonto");
            String cbu = (String) session.getAttribute("cbu");
            String idCuentaStr = request.getParameter("idCuenta");

            if (montoStr == null || montoStr.isEmpty() || cbu == null || cbu.isEmpty() || idCuentaStr == null || idCuentaStr.isEmpty() ) {
            	errorMsg = "Faltan datos para realizar la transferencia";
                request.setAttribute("error", errorMsg);
                request.setAttribute("bandera", operacionExitosa);
                RequestDispatcher rd = request.getRequestDispatcher("Transferencia.jsp");
                rd.forward(request, response);
                return;
            }

            try {
                BigDecimal monto = new BigDecimal(montoStr);

                // Validación del monto
                if (monto.compareTo(BigDecimal.ZERO) <= 0) {
                	errorMsg = "El monto debe ser mayor a cero";
                    request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("Transferencia.jsp");
                    rd.forward(request, response);
                    return;
                }

                int idCuentaOrigen = Integer.parseInt(idCuentaStr);

                // Verificación de fondos
                double saldoOrigen = negocioCuenta.obtenerSaldo(idCuentaOrigen);
                if (monto.doubleValue() > saldoOrigen) {
                	errorMsg = "Fondos insuficientes.";
                    request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("Transferencia.jsp");
                    rd.forward(request, response);
                    return;
                }

                // Cuenta Origen
                origen.setIdCuenta(idCuentaOrigen);
                origen.setId_tipo_movimiento(4);
                origen.setDetalle("Transferencia enviada");
                origen.setImporte(monto);

                // Cuenta Destino
                int idCuentaDestino = negocioCuenta.obtenerIdCuenta(cbu);
                boolean CuentaDestinoActiva = negocioCuenta.cuentaDestinoActiva(idCuentaDestino);
                negocioClienteImpl negCliente = new negocioClienteImpl();
                boolean clienteActivo = negCliente.clienteActivo(idCuentaDestino);
                
                if (idCuentaDestino == -1 || !CuentaDestinoActiva || !clienteActivo) {
                	errorMsg = "CBU de destino no valido, cuenta/cliente inactivo.";
                    request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("Transferencia.jsp");
                    rd.forward(request, response);
                    return;
                }
            
                Integer idCliente = (Integer) session.getAttribute("idCliente");
                ArrayList<cuenta> cuentasCliente = negocioCuenta.obtenerCuentasPorIdCliente(idCliente);

                // se asegura que el el cbu no es el mismo a cual se le transfiere
                boolean mismoCbu = false;
                for (cuenta c : cuentasCliente) {
                    if (c.getIdCuenta() == idCuentaOrigen && c.getCbu().equals(cbu)) {
                        mismoCbu = true;
                        break;
                    }
                }

                if (mismoCbu) {
                	errorMsg = "No se puede transferir a la misma cuenta";
                    request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("Transferencia.jsp");
                    rd.forward(request, response);
                    return;
                }
             
                destino.setIdCuenta(idCuentaDestino);
                destino.setImporte(monto);
                destino.setDetalle("Transferencia recibida");
                destino.setId_tipo_movimiento(4);

                // Guardamos movimientos
                int insertOrigen = negocioMovimiento.guardarMovimientoDevolviendoID(origen);
                int insertDestino = negocioMovimiento.guardarMovimientoDevolviendoID(destino);
                
                //INSERT TRANSFERENCIA
                negocioTransferenciaImpl trans = new negocioTransferenciaImpl();
                boolean insertTrans = trans.insertarTransferencia(insertOrigen, insertDestino);
                if (!insertTrans) {
                	errorMsg = "Error al guardar la transferencia";
                    request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("Transferencia.jsp");
                    rd.forward(request, response);
                    return;
                }
                
                // Update Saldo
                boolean updateOrigen = negocioCuenta.updatearSaldoTranferenciaOrigen(idCuentaOrigen, monto.doubleValue());
                boolean updateDestino = negocioCuenta.updatearSaldoTranferenciaDestino(idCuentaDestino, monto.doubleValue());
                session = request.getSession();
               
                ArrayList<cuenta> cuentas = negocioCuenta.obtenerCuentasPorIdCliente(idCliente);         
                session.setAttribute("cuentas", cuentas);
                
                //actualiza notificaciones
                negocioNotificacionImpl neg = new negocioNotificacionImpl();
                ArrayList<notificacion> notificaciones = neg.obtenerNotificacionesPorCliente(idCliente);
        	    session.setAttribute("notificaciones", notificaciones);
        	    
                // mensaje
                if (insertOrigen != -1 && insertDestino != -1 && updateOrigen && updateDestino && insertTrans) {
                	errorMsg = "Transferencia realizada con exito";
                    request.setAttribute("error", errorMsg);
                    operacionExitosa = true;
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("Transferencia.jsp");
                    rd.forward(request, response);
                } else {
                	errorMsg = "Error al realizar la transferencia";
                    request.setAttribute("error", errorMsg);
                    request.setAttribute("bandera", operacionExitosa);
                    RequestDispatcher rd = request.getRequestDispatcher("Transferencia.jsp");
                    rd.forward(request, response);
                }

                RequestDispatcher rd = request.getRequestDispatcher("Transferencia.jsp");
                rd.forward(request, response);
            }catch (NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("msj1", "Formato de número incorrecto.");
                RequestDispatcher rd = request.getRequestDispatcher("Transferencia.jsp");
                rd.forward(request, response);

            } catch (OperacionFallidaException e) {
                // Manejar la excepción OperacionFallidaException
                errorMsg = "Operación fallida: " + e.getMessage();
                request.setAttribute("error", errorMsg);
                request.setAttribute("bandera", operacionExitosa);
                RequestDispatcher rd = request.getRequestDispatcher("Transferencia.jsp");
                rd.forward(request, response);
            }
        }
    }

}
