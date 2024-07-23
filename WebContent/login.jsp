<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="navbar.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
            <h2 class="text-center">Login</h2>
            <form action="ServletLogin" method="post">
                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre:</label>
                    <input type="text" id="nombre" name="nombre" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="contraseña" class="form-label">Contraseña:</label>
                    <input type="password" id="contraseña" name="contraseña" class="form-control" required>
                </div>
                <div class="mb-3">
                    <input type="submit" value="Login" class="btn btn-primary w-100">
                </div>                
            </form>
        </div>
    </div>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
</body>
</html>

