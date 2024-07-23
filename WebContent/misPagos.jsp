<%@ include file="navbar.jsp" %>
<%@ page import="entidad.pago" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Pagos</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $.fn.dataTable.ext.errMode = 'none';
            $('#table_id').DataTable();
        });
    </script>
</head>
<body>
    <div class="container mt-4">
        <h1 class="display-4 text-center mb-4">Listado de Pagos</h1>
        
        <c:if test="${not empty mensaje}">
            <div class="alert alert-info">${mensaje}</div>
        </c:if>

        <%
            List<pago> listaPagos = (List<pago>) request.getAttribute("listaPagos");
        %>
        
        <div class="table-responsive">
            <table class="table table-bordered table-striped" id="table_id">
                <thead class="thead-dark">
                    <tr class="table-header">
                        <th>ID Pago</th>
                        <th>ID Cuota</th>
                        <th>ID Cuenta</th>
                        <th>Fecha de Pago</th>
                        <th>Monto</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (listaPagos != null && !listaPagos.isEmpty()) {
                        for (pago pago : listaPagos) {
                    %>
                    <tr class="table-row">
                        <td><%= pago.getIdPago() %></td>
                        <td><%= pago.getIdCuota().getIdCuota() %></td>
                        <td><%= pago.getIdCuenta().getIdCuenta() %></td>
                        <td><%= pago.getFechaPago() %></td>
                        <td><%= pago.getMonto() %></td>
                    </tr>
                    <% } } else { %>
                    <tr>
                        <td colspan="5" class="text-center">No hay pagos registrados.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            
            <div class="text-center mb-4">
                <a href="PrincipalCliente.jsp" class="btn btn-secondary">Volver</a>
            </div>
        </div>
    </div>
</body>
</html>