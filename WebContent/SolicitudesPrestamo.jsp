<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="entidad.solicitudPrestamo" %>
<%@ page import="java.util.List" %>
<%@ include file="navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Listado de Solicitudes de Préstamo</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="CSS/styles.css" rel="stylesheet">
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
    <div class="container mt-4">
        <h1 class="display-4 text-center mb-4">Listado de Solicitudes de Préstamo</h1>

        <c:if test="${not empty mensaje}">
            <div class="alert alert-info">${mensaje}</div>
        </c:if>

        <%
            List<solicitudPrestamo> listaSolicitudes = null;
            if (request.getAttribute("listaSolicitudes") != null) {
                listaSolicitudes = (List<solicitudPrestamo>) request.getAttribute("listaSolicitudes");
            }
        %>

        <div class="table-responsive">
            <table class="table table-bordered table-striped" id="table_id">
                <thead class="thead-dark">
                    <tr class="table-header">
                        <th>ID Solicitud</th>
                        <th>ID Cliente</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>ID Cuenta</th>
                        <th>Fecha</th>
                        <th>Importe Pedido</th>
                        <th>Plazo en Meses</th>
                        <th>Interes</th>
                        <th>Estado</th>
                        <th>Acción Aceptar</th>
                        <th>Acción Rechazar</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (listaSolicitudes != null && !listaSolicitudes.isEmpty()) {
                        for (solicitudPrestamo solicitud : listaSolicitudes) {
                            String estado = solicitud.getEstadoSolicitud();
                            boolean mostrarBotones = !estado.equals("aprobado") && !estado.equals("rechazado");
                    %>
                    <tr class="table-row">
                        <td><%= solicitud.getIdSolicitud() %></td>
                        <td><%= solicitud.getCliente().getIdCliente() %></td>
                        <td><%= solicitud.getCliente().getNombre() %></td>
                        <td><%= solicitud.getCliente().getApellido() %></td>
                        <td><%= solicitud.getCuenta().getIdCuenta() %></td>
                        <td><%= solicitud.getFecha() %></td>
                        <td>$ <%= solicitud.getImportePedido() %></td>
                        <td><%= solicitud.getPlazoMeses() %></td>
                        <td><%= solicitud.getTasaInteres() %> %</td>
                        <td><%= solicitud.getEstadoSolicitud() %></td>
                        <% if (mostrarBotones) { %>
                        <td>
                            <form method="post" action="ServletPrestamo">
                                <input type="hidden" name="idSolicitud" value="<%= solicitud.getIdSolicitud() %>">
                                <input type="hidden" name="idCliente" value="<%= solicitud.getCliente().getIdCliente() %>">
                                <input type="hidden" name="idCuenta" value="<%= solicitud.getCuenta().getIdCuenta() %>">
                                <input type="hidden" name="fecha" value="<%= solicitud.getFecha() %>">
                                <input type="hidden" name="importePedido" value="<%= solicitud.getImportePedido() %>">
                                <input type="hidden" name="plazoMeses" value="<%= solicitud.getPlazoMeses() %>">
                                <input type="hidden" name="TasaInteres" value="<%= solicitud.getTasaInteres() %>">
                                <input type="hidden" name="action" value="aceptar">
                                <button type="submit" class="btn btn-success">Aceptar</button>
                            </form>
                        </td>
                        <td>
                            <form method="post" action="ServletPrestamo">
                                <input type="hidden" name="idSolicitud" value="<%= solicitud.getIdSolicitud() %>">
                                <input type="hidden" name="idCliente" value="<%= solicitud.getCliente().getIdCliente() %>">
                                <input type="hidden" name="importePedido" value="<%= solicitud.getImportePedido() %>">
                                <input type="hidden" name="action" value="rechazar">
                                <button type="submit" class="btn btn-danger">Rechazar</button>
                            </form>
                        </td>
                        <% } else { %>
                        <td>-</td>
                        <td>-</td>
                        <% } %>
                    </tr>
                    <% } } else { %>
                    <tr>
                        <td colspan="11" class="text-center">No hay solicitudes de préstamo registradas.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>

            <div class="text-center">
                <form method="get" action="Principal.jsp" style="display: inline-block; margin-bottom:50px;">
                    <button type="submit" class="btn btn-secondary mt-3">Volver</button>
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
            </div>
        </div>
    </div>
</body>
</html>