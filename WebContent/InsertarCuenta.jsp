<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="navbar.jsp" %>
<%@page import="entidad.cuenta" %>
<%@page import="entidad.tipo_de_cuenta" %>
<%@page import="java.util.ArrayList" %>
<% if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 1) { %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Agregar cuenta</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">
  <h1 class="display-4 text-center my-4">Agregar cuenta</h1>

  <% Boolean band = (Boolean) request.getAttribute("bandera"); %>
  <% String error = (String) request.getAttribute("error"); %>
  <% if (error != null) { %>
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      <%= error %>
      <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
  <% } else if (band != null && band) { %>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      ¡Agregado correctamente!
      <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
  <% } %>

  <% 
     String idCliente = request.getParameter("idCliente");
     String numeroCuenta = (String) request.getAttribute("numeroCuenta");
     String cbu = (String) request.getAttribute("cbu");
  %>
  
  <form action="ServletCuentas" method="post">
    <div class="mb-3">
      <label for="idCliente" class="form-label">ID Cliente</label>
      <input type="number" class="form-control" id="idCliente" name="idCliente" value="<%= idCliente %>" readonly required>
    </div>
    <div class="mb-3">
      <label for="id_tipo_cuenta" class="form-label">ID Tipo de Cuenta</label>
      <select class="form-control" id="id_tipo_cuenta" name="id_tipo_cuenta" required>
        <option value="">Selecciona un tipo de cuenta</option>
        <option value="1">Caja de ahorro</option>
        <option value="2">Cuenta corriente</option>
      </select>
    </div>
    <div class="mb-3">
      <label for="numeroCuenta" class="form-label">Número de Cuenta</label>
      <input type="text" class="form-control" id="numeroCuenta" name="numeroCuenta" 
             value="<%= request.getAttribute("numeroCuenta") != null ? request.getAttribute("numeroCuenta") : "El número de cuenta se generará automáticamente al agregar la cuenta" %>" readonly required>
    </div>
    <div class="mb-3">
      <label for="cbu" class="form-label">CBU</label>
      <input type="text" class="form-control" id="cbu" name="cbu" 
             value="<%= request.getAttribute("cbu") != null ? request.getAttribute("cbu") : "El CBU se generará automáticamente al agregar la cuenta" %>" readonly required>
    </div>
    <button type="submit" class="btn btn-primary" id="btnAgregar">Agregar Cuenta</button>
    <a href="ServletClientes?action=listar" class="btn btn-secondary">Volver</a>
  </form>
  
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

</body>
</html>
<% } else { %>
    <%@ include file="Error.jsp" %>
<% } %>
