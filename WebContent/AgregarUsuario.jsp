<%@page import="datosImpl.UsuarioDaoImpl"%>
<%@page import="datos.UsuarioDao"%>
<%@page import="entidad.usuario"%>
<%@ include file="navbar.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<% if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 1) { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Agregar Usuario</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link href="CSS/styles.css" rel="stylesheet">
</head>
<body>
<div class="container" style="margin-top: 100px;">
  <div class="row justify-content-center">
    <div class="col-md-6">
      <% String error = (String) request.getAttribute("error"); %>     
      <% Boolean band = (Boolean) request.getAttribute("bandera"); %>
      <% if (band != null) { %>
          <div class="alert <%= band ? "alert-success" : "alert-danger" %> alert-dismissible fade show" role="alert">
              <%= band ? "¡Operación realizada correctamente!" : error %>
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
              </button>
          </div>
      <% } %>
      <h1 class="text-center">Nuevo Usuario</h1>
      <form action="ServletUsuario" method="get">
        <div class="form-group">
          <label for="txtNombre">Ingrese nombre de usuario:</label>
          <input type="text" id="txtNombre" name="txtNombre" class="form-control" 
                 data-toggle="tooltip" title="Debe tener entre 3 y 20 caracteres, contener solo letras, números, . _ -, no comenzar ni terminar con . - _, no contener caracteres repetidos consecutivamente como .. __ --, y no ser solo números" required>
        </div>
        <div class="mb-3">
                <label for="sexo" class="form-label">Tipo de usuario</label>
                <select class="form-control" id="tipo" name="tipo" required>
                    <option value="1">Administrador</option>
                    <option value="2">Cliente</option>
                </select>
        </div>
        <div class="form-group">
          <label for="txtPass">Ingrese contraseña:</label>
          <input type="password" id="txtPass" name="txtPass" class="form-control" 
                 data-toggle="tooltip" title="La contraseña debe tener al menos 5 caracteres, no comenzar ni terminar con . - _, no contener caracteres repetidos consecutivamente como .. __ --, ni espacios en blanco, y las contraseñas deben coincidir" required>
        </div>
        <div class="form-group">
          <label for="txtPassConfirm">Confirme su contraseña:</label>
          <input type="password" id="txtPassConfirm" name="txtPassConfirm" class="form-control" required>
        </div>
        <div class="mb-3">
          <button type="submit" name="btnAceptar" class="btn btn-primary w-100">Aceptar</button>
        </div>
          <a href="Principal.jsp" class="btn btn-secondary w-100">Volver</a>
      </form>

      <% 
        int filas = 0;
        if (request.getAttribute("cantFilas") != null) {
          filas = Integer.parseInt(request.getAttribute("cantFilas").toString());
        }
        if (filas == 1) {
      %>
        <div class="alert alert-success mt-3" role="alert">
          Insert exitoso
        </div>
      <% } %>
    </div>
  </div>
</div>
<% } else { 
	response.sendRedirect("Error.jsp");
	} %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
<script>
  $(function () {
    $('[data-toggle="tooltip"]').tooltip();
  });
</script>
</body>
</html>
