<%@page import="entidad.cuenta" %>
<%@page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="navbar.jsp" %>
<% if (session.getAttribute("tipoUsuario") != null) { %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cuentas</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h1 class="display-4 text-center mb-4">Cuentas</h1>
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
    ArrayList<cuenta> listarCuentas = null;
    if(request.getAttribute("listarCuentas") != null) {
        listarCuentas = (ArrayList<cuenta>) request.getAttribute("listarCuentas");
    }
    %>
    
    <div class="table-responsive">
	    <table class="table table-bordered table-striped">
	        <thead class="thead-dark">
	            <tr class="table-header">
	                <th>ID Cliente</th>
	                <th>ID Cuenta</th>
	                <th>Tipo de Cuenta</th>
	                <th>Numero de Cuenta</th> 
	                <th>CBU</th>
	                <th>SALDO $</th>
	                <th>MOVIMIENTOS</th>
	                <% if((int) session.getAttribute("tipoUsuario") == 1) {%>
	                	<th>ELIMINAR</th>
	                <% } %>
	            </tr>
	        </thead>
	        <tbody>
	            <% if(listarCuentas != null && !listarCuentas.isEmpty()) {
	                for (cuenta cuenta : listarCuentas) {
	            %>
	            <tr class="table-row">
	                <td><%= cuenta.getIdCliente() %></td>
	                <td><%= cuenta.getIdCuenta() %></td>
	                <td><%= cuenta.getId_tipo_cuenta()==1?"Caja de ahorro":"Cuenta corriente" %></td>
	                <td><%= cuenta.getNumeroCuenta() %></td>
	                <td><%= cuenta.getCbu() %></td>
	                <td><%= cuenta.getSaldo() %></td>
	                <td>
	                    <a class="btn btn-info uniform-button" href="<%="ServletMovimientos?action=listar&idCuenta="+ cuenta.getIdCuenta() %>" class="button">Ver movimientos</a>      
	                </td>
	                <% if((int) session.getAttribute("tipoUsuario") == 1) {%>
	                 <td>
                        <button type="button" class="btn btn-danger uniform-button" onclick="eliminarCuenta(<%= cuenta.getIdCliente() %>, <%= cuenta.getIdCuenta() %>)">Eliminar</button>      
                    </td>

	                <% } %>
	            </tr>
	            <%  
	                }
	            } else { 
	            %>
	            <tr>
	                <td colspan="8" class="text-center">No hay cuentas para mostrar</td>
	            </tr>
	            <% } %>
	        </tbody>
        </table>
    </div>
    
    <form id="formEliminar" method="get" action="ServletCuentas" style="display:none;">
        <input type="hidden" name="action" value="eliminar">
        <input type="hidden" name="idCliente" id="idCliente">
        <input type="hidden" name="idCuenta" id="idCuenta">
    </form>
  
    <div class="text-center">
        <a href="<% if((int) session.getAttribute("tipoUsuario") == 1) {%>ServletClientes?action=listar<%}else{%>PrincipalCliente.jsp<%}%>" class="btn btn-secondary">Volver</a>
    </div>
</div>

<script>
    function eliminarCuenta(idCliente, idCuenta) {
        if (confirm('Estás seguro de eliminar esta cuenta?')) {
            document.getElementById('idCliente').value = idCliente;
            document.getElementById('idCuenta').value = idCuenta;
            document.getElementById('formEliminar').submit();
        }
    }
</script>

</body>
</html>

<% } else { %>
    <%@ include file="Error.jsp" %>
<% } %>
