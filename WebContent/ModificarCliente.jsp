<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="navbar.jsp" %>
<%@ page import="entidad.cliente" %>
<%@page import="entidad.direccion" %>
<%@page import="entidad.localidad" %>
<%@page import="entidad.provincia" %>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title><%= request.getAttribute("cliente") != null ? "Modificar Cliente" : "Agregar Cliente" %></title>
     <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script>
        function actualizarLocalidades() {
            var provinciaSelect = document.getElementById("provincia");
            var localidadSelect = document.getElementById("localidad");
            var selectedProvincia = provinciaSelect.value;

            for (var i = 0; i < localidadSelect.options.length; i++) {
                var option = localidadSelect.options[i];
                option.style.display = option.getAttribute("data-provincia") == selectedProvincia ? "block" : "none";
            }

          
            localidadSelect.value = "";
        }

        function validarFormulario() {
            var nombre = document.getElementById("nombre").value;
            var apellido = document.getElementById("apellido").value;
            var nacionalidad = document.getElementById("nacionalidad").value;
            var calle = document.getElementById("direccionCalle").value;
            var numero = document.getElementById("direccionNumero").value;

            var soloLetras = /^[a-zA-Z]+$/;
            var calleLetras = /^[a-zA-Z\s.]+$/;
            var soloNumeros = /^[0-9]+$/;

            if (!soloLetras.test(nombre)) {
                swal("Error", "El nombre debe contener solo letras.", "error");
                return false;
            }

            if (!soloLetras.test(apellido)) {
                swal("Error", "El apellido debe contener solo letras.", "error");
                return false;
            }

            if (!soloLetras.test(nacionalidad)) {
                swal("Error", "La nacionalidad debe contener solo letras.", "error");
                return false;
            }

            if (!calleLetras.test(calle)) {
                swal("Error", "La calle debe contener solo letras.", "error");
                return false;
            }

            if (!soloNumeros.test(numero)) {
                swal("Error", "El número debe contener solo números.", "error");
                return false;
            }

            return true;
        }
        
    </script>
