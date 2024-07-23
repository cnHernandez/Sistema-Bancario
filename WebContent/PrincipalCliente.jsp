<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="navbar.jsp" %>
<%@ page import="entidad.cliente" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidad.cuenta" %>
<%@ page import="java.text.DecimalFormat" %>

<% if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 2) { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="CSS//styles.css" rel="stylesheet">
</head>
<body>
<%
    Integer idCliente = (Integer) session.getAttribute("idCliente");
    String link = "ServletCuentas?action=listar";
    if (idCliente != null) {
        link += "&idCliente=" + idCliente;
    }
%>

<div class="container custom-container-principal">
<div class="cards">
    <h2 class="header">
        <img src="https://cdn-icons-png.flaticon.com/128/5084/5084224.png" alt="Descripción de la imagen">¡Bienvenido cliente!
    </h2>
    <div class="cuentas">
        <%
            ArrayList<cuenta> cuentas = (ArrayList<cuenta>) session.getAttribute("cuentas");
            if (cuentas != null) {
                DecimalFormat df = new DecimalFormat("#,###.00");
                for (int i = 0; i < cuentas.size(); i++) {
                    cuenta c = cuentas.get(i);
                    String tipoCuenta;
                    switch (c.getId_tipo_cuenta()) {
                        case 1:
                            tipoCuenta = "Caja de Ahorro";
                            break;
                        case 2:
                            tipoCuenta = "Cuenta Corriente";
                            break;
                        default:
                            tipoCuenta = "Tipo de Cuenta Desconocido";
                    }
        %>
                    <div class="content content-<%= (i % 3) + 1 %>">
                        <div class="fab fa-bank"></div> 
                        <h2>
                            <%= tipoCuenta %>
                        </h2>
                        <p>
                            Número de Cuenta: <%= c.getNumeroCuenta() %><br>
                            CBU: <%= c.getCbu() %><br>
                            Saldo: <%= df.format(c.getSaldo()) %>
                        </p>
                        <a href="ServletMovimientos?action=listar&idCuenta=<%= c.getIdCuenta() %>" class="button">Ver más</a>
                    </div>
        <%
                }
            } else {
        %>
                <p>No se encontraron cuentas para este cliente.</p>
        <%
            }
        %>
    </div>
</div>
</div>
<form method="get" action="ServletCuentas">
<div class="container custom-container-principal">
    <img src="https://cdn-icons-png.flaticon.com/128/8774/8774909.png" alt="Descripción de la imagen">
    <h1>¡Gestiona tus cuentas y pide prestamos!</h1>
    <div class="button-group">
        <a href="<%= link %>" class="button">Ver mis cuentas</a>
        <a href="solicitarPrestamo.jsp" class="button">Solicitar Préstamo</a>
        <a href="listarPrestamos?" class="button">Ver mis préstamos</a>
        <a href="Transferencia.jsp" class="button">Transferencia</a>
    </div>
</div>
</form>
</body>
</html>

<% } else { %>
    <%@ include file="Error.jsp" %>
<% } %>
