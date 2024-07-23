<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 2) { %>
<% String error = "¡Operación no permitida! No eres administrador."; %>

<div class="alert alert-danger alert-dismissible fade show" role="alert">
    <%= error %>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
    
<% } else if(session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 1) {%>
<% String error = "¡Operación no permitida! No eres cliente."; %>

<div class="alert alert-danger alert-dismissible fade show" role="alert">
    <%= error %>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
   
<% }else if(session.getAttribute("tipoUsuario") == null){%>
<% String error = "¡Operación no permitida! Logueate."; %>
<div class="alert alert-danger alert-dismissible fade show" role="alert">
    <%= error %>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
<% }%>
</body>
</html>