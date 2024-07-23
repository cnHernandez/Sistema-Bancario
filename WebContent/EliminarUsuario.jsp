<%@page import="entidad.usuario"%>
<%@page import="entidad.cliente"%>
<%@page import="java.util.ArrayList"%>
<%@ include file="navbar.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 1) { %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Buscar Cliente</title>
<link href="CSS//styles.css" rel="stylesheet">
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.datatables.net/2.0.8/css/dataTables.dataTables.css" />
<script src="https://cdn.datatables.net/2.0.8/js/dataTables.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    $.fn.dataTable.ext.errMode = 'none';
    $('#table_clientes').DataTable({
    });
    $('#table_administradores').DataTable({
    });

});

</script>
</head>
<body>

<div class="container mt-4">
    <h2 class="display-4 text-center mb-4">Usuario cliente</h2>
    <form action="ServletUsuario" method="get" class="mb-4">
        <div class="form-group">
            <label for="dniCliente">Ingrese el número de DNI del cliente:</label>
            <input type="text" class="form-control" id="dniCliente" name="dniCliente">
        </div>
        <button type="submit" name="btnBuscarParaBaja" value="Buscar" class="btn btn-primary">Buscar</button>
    </form>
    

    <div class="table-responsive notificacion">
        <table class="table table-bordered table-striped" id="table_clientes">
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
                    <th>Nombre de usuario</th>
                    <th>Contraseña</th>
                    <th>Eliminar</th>
                    <th>Dar alta</th>
                    <th>Modificar</th>
                </tr>
            </thead>
            <tbody>
                <% 
                usuario user = (usuario) request.getAttribute("usuario");
                cliente cliente = (cliente) request.getAttribute("cliente");

                if (cliente != null) {
                %>
                    <tr class="table-row">
                        <td><%= cliente.getIdCliente() %></td>
                        <td><%= cliente.getIdUsuario() %></td>
                        <td><%= cliente.getDni() %></td>
                        <td><%= cliente.getCuil() %></td>
                        <td><%= cliente.getApellido() %></td>
                        <td><%= cliente.getNombre() %></td>
                        <td><%= cliente.getSexo() %></td>
                        <td><%= cliente.getNacionalidad() %></td>
                        <td><%= cliente.getFechaNacimiento() %></td>
                        <td><%=cliente.getDireccion().getCalle() %> <%=cliente.getDireccion().getNumero() %></td>
                    	<td><%=cliente.getDireccion().getLocalidad().getNombre() %></td>
                    	<td><%=cliente.getDireccion().getLocalidad().getProvincia().getNombre() %></td>
                        <td><%= cliente.getCorreoElectronico() %></td>
                        <td><%= cliente.getTelefono() %></td>
                        <td>
                            <% if (user.getEstado()) { %>
                                ACTIVO
                            <% } else { %>
                                INACTIVO
                            <% } %>
                        </td>
                        <% if (user != null) { %>
                            <td><%= user.getNombre() %></td>
                        <% } else { %>
                            <td>Usuario no encontrado</td>
                        <% } %>
                        <td><%= user.getContraseña()%></td>
                        <td>
                            <form method="get" action="ServletUsuario">
                                <input type="hidden" name="action" value="eliminar">
                                <input type="hidden" name="idUsuario" value="<%= cliente.getIdUsuario() %>">
                                <button type="submit" class="btn btn-danger uniform-button" onclick="return confirm('¿Estás seguro de eliminar este cliente?')">Eliminar</button>      
                            </form>
                        </td>
                        <td>
                            <form method="get" action="ServletUsuario">
                                <input type="hidden" name="action" value="activar">
                                <input type="hidden" name="idUsuario" value="<%= cliente.getIdUsuario() %>">
                                <button type="submit" class="btn btn-success uniform-button" onclick="return confirm('¿Estás seguro de dar de alta este cliente?')">Dar alta</button>      
                            </form>
                        </td>
                        <td>
                            <button type="button" class="btn btn-secondary uniform-button" onclick="showPasswordInputs('<%= cliente.getIdUsuario()%>')">Modificar</button>
                        </td>
                    </tr>
                    <tr id="passwordInputs<%= cliente.getIdUsuario() %>" style="display:none;">
					    <td colspan="19">
					        <form method="get" action="ServletUsuario" onsubmit="return validarFormulario();">
					            <input type="hidden" name="idUsuario" value="<%= cliente.getIdUsuario() %>">
					            <div class="form-group">
					                <label for="newPassword">Nueva Contraseña:</label>
					                <input type="password" class="form-control" id="newPassword" name="newPassword"
					                data-toggle="tooltip" title="La contraseña debe tener al menos 5 caracteres, no comenzar ni terminar con . - _, no contener caracteres repetidos consecutivamente como .. __ --, ni espacios en blanco, y las contraseñas deben coincidir" required>
					            </div>
					            <div class="form-group">
					                <label for="confirmPassword">Confirmar Nueva Contraseña:</label>
					                <input type="password" class="form-control" id="confirmPassword" name="confirmPass" required>
					            </div>
					            <button type="submit" class="btn btn-primary" name="action" value="modificar">Guardar Cambios</button>
					            <button type="button" class="btn btn-secondary" onclick="hidePasswordInputs('<%= cliente.getIdUsuario() %>')">Cancelar</button>
					        </form>
					    </td>
					</tr>
                <% } else { %>
                    <tr>
                        <td colspan="18" class="text-center">No hay clientes para mostrar</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <div class="container">
    <div class="row">
        <div class="col-sm-6 mt-4"> 
                <input type="hidden" name="action" value="listar">
                <a href="AgregarUsuario.jsp" class="btn btn-success btn-block">Agregar</a>
        </div>
        <div class="col-sm-6 mt-4">
            <a href="Principal.jsp" class="btn btn-secondary btn-block">Volver</a>
        </div>
    </div>
</div>
<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <p class="text-danger"><%= error %></p>
<% } %>

