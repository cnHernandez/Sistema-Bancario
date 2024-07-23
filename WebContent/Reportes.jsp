<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="entidad.prestamo" %>
    <%@ page import="entidad.movimiento" %>
<%@ page import="java.util.ArrayList" %>
<%@page import="java.math.BigDecimal"%>
    <%@ include file="navbar.jsp" %>
    <% if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 1) { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="CSS////styles.css" rel="stylesheet">
</head>
<body>
<%
    ArrayList<movimiento> movimientos = (ArrayList<movimiento>) session.getAttribute("movimientos");
    BigDecimal altaCuentaTotal = BigDecimal.ZERO;
    BigDecimal altaPrestamoTotal = BigDecimal.ZERO;
    BigDecimal pagoPrestamoTotal = BigDecimal.ZERO;
    BigDecimal transferenciaTotal = BigDecimal.ZERO;

    if (movimientos != null) {
        for (movimiento mov : movimientos) {
            if (mov.getId_tipo_movimiento() == 1) {
                altaCuentaTotal = altaCuentaTotal.add(mov.getImporte());
            } else if (mov.getId_tipo_movimiento() == 2) {
                altaPrestamoTotal = altaPrestamoTotal.add(mov.getImporte());
            } else if (mov.getId_tipo_movimiento() == 3) {
                pagoPrestamoTotal = pagoPrestamoTotal.add(mov.getImporte());
            } else if (mov.getId_tipo_movimiento() == 4) {
                transferenciaTotal = transferenciaTotal.add(mov.getImporte());
            }
        }
    }
    BigDecimal balance = pagoPrestamoTotal.subtract(altaPrestamoTotal).subtract(altaCuentaTotal);
%>

<div class="container">
    <h2 class="mt-4 mb-4 h2-reportes">Total discrminado por tipo de movimiento</h2>
    <div class="row">
        <div class="col-lg-3 col-md-6 mb-4">
            <div class="card cardReporte shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">Alta de cuenta</h5>
                    <p class="card-text">$ - <%= altaCuentaTotal %></p>
                </div>
            </div>
        </div>
        <div class="col-lg-3 col-md-6 mb-4">
            <div class="card cardReporte shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">Alta de préstamo</h5>
                    <p class="card-text">$ - <%= altaPrestamoTotal %></p>
                </div>
            </div>
        </div>
        <div class="col-lg-3 col-md-6 mb-4">
            <div class="card cardReporte shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">Pago de préstamo</h5>
                    <p class="card-text">$ + <%= pagoPrestamoTotal %></p>
                </div>
            </div>
        </div>
        <div class="col-lg-3 col-md-6 mb-4">
            <div class="card cardReporte shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">Transferencia</h5>
                    <p class="card-text">$ <%= transferenciaTotal %></p>
                </div>
            </div>
        </div>
    </div>
</div>
<br>
<br>
<section class="reporte-2 notificacion">
<div class="container">
    <h2 class="mt-4 mb-4 h2-reportes">Balances (Cobros de prestamos - altas de cuentas - prestamos otorgados)</h2>
    <form method="get" action="ServletReportes">
        <div class="form-group">
            <label for="fechaInicioBalance">Fecha de Inicio:</label>
            <input type="date" id="fechaInicioBalance" name="fechaInicioBalance" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="fechaFinBalance">Fecha de Fin:</label>
            <input type="date" id="fechaFinBalance" name="fechaFinBalance" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Generar Balance</button>
    </form>

    <%-- Aquí se mostrará el resultado del balance --%>
    <%
        if (request.getAttribute("balance") != null) {
            BigDecimal balanceCalculado = (BigDecimal) request.getAttribute("balance");
    %>
        <h3 class="mt-4 mb-4 h3-reportes">Balance: $ <%= balanceCalculado %></h3>
    <% } %>
</div>
</section>
 <br> 
 <br>
 <div class="container">
    <h2 class="mt-4 mb-4 h2-reportes">Intereses Generados en Préstamos</h2>
    <form method="get" action="ServletReportes">
        <div class="form-group">
            <label for="fechaInicioIntereses">Fecha de Inicio:</label>
            <input type="date" id="fechaInicioIntereses" name="fechaInicioIntereses" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="fechaFinIntereses">Fecha de Fin:</label>
            <input type="date" id="fechaFinIntereses" name="fechaFinIntereses" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Generar Reporte de Intereses</button>
    </form>

    <%-- Aquí se mostrará el resultado de los intereses generados --%>
    <% if (request.getAttribute("interesesGenerados") != null) { %>
        <div id="reporteInteresesResultado">
            <h3 class="mt-4 mb-4 h3-reportes">Intereses Generados: $ <%= request.getAttribute("interesesGenerados") %></h3>
        </div>
    <% } %>
</div>
 <br> 
 <br>
 <section class="reporte-2 notificacion mb-5">
    <div class="container">
        <h2 class="mt-4 mb-4 h2-reportes">Reporte de Préstamos Otorgados</h2>
        <form method="get" action="ServletReportes">
            <div class="form-group">
                <label for="fechaInicio">Fecha de Inicio:</label>
                <input type="date" id="fechaInicio" name="fechaInicio" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="fechaFin">Fecha de Fin:</label>
                <input type="date" id="fechaFin" name="fechaFin" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary">Generar Reporte</button>
           
        
        </form>
        <br>
        <br>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
        <% } %>

        <% if (request.getAttribute("prestamos") != null) { %>
            <div id="reporteResultado">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Fecha</th>
                            <th>Importe Total</th>
                            <th>Plazo Meses</th>
                            <th>Total: </th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            ArrayList<prestamo> prestamos = (ArrayList<prestamo>) request.getAttribute("prestamos");
                            double total = 0;
                            for (prestamo prestamo : prestamos) {
                            	total += prestamo.getImporteTotal();
                        %>
                            <tr>
                                <td><%= prestamo.getFecha() %></td>
                                <td><%= prestamo.getImporteTotal() %></td>
                                <td><%= prestamo.getPlazoMeses() %></td>
                            
                        <% } %>
                        <td><%= total%></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        <% } %>
    </div>
    </section>
</body>
</html>

<% } else { %>
    <%@ include file="Error.jsp" %>
<% } %>