<%@page import="entidad.cliente" %>
<%@page import="entidad.direccion" %>
<%@page import="entidad.localidad" %>
<%@page import="entidad.provincia" %>
<%@page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="navbar.jsp" %>
<% if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 1) { %>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listado de Clientes</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link href="CSS/styles.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $.fn.dataTable.ext.errMode = 'none';
        $('#table_id').DataTable();
    });
</script>
</head>
<body>
        
<form method="get" action="ServletClientes">
<div class="container mt-4">
    <h1 class="display-4 text-center mb-4">Listado de Clientes</h1>
    <% 
    ArrayList<cliente> listaClientes = null;
    if(request.getAttribute("listarClientes") != null) {
        listaClientes = (ArrayList<cliente>) request.getAttribute("listarClientes");
    }
    %>
    
    <div class="table-responsive">
        <table class="table table-bordered table-striped" id="table_id">
            <thead class="thead-dark">
                <tr class="table-header">
                    <th>ID Cliente</th>
                    <th>ID Usuario</th>
                    <th>DNI</th>
                    <th>CUIL</th>
                    <th>Apellido</th>
                    <th>Nombre</th>
                    <th>Sexo</th>
                    <th>Nacionalidad</th>
                    <th>Fecha de Nacimiento</th>
                    <th>Direccion</th>
                    <th>Localidad</th>
                    <th>Provincia</th>
                    <th>Correo</th>
                    <th>Telefono</th>
                    <th>Estado</th>
                    <th>Eliminar</th> 
                    <th>Modificar</th>
                    <th>Ver Cuentas</th>
                    <th>Agregar Cuenta</th>
                </tr>
            </thead>
            <tbody>
                <% if(listaClientes != null && !listaClientes.isEmpty()) {
                    for (cliente cliente : listaClientes) {
                        if (cliente.isEstado()) {
                %>
                <tr class="table-row">
                    <td><%=cliente.getIdCliente() %></td>
                    <td><%=cliente.getIdUsuario() %></td>
                    <td><%=cliente.getDni() %></td>
                    <td><%=cliente.getCuil() %></td>
                    <td><%=cliente.getApellido() %></td>
                    <td><%=cliente.getNombre() %></td>
                    <td><%=cliente.getSexo() %></td>
                    <td><%=cliente.getNacionalidad() %></td>
                    <td><%=cliente.getFechaNacimiento() %></td>
                    <td><%=cliente.getDireccion().getCalle() %> <%=cliente.getDireccion().getNumero() %></td>
                    <td><%=cliente.getDireccion().getLocalidad().getNombre() %></td>
                    <td><%=cliente.getDireccion().getLocalidad().getProvincia().getNombre() %></td>
                    <td><%=cliente.getCorreoElectronico() %></td>
                    <td><%=cliente.getTelefono() %></td>
                    <td>
                        <% if(cliente.isEstado()) { %>
                            ACTIVO
                        <% } else { %>
                            INACTIVO
                        <% } %>
                    </td>
                    <td>
                        <form method="get" action="ServletClientes">
                            <input type="hidden" name="action" value="eliminar">
                            <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">
                            <button type="submit" class="btn btn-danger uniform-button" onclick="return confirm('¿Estás seguro de eliminar este cliente?')">Eliminar</button>      
                        </form>
                    </td>
                    <td>
                        <form method="get" action="ServletClientes">
                            <input type="hidden" name="action" value="modificar">
                            <input type="hidden" name="idUsuario" value="<%= cliente.getIdUsuario() %>">
                            <button type="submit" class="btn btn-secondary uniform-button" onclick="return confirm('¿Estás seguro de modificar este cliente?')">Modificar</button>      
                        </form>
                    </td>
                    <td>
                        <form method="get" action="ServletCuentas">
                        <input type="hidden" name="action" value="listar">	
                            <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">
                            <button type="submit" class="btn btn-info uniform-button">Ver Cuentas</button>
                        </form>
                    </td>
                    <td>
                        <form method="get" action="InsertarCuenta.jsp">
                       	 <input type="hidden" name="action" value="insertar">	
                            <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">
                            <button type="submit" class="btn btn-success uniform-button">Agregar Cuenta</button>
                        </form>
                    </td>
                </tr>
                <%  
                        }
                    }
                } else { 
                %>
                <tr>
                    <td colspan="18" class="text-center">No hay clientes para mostrar</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
    
   <div class="container mb-5">
    <div class="row">
        <div class="col-sm-6 mt-4"> 
            <form method="get" action="ServletClientes">
                <input type="hidden" name="action" value="listar">
                <button type="submit" class="btn btn-primary btn-block">Listar</button>
            </form>
        </div>
        <div class="col-sm-6 mt-4">
            <a href="Principal.jsp" class="btn btn-secondary btn-block">Volver</a>
        </div>
    </div>
</div>


 <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <p class="text-danger mt-4"><%= error %></p>
        <% } %>

        <% Boolean band = (Boolean) request.getAttribute("bandera"); %>
        <% if (band != null) { %>
            <div class="mt-4 alert <%= band ? "alert-success" : "alert-danger" %> alert-dismissible fade show" role="alert">
                <%= band ? " Operación realizada correctamente!" : " No se pudo realizar la operación!" %>
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