</head>
<body>
    <div class="container">
        <h1 class="display-4 text-center my-4"><%= request.getAttribute("cliente") != null ? "Modificar Cliente" : "Agregar Cliente" %></h1>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <p class="text-danger"><%= error %></p>
        <% } %>

        <% Boolean band = (Boolean) request.getAttribute("bandera"); %>
        <% if (band != null) { %>
            <div class="alert <%= band ? "alert-success" : "alert-danger" %> alert-dismissible fade show" role="alert">
                <%= band ? " Operación realizada correctamente!" : " No se pudo realizar la operación!" %>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        <% } %>

        <form action="ServletClientes" method="post" onsubmit="return validarFormulario()">
            <input type="hidden" name="esModificacion" value="<%= request.getAttribute("cliente") != null %>">
            <% cliente cliente = (cliente) request.getAttribute("cliente"); %>
            <div class="mb-3">
                <label for="idUsuario" class="form-label">ID Usuario</label>
                <input type="text" class="form-control" id="idUsuario" name="idUsuario" value="<%= cliente != null ? cliente.getIdUsuario() : "" %>" readonly>
            </div>
            <div class="mb-3">
                <label for="idCliente" class="form-label">ID Cliente</label>
                <input type="text" class="form-control" id="idCliente" name="idCliente" value="<%= cliente != null ? cliente.getIdCliente() : "" %>" readonly>
            </div>
            <div class="mb-3">
                <label for="dni" class="form-label">DNI</label>
                <input type="number" class="form-control" id="dni" name="dni" value="<%= cliente != null ? cliente.getDni() : "" %>" <%= (int) session.getAttribute("tipoUsuario") != 1 && cliente != null ? "readonly" : "required" %>>
            </div>
            <div class="mb-3">
                <label for="cuil" class="form-label">CUIL</label>
                <input type="number" class="form-control" id="cuil" name="cuil" value="<%= cliente != null ? cliente.getCuil() : "" %>" <%= (int) session.getAttribute("tipoUsuario") != 1 && cliente != null ? "readonly" : "required" %>>
            </div>
            <div class="form-group">
                <label for="nombre">Nombre</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="<%= cliente != null ? cliente.getNombre() : "" %>" <%= (int) session.getAttribute("tipoUsuario") != 1 && cliente != null ? "readonly" : "required" %>>
            </div>
            <div class="mb-3">
                <label for="apellido" class="form-label">Apellido</label>
                <input type="text" class="form-control" id="apellido" name="apellido" value="<%= cliente != null ? cliente.getApellido() : "" %>" <%= (int) session.getAttribute("tipoUsuario") != 1 && cliente != null ? "readonly" : "required" %>>
            </div>
            <div class="mb-3">
                <label for="sexo" class="form-label">Sexo</label>
                <select class="form-control" id="sexo" name="sexo" <%= (int) session.getAttribute("tipoUsuario") != 1 && cliente != null ? "readonly" : "required" %>>
                    <option value="M" <%= cliente != null && cliente.getSexo().equals("M") ? "selected" : "" %>>Masculino</option>
                    <option value="F" <%= cliente != null && cliente.getSexo().equals("F") ? "selected" : "" %>>Femenino</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="nacionalidad" class="form-label">Nacionalidad</label>
                <input type="text" class="form-control" id="nacionalidad" name="nacionalidad" value="<%= cliente != null ? cliente.getNacionalidad() : "" %>" <%= (int) session.getAttribute("tipoUsuario") != 1 && cliente != null ? "readonly" : "required" %>>
            </div>
            <div class="mb-3">
                <label for="fechaNacimiento" class="form-label">Fecha de Nacimiento (YYYY-MM-DD)</label>
                <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" value="<%= cliente != null ? cliente.getFechaNacimiento().toString() : "" %>" <%= (int) session.getAttribute("tipoUsuario") != 1 && cliente != null ? "readonly" : "required" %>>
            </div>
            <div class="form-group">
                <input type="hidden" id="idDireccion" name="idDireccion" value="<%= cliente != null && cliente.getDireccion() != null ? cliente.getDireccion().getIdDireccion() : "" %>">
                <label for="direccionCalle">Calle:</label>
                <input type="text" class="form-control" id="direccionCalle" name="direccionCalle" value="<%= request.getAttribute("cliente") != null ? ((cliente) request.getAttribute("cliente")).getDireccion().getCalle() : "" %>" <%= (int) session.getAttribute("tipoUsuario") != 1 && cliente != null ? "readonly" : "required" %>>
            </div>
            <div class="form-group">
                <label for="direccionNumero">Número:</label>
                <input type="text" class="form-control" id="direccionNumero" name="direccionNumero" value="<%= request.getAttribute("cliente") != null ? ((cliente) request.getAttribute("cliente")).getDireccion().getNumero() : "" %>" <%= (int) session.getAttribute("tipoUsuario") != 1 && cliente != null ? "readonly" : "required" %>>
            </div>
            <div class="form-group">
                <label for="provincia">Provincia:</label>
                <select class="form-control" id="provincia" name="provincia" onchange="actualizarLocalidades()" <%= (int) session.getAttribute("tipoUsuario") != 1 && cliente != null ? "readonly" : "required" %>>
                    <option value="">Seleccionar</option>
                    <% 
                    ArrayList<provincia> provincias = (ArrayList<provincia>) request.getAttribute("provincias");
                    if (provincias != null) {
                        for (provincia provincia : provincias) {
                    %>
                        <option value="<%= provincia.getIdProvincia() %>" <%= cliente != null && cliente.getDireccion() != null && cliente.getDireccion().getLocalidad() != null && cliente.getDireccion().getLocalidad().getProvincia() != null && cliente.getDireccion().getLocalidad().getProvincia().getIdProvincia() == provincia.getIdProvincia() ? "selected" : "" %>><%= provincia.getNombre() %></option>
                    <% 
                        }
                    } 
                    %>
                </select>
            </div>
            <div class="form-group">
                <label for="localidad">Localidad:</label>
                <select class="form-control" id="localidad" name="localidad" <%= (int) session.getAttribute("tipoUsuario") != 1 && cliente != null ? "readonly" : "required" %>>
                    <option value="">Seleccionar</option>
                    <% 
                    ArrayList<localidad> localidades = (ArrayList<localidad>) request.getAttribute("localidades");
                    if (localidades != null) {
                        for (localidad localidad : localidades) {
                    %>
                        <option value="<%= localidad.getIdLocalidad() %>" data-provincia="<%= localidad.getProvincia().getIdProvincia() %>" <%= cliente != null && cliente.getDireccion() != null && cliente.getDireccion().getLocalidad() != null && cliente.getDireccion().getLocalidad().getIdLocalidad() == localidad.getIdLocalidad() ? "selected" : "" %>><%= localidad.getNombre() %></option>
                    <% 
                        }
                    } 
                    %>
                </select>
            </div>
            <div class="mb-3">
                <label for="correoElectronico" class="form-label">Correo Electronico</label>
                <input type="email" class="form-control" id="correoElectronico" name="correoElectronico" value="<%= cliente != null ? cliente.getCorreoElectronico() : "" %>" <%= (int) session.getAttribute("tipoUsuario") != 1 && cliente != null ? "readonly" : "required" %>>
            </div>
            <div class="mb-3">
                <label for="telefono" class="form-label">Telefono</label>
                <input type="number" class="form-control" id="telefono" name="telefono" value="<%= cliente != null ? cliente.getTelefono() : "" %>" <%= (int) session.getAttribute("tipoUsuario") != 1 && cliente != null ? "readonly" : "required" %>>
            </div>
            <button type="submit" class="btn btn-primary mb-5" id="btnGuardar" <%= (int) session.getAttribute("tipoUsuario") != 1 && cliente != null ? "hidden" : "" %>><%= cliente != null ? "Modificar Cliente" : "Guardar" %></button>
            <% if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 1) { %>
                <a href="ServletClientes?action=listar" class="btn btn-secondary mb-5">Volver</a>
            <% } else if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 2) { %>
                <a href="PrincipalCliente.jsp" class="btn btn-secondary">Volver</a>
            <% } %>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
