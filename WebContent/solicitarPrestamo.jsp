<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="navbar.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidad.cuenta" %>

<%
    if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 2) {
        ArrayList<cuenta> cuentas = (ArrayList<cuenta>) session.getAttribute("cuentas");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Solicitar Préstamo</title>
<link href="CSS//styles.css" rel="stylesheet">
</head>
<body>
    <div class="container custom-container">
        <h2>Solicitar Préstamo</h2>
        <p>ID Cliente: <%= request.getSession().getAttribute("idCliente") %></p>

        <form method="post" action="ServletSolicitudPrestamo">
            <label for="importePedido">Importe solicitado</label>
            <input type="number" step="0.01" id="importePedido" name="importePedido" min="0.01" required>

            <label for="plazoMeses">Plazo (en meses)</label>
            <select id="plazoMeses" name="plazoMeses" required>
                <option value="3">3 meses 30% interes</option>
                <option value="6">6 meses 50% interes</option>
                <option value="12">12 meses 100% interes</option>
            </select>

            <label for="idCuenta">Cuenta</label>
            <select id="idCuenta" name="idCuenta" required>
            	<option value ="">
                <% 
                    if (cuentas != null && !cuentas.isEmpty()) {
                        for (cuenta c : cuentas) {
                %>
                        <option value="<%= c.getIdCuenta() %>">Número: <%= c.getId_tipo_cuenta() %>, CBU: <%= c.getCbu() %></option>
                <%
                        }
                    } else {
                %>
                        <option value="">No hay cuentas disponibles</option>
                <%
                    }
                %>
            </select>

            <button type="submit" name="btnAceptar" class="btn btn-success uniform-button">Solicitar</button>
            <a href="PrincipalCliente.jsp" class="btn btn-secondary uniform-button">Volver</a>
        </form>
        
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
</body>
</html>
<% } else { %>
    <%@ include file="Error.jsp" %>
<% } %>
