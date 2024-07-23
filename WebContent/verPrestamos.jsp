<%@ include file="navbar.jsp" %>
<%@ page import="entidad.prestamo" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Préstamos</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#table_id').DataTable();
        });
    </script>
</head>
<body>
    <div class="container mt-4">
        <h1 class="display-4 text-center mb-4">Listado de Préstamos</h1>
        
        <c:if test="${not empty mensaje}">
            <div class="alert alert-info">${mensaje}</div>
        </c:if>

        <%
            List<prestamo> listaPrestamos = (List<prestamo>) request.getAttribute("listaPrestamos");
        %>
        
        <div class="table-responsive">
            <table class="table table-bordered table-striped" id="table_id">
                <thead class="thead-dark">
                    <tr class="table-header">
                        <th>ID Préstamo</th>
                        <th>Fecha</th>
                        <th>Importe total</th>
                        <th>Importe pedido</th>
                        <th>Plazo en Meses</th>
                        <th>Monto de Cuota</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (listaPrestamos != null && !listaPrestamos.isEmpty()) {
                        for (prestamo prestamo : listaPrestamos) {
                    %>
                    <tr class="table-row">
                        <td><%= prestamo.getIdPrestamo() %></td>
                        <td><%= prestamo.getFecha() %></td>
                        <td>$ <%= prestamo.getImporteTotal() %></td>
                        <td>$ <%= prestamo.getImportePedido() %></td>
                        <td><%= prestamo.getPlazoMeses() %></td>
                        <td>$ <%= prestamo.getMontoCuota() %></td>
                        <td>
                            <% if (prestamo.isAutorizado()) { %>
                                No Acreditado
                            <% } else { %>
                                Acreditado
                            <% } %>
                        </td>
                        <td>
                            <form action="ServletCuota" method="get">
                                <input type="hidden" name="action" value="verCuotas">
                                <input type="hidden" name="idPrestamo" value="<%= prestamo.getIdPrestamo() %>">
                                <button type="submit" class="btn btn-primary">Ver Cuotas</button>
                            </form>
                        </td>
                    </tr>
                    <% } } else { %>
                    <tr>
                        <td colspan="7" class="text-center">No hay préstamos registrados.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            
            <div class="text-center">
                <a href="PrincipalCliente.jsp" class="btn btn-secondary">Volver</a>
            </div>
        </div>
    </div>
</body>
</html>