<% Boolean band = (Boolean) request.getAttribute("bandera"); %>
<% if (band != null) { %>
    <div class="mt-5 alert <%= band ? "alert-success" : "alert-danger" %> alert-dismissible fade show" role="alert">
        <%= band ? " Operación realizada correctamente!" : " No se pudo realizar la operación!" %>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
<% } %>
 <!-- Tabla de administradores -->
<div class="mt-custom notificacion">
    <h2 class="text-center">Usuarios Administradores</h2>
    <div class="table-responsive">
        <table class="table table-bordered table-striped display" id="table_administradores">
            <thead class="thead-dark">
                <tr>
                    <th>ID Usuario</th>
                    <th>Nombre de usuario</th>
                    <th>Contraseña</th>
                    <th>Estado</th>
                    <th>Eliminar</th>
                    <th>Dar alta</th>
                    <th>Modificar</th>
                </tr>
            </thead>
            <tbody>
                <% 
                ArrayList<usuario> administradores = (ArrayList<usuario>) request.getAttribute("administradores");
                if (administradores != null) {
                    for (usuario admin : administradores) {
                %>
                        <tr>
                            <td><%= admin.getId_usuario() %></td>
                            <td><%= admin.getNombre() %></td>
                            <td><%= admin.getContraseña()%></td>
                            <td>
                                <% if (admin.getEstado()) { %>
                                    ACTIVO
                                <% } else { %>
                                    INACTIVO
                                <% } %>
                            </td>
                            <td>
                                <form method="get" action="ServletUsuario">
                                    <input type="hidden" name="action" value="eliminar">
                                    <input type="hidden" name="idUsuario" value="<%= admin.getId_usuario() %>">
                                    <button type="submit" class="btn btn-danger uniform-button" onclick="return confirm('¿Estás seguro de eliminar este administrador?')">Eliminar</button>
                                </form>
                            </td>
                            <td>
                                <form method="get" action="ServletUsuario">
                                    <input type="hidden" name="action" value="activar">
                                    <input type="hidden" name="idUsuario" value="<%= admin.getId_usuario() %>">
                                    <button type="submit" class="btn btn-success uniform-button" onclick="return confirm('¿Estás seguro de dar de alta este administrador?')">Dar alta</button>
                                </form>
                            </td>
                            <td>
                                <button type="button" class="btn btn-secondary uniform-button" onclick="showPasswordInputs('<%= admin.getId_usuario() %>')">Modificar</button>
                            </td>
                        </tr>
                        <tr id="passwordInputs<%= admin.getId_usuario() %>" style="display:none;">
                            <td colspan="7">
                                <form method="get" action="ServletUsuario" onsubmit="return validarFormulario();">
                                    <input type="hidden" name="idUsuario" value="<%= admin.getId_usuario() %>">
                                    <div class="form-group">
                                        <label for="newPassword">Nueva Contraseña:</label>
                                        <input type="password" class="form-control" id="newPassword" name="newPassword" 
										data-toggle="tooltip" title="La contraseña debe tener al menos 5 caracteres, no comenzar ni terminar con . - _, no contener caracteres repetidos consecutivamente como .. __ --, ni espacios en blanco, y las contraseñas deben coincidir" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="confirmPassword">Confirmar Nueva Contraseña:</label>
                                        <input type="password" class="form-control" id="confirmPassword" name="confirmPass" required>
                                    </div>
                                    <button type="submit" class="btn btn-primary" name="action" value="modificar">Guardar Cambios</button>
                                    <button type="button" class="btn btn-secondary" onclick="hidePasswordInputs('<%= admin.getId_usuario() %>')">Cancelar</button>
                                </form>
                            </td>
                        </tr>
                <% 
                    }
                } else {
                %>
                    <tr>
                        <td colspan="7" class="text-center">No hay administradores para mostrar</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>


<div class="container mb-custom">
    <div class="row">
        <div class="col-sm-6 mt-4"> 
            <form method="get" action="ServletUsuario">
                <input type="hidden" name="action" value="listarTodos">
                <div class="text-center"> 
                    <button type="submit" class="btn btn-primary btn-block">Listar</button>
                </div>
            </form>
        </div>
    </div>
</div>

  </div>
<% } else { 
	response.sendRedirect("Error.jsp");
	} %>

