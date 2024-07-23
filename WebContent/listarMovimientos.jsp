<%@page import="entidad.cuenta" %>
<%@page import="entidad.movimiento" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.math.BigDecimal" %> <!-- Importa BigDecimal -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="navbar.jsp" %>
<% if (session.getAttribute("tipoUsuario") != null) { %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Movimientos</title>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/2.0.8/css/dataTables.dataTables.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/2.0.8/js/dataTables.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $.fn.dataTable.ext.errMode = 'none';
            $('#table_id').DataTable();
        });
    </script>
</head>
<body>
    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <p class="text-danger"><%= error %></p>
    <% } %>

    <% Boolean band = (Boolean) request.getAttribute("bandera"); %>
    <% if (band != null) { %>
        <div class="alert <%= band ? "alert-success" : "alert-danger" %> alert-dismissible fade show" role="alert">
            <%= band ? "Operación realizada correctamente!" : "No se pudo realizar la operación!" %>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    <% } %>

    <% 
    ArrayList<movimiento> listarMovimientos = null;
    if(request.getAttribute("listarMovimientos") != null) {
    	listarMovimientos = (ArrayList<movimiento>) request.getAttribute("listarMovimientos");
    }
    Integer idCliente = (Integer) session.getAttribute("idCliente");
    %>
    <form method="get" action="ServletMovimientos">
  	<div class="container mt-4">
  		<h1 class="display-4 text-center mb-4">Movimientos</h1>
	    <div class="table-responsive">
		    <table class="table table-bordered table-striped" id="table_id">
		        <thead class="thead-dark">
		            <tr class="table-header">
		                <th>ID Movimiento</th>
		                <th>ID Cuenta</th>
		                <th>Tipo de Movimiento</th>
		                <th>Fecha</th> 
		                <th>Detalle</th>
		                <th>Importe $</th>
		            </tr>
		        </thead>
		        <tbody>
    <% if (listarMovimientos != null && !listarMovimientos.isEmpty()) {
        for (movimiento movimiento : listarMovimientos) {
            BigDecimal importe = movimiento.getImporte();
            String importeClass = (importe.compareTo(BigDecimal.ZERO) >= 0) ? "positive" : "negative";
            String signo = "";
            String tipoMovimiento = movimiento.getDetalle(); 

            if (tipoMovimiento.equals("Transferencia enviada")) {
                signo = "-";
            } else if (tipoMovimiento.equals("Transferencia recibida")) {
                signo = "+";
            }
    %>
    <tr class="table-row">
        <td><%= movimiento.getIdMovimiento() %></td>
        <td><%= movimiento.getIdCuenta() %></td>
        <td><%= movimiento.getId_tipo_movimiento() %></td>
        <td><%= movimiento.getFecha() %></td>
        <td><%= movimiento.getDetalle() %></td>
        <td class="<%= importeClass %>"><%= signo %><%= importe.abs() %></td>
    </tr>
    <%  
        }
    } else { 
    %>
    <tr>
        <td colspan="6" class="text-center">No hay movimientos para mostrar</td>
    </tr>
    <% } %>
</tbody>
	        </table>
	    </div>
	    
	    <div class="text-center">
	        <a href="<%=idCliente != null ? "ServletCuentas?action=listar&idCliente=" + idCliente : "javascript:history.back();"%>" class="btn btn-secondary">Volver</a>
	    </div>
	</div>
	</form>

</body>
</html>

<% } else { %>
    <%@ include file="Error.jsp" %>
<% } %>
