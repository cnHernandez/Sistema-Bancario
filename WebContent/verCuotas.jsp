<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@ page import="entidad.cuotas" %>
<%@ page import="java.util.List" %>
<%@ page import="entidad.cuenta" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ include file="navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Cuotas por Préstamo</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/2.0.8/css/dataTables.dataTables.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/2.0.8/js/dataTables.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#table_id').DataTable();
        });
    </script>
</head>
<body>

   <%-- Mostrar mensaje de error si existe --%>
    <% String mensajeError = (String) request.getAttribute("mensajeError"); %>
    <% if (mensajeError != null) { %>
        <div class="alert alert-danger" role="alert">
            <%= mensajeError %>
        </div>
    <% } %>

<div class="mb-3">
    <%-- Mostrar el ID de cuenta obtenido de la sesión --%>
    <% 
    ArrayList<cuenta> cuentas = (ArrayList<cuenta>) session.getAttribute("cuentas");
    %>
</div>

<div class="container mt-4">
    <h2>Listado de Cuotas por Préstamo</h2>

    <div class="table-responsive">
        <table class="table table-bordered table-striped" id="table_id">
            <thead class="thead-dark">
                <tr class="table-header">
                    <th>ID Cuota</th>
                    <th>Fecha de Vencimiento</th>
                    <th>Monto</th>
                    <th>Estado</th>
                    <th>Pagar</th> <!-- Agregar botón para pagar si es necesario -->
                </tr>
            </thead>
            <tbody>
                <% List<cuotas> listaCuotas = (List<cuotas>) request.getAttribute("listaCuotas"); %>
                <% if(listaCuotas != null && !listaCuotas.isEmpty()) {
                    for (cuotas cuotas : listaCuotas) {
                %>
                <tr class="table-row">
                    <td><%= cuotas.getIdCuota() %></td>
                    <td><%= cuotas.getFechaVencimiento() %></td>
                    <td>$ <%= cuotas.getMonto() %></td>
                    <td>
                        <% if (cuotas.isPagada()) { %>
                            Paga
                        <% } else { %>
                            Impaga
                        <% } %>
                    </td>
                    <td>
                        <% if (!cuotas.isPagada()) { %>
                            <form action="ServletCuota" method="post">
                                <input type="hidden" name="action" value="pagar">
                                <input type="hidden" name="idCuota" value="<%= cuotas.getIdCuota() %>">
                                <input type="hidden" name="monto" value="<%= cuotas.getMonto() %>">
                                <input type="hidden" name="idPrestamo" value="<%= cuotas.getIdPrestamo() %>">
                                
                                <div class="form-group">
                                    <label for="idCuenta">Seleccionar Cuenta:</label>
                                    <select name="idCuenta" class="form-control" required>
                                        <% for (cuenta cuenta : cuentas) { %>
                                            <option value="<%= cuenta.getIdCuenta() %>">
                                                <%= cuenta.getIdCuenta() %> - Saldo: $<%= cuenta.getSaldo() %>
                                            </option>
                                        <% } %>
                                    </select>
                                </div>
                                
                                <button type="submit" class="btn btn-primary">Pagar</button>
                            </form>
                        <% } else { %>
                            <button type="button" class="btn btn-success" disabled>Pagado</button>
                        <% } %>
                    </td>
                </tr>
                <% }
                    } else { %>
                <tr>
                    <td colspan="5">No se encontraron cuotas.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <div class="text-center mt-4">
        <a href="listarPrestamos?" class="btn btn-secondary">Volver a Préstamos</a>
    </div>
</div>

</body>
</html>