<script>

function showPasswordInputs(idUsuario) {
    document.getElementById('passwordInputs' + idUsuario).style.display = 'table-row';
}

function hidePasswordInputs(idUsuario) {
    document.getElementById('passwordInputs' + idUsuario).style.display = 'none';
}

function validarFormulario() {
    var password = document.getElementById("newPassword").value;
    var confirmPassword = document.getElementById("confirmPassword").value;

    // Comprobar si las contraseñas coinciden
    if (password !== confirmPassword) {
        swal("Error", "Las contraseñas no coinciden.", "error");
        return false;
    }

    // Longitud mínima
    if (password.length < 5) {
        swal("Error", "La contraseña debe tener al menos 5 caracteres.", "error");
        return false;
    }

    // No comenzar ni terminar con caracteres especiales
    if (password.startsWith(".") || password.startsWith("-") || password.startsWith("_") ||
        password.endsWith(".") || password.endsWith("-") || password.endsWith("_")) {
        swal("Error", "La contraseña no debe comenzar ni terminar con ., -, _.", "error");
        return false;
    }

    // No caracteres repetidos consecutivamente
    if (password.includes("..") || password.includes("__") || password.includes("--")) {
        swal("Error", "La contraseña no debe contener caracteres repetidos consecutivamente como .., __, --.", "error");
        return false;
    }

    // No espacios en blanco
    if (password.includes(" ")) {
        swal("Error", "La contraseña no debe contener espacios en blanco.", "error");
        return false;
    }

    return true;
}

</script>
</body>
</html